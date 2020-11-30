package apap.sipayroll.service;

import apap.sipayroll.model.UserModel;

<<<<<<< HEAD
public interface UserService {
    UserModel findByUsername(String username);

=======
import java.util.List;

public interface UserService {
    UserModel addUser(UserModel user);
    public String encrypt (String password);
    UserModel findByUsername(String username);
    List<UserModel> getListUser();
>>>>>>> 23fdff1d8154799db8f8b395d6a71abbfd36b1a4
}
