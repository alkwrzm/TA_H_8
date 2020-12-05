package apap.sipayroll.controller;

import apap.sipayroll.model.GajiModel;
import apap.sipayroll.model.LemburModel;
import apap.sipayroll.model.RoleModel;
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
import java.util.Calendar;
import java.util.Date;
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
    private String addLemburSubmit(
                                   @RequestParam("waktuMulai") String waktuMulai,
                                   @RequestParam("waktuSelesai")String waktuSelesai,
                                   @ModelAttribute LemburModel lembur,
                                   Model model){
        UserModel user = userFinder();
        String userId = user.getUuid();
        //Cek apakah id ada di database gaji
        if(gajiHasId(userId, lembur)){
            model.addAttribute("msg", "Lembur berhasil ditambahkan!");
        }else{
            model.addAttribute("msg", "Lembur gagal ditambahkan!");
        }if(lemburService.dateValidator(waktuMulai,waktuSelesai)){
            model.addAttribute("msg", "Lembur berhasil ditambahkan!");
            lemburService.addLembur(lembur);
        }else{
            model.addAttribute("msg", "Lembur gagal ditambahkan (tanggal harus sama!)");}

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

    //if Kepala Departemen HR dan Staff Payroll, status persetujuan
    //if karyawan, waktu mulai dan waktu selesai
    @GetMapping({"/change/{idLembur}", "/change"})
    private String changeLemburForm(
            @PathVariable(required = false) Long idLembur,
            Model model
    ) {
        LemburModel lembur = lemburService.findById(idLembur).get();
        if(lembur.getStatusPersetujuan()==2){
            model.addAttribute("msg", "Lembur telah disetujui. Data tidak boleh diubah.");
            return "lembur-notif";
        } else{
            UserModel user = userFinder();
            String role = user.getRoleModel().getNamaRole();
            model.addAttribute("lembur", lembur);
            if((role.equals("Kepala Departemen HR") || (role.equals("Staff Payroll")))){
                model.addAttribute("waktuMulai", lembur.getWaktuMulai());
                model.addAttribute("waktuSelesai", lembur.getWaktuSelesai());
                return "form-change-lembur-HRPR";
            }else{
                if(lembur.getStatusPersetujuan()==0){
                    model.addAttribute("status", "Menunggu Persetujuan");
                }else if(lembur.getStatusPersetujuan()==1){
                    model.addAttribute("status", "Ditolak");
                }else{
                    model.addAttribute("status", "Disetujui");
                }
                return "form-change-lembur";
            }
        }

    }
    @PostMapping("/change")
    private String changeLemburSubmit(@ModelAttribute LemburModel lembur, Model model){
        lemburService.changeLembur(lembur);
        model.addAttribute("msg", "Pengubahan lembur berhasil!");
        return "lembur-notif";
    }

    @GetMapping("/delete/{idLembur}")
    private String deleteLembur(
            @PathVariable(required = false) Long idLembur,
            Model model
    ) {
        LemburModel lembur = lemburService.findById(idLembur).get();
        model.addAttribute("lembur", lembur);
        UserModel user = userFinder();
        String role = user.getRoleModel().getNamaRole();
        //Kalau karyawan, hanya bisa erase punya sendiri
        if(role.equals("Karyawan")){
            List<LemburModel> lemburs = user.getGajiModel().getListLembur();
            List<Long> lemburIds = new ArrayList<>();
            for(LemburModel each : lemburs){
                lemburIds.add(each.getId());
            }
            if (lemburIds.contains(idLembur)){
                model.addAttribute("msg", "Lembur berhasil dihapus!");
                lemburService.deleteById(idLembur);
            } else{
                model.addAttribute("msg", "Lembur tersebut tidak dapat anda hapus!");
            }
        } else{
            model.addAttribute("msg", "Lembur berhasil dihapus!");
            lemburService.deleteById(idLembur);
        }
        return "lembur-notif";
    }

    @GetMapping("/view")
    private String viewLembur(Model model) {
        UserModel user = userFinder();
        RoleModel role = user.getRoleModel();
        String roleName = role.getNamaRole();
        //Jika diakses oleh Kepala Departemen HR atau Staff Payroll
        if ((roleName.equals("Kepala Departemen HR")) || (roleName.equals("Staff Payroll"))) {
            List<LemburModel> lemburList = lemburService.findAll();
            if (lemburList.size() > 0) {
                model.addAttribute("kompensasi", lemburService.getAllKompensasiList());
                model.addAttribute("lemburList", lemburList);
            }
        } else {
            if (user.getRoleModel() != null) {
                Long idGaji = user.getGajiModel().getId();
                List<LemburModel> lemburList = lemburService.findByIdGaji(idGaji);
                model.addAttribute("kompensasi", lemburService.getKompensasiList(idGaji));
                model.addAttribute("lemburList", lemburList);
            }
        }
        return "view-lembur";
    }



}
