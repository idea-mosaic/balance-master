package com.mosaic.balance.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.LinkedList;

public class GameDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GameCreateDTO {
        private String title;
        private String red;
        private String blue;
        private String redDescription;
        private String blueDescription;
        private MultipartFile redImg;
        private MultipartFile blueImg;
        private String pw;
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
        private GameResultDTO result;
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
}
