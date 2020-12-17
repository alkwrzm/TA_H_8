package apap.sipayroll.restcontroller;


import apap.sipayroll.model.UserModel;
import apap.sipayroll.service.UserRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1")
public class UserRestController {

    @Autowired
    private UserRestService userRestService;


    @GetMapping(value = "/karyawan-lama")
    private List<UserModel> getKaryawanLama(){

        try {
            return userRestService.getKaryawanLama();

        }catch (NoSuchElementException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Tidak ada Karyawan Lama"
            );
        }
    }





}
