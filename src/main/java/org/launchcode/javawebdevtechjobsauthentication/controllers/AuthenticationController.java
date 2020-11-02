package org.launchcode.javawebdevtechjobsauthentication.controllers;

import org.launchcode.javawebdevtechjobsauthentication.models.User;
import org.launchcode.javawebdevtechjobsauthentication.models.data.UserRepository;
import org.launchcode.javawebdevtechjobsauthentication.models.dto.LoginDTO;
import org.launchcode.javawebdevtechjobsauthentication.models.dto.RegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Oksana
 */
@Controller
public class AuthenticationController {

   @Autowired
   UserRepository userRepository;

   private final static String userSessionKey = "user";

   private static final List<String> whitelist = Arrays.asList("/login", "/register", "/logout", "/css");


   public User getUserFromSession(HttpSession session){
      Integer userId = (Integer) session.getAttribute(userSessionKey);

      if(userId == null){
         return null;
      }

      Optional<User> user = userRepository.findById(userId);

      if(user.isEmpty()){
         return null;
      }
      return  user.get();
   }

   public static void setUserInSession(HttpSession session, User user){

      session.setAttribute(userSessionKey, user.getId());
   }

   @GetMapping("/register")
   public String displayRegistrationForm(Model model){

      model.addAttribute("title", "Register");
      model.addAttribute(new RegisterDTO());

      return "/register";
   }

   @PostMapping("/register")
   public String processRegistrationForm(@ModelAttribute @Valid RegisterDTO registerDTO,
                                         Errors errors, HttpServletRequest request, Model model){
      if(errors.hasErrors()){
         model.addAttribute("title", "Register");
         return "register";
      }
      User existingUser = userRepository.findByUsername(registerDTO.getUserName());

      if(existingUser !=null){
         errors.rejectValue("userName", "userName.alreadyexists", "A user already exists");
         model.addAttribute("title", "Register");

         return "register";
      }
      String password = registerDTO.getPassword();
      String verifyPassword = registerDTO.getVerifyPassword();

      if(!password.equals(verifyPassword)){
         errors.rejectValue("password", "Password mismatch", "Passwords do not match");
         model.addAttribute("title", "Register");
         return "register";
      }

      User newUser = new User(registerDTO.getUserName(), registerDTO.getPassword());

      userRepository.save(newUser);

      setUserInSession(request.getSession(), newUser);

      return "redirect:/login";
   }

   @GetMapping("/login")
   public String displayLoginForm(Model model){
      model.addAttribute(new LoginDTO());
      model.addAttribute("title", "Log In");

      return "login";
   }

   @PostMapping("/login")
   public String processLoginForm(@ModelAttribute @Valid LoginDTO loginDTO,
                                  Errors errors, HttpServletRequest request, Model model){
      if(errors.hasErrors()){
         model.addAttribute("title", "Log In");

         return "login";
      }
      User theUser = userRepository.findByUsername(loginDTO.getUserName());

      if(theUser == null) {
         errors.rejectValue("userName", "user.invalid", "The given username does not exist");

         model.addAttribute("title", "Log In");
         return "login";
      }

         String password = loginDTO.getPassword();

         if(!theUser.isMatchingPassword(password)){
            errors.rejectValue("password", "password.invalid", "Invalid password");
            model.addAttribute("title", "Log In");

            return "login";
         }
         setUserInSession(request.getSession(), theUser);

         return "redirect:";
      }
   @GetMapping("/logout")
   public String logout (HttpServletRequest request){

      request.getSession().invalidate();

      return "redirect:/login";
   }

}
