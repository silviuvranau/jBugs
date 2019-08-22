package controller;

import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.managers.interfaces.BugManagerRemote;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Path("/bugs")
@Produces("application/json")
public class BugController {
    @EJB
    BugManagerRemote bugManagerRemote;

    @GET
    public List<BugDTO> getAllBugs() {
        return bugManagerRemote.findAllBugs();
    }
}
