package apap.sipayroll.service;

import apap.sipayroll.model.UserModel;
import apap.sipayroll.rest.BaseResponse;
import apap.sipayroll.rest.UserDetail;
import apap.sipayroll.rest.UserResponse;
import reactor.core.publisher.Mono;
import java.util.List;

public interface UserRestService {
    UserModel createUser(UserModel userModel);

    UserModel findUserByUsername(String username);

    UserModel findUserByUuid(String uuid);

    UserResponse getPegawai(String uuid);

    UserResponse postPegawai(UserDetail pegawai);
    //Mono<ResepDetail> postStatus();
    List<UserModel> getKaryawanLama();
    List<UserModel> getAllKaryawan();
    void setLamaBerkerjaAllKaryawan(UserModel user);

}
