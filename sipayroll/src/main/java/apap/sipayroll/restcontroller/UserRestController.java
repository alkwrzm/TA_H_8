package apap.sipayroll.restcontroller;

import apap.sipayroll.model.RoleModel;
import apap.sipayroll.model.UserModel;
import apap.sipayroll.rest.BaseResponse;
import apap.sipayroll.rest.UserDetail;
import apap.sipayroll.service.RoleService;
import apap.sipayroll.service.UserRestService;
import apap.sipayroll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/v1")
public class UserRestController {
    @Autowired
    private UserRestService userRestService;
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;



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

    @PostMapping(value = "/user")
    private UserModel createUser(@Valid
                                     @RequestBody UserModel user,
                                 BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request Body has invalid type or missing field"
            );
        }
        else{
            return userRestService.createUser(user);
        }
    }
    @GetMapping(value = "/user/{uuid}")
    private UserModel retrieveUser(@PathVariable(value = "uuid") String uuid){
        try{
            return userRestService.findUserByUuid(uuid);
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User with Number "+ String.valueOf(uuid) + " not found!"
            );
        }
    }

    @GetMapping(value = "/user")
    private BaseResponse getPegawai(Authentication auth){
        String username = auth.getName();
        return userRestService.getPegawai(username);
    }

    @PostMapping(value = "/add/pegawaii")
    public BaseResponse addUserSubmit(
            @RequestBody UserDetail user){
        System.out.println(user.getIdRole());
        return userRestService.postPegawai(user);
    }


    @PostMapping(value = "/add/pegawai")
    public BaseResponse addUserSubmits(
            @RequestParam("nama") String nama,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("roleId") Long idRole,
            @RequestParam("noTelepon") String noTelepon,
            @RequestParam("tempatLahir") String tempatLahir,
            @RequestParam("tanggalLahir") String tanggalLahir,
            @RequestParam("alamat") String alamat,
            Model model
    ) {
//        if(userService.findByUsername(username) != null){
//            String notif = "Gunakan username yang lain";
//            model.addAttribute("notif", notif);
//
//
//        } else{
            UserModel user = new UserModel();
            RoleModel role = roleService.findById(idRole);
            user.setUsername(username);
            user.setRoleModel(role);
            user.setPassword(password);
            userService.addUser(user);
            UserDetail pegawai = new UserDetail();
            pegawai.setNama(nama);
            pegawai.setUsername(username);
            pegawai.setIdRole(idRole);
            pegawai.setNoTelepon(noTelepon);
            pegawai.setTanggalLahir(tanggalLahir);
            pegawai.setTempatLahir(tempatLahir);
            pegawai.setAlamat(alamat);

       // }
        return userRestService.postPegawai(pegawai);
    }


}

