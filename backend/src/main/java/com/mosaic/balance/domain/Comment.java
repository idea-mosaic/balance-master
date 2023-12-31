package com.mosaic.balance.domain;

import com.mosaic.balance.util.BaseTimeEntity;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_seq")
    private long commentSeq;

    @Column(name="pwd")
    @NotNull
    private String pwd;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_seq")
    private Game game;

    @Column(name = "color")
    private boolean color;

    public void updateContent(String content){
        this.content = content;
    }
}