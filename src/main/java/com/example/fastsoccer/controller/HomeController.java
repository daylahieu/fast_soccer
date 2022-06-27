package com.example.fastsoccer.controller;

import com.example.fastsoccer.entity.OwnPitch;
import com.example.fastsoccer.repository.OwnPitchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    OwnPitchRepository ownPitchRepository;
    @GetMapping("/loadPage")
    public String loadPage(Model model) {
        List<OwnPitch> ownPitchList=ownPitchRepository.findAll(); //hiển thị sân

        return "index.html";
    }
    @GetMapping("/listPitch")
    public String listPitch(Model model) {
        List<OwnPitch> ownPitchList=ownPitchRepository.findAll(); //hiển thị sân

        return "redirect:/manager";
    }

}