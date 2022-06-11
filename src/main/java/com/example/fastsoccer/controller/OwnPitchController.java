package com.example.fastsoccer.controller;

import com.example.fastsoccer.entity.District;
import com.example.fastsoccer.entity.OwnPitch;

import com.example.fastsoccer.entity.Yard;
import com.example.fastsoccer.repository.DistricRepository;
import com.example.fastsoccer.repository.OwnPitchRepository;
import com.example.fastsoccer.repository.YardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
//các chức năng cho chủ sân bóng
@Controller
@MultipartConfig
/*@RequestMapping("/own")*/
public class OwnPitchController {
    @Autowired
    OwnPitchRepository ownPitchRepository;

    @Autowired
    DistricRepository districRepository;

    @GetMapping("/showformRegisterPitch")
    public String loadDistrict(Model model) {
        List<District> districtList=districRepository.findAll();
        model.addAttribute("districtList", districtList);
        return "registerPitch";
    }

//đăng kí sân
    @Value("${config.upload_folder}")
String UPLOAD_FOLDER;
    @PostMapping("/registerPitch")
    public String addPro(@ModelAttribute("obj") OwnPitch ownPitch,
                         @RequestParam("pic1") MultipartFile file1,
                         @RequestParam("pic2") MultipartFile file2,
                         @RequestParam("pic3") MultipartFile file3) {
        String relativeFilePath1 = null;
        String relativeFilePath2 = null;
        String relativeFilePath3 = null;
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        int day = localDate.getDayOfMonth();
        String subFolder = day + "_" + year + "/";
        String fullUploadDir = UPLOAD_FOLDER + subFolder;
        File checkDir = new File(fullUploadDir);
        if (!checkDir.exists() || checkDir.isFile()) {
            checkDir.mkdir();

        }
        try {
            relativeFilePath1 = subFolder + Instant.now().getEpochSecond() + file1.getOriginalFilename();
            Files.write(Paths.get(UPLOAD_FOLDER + relativeFilePath1), file1.getBytes());
            relativeFilePath2 = subFolder + Instant.now().getEpochSecond() + file2.getOriginalFilename();
            Files.write(Paths.get(UPLOAD_FOLDER + relativeFilePath2), file2.getBytes());
            relativeFilePath3 = subFolder + Instant.now().getEpochSecond() + file3.getOriginalFilename();
            Files.write(Paths.get(UPLOAD_FOLDER + relativeFilePath3), file3.getBytes());
            ownPitch.setImg1(relativeFilePath1);
            ownPitch.setImg2(relativeFilePath2);
            ownPitch.setImg3(relativeFilePath3);
        } catch (IOException e) {
            System.out.println("ko upload duoc");
            e.printStackTrace();
        }

        ownPitchRepository.save(ownPitch);
        return "thankyou.html";
    }
    @PostMapping("/updateStatus")
    public String updateStatus(@ModelAttribute("obj") OwnPitch ownPitch) {

        ownPitchRepository.save(ownPitch);
        return "index.html";
    }

@Autowired
    YardRepository yardRepository;
    //trang thông tin chủ sân sau khi login
    @GetMapping("/homeOwn")
    public String homeOwn(Model model,@RequestParam("phone") Long phone) {
        OwnPitch ownPitch = ownPitchRepository.findAllByPhone(String.valueOf(phone));
        Long id=ownPitch.getId().longValue();
        List<Yard> yardList=yardRepository.findAllByOwnPitchId(id);
        model.addAttribute("yardList", yardList);
        return "homeOwn";
    }
}
