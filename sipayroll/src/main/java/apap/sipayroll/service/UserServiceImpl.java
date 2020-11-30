package apap.sipayroll.service;

import apap.sipayroll.model.UserModel;
import apap.sipayroll.respository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.stereotype.Service;

@Service
=======
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
>>>>>>> 23fdff1d8154799db8f8b395d6a71abbfd36b1a4
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDb userDb;

    @Override
<<<<<<< HEAD
=======
    public UserModel addUser(UserModel user) {
        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        return userDb.save(user);
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }
    @Override
    public List<UserModel> getListUser(){
        return userDb.findAll();
    }

    @Override
>>>>>>> 23fdff1d8154799db8f8b395d6a71abbfd36b1a4
    public UserModel findByUsername(String username) {
        return userDb.findByUsername(username);
    }
}
