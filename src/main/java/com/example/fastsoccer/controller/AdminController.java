package com.example.fastsoccer.controller;

import com.example.fastsoccer.entity.OwnPitch;
import com.example.fastsoccer.repository.OwnPitchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    OwnPitchRepository ownPitchRepository;
//hiển thị danh sách sân chờ duyệt

    //detail sân chờ duyệt

    @GetMapping("/loadPitchNotAllow")
    public String loadPitchNotAllow(Model model) {
        List<OwnPitch> ownPitchList=ownPitchRepository.findOwnPitchWatting(); //hiển thị sân
        model.addAttribute("ownPitchList", ownPitchList);
        List<OwnPitch> ownPitchListOk=ownPitchRepository.findOwnPitchSuccess(); //hiển thị sân
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
    //tạo tài khoản cho chủ sân bóng
    @GetMapping("/createacount")
    public ModelAndView createAcount(Model model, @RequestParam("id") Long id) {
        ModelAndView mav = new ModelAndView("createAccountOwn");
        OwnPitch ownPitch = ownPitchRepository.findById(id).get();
        mav.addObject("ownPitch", ownPitch);
        return mav;
    }


}
