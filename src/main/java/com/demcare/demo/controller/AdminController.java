package com.demcare.demo.controller;

import com.demcare.demo.entities.*;
import com.demcare.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Controller
@CrossOrigin(origins = "*")
public class AdminController  {

    @Autowired
    private UserService userService;

    @Autowired
    private AssociationInstitutionUserService associationInstitutionUserService;

    @Autowired
    private AssociationCarerPlayerService associationCarerPlayerService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SolicitudeService solicitudeService;

    @Autowired
    private InvitationService invitationService;

    @Autowired
    private GameService gameService;

    @Autowired
    private DataService dataService;

    @Autowired
    private AssociationInstitutionGameService associationInstitutionGameService;

    @RequestMapping("/admin/list")
    public String getList(Model model){
        model.addAttribute("userList", userService.getAdminList() );
        return "admin/list";
    }

    @RequestMapping("/admin/listgames")
    public String getListGames(Model model){
        model.addAttribute("gameList", gameService.findAll() );
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user.getId() );
        return "admin/listgames";
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
        List<Solicitude> listSolicitudes = solicitudeService.findByUser(userToDelete);
        for(Solicitude a: listSolicitudes){
            solicitudeService.deleteSolicitude(a.getId());
        }

        listSolicitudes = solicitudeService.findByUserInstitution(userToDelete);
        for(Solicitude a: listSolicitudes){
            solicitudeService.deleteSolicitude(a.getId());
        }

        //Borramos invitaciones
        List<Invitation> listInvitations = invitationService.findByUser(userToDelete);
        for(Invitation a: listInvitations){
            invitationService.deleteInvitation(a.getId());
        }

        listInvitations = invitationService.findByUserInstitution(userToDelete);
        for(Invitation a: listInvitations){
            invitationService.deleteInvitation(a.getId());
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
    public String getGame(Model model){
        model.addAttribute("game", new Game());
        return "admin/addgame";
    }

    @RequestMapping(value="/admin/addgame", method= RequestMethod.POST )
    public String setGame(@RequestBody Game game) {
        gameService.save(game);
        List<User> instituciones = userService.getInstitutions();
        for(User u: instituciones){
            AssociationInstitutionGame asociacion = new AssociationInstitutionGame();
            asociacion.setInstitution(u);
            asociacion.setGame(game);
            asociacion.setDesactivate(true);
            associationInstitutionGameService.save(asociacion);
        }
        return "redirect:/home";
    }

    @RequestMapping("/admin/listinformes" )
    public String getJugadoresInformes(Model model, HttpServletRequest request, Pageable pageable){
        List<User> jugadores = userService.getPlayerList();
        List<Game> juegos = gameService.findAll();
        List<String> juegosString = new ArrayList<>();
        for(Game game: juegos){
            juegosString.add(game.getTitulo());
        }
        model.addAttribute("jugadoresAsocidados", jugadores);
        model.addAttribute("juegos", juegosString);

        return "admin/listinformes";
    }

    @RequestMapping("/admin/information/{id}/{gameString}" )
    public String userInformation(Model model,@PathVariable Long id, @PathVariable String gameString, HttpServletRequest request){
        User user = userService.findById(id);
        Game game = gameService.findByTitulo(gameString);
        request.getSession().setAttribute("game",game);
        request.getSession().setAttribute("paciente",user);
        return "redirect:/admin/information";
    }

    @RequestMapping("/admin/information" )
    public String userInformationGraph(Model model, HttpServletRequest request){
        User paciente = (User) request.getSession().getAttribute("paciente");
        Game game = (Game) request.getSession().getAttribute("game");
        List<Data> dataList =  dataService.findByUserAndGame(paciente,game);

        Map<String, List<Data>> mapGames = new HashMap<String, List<Data>>();
        for(Data data: dataList){
            if(mapGames.containsKey(data.getScene())){
                List<Data> listExistente = mapGames.get(data.getScene());
                listExistente.add(data);
            }else{
                List<Data> newList = new ArrayList<>();
                newList.add(data);
                mapGames.put(data.getScene(),newList);
            }

        }

        Map<String, List<Data>> mapFilteredGames = new HashMap<String, List<Data>>();
        for (Map.Entry<String, List<Data>>  data : mapGames.entrySet()) {
            List<Data> listDatos = data.getValue();
            if(listDatos.size() >1 ){
                mapFilteredGames.put(data.getKey(),data.getValue());
            }
        }
        List<String> listaConDatos = new ArrayList();
        for (Iterator<Map.Entry<String, List<Data>>> entries = mapFilteredGames.entrySet().iterator(); entries.hasNext(); ) {
            Map.Entry<String, List<Data>> data = entries.next();
            List<Data> listDatos = data.getValue();
            List<Double> listaTiempos = new ArrayList();
            List<Integer> listaClicks = new ArrayList();
            List<Integer> listMaxAcceleration = new ArrayList();
            List<Integer> listaMaxSpeed = new ArrayList();
            List<Integer> listaNumErrors = new ArrayList();
            for (Data d : listDatos) {
                listaTiempos.add(d.getTime_opened());
                listaClicks.add(d.getNumber_clicks());
                listMaxAcceleration.add(d.getMax_acceleration());
                listaMaxSpeed.add(d.getMax_speed());
                listaNumErrors.add(d.getNumber_errors());
            }

            String stringTiempos = "time opened-" + data.getKey();
            for (int i = 0; i < listaTiempos.size(); i++) {
                stringTiempos += "-" + listaTiempos.get(i);
            }
            listaConDatos.add(stringTiempos);

            String stringClicks = "number of clicks-" + data.getKey();
            int sumaclicks = 0;
            for (int i = 0; i < listaClicks.size(); i++) {
                stringClicks += "-" + listaClicks.get(i);
                sumaclicks += listaClicks.get(i);
            }
            if (sumaclicks > 0) {
                listaConDatos.add(stringClicks);
            }

            String stringAcc = "max acceleration mouse px/s2-" + data.getKey();
            for (int i = 0; i < listMaxAcceleration.size(); i++) {
                stringAcc += "-" + listMaxAcceleration.get(i);
            }
            listaConDatos.add(stringAcc);

            String stringSpeed = "max speed mouse px/s2-" + data.getKey();
            for (int i = 0; i < listaMaxSpeed.size(); i++) {
                stringSpeed += "-" + listaMaxSpeed.get(i);
            }
            listaConDatos.add(stringSpeed);

            String stringErrors = "number of errors-" + data.getKey();
            for (int i = 0; i < listaNumErrors.size(); i++) {
                stringErrors += "-" + listaNumErrors.get(i);
            }
            listaConDatos.add(stringErrors);
        }
        model.addAttribute("listaConDatos", listaConDatos);
        return "admin/information";
    }

}
