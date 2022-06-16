package com.example.fastsoccer.controller;

import com.example.fastsoccer.entity.District;
import com.example.fastsoccer.entity.OwnPitch;
import com.example.fastsoccer.entity.UserEntity;
import com.example.fastsoccer.repository.DistricRepository;
import com.example.fastsoccer.repository.OwnPitchRepository;
import com.example.fastsoccer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

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
        List<District> districtList = districRepository.findAll();
        model.addAttribute("districtList", districtList);
        return "index";
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

}
