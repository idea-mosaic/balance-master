package com.mosaic.balance.domain;

import com.mosaic.balance.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "vote")
public class Vote extends BaseTimeEntity {
    @EmbeddedId
    private VotePK votePK;
    @Column(name = "color")
    private boolean color;
}