package apap.sipayroll.service;

import apap.sipayroll.model.UserModel;
import apap.sipayroll.rest.BaseResponse;
import apap.sipayroll.rest.UserDetail;
import reactor.core.publisher.Mono;

public interface UserRestService {
    UserModel createUser(UserModel userModel);

    UserModel findUserByUsername(String username);

    UserModel findUserByUuid(String uuid);

    BaseResponse getPegawai(String uuid);

    BaseResponse postPegawai(UserDetail pegawai);
    //Mono<ResepDetail> postStatus();
}
