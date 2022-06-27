package com.example.fastsoccer.controller;

import com.example.fastsoccer.entity.OwnPitch;
import com.example.fastsoccer.entity.UserEntity;
import com.example.fastsoccer.repository.OwnPitchRepository;
import com.example.fastsoccer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
//chức năng cho quản trị viên
@Controller
/*@RequestMapping("/admin")*/
public class AdminController {
    @Autowired
    OwnPitchRepository ownPitchRepository;
    @Autowired
    UserRepository userRepository;
//hiển thị danh sách sân chờ duyệt

    //detail sân chờ duyệt

    @GetMapping("/admin")
    public String loadPitchNotAllow(Model model) {
        List<OwnPitch> ownPitchList=ownPitchRepository.findOwnPitchWatting(); //hiển thị sân chưa xác nhận
        model.addAttribute("ownPitchList", ownPitchList);
        List<OwnPitch> ownPitchListOk=ownPitchRepository.findOwnPitchSuccess(); //hiển thị sân đã xác nhận
        model.addAttribute("ownPitchListOk", ownPitchListOk);
        return "manager";
    }
    //xem thông tin đầy đủ của sân và xét duyệt
    @GetMapping("/update")
    public ModelAndView update(Model model, @RequestParam("id") Long id) {
        ModelAndView mav = new ModelAndView("detail");
        OwnPitch ownPitch = ownPitchRepository.findById(id).get();
        mav.addObject("ownPitch", ownPitch);
        return mav;
    }
    @PostMapping("/updateStatus")
    public String updateStatus(@ModelAttribute("obj") OwnPitch ownPitch) {
        ownPitchRepository.save(ownPitch);
        return "redirect:/admin";
    }
    //chuyển sang trang tạo tài khoản cho chủ sân bóng
    @GetMapping("/createacount")
    public ModelAndView createAcount(Model model, @RequestParam("id") Long id) {
        ModelAndView mav = new ModelAndView("createAccountOwn");
        OwnPitch ownPitch = ownPitchRepository.findById(id).get();
        mav.addObject("ownPitch", ownPitch);
        model.addAttribute("user", new UserEntity());
        return mav;
    }
    //tạo tài khoản cho chủ sân bóng

    @PostMapping("/createAccountOwn")
    public String processRegister(UserEntity user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole("OWN");
        userRepository.save(user);
        return "redirect:/admin";
    }
}
