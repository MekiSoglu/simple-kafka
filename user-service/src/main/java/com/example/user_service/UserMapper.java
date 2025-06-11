package com.example.user_service;

public class UserMapper {

    public static User toEntity(UserDTO userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setUserName(userDTO.getUserName());
        user.setAddress(userDTO.getAddress());
        user.setEmail(userDTO.getEmail());
        return user;
    }


}
