package com.msmgym.application.controllers;

import com.msmgym.application.beans.Email;
import com.msmgym.application.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/mail")
public class MailController {

    @Autowired
    EmailService emailService;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    private void sendEmail(@RequestBody Email email){
        emailService.sendEmail(email);
    }

}
