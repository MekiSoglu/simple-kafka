package com.example.user_service;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void createUser(@RequestBody UserDTO userDTO){
        userService.createUser(userDTO);
    }

    // 🔥 Trafik testi için endpoint
    @GetMapping("/simulate")
    public String simulateHighTraffic() {
        for (int i = 0; i < 10_000; i++) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(i);
            userDTO.setUserName("User_" + i);
            userDTO.setAddress("Address_" + i);
            userDTO.setEmail("user" + i + "@example.com");

            userService.createUser(userDTO);
        }
        return "10,000 mesaj gönderildi!";
    }
}
