package com.mosaic.balance.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

public class GameDTO {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GameCreateDTO {
        @NotEmpty
        private String title;
        @NotEmpty
        private String red;
        @NotEmpty
        private String blue;
        private String redDescription;
        private String blueDescription;
        @JsonIgnoreProperties
        private MultipartFile redImg;
        @JsonIgnoreProperties
        private MultipartFile blueImg;
        private String pw;

        public void appendImage(MultipartFile redImg, MultipartFile blueImg) {
            this.redImg = redImg;
            this.blueImg = blueImg;
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class GameCreatedDTO {
        private long gameId;
        private LocalDateTime createdDate;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class GameModifiedDTO {
        private long gameId;
        private String redImg;
        private String blueImg;
        private LocalDateTime createdDate;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class GameThumbnailDTO {
        private long gameId;
        private String title;
        private String redImg;
        private String blueImg;
        private LocalDateTime createdDate;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class GameDetailDTO {
        private long gameId;
        private String title;
        private String red;
        private String blue;
        private String redDescription;
        private String blueDescription;
        private String redImg;
        private String blueImg;
//        private CommentDTO.ResponseReadDTO commentDetails;
        private LocalDateTime createdDate;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class GameResultDTO {
        private float redScore;
        private float blueScore;
        private CommentDTO.ResponseReadDTO commentDetails;
//        private LinkedList<> redComments;
//        private LinkedList<> blueComments;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class GameDetailResponseDTO {
        private GameDetailDTO game;
        private GameResultDTO result;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class GameListDTO {
        private List<GameThumbnailDTO> hot;
        private List<GameThumbnailDTO> sexy;
        private List<GameThumbnailDTO> cool;
    }
}
