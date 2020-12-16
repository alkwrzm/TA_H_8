package apap.sipayroll.service;


import apap.sipayroll.model.UserModel;
import apap.sipayroll.respository.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserRestServiceImpl implements UserRestService{

    @Autowired
    private UserDb user;

    @Override
    public List<UserModel> getKaryawanLama() {
        List<UserModel> karyawan = getAllKaryawan();
        List<UserModel> result = new ArrayList<>();
        LocalDate curr = LocalDate.now();
        for (UserModel a : karyawan
        ) {
            if (a.getGajiModel() != null){
                Date tanggalMasuk = a.getGajiModel().getTanggalMasuk();
                LocalDate tanggalMasukFormat = tanggalMasuk.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Period period = Period.between(tanggalMasukFormat, curr);
                System.out.println(period.getYears());
                if (period.getYears() >= 2) result.add(a);
            }
        }
        return result;
    }

    @Override
    public List<UserModel> getAllKaryawan() {
        return user.findAll();
    }
}
