package com.demcare.demo.controller;


import com.demcare.demo.entities.AssociationInstitutionGame;
import com.demcare.demo.entities.Data;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@CrossOrigin(origins = "*")
public class InstitutionController extends DemcareController {

    @Autowired
    private UserService userService;

    @Autowired
    private AssociationInstitutionGameService associationInstitutionGameService;

    @Autowired
    private DataService dataService;

    @Autowired
    private GameService gameService;


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
        List<User> users = userService.getUsersWithoutInvitation(user.getId());
        model.addAttribute("userList", userService.getUsersWithoutAsocciationWithAInstitution(users));
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
        List<User> jugadoresNoAsociados = userService.getNotAssociatedPlayers(user); //jugadores no asociados al cuidador seleccionado
        List<User> jugadoresNoAsociadosAsociados = userService.getNotAssociatedPlayersWithCarer(jugadoresNoAsociados,institution.getId()); //jugadores asociados a la misma institucion
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

    @RequestMapping("/institution/listinformes" )
    public String getJugadoresListInformes(Model model, HttpServletRequest request){

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User institucion = userService.findByMail(username);

        List<User> cuidadoresAsociados = userService.getAssociateCarers(institucion.getId());
        List<Game> juegos = gameService.findAll();
        List<User> jugadoresAsocidados = new ArrayList<>();
        for(User u: cuidadoresAsociados){
            List<User> jugadoresAsociadosCuidador = userService.getAssociatedUsersToCarer(u);
            for(User uc: jugadoresAsociadosCuidador){
                boolean existe = false;
                for(User uf: jugadoresAsocidados){
                    if(uf.getId() == uc.getId()){
                        existe = true;
                    }
                }
                if(!existe){
                    jugadoresAsocidados.add(uc);
                }
            }
        }

        List<String> juegosString = new ArrayList<>();
        for(Game game: juegos){
            juegosString.add(game.getTitulo());
        }
        model.addAttribute("jugadoresAsocidados", jugadoresAsocidados);
        model.addAttribute("juegos",juegosString);
        return "/institution/listinformes";
    }

    @RequestMapping("/institution/information/{id}/{gameString}" )
    public String userInformation(Model model,@PathVariable Long id, @PathVariable String gameString, HttpServletRequest request){
        User user = userService.findById(id);
        Game game = gameService.findByTitulo(gameString);
        request.getSession().setAttribute("game",game);
        request.getSession().setAttribute("paciente",user);
        return "redirect:/institution/informationuser";
    }

    @RequestMapping("/institution/informationuser" )
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
            for(Data d: listDatos){
                listaTiempos.add(d.getTime_opened());
                listaClicks.add(d.getNumber_clicks());
                listMaxAcceleration.add(d.getMax_acceleration());
                listaMaxSpeed.add(d.getMax_speed());
                listaNumErrors.add(d.getNumber_errors());
            }

            String stringTiempos = "time opened-" + data.getKey();
            for(int i=0; i<listaTiempos.size();i++){
                stringTiempos+= "-" + listaTiempos.get(i);
            }
            listaConDatos.add(stringTiempos);

            String stringClicks = "number of clicks-"+ data.getKey();
            int sumaclicks = 0;
            for(int i=0; i<listaClicks.size();i++){
                stringClicks+= "-" + listaClicks.get(i);
                sumaclicks+= listaClicks.get(i);
            }
            if(sumaclicks>0){
                listaConDatos.add(stringClicks);
            }

            String stringAcc = "max acceleration mouse px/s2-"+ data.getKey();
            for(int i=0; i<listMaxAcceleration.size();i++){
                stringAcc+= "-" + listMaxAcceleration.get(i);
            }
            listaConDatos.add(stringAcc);

            String stringSpeed = "max speed mouse px/s2-"+ data.getKey();
            for(int i=0; i<listaMaxSpeed.size();i++){
                stringSpeed+= "-" + listaMaxSpeed.get(i);
            }
            listaConDatos.add(stringSpeed);

            String stringErrors = "number of errors-" + data.getKey();
            for(int i=0; i<listaNumErrors.size();i++){
                stringErrors+= "-" + listaNumErrors.get(i);
            }
            listaConDatos.add(stringErrors);
        }
        model.addAttribute("listaConDatos", listaConDatos);
        return "/institution/informationuser";
    }

}
