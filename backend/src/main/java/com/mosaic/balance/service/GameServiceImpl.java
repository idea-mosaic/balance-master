package com.mosaic.balance.service;

import com.mosaic.balance.domain.Game;
import com.mosaic.balance.dto.GameDTO;
import com.mosaic.balance.repository.GameRepository;
import com.mosaic.balance.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    private final VoteRepository voteRepository;

    private final FileService fileService;

    private final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);

    @Override
    public GameDTO.GameCreatedDTO createGame(GameDTO.GameCreateDTO gameCreateDTO) throws Exception {
        logger.info("Create game : {}", gameCreateDTO.getTitle());

        String redImgPath = fileService.upload(gameCreateDTO.getRedImg());
        String blueImgPath = fileService.upload(gameCreateDTO.getBlueImg());
        logger.info("Successfully uploaded file");

        try {
            Game game = Game.builder()
                    .title(gameCreateDTO.getTitle())
                    .red(gameCreateDTO.getRed())
                    .blue(gameCreateDTO.getBlue())
                    .redDescription(gameCreateDTO.getRedDescription())
                    .blueDescription(gameCreateDTO.getBlueDescription())
                    .redImg(redImgPath)
                    .blueImg(blueImgPath)
                    .password(gameCreateDTO.getPw())
                    .build();

            Game createdGame = gameRepository.save(game);

            logger.info("Created : {}", createdGame);

            return GameDTO.GameCreatedDTO.builder()
                    .gameId(createdGame.getGameSeq())
                    .createdDate(createdGame.getCreatedTime())
                    .build();

        } catch (IllegalArgumentException e) {
            logger.info("Failed to create entity");
            fileService.delete(redImgPath);
            fileService.delete(blueImgPath);
            logger.info("Successfully deleted images");
            throw new CannotCreateTransactionException("Failed to create Game");
        }
    }

    @Override
    public GameDTO.GameDetailDTO gameDetail(long gameId, long userIdentifier) {
        logger.info("Get game detail : {}", gameId);
        // throws NoSuchElementException
        Game game = gameRepository.findById(gameId).get();

        GameDTO.GameDetailDTO.GameDetailDTOBuilder builder =
                GameDTO.GameDetailDTO.builder()
                        .gameId(game.getGameSeq())
                        .title(game.getTitle())
                        .red(game.getRed())
                        .blue(game.getBlue())
                        .redDescription(game.getRedDescription())
                        .blueDescription(game.getBlueDescription())
                        .redImg(game.getRedImg())
                        .blueImg(game.getBlueImg())
                        .createdDate(game.getCreatedTime());

        if(voteRepository.existsByVotePKGameGameSeqAndVotePKParticipantSeq(gameId, userIdentifier)) {
            int totalCnt = game.getRedCnt() + game.getBlueCnt();
            // to avoid div by zero
            totalCnt = totalCnt > 0 ? totalCnt : 1;

            GameDTO.GameResultDTO resultDTO = GameDTO.GameResultDTO.builder()
                    .redScore(game.getRedCnt() / totalCnt)
                    .blueScore(game.getBlueCnt() / totalCnt)
                    // about comments
                    .build();

            builder.result(resultDTO);
        }
        return builder.build();
    }

    @Override
    public GameDTO.GameCreatedDTO modifyGame(long gameId, GameDTO.GameCreateDTO gameCreateDTO) {
        return null;
    }

    @Override
    public int deleteGame(long gameId, String pw) {
        return 0;
    }
}
