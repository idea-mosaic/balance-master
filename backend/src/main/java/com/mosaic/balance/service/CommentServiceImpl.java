package com.mosaic.balance.service;

import com.mosaic.balance.domain.Comment;
import com.mosaic.balance.domain.Game;
import com.mosaic.balance.dto.CommentDTO;
import com.mosaic.balance.exception.CommentErrorResult;
import com.mosaic.balance.exception.CommentException;
import com.mosaic.balance.repository.CommentRepository;
import com.mosaic.balance.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService{
    private final GameRepository gameRepository;
    private final CommentRepository commentRepository;
    @Override
    public CommentDTO.ResponseCreateDTO createComment(long gameSeq, CommentDTO.RequestCreateDTO requestCreateDTO) {
        Game game = gameRepository.findById(gameSeq).orElseThrow(()->new CommentException(CommentErrorResult.GAME_NOT_EXIST));
        Comment newComment = Comment.builder()
                .pwd(requestCreateDTO.getPw())
                .color(requestCreateDTO.isChoice())
                .nickname(requestCreateDTO.getNickname())
                .content(requestCreateDTO.getContent())
                .game(game)
                .build();
        Comment comment = commentRepository.save(newComment);
        CommentDTO.ResponseCreateDTO responseCreateDTO = new CommentDTO.ResponseCreateDTO(comment);
        return responseCreateDTO;
    }

    @Override
    public CommentDTO.ResponseReadDTO getComments() {
        return null;
    }

    @Override
    public CommentDTO.ResponseUpdateDTO updateComment(long gameSeq, long commentSeq, CommentDTO.RequestUpdateDTO requestUpdateDTO) {
        Game game = gameRepository.findById(gameSeq).orElseThrow(()-> new CommentException(CommentErrorResult.GAME_NOT_EXIST));
        Comment comment = commentRepository.findById(commentSeq).orElseThrow(()->new CommentException(CommentErrorResult.COMMENT_NOT_EXIST));
        System.out.println(comment.getPwd());
        System.out.println(requestUpdateDTO.getPw());
        if(!comment.getPwd().equals(requestUpdateDTO.getPw())) {
            throw new CommentException(CommentErrorResult.PWD_INCORRECT);
        }
        comment.updateContent(requestUpdateDTO.getContent());
        comment = commentRepository.save(comment);
        return new CommentDTO.ResponseUpdateDTO(comment);

    }

    @Override
    public void deleteComment() {

    }
}
