package dao;

import entity.Bug;
import entity.User;
import entity.enums.Severity;
import entity.enums.Status;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@RunWith(MockitoJUnitRunner.class)
public class BugDaoTest {

    @Mock
    private BugDao bugDao;

    public BugDaoTest() {
        this.bugDao = new BugDao();
    }

    private Bug createBug() {
        Bug bug = new Bug();
        User user = new User();
        bug.setId(1000);
        bug.setAssignedId(user);
        bug.setCreatedId(user);
        bug.setDescription("...");
        bug.setFixedVersion("1.3");
        bug.setSeverity(Severity.CRITICAL);
        bug.setTargetDate("2000-01-09 01:10:20");
        bug.setStatus(Status.NEW);
        bug.setTitle("title");

        return bug;
    }

    @Test
    public void insertBug() {
        Bug bug = createBug();

        Bug insertedBug = bugDao.insertBug(bug);
        Assert.assertEquals(bug, insertedBug);
    }

    @Test
    public void findBug() {
    }

    @Test
    public void findAllBugs() {
    }

    @Test
    public void findByCreatedId() {
    }

    @Test
    public void findByAssignedId() {
    }

    @Test
    public void deleteBugs() {
    }
}