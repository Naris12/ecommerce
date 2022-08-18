package com.shopme.admin.user;

import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/users")
    public String listall(Model model){
        List<User> listalluser = service.listalluser();
            model.addAttribute("listalluser",listalluser);
        return "users";
    }

    @GetMapping("/users/new")
    public String newUser(Model model){
       User user = new User();
       model.addAttribute("user",new User());
       return "user_form";
    }

    @PostMapping("/users/save")
    public String saveuser (User user){
        System.out.println(user);
        return "redirect:/users";
    }

}