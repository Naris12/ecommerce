package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String newuser(Model model){

        List<Role> listrole = service.listrole();
        User user = new User();
        user.setEnable(true);
       model.addAttribute("user",user);
        model.addAttribute("listrole",listrole);
        model.addAttribute("pageTitle","Create new User");
       return "user_form";
    }

    @PostMapping("/users/save")
    public String saveuser (User user, RedirectAttributes redirectAttributes){
        System.out.println(user);
        service.save(user);
        redirectAttributes.addFlashAttribute("message","The user have been saved successfully");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String edituser(@PathVariable (name = "id")Integer id,RedirectAttributes redirectAttributes,Model model){
        try {
            User user=    service.get(id);
            List<Role> listrole = service.listrole();
            model.addAttribute("user",user);
            model.addAttribute("pageTitle","Edit User (ID : "+id+")");
            model.addAttribute("listrole",listrole);
            return "user_form";
        }catch (UserNotFoundException ex){
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
            return "redirect:/users";
        }

    }



}
