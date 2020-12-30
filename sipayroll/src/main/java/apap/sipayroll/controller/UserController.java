package apap.sipayroll.controller;


import apap.sipayroll.model.RoleModel;
import apap.sipayroll.model.UserModel;
import apap.sipayroll.rest.BaseResponse;
import apap.sipayroll.rest.UserDetail;
import apap.sipayroll.rest.UserResponse;
import apap.sipayroll.service.RoleService;
import apap.sipayroll.service.UserRestService;
import apap.sipayroll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRestService userRestService;

    @GetMapping(value = "/addUser")
    private String addUserSubmit(Model model){

        model.addAttribute("listRole", roleService.findAll());
        return "web-add-user";
    }
    @PostMapping(value = "/addUser")
    public String addPegawaiSubmit(
            @RequestParam("nama") String nama,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("idRole") Long idRole,
            @RequestParam("noTelepon") String noTelepon,
            @RequestParam("tempatLahir") String tempatLahir,
            @RequestParam("tanggalLahir") String tanggalLahir,
            @RequestParam("alamat") String alamat,
            Model model
    )  {

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

        userRestService.postPegawai(pegawai);

        String notif = "Pengguna dengan nama " + pegawai.getNama() + " berhasil ditambahkan!";
        model.addAttribute("notif", notif);

        return "redirect:/";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    private String userProfile(Authentication auth, Model model){

        String username = auth.getName();
        UserResponse respon = userRestService.getPegawai(username);
        UserModel userModel = userService.findByUsername(username);
        UserDetail user =respon.getResult();
        model.addAttribute("user", user);
        model.addAttribute("userModel", userModel);
        return "user";
    }
}
