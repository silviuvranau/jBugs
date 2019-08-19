package servlet;

import ro.msg.edu.jbugs.managers.interfaces.BugManagerRemote;
import ro.msg.edu.jbugs.managers.interfaces.CommentManagerRemote;

import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@WebServlet("/deletion")
public class DeleteCommentsAndBugs extends HttpServlet{
    @EJB
    private BugManagerRemote bugManager;
    @EJB
    private CommentManagerRemote commentManager;

    public void init() throws ServletException {
    }

    public void destroy(){}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();


        //deleting bugs
        out.println("Number of deleted bugs that exceed date: ");
        out.println(bugManager.deleteExceedingBugs());

        //deleteing comments
        out.println("Number of deleted comments that are one year old: ");
        out.println(commentManager.deleteComments());
    }

    @Lock(LockType.READ)
    @Schedule(second = "/5", minute = "", hour = "*", persistent = false)
    public void deleteCommentsAndBugs() {

        Integer deletedComments = commentManager.deleteComments();
        Integer deletedBugs = bugManager.deleteExceedingBugs();

//        String sendEmailTo = "sebastian.black12@gmail.com";
//        String senderEmail = "pricop.stefania98@gmail.com";
//        String host = "localhost";
//
//        Properties properties = System.getProperties();
//        properties.setProperty("mail.smtp.host", host);
//        properties.setProperty("mail.smtp.ssl.enable", "true");
//        Session session = Session.getDefaultInstance(properties);
//
//        try {
//            // Create a default MimeMessage object.
//            MimeMessage message = new MimeMessage(session);
//
//            // Set From: header field of the header.
//            message.setFrom(new InternetAddress(senderEmail));
//
//            // Set To: header field of the header.
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendEmailTo));
//
//            // Set Subject: header field
//            message.setSubject("Deletion Report");
//
//            // Now set the actual message
//            message.setText("Number of comments deleted: " + deletedComments + "/n" + "Number of bugs deleted: " + deletedBugs);
//
//            // Send message
//            Transport.send(message);
//            System.out.println("Sent message successfully....");
//        } catch (MessagingException mex) {
//            mex.printStackTrace();
//        }

//        String senderEmail = "pricop.stefania98@gmail.com";//change with your sender email
//        String senderPassword = "527934Pricop";//change with your sender password


//        try {
//            String mailContent = "Number of deleted comments: " + deletedComments + "/n" +
//                    "Number of deleted bugs: " + deletedBugs;
//            sendMail(mailContent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

//        public void sendAsHtml(String to, String title, String html) throws MessagingException {
//            System.out.println("Sending email to " + to);
//            Session session = createSession();
//            //create message using session
//            MimeMessage message = new MimeMessage(session);
//            prepareEmailMessage(message, to, title, html);
//            //sending message
//            Transport.send(message);
//            System.out.println("Done");
//        }
//        public static void sendMail(String msg) throws Exception {
//            Session session = createSession();
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress("pricop.stefania98@gmail.com"));
//            message.setRecipients(
//                    Message.RecipientType.TO, InternetAddress.parse("pricop.stefania98@gmail.com"));
//            message.setSubject("Deletion Report");
//            MimeBodyPart mimeBodyPart = new MimeBodyPart();
//            mimeBodyPart.setContent(msg, "text/html");
//            Multipart multipart = new MimeMultipart();
//            multipart.addBodyPart(mimeBodyPart);
//            message.setContent(multipart);
//            Transport.send(message);
//        }
//        private static void prepareEmailMessage(MimeMessage message, String to, String title, String html)
//            throws MessagingException {
//            message.setContent(html, "text/html; charset=utf-8");
//            //message.setFrom(new InternetAddress(senderEmail));
//            message.setFrom(new InternetAddress("pricop.stefania98@gmail.com"));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//            message.setSubject(title);
//        }
//        private static Session createSession() {
//
//            String senderEmail = "pricop.stefania98@gmail.com";//change with your sender email
//            String senderPassword = "527934Pricop";//change with your sender password
//            Properties props = new Properties();
//            props.put("mail.smtp.starttls.enable", "true");
//            props.put("mail.smtp.host", "smtp.gmail.com");
//            props.put("mail.smtp.managers", "mail");
//            props.put("mail.smtp.password", "pw123456!");
//            props.put("mail.smtp.port", 587);
//            props.put("mail.smtp.auth", "true");
//            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
//                protected PasswordAuthentication getPasswordAuthentication() {
//                    return new PasswordAuthentication(senderEmail, senderPassword);
//                }
//            });
//            return session;
//        }
    }
