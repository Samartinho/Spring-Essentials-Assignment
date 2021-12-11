package springEssentialsAssignment.main.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import springEssentialsAssignment.main.model.User;
import springEssentialsAssignment.main.model.UserPrincipal;
import springEssentialsAssignment.main.service.UserService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired private UserService service;

    private UserPrincipal log;

    @GetMapping("/add-User")
    public String addUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("warning", "");
        return "add-User";
    }

    @GetMapping("/print-Users")
    public String printUsers(Model model){
        List<User> list = service.getAllUsers();
        model.addAttribute("users", list);
        return "print-Users";
    }

    @GetMapping("/error")
    public String handleError() { return "error"; }

    @GetMapping("/home")
    public String homeUser(Model model){
        model.addAttribute("user", log.getUser());
        return "home";
    }

    @GetMapping("/logIn")
    public String signIn(@ModelAttribute("user") User user) { return "logIn"; }

    @GetMapping("/edit-User")
    public String editUser(Model model) {
        model.addAttribute("user", log.getUser());
        return "edit-User";
    }

    @GetMapping("/delete-User")
    public String deleteUser(){
        service.deleteUser(log.getUser().getId());
        return "redirect:/";
    }





    @PostMapping("/add-User")
    public String createUser(User user, Model model){
        if(this.verifyEmail(user.getEmail()))
        {
            service.createUser(user);
            return "redirect:/";
        }
        else
        {
            model.addAttribute("warning", "this email already exists");
            return "add-User";
        }
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("user") User user){
        service.attUser(user, log);
        return "home";
    }






    public boolean verifyEmail(String email){
        Optional<User> user = service.findUserByEmail(email);
        if(user.isEmpty()) return true;
        else return false;
    }

    public void setUserPrincipal(UserPrincipal userPrincipal){
        this.log = userPrincipal;
    }

    public UserPrincipal getLog() { return log; }

    @InitBinder
    private void dateBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, editor);
    }
}
