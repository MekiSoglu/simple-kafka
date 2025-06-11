package com.example.user_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducer {
    private static final Logger logger =  LoggerFactory.getLogger(KafkaProducer.class);

    private final KafkaTemplate<String,UserDTO> kafkaTemplate;

    private static final String USER_TOPIC = "user_events";

    public KafkaProducer(KafkaTemplate<String, UserDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUser(UserDTO userDTO){
        int userDTOId = userDTO.getId();
        String key = String.valueOf(userDTOId);
        logger.info(String.format("User event gönderiliyor  Key: %s, User: %s\"", key,userDTO.toString()));
        CompletableFuture<SendResult<String , UserDTO>> future = kafkaTemplate.send(USER_TOPIC,key,userDTO);
        future.whenComplete((result , ex)->{
            if(ex==null){
                logger.info(String.format("User event basariyla gonderildi" ,key, result.getRecordMetadata().offset(),result.getRecordMetadata().partition()));
            }else{
                logger.info(String.format("User event gonderilirken hata alindi", key, ex.getMessage(), ex));
            }
        });

    }
}
