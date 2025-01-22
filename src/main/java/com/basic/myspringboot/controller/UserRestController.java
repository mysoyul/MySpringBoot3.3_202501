package com.basic.myspringboot.controller;

import com.basic.myspringboot.entity.User;
import com.basic.myspringboot.exception.BusinessException;
import com.basic.myspringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {
    private final UserRepository userRepository;

    //Constructor Injection
//    public UserRestController(UserRepository userRepository) {
//        System.out.println("UserRepository 구현객체명 =" + userRepository.getClass().getName());
//        this.userRepository = userRepository;
//    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long userId){
        return getUserNotFound(userId);
    }

    private User getUserNotFound(Long userId) {
        return userRepository.findById(userId) //Optional<User>
                //orElseThrow(Supplier) Supplier 의 추상메서드 T get()
                .orElseThrow(() -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{email}/")
    public User updateUserByEmail(@PathVariable String email, @RequestBody User userDetail){
        User existUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));
        existUser.setName(userDetail.getName());
        return userRepository.save(existUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        User user = getUserNotFound(id);
        userRepository.delete(user);
        return ResponseEntity.ok("Id = " + id + " User Deleted OK!");
    }

}
