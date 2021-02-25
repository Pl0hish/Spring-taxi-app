package com.mnyshenko.taxiSpringApp.contoller;

import com.mnyshenko.taxiSpringApp.dto.UserDTO;
import com.mnyshenko.taxiSpringApp.exception.UserException;
import com.mnyshenko.taxiSpringApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/registration")
    public String showRegistrationPage(@ModelAttribute("user") UserDTO userDTO, Model model) {
        model.addAttribute("exception", "");
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(
            @ModelAttribute("user") @Valid UserDTO userDTO,
            BindingResult bindingResult,
            Model model) {

        model.addAttribute("exception", "");

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        try {
            userService.save(userDTO);
        } catch (UserException e) {
            model.addAttribute("exception", userDTO.getEmail());
            return "registration";
        }

        return "redirect:/login";
    }
}
