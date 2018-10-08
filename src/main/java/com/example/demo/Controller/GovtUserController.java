package com.example.demo.Controller;

import com.example.demo.Entity.Complaint;
import com.example.demo.Entity.ComplaintReply;
import com.example.demo.Entity.GovtUser;
import com.example.demo.Entity.User;
import com.example.demo.Service.ComplaintReplyService;
import com.example.demo.Service.ComplaintService;
import com.example.demo.Service.GovtUserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;


/**
 * Created by Cloie Andrea on 01/10/2018.
 */
@Controller
public class GovtUserController {

     @Autowired
     GovtUserService govtUserService;

    @Autowired
    ComplaintReplyService complaintReplyService;

    @Autowired
    ComplaintService complaintService;

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

    @RequestMapping("/homepage")
    private String homepage(Model model,ModelMap map,HttpSession session){
        String type = (String)session.getAttribute("type");
        List<Complaint> complaint = govtUserService.findByGovtAgencyAndStatus(type,null);
//        System.out.println(complaint);
//      --------------------------------------------------------
        if(type.equals("PAG")){
            model.addAttribute("complaint",complaint);
            map.addAttribute("agency","PAG");
            map.addAttribute("img","/images/love.png");
            return "homepage";
        }

        if(type.equals("LRA")){
            model.addAttribute("complaint",complaint);
            map.addAttribute("agency","LRA");
            map.addAttribute("img","/images/love.png");
            return "homepage";
        }
        if(type.equals("LTO")){
            model.addAttribute("complaint",complaint);
            map.addAttribute("agency","LTO");
            map.addAttribute("img","/images/love.png");
            return "homepage";
        }
        if(type.equals("SSS"))
            model.addAttribute("complaint",complaint);
            map.addAttribute("agency","SSS");
            map.addAttribute("img","/images/love.png");
        return "homepage";
    }

    @RequestMapping("/complaint")
    private String viewcomplaints(HttpSession session,Model model,ModelMap map){
        String type = (String)session.getAttribute("type");
        Long userid = (Long)session.getAttribute("userid");
        List<ComplaintReply> complaintReply = complaintReplyService.findByAgencyAndUserid(type,userid);

        if(type.equals("PAG")){
            model.addAttribute("viewcomplaint",complaintReply);
            map.addAttribute("img","/images/love.png");
            return "viewcomplaints";
        }

        if(type.equals("LRA")){
            model.addAttribute("viewcomplaint",complaintReply);
            map.addAttribute("img","/images/love.png");
            return "viewcomplaints";
        }
        if(type.equals("LTO")){
            model.addAttribute("viewcomplaint",complaintReply);
            map.addAttribute("img","/images/love.png");
            return "viewcomplaints";
        }
        if(type.equals("SSS")) {
            model.addAttribute("viewcomplaint", complaintReply);
            map.addAttribute("img", "/images/love.png");
            return "viewcomplaints";
        }
        return null;
    }

    private void sendEmail(String to,String reply,String subj) throws UnknownHostException {

//        final String APIKey = "ad1cbf3ebf8ee591bb9d249d0a0c30e6";
//        final String SecretKey = "b7f1d90c92060ffa50d93d9ab31566cc";
//
        String from = "divulgo.kc@gmail.com";
//
//        Properties props = new Properties ();
//
//        props.put ("mail.smtp.host", "in.mailjet.com");
//        props.put ("mail.smtp.socketFactory.port", "465");
//        props.put ("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        props.put ("mail.smtp.auth", "true");
//        props.put ("mail.smtp.port", "465");
//
//        Session session = Session.getDefaultInstance (props,
//                new javax.mail.Authenticator ()
//                {
//                    protected PasswordAuthentication getPasswordAuthentication ()
//                    {
//                        return new PasswordAuthentication (APIKey, SecretKey);
//                    }
//                });
//
//        try
//        {
//
//            Message message = new MimeMessage(session);
//            message.setFrom (new InternetAddress(from));
//            message.setRecipients (Message.RecipientType.TO, InternetAddress.parse(to));
//            message.setSubject (subj);
//            message.setText (reply);
//
//            Transport.send (message);
//
//        }
//        catch (MessagingException e)
//        {
//            throw new RuntimeException (e);
//        }

//        String to = "sonoojaiswal1988@gmail.com";//change accordingly
//        String from = "sonoojaiswal1987@gmail.com";change accordingly
        InetAddress inetAddress = InetAddress.getLocalHost();
        String host =  inetAddress.getHostAddress();//or IP address
        System.out.println(host);

        //Get the session object
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties);

        //compose the message
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject(subj);
            message.setText(reply);

