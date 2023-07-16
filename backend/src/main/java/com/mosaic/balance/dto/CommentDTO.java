package com.mosaic.balance.dto;

import com.mosaic.balance.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class CommentDTO {
    public CommentDTO(Comment comment){
        this.commentSeq = comment.getCommentSeq();
        this.content = comment.getContent();
        this.createdDate = comment.getCreatedTime();
        this.nickname = comment.getNickname();
    }
    private long commentSeq;
    private String content;
    private LocalDateTime createdDate;
    private String nickname;

    @Getter
    @NoArgsConstructor
    public static class ResponseCreateDTO {
        public ResponseCreateDTO(Comment comment){
            this.commentSeq = comment.getCommentSeq();
            this.createdDate = comment.getCreatedTime();
        }
        private long commentSeq;
        private LocalDateTime createdDate;
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RequestCreateDTO{
        private boolean choice;
        private String content;
        private String pw;
        private String nickname;
    }

    @Getter
    @NoArgsConstructor
    public class ResponseReadDTO {
        public ResponseReadDTO(List<Comment> comments){
            comments.forEach(comment -> {
                if(comment.isColor()) this.redComments.add(new CommentDTO(comment));
                else this.blueComments.add(new CommentDTO(comment));
            });
        }
        List<CommentDTO> redComments;
        List<CommentDTO> blueComments;
    }
    @Getter
    public static class ResponseUpdateDTO {
        public ResponseUpdateDTO(Comment comment){
            this.commentSeq = comment.getCommentSeq();
            this.updatedTime = comment.getUpdatedTime();
        }
        private long commentSeq;
        private LocalDateTime updatedTime;
    }
    @Getter
    @Builder
    public static class RequestUpdateDTO{
        private String content;
        private String pw;
    }
}
