package apap.sipayroll.controller;

import apap.sipayroll.model.BonusModel;
import apap.sipayroll.model.GajiModel;
import apap.sipayroll.model.LemburModel;
import apap.sipayroll.model.RoleModel;
import apap.sipayroll.model.UserModel;
import apap.sipayroll.rest.BaseResponse;
import apap.sipayroll.service.*;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
    DetailGajiRestService detailGajiRestService;

    @Autowired
    UserRestService userRestService;


    @GetMapping("/gaji/{username}")
    public String detailGaji(@PathVariable String username,
                             Model model){
        UserModel user = userService.findByUsername(username);
        List<GajiModel> listGaji = gajiService.getListGaji();
        List<Long> jumlahLembur = new ArrayList<>();
        List<BonusModel> bonusnya = user.getGajiModel().getListBonus();
        Mono<BaseResponse> respon = detailGajiRestService.getPelatihan(user.getUsername());

        BaseResponse fix = respon.block();

        List<LinkedHashMap<String,String>> listPelatihan = (List<LinkedHashMap<String,String>>) fix.getResult();

        System.out.print(listPelatihan + "aduh");

        if(listPelatihan == null){
            Boolean notes = false;
        }else {
            Boolean notes = true;
        }

        Integer jumlahnya = 0;

        for (BonusModel b: bonusnya
             ) { jumlahnya += b.getJumlahBonus();

        }

        for(GajiModel i : listGaji){
            long jumlah = 0;
            List<LemburModel> lembur = i.getListLembur();
            for(LemburModel j : lembur){
                jumlah += j.getKompensasiPerJam()*(((j.getWaktuSelesai().getTime() - j.getWaktuMulai().getTime())/(1000 * 60 * 60))%24);
//                System.out.println(j.getWaktuSelesai().getTime() - j.getWaktuMulai().getTime());
            }
            jumlahLembur.add(jumlah);
        }

        UserModel penyetuju = userService.findByUuid(user.getGajiModel().getUserPenyetujuModel().getUuid());
        UserModel pengaju = userService.findByUuid(user.getGajiModel().getUserPengajuModel().getUuid());


        model.addAttribute("jumlahLembur", jumlahLembur);
        model.addAttribute("jumlahBonus", jumlahnya);
        model.addAttribute("user", user);
        model.addAttribute("pengaju", pengaju);
        model.addAttribute("penyetuju", penyetuju);
        return "detail-gaji";

    }


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

    @GetMapping("gaji/status/viewall")
    public String viewallgajiStatus(
            Model model){
        List<GajiModel> listCompareGaji = gajiService.getListGaji();
        List<GajiModel> listGaji = gajiService.getListGaji();
        List<Long> jumlahLembur = new ArrayList<>();
        UserModel userLogin = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        String userRoleLogin = userLogin.getUuid();
        System.out.println("ini 1");

        for(GajiModel i : listCompareGaji){
//            System.out.println(i.getId());
//            System.out.println(userLogin.getUuid());
//            System.out.println(i.getUserPenyetujuModel().getUuid());
            if(i.getStatusPersetujuan()!=0){
                listGaji.remove(i);
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
        return "view-all-gaji-status";
    }

    @GetMapping("gaji/viewall/karyawan-lama")
    public String viewallgajiKaryawanLama(
            Model model){
        List<Long> jumlahLembur = new ArrayList<>();
        List<UserModel> userSemua = userRestService.getKaryawanLama();
        List<GajiModel> listGaji = gajiService.getListGaji();

        for (UserModel a : userSemua
             ) {
            userRestService.setLamaBerkerjaAllKaryawan(a);
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
        model.addAttribute("userSemua", userSemua);
        model.addAttribute("jumlahLembur", jumlahLembur);
        return "view-all-karyawan-lama";
    }
    
}
