package ro.msg.edu.jbugs.managers.implementations;

import dao.BugDao;
import entity.Bug;
import entity.User;
import entity.enums.Severity;
import entity.enums.Status;
import exceptions.BusinessException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.mappers.BugDTOEntityMapper;
import ro.msg.edu.jbugs.mappers.UserDTOEntityMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@RunWith(MockitoJUnitRunner.class)
public class BugManagerTest {

    @InjectMocks
    private BugManager bugManager;

    @Mock
    private BugDao bugDao;

    public BugManagerTest() {
        this.bugDao = new BugDao();
        this.bugManager = new BugManager();
    }

    @Test
    public void insertBug() {
//        Bug bug = createBug();
//        when(bugDao.findBug(any(Integer.class))).thenReturn(bug);
//        when(bugDao.insertBug(any(Bug.class))).thenReturn(bug);
//
//        BugDTO bugToInsert = BugDTOEntityMapper.getDtoFromBug(bug);
//        BugDTO bugReturned = bugToInsert;
//        try {
//            bugReturned = bugManager.insertBug(bugToInsert);
//        } catch (BusinessException e) { }
//
//        Assert.assertEquals(bugToInsert.getId(), bugReturned.getId());
//        Assert.assertEquals(bugToInsert.getDescription(), bugReturned.getDescription());
//        Assert.assertEquals(bugToInsert.getSeverity(), bugReturned.getSeverity());
//        Assert.assertEquals(bugToInsert.getFixedVersion(), bugReturned.getFixedVersion());
    }

    @Test
    public void findABug() {
        Bug bug = createBug();
        when(bugDao.findBug(bug.getId())).thenReturn(bug);

        BugDTO searchedBugDto = bugManager.findABug(bug.getId());

        Assert.assertEquals(bug.getTitle(), searchedBugDto.getTitle());
        Assert.assertEquals(bug.getSeverity(), searchedBugDto.getSeverity());
        Assert.assertEquals(bug.getAssignedId().getId(), searchedBugDto.getAssignedId().getId());
        Assert.assertEquals(bug.getCreatedId().getId(), searchedBugDto.getCreatedId().getId());
        Assert.assertEquals(bug.getDescription(), bug.getDescription());
        Assert.assertEquals(bug.getFixedVersion(), searchedBugDto.getFixedVersion());
        Assert.assertEquals(bug.getVersion(), searchedBugDto.getVersion());
        Assert.assertEquals(bug.getStatus(), searchedBugDto.getStatus());
        Assert.assertEquals(bug.getTargetDate(), searchedBugDto.getTargetDate());
    }

    @Test
    public void findAllBugs() {
        List<Bug> bugsList = new ArrayList<>();
        Bug bug = createBug();
        bugsList.add(bug);
        bug.setId(13);
        bugsList.add(bug);

        when(bugDao.findAllBugs()).thenReturn(bugsList);
        List<BugDTO> returnedBugs = bugManager.findAllBugs();
        Assert.assertEquals(2, returnedBugs.size());
    }

    @Test
    public void updateBug() {
//        Bug bug = createBug();
//        Bug newBug = bug;
//        newBug.setTitle("updatedTitle");
//
//        when(bugDao.findBug(bug.getId())).thenReturn(bug);
//
//        try {
//            BugDTO returnedBug = bugManager.updateBug(BugDTOEntityMapper.getDtoFromBug(newBug), "popm");
//            Assert.assertEquals(newBug.getTitle(), returnedBug.getTitle());
//        } catch (BusinessException e) {
//        }

    }

    @Test(expected = BusinessException.class)
    public void updateBugNotInDbException() throws BusinessException {
        Bug bug = createBug();
        Bug newBug = bug;
        newBug.setTitle("updatedTitle");

        when(bugDao.findBug(bug.getId())).thenReturn(null);

//        bugManager.updateBug(BugDTOEntityMapper.getDtoFromBug(newBug), "popm");
    }

    /**
     * @Test(expected = BusinessException.class)
     * public void updateBugStatusUnreachableException () throws BusinessException{
     * Bug bug = createBug();
     * Bug newBug = bug;
     * newBug.setStatus(Status.IN_PROGRESSS);
     * BugDTO bugDto = BugDTOEntityMapper.getDtoFromBug(newBug);
     * <p>
     * when(bugDao.findBug(bug.getId())).thenReturn(bug);
     * when(bugManager.statusIsReachable(any(Status.class), any(Status.class))).thenReturn(false);
     * <p>
     * bugManager.updateBug(newBug.getId(), BugDTOEntityMapper.getDtoFromBug(newBug));
     * }
     **/

