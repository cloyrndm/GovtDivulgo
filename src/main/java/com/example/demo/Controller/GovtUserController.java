package com.example.demo.Controller;

import com.example.demo.Entity.*;
import com.example.demo.Service.*;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tartarus.snowball.ext.PorterStemmer;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    @Autowired
    ArticleService articleService;

    @Autowired
    NgramService ngramService;

    @Autowired
    FrequencyService freService;

    @Autowired
    TfidfService tfidfService;

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
        List<Complaint> complaint = govtUserService.findByAgencyAndStatus(type,null);
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

    @RequestMapping("/viewcomplaints")
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
            map.addAttribute("img","/images/lra.png");
            return "viewcomplaints";
        }
        if(type.equals("LTO")){
            model.addAttribute("viewcomplaint",complaintReply);
            map.addAttribute("img","/images/lto.png");
            return "viewcomplaints";
        }
        if(type.equals("SSS")) {
            model.addAttribute("viewcomplaint", complaintReply);
            map.addAttribute("img", "/images/sss.png");
            return "viewcomplaints";
        }
        return "homepage";
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

//      String to = "sonoojaiswal1988@gmail.com";//change accordingly
//      String from = "sonoojaiswal1987@gmail.com";change accordingly
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

    @RequestMapping("/correction")
    private void correction(HttpServletRequest request,HttpSession session, Model model,ModelMap map,ComplaintReply complaintReply) throws IOException {
        String complaint = request.getParameter("complaint");
        String agency = request.getParameter("agency");

        Article article = new Article();
        article.setContent(complaint);
        article.setAgency(agency);

        cleanContent(complaint);
    }

    @PostMapping("/cleanContent")
    public String cleanContent(String content) throws IOException {

        System.out.println("done scrape");
        File file = new File("C:\\Users\\Cloie Andrea\\IdeaProjects\\GovtDivulgo\\stopwords.txt");
//        Set<String> stopWords = new LinkedHashSet<String>();
        ArrayList<String> stopWords = new ArrayList<String>();
        List<String> ngrams = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader(file));
//        String regex = "\\d+";
        int wc=0, tempWC=0;

//        String[] words =content.replaceAll("[^a-zA-Z]", "").split("\\s+");
        String[] words =content.replaceAll("[^a-zA-Z ]", "").split("\\s+");
        String regex = "[A-Z]+";
        Pattern r = Pattern.compile(regex);


        for(String line;(line = br.readLine()) != null;)
            stopWords.add(line.trim());
        br.close();

        ArrayList<String> wordsList = new ArrayList<String>();
        ArrayList<String> stemList = new ArrayList<String>();
        for (String word : words) {

            wordsList.add(word);
        }
        System.out.println("After for loop:  " + wordsList);

//        for(int i = 0; i < wordsList.size(); i++) {
//            Matcher m = r.matcher(wordsList.get(i));
//            if (m.find()) {
////                System.out.println(wordsList.get(i));
//                wordsList.remove(i);
//                minus.add(wordsList.get(i));
//                System.out.println("removed capital");
//
//            }
//
////        for(int i = 0; i < wordsList.size(); i++) {
//            else if (stopWords.contains(wordsList.get(i))) {
//                wordsList.remove(i);
//                minus.add(wordsList.get(i));
//                System.out.println("remove stop");
//
//            }
//        }
//
//        wordsList.removeAll(minus);

        for (String a:wordsList){
            PorterStemmer stemmer = new PorterStemmer();
            stemmer.setCurrent(a);
            stemmer.stem();
            String steem=stemmer.getCurrent();
            stemList.add(steem);
            System.out.println("stemmer: "+ steem);
        }
        System.out.println("DONE STEMMING");

        Article sampleContent = articleService.findByContent(content);
        int articleid = sampleContent.getArticleId();
        for (String bag:stemList){

            Ngram sampleWord = ngramService.findByWords(bag);
            if (sampleWord != null) {
//                int id = sampleWord.getArticleId();
//                wc=sampleWord.getWordCount();
            }
            else {
                Ngram ngram = new Ngram();
//                ngram.setArticleId(articleid);
                ngram.setWords(bag);
                ngram.setWordCount(wc);
                ngramService.save(ngram);

            }

        }

        System.out.println("DONE SAVING STEM WORDS");
        Set<String> unique = new HashSet<String>(stemList);

        for (String key : unique) {
            Ngram sampleWords = ngramService.findByWords(key);

            int wordsid = sampleWords.getNgramId();
//            int artid = sampleWords.getArticleId();

            if (sampleWords != null) {
                tempWC = sampleWords.getWordCount();
                System.out.println("temp count:" + tempWC);
                wc = tempWC + Collections.frequency(stemList, key);
                sampleWords.setWordCount(wc);
                ngramService.save(sampleWords);
                Frequency fre = new Frequency();
                fre.setFrequency(Collections.frequency(stemList, key));
                fre.setNgramId(wordsid);
                fre.setArtId(articleid);
                fre.setWord(key);
                freService.save(fre);

            } else {
                Frequency fre1 = new Frequency();
                fre1.setFrequency(Collections.frequency(stemList, key));
                fre1.setNgramId(wordsid);
                fre1.setArtId(articleid);
                fre1.setWord(key);
                freService.save(fre1);
            }
        }
        System.out.println("DONE NGRAM");
        return "index";
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
        List<Complaint> complaint = govtUserService.findByAgencyAndStatus(type,status);
        System.out.println(complaint);
//        List<Complaint> complaint2 = new ArrayList<>();

//        for(int i=0; i<complaint.size(); i++){
//            complaint2 = govtUserService.findByGovtAgency(complaint.get(i).getFile_path());
//        }
//        complaint.getClass()
        if(type.equals("PAG")){
            model.addAttribute("complaint",complaint);
            map.addAttribute("agency","PAG");
            map.addAttribute("img","/images/love.png");
//            sendEmail(user.getEmail(),complaintReply.getComplaintReply(),"Divulgo: PAG-IBIG's feedback reply");
            return "homepage";
        }
        if(type.equals("LRA")){
            model.addAttribute("complaint",complaint);
            map.addAttribute("agency","LRA");
            map.addAttribute("img","/images/lra.png");
//            sendEmail(user.getEmail(),complaintReply.getComplaintReply(),"Divulgo: LRA's feedback reply");
            return "homepage";
        }
        if(type.equals("LTO")){
            model.addAttribute("complaint",complaint);
            map.addAttribute("agency","LTO");
            map.addAttribute("img","/images/lto.png");
//            sendEmail(user.getEmail(),complaintReply.getComplaintReply(),"Divulgo: LTO's feedback reply");
            return "homepage";
        }
        if(type.equals("SSS"))
            model.addAttribute("complaint",complaint);
            map.addAttribute("agency","SSS");
            map.addAttribute("img","/images/sss.png");
//            sendEmail(user.getEmail(),complaintReply.getComplaintReply(),"Divulgo: SSS's feedback reply");
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

        List<Complaint> complaint = govtUserService.findByAgencyAndStatus(type,status);

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
