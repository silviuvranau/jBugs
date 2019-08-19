package servlet;

import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.managers.implementations.UserManager;
import ro.msg.edu.jbugs.managers.interfaces.UserManagerRemote;

import javax.ejb.EJB;
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
@WebServlet("/testServlet")
public class TestServlet extends HttpServlet {
    private String message;
    private UserDTO userDTO;
    @EJB
    private UserManagerRemote userManager;

    public void init() throws ServletException {
        //doSomething
        message = "Hello!";

        userDTO = new UserDTO();
        userDTO.setCounter(3);
        userDTO.setFirstName("test3");
        userDTO.setLastName("test3");
        userDTO.setEmail("test3");
        userDTO.setMobileNumber("test3");
        userDTO.setUsername("test3");
        userDTO.setPassword("test3");
        userDTO.setStatus(false);
    }

    public void destroy(){}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        List<UserDTO> userDTOList = userManager.findAllUsers();
        for (UserDTO u : userDTOList){
            out.println("<h1>" + u.toString() + "</h1>");
        }

    }
}
