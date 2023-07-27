package com.mosaic.balance.app.comment.controller;

import com.google.gson.Gson;
import com.mosaic.balance.controller.GameCommentController;
import com.mosaic.balance.controller.GameController;
import com.mosaic.balance.domain.Comment;
import com.mosaic.balance.domain.Game;
import com.mosaic.balance.dto.CommentDTO;
import com.mosaic.balance.service.CommentService;
import com.mosaic.balance.util.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {
    @InjectMocks
//    private GameCommentController gameCommentController;
    private GameController gameController;
    @Mock
    private CommentService commentService;
    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    public void init(){
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(gameController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void PWDNotExist() throws Exception {
        //given
        final String url = "/games/1/comments";

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(makeCreateDTO(null,"TestCreateContent",true)))
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void CreateComment() throws Exception{
        //given
        final String url = "/games/1/comments";

        final Comment comment = makeComment("testPwd",null, true );
        final CommentDTO.RequestCreateDTO requestCreateDTO = makeCreateDTO("testPwd","testContent",true);
        final CommentDTO.ResponseCreateDTO responseCreateDTO = new CommentDTO.ResponseCreateDTO(comment);
        doReturn(responseCreateDTO).when(commentService).createComment(any(Long.class),any(CommentDTO.RequestCreateDTO.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(requestCreateDTO))
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isCreated());
        final CommentDTO.ResponseCreateDTO response = gson.fromJson(resultActions.andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8), CommentDTO.ResponseCreateDTO.class);
        assertThat(response.getCommentSeq()).isEqualTo(100L);
    }
    @Test
    public void ReadComment() throws Exception {
        // given
        final String url = "/games/1/comments";
        final Game game = makeGame();
        List<Comment> redComment = new ArrayList<>();
        List<Comment> blueComment = new ArrayList<>();
        redComment.add(makeComment("123",game, true ));
        blueComment.add(makeComment("123",game,false));
        final CommentDTO.RequestReadDTO requestReadDTO = new CommentDTO.RequestReadDTO(0,1,0,1);
        assertThat(redComment.size()).isEqualTo(1);
        final CommentDTO.ResponseReadDTO responseReadDTO = new CommentDTO.ResponseReadDTO(redComment,blueComment);
        doReturn(responseReadDTO)
                .when(commentService)
                .getComments(any(Long.class), any(CommentDTO.RequestReadDTO.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .content(gson.toJson(requestReadDTO))
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().is2xxSuccessful());
        final CommentDTO.ResponseReadDTO response = gson.fromJson(resultActions.andReturn()
                .getResponse().getContentAsString(StandardCharsets.UTF_8),CommentDTO.ResponseReadDTO.class);
        assertThat(response.getBlueComments().size()).isEqualTo(1);
        assertThat(response.getRedComments().size()).isEqualTo(1);

    }
    @Test
    public void UpdateComment() throws Exception {
        // given
        final String url = "/games/1/comments/1";
        final CommentDTO.RequestUpdateDTO requestUpdateDTO = new CommentDTO.RequestUpdateDTO("test update content", "correct");
        final CommentDTO.ResponseUpdateDTO responseUpdateDTO = new CommentDTO.ResponseUpdateDTO(makeComment("pwd",null,true));
        doReturn(responseUpdateDTO)
                .when(commentService)
                .updateComment(any(Long.class),any(Long.class),any(CommentDTO.RequestUpdateDTO.class));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.patch(url)
                        .content(gson.toJson(requestUpdateDTO))
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().is2xxSuccessful());
        final CommentDTO.ResponseUpdateDTO response = gson.fromJson(
                resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8),
                CommentDTO.ResponseUpdateDTO.class);
        assertThat(response.getUpdatedTime()).isNull();

    }
    @Test
    public void DeleteComment() throws Exception{
        //given
        final String url = "/games/1/comments/1";
        final CommentDTO.RequestDeleteDTO requestDeleteDTO = new CommentDTO.RequestDeleteDTO("pw");

        //when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(requestDeleteDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().is2xxSuccessful());
    }

    public CommentDTO.RequestCreateDTO makeCreateDTO(String pw, String content, boolean color){
        return CommentDTO.RequestCreateDTO.builder()
                .nickname("testNickname")
                .pw(pw)
                .content(content)
                .choice(color)
                .build();
    }
    public Comment makeComment(String pw, Game game , boolean color){
        return Comment.builder()
                .commentSeq(100L)
                .game(game)
                .pwd(pw)
                .color(true)
                .content("testContent")
                .nickname("testNickname")
                .build();
    }
    public Game makeGame(){
        return Game.builder()
                .blue("blue")
                .red("red")
                .password("pwd")
                .redCnt(0)
                .blueCnt(0)
                .build();
    }
}
