package com.mosaic.balance.repository;

import com.mosaic.balance.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
