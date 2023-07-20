package com.mosaic.balance.app.game.repository;

import com.mosaic.balance.domain.Game;
import com.mosaic.balance.dto.GameDTO;
import com.mosaic.balance.repository.GameRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void createTest() {
        // given
        Game game = game();

        // when
        Game createdGame = gameRepository.save(game);

        // then
        assertNotNull(createdGame);
    }

    @Test
    public void readTest() {
        // given
        Game createdGame = gameRepository.save(game());

        // when
        Game game = gameRepository.findById(createdGame.getGameSeq()).get();

        // then
        // success
        assertNotNull(game);
        assertEquals(game.getCreatedTime(), createdGame.getCreatedTime());

        // failure
        Assertions.assertThatThrownBy(() ->
                        gameRepository.findById(9999999999L).get())
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void modifyTest() {
        // given
        Game createdGame = gameRepository.save(game());
        Game game = gameRepository.findById(createdGame.getGameSeq()).get();

        // when
        game.modify(GameDTO.GameCreateDTO.builder()
                        .title("MODIFIED")
                        .build(),
                "new_red_img", null
                );
        gameRepository.save(game);
        Game modified = gameRepository.findById(createdGame.getGameSeq()).get();

        // then
        assertEquals(modified.getTitle(), "MODIFIED");
        assertEquals(modified.getRedImg(), "new_red_img");

    }

    @Test
    public void deleteTest() {
        // given
        Game createdGame = gameRepository.save(game());

        // when
        gameRepository.deleteById(createdGame.getGameSeq());

        // then
        assertEquals(true, gameRepository.findById(createdGame.getGameSeq()).isEmpty());

    }
    private Game game() {
        return Game.builder()
                .title("red vs. blue")
                .red("red").blue("blue")
                .redDescription("freaking red")
                .blueDescription("amazing blue")
                .password("1234")
                .build();
    }

}