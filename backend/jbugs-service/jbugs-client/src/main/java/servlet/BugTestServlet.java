package servlet;

import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
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
import java.util.List;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@WebServlet("/testBug")
public class BugTestServlet extends HttpServlet {
    private UserDTO userDTO;
    private BugDTO bugDTO;
    @EJB
    private UserManagerRemote userManager;
    @EJB
    private BugManagerRemote bugManager;

//    public void init(){
//        userDTO = new UserDTO();
//        userDTO.setCounter(1);
//        userDTO.setEmail("steph");
//        userDTO.setFirstName("steph");
//        userDTO.setLastName("steph");
//        userDTO.setMobileNumber("steph");
//        userDTO.setPassword("steph");
//        userDTO.setUsername("steph");
//        userDTO.setStatus(1);
//
//        bugDTO = new BugDTO();
//        bugDTO.setCreatedId(userDTO);
//        bugDTO.setAssignedId(null);
//        bugDTO.setDescription("testB");
//        bugDTO.setFixedVersion("testB");
//        bugDTO.setSeverity("testB");
//        bugDTO.setVersion("testB");
//        bugDTO.setTitle("testB");
//        bugDTO.setTargetDate(null);
//    }
//
//    public void destroy(){}
//
//    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
//        init();
//        response.setContentType("text/html");
//
//        PrintWriter out = response.getWriter();
//        out.println("----before bug insert---");
//        List<BugDTO> bugs = bugManager.findAllBugs();
//        for (BugDTO b : bugs){
//            out.println(b.toString());
//        }
//
//        out.println("----after bug insert---");
//        bugManager.insertBug(bugDTO);
//        List<BugDTO> bugs2 = bugManager.findAllBugs();
//        for (BugDTO b : bugs2){
//            out.println(b.toString());
//        }
//
//        out.println("---getting all bugs for managers");
//        List<BugDTO> bugs3 = bugManager.findAllBugs(UserDTOEntityMapper.getUserFromUserDto(userDTO));
//        for (BugDTO b : bugs3){
//            out.println(b.toString());
//        }
//    }

    public void init() throws ServletException{
        userDTO = new UserDTO();
        userDTO.setCounter(1);
        userDTO.setEmail("test");
        userDTO.setFirstName("test");
        userDTO.setMobileNumber("test");
        userDTO.setPassword("test");
        userDTO.setLastName("test");
        userDTO.setStatus(true);
        userDTO.setUsername("test");

        bugDTO = new BugDTO();
        bugDTO.setCreatedId(userDTO);
        bugDTO.setAssignedId(userDTO);
        bugDTO.setVersion("test version");
        bugDTO.setTitle("test title");
        bugDTO.setTargetDate(null);
        //bugDTO.setSeverity(Severity.CRITICAL);
        bugDTO.setFixedVersion("test fixed");
//        bugDTO.setStatus("test status");
        bugDTO.setDescription("test description");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();


        //inserting a new managers managers
        out.println("Inserting a new managers... <br>");
        UserDTO user = userManager.insertUser(userDTO);
        userDTO.setId(user.getId());

        //finding managers after id
        out.println("Searching for managers with id 2... <br>");
        UserDTO userDTO = userManager.findAUser(2);
        out.println(userDTO);
        out.println("<br>");

        //shows the number of the created bugs for each managers each 2 minutes
        //createView(out);

        //inserting a new bug
        out.println("Inserting a bug...<br>");
        try {
            bugManager.insertBug(bugDTO);
        } catch (BusinessException e) {
        }


        //find by assigned id
        out.println("Searching after assigned id...<br>");
        List<BugDTO> bugsByAssignedID = bugManager.findBugsByAssignedId(userDTO);
        bugsByAssignedID.forEach(bugCreated -> out.println(bugCreated.toString()+"<br>"));

        //find by created id
        out.println("Searching after created id...<br>");
        userDTO.setId(1);
        List<BugDTO> bugsByCreatedID = bugManager.findBugsByCreatedId(userDTO);
        bugsByCreatedID.forEach(bugAssigned -> out.println(bugAssigned.toString()+"<br>"));
    }

//    @Lock(LockType.READ)
//    @Schedule(second = "/5", minute = "", hour = "*", persistent = false)
//    public void createView(PrintWriter out){
//        List<UserDTO> users = userManager.findAllUsers(); // finds all the users from the db
//        for(UserDTO u: users){
//            Long createdBugs = userManager.findCreatedBugs(u);
//            out.println(u.getID()+" "+u.getFirstName()+" number of bugs: "+createdBugs+"<br>");
//        }
//    }
}