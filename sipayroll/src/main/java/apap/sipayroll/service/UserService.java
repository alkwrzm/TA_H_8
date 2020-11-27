package apap.sipayroll.service;

import apap.sipayroll.model.UserModel;

import java.util.List;

public interface UserService {
    UserModel findByUsername(String username);
    List<UserModel> getListUser();
}
