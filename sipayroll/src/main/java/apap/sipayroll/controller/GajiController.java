package apap.sipayroll.controller;

import apap.sipayroll.model.*;
import apap.sipayroll.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class GajiController {

    @Qualifier("gajiServiceImpl")
    @Autowired
    GajiService gajiService;

    @Autowired
    UserService userService;

    @Autowired
    LemburService lemburService;

    @Autowired
    RoleRestService roleRestService;

    @Autowired
    JenisBonusRestService jenisBonusRestService;

    @Autowired
    BonusRestService bonusRestService;

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
        userPengaju.setGajiModel(gajiModel);
        gajiService.addGaji(gajiModel);
        Long gaji = gajiModel.getId();
        model.addAttribute("gaji", gaji);
        return "add-gaji";
    }

    @GetMapping("/gaji/update/{id}")
    public String formUpdateGaji(
            @PathVariable Long id,
            Model model){
        GajiModel gaji = gajiService.getGajiById(id);
        UserModel userPengaju = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if(userPengaju.equals(gaji.getUserModel())){
            return "error-update-gaji";
        }else{
            model.addAttribute("gaji", gaji);
            return "form-update-gaji";
        }
    }

    @PostMapping("/gaji/update")
    public String updateGajiSubmit(
            @ModelAttribute GajiModel gaji,
            Model model){
        UserModel userPengaju = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        gaji.setUserPengajuModel(userPengaju);
        gaji.setStatusPersetujuan(0);
        gajiService.addGaji(gaji);
        Long idGaji = gaji.getId();
        model.addAttribute("gaji", idGaji);
        return "update-gaji";
    }
    @GetMapping("gaji/delete/{id}")
    public String deleteGaji(
            @PathVariable Long id,
            Model model){
        GajiModel gaji = gajiService.getGajiById(id);
        Long idGaji = gaji.getId();
        List<LemburModel> lembur = lemburService.findByIdGaji(id);
        if(lembur.size()>0){
            for(LemburModel l:lembur){
                Long idLembur = l.getId();
                lemburService.deleteById(idLembur);
            }
        }
        model.addAttribute("gaji", idGaji);
        gajiService.deleteGaji(gaji);
        return "delete-gaji";
        // Jangan lupa delete bonus dan lembur
    }
    @GetMapping("gaji/viewall")
    public String viewallgaji(
            Model model){
        List<GajiModel> listCompareGaji = gajiService.getListGaji();
        List<GajiModel> listGaji = gajiService.getListGaji();
        List<Long> jumlahLembur = new ArrayList<>();
        UserModel userLogin = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        RoleModel userRoleLogin = userLogin.getRoleModel();
        if(!userRoleLogin.getNamaRole().equals("Kepala Departemen HR") && !userRoleLogin.getNamaRole().equals("Staff Payroll")){
            for(GajiModel i : listCompareGaji){
                if(i.getUserModel().getUuid()!=userLogin.getUuid()){
                    listGaji.remove(i);
                }
            }
        }
        for(GajiModel i : listGaji){
            long jumlah = 0;
            List<LemburModel> lembur = i.getListLembur();
            for(LemburModel j : lembur){
                jumlah += j.getKompensasiPerJam()*(((j.getWaktuSelesai().getTime() - j.getWaktuMulai().getTime())/(1000 * 60 * 60))%24);
                System.out.println(j.getWaktuSelesai().getTime() - j.getWaktuMulai().getTime());
            }
            jumlahLembur.add(jumlah);
        }
        System.out.print(jumlahLembur);
        model.addAttribute("listGaji", listGaji);
        model.addAttribute("jumlahLembur", jumlahLembur);
        return "view-all-gaji";
    }

    //Get Mapping Bonus
    @GetMapping("/bonus")
    private String addBonusForm(@ModelAttribute BonusModel bonus, Model model){
        //UUID Karyawan(Dropdown)
        RoleModel karyawanRole = roleRestService.findByNamaRole("Karyawan");
        List<UserModel> karyawanList = karyawanRole.getListUser();
        HashMap<String, Long> karyawanGajiId = new HashMap<String,Long>();
        for(UserModel karyawan : karyawanList){
            System.out.println(karyawan.getGajiModel());
            karyawanGajiId.put(karyawan.getUuid(), karyawan.getGajiModel().getId());
        }
        model.addAttribute("karyawanGajiId", karyawanList);
        //Jenis Bonus
        List<JenisBonusModel> jenisBonus = jenisBonusRestService.findAll();
        model.addAttribute("jenisBonus", jenisBonus);
        model.addAttribute("bonus", new BonusModel());
        return "form-add-bonus";
    }

    @PostMapping("/bonus")
    private String addBonusSubmit(
            @RequestParam(name = "karyawanGajiId") Long gajiId,
            @RequestParam(name = "jenisBonus") String jenisBonus,
            @ModelAttribute BonusModel bonus,
            Model model){
        //Cari gaji pokok Karyawan
        GajiModel gaji = gajiService.getGajiById(gajiId);
        Integer gapokKaryawan = gaji.getGajiPokok();
        //Cari Id Jenis Bonus. Apply validator
        boolean validity = jenisBonusRestService.validatorAndSetter(Long.parseLong(jenisBonus), bonus, gapokKaryawan);
        if(validity){
            model.addAttribute("msg", "Bonus berhasil ditambahkan!");
            bonus.setGajiModel(gaji);
            bonusRestService.save(bonus);
        }else{
            model.addAttribute("msg", "Bonus gagal ditambahkan!");
        }
        return "bonus-notif";
    }
    
}
