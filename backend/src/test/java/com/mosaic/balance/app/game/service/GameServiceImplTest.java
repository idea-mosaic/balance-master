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
import org.springframework.transaction.CannotCreateTransactionException;

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
        - success
        - failed to upload image
        - failed to create entity
     */
    @Test
    public void createGameTest() throws Exception {
        // given
        // thenReturn : execute & return
        when(gameRepository.save(any(Game.class))).thenReturn(Game.builder().gameSeq(32L).build());
        // doReturn : return without executing
        doReturn("img_url").when(fileService).upload(any());

        // when
        GameDTO.GameCreatedDTO result = gameService.createGame(gameCreateDTO());

        // then
        assertNotNull(result);
        assertEquals(result.getGameId(), 32L);
    }

    @Test
    public void failedToUploadImgTest() throws Exception {
        // given
        when(fileService.upload(any())).thenThrow(new Exception());

        // when & then
        Assertions.assertThatThrownBy(() -> gameService.createGame(gameCreateDTO()))
                .isInstanceOf(Exception.class);

    }

    @Test
    public void failedToCreateGameTest() throws Exception {
        // given
        when(gameRepository.save(any()))
                .thenThrow(new CannotCreateTransactionException("Failed to create Game"));
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
        GameDTO.GameDetailDTO result = gameService.gameDetail(1L, 192168000001L);

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
        GameDTO.GameDetailDTO result = gameService.gameDetail(1L, 127168000001L);

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

    /**
     * Mock repository
     */
}