package com.mosaic.balance.app.game.service;

import com.mosaic.balance.domain.Game;
import com.mosaic.balance.domain.Vote;
import com.mosaic.balance.dto.GameDTO;
import com.mosaic.balance.repository.GameRepository;
import com.mosaic.balance.repository.VoteRepository;
import com.mosaic.balance.service.FileService;
import com.mosaic.balance.service.GameServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    @InjectMocks
    private GameServiceImpl gameService;
    @Mock
    private FileService fileService;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private VoteRepository voteRepository;

    /*
    Create
        - success(without image)
        - success(with image)
        - failed to upload image
        - failed to create entity
     */
    @Test
    public void createGameTest() throws Exception {
        // given
        // thenReturn : execute & return
        when(gameRepository.save(any(Game.class))).thenReturn(Game.builder().gameSeq(32L).build());
        // doReturn : return without executing
        doReturn(new String[2]).when(fileService).upload(any(MultipartFile[].class), anyString());

        // when
        GameDTO.GameCreatedDTO result = gameService.createGame(gameCreateDTO());

        // then
        assertNotNull(result);
        assertEquals(result.getGameId(), 32L);
    }

    @Test
    public void createGameWithImgTest() throws Exception {
        // given
        when(gameRepository.save(any(Game.class))).thenReturn(Game.builder().gameSeq(32L).build());
        doReturn(new String[2]).when(fileService).upload(any(MultipartFile[].class), anyString());

        // when
        GameDTO.GameCreatedDTO result = gameService.createGame(gameCreateWithImageDTO());

        // then
        assertEquals(result.getGameId(), 32L);
    }

    @Test
    public void failedToUploadImgTest() throws Exception {
        // given
        when(fileService.upload(any(MultipartFile[].class), anyString())).thenThrow(new IOException());

        // when & then
        Assertions.assertThatThrownBy(() -> gameService.createGame(gameCreateDTO()))
                .isInstanceOf(IOException.class);

    }

    @Test
    public void failedToCreateGameTest() throws Exception {
        // given
        when(gameRepository.save(any()))
                .thenThrow(new CannotCreateTransactionException("Failed to create Game"));
        doReturn(new String[2]).when(fileService).upload(any(MultipartFile[].class), anyString());
        // create file

        // when
        Assertions.assertThatThrownBy(() -> gameService.createGame(gameCreateDTO()))
                .isInstanceOf(CannotCreateTransactionException.class);

        // then
        // check whether file exists
    }

    /*
    Read
        - success(DNP yet)
        - success(played)
        - game with given PK does not exist
     */
    @Test
    public void readDNPGameTest() {
        // given
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(Game.builder().build()));
        when(voteRepository.existsByVotePKGameGameSeqAndVotePKParticipantSeq(anyLong(), anyLong()))
                .thenReturn(false);
        //

        // when
        GameDTO.GameDetailResponseDTO result = gameService.gameDetail(1L, 192168000001L);

        // then
        assertNotNull(result);
        assertNull(result.getResult());
    }

    @Test
    public void readPlayedGameTest() {
        // given
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(Game.builder().build()));
        when(voteRepository.existsByVotePKGameGameSeqAndVotePKParticipantSeq(anyLong(), anyLong()))
                .thenReturn(true);

        // when
        GameDTO.GameDetailResponseDTO result = gameService.gameDetail(1L, 127168000001L);

        // then
        assertNotNull(result);
        assertNotNull(result.getResult());
    }

    @Test
    public void readGameNotExistsTest() {
        // given
        when(gameRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then
        Assertions.assertThatThrownBy(() -> gameService.gameDetail(1L, 1354645646L))
                .isInstanceOf(NoSuchElementException.class);

    }

    /*
    Modify
        - success(without image)
        - success(with image) [YET]
        - unauthorized(incorrect password)
        - unauthorized(user) [TBD]
        - game not found
        - delete file(s) [TBD]
        - failed with images [YET]
     */
    @Test
    public void modifyGameTest() throws Exception{
        // given
        when(gameRepository.findById(anyLong()))
                .thenReturn(Optional.of(Game.builder().password("1234").build()));
        doReturn(new String[2]).when(fileService).upload(any(MultipartFile[].class), anyString());

        // when
        GameDTO.GameModifiedDTO result = gameService.modifyGame(123L, gameCreateDTO());

        // then
        assertNotNull(result);
    }

    @Test
    public void modifyGameImageTest() throws Exception{
        // given
        when(gameRepository.findById(anyLong()))
                .thenReturn(Optional.of(Game.builder().password("1234").build()));
        doReturn(new String[]{"new_img_url", ""}).when(fileService).upload(any(MultipartFile[].class), anyString());

        // when
        GameDTO.GameModifiedDTO result = gameService.modifyGame(123L, gameCreateWithImageDTO());

        // then
        assertNotNull(result);
        assertEquals("new_img_url", result.getRedImg());
        // check whether file has been deleted
    }

    @Test
    public void modifyGameWithIncorrectPWTest() throws Exception {
        // given
        when(gameRepository.findById(anyLong()))
                .thenReturn(Optional.of(Game.builder().password("password").build()));

        // when & then
        Assertions.assertThatThrownBy(() -> gameService.modifyGame(123L, gameCreateDTO()))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    public void modifyGameNotFoundTest() throws Exception {
        // given
        when(gameRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then
        Assertions.assertThatThrownBy(() -> gameService.modifyGame(123L, gameCreateDTO()))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void modifyGameFailedWithFileTest() throws Exception {
        // given
        when(gameRepository.findById(anyLong()))
                .thenReturn(Optional.of(Game.builder()
                                .password("1234")
                                .redImg("original_image.png").build()));
        doThrow(IOException.class).when(fileService).upload(any(MultipartFile[].class), anyString());

        // when
        Assertions.assertThatThrownBy(() ->
                gameService.modifyGame(12345L, gameCreateWithImageDTO()))
                .isInstanceOf(IOException.class);

        // then
    }

    /*
    Delete
        - success(correct pw)
        - unauthorized
        - not found
        - failed to delete file [Unable to get result from s3.deleteObject()]
     */
    @Test
    public void deleteGameTest() throws Exception {
        // given
        when(gameRepository.findById(anyLong()))
                .thenReturn(Optional.of(Game.builder()
                                .redImg("src_img.jpg")
                                .password("1234").build()));
        doReturn(0).when(fileService).delete(anyString());

        // when
        int res = gameService.deleteGame(123L, "1234");

        // then
        assertEquals(0, res);
    }

    @Test
    public void deleteGameWithIncorrectPWTest() throws Exception {
        // given
        when(gameRepository.findById(anyLong()))
                .thenReturn(Optional.of(Game.builder().password("1234").build()));

        // when & then
        Assertions.assertThatThrownBy(() ->
                gameService.deleteGame(123L, "password"))
                .isInstanceOf(AccessDeniedException.class);

    }

    @Test
    public void deleteGameNotFoundTest() throws Exception {
        // given
        when(gameRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        // when & then
        Assertions.assertThatThrownBy(() ->
                gameService.deleteGame(123L, "1234"))
                .isInstanceOf(NoSuchElementException.class);
    }


    /**
     * Creates request DTO to create game
     * @return DTO
     */
    private GameDTO.GameCreateDTO gameCreateDTO() {
        return GameDTO.GameCreateDTO.builder()
                        .title("red vs. blue")
                        .red("red").redDescription("RED")
                        .blue("blue").blueDescription("BLUE")
                        .pw("1234")
                        .build();
    }

    private GameDTO.GameCreateDTO gameCreateWithImageDTO() {
        return GameDTO.GameCreateDTO.builder()
                .title("red vs. blue")
                .red("red").redDescription("RED")
                .blue("blue").blueDescription("BLUE")
                .redImg(new MockMultipartFile("red.jpg", new byte[0]))
                .blueImg(new MockMultipartFile("blue.png", new byte[0]))
                .pw("1234")
                .build();
    }

    /**
     * Mock repository
     */
}