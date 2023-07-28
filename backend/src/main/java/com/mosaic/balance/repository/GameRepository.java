package com.mosaic.balance.repository;

import com.mosaic.balance.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
