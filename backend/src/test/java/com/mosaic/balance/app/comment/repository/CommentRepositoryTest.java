package com.mosaic.balance.app.comment.repository;

import com.mosaic.balance.domain.Comment;
import com.mosaic.balance.domain.Game;
import com.mosaic.balance.repository.CommentRepository;
import com.mosaic.balance.repository.GameRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.internal.exceptions.ExceptionIncludingMockitoWarnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private EntityManager entityManager;
    private static int time = 1;
    public ArrayList<Comment> makeCommentEntity(Game game,long seq){
        ArrayList<Comment> ret =new ArrayList<>();
        ret.add(Comment.builder()
                .commentSeq(seq+1)
                .color(true)
                .content("Test create comment Red")
                .nickname("anony")
                .pwd("test_pwd")
                .game(game)
                .build());
        ret.add(Comment.builder()
                .commentSeq(seq+2)
                .color(false)
                .content("Test create comment Blue")
                .nickname("anony")
                .pwd("test_pwd")
                .game(game)
                .build());
        return ret;
    }

    public Game makeGameEntity(){
        return Game.builder()
                .gameSeq(1L)
                .red("red")
                .blue("blue")
                .build();
    }
    @Test
    @Rollback(value = false)
    @Order(0)
    public void createComment(){
        // given
        final Game game = gameRepository.save(makeGameEntity());
        final ArrayList<Comment> comment = makeCommentEntity(game,0);

        //when
        final Comment resultRedComment = commentRepository.save(comment.get(0));
        final Comment resultBlueComment = commentRepository.save(comment.get(1));

        //then
        assertThat(resultRedComment.getCommentSeq()).isEqualTo(1);
        assertThat(resultRedComment.isColor()).isTrue();
        assertThat(resultRedComment.getContent()).isEqualTo("Test create comment Red");
        assertThat(resultRedComment.getNickname()).isEqualTo("anony");
    }
    @Test
    @Rollback(value = false) // 이 테스트 끝나고 rollback 안할거임 !
    @Order(1)
    public void deleteComment(){

        //given
        final Optional<Game> optionalGame = gameRepository.findById(1L);
        Game game = null ;
        if(!optionalGame.isPresent()){
            game = gameRepository.save(makeGameEntity());
            for (Comment comment : makeCommentEntity(game,0)){
                commentRepository.save(comment);
            }
        }else {
            game = optionalGame.get();
        }

        List<Comment> comments = game.getComments();
        comments.removeIf(comment->comment.getCommentSeq()==1L);
        commentRepository.deleteById(1L);

        //then
    }
    @Test
    @Order(2)
    public void afterDelete() throws Exception{
        final Game game = gameRepository.findById(1L).orElseThrow(()->new Exception("not exist game : 1"));
        final List<Comment> comments = game.getComments();
        assertThat(comments.size()).isEqualTo(1); // 사이즈 1이여야됨
    }
    @Test
    @Order(3)
    public void readComment() throws Exception {
        //given
        final Game game= gameRepository.findById(1L).orElseThrow(()->new Exception("not exist game : 1"));
        final List<Comment> comments = game.getComments();

        assertThat(comments.size()).isEqualTo(1);
        assertThat(comments.get(0).isColor()).isFalse();
        assertThat(comments.get(0).getContent()).isEqualTo("Test create comment Blue");
    }
    @Test
    @Order(4)
    public void updateComment() throws Exception{
        final Comment comment = commentRepository.findById(2L).get();
        final String modifiedContent = "test update content";

        final Comment modifiedComment = Comment.builder()
                .commentSeq(comment.getCommentSeq())
                .color(comment.isColor())
                .content(modifiedContent)
                .pwd(comment.getPwd())
                .nickname(comment.getNickname())
                .build();
        commentRepository.save(modifiedComment);
        assertThat(commentRepository.findById(2L).get().getContent()).isEqualTo("test update content");
    }
    @Test
    @Order(6)
    public void pagingComment() throws Exception{
        //given
        final Game game = gameRepository.findById(1L).orElseThrow(()-> new Exception("not exist game : 1L"));
        List<Comment> comments = makeCommentEntity(game,100L);
        for(Comment comment : comments){
            commentRepository.save(comment);
        }

        PageRequest pageRequest = PageRequest.of(0,2);

        //when
        final List<Comment> commentsRed = commentRepository.findByGameAndColor(game,false, pageRequest);
        final List<Comment> commentsBlue = commentRepository.findByGameAndColor(game, true, pageRequest);
        //then
        assertThat(commentsRed.size()).isEqualTo(2);
        assertThat(commentsBlue.size()).isEqualTo(1);


    }
    @Test
    @Order(7)
    @Rollback(value = false)
    public void createdCommentTime() throws Exception{
        //given
        final Game game = makeGameEntity();
        final List<Comment> comments = makeCommentEntity(game,100L);

        //when
        final Comment beforeComment = commentRepository.save(comments.get(0));
        final Comment afterComment = commentRepository.save(comments.get(1));

        //then
        assertThat(beforeComment.getCreatedTime().isBefore(afterComment.getCreatedTime())).isTrue();

    }

    @Test
    @Order(8)
    public void updatedCommentTime() throws Exception{
        //given
        Comment comment = commentRepository.findById(5L).orElseThrow(()->new Exception("not exist comment:5L"));

        //when
        comment.updateContent("Test updated time");
        commentRepository.save(comment);
        commentRepository.flush();
        comment = commentRepository.findById(5L).orElseThrow(()->new Exception("not exist comment : 5L"));

        //then
        assertThat(comment.getUpdatedTime().getNano()).isNotEqualTo(comment.getCreatedTime().getNano());
    }
}
