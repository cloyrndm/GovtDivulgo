package com.example.demo.Controller;

import com.example.demo.Entity.GovtUser;
import com.example.demo.Service.GovtUserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

//import com.example.demo.Service.GovtUserService;

/**
 * Created by Cloie Andrea on 01/10/2018.
 */
@Controller
public class GovtUserController {

@Autowired
GovtUserService govtUserService;

    @RequestMapping("/")
    private String goHome(HttpSession session){

        if(session.isNew()){
            return "login";
        }
        if(session.getAttribute("type")=="PAG"){
            System.out.println(session.getAttribute("type"));
            return "homepage";
        }
        if(session.getAttribute("type")=="LRA"){
            System.out.println(session.getAttribute("type"));
            return "homepage";
        }
        if(session.getAttribute("type")=="LTO"){
            System.out.println(session.getAttribute("type"));
            return "homepage";
        }
        if(session.getAttribute("type")=="SSS")
            System.out.println(session.getAttribute("type"));
            return "homepage";

    }

    @RequestMapping("/logout")
    private String logout(){
        return "login";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, Model model, HttpSession session){

//        List<Complaints> complaints = complaintService.findAll();
        System.out.println("------I GOT INSIDE THE LOGIN CONTROLLER-------");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        session.setAttribute("username",username);
        session.setAttribute("password",password);

        GovtUser user = govtUserService.findByUsernameAndPassword(username,password);
        if(user==null){
            System.out.println("--------USER NOT FOUND-------");
            return "login";
        }

        if (user.getType().equals("PAG") ) {
//            model.addAttribute("complaints",complaints);
            model.addAttribute("img","image/love.png");
//            session.setAttribute("img",img1);
            session.setAttribute("type",user.getType());
            return "homepage";
        }
        if (user.getType().equals("LRA")) {
//            model.addAttribute("complaints",complaints);
            model.addAttribute("img","image/lra.png");
//            session.setAttribute("img",img2);
            session.setAttribute("type",user.getType());
            return "homepage";
        }
        if (user.getType().equals("LTO")) {
//            model.addAttribute("complaints",complaints);
            model.addAttribute("img","image/lto.png");
//            session.setAttribute("img",img3);
            session.setAttribute("type",user.getType());
            return "homepage";
        }
        if (user.getType().equals("SSS")) {
//            model.addAttribute("complaints",complaints);
            model.addAttribute("img","image/sss.png");
//            session.setAttribute("img",img4);
            session.setAttribute("type",user.getType());
            return "homepage";
        }


        return null;
    }


}
