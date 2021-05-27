package com.demcare.demo.controller;

import com.demcare.demo.entities.AssociationCarerPlayer;
import com.demcare.demo.entities.AssociationInstitutionUser;
import com.demcare.demo.entities.Token;
import com.demcare.demo.entities.User;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

@Controller
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
    private SingUpFormValidator signUpFormValidator;


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
        userService.solicitudeInstitution(user.getId(),id);
        return "redirect:/cuidador/listInstitutions";
    }

    @RequestMapping(value="/cuidador/add")
    public String getUser(Model model){
        model.addAttribute("rolesList", rolesService.getRoleJugador());
        model.addAttribute("user", new User());
        return "/cuidador/add";
    }

    @RequestMapping("/cuidador/listCuidadores")
    public String getCuidadores(Model model){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String username = authentication.getName();
        User user = userService.findByMail(username);
        List<User> institutionsAsociated = userService.getInstitutionsWithAsociation(user.getId());
        model.addAttribute("cuidadores", userService.getAssociateCarersWithoutUserAuthenticated(institutionsAsociated,user.getId()));
        return "/cuidador/listCuidadores";
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
        User cuidador1 = userService.findByMail(username);
        List<User> jugadoresAsocidados = userService.getAssociatedUsers(cuidador1, cuidador2);
        model.addAttribute("jugadoresAsocidados", jugadoresAsocidados);
        return "/cuidador/listJugadores";
    }

    @RequestMapping("/cuidador/asociate/{id}" )
    public String asociateUser(@PathVariable Long id, HttpServletRequest request){
        User carer = (User) request.getSession().getAttribute("cuidador");
        User player = userService.findById(id);
        userService.asociateCarerPlayer(carer.getId(),player.getId());
        return "redirect:/cuidador/listJugadores";
    }

    @RequestMapping(value="/cuidador/add", method=RequestMethod.POST )
    public String setUser(@Validated User user, BindingResult result, Model
            model, HttpServletRequest request) {
        signUpFormValidator.validate(user, result);
        if (result.hasErrors()) {
            return "singup";
        }
        model.addAttribute("rolesList", rolesService.getRoles());
        userService.register(user);

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
        User carer = userService.findByMail(username);
        asociation.setCarerUser(carer);
        asociation.setPlayerUser(user);
        associationCarerPlayerService.save(asociation);


        List<AssociationInstitutionUser> list = associationInstitutionUserService.findByUser(carer);
        for(AssociationInstitutionUser a: list){
            AssociationInstitutionUser asociationInstitution = new AssociationInstitutionUser();
            asociationInstitution.setUser(user);
            asociationInstitution.setUserInstitution(a.getUserInstitution());
            associationInstitutionUserService.save(asociationInstitution);
        }


        File folder = new File("src/main/resources/static/html/" );
        File[] files = folder.listFiles();
        if(files!=null) {
            for(File f: files) {
                f.delete();
            }
        }

        String uploadDir = "src/main/resources/static/html/" + user.getName() + ".html";
        File file = new File( uploadDir);
        String data = "<!DOCTYPE html>\n" +
                "<html lang=\"es\">\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>HTML</title>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"refresh\" content=\"0; url=http://localhost:8080/token/"+ tokenCode +"\" />\n" +
                "    <link rel=\"stylesheet\" href=\"estilo.css\">\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "</body>\n" +
                "</html>";

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.write(data);
        writer.close();
        String path = "../html/" + user.getName()+".html";
        request.getSession().setAttribute("pathdownload",path);
        return "redirect:/cuidador/download";
    }

    @RequestMapping(value="/cuidador/download")
    public String download(Model model, HttpServletRequest request){
        model.addAttribute("path", request.getSession().getAttribute("pathdownload"));
        return "/cuidador/download";
    }

}
