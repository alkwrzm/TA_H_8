package apap.sipayroll.controller;


import apap.sipayroll.model.UserModel;
import apap.sipayroll.service.RoleService;
import apap.sipayroll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/addUser")
    private String addUserSubmit(Model model){
        model.addAttribute("user", new UserModel());
        model.addAttribute("listRole", roleService.findAll());

        return "add-user";
    }
    @PostMapping(value = "/addUser")
    private String postUserSubmit(@ModelAttribute UserModel user){
        /*
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getRoleModel().getId());
        */
        userService.addUser(user);

        return "redirect:/";
    }
}
