package com.salas.controller;

import com.mitocode.model.ResetMail;
import com.mitocode.model.User;
import com.mitocode.service.IResetMailService;
import com.mitocode.service.IUserService;
import com.mitocode.util.EmailUtil;
import com.mitocode.util.Mail;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final EmailUtil emailUtil;
    private final IResetMailService resetMailService;
    private final IUserService userService;

    @PostMapping("/sendMail")
    public ResponseEntity<Integer> sendMail(@RequestBody String username) throws MessagingException {
        int result = 0;
        final int EXPIRATION_TIME = 10;

        User us = userService.findOneByUsername(username);
        if(us != null && us.getIdUser() > 0){
            ResetMail resetMail = new ResetMail();
            resetMail.setRandom(UUID.randomUUID().toString());
            resetMail.setUser(us);
            resetMail.setExpiration(EXPIRATION_TIME);
            resetMailService.save(resetMail);

            Mail mail = new Mail();
            mail.setFrom("email.prueba.demo@gmail.com");
            mail.setTo(us.getUsername());
            mail.setSubject("RESET PASSWORD MEDIAPP");

            Map<String, Object> model = new HashMap<>();
            String url = "http://localhost:4200/forgot/" + resetMail.getRandom();
            model.put("resetUrl", url);
            model.put("username", resetMail.getUser().getUsername());
            mail.setModel(model);

            emailUtil.sendMail(mail);
            result = 1;
        }

        return ResponseEntity.ok(result);
    }


    @GetMapping("/reset/check/{random}")
    public ResponseEntity<Integer> checkRandom(@PathVariable String random){
        int result = 0;

        ResetMail resetMail = resetMailService.findByRandom(random);
        if(resetMail != null && !resetMail.isExpired()){
            result = 1;
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/reset/{random}")
    public ResponseEntity<Integer> resetPassword(@PathVariable String random, @RequestBody String password){
        int result = 0;
        ResetMail rm = resetMailService.findByRandom(random);
        if(rm != null && rm.getId() > 0){
            if(!rm.isExpired()){
                userService.changePassword(rm.getUser().getUsername(), password);
                resetMailService.delete(rm);
                result = 1;
            }
        }

        return ResponseEntity.ok(result);
    }
}
