package apap.sipayroll.controller;

import apap.sipayroll.model.UserModel;
import apap.sipayroll.service.RoleService;
import apap.sipayroll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String home(Model model){
        UserModel user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("listRole", roleService.findAll());
        model.addAttribute("role",user.getRoleModel().getNamaRole());
        return "home";
    }

}

