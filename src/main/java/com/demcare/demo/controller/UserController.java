package com.demcare.demo.controller;

import com.demcare.demo.entities.User;
import com.demcare.demo.service.RolesService;
import com.demcare.demo.validators.SingUpFormValidator;
import com.demcare.demo.service.SecurityService;
import com.demcare.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class UserController extends DemcareController {

    @Autowired
    private UserService userService;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private SingUpFormValidator signUpFormValidator;


    @RequestMapping(value="/user/add", method=RequestMethod.POST )
    public String setUser(@Validated User user, BindingResult result, Model
            model) {
        signUpFormValidator.validate(user, result);
        if (result.hasErrors()) {
            return "singup";
        }
        model.addAttribute("rolesList", rolesService.getRoles());
        userService.register(user);
        return "redirect:/home";
    }

    @RequestMapping(value="/user/add")
    public String getUser(Model model){
        model.addAttribute("rolesList", rolesService.getRoles());
        model.addAttribute("user", new User());
        return "user/add";
    }



    //TODO: No funciona del todo el actualizar
    @RequestMapping("/user/list/update")
    public String updateList(Model model){
        model.addAttribute("userList", userService.getUsersList() );
        return "user/list :: tableUsers";
    }

    @RequestMapping(value = "/singup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("rolesList", rolesService.getRoles());
        model.addAttribute("user", new User());
        return "singup";
    }

    @RequestMapping(value = "/singup", method = RequestMethod.POST)
    public String signup(@Validated User user, BindingResult result, Model
            model) {

        validateRequiredParam(user.getMail(), "mail");
        validateRequiredParam(user.getPassword(), "password");
        validateRequiredParam(user.getName(), "name");
        validateRequiredParam(user.getSurname(), "surname");

        signUpFormValidator.validate(user, result);
        if (result.hasErrors()) {
            return "singup";
        }
        userService.register(user);
        securityService.autoLogin(user.getMail(), user.getPasswordConfirm());
        return "redirect:home";
    }

    /*@RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@Validated User user, BindingResult result, Model
            model) {

        *//*loginFormValidator.validate(user, result);
        if (result.hasErrors()) {
            return "login";
        }*//*
        return "redirect:home";
    }*/

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }



    @RequestMapping(value = { "/home" }, method = RequestMethod.GET)
    public String home(Model model) {
        return "home";
    }

    @RequestMapping("/admin/list")
    public String getList(Model model){
        model.addAttribute("userList", userService.getUsersList() );
        return "/admin/list";
    }

    @RequestMapping("/admin/delete/{id}" )
    public String deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return "redirect:/admin/list";
    }

    @RequestMapping("/admin/suspend/{id}" )
    public String suspendUser(@PathVariable Long id){
        userService.suspendUser(id);
        return "redirect:/admin/list";
    }

    @RequestMapping("/admin/activate/{id}" )
    public String activateUser(@PathVariable Long id){
        userService.activateUser(id);
        return "redirect:/admin/list";
    }

  /*  @RequestMapping("/institution/listUsers")
    public String getInstitutionList(Model model){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        model.addAttribute("userList", userService.getNotAsociatedUsers(user.getId()));
        model.addAttribute("invitatedUsers", userService.getInvitatedUsers(user.getId()));
        return "/institution/listUsers";
    }*/

      /*@RequestMapping("/institution/asociate/{id}" )
    public String asociateUser(@PathVariable Long id){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        userService.asociateUser(user.getId(),id);
        return "redirect:/institution/listUsers";
    }*/

    @RequestMapping("/institution/list")
    public String getInstitutionList(Model model){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        model.addAttribute("userList", userService.getUsersWithAsociation(user.getId()));
        return "/institution/list";
    }

    @RequestMapping("/institution/listUsersToInvite")
    public String getInstitutionListToInvite(Model model){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        model.addAttribute("userList", userService.getUsersWithoutInvitation(user.getId()));
        model.addAttribute("invitatedUsers", userService.getUsersWithInvitationAndRequest(user.getId()));
        return "/institution/listUsersToInvite";
    }


    @RequestMapping("/institution/invite/{id}" )
    public String inviteUser(@PathVariable Long id){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        userService.invitateUser(user.getId(),id);
        return "redirect:/institution/listUsersToInvite";
    }

    @RequestMapping("/institution/listSolicitudes" )
    public String getSolicitudes(Model model){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        model.addAttribute("userList", userService.getSolicitudes(user.getId()));
        return "/institution/listSolicitudes" ;
    }

    @RequestMapping("/institution/accept/{id}" )
    public String acceptSolicitude(@PathVariable Long id){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        userService.acceptSolicitude(user.getId(),id);
        return "redirect:/institution/listSolicitudes";
    }

    @RequestMapping("/cuidador/list")
    public String getCuidadorList(Model model){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        model.addAttribute("cuidadorList", userService.getInstitutionsWithAsociation(user.getId()));
        return "/cuidador/list";
    }

    @RequestMapping("/cuidador/listInstitutions")
    public String getInstitutions(Model model){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        model.addAttribute("userList", userService.getNotRequestInstitutions(user.getId()));
        model.addAttribute("requestInstitutions", userService.getRequestInstitutions((user.getId())));
        return "/cuidador/listInstitutions";
    }

    @RequestMapping(value = "/cuidador/addphoto", method = RequestMethod.GET)
    public String addphoto(Model model) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        model.addAttribute("user",user);
        String s = user.getPhotosImagePath();
        return "/cuidador/addphoto";
    }

    @RequestMapping(value = "/cuidador/addphoto", method = RequestMethod.POST)
    public String saveUser(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        user.setPhotos("image.png");

        User savedUser = userService.save(user);

        String uploadDir = "src/main/resources/static/img/" + savedUser.getMail();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return "redirect:/cuidador/addphoto";
    }

    @RequestMapping("/cuidador/listInvitations")
    public String getInvitations(Model model){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        model.addAttribute("userList", userService.getInvitations(user.getId()));
        return "/cuidador/listInvitations";
    }

    @RequestMapping("/cuidador/accept/{id}" )
    public String acceptInvitation(@PathVariable Long id){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        userService.acceptInvitation(id,user.getId());
        return "redirect:/cuidador/listInvitations";
    }

    @RequestMapping("/cuidador/solicitude/{id}" )
    public String sentSolicitude(@PathVariable Long id){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        userService.sentSolicitude(user.getId(),id);
        return "redirect:/cuidador/listInstitutions";
    }

}
