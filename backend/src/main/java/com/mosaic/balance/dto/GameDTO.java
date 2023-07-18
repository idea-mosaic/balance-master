package com.mosaic.balance.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class GameDTO {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GameCreateDTO {
        private String title;
        private String red;
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
        private String img;
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
        private LocalDateTime createdDate;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class GameResultDTO {
        private float redScore;
        private float blueScore;
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
}
