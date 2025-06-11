package com.example.addres_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service

public class AddressService {
    private static Logger loggerFactory = LoggerFactory.getLogger(AddressService.class);
    private final AddressRepository addressRepository;

    public AddressService(final AddressRepository addressRepository) {this.addressRepository = addressRepository;}

    @KafkaListener(topics = "user_events", groupId = "address_group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(UserDTO userDTO) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("event başarıyla alındı: " + userDTO);
        Address address = new Address();
        address.setUserName(userDTO.getUserName());
        address.setAddress(userDTO.getAddress());
        address.setUserId(userDTO.getId());
        addressRepository.save(address);
        if(addressRepository.existsByUserId(userDTO.getId())){
            loggerFactory.info("addres basarıyla kaydedıldı");
        }else{
            loggerFactory.info("addres kayıt edilemedi");
        }
    }


}
