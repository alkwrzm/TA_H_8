package apap.sipayroll.service;

import apap.sipayroll.model.UserModel;

import java.util.List;


public interface UserService {
    UserModel addUser(UserModel user);
    public String encrypt (String password);
    UserModel findByUsername(String username);
    List<UserModel> getListUser();
    UserModel findByUuid(String id);
}
