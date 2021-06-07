package com.demcare.demo.controller;

import com.demcare.demo.entities.*;
import com.demcare.demo.models.DataModel;
import com.demcare.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@CrossOrigin(origins = "*")
public class AdminController extends DemcareController {

    @Autowired
    private UserService userService;

    @Autowired
    private AssociationInstitutionUserService associationInstitutionUserService;

    @Autowired
    private AssociationCarerPlayerService associationCarerPlayerService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SolicitudesService solicitudesService;

    @Autowired
    private InvitationsService invitationsService;

    @Autowired
    private GameService gameService;

    @Autowired
    private AssociationInstitutionGameService associationInstitutionGameService;

    @RequestMapping("/admin/list")
    public String getList(Model model){
        model.addAttribute("userList", userService.getAdminList() );
        return "/admin/list";
    }

    @RequestMapping("/admin/listgames")
    public String getListGames(Model model){
        model.addAttribute("gameList", gameService.findAll() );
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        model.addAttribute("user", user.getId() );
        return "/admin/listgames";
    }

    @RequestMapping("/admin/delete/{id}" )
    public String deleteUser(@PathVariable Long id){
        User userToDelete = userService.findById(id);
        //Borramos primero todas sus asociaciones
        List<AssociationInstitutionUser> listAssocitionsInstitution = associationInstitutionUserService.findByUser(userToDelete);
        for(AssociationInstitutionUser a: listAssocitionsInstitution){
            associationInstitutionUserService.deleteAssociationInstitutionUser(a.getId());
        }

        listAssocitionsInstitution = associationInstitutionUserService.findByUserInstitution(userToDelete);
        for(AssociationInstitutionUser a: listAssocitionsInstitution){
            associationInstitutionUserService.deleteAssociationInstitutionUser(a.getId());
        }

        List<AssociationCarerPlayer> listAssociationCarer = associationCarerPlayerService.findByCarerUser(userToDelete);
        for(AssociationCarerPlayer a: listAssociationCarer){
            associationCarerPlayerService.deleteAssociationCarerPlayer(a.getId());
        }

        listAssociationCarer = associationCarerPlayerService.findByPlayerUser(userToDelete);
        for(AssociationCarerPlayer a: listAssociationCarer){
            associationCarerPlayerService.deleteAssociationCarerPlayer(a.getId());
        }

        //Borramos token
        Token token = tokenService.findByUser(userToDelete);
        if(token != null){
            tokenService.deleteToken(token.getId());
        }

        //Borramos solicitudes
        List<SolicitudesInstitutions> listSolicitudes = solicitudesService.findByUser(userToDelete);
        for(SolicitudesInstitutions a: listSolicitudes){
            solicitudesService.deleteSolicitude(a.getId());
        }

        listSolicitudes = solicitudesService.findByUserInstitution(userToDelete);
        for(SolicitudesInstitutions a: listSolicitudes){
            solicitudesService.deleteSolicitude(a.getId());
        }

        //Borramos invitaciones
        List<InvitationsInstitutions> listInvitations = invitationsService.findByUser(userToDelete);
        for(InvitationsInstitutions a: listInvitations){
            invitationsService.deleteInvitation(a.getId());
        }

        listInvitations = invitationsService.findByUserInstitution(userToDelete);
        for(InvitationsInstitutions a: listInvitations){
            invitationsService.deleteInvitation(a.getId());
        }

        //Borramos juegos si es una institucion
        List<AssociationInstitutionGame> listGames = associationInstitutionGameService.findByInstitution(userToDelete);
        for(AssociationInstitutionGame a: listGames){
            associationInstitutionGameService.deleteById(a.getId());
        }

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

    @RequestMapping("/admin/desactive/{id}" )
    public String desactivateGame(@PathVariable Long id){
        gameService.desactivate(id);
        return "redirect:/admin/listgames";
    }

    @RequestMapping("/admin/activategame/{id}" )
    public String activateGame(@PathVariable Long id){
        gameService.activate(id);
        return "redirect:/admin/listgames";
    }

    @RequestMapping(value="/admin/addgame")
    public String getUser(Model model){
        model.addAttribute("game", new Game());
        return "/admin/addgame";
    }

    @RequestMapping(value="/admin/addgame", method= RequestMethod.POST )
    public String setUser(@Validated Game game, BindingResult result, Model
            model, HttpServletRequest request) {
        gameService.save(game);
        List<User> instituciones = userService.getInstitutions();
        for(User u: instituciones){
            AssociationInstitutionGame asociacion = new AssociationInstitutionGame();
            asociacion.setInstitution(u);
            asociacion.setGame(game);
            associationInstitutionGameService.save(asociacion);
        }
        return "redirect:/home";
    }

}
