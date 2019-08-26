package servlet;

import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.NotificationDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.managers.interfaces.BugManagerRemote;
import ro.msg.edu.jbugs.managers.interfaces.CommentManagerRemote;
import ro.msg.edu.jbugs.managers.interfaces.NotificationManagerRemote;
import ro.msg.edu.jbugs.managers.interfaces.UserManagerRemote;

import javax.ejb.EJB;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@WebServlet("/servlet")
public class Servlet extends HttpServlet {
    @EJB
    UserManagerRemote userManager;
    @EJB
    CommentManagerRemote commentManager;
    @EJB
    BugManagerRemote bugManager;
    private UserDTO firstUser, secondUser;
    @EJB
    NotificationManagerRemote notificationManagerRemote;

    public void init() throws ServletException {
        //doSomething

        firstUser = new UserDTO();
        firstUser.setCounter(3);
        firstUser.setFirstName("test");
        firstUser.setLastName("test");
        firstUser.setEmail("test");
        firstUser.setMobileNumber("test");
        //firstUser.setUsername("fuckU");
        firstUser.setPassword("test");
        firstUser.setStatus(true);

        secondUser = new UserDTO();
        secondUser.setCounter(3);
        secondUser.setFirstName("test");
        secondUser.setLastName("test");
        secondUser.setEmail("test2");
        secondUser.setMobileNumber("test2");
        //secondUser.setUsername("fuckU");
        secondUser.setPassword("test2");
        secondUser.setStatus(false);
        secondUser.setStatus(true);
    }

    public void destroy(){}

//    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Set response content type
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//
//
//        userManager.insertUser(firstUser);
//        out.println("---AFTER FIRST INSERT---");
//        List<UserDTO> userDTOList = userManager.findAllUsers();
//        for (UserDTO u : userDTOList){
//            out.println("<h1>" + u.toString() + "</h1>");
//        }
//        List<NotificationDTO> notificationDTOS = notificationManagerRemote.findAllNotifications();
//        for (NotificationDTO n : notificationDTOS){
//            out.println("<h1>" + n.toString() + "</h1>");
//        }
//
//        userManager.insertUser(secondUser);
//        out.println("---AFTER SECOND INSERT---");
//
//        userDTOList = userManager.findAllUsers();
//        for (UserDTO u : userDTOList){
//            out.println("<h1>" + u.toString() + "</h1>");
//        }
//        notificationDTOS = notificationManagerRemote.findAllNotifications();
//        for (NotificationDTO n : notificationDTOS){
//            out.println("<h1>" + n.toString() + "</h1>");
//        }
//
//        try {
//            userManager.login("testt", "test");
//        } catch (BusinessException e) {
//            e.printStackTrace();
//        }
//
//        UserDTO user = userManager.findAUser(2);
//        out.println("---CHECK IF FOUND USER WITH ID: 1 ---");
//        out.println("<h1>" + user.toString() + "</h1>");
//
//        Integer delete = userManager.deleteUser(15);
//        out.println("---NR OF DELETED USERS: " + delete + "<br>");
//
//        out.println("---CHECK USER IN DB AFTER DELETION: ----");
//        userDTOList = userManager.findAllUsers();
//        for (UserDTO u : userDTOList){
//            out.println("<h1>" + u.toString() + "</h1>");
//        }
//
//
//        String text = request.getParameter("text") != null ? request.getParameter("text") : "Hello World";
//        try {
//                Context ic = new InitialContext();
//
//                ConnectionFactory cf = (ConnectionFactory) ic.lookup("java:comp/DefaultJMSConnectionFactory");
//                Queue queue = (Queue) ic.lookup("tutorialQueue");
//
//                Connection connection = cf.createConnection();
//
//            Session session = connection.createSession(
//                    false, Session.AUTO_ACKNOWLEDGE);
//            MessageProducer publisher = session
//                    .createProducer(queue);
//
//            connection.start();
//
//            TextMessage message = session.createTextMessage(text);
//            publisher.send(message);
//
//        } catch (NamingException | JMSException e) {
//            response.getWriter()
//                    .println("Error while trying to send <" + text + "> message: " + e.getMessage());
//        }
//
//        response.getWriter()
//                .println("Message sent: " + text);
//    }
}
