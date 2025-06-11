package com.example.user_service;

import org.springframework.stereotype.Service;

@Service

public class UserService {

    private final UserRepository userRepository;

    private final KafkaProducer kafkaProducer;

    public UserService(final UserRepository userRepository, final KafkaProducer kafkaProducer) {this.userRepository = userRepository;
        this.kafkaProducer = kafkaProducer;
    }

    public void createUser(UserDTO userDTO){
        User user = UserMapper.toEntity(userDTO);
        userRepository.save(user);
        kafkaProducer.sendUser(userDTO);
    }

}
