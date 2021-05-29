package com.demcare.demo.controller;


import com.demcare.demo.entities.AssociationInstitutionGame;
import com.demcare.demo.entities.Game;
import com.demcare.demo.entities.User;
import com.demcare.demo.models.GameModel;
import com.demcare.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class InstitutionController extends DemcareController {

    @Autowired
    private UserService userService;

    @Autowired
    private AssociationInstitutionGameService associationInstitutionGameService;

    @RequestMapping("/institution/list")
    public String getInstitutionList(Model model){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        model.addAttribute("userList", userService.getAssociateUsers(user.getId()));
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

    @RequestMapping("/institution/listCuidadores")
    public String getCuidadores(Model model){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        model.addAttribute("cuidadores", userService.getAssociateCarers(user.getId()));
        return "/institution/listCuidadores";
    }

    @RequestMapping("/institution/listJugadores/{id}" )
    public String getJugadores(Model model,@PathVariable Long id, HttpServletRequest request){
        User user = userService.findById(id);
        request.getSession().setAttribute("cuidador",user);

        return "redirect:/institution/listJugadores";
    }

    @RequestMapping("/institution/listJugadores" )
    public String getJugadoresList(Model model, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("cuidador");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User institution = userService.findByMail(username);
        List<User> jugadoresNoAsociados = userService.getNotAssociatedPlayers(user);
        List<User> jugadoresNoAsociadosAsociados = userService.getNotAssociatedPlayersWithCarer(jugadoresNoAsociados,institution.getId());
        model.addAttribute("jugadoresNoAsociados", jugadoresNoAsociadosAsociados);
        return "/institution/listJugadores";
    }

    @RequestMapping("/institution/asociate/{id}" )
    public String asociateUser(@PathVariable Long id, HttpServletRequest request){
        User carer = (User) request.getSession().getAttribute("cuidador");
        User player = userService.findById(id);
        userService.asociateCarerPlayer(carer.getId(),player.getId());
        return "redirect:/institution/listJugadores";
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

    @RequestMapping("/institution/listgames")
    public String getListGames(Model model){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        List<AssociationInstitutionGame> list = associationInstitutionGameService.findByInstitution(user);
        List<GameModel> games = new ArrayList<>();
        for(AssociationInstitutionGame a: list){
            GameModel game = new GameModel();
            game.setDescripcion(a.getGame().getDescripcion());
            game.setId(a.getId());
            game.setTitulo(a.getGame().getTitulo());
            game.setDesactivate(a.getDesactive());
            games.add(game);
        }
        model.addAttribute("gameList", games );
        model.addAttribute("user", user.getId() );
        return "/institution/listgames";
    }

    @RequestMapping("/institution/suspend/{id}" )
    public String suspend(@PathVariable Long id){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        List<AssociationInstitutionGame> list = associationInstitutionGameService.findByInstitution(user);
        for(AssociationInstitutionGame a: list){
            if(a.getId() == id){
                associationInstitutionGameService.suspend(id);
            }
        }
        return "redirect:/institution/listgames";
    }

    @RequestMapping("/institution/activate/{id}" )
    public String activate(@PathVariable Long id){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        List<AssociationInstitutionGame> list = associationInstitutionGameService.findByInstitution(user);
        for(AssociationInstitutionGame a: list){
            if(a.getId() == id){
                associationInstitutionGameService.activate(id);
            }
        }
        return "redirect:/institution/listgames";
    }

}
