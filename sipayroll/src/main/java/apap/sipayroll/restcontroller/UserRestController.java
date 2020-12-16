package apap.sipayroll.restcontroller;


import apap.sipayroll.model.UserModel;
import apap.sipayroll.service.UserRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserRestController {

    @Autowired
    private UserRestService userRestService;


    @GetMapping(value = "/karyawan-lama")
    private List<UserModel> getKaryawanLama(){
       return userRestService.getKaryawanLama();
    }





}
