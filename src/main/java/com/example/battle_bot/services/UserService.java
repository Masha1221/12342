package com.example.battle_bot.services;

import com.example.battle_bot.models.UserEntity;
import com.example.battle_bot.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveNotExistingUser(long chatId, Message message) {
        Optional<UserEntity> user = userRepository.findByChatId(chatId);
        if (!user.isPresent()) {
            UserEntity userEntity = new UserEntity();
            userEntity.setName(message.getFrom().getUserName());
            userEntity.setChatId(message.getChatId());
            userRepository.save(userEntity);
        }
    }


    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity findByChatId(long chatId) {
        return userRepository.findByChatId(chatId).orElseThrow();
    }

    public void save(UserEntity entity) {
        userRepository.save(entity);
    }

    public List<String> getNamesOfUsers(){
        return getAllUsers().stream()
                .map(UserEntity::getName)
                .collect(Collectors.toList());

    }
}
