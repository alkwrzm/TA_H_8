package apap.sipayroll.service;

import apap.sipayroll.model.UserModel;
import apap.sipayroll.respository.UserDb;
import apap.sipayroll.rest.BaseResponse;
import apap.sipayroll.rest.Setting;
import apap.sipayroll.rest.UserDetail;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class UserRestServiceImpl implements UserRestService{
    private final WebClient webClient;
    @Autowired
    private UserDb userDb;

    public UserRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(Setting.userUrl).build(); }



    @Override
    public UserModel createUser(UserModel userModel) {
        return userDb.save(userModel);
    }
    @Override
    public UserModel findUserByUsername(String username) {
        return userDb.findByUsername(username);
    }

    @Override
    public UserModel findUserByUuid(String uuid) {
        return userDb.findByUuid(uuid);
    }

    @Override
    public BaseResponse getPegawai(String username) {
        return this.webClient.get().uri(uriBuilder -> uriBuilder.path("/api/v1/pegawai/{username}" )
                .build(username)).
                retrieve().
                bodyToMono(BaseResponse.class).
                block();
    }

    @Override
    public BaseResponse postPegawai(UserDetail pegawai) {
        {
            return this.webClient
                    .post()
                    .uri("/api/v1/pegawai")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(pegawai)
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();
        }
    }
}
