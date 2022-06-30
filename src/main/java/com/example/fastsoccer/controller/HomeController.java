package com.example.fastsoccer.controller;

import com.example.fastsoccer.config.Config;
import com.example.fastsoccer.entity.*;
import com.example.fastsoccer.repository.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

//trang home
@Controller
public class HomeController {
    @Autowired
    OwnPitchRepository ownPitchRepository;
    @Autowired
    DistricRepository districRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookingService bookingService;

    @GetMapping("/loadPage")
    public String loadPage(Model model) {
        List<OwnPitch> ownPitchListOk = ownPitchRepository.findOwnPitchSuccess(); //hiển thị sân đã xác nhận
        model.addAttribute("ownPitchListOk", ownPitchListOk);
        return "index";
    }

    @GetMapping("/loadPageAfterLogin")
    public String loadPageAfterLogin(Model model, HttpSession session) {
        /*List<OwnPitch> ownPitchListOk = ownPitchRepository.findOwnPitchSuccess(); //hiển thị sân đã xác nhận
        model.addAttribute("ownPitchListOk", ownPitchListOk);
        List<District> districtList = districRepository.findAll();
        model.addAttribute("districtList", districtList);
        UserEntity userEntity = (UserEntity) session.getAttribute("user");
        model.addAttribute("user", userEntity);
        int countUser = userRepository.countReview();
        model.addAttribute("countUser", countUser);*/
        return "redirect:/loadPage";
    }

    @GetMapping( "/loadFormLogin")
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

    @GetMapping( "/logout-success")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
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
        OwnPitch ownPitch = ownPitchRepository.findById(id).get();
        model.addAttribute("ownPitch", ownPitch);
        List<Yard> yardList = yardRepository.findAllByOwnPitchId(id);
        model.addAttribute("yardList", yardList);
        List<PriceYard> priceYardList = priceYardRepository.findAllByYardId_OwnPitch_Id(id);
        model.addAttribute("priceYardList", priceYardList);
        // mav.addObject("ownPitch", ownPitch);
        return "pitchDetail1";
    }


    @GetMapping("/loadbyyard")
    public String loadbyyard(Model model, @RequestParam("id") Long id) {
        List<PriceYard> priceYardList = priceYardRepository.findAllByYardId(id);
        model.addAttribute("priceYardList", priceYardList);
        return "pitchDetail1";
    }

    @PostMapping("/booking")
    public String bookingssss(@ModelAttribute("obj") Booking booking, HttpSession session) throws UnsupportedEncodingException {
        PriceYard priceYard = priceYardRepository.findById(booking.getId()).get();
        booking.setId(null);
        booking.setPriceYardID(priceYard);
        booking.setStatus(false);
      /*  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();*/
        UserEntity userEntity = (UserEntity) session.getAttribute("user");
        booking.setUserId(userEntity);
        booking = bookingService.save(booking);
        if (userEntity.getBookingList() != null) {
            userEntity.getBookingList().add(booking);
        } else {
            userEntity.setBookingList(new ArrayList<Booking>());
            userEntity.getBookingList().add(booking);
        }
        userEntity.getBookingList().add(booking);

        userRepository.save(userEntity);
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_OrderInfo = booking.getUserId().getUsername();
        String orderType = "100000";
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";
        String vnp_TmnCode = Config.vnp_TmnCode;

        int amount = (int) (booking.getPriceYardID().getPrice() * 100);
        Map vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        String bank_code = "NCB";
        if (bank_code != null && !bank_code.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bank_code);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = "vn";
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl+booking.getId());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        //Add Params of 2.1.0 Version
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        //Billing
        vnp_Params.put("vnp_Bill_Mobile", "09123891");
        vnp_Params.put("vnp_Bill_Email", "huuquangnh@gmail.com");
        String fullName = "Quang dz".trim();
        if (fullName != null && !fullName.isEmpty()) {
            int idx = fullName.indexOf(' ');
            String firstName = fullName.substring(0, idx);
            String lastName = fullName.substring(fullName.lastIndexOf(' ') + 1);
            vnp_Params.put("vnp_Bill_FirstName", firstName);
            vnp_Params.put("vnp_Bill_LastName", lastName);

        }
        vnp_Params.put("vnp_Bill_Address", "Test thui");
        vnp_Params.put("vnp_Bill_City", "Ha Noi");
        vnp_Params.put("vnp_Bill_Country", "Viet Nam");
//        if (req.getParameter("txt_bill_state") != null && !req.getParameter("txt_bill_state").isEmpty()) {
//            vnp_Params.put("vnp_Bill_State", req.getParameter("txt_bill_state"));
//        }
        // Invoice
        vnp_Params.put("vnp_Inv_Phone", "0963089510");
        vnp_Params.put("vnp_Inv_Email", "coolquanghuu@gmail.com");
        vnp_Params.put("vnp_Inv_Customer","nguyen huu quang");
        vnp_Params.put("vnp_Inv_Address", "Ha Noi");
        vnp_Params.put("vnp_Inv_Company", "CY");
        vnp_Params.put("vnp_Inv_Taxcode", "32222");
//        vnp_Params.put("vnp_Inv_Type", req.getParameter("cbo_inv_type"));
        //Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
        com.google.gson.JsonObject job = new JsonObject();
        job.addProperty("code", "00");
        job.addProperty("message", "success");
        job.addProperty("data", paymentUrl);
        Gson gson = new Gson();
        return  "redirect:"+paymentUrl;
    }

    @GetMapping( "/myBooking")
    public String myBooking(HttpSession session, Model model) {
        UserEntity userEntity = (UserEntity) session.getAttribute("user");
        if (userEntity == null) {
            return "redirect:/login";
        }else {
            List<Booking> bookingList = bookingService.findAllByUserId(userEntity);
            model.addAttribute("bookingList", bookingList);
            return "myBooking";
        }
    }

    @GetMapping("/user/pay")
    public String payvn(@RequestParam("id") Long id,Model model) {
        Booking booking = bookingService.findById(id).get();
        booking.setStatus(true);
        bookingService.save(booking);
        //hien thi file html
        model.addAttribute("booking", booking);
        return "bookingsuccess";
    }
}

