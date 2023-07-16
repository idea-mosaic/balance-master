package com.mosaic.balance.repository;

import com.mosaic.balance.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    public boolean existsByVotePKGameGameSeqAndVotePKParticipantSeq(long gameSeq, long participantSeq);
}
