package com.app.Controller;

import com.app.Entity.DTO.accountLoginRequest;
import com.app.Entity.account;
import com.app.Entity.book;
import com.app.Entity.category;
import com.app.service.Impl.EmailService;
import com.app.service.bookService;
import com.app.service.categoryService;
import com.app.service.accountService;
import com.app.utils.CookieService;
import com.app.utils.SessionService;
import jakarta.mail.MessagingException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    accountService accountService;
    @Autowired
    categoryService categoryService;
    @Autowired
    bookService bookService;
    @Autowired
    CookieService cookieService;
    @Autowired
    SessionService sessionService;
    @Autowired
    EmailService emailService;

    @GetMapping("/login")
    public String doLogin(Model model){
        String username = cookieService.getValue("username");
        if(username!= null){
         String password = cookieService.getValue("password");
         Boolean remember = Boolean.valueOf(cookieService.getValue("remember"));
         accountLoginRequest acc = new accountLoginRequest(username,password,remember);
            model.addAttribute("accountLoginDTO", acc);
            model.addAttribute("pswCookie",acc.getPassword());
        }else {
            model.addAttribute("accountLoginDTO", new accountLoginRequest());
        }

        List<category> navCartegory = categoryService.findAll();
        model.addAttribute("navCategory",navCartegory);
        return "login";
    }

    @PostMapping("/login")
    public String PostLogin(Model model
            , @ModelAttribute("accountLoginDTO") accountLoginRequest accountLoginRequest,@RequestParam("password")String pwd){
        System.out.println("accrequest: "+ accountLoginRequest.toString());
        Optional<account> accLogin = accountService.findByUsernameAndPassword(accountLoginRequest.getUsername(),pwd);
        System.out.println("acc: "+ accLogin.toString());
        if(!accLogin.isPresent()){
            model.addAttribute("errorMessage","Tài Khoản mật khẩu không chính xác");
        }else{
            if(accountLoginRequest.isRemember()){
                cookieService.add("username",accLogin.get().getUsername(),1);
                cookieService.add("password",accLogin.get().getPassword(),1);
                cookieService.add("remember", String.valueOf(true),1);
                sessionService.set("username",accLogin.get().getUsername());
                sessionService.set("accountId",accLogin.get().getId());
                model.addAttribute("successMessage","Login Thành công !");
            }else{
                cookieService.remove("username");
                cookieService.remove("password");
                cookieService.remove("remember");
                sessionService.set("username",accLogin.get().getUsername());
                sessionService.set("accountId",accLogin.get().getId());
                    model.addAttribute("successMessage","Login Thành công !");
            }
        }

        List<category> navCartegory = categoryService.findAll();
        model.addAttribute("navCategory",navCartegory);
        return "login";
    }
    @GetMapping("/register")
    public  String doRegister(Model model ){
        model.addAttribute("accountRegister",new account());

        List<category> navCartegory = categoryService.findAll();
        model.addAttribute("navCategory",navCartegory);
        return "register";
    }

    @PostMapping("/register")
    public  String doRegistered(Model model ,@ModelAttribute("accountRegister") account accountRegister
                        ,@RequestParam("password__confirm")String repeatPass) throws ParseException {
        List<category> navCartegory = categoryService.findAll();
        Optional<account> accountCheck = accountService.findByEmail(accountRegister.getEmail());
        Optional<account> accountCheckUsername = accountService.findByUsername(accountRegister.getUsername());
        System.out.println("account: "+accountRegister.toString());
        System.out.println("password__confirm: "+repeatPass);
        if(repeatPass.equals(accountRegister.getPassword()) && !accountCheck.isPresent() && !accountCheckUsername.isPresent()){
            accountService.save(accountRegister);
            model.addAttribute("successMessage","Register sucesss");

            model.addAttribute("navCategory",navCartegory);
            return "register";
        }else {
         if (!repeatPass.equals(accountRegister.getPassword())){
             model.addAttribute("errorMess","pass no match");
             model.addAttribute("accountRegister",accountRegister);


             model.addAttribute("navCategory",navCartegory);
             return "register";
         }
         if(accountCheck.isPresent()){
             model.addAttribute("errorMess","email is exits");
             model.addAttribute("accountRegister",accountRegister);

             model.addAttribute("navCategory",navCartegory);
             return "register";
         }
            if(accountCheckUsername.isPresent()){
                model.addAttribute("errorMess","username is exits");
                model.addAttribute("accountRegister",accountRegister);

                model.addAttribute("navCategory",navCartegory);
                return "register";
            }
        }
        model.addAttribute("accountRegister",new account());


        model.addAttribute("navCategory",navCartegory);
        return "register";
    }

    @GetMapping("/logout")
    public String doLogout(Model model){
        sessionService.remove("username");
        sessionService.set("accountId",0);
        return "redirect:/account/login";
    }

    @GetMapping("/forgot")
    public String doForgot(Model model){


        return "forgot";
    }
    @PostMapping("/forgot")
    public String doPostForgot(Model model,@RequestParam("email")String email) throws MessagingException {
        Optional<account> checkemail =  accountService.findByEmail(email);
        if(checkemail.isPresent())
        {
            checkemail.get().setPassword(generateRandomString(10));
            accountService.save(checkemail.get());
            emailService.sendEmail(email,"Quên mật khẩu !!! ","Mật khẩu mới của bạn là:"+checkemail.get().getPassword(),null);

            sessionService.set("username",checkemail.get().getUsername());
            sessionService.set("accountId",checkemail.get().getId());
            model.addAttribute("success","Chúng tôi đã gửi mật khẩu mới về email của bạn. ");
            return "forgot";
        }
        else
        {
            model.addAttribute("error","Không tìm thấy email của bạn ");
        }
        return "forgot";
    }

    @GetMapping("/changePassword")
    public String doGetChange(Model model ) {
        Optional<account> accountChange = accountService.findById(sessionService.get("accountId"));

        return "changePass";
    }
    @PostMapping("/changePassword")
    public String doPostChange(Model model,@RequestParam("psw")String password, @RequestParam("pswn")String passwordNew,
                              @RequestParam("rppswn")String repeatPassword ) {
        Optional<account> accountChange = accountService.findById(sessionService.get("accountId"));
        if (password.equals(accountChange.get().getPassword()))
        {
            if(passwordNew.equals(repeatPassword))
            {
                accountChange.get().setPassword(passwordNew);
                accountService.save(accountChange.get());
                model.addAttribute("success","đổi mật khẩu thành công");
                return "changePass";
            }else
            {
                model.addAttribute("error","Mật không khớp ");
            }
        } else
        {
            model.addAttribute("error","Mật khẩu sai ");
        }
        return "changePass";
    }
    public  String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder(length);
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }
}
