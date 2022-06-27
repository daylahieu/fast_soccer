package com.example.fastsoccer.controller;

import com.example.fastsoccer.entity.PriceYard;
import com.example.fastsoccer.entity.Yard;
import com.example.fastsoccer.repository.YardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping
public class RestController {
    @Autowired
    YardRepository yardRepository;
    @GetMapping("/yard/getTime/{id}")
    public ResponseEntity<?> getTime(@PathVariable("id") Long id){
        Yard yard1 = yardRepository.findById(id).get();
        if (yard1 !=null){

            return  ResponseEntity.ok(yard1);
        }else {
            return (ResponseEntity<?>) ResponseEntity.status(4000);
        }

    }
}
