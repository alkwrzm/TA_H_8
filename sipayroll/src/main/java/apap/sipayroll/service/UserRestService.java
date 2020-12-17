package apap.sipayroll.service;

import apap.sipayroll.model.UserModel;

import java.util.List;

public interface UserRestService {

    List<UserModel> getKaryawanLama();
    List<UserModel> getAllKaryawan();
    void setLamaBerkerjaAllKaryawan(UserModel user);


}
