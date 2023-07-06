package com.mosaic.balance.domain;

import com.mosaic.balance.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "game")
public class Game extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_seq")
    private long gameSeq;
    @Column(name = "red")
    private String red;
    @Column(name="blue")
    private String blue;
    @Column(name="red_description")
    private String redDescription;
    @Column(name="blue_description")
    private String blueDescription;
    @Column(name="red_img")
    private String redImg;
    @Column(name="blue_img")
    private String blueImg;
    @Column(name="red_cnt")
    private int redCnt;
    @Column(name="blue_cnt")
    private int blueCnt;
    @Column(name="participant_cnt")
    private int participantCnt;
    @Column(name="password")
    private String password;
    @OneToMany(mappedBy = "game",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments ;

}