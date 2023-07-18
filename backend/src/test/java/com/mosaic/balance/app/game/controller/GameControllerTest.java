package com.mosaic.balance.app.game.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosaic.balance.controller.GameController;
import com.mosaic.balance.dto.GameDTO;
import com.mosaic.balance.service.GameService;
import jdk.jfr.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class GameControllerTest {

    private MockMvc mockMvc;

    private Gson gson;

    @InjectMocks
    private GameController gameController;
    @Mock
    private GameService gameService;

    @BeforeEach
    public void initMock() {
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
        gson = new Gson();
    }

    @Test
    public void initTest() {
        assertNotNull(mockMvc);
        assertNotNull(gson);
    }

    /*
    Create
        - success(without file)
        - success(file)
        - BR(absent) [TBD]
        - BR(contents too long) [TDB]
        - 500
     */
    @Test
    public void createGameTest() throws Exception {
        // given
        String url = "/games";
        GameDTO.GameCreateDTO gameCreateDTO = createGameDTO();
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("gameInfo", gameCreateDTO);

        when(gameService.createGame(any(GameDTO.GameCreateDTO.class)))
                .thenReturn(GameDTO.GameCreatedDTO.builder().gameId(123).build());

        // when
//        ResultActions result = mockMvc.perform(
//                MockMvcRequestBuilders.post(url)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(gson.toJson(requestBody))
//                        .characterEncoding("UTF-8")
//        );
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.multipart(HttpMethod.POST, url)
                        .file(new MockMultipartFile("gameInfo", "", MediaType.APPLICATION_JSON_VALUE, gson.toJson(gameCreateDTO).getBytes("UTF8")))
                        .characterEncoding("UTF-8")
        );

        // then
        result.andExpect(status().isCreated());

        GameDTO.GameCreatedDTO gameCreatedDTO = responseToDTO(result, GameDTO.GameCreatedDTO.class);

        assertNotNull(gameCreatedDTO);
        assertTrue(gameCreatedDTO.getGameId() == 123L);
    }

    @Test
    public void createGameWithFileTest() throws Exception {
        // given
        String url = "/games";
        GameDTO.GameCreateDTO gameCreateDTO = createGameDTO();
        when(gameService.createGame(any(GameDTO.GameCreateDTO.class)))
                .thenReturn(GameDTO.GameCreatedDTO.builder().gameId(123).build());

        // when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.multipart(HttpMethod.POST, url)
                        .file(new MockMultipartFile("redImg", "redImg.png", MediaType.TEXT_PLAIN_VALUE, new byte[1]))
                        .file(new MockMultipartFile("blueImg", "blueImg.png", MediaType.TEXT_PLAIN_VALUE, new byte[1]))
                        .file(new MockMultipartFile("gameInfo", "", MediaType.APPLICATION_JSON_VALUE, gson.toJson(gameCreateDTO).getBytes("UTF8")))
                        .characterEncoding("UTF-8")
        );

        // then
        result.andExpect(status().isCreated());

        GameDTO.GameCreatedDTO gameCreatedDTO = responseToDTO(result, GameDTO.GameCreatedDTO.class);

        assertNotNull(gameCreatedDTO);
        assertTrue(gameCreatedDTO.getGameId() == 123L);
    }

//    @ParameterizedTest
//    @MethodSource("gameCreateDTOStream")
//    public void createGameWithInAdequateParam(GameDTO.GameCreateDTO gameCreateDTO) throws Exception {
//        assertNotNull(gameCreateDTO);
//    }
//
//    private static Stream<Arguments> gameCreateDTOStream() {
//        return Stream.of(
//                Arguments.of(GameDTO.GameCreateDTO.builder().build())
//        );
//    }

    @Test
    public void createGameFailedWithFile() throws Exception {
        // given
        String url = "/games";
        GameDTO.GameCreateDTO gameCreateDTO = createGameDTO();
        when(gameService.createGame(any(GameDTO.GameCreateDTO.class)))
                .thenThrow(Exception.class);
        // when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.multipart(HttpMethod.POST, url)
                        .file(new MockMultipartFile("redImg", "redImg.png", MediaType.MULTIPART_FORM_DATA_VALUE, new byte[1]))
                        .file(new MockMultipartFile("blueImg", "blueImg.png", MediaType.MULTIPART_FORM_DATA_VALUE, new byte[1]))
                        .file(new MockMultipartFile("gameInfo", "", MediaType.APPLICATION_JSON_VALUE, gson.toJson(gameCreateDTO).getBytes()))
        );

        // then
        result.andExpect(status().isInternalServerError());
    }
    /*
    Detail
        - success(played)
        - success(DNP)
        - Not found
        - required header is absent
     */

    /*
    Modify
        - success(file)
        - success(without file)
        - Not found
        - Unauthorized
        - 500
     */

    /*
    Delete
        - success
        - Not found
        - Unauthorized
        - 500
     */

    /**
     * Create DTO without file
     */
    private GameDTO.GameCreateDTO createGameDTO() {
        return GameDTO.GameCreateDTO.builder()
                .title("red vs. blue")
                .red("red").redDescription("RED")
                .blue("blue").blueDescription("BLUE")
                .redImg(null).blueImg(null)
                .pw("1234")
                .build();
    }
    /**
     * Create DTO with file
     */
    private GameDTO.GameCreateDTO createGameWithFile() {
        return GameDTO.GameCreateDTO.builder()
                .title("red vs. blue")
                .red("red").redDescription("RED")
                .blue("blue").blueDescription("BLUE")
                .redImg(new MockMultipartFile("red_img.png", new byte[0]))
                .blueImg(new MockMultipartFile("blue_img.png", new byte[0]))
                .pw("1234")
                .build();
    }
    /**
     * Convert response to DTO
     * GameDTO.GameCreatedDTO response = gson.fromJson(
     *                         result.andReturn().getResponse()
     *                         .getContentAsString(StandardCharsets.UTF_8)
     *                 , GameDTO.GameCreatedDTO.class);
     */
    private <T> T responseToDTO(ResultActions result, Class type) throws Exception {
        return (T) gson.fromJson(
                result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8), type
        );
    }
}
