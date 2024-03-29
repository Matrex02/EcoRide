package com.ECO.controller;

import com.ECO.login_System.registration.RegistrationRequest;
import com.ECO.login_System.registration.RegistrationService;
import com.ECO.service.EcoService;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Controller
public class HomeController {

    private final EcoService ecoService;

    private final RegistrationService registrationService;
    private final SpringTemplateEngine templateEngine;

    public HomeController(EcoService ecoService,RegistrationService registrationService,SpringTemplateEngine templateEngine) {
        this.ecoService = ecoService;
        this.registrationService = registrationService;
        this.templateEngine = templateEngine;
    }

    @GetMapping("/api/v/registration/index")
    public String getHomeWithoutLogin(Model model, HttpSession session) {
        //model.addAttribute("loggedIn", session.getAttribute("loggedIn"));
        return ecoService.getHomeWithoutLogin();
    }
    @GetMapping("/index")
    public ModelAndView getHome(Authentication authentication) {
        return ecoService.getHome(authentication);
    }
    @GetMapping("/api/v/registration/register")
    public String getRegister() {
        return ecoService.getRegister();
    }

    @GetMapping("/api/v/registration/logout")
    public String getLogout() {
        return ecoService.getLogout();
    }
    @RequestMapping("/profile")
    public ModelAndView zeigeProfil(Authentication authentication) {
        return ecoService.zeigeProfil(authentication);
    }


    @GetMapping("/api/v/registration/contact")
    public String getContactWithoutLogin() {
        return ecoService.getContactWithoutLogin();
    }
    @GetMapping("/contact")
    public ModelAndView getContact(Authentication authentication) {

        return ecoService.getContact(authentication);
    }

    @GetMapping("/api/v/registration/services")
    public String getServices() {
        return ecoService.getServicesWithoutLogin();
    }
    @GetMapping("/services")
    public ModelAndView getServicesWithoutLogin(Authentication authentication) {
        return ecoService.getServices(authentication);
    }
    @GetMapping("/api/v/registration/about")
    public String getAbout() {
        return ecoService.getAboutWithoutLogin();
    }
    @GetMapping("/about")
    public ModelAndView getAboutWithoutLogin(Authentication authentication) {
        return ecoService.getAbout(authentication);
    }
    @PostMapping(path = "api/v1/registration")
    public ResponseEntity<String> register(@ModelAttribute @RequestBody RegistrationRequest registrationRequest,HttpServletRequest request) {
        String confirmationResult = registrationService.register(registrationRequest,request);
        Context context = new Context();
        context.setVariable("confirmationResult", confirmationResult);
        String renderedHtml = templateEngine.process("/registerSucsses", context);
        // Return the rendered HTML as a response with appropriate headers and status
        return ResponseEntity.ok().body(renderedHtml);
    }
    @GetMapping(path = "confirm")
    public ResponseEntity<String> confirm(@RequestParam("token") String token, Model model) {
        String confirmationResult = registrationService.confirmToken(token);
        // Create a Thymeleaf context and add necessary data
        Context context = new Context();
        context.setVariable("confirmationResult", confirmationResult);

        // Render the HTML template
        String renderedHtml = templateEngine.process("confirmed", context);

        // Return the rendered HTML as a response with appropriate headers and status
        return ResponseEntity.ok().body(renderedHtml);
    }
    @GetMapping()
    public String registerDone() {
        return "sucess";

    }

    @GetMapping(path = "/api/v/registration/term_condition")
    public String getTerm_condition() {
        return "term_condition";
    }

}
