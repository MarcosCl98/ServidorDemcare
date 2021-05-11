package com.demcare.demo.service;

import com.demcare.demo.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    User findByMail(String mail);
    void deleteUser(Long id);
    void suspendUser(Long id);
    void activateUser(Long id);
    void asociateUser(Long idInstitution, Long id);
    User register(User userEntity);
    List<User> getUsers();
    List<User> getUsersList();
    List<User> getPosibleAsociatedUsers();
    List<User> getAsociatedUsers();
    List<User> getNotAsociatedUsers();

    User save(User user);

    void saveImage(MultipartFile imageFile) throws IOException;
}
