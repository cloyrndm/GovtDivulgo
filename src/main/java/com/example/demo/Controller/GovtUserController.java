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
    FrequencyService frequencyService;

    @Autowired
    NgramService ngramService;

    @Autowired
    FrequencyService freService;

    @Autowired
    TfidfService tfidfService;

//    @Autowired
//    TfidfService tfidfService;

    @Autowired
    TfService tfService;

    @Autowired
    IdfService idfService;

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
    private String correction(HttpServletRequest request,HttpSession session, Model model,ModelMap map,ComplaintReply complaintReply) throws IOException {
        String complaint = request.getParameter("complaint");
        String agency = request.getParameter("agency");
        String id = request.getParameter("id");
        long idd = Long.valueOf(id);

        Complaint complaint1 = complaintService.findByComplaintId(idd);
        complaint1.setAgency(agency);
        complaintService.save(complaint1);

        Article article = new Article();
        article.setContent(complaint);
        article.setAgency(agency);
        article.setTitle("complaint");
        articleService.save(article);

        cleanContent(complaint);
        TermFrequency();
        InverseTermFrequency();
        clean();
        TermFrequencyAndInverseTermFrequency();

        String type = (String)session.getAttribute("type");
        List<Complaint> complaint2 = govtUserService.findByAgencyAndStatus(type,null);
//        System.out.println(complaint);
//      --------------------------------------------------------
        if(type.equals("PAG")){
            model.addAttribute("complaint",complaint2);
            map.addAttribute("agency","PAG");
            map.addAttribute("img","/images/love.png");
            return "homepage";
        }

        if(type.equals("LRA")){
            model.addAttribute("complaint",complaint2);
            map.addAttribute("agency","LRA");
            map.addAttribute("img","/images/love.png");
            return "homepage";
        }
        if(type.equals("LTO")){
            model.addAttribute("complaint",complaint2);
            map.addAttribute("agency","LTO");
            map.addAttribute("img","/images/love.png");
            return "homepage";
        }
        if(type.equals("SSS"))
            model.addAttribute("complaint",complaint2);
        map.addAttribute("agency","SSS");
        map.addAttribute("img","/images/love.png");
        return "homepage";


    }

    @PostMapping("/cleanContent")
    public String cleanContent(String content) throws IOException {

        System.out.println("done scrape");
        File file = new File("C:\\Users\\Cloie Andrea\\IdeaProjects\\GovtDivulgo\\stopwords.txt");
        Set<String> stopWords = new LinkedHashSet<String>();
        List<String> ngrams = new ArrayList<String>();
        List<String> minus = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String regex = "[A-Z]+";
        Pattern r = Pattern.compile(regex);
        int wc=0, tempWC=0;
        String changeWord;
        String[] words =content.replaceAll("[^a-zA-Z ]", "").split("\\s+");

        for(String line;(line = br.readLine()) != null;)
            stopWords.add(line.trim());
        br.close();

        ArrayList<String> wordsList = new ArrayList<String>();
        ArrayList<String> stemList = new ArrayList<String>();
        for (String word : words) {

            wordsList.add(word);
        }

        System.out.println("After for loop:  " + wordsList);

        for(int i = 0; i < wordsList.size(); i++) {
            Matcher m = r.matcher(wordsList.get(i));
            if (m.find()) {
                wordsList.remove(i);
                minus.add(wordsList.get(i));
                System.out.println("removed capital");

            }

            else if (stopWords.contains(wordsList.get(i))) {
                wordsList.remove(i);
                minus.add(wordsList.get(i));
                System.out.println("remove stop");

            }
        }

        wordsList.removeAll(minus);

        for (String a:wordsList){
            PorterStemmer stemmer = new PorterStemmer();
            stemmer.setCurrent(a);
            stemmer.stem();
            String steem=stemmer.getCurrent();
            stemList.add(steem);
            System.out.println("stemmer: "+ steem);
        }
        Article sampleContent = articleService.findByContent(content);
        int articleid = sampleContent.getArticleId();
        for (String bag:stemList){

            Ngram sampleWord = ngramService.findByWords(bag);
//            if (sampleWord != null) {
//                int id = sampleWord.getArticleId();
//                wc=sampleWord.getWordCount();
//            }
            if (sampleWord == null) {
                Ngram ngram = new Ngram();
//                ngram.setArticleId(articleid);
                ngram.setWords(bag);
                ngram.setWordCount(wc);
                ngramService.save(ngram);

            }

        }

//        System.out.println("DONE SAVING STEM WORDS");
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
        return "homepage";
    }

    public void TermFrequency(){

        List<Frequency> freq = frequencyService.findAll();
        List<Integer> artid = new ArrayList<Integer>();
        List<Integer> frid = new ArrayList<Integer>();
        List<Integer> nid = new ArrayList<>();

        for (int i = 0; i < freq.size(); i++) {
            artid.add(frequencyService.findAll().get(i).getArtId());
            frid.add(frequencyService.findAll().get(i).getFreqId());
            nid.add(frequencyService.findAll().get(i).getNgramId());
        }
        System.out.println("asd"+artid.size());

        Map<Integer, String> artIdContent = new HashMap<>();
        List<Article> article = articleService.findAll();
        for (int i = 0; i < article.size(); i++) {
            artIdContent.put(articleService.findAll().get(i).getArticleId(),articleService.findAll().get(i).getContent());
        }
//      for checking purposes only
        System.out.println("-----------------------------");
        for (Map.Entry<Integer, String> artic : artIdContent.entrySet()) {
            System.out.println(artic.getKey()+artic.getValue());
        }

        Map<Integer, Integer> artIdAndArtSize = new HashMap<>();
        for (Map.Entry<Integer, String> artic : artIdContent.entrySet()) {
            StringTokenizer st = new StringTokenizer(String.valueOf(artic.getValue()));
            Integer count = st.countTokens();
            artIdAndArtSize.put(artic.getKey(),count);

        }
        //checking purposes only

        System.out.println("---------Article id and Count---------");
        for (Map.Entry<Integer, Integer> artic : artIdAndArtSize.entrySet()) {
            System.out.println(artic.getKey() + "\t\t" + artic.getValue());
            Article article1 = articleService.findByArticleId(artic.getKey());
            article1.setArtSize(artic.getValue());
            articleService.save(article1);
        }

        System.out.println("---------------------------------------");
        //checking purposes only
        Map<Integer, Double> tfval =  new HashMap<>();

        System.out.println("freq"+freq.size());

        List<Tf> tf1 = tfService.findAll();

        for (int i = 0; i<freq.size(); i++) {
            Article article1 = articleService.findByArticleId(artid.get(i));
            Frequency fre1 = frequencyService.findByArtIdAndFreqId(artid.get(i),frid.get(i));
            Tf tf5 = tfService.findByFreqId(frid.get(i));

            Double freqq = Double.valueOf(fre1.getFrequency());
            Double arts = Double.valueOf(article1.getArtSize());
            if(tf1.size()==0){
                System.out.println("NONE");
                Tf tf = new Tf();
                tf.setNgramId(fre1.getNgramId());
                tf.setAgency(article1.getAgency());
                tf.setWord(fre1.getWord());
                tf.setFreqId(fre1.getFreqId());
                tf.setTfVal(freqq / arts);
                tfService.save(tf);
//               System.out.println("done tf");
            }
            else if(tfService.findByFreqId(frid.get(i))!=null){
                System.out.println("TF UPDATE");
                tf5.setNgramId(fre1.getNgramId());
                tf5.setAgency(article1.getAgency());
                tf5.setWord(fre1.getWord());
                tf5.setFreqId(fre1.getFreqId());
                tf5.setTfVal(freqq / arts);
                tfService.save(tf1.get(i));
//               System.out.println("done tf");
            }
            else if(tfService.findByFreqId(frid.get(i))==null){
                System.out.println("WAASD!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                Tf tf = new Tf();
                tf.setNgramId(fre1.getNgramId());
                tf.setAgency(article1.getAgency());
                tf.setWord(fre1.getWord());
                tf.setFreqId(fre1.getFreqId());
                tf.setTfVal(freqq / arts);
                tfService.save(tf);
//               System.out.println("done tf");
            }
        }
        System.out.println("DONE TF");

    }

    public void clean(){
        List<Ngram> ngram = ngramService.findAll();
        for (int i = 0; i < ngram.size(); i++) {
            ngram.get(i).setIdfWcount(null);
            ngramService.save(ngram.get(i));
        }
    }

    public void InverseTermFrequency() {
        List<Frequency> freq = frequencyService.findAll();

        ArrayList<Integer> ngramid = new ArrayList<Integer>();
        ArrayList<Integer> aid = new ArrayList<Integer>();
        ArrayList<Integer> freqqq = new ArrayList<>();

        for (int i = 0; i < freq.size(); i++) {
            ngramid.add(frequencyService.findAll().get(i).getNgramId());
            aid.add(frequencyService.findAll().get(i).getArtId());
            freqqq.add(frequencyService.findAll().get(i).getFreqId());
        }
        for(int i = 0; i<freq.size();i++){
            Ngram ngram = ngramService.findByNgramId(ngramid.get(i));
            List<Frequency> frequency1 = frequencyService.findAll();
            if(ngramid.get(i).equals(frequency1.get(i).getNgramId())&&ngram.getIdfWcount()==null) {
                ngram.setIdfWcount(1);
                ngramService.save(ngram);
            }
            else if(ngramid.get(i).equals(frequency1.get(i).getNgramId())&&ngram.getIdfWcount()!=null){
                ngram.setIdfWcount(ngram.getIdfWcount()+1);
                ngramService.save(ngram);
            }

        }

        //TF-IDF
        List<Article> a = articleService.findAll();
        int size = a.size();
        System.out.println("size: "+size);

        List<Idf> idf5 = idfService.findAll();
        for(int i = 0; i<freq.size(); i++){

            Idf idf2 = idfService.findByFreqId(freq.get(i).getFreqId());
            Ngram ngram = ngramService.findByNgramId(freq.get(i).getNgramId());

            if(idf5.size()==0){
                System.out.println(1);
                Idf idf1 = new Idf();
                Double d = size / Double.valueOf(ngram.getIdfWcount());

                System.out.println("Idf w count: "+ngram.getIdfWcount());
                Double idff = Math.log(d);
                idf1.setIdfVal(idff);
                idf1.setNgramId(ngram.getNgramId());
                idf1.setFreqId(freq.get(i).getFreqId());
                idfService.save(idf1);

            }
            else if(idfService.findByFreqId(freq.get(i).getFreqId())!=(null)) {
                System.out.println(2);
                Double d = size / Double.valueOf(ngram.getIdfWcount());
                System.out.println("Idf w count: "+ngram.getIdfWcount());
                Double idff = Math.log(d);
                idf2.setIdfVal(idff);
                idf2.setNgramId(ngram.getNgramId());
                idf2.setFreqId(freq.get(i).getFreqId());
                idfService.save(idf2);
//                System.out.println("done idf");
            }
            else if(idfService.findByFreqId(freq.get(i).getFreqId())==(null)){
                System.out.println(3);
                Idf idf1 = new Idf();
                Double d = size / Double.valueOf(ngram.getIdfWcount());
                System.out.println("Idf w count: "+ngram.getIdfWcount());
                Double idff = Math.log(d);
                idf1.setIdfVal(idff);
                idf1.setNgramId(ngram.getNgramId());
                idf1.setFreqId(freq.get(i).getFreqId());
                idfService.save(idf1);
//                System.out.println("done idf");
            }

        }
        System.out.println("done idf");
    }

    public void TermFrequencyAndInverseTermFrequency(){

        List<Tf> tf = tfService.findAll();
        System.out.println("size tf"+tf.size());
        List<Idf> idf = idfService.findAll();
        System.out.println("size idf"+idf.size());
        List<Tfidf> tfidf1 = tfidfService.findAll();
        for(int i = 0; i<tf.size(); i++) {
            Tfidf tfidf2 = tfidfService.findByFreqId(tf.get(i).getFreqId());
            if(tfidf1.size()==0) {
                System.out.println(1);
                Tfidf tfidf = new Tfidf();
                Idf idf1 = idfService.findByFreqIdAndNgramId(tf.get(i).getFreqId(), tf.get(i).getNgramId());
                Double tfidff = idf1.getIdfVal() * tf.get(i).getTfVal();
                tfidf.setNgramId(tf.get(i).getNgramId());
                tfidf.setWord(tf.get(i).getWord());
                tfidf.setAgency(tf.get(i).getAgency());
                tfidf.setTfidfVal(tfidff);
                tfidf.setFreqId(tf.get(i).getFreqId());
                tfidfService.save(tfidf);
            }
            else if(tfidfService.findByFreqId(tf.get(i).getFreqId())!=null){
                System.out.println(2);
                Idf idf1 = idfService.findByFreqIdAndNgramId(tf.get(i).getFreqId(), tf.get(i).getNgramId());
                Double tfidff = idf1.getIdfVal() * tf.get(i).getTfVal();
                tfidf2.setNgramId(tf.get(i).getNgramId());
                tfidf2.setWord(tf.get(i).getWord());
                tfidf2.setAgency(tf.get(i).getAgency());
                tfidf2.setTfidfVal(tfidff);
                tfidf2.setFreqId(tf.get(i).getFreqId());
                tfidfService.save(tfidf2);

            }
            else if(tfidfService.findByFreqId(tf.get(i).getFreqId())==null){
                System.out.println(3);
                Tfidf tfidf = new Tfidf();
                Idf idf1 = idfService.findByFreqIdAndNgramId(tf.get(i).getFreqId(), tf.get(i).getNgramId());
                Double tfidff = idf1.getIdfVal() * tf.get(i).getTfVal();
                tfidf.setNgramId(tf.get(i).getNgramId());
                tfidf.setWord(tf.get(i).getWord());
                tfidf.setAgency(tf.get(i).getAgency());
                tfidf.setTfidfVal(tfidff);
                tfidf.setFreqId(tf.get(i).getFreqId());
                tfidfService.save(tfidf);
            }

        }
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
