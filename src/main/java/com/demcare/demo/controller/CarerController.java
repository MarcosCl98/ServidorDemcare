package com.demcare.demo.controller;

import com.demcare.demo.entities.*;
import com.demcare.demo.models.UserModel;
import com.demcare.demo.service.*;
import com.demcare.demo.util.FileUploadUtil;
import com.demcare.demo.util.SecureTokenGenerator;
import com.demcare.demo.validators.SingUpFormValidator;
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

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@Controller
@CrossOrigin(origins = "*")
public class CarerController extends DemcareController {

    @Autowired
    private UserService userService;

    @Autowired
    private AssociationCarerPlayerService associationCarerPlayerService;

    @Autowired
    private AssociationInstitutionUserService associationInstitutionUserService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private DataService dataService;

    @Autowired
    private GameService gameService;

    @Autowired
    private SingUpFormValidator signUpFormValidator;


    @RequestMapping("/cuidador/list")
    public String getCuidadorList(Model model){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if(user!=null){
            model.addAttribute("cuidadorList", userService.getInstitutionsWithAsociation(user.getId()));
        }
        return "cuidador/list";
    }

    @RequestMapping("/cuidador/listInstitutions")
    public String getInstitutions(Model model){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("userList", userService.getNotRequestInstitutions(user.getId()));
        model.addAttribute("requestInstitutions", userService.getRequestInstitutions((user.getId())));
        return "cuidador/listInstitutions";
    }

