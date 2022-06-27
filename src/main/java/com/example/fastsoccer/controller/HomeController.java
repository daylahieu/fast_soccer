package com.example.fastsoccer.controller;

import com.example.fastsoccer.entity.*;
import com.example.fastsoccer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

//trang home
@Controller
public class HomeController {
    @Autowired
    OwnPitchRepository ownPitchRepository;
    @Autowired
    DistricRepository districRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/loadPage")
    public String loadPage(Model model) {
        List<OwnPitch> ownPitchListOk = ownPitchRepository.findOwnPitchSuccess(); //hiển thị sân đã xác nhận
        model.addAttribute("ownPitchListOk", ownPitchListOk);
        return "index";
    }
    @GetMapping("/loadPageAfterLogin")
    public String loadPageAfterLogin(Model model, HttpSession session) {
        List<OwnPitch> ownPitchListOk = ownPitchRepository.findOwnPitchSuccess(); //hiển thị sân đã xác nhận
        model.addAttribute("ownPitchListOk", ownPitchListOk);
        List<District> districtList = districRepository.findAll();
        model.addAttribute("districtList", districtList);
        UserEntity userEntity = (UserEntity) session.getAttribute("user");
        model.addAttribute("user", userEntity);
        int countUser=userRepository.countReview();
        model.addAttribute("countUser",countUser);
        return "indexold";
    }

    @GetMapping("/loadFormLogin")
    public String loadFormLogin() {
        return "loginform";
    }

    @GetMapping("/loadFormRegister")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "register";
    }
    @PostMapping("/process_register")
    public String processRegister(UserEntity user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole("USER");
        userRepository.save(user);
        return "thankyou";
    }
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/loadPage";
    }
    @Autowired
    YardRepository yardRepository;
    @Autowired
    PriceYardRepository priceYardRepository;
    @GetMapping("/showDetail")
    public String showDetail(Model model, @RequestParam("id") Long id) {
      //  ModelAndView mav = new ModelAndView("single");
        OwnPitch ownPitch=ownPitchRepository.findById(id).get();
        model.addAttribute("ownPitch",ownPitch);
        List<Yard>yardList=yardRepository.findAllByOwnPitchId(id);
        model.addAttribute("yardList",yardList);
        List<PriceYard> priceYardList=priceYardRepository.findAllByYardId_OwnPitch_Id(id);
        model.addAttribute("priceYardList",priceYardList);
       // mav.addObject("ownPitch", ownPitch);
        return "pitchDetail";
    }


    @GetMapping("/loadbyyard")
    public String loadbyyard(Model model, @RequestParam("id") Long id) {
    List<PriceYard> priceYardList=priceYardRepository.findAllByYardId(id);
        model.addAttribute("priceYardList",priceYardList);
        return "pitchDetail";
    }

}