    @Test(expected = BusinessException.class)
    public void updateBugDifferentIdsException() throws BusinessException {
        Bug bug = createBug();
        Bug newBug = bug;
        newBug.setStatus(Status.IN_PROGRESSS);
        BugDTO bugDto = BugDTOEntityMapper.getDtoFromBug(newBug);

//        bugManager.updateBug(bugDto, "popm");
    }

    @Test(expected = BusinessException.class)
    public void canDeactivateUserException() throws BusinessException {
        Bug bug = createBug();
        User user = bug.getAssignedId();
        UserDTO userDTO = UserDTOEntityMapper.getDtoFromUser(user);
        when(bugDao.getClosedBugsByAssignedId(any(User.class))).thenReturn(4);

        bugManager.canDeactivateUser(userDTO);
    }

    @Test
    public void canDeactivateUser() {
        Bug bug = createBug();
        when(bugDao.getClosedBugsByAssignedId(bug.getAssignedId())).thenReturn(0);

        try {
            Assert.assertTrue(bugManager.canDeactivateUser(UserDTOEntityMapper.getDtoFromUser(bug.getAssignedId())));
        } catch (BusinessException e) {
        }
    }

    @Test
    public void findBugsByCreatedId() {
    }

    @Test
    public void findBugsByAssignedId() {
    }

    @Test
    public void deleteExceedingBugs() {
    }

    private Bug createBug() {
        Bug bug = new Bug();
        User user = new User();
        user.setId(1);
        bug.setId(1000);
        bug.setTitle("title");
        bug.setDescription("...");
        bug.setVersion("1.0");
        bug.setFixedVersion("1.3");
        bug.setSeverity(Severity.CRITICAL);
        bug.setCreatedId(user);
        bug.setAssignedId(user);
        bug.setTargetDate("2000-01-09 01:10:20");
        bug.setStatus(Status.CLOSED);

        return bug;
    }

    @Test
    public void statusIsReachable() {
        Assert.assertFalse(bugManager.statusIsReachable(Status.CLOSED, Status.IN_PROGRESSS));
        Assert.assertFalse(bugManager.statusIsReachable(Status.REJECTED, Status.IN_PROGRESSS));
        Assert.assertFalse(bugManager.statusIsReachable(Status.CLOSED, Status.NEW));
        Assert.assertTrue(bugManager.statusIsReachable(Status.IN_PROGRESSS, Status.NEW));
        Assert.assertTrue(bugManager.statusIsReachable(Status.IN_PROGRESSS, Status.CLOSED));
        Assert.assertTrue(bugManager.statusIsReachable(Status.IN_PROGRESSS, Status.REJECTED));
        Assert.assertTrue(bugManager.statusIsReachable(Status.IN_PROGRESSS, Status.INFO_NEEDED));
        Assert.assertTrue(bugManager.statusIsReachable(Status.IN_PROGRESSS, Status.FIXED));
        Assert.assertTrue(bugManager.statusIsReachable(Status.NEW, Status.FIXED));
        Assert.assertTrue(bugManager.statusIsReachable(Status.NEW, Status.CLOSED));
        Assert.assertTrue(bugManager.statusIsReachable(Status.NEW, Status.INFO_NEEDED));
        Assert.assertTrue(bugManager.statusIsReachable(Status.NEW, Status.IN_PROGRESSS));
        Assert.assertTrue(bugManager.statusIsReachable(Status.NEW, Status.REJECTED));
        Assert.assertTrue(bugManager.statusIsReachable(Status.INFO_NEEDED, Status.FIXED));
        Assert.assertTrue(bugManager.statusIsReachable(Status.INFO_NEEDED, Status.CLOSED));
        Assert.assertTrue(bugManager.statusIsReachable(Status.INFO_NEEDED, Status.NEW));
        Assert.assertTrue(bugManager.statusIsReachable(Status.INFO_NEEDED, Status.IN_PROGRESSS));
        Assert.assertTrue(bugManager.statusIsReachable(Status.INFO_NEEDED, Status.REJECTED));
        Assert.assertTrue(bugManager.statusIsReachable(Status.INFO_NEEDED, Status.INFO_NEEDED));
    }


}