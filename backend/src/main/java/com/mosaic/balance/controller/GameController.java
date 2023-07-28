package com.mosaic.balance.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mosaic.balance.dto.CommentDTO;
import com.mosaic.balance.dto.GameDTO;
import com.mosaic.balance.service.CommentService;
import com.mosaic.balance.service.GameService;
import com.mosaic.balance.util.IPAddressUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

    private final Logger logger = LoggerFactory.getLogger(GameController.class);

    private final GameService gameService;
    final private CommentService commentService;

    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> addrTest(HttpServletRequest request) {
        HttpStatus status;
        Map<String, Object> resultMap = new HashMap<>();

        String addr = IPAddressUtil.getIPAddress(request);
        logger.info("IP : {}", addr);

        status = HttpStatus.OK;

        return new ResponseEntity<>(status);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> createGame(
            @RequestPart(value = "gameInfo") GameDTO.GameCreateDTO gameCreateDTO,
            @RequestPart(value = "redImg", required = false) MultipartFile redImg,
            @RequestPart(value = "blueImg", required = false) MultipartFile blueImg
    ) {
        HttpStatus status;
        Map<String, Object> resultMap = new HashMap<>();

        logger.info("game : {}", gameCreateDTO.toString());
        logger.info("image size : {}", redImg != null ? redImg.getSize() : 0);

        try {
            logger.info("Create Game. title : {}", gameCreateDTO.getTitle());
            gameCreateDTO.appendImage(redImg, blueImg);
            GameDTO.GameCreatedDTO gameCreatedDTO = gameService.createGame(gameCreateDTO);

            logger.info("Successfully created game, game Id : {}", gameCreatedDTO.getGameId());
            status = HttpStatus.CREATED;
            resultMap.put("gameId", gameCreatedDTO.getGameId());
            resultMap.put("createdDate", gameCreatedDTO.getCreatedDate());

        } catch (Exception e) {
            logger.info("failed to create game. caused by : {}", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(resultMap, status);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<Map<String, Object>> gameDetail(
            HttpServletRequest request,
            @PathVariable long gameId,
            @RequestBody(required = false) CommentDTO.RequestReadDTO commentReadDTO) {
        HttpStatus status;
        Map<String, Object> resultMap = new HashMap<>();

        logger.info("get game detail : {}", gameId);

        try {
            String userIp = IPAddressUtil.getIPAddress(request);
            logger.info("user IP address : {}", userIp);
            if(userIp == null || userIp.equals("")) {
                throw new InsufficientAuthenticationException("No IP address available");
            }

            // ip address will be determined soon...
            GameDTO.GameDetailResponseDTO gameDetailResponseDTO = gameService.gameDetail(gameId, 123L, commentReadDTO);
            status = HttpStatus.OK;
            resultMap.put("game", gameDetailResponseDTO.getGame());
            resultMap.put("result", gameDetailResponseDTO.getResult());

        } catch (NoSuchElementException e) {
            logger.info("Game {} Not found", gameId);
            status = HttpStatus.NOT_FOUND;
        } catch (InsufficientAuthenticationException e) {
            logger.info("User Identifier is absent");
            status = HttpStatus.UNAUTHORIZED;
        } catch (Exception e) {
            logger.info("Unintended Exception occurred : {}", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(resultMap, status);
    }

    @PatchMapping(value = "/{gameId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Map<String, Object>> modifyGame(@PathVariable long gameId,
                    @RequestPart(value = "gameInfo", required = true)GameDTO.GameCreateDTO gameCreateDTO,
                    @RequestPart(value = "redImg", required = false) MultipartFile redImg,
                    @RequestPart(value = "blueImg", required = false) MultipartFile blueImg){
        HttpStatus status;
        Map<String, Object> resultMap = new HashMap<>();

        logger.info("modify game : {}", gameId);

        try {
            GameDTO.GameModifiedDTO modifiedDTO = gameService.modifyGame(gameId, gameCreateDTO);
            logger.info("Game modified");

            status = HttpStatus.OK;
            resultMap.put("redImg", modifiedDTO.getRedImg());
            resultMap.put("blueImg", modifiedDTO.getBlueImg());

        } catch (NoSuchElementException e) {
            logger.info("Game {} Not found", gameId);
            status = HttpStatus.NOT_FOUND;
        } catch (AccessDeniedException e) {
            logger.info("Incorrect password : {}", gameCreateDTO.getPw());
            status = HttpStatus.UNAUTHORIZED;
        } catch (Exception e) {
            logger.info("Unintended Error : {}", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(resultMap, status);
    }

    @DeleteMapping("{gameId}")
    public ResponseEntity deleteGame(@PathVariable long gameId, @RequestBody(required = false) String pw) {
        HttpStatus status;

        logger.info("delete game : {}", gameId);

        try {
            int res = gameService.deleteGame(gameId, pw);

            logger.info("Successfully deleted game : {}", gameId);
            status = HttpStatus.OK;
        } catch (NoSuchElementException e) {
            logger.info("Game {} Not found", gameId);
            status = HttpStatus.OK;
        } catch (AccessDeniedException e) {
            logger.info("Unauthorized access or wrong password({})", pw);
            status = HttpStatus.UNAUTHORIZED;
        } catch (Exception e) {
            logger.info("Unintended Error : {}", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity(status);
    }

    @GetMapping
    public ResponseEntity<GameDTO.GameListDTO> getGameList() {
        HttpStatus status;
        logger.info("Get Game list");
        try {
            GameDTO.GameListDTO gameListDTO = gameService.getGameList();
            status = HttpStatus.OK;
            return new ResponseEntity<>(gameListDTO, status);

        } catch (NoSuchElementException e) {
            logger.info("No games are found");
            status = HttpStatus.NOT_FOUND;
        } catch (Exception e) {
            logger.info("Unintended Error : {}", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(status);
    }

    @GetMapping("{gameId}/comments")
    public ResponseEntity<CommentDTO.ResponseReadDTO> getGameComments(
            @PathVariable("gameId") long gameId,
            @RequestBody @Valid CommentDTO.RequestReadDTO requestReadDTO){
        return new ResponseEntity<>(
                commentService.getComments(gameId, requestReadDTO),
                HttpStatus.OK);
    }

    @PostMapping("{gameId}/comments")
    public ResponseEntity<CommentDTO.ResponseCreateDTO> addGameComment(
            @PathVariable("gameId") long gameId,
            @RequestBody @Valid CommentDTO.RequestCreateDTO requestCreateDTO) {

        return new ResponseEntity<>(
                commentService.createComment(gameId,requestCreateDTO),
                HttpStatus.CREATED);
    }

    @PatchMapping("{gameId}/comments/{commentId}")
    public ResponseEntity<CommentDTO.ResponseUpdateDTO> updateGameComment(
            @PathVariable("gameId") long gameId,
            @PathVariable("commentId") long commentId,
            @RequestBody @Valid CommentDTO.RequestUpdateDTO requestUpdateDTO){
        return new ResponseEntity<>(
                commentService.updateComment(gameId,commentId,requestUpdateDTO),
                HttpStatus.OK
        );
    }

    @PostMapping("{gameId}/comments/{commentId}")
    public ResponseEntity<?> deleteGameComment(
            @PathVariable("gameId") long gameSeq,
            @PathVariable("commentId") long commentSeq,
            @RequestBody CommentDTO.RequestDeleteDTO requestDeleteDTO){
        commentService.deleteComment(commentSeq,requestDeleteDTO);
        return ResponseEntity.ok().build();
    }
}
