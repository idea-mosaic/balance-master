package com.mosaic.balance.app.game.repository;

import com.mosaic.balance.domain.Game;
import com.mosaic.balance.dto.GameDTO;
import com.mosaic.balance.repository.GameRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
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

    @Test
    public void gameListTest() {
        // given
        for(int i=0; i<4; i++)
            gameRepository.save(game());
        // hottest game
        gameRepository.save(getGameBuilder().title("hot").participantCnt(49).build());
        // new game
        gameRepository.save(getGameBuilder().title("sexy").build());
        // DNP games [TBD]

        // when
        PageRequest pageRequest = PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "participantCnt"));
        List<Game> hotGames = gameRepository.findAll(pageRequest).getContent();
        pageRequest = PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "createdTime"));
        List<Game> newGames = gameRepository.findAll(pageRequest).getContent();
        // DNP [TBD]

        // then
        assertEquals(4, hotGames.size());
        assertEquals("hot", hotGames.get(0).getTitle());
        assertEquals("sexy", newGames.get(0).getTitle());
        // DNP [TBD]

    }

    @Test
    public void emptyGameListTest() {
        // given

        // when
        PageRequest pageRequest = PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "participantCnt"));
        List<Game> hotGames = gameRepository.findAll(pageRequest).getContent();

        assertTrue(hotGames.isEmpty());
        assertEquals(0, hotGames.size());
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

    private Game.GameBuilder getGameBuilder() {
        return Game.builder()
                .red("RED").blue("BLUE")
                .redDescription("REEEED")
                .blueDescription("BLOOO")
                .password("1234");
    }

}