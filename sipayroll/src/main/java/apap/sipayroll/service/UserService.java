package apap.sipayroll.service;

import apap.sipayroll.model.UserModel;

public interface UserService {
    UserModel findByUsername(String username);

}
