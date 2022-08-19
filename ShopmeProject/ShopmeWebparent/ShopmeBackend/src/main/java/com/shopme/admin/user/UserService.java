package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import com.shopme.admin.security.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List <User> listalluser(){
     return    (List<User>) userRepository.findAll();
    }

    public List<Role> listrole(){
        return (List<Role>)repository.findAll();
    }

    public void save(User user) {
        encodePassword(user);
      userRepository.save(user);
    }

    private void encodePassword(User user){
        String encodedPassword= passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public boolean Isemailunique(String email){
        User userByEmail = userRepository.getUserByEmail(email);
        return userByEmail==null;
    }
}
