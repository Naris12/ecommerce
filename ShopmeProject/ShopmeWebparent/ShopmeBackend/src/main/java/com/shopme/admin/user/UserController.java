package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    public String saveuser (User user, RedirectAttributes redirectAttributes, @RequestParam("image")MultipartFile multipartFile){
        System.out.println(user);
        System.out.println(multipartFile.getOriginalFilename());
        //service.save(user);
        //redirectAttributes.addFlashAttribute("message","The user have been saved successfully");
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

    @GetMapping("/users/delete/{id}")
    public String deleteuser(@PathVariable (name = "id")Integer id,RedirectAttributes redirectAttributes){
        try{
            service.Delete(id);
            redirectAttributes.addFlashAttribute("message","The user id "+id+" has been deleted");

        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message",e.getMessage());

        }
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/enabled/{status}")
    public String updateUserEnabledStatus(@PathVariable("id")int id,@PathVariable("status") boolean enable,RedirectAttributes redirectAttributes){
        service.updateenable(id,enable);
        String status=enable ? "enabled" : "disabled";
        String  message="the user ID "+id+" has been "+status;
        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/users";
    }

}
