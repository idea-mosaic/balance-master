package com.mosaic.balance.dto;

import com.mosaic.balance.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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
        @NotEmpty
        private String pw;
        private String nickname;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestReadDTO{
        private int redPageNo;
        private int redPageSize;
        private int bluePageNo;
        private int bluePageSize;
    }
    @Getter
    @NoArgsConstructor
    public static class ResponseReadDTO {
        public ResponseReadDTO(List<Comment> commentsBlue, List<Comment> commentsRed){
            this.redComments = commentsRed.stream()
                    .map(comment -> new CommentDTO(comment))
                    .collect(Collectors.toList());
            this.blueComments = commentsBlue.stream()
                    .map(comment -> new CommentDTO(comment))
                    .collect(Collectors.toList());
        }
        List<CommentDTO> redComments;
        List<CommentDTO> blueComments;
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseUpdateDTO {
        public ResponseUpdateDTO(Comment comment){
            this.commentSeq = comment.getCommentSeq();
            this.updatedTime = comment.getUpdatedTime();
        }
        private long commentSeq;
        private LocalDateTime updatedTime;
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestUpdateDTO{
        private String content;
        private String pw;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestDeleteDTO{
        @NotEmpty
        private String pw;
    }

}
