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
import ro.msg.edu.jbugs.mappers.BugDTOEntityMapper;

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
        Bug bug = createBug();
        when(bugDao.insertBug(bug)).thenReturn(createBug());
        BugDTO bugToInsert = BugDTOEntityMapper.getDtoFromBug(createBug());
        BugDTO bugReturned = new BugDTO();
        try {
            bugReturned = bugManager.insertBug(bugToInsert);
        } catch (BusinessException e) {
        }

        Assert.assertEquals(bugToInsert.getId(), bugReturned.getId());
        Assert.assertEquals(bugToInsert.getDescription(), bugReturned.getDescription());
        Assert.assertEquals(bugToInsert.getSeverity(), bugReturned.getSeverity());
        Assert.assertEquals(bugToInsert.getFixedVersion(), bugReturned.getFixedVersion());
    }

    @Test
    public void findABug() {
    }

    @Test
    public void findAllBugs() {
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
        bug.setId(1000);
        bug.setTitle("title");
        bug.setDescription("...");
        bug.setVersion("1.0");
        bug.setFixedVersion("1.3");
        bug.setSeverity(Severity.CRITICAL);
        bug.setCreatedId(user);
        bug.setAssignedId(user);
        bug.setTargetDate("2000-01-09 01:10:20");
        bug.setStatus(Status.NEW);


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