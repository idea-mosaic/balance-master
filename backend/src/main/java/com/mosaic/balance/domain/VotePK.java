package com.mosaic.balance.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class VotePK implements Serializable {
    @Column(name = "participant_seq")
    private long participantSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_seq")
    private Game game;
}