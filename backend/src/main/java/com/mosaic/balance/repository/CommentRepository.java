package com.mosaic.balance.repository;

import com.mosaic.balance.domain.Comment;
import com.mosaic.balance.domain.Game;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByGameAndColor(@Param("game") Game game, @Param("color") boolean color, Pageable pageable);
}