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

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;


@Service
@Transactional
public class UserRestServiceImpl implements UserRestService{
    private final WebClient webClient;
    
    @Autowired
    private UserDb user;

    public UserRestServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(Setting.userUrl).build(); }



    @Override
    public UserModel createUser(UserModel userModel) {
        return user.save(userModel);
    }
    @Override
    public UserModel findUserByUsername(String username) {
        return user.findByUsername(username);
    }

    @Override
    public UserModel findUserByUuid(String uuid) {
        return user.findByUuid(uuid);
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

    @Override
    public List<UserModel> getKaryawanLama() {
        List<UserModel> karyawan = getAllKaryawan();
        List<UserModel> result = new ArrayList<>();
        LocalDate curr = LocalDate.now();

        for (UserModel a : karyawan) {
            if (a.getGajiModel() != null){
                Date tanggalMasuk = a.getGajiModel().getTanggalMasuk();
                LocalDate tanggalMasukFormat = tanggalMasuk.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Period period = Period.between(tanggalMasukFormat, curr);

                System.out.println(period.getYears() + "disini");
                System.out.println();
                if (period.getYears() >= 2) {
                    result.add(a);}
            }
        }
        return result;
    }

    @Override
    public List<UserModel> getAllKaryawan() {
        return user.findAll();
    }

    @Override
    public void setLamaBerkerjaAllKaryawan(UserModel user) {
        LocalDate curr = LocalDate.now();
        Date tanggalMasuk = user.getGajiModel().getTanggalMasuk();
        LocalDate tanggalMasukFormat = tanggalMasuk.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period period = Period.between(tanggalMasukFormat, curr);
        Integer periode = (period.getYears());
        user.setLamaBerkerja(periode);
        System.out.print("masukga" + periode);
    }
}