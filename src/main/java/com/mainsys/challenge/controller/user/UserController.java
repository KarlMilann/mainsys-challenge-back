package com.mainsys.challenge.controller.user;

import com.mainsys.challenge.model.Sales;
import com.mainsys.challenge.model.user.Role;
import com.mainsys.challenge.model.user.RoleName;
import com.mainsys.challenge.model.user.User;
import com.mainsys.challenge.repo.user.RoleRepository;
import com.mainsys.challenge.repo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Rest Controller to manage the users
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        System.out.println("Get all users...");

        List<User> users = new ArrayList<>();
        users.addAll(userRepository.findAll());
        return users;
    }
    @GetMapping("/users/{username}")
    public User getUserByName(@PathVariable("username") String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(
                        "User Not Found with -> username or email : " + username));

    }
    @GetMapping("/users/role/{roleName}")
    public List<User> getUsersByRoles(@PathVariable String roleName) {
        List<User> users = new ArrayList<>();
        Role role = roleRepository.findByName(RoleName.valueOf(roleName)).isPresent() ? roleRepository.findByName(RoleName.valueOf(roleName)).get() : null;
        users.addAll(userRepository.findByRoles(role));
        return users;
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id) {
        System.out.println("Delete user with ID = " + id + "...");

        userRepository.deleteById(id);

        return new ResponseEntity<>("User has been deleted!", HttpStatus.OK);
    }
    @PutMapping("/users/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        System.out.println("Update User with ID = " + id + "...");

        Optional<User> _userData = userRepository.findById(id);

        if (_userData.isPresent()) {
            User _user = _userData.get();
            _user.setPassword(user.getPassword());
            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
