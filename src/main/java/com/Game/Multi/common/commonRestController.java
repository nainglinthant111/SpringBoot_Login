package com.Game.Multi.common;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.Game.Multi.model.User;
import com.Game.Multi.reporisity.UserRepo;
import com.Game.Multi.service.EmailServices;
import com.Game.Multi.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class commonRestController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private EmailServices emailService;

    @Autowired
    private UserService userService;

    // @Autowired
    // private S3Service s3Service;

    @RequestMapping(value = "/confirmAlready/{email}", method = RequestMethod.GET)
    public String getBook(@PathVariable String email) {
        Optional<User> userOptional = userRepo.findByEmail(email);
        if (userOptional.isPresent()) {
            // User is present, get the user
            return "NG";
        } else {
            return "OK";
        }

    }

    @GetMapping("/send")
    @ResponseBody
    public String sendEmail(@RequestParam String email, HttpServletRequest request, User user) {
        int length = 8;
        String password = UserService.generatePassword(length);
        String hashedPassword = userService.decodePass(password);
        user.setEmail(email);
        user.setPassword(hashedPassword);
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String subject = "Account Create is Successful";
        String body = "Hello " + email + " , \nYour login this Url : " + url
                + "/login?email=" + email + "&password= ,\n Passwors : " + password
                + "\n and fill your information.\nThink You!";
        if (emailService.sendEmail(email, subject, body) == "OK") {
            userService.saveUser(user);
            return "OK";
        } else {
            return "NG";
        }
    }

    //  @PostMapping("/upload")
    // public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
    //     try {
    //         String url = s3Service.uploadFile(file);
    //         return ResponseEntity.ok(url);
    //     } catch (IOException e) {
    //         return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
    //     }
    // }

}
