package com.mnyshenko.taxiSpringApp.contoller;

import com.mnyshenko.taxiSpringApp.model.User;
import com.mnyshenko.taxiSpringApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all-users")
    public String getUsers(Model model) {

        userService.save(new User("name1"));
        userService.save(new User("name2"));
        userService.save(new User("name3"));
        userService.save(new User("name4"));
        userService.save(new User("name5"));
        userService.save(new User("name6"));

        model.addAttribute("users", userService.getUsers());


        return "users";
    }


}
