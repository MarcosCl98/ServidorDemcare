package com.demcare.demo.controller;


import com.demcare.demo.entities.User;
import com.demcare.demo.service.*;
import com.demcare.demo.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@Controller
public class PlayerController extends DemcareController {

    @Autowired
    private UserService userService;

    @Autowired
    private GameService gameService;

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/jugador/addphoto", method = RequestMethod.GET)
    public String addphotoJugador(Model model) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        model.addAttribute("user",user);
        String s = user.getPhotosImagePath();
        return "/jugador/addphoto";
    }

    @RequestMapping(value = "/jugador/addphoto", method = RequestMethod.POST)
    public String saveUserJugador(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        user.setPhotos("image.png");

        User savedUser = userService.save(user);

        String uploadDir = "src/main/resources/static/img/" + savedUser.getMail();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return "redirect:/jugador/addphoto";
    }

    @RequestMapping("/jugador/listInvitations")
    public String getInvitationsJugador(Model model){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        model.addAttribute("userList", userService.getInvitations(user.getId()));
        return "/jugador/listInvitations";
    }

    @RequestMapping("/jugador/accept/{id}" )
    public String acceptInvitationJugador(@PathVariable Long id){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        userService.acceptInvitation(id,user.getId());
        return "redirect:/jugador/listInvitations";
    }

    @RequestMapping("/jugador/playgames")
    public String getListGames(Model model){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        model.addAttribute("gameList", gameService.findActiveGames(user.getId()) );
        model.addAttribute("user", user.getId() );
        return "/jugador/playgames" ;
    }

}
