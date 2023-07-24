package com.mosaic.balance.controller;

import com.mosaic.balance.dto.CommentDTO;
import com.mosaic.balance.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("games")
public class GameCommentController {

    final private CommentService commentService;

    @PostMapping("{gameId}/comments")
    public ResponseEntity<CommentDTO.ResponseCreateDTO> addGameComment(
            @PathVariable("gameId") long gameId,
            @RequestBody @Valid CommentDTO.RequestCreateDTO requestCreateDTO) {

        return new ResponseEntity<>(
                commentService.createComment(gameId,requestCreateDTO),
                HttpStatus.CREATED);
    }

    @GetMapping("{gameId}/comments")
    public ResponseEntity<CommentDTO.ResponseReadDTO> getGameComments(
            @PathVariable("gameId") long gameId,
            @RequestBody @Valid CommentDTO.RequestReadDTO requestReadDTO){
        return new ResponseEntity<>(
                commentService.getComments(gameId, requestReadDTO),
                HttpStatus.OK);
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
