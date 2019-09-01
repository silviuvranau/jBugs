package ro.msg.edu.jbugs.managers.implementations;

import dao.BugDao;
import dao.UserDao;
import entity.Attachment;
import entity.Bug;
import entity.User;
import entity.enums.NotificationType;
import entity.enums.Severity;
import entity.enums.Status;
import exceptions.BusinessException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.dto.AttachmentDTO;
import ro.msg.edu.jbugs.dto.BugAttachmentWrapperDTO;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.mappers.AttachmentDTOEntityMapper;
import ro.msg.edu.jbugs.mappers.BugDTOEntityMapper;
import ro.msg.edu.jbugs.mappers.UserDTOEntityMapper;
import ro.msg.edu.jbugs.util.NotificationUtils;
import ro.msg.edu.jbugs.util.PermissionChecker;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

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

    @Mock
    private UserDao userDao;

    @Mock
    private PermissionChecker permissionChecker;

    public BugManagerTest() {
        this.bugDao = new BugDao();
        this.bugManager = new BugManager();
    }

    private Bug createBug() {
        Bug bug = new Bug();
        User user = new User();
        User user2 = new User();
        user2.setId(2);
        user.setId(1);
        bug.setId(1000);
        bug.setTitle("title");
        bug.setDescription("...");
        bug.setVersion("1.0");
        bug.setFixedVersion("1.3");
        bug.setSeverity(Severity.CRITICAL);
        bug.setCreatedId(user);
        bug.setAssignedId(user2);
        bug.setTargetDate("2000-01-09 01:10:20");
        bug.setStatus(Status.CLOSED);

        return bug;
    }

    private Attachment createAttachment() {
        Attachment attachment = new Attachment();
        attachment.setId(1);
        attachment.setAttContent("TestContent".getBytes());

        return attachment;
    }

    @Test(expected = BusinessException.class)
    public void insertBugWithBugDtoNull() throws BusinessException {
        Bug bug = createBug();
        Attachment attachment = createAttachment();
        attachment.setBug(bug);

        BugAttachmentWrapperDTO bugAttachmentWrapperDTO = new BugAttachmentWrapperDTO();
        bugAttachmentWrapperDTO.setBugDTO(null);
        bugAttachmentWrapperDTO.setAttachmentDTO(AttachmentDTOEntityMapper.getDtoFromAttachment(attachment));

        bugManager.insertBug(bugAttachmentWrapperDTO);
    }

    @Test(expected = BusinessException.class)
    public void insertBugWithAssignedIdNotInDb() throws BusinessException {
        Bug bug = createBug();
        Attachment attachment = createAttachment();
        attachment.setBug(bug);

        BugAttachmentWrapperDTO bugAttachmentWrapperDTO = new BugAttachmentWrapperDTO();
        bugAttachmentWrapperDTO.setBugDTO(BugDTOEntityMapper.getDtoFromBug(bug));
        bugAttachmentWrapperDTO.setAttachmentDTO(AttachmentDTOEntityMapper.getDtoFromAttachment(attachment));

        when(userDao.findUser(bug.getAssignedId().getId())).thenReturn(null);

        bugManager.insertBug(bugAttachmentWrapperDTO);
    }


    @Test(expected = BusinessException.class)
    public void insertBugCouldNotInsert() throws BusinessException {
        Bug bug = createBug();
        Attachment attachment = createAttachment();
        attachment.setBug(bug);

        BugAttachmentWrapperDTO bugAttachmentWrapperDTO = new BugAttachmentWrapperDTO();
        bugAttachmentWrapperDTO.setBugDTO(BugDTOEntityMapper.getDtoFromBug(bug));
        bugAttachmentWrapperDTO.setAttachmentDTO(AttachmentDTOEntityMapper.getDtoFromAttachment(attachment));

        when(userDao.findUser(anyInt())).thenReturn(bug.getAssignedId());
        when(bugDao.insertBug(any(Bug.class))).thenReturn(null);

        bugManager.insertBug(bugAttachmentWrapperDTO);
    }

    @Test
    public void insertBug() throws BusinessException {
        Bug bug = createBug();
        Attachment attachment = createAttachment();
        attachment.setBug(bug);
        attachment.setAttContent(null);

        BugAttachmentWrapperDTO bugAttachmentWrapperDTO = new BugAttachmentWrapperDTO();
        bugAttachmentWrapperDTO.setBugDTO(BugDTOEntityMapper.getDtoFromBug(bug));
        bugAttachmentWrapperDTO.setAttachmentDTO(AttachmentDTOEntityMapper.getDtoFromAttachment(attachment));

        when(userDao.findUser(anyInt())).thenReturn(bug.getAssignedId());
        when(bugDao.insertBug(any(Bug.class))).thenReturn(bug);

        BugAttachmentWrapperDTO returned = bugManager.insertBug(bugAttachmentWrapperDTO);

        assertEquals(returned.getAttachmentDTO(), null);
        assertEquals(returned.getBugDTO().getId(), bugAttachmentWrapperDTO.getBugDTO().getId());
    }

    @Test
    public void findABug() {
        Bug bug = createBug();
        when(bugDao.findBug(bug.getId())).thenReturn(bug);

        BugDTO searchedBugDto = bugManager.findABug(bug.getId());

        assertEquals(bug.getTitle(), searchedBugDto.getTitle());
        assertEquals(bug.getSeverity(), searchedBugDto.getSeverity());
        assertEquals(bug.getAssignedId().getId(), searchedBugDto.getAssignedId().getId());
        assertEquals(bug.getCreatedId().getId(), searchedBugDto.getCreatedId().getId());
        assertEquals(bug.getDescription(), bug.getDescription());
        assertEquals(bug.getFixedVersion(), searchedBugDto.getFixedVersion());
        assertEquals(bug.getVersion(), searchedBugDto.getVersion());
        assertEquals(bug.getStatus(), searchedBugDto.getStatus());
        assertEquals(bug.getTargetDate(), searchedBugDto.getTargetDate());
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
        assertEquals(2, returnedBugs.size());
    }

    @Test(expected = BusinessException.class)
    public void updateBugNotInDbException() throws BusinessException {
        Bug bug = createBug();
        Bug newBug = bug;
        newBug.setTitle("Updated title.");
        Attachment attachment = createAttachment();
        attachment.setBug(bug);

        BugAttachmentWrapperDTO bugAttachmentWrapperDTO = new BugAttachmentWrapperDTO();
        bugAttachmentWrapperDTO.setBugDTO(BugDTOEntityMapper.getDtoFromBug(newBug));
        bugAttachmentWrapperDTO.setAttachmentDTO(AttachmentDTOEntityMapper.getDtoFromAttachment(attachment));

        when(bugDao.findBug(bug.getId())).thenReturn(null);

        bugManager.updateBug(bugAttachmentWrapperDTO, "popm");
    }

    @Test(expected = BusinessException.class)
    public void updateBugNoSuchTransition() throws BusinessException {
        Bug bug = createBug();
        Bug newBug = createBug();
        newBug.setStatus(Status.FIXED);
        Attachment attachment = createAttachment();
        attachment.setBug(bug);

        BugAttachmentWrapperDTO bugAttachmentWrapperDTO = new BugAttachmentWrapperDTO();
        bugAttachmentWrapperDTO.setBugDTO(BugDTOEntityMapper.getDtoFromBug(newBug));
        bugAttachmentWrapperDTO.setAttachmentDTO(AttachmentDTOEntityMapper.getDtoFromAttachment(attachment));

        when(bugDao.findBug(bug.getId())).thenReturn(bug);

        bugManager.updateBug(bugAttachmentWrapperDTO, "popm");
    }

    @Test
    public void updateBug() throws BusinessException {
        Bug bug = createBug();
        Bug newBug = createBug();
        bug.setStatus(Status.NEW);
        newBug.setStatus(Status.IN_PROGRESS);
        Attachment attachment = createAttachment();
        attachment.setBug(bug);

        BugAttachmentWrapperDTO bugAttachmentWrapperDTO = new BugAttachmentWrapperDTO();
        bugAttachmentWrapperDTO.setBugDTO(BugDTOEntityMapper.getDtoFromBug(newBug));
        bugAttachmentWrapperDTO.setAttachmentDTO(AttachmentDTOEntityMapper.getDtoFromAttachment(attachment));
        bugAttachmentWrapperDTO.getAttachmentDTO().setAttContent(null);

        when(bugDao.findBug(bug.getId())).thenReturn(bug);
        when(permissionChecker.checkPermission(anyString(), anyString())).thenReturn(true);

        BugManager bugManagerMock = mock(BugManager.class);
        doNothing().when(bugManagerMock).sendNotification(isA(BugDTO.class), isA(NotificationType.class), isA(String.class), isA(String.class));

        BugAttachmentWrapperDTO returned = bugManager.updateBug(bugAttachmentWrapperDTO, "popm");

        assertEquals(returned.getBugDTO().getId(), bugAttachmentWrapperDTO.getBugDTO().getId());
        assertEquals(returned.getBugDTO().getStatus(), bugAttachmentWrapperDTO.getBugDTO().getStatus());
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
    public void statusIsReachable() {
        Assert.assertFalse(bugManager.statusIsReachable(Status.CLOSED, Status.IN_PROGRESS));
        Assert.assertFalse(bugManager.statusIsReachable(Status.REJECTED, Status.IN_PROGRESS));
        Assert.assertFalse(bugManager.statusIsReachable(Status.CLOSED, Status.NEW));
        Assert.assertTrue(bugManager.statusIsReachable(Status.IN_PROGRESS, Status.NEW));
        Assert.assertTrue(bugManager.statusIsReachable(Status.IN_PROGRESS, Status.CLOSED));
        Assert.assertTrue(bugManager.statusIsReachable(Status.IN_PROGRESS, Status.REJECTED));
        Assert.assertTrue(bugManager.statusIsReachable(Status.IN_PROGRESS, Status.INFO_NEEDED));
        Assert.assertTrue(bugManager.statusIsReachable(Status.IN_PROGRESS, Status.FIXED));
        Assert.assertTrue(bugManager.statusIsReachable(Status.NEW, Status.FIXED));
        Assert.assertTrue(bugManager.statusIsReachable(Status.NEW, Status.CLOSED));
        Assert.assertTrue(bugManager.statusIsReachable(Status.NEW, Status.INFO_NEEDED));
        Assert.assertTrue(bugManager.statusIsReachable(Status.NEW, Status.IN_PROGRESS));
        Assert.assertTrue(bugManager.statusIsReachable(Status.NEW, Status.REJECTED));
        Assert.assertTrue(bugManager.statusIsReachable(Status.INFO_NEEDED, Status.FIXED));
        Assert.assertTrue(bugManager.statusIsReachable(Status.INFO_NEEDED, Status.CLOSED));
        Assert.assertTrue(bugManager.statusIsReachable(Status.INFO_NEEDED, Status.NEW));
        Assert.assertTrue(bugManager.statusIsReachable(Status.INFO_NEEDED, Status.IN_PROGRESS));
        Assert.assertTrue(bugManager.statusIsReachable(Status.INFO_NEEDED, Status.REJECTED));
        Assert.assertTrue(bugManager.statusIsReachable(Status.INFO_NEEDED, Status.INFO_NEEDED));
    }


}
