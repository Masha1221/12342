package com.example.battle_bot.repositories;

import com.example.battle_bot.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByChatId(long chatId);
}
