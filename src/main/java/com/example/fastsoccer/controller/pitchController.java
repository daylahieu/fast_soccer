package com.example.fastsoccer.controller;

import com.example.fastsoccer.entity.OwnPitch;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class pitchController {
    @GetMapping("/pitch")
    public String pitch(Model model) {

        return "pitchDetail.html";
    }
}
