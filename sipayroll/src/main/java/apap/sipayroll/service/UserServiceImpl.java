package apap.sipayroll.service;

import apap.sipayroll.model.UserModel;
import apap.sipayroll.respository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDb userDb;

    @Override
    public UserModel findByUsername(String username) {
        return userDb.findByUsername(username);
    }
}