    @RequestMapping(value = "/cuidador/addphoto", method = RequestMethod.GET)
    public String addphoto(Model model) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user",user);
        String s = user.getPhotosImagePath();
        return "cuidador/addphoto";
    }

    @RequestMapping(value = "/cuidador/addphoto", method = RequestMethod.POST)
    public String saveUser(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        user.setPhotos("image.png");

        User savedUser = userService.save(user);

        String uploadDir = "src/main/resources/static/img/" + savedUser.getUsername();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return "redirect:/cuidador/addphoto";
    }

    @RequestMapping("/cuidador/listInvitations")
    public String getInvitations(Model model){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("userList", userService.getInvitations(user.getId()));
        return "cuidador/listInvitations";
    }

    @RequestMapping("/cuidador/accept/{id}" )
    public String acceptInvitation(@PathVariable Long id){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if(user != null){
            userService.acceptInvitation(id,user.getId());
        }
        return "redirect:/cuidador/listInvitations";
    }

    @RequestMapping("/cuidador/reject/{id}" )
    public String rejectInvitation(@PathVariable Long id){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        userService.rejectInvitation(id,user.getId());
        return "redirect:/cuidador/listInvitations";
    }

    @RequestMapping("/cuidador/solicitude/{id}" )
    public String sentSolicitude(@PathVariable Long id){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if(user!=null){
            userService.solicitudeInstitution(user.getId(),id);
        }
        return "redirect:/cuidador/listInstitutions";
    }

    @RequestMapping(value="/cuidador/add")
    public String getUser(Model model){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("rolesList", rolesService.getRoleJugador());
        model.addAttribute("institutionList", userService.getInstitutionsAssociated(user));
        model.addAttribute("user", new UserModel());
        return "cuidador/add";
    }

    @RequestMapping("/cuidador/listCuidadores")
    public String getCuidadores(Model model){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<User> cuidadores = userService.getCarerListWithoutUserSession(user);
        model.addAttribute("cuidadores", cuidadores);
        return "cuidador/listCuidadores";
    }

    @RequestMapping("/cuidador/listJugadores/{id}" )
    public String getJugadores(Model model,@PathVariable Long id, HttpServletRequest request){
        User user = userService.findById(id);
        request.getSession().setAttribute("cuidador",user);
        return "redirect:/cuidador/listJugadores";
    }

    @RequestMapping("/cuidador/listJugadores" )
    public String getJugadoresList(Model model, HttpServletRequest request){
        User cuidador2 = (User) request.getSession().getAttribute("cuidador");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User cuidador1 = userService.findByUsername(username);
        List<User> jugadoresAsocidados = userService.getAssociatedUsers(cuidador1, cuidador2);
        model.addAttribute("jugadoresAsocidados", jugadoresAsocidados);
        return "cuidador/listJugadores";
    }

    @RequestMapping("/cuidador/asociate/{id}" )
    public String asociateUser(@PathVariable Long id, HttpServletRequest request){
        User carer = (User) request.getSession().getAttribute("cuidador");
        User player = userService.findById(id);
        userService.asociateCarerPlayer(carer.getId(),player.getId());
        return "redirect:/cuidador/listJugadores";
    }

    @RequestMapping(value="/cuidador/add", method=RequestMethod.POST )
    public String setUser(@Validated UserModel userModel, BindingResult result, Model
            model, HttpServletRequest request) {
        validateRequiredParam(userModel.getUsername(), "username");
        validateRequiredParam(userModel.getPassword(), "password");
        validateRequiredParam(userModel.getName(), "name");
        validateRequiredParam(userModel.getSurname(), "surname");

        User user = new User();
        user.setRole("ROLE_JUGADOR");
        user.setName(userModel.getName());
        user.setSurname(userModel.getSurname());
        user.setUsername(userModel.getUsername());
        user.setPassword(userModel.getPassword());
        user.setPasswordConfirm(userModel.getPasswordConfirm());
        signUpFormValidator.validate(user, result);
        if (result.hasErrors()) {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = securityContext.getAuthentication();
            String username = authentication.getName();
            User institucion = userService.findByUsername(username);
            model.addAttribute("rolesList", rolesService.getRoleJugador());
            model.addAttribute("institutionList", userService.getInstitutionsAssociated(institucion));
            model.addAttribute("user", new UserModel());
            return "cuidador/add";
        }


        userService.register(user);
        model.addAttribute("rolesList", rolesService.getRoles());


        Token token = new Token();
        token.setUser(user);
        String tokenPrueba = SecureTokenGenerator.nextToken();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String tokenCode = tokenPrueba + timestamp.getTime();
        token.setCode(tokenCode);
        tokenService.save(token);

        AssociationCarerPlayer asociation = new AssociationCarerPlayer();
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User carer = userService.findByUsername(username);
        asociation.setCarerUser(carer);
        asociation.setPlayerUser(user);
        associationCarerPlayerService.save(asociation);

        AssociationInstitutionUser asociationInstitution = new AssociationInstitutionUser();
        if(userModel.getInstitucion()!=null){
            asociationInstitution.setUserInstitution(userService.findByName(userModel.getInstitucion()));
            asociationInstitution.setUser(user);
            associationInstitutionUserService.save(asociationInstitution);
        }
        request.getSession().setAttribute("tokenCode",tokenCode);
        return "redirect:/cuidador/download";
    }

    @RequestMapping(value="/cuidador/download")
    public String download(Model model, HttpServletRequest request){
        model.addAttribute("tokenCode", request.getSession().getAttribute("tokenCode"));
        return "cuidador/download";
    }

    @RequestMapping("/cuidador/listJugadoresInformes" )
    public String getJugadoresListInformes(Model model, HttpServletRequest request){

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User cuidador = userService.findByUsername(username);

        List<User> jugadoresAsocidados = userService.getAssociatedUsersToCarer(cuidador);
        List<Game> juegos = gameService.findAll();
        List<String> juegosString = new ArrayList<>();
        for(Game game: juegos){
            juegosString.add(game.getTitulo());
        }
        model.addAttribute("jugadoresAsocidados", jugadoresAsocidados);
        model.addAttribute("juegos",juegosString);
        return "cuidador/listJugadoresInformes";
    }

    @RequestMapping("/cuidador/information/{id}/{gameString}" )
    public String userInformation(Model model,@PathVariable Long id, @PathVariable String gameString, HttpServletRequest request){
        User user = userService.findById(id);
        Game game = gameService.findByTitulo(gameString);
        request.getSession().setAttribute("game",game);
        request.getSession().setAttribute("paciente",user);
        return "redirect:/cuidador/information";
    }

    @RequestMapping("/cuidador/information" )
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

            String stringAcc = "max acceleration px/s2-"+ data.getKey();
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
        return "cuidador/information";
    }
}
