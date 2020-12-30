package apap.sipayroll.controller;
import apap.sipayroll.model.UserModel;
import apap.sipayroll.rest.*;
import apap.sipayroll.service.LowonganRestService;

import apap.sipayroll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

@Controller
public class LowonganController {
    @Autowired
    private LowonganRestService lowonganRestService;

    @Autowired
    private UserService userService;

    @RequestMapping("/lowongan/add")
    public String addUserPage(Model model){
        model.addAttribute("lowongan", new LowonganDetail());
        return "form-add-lowongan";
    }

    @RequestMapping(value="/lowongan/add", method = RequestMethod.POST)
    public String addLowonganFormPage(@ModelAttribute LowonganDetail lowongan,
                                      RedirectAttributes redir
    ){
        MultiValueMap<String, String> dataform = new LinkedMultiValueMap<>();
        UserModel userLowongan = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        lowongan.setUsername(userLowongan.getUsername());
        if(lowongan.getDivisi() != null && lowongan.getJenisLowongan() != null && lowongan.getJumlah() != null && lowongan.getPosisi() != null){
            lowonganRestService.requestLowongan(lowongan);
            redir.addFlashAttribute("notes", "Penambahan lowongan berhasil!");
        }else{
            redir.addFlashAttribute("notes", "Penambahan lowongan gagal!");
        }
        System.out.println(dataform);
        System.out.println(userLowongan);
        return "redirect:/lowongan/add";
    }
}