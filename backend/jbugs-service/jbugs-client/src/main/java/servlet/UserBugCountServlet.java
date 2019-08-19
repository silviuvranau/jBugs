package servlet;

import ro.msg.edu.jbugs.managers.implementations.BugManager;
import ro.msg.edu.jbugs.managers.implementations.UserManager;
import ro.msg.edu.jbugs.managers.interfaces.BugManagerRemote;
import ro.msg.edu.jbugs.managers.interfaces.UserManagerRemote;

import javax.ejb.EJB;
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
@WebServlet("/userBugs")
public class UserBugCountServlet extends HttpServlet {
    private String message;
    @EJB
    private UserManagerRemote userManager;
    @EJB
    private BugManagerRemote bugManager;

    public void init() throws ServletException {
        //doSomething
        message = "Hello!";
    }

    public void destroy(){}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        //out.println("<h1>" + message + "</h1>");

        //out.println(userDTO.toString());
        //userManager.insertBug(userDTO);
        //UserDTO userDto = userManager.findUser(2);
        //out.println("<h1>" + userDto.toString() + "</h1>");

//        List<UserDTO> userDTOList = userManager.findAllUsers();
//        for (UserDTO u : userDTOList){
//            out.println("<h1>" + u.toString() + "</h1>");
//            Integer count = bugManager.countBugs(UserDTOEntityMapper.getUserFromUserDto(u));
//            out.print("   ="+ count);
//        }

    }

}
