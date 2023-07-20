package com.mosaic.balance.service;

import com.mosaic.balance.domain.Game;
import com.mosaic.balance.dto.GameDTO;
import com.mosaic.balance.repository.GameRepository;
import com.mosaic.balance.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

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

        String[] imgUrls = fileService.upload(new MultipartFile[] {
                gameCreateDTO.getRedImg(),
                gameCreateDTO.getBlueImg(),
        }, "image");

        logger.info("Successfully uploaded file");

        try {
            Game game = Game.builder()
                    .title(gameCreateDTO.getTitle())
                    .red(gameCreateDTO.getRed())
                    .blue(gameCreateDTO.getBlue())
                    .redDescription(gameCreateDTO.getRedDescription())
                    .blueDescription(gameCreateDTO.getBlueDescription())
                    .redImg(imgUrls[0])
                    .blueImg(imgUrls[1])
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
            fileService.delete(imgUrls);
            logger.info("Successfully deleted images");
            throw new CannotCreateTransactionException("Failed to create Game");
        }
    }

    @Override
    public GameDTO.GameDetailResponseDTO gameDetail(long gameId, long userIdentifier) {
        logger.info("Get game detail : {}", gameId);
        // throws NoSuchElementException
        Game game = gameRepository.findById(gameId).get();

        GameDTO.GameDetailResponseDTO.GameDetailResponseDTOBuilder builder =
                GameDTO.GameDetailResponseDTO.builder().game(
                        GameDTO.GameDetailDTO.builder()
                                .gameId(game.getGameSeq())
                                .title(game.getTitle())
                                .red(game.getRed())
                                .blue(game.getBlue())
                                .redDescription(game.getRedDescription())
                                .blueDescription(game.getBlueDescription())
                                .redImg(game.getRedImg())
                                .blueImg(game.getBlueImg())
                                .createdDate(game.getCreatedTime())
                                .build()
                );

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
    public GameDTO.GameModifiedDTO modifyGame(long gameId, GameDTO.GameCreateDTO gameCreateDTO) throws Exception {
        logger.info("Modify Game : {}", gameId);
        // throws NoSuchElementException
        Game game = gameRepository.findById(gameId).get();

        // AUTH check
        if(game.getPassword() != null && (!game.getPassword().equals(gameCreateDTO.getPw()))) {
            logger.info("Incorrect Password : {}", gameCreateDTO.getPw());
            throw new AccessDeniedException("Incorrect password");
        }
        // also AUTH check for users

        String redImgUrl = game.getRedImg();
        String blueImgUrl = game.getBlueImg();
        String[] newImgUrl = fileService.upload(
                new MultipartFile[] {gameCreateDTO.getRedImg(), gameCreateDTO.getBlueImg()}, "image");


        try {
            game.modify(gameCreateDTO, newImgUrl[0], newImgUrl[1]);
            gameRepository.save(game);
        } catch (IllegalArgumentException e) {
            logger.info("Failed to Update Entity\n Caused by : {}", e.getCause());
            fileService.delete(newImgUrl);
            throw new IllegalArgumentException();
        }

        logger.info("Successfully Updated Entity, delete old files");
        fileService.delete(redImgUrl);
        fileService.delete(blueImgUrl);

        return GameDTO.GameModifiedDTO.builder()
                .gameId(game.getGameSeq())
                .redImg(game.getRedImg())
                .blueImg(game.getBlueImg())
                .createdDate(game.getCreatedTime())
                .build();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public int deleteGame(long gameId, String pw) throws Exception {
        logger.info("delete game : {}", gameId);

        //throws NoSuchElementException
        Game game = gameRepository.findById(gameId).get();

        // AUTH check
        if(game.getPassword() != null && (!game.getPassword().equals(pw))) {
            logger.info("Incorrect Password : {}", pw);
            throw new AccessDeniedException("Incorrect password");
        }
        // also AUTH check for users

        gameRepository.delete(game);

        try {
            logger.info("delete files : {}, {}", game.getRedImg(), game.getBlueImg());
            int deleted = 0;
            deleted +=
                fileService.delete(game.getRedImg());
            deleted +=
                fileService.delete(game.getBlueImg());

        } catch (Exception e) {
            logger.info("Failed to delete file, ROLLBACK\nCaused by : {}", e.getCause());
            throw e;
        }

        return 0;
    }
}
