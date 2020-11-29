package apap.sipayroll.controller;

import apap.sipayroll.model.GajiModel;
import apap.sipayroll.model.UserModel;
import apap.sipayroll.service.GajiService;
import apap.sipayroll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class GajiController {

    @Qualifier("gajiServiceImpl")
    @Autowired
    GajiService gajiService;

    @Autowired
    UserService userService;

    @GetMapping("/gaji/add")
    public String addGajiFormPage(Model model){
        GajiModel gajiModel = new GajiModel();
        List<UserModel> userModelList = userService.getListUser();
        model.addAttribute("listUser", userModelList);
        model.addAttribute("gaji", gajiModel);
        return "form-add-gaji";
    }

    @PostMapping("/gaji/add")
    public String addGajiSubmit(
            @ModelAttribute GajiModel gajiModel,
            Model model){
        UserModel userPengaju = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        gajiModel.setUserPengajuModel(userPengaju);
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        gajiModel.setStatusPersetujuan(0);
        gajiService.addGaji(gajiModel);
        Long gaji = gajiModel.getId();
        model.addAttribute("gaji", gaji);
        return "add-gaji";
    }
}
