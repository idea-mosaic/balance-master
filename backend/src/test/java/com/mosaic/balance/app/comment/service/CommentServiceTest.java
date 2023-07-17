package com.mosaic.balance.app.comment.service;

import com.mosaic.balance.domain.Comment;
import com.mosaic.balance.domain.Game;
import com.mosaic.balance.dto.CommentDTO;
import com.mosaic.balance.exception.CommentErrorResult;
import com.mosaic.balance.exception.CommentException;
import com.mosaic.balance.repository.CommentRepository;
import com.mosaic.balance.repository.GameRepository;
import com.mosaic.balance.service.CommentServiceImpl;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentServiceTest {
    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private GameRepository gameRepository;
    private final long commentSeq = 1L;
    private final long gameSeq = 1L;
    private final boolean colorRed = true;
    private final boolean colorBlue = false;
    private final String content = "testContent";
    private final String pwd = "testPwd";
    private CommentDTO.RequestCreateDTO requestCreateDTO = CommentDTO.RequestCreateDTO.builder()
            .nickname("testNickname")
            .choice(colorRed)
            .content(content)
            .pw(pwd)
            .build();
    private CommentDTO.RequestUpdateDTO requestUpdateDTO = CommentDTO.RequestUpdateDTO.builder()
            .content(content)
            .pw("Incorrect PWD")
            .build();
    private CommentDTO.RequestDeleteDTO requestDeleteDTO = CommentDTO.RequestDeleteDTO.builder()
            .pw("Incorrect PWD")
            .build();

    public Game makeGameEntity(){
        return Game.builder()
                .gameSeq(1L)
                .red("red")
                .blue("blue")
                .build();
    }
    public Comment makeCommentEntity(Game game){
        return Comment.builder()
                .commentSeq(commentSeq)
                .color(colorBlue)
                .pwd(pwd)
                .game(game)
                .content(content)
                .build();
    }
    @Test
    public void GameNotExist(){
        //given
        doReturn(Optional.empty()).when(gameRepository).findById(gameSeq);

        //when
        final CommentException result = assertThrows(
                CommentException.class,
                ()-> commentService.createComment(gameSeq, requestCreateDTO));

        //then
        assertThat(result.getErrorResult()).isEqualTo(CommentErrorResult.GAME_NOT_EXIST);
    }

    @Test
    public void PWDIncorrect(){
        //given
        Game game = makeGameEntity();
        Comment comment = makeCommentEntity(game);
        doReturn(Optional.of(game)).when(gameRepository).findById(gameSeq);
        doReturn(Optional.of(comment)).when(commentRepository).findById(commentSeq);

        //when
        final CommentException resultUpdate = assertThrows(
                CommentException.class,
                ()-> commentService.updateComment(gameSeq,commentSeq,requestUpdateDTO));
        final CommentException resultDelete = assertThrows(
                CommentException.class,
                ()-> commentService.deleteComment(commentSeq,requestDeleteDTO));
        //then
        assertThat(resultUpdate.getErrorResult()).isEqualTo(CommentErrorResult.PWD_INCORRECT);
        assertThat(resultDelete.getErrorResult()).isEqualTo(CommentErrorResult.PWD_INCORRECT);
    }

    @Test
    public void CreateComment(){
        //given
        Game game = makeGameEntity();
        doReturn(Optional.of(game)).when(gameRepository).findById(1L);
        doReturn(makeCommentEntity(game)).when(commentRepository).save(any(Comment.class));

        //when
        CommentDTO.ResponseCreateDTO responseCreateDTO = commentService.createComment(1L,requestCreateDTO);

        //then
        assertThat(responseCreateDTO.getCommentSeq()).isEqualTo(1L);
    }
}
