package com.example.fastsoccer.controller;

import com.example.fastsoccer.entity.Booking;
import com.example.fastsoccer.entity.Yard;
import com.example.fastsoccer.repository.BookingService;
import com.example.fastsoccer.repository.YardRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.util.List;

import static org.thymeleaf.util.StringUtils.substring;

@Controller
@RequestMapping
public class RestController {
    @Autowired
    YardRepository yardRepository;
    /*
     * @author: HieuMM
     * @since: 06-Jul-22 1:48 PM
     * @description-VN:  Truyển vào ngày và id khung giờ của sân đã đặt, trả về thông tin booking của sân đó
     * @description-EN:
     * @param:
     * */
    @Autowired
    BookingService bookingService;

    @GetMapping("/yard/getTime/{id}")
    public ResponseEntity<?> getTime(@PathVariable("id") Long id) {
        Yard yard1 = yardRepository.findById(id).get();
        if (yard1 != null) {

            return ResponseEntity.ok(yard1);
        } else {
            return (ResponseEntity<?>) ResponseEntity.status(4000);
        }

    }
    /*
    * @author: HieuMM
    * @since: 07-Jul-22 3:24 PM
    * @description-VN:  Danh sách khung giờ đã được đặt  trong ngày theo id sân
    * @description-EN:
    * @param:
    * */

    @GetMapping("/getBooking/{fromDate}/{id}")
    public ResponseEntity<?> getBooking(@PathVariable(value = "fromDate") Date fromDate, @PathVariable(value = "id") Long id1) {
        List<Booking> booking = bookingService.findAllPriceYardIsBooking(fromDate, id1);

            return ResponseEntity.ok().body(booking);

    }

    @GetMapping(value = "/sendSMS/{phone}")
    public ResponseEntity<String> sendSMS(@PathVariable(value = "phone") String phone) {
        Twilio.init("ACb451dd21c4c07f810dd8d7d3351678bf", "bb6c8342627a5f6602ea99c6e476bd86");
        String truePhone = substring(phone, 1);
        Message.creator(new PhoneNumber("+84" + truePhone),
                new PhoneNumber("+14845099386"), "Fast soccer chúc mừng bạn đăng kí thành công 📞").create();

        return new ResponseEntity<String>("Message sent successfully", HttpStatus.OK);
    }

}
