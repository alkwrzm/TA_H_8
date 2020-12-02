package apap.sipayroll.controller;

import apap.sipayroll.model.GajiModel;
import apap.sipayroll.model.LemburModel;
import apap.sipayroll.model.UserModel;
import apap.sipayroll.service.GajiService;
import apap.sipayroll.service.LemburService;
import apap.sipayroll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/lembur")
public class LemburController {
    @Autowired
    UserService userService;

    @Autowired
    LemburService lemburService;

    @Autowired
    GajiService gajiService;

    @GetMapping("/add")
    private String addLemburForm(Model model){
        model.addAttribute("lembur", new LemburModel());
        return "form-add-lembur";
    }

    @PostMapping("/add")
    private String addLemburSubmit(@ModelAttribute LemburModel lembur, Model model){
        UserModel user = userFinder();
        String userId = user.getUuid();
        //Cek apakah id ada di database gaji
        if(gajiHasId(userId, lembur)){
            model.addAttribute("msg", "Lembur berhasil ditambahkan!");
            lemburService.addLembur(lembur);
        }else{
            model.addAttribute("msg", "Lembur gagal ditambahkan!");
        }
        return "lembur-notif";
    }

    private boolean gajiHasId(String userId, LemburModel lembur){
        List<GajiModel> gajis = gajiService.getListGaji();
        boolean test = false;
        for(GajiModel gaji:gajis){
            if(gaji.getUserModel().getUuid().equals(userId)){
                lembur.setGajiModel(gaji);
                test = true;
            }
        }
        return test;
    }

    private UserModel userFinder(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByUsername(username);
    }
}