            // Send message
            Transport.send(message);
            System.out.println("message sent successfully....");

        }catch (MessagingException mex) {mex.printStackTrace();}

    }


    @RequestMapping("/reply")
    private String reply(HttpServletRequest request,HttpSession session, Model model,ModelMap map,ComplaintReply complaintReply) throws UnknownHostException {
//      --------------------get from html-----------------------
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss");

        String complaintId = request.getParameter("complaintid");
        String agency = request.getParameter("complaintagency");
        Long userid = (Long)session.getAttribute("userid");
        String reply = request.getParameter("replyy");
        String type = (String)session.getAttribute("type");
        String dateee = formatter.format(date);
        String time = formatter2.format(date);
        String status = null;
//      --------------------save to database---------------------
        complaintReply.setComplaintId(Long.parseLong(complaintId));
        complaintReply.setComplaintReply(reply);
        complaintReply.setDate(dateee);
        complaintReply.setTime(time);
        complaintReply.setAgency(agency);
        complaintReply.setUserid(userid);
        complaintReplyService.save(complaintReply);
//      --------------------------check data----------------------
        System.out.println("-----------COMPLAINT REPLY DETAILS-----------");
        System.out.println("Complaint id: "+complaintId);
        System.out.println("Reply: "+reply);
        System.out.println("Date: "+dateee);
        System.out.println("Time "+time);
        System.out.println("Type: "+type);


        Complaint complaint1 = govtUserService.findByComplaintId(Long.parseLong(complaintId));
        complaint1.setStatus("1");
        complaintService.save(complaint1);
        //      --------------------------getemail----------------------
        User user = govtUserService.findByUserId(complaint1.getUserId());
        List<Complaint> complaint = govtUserService.findByGovtAgencyAndStatus(type,status);
        System.out.println(complaint);
        if(type.equals("PAG")){
            model.addAttribute("complaint",complaint);
            map.addAttribute("agency","PAG");
            map.addAttribute("img","/images/love.png");
            sendEmail(user.getEmail(),complaintReply.getComplaintReply(),"Divulgo: PAG-IBIG's feedback reply");
            return "homepage";
        }

        if(type.equals("LRA")){
            model.addAttribute("complaint",complaint);
            map.addAttribute("agency","LRA");
            map.addAttribute("img","/images/love.png");
            sendEmail(user.getEmail(),complaintReply.getComplaintReply(),"Divulgo: LRA's feedback reply");
            return "homepage";
        }
        if(type.equals("LTO")){
            model.addAttribute("complaint",complaint);
            map.addAttribute("agency","LTO");
            map.addAttribute("img","/images/love.png");
            sendEmail(user.getEmail(),complaintReply.getComplaintReply(),"Divulgo: LTO's feedback reply");
            return "homepage";
        }
        if(type.equals("SSS"))
            model.addAttribute("complaint",complaint);
            map.addAttribute("agency","SSS");
            map.addAttribute("img","/images/love.png");
            sendEmail(user.getEmail(),complaintReply.getComplaintReply(),"Divulgo: SSS's feedback reply");
            return "homepage";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, Model model, HttpSession session,ModelMap map){


        String type;
        System.out.println("------I GOT INSIDE THE LOGIN CONTROLLER-------");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        session.setAttribute("username",username);
        session.setAttribute("password",password);

        GovtUser user = govtUserService.findByUsernameAndPassword(username,password);
        type=user.getType();

        String status = null;


        List<Complaint> complaint = govtUserService.findByGovtAgencyAndStatus(type,status);

        if(user==null){
            System.out.println("--------USER NOT FOUND-------");
            return "login";
        }
        if (user.getType().equals("PAG")) {
            model.addAttribute("complaint",complaint);
            map.addAttribute("agency","PAG");
            map.addAttribute("img","/images/love.png");
            session.setAttribute("type",user.getType());
            session.setAttribute("stat",status);
            session.setAttribute("userid",user.getId());
//            session.setAttribute("complaint",complaint);
            return "homepage";
        }
        if (user.getType().equals("LRA")) {
            model.addAttribute("complaint",complaint);
            map.addAttribute("agency","LRA");
            map.addAttribute("img","/images/lra.png");
            session.setAttribute("type",user.getType());
            session.setAttribute("stat",status);
            session.setAttribute("userid",user.getId());
            return "homepage";
        }
        if (user.getType().equals("LTO")) {
          model.addAttribute("complaint",complaint);
            map.addAttribute("img","/images/lto.png");
            map.addAttribute("agency","LTO");
            session.setAttribute("type",user.getType());
            session.setAttribute("stat",status);
            session.setAttribute("userid",user.getId());
            return "homepage";
        }
        if (user.getType().equals("SSS")) {
            model.addAttribute("complaint",complaint);
            map.addAttribute("img","/images/sss.png");
            map.addAttribute("agency","SSS");
            session.setAttribute("type",user.getType());
            session.setAttribute("stat",status);
            session.setAttribute("userid",user.getId());
            return "homepage";
        }


        return null;
    }


}
