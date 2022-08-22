package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import com.shopme.admin.security.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
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
        boolean isUpdatingUser =(user.getId()!=null);
        if(isUpdatingUser){

            User existingUser = userRepository.findById(user.getId()).get();

            if(user.getPassword().isEmpty()){
                user.setPassword(existingUser.getPassword());
            }else {
                encodePassword(user);
            }
        }else {

            encodePassword(user);
        }
      userRepository.save(user);
    }

    private void encodePassword(User user){
        String encodedPassword= passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public boolean Isemailunique(Integer id,String email){
        User userByEmail = userRepository.getUserByEmail(email);
        if(userByEmail == null)  return true;
        boolean isCreatedNew=  (id==null);
        if(isCreatedNew) {
            if(userByEmail!=null) return false;
        }  else{
            if(userByEmail.getId()!=id){
                return false;
            }
        }

        return true;
    }

    public User get(Integer id) throws UserNotFoundException {

        try{

            return userRepository.findById(id).get();
        }catch (NoSuchElementException ex){
            throw new UserNotFoundException("Could not find anyuser with id "+id);
        }

    }

    public void Delete(int id) throws UserNotFoundException {
        Long countById= userRepository.countById(id);

        if(countById==null||countById==0){
            throw new UserNotFoundException("Could not find anyuser with id "+id);
        }

        userRepository.deleteById(id);
    }

    public void updateenable(int id,boolean eneble){
        userRepository.updateEnabledStatus(id,eneble);
    }
}
