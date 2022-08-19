package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserReporitoryTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUserWithOneRole(){
        Role RoleAdmin = entityManager.find(Role.class,1);
        User username= new User("naris@email.com","12345","naris","bin");
        username.addRole(RoleAdmin);
        User save = repository.save(username);
        assertThat(save.getId()).isGreaterThan(0);
    }


    @Test
    public void testCreateUserWithTwoRole() {
        User usertest=new User("test@email","12345","te","st");
        Role roleEditor=new Role(3);
        Role roleAssistant= new Role(5);

        usertest.addRole(roleEditor);
        usertest.addRole(roleAssistant);

        User save = repository.save(usertest);
        assertThat(save.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetAlluser(){
        Iterable<User> list = repository.findAll();
        list.forEach(user -> System.out.println(user));

    }

    @Test
    public void testgetuserbyid(){
        User user = repository.findById(1).get();
        System.out.println(user);
        assertThat(user).isNotNull();
    }

    @Test
    public void testUpdateUserDetails(){
        User user =  repository.findById(1).get();
        user.setEnable(true);
        user.setEmail("testsetemail@email.com");

        repository.save(user);
    }

    @Test
    public  void  testupdateuserrole(){
         User user=repository.findById(2).get();
         Role roleEditor = new Role(3);
         Role saleperson = new Role(2);
         user.getRoles().remove(roleEditor);
         user.addRole(saleperson);
         repository.save(user);
    }

    @Test
    public void testDeletebyid(){
        int userid=2;
        repository.deleteById(2);

    }

    @Test
    public void testGetUserByemail(){
        String email="test@email";
        User user = repository.getUserByEmail(email);
        assertThat(user).isNotNull();
    }

}
