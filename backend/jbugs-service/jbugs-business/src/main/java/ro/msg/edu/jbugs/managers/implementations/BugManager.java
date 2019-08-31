package ro.msg.edu.jbugs.managers.implementations;

import dao.AttachmentDao;
import dao.BugDao;
import dao.UserDao;
import entity.Attachment;
import entity.Bug;
import entity.User;
import entity.enums.NotificationType;
import entity.enums.Status;
import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.AttachmentDTO;
import ro.msg.edu.jbugs.dto.BugAttachmentWrapperDTO;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.interceptors.Interceptor;
import ro.msg.edu.jbugs.managers.interfaces.AttachmentManagerRemote;
import ro.msg.edu.jbugs.managers.interfaces.BugManagerRemote;
import ro.msg.edu.jbugs.mappers.AttachmentDTOEntityMapper;
import ro.msg.edu.jbugs.mappers.BugDTOEntityMapper;
import ro.msg.edu.jbugs.mappers.UserDTOEntityMapper;
import ro.msg.edu.jbugs.util.NotificationUtils;
import ro.msg.edu.jbugs.util.PermissionChecker;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Stateless
@Interceptors(Interceptor.class)
public class BugManager implements BugManagerRemote {
    @EJB
    private BugDao bugDao;

    @EJB
    private UserDao userDao;

    @EJB
    private AttachmentDao attachmentDao;

    @EJB
    PermissionChecker permissionChecker;

    @EJB
    private NotificationUtils notificationUtils;

    @EJB
    private AttachmentManagerRemote attachmentManager;

    private Map<Status, List<Status>> statusTransitions;

    public BugManager() {
        this.statusTransitions = new HashMap<>();
        createStatusTransitions();
    }

    /**
     * Generate map from diagramm with transition of the statuses.
     */
    private void createStatusTransitions() {
        this.statusTransitions.put(Status.NEW, Arrays.asList(Status.IN_PROGRESS, Status.REJECTED));
        this.statusTransitions.put(Status.IN_PROGRESS, Arrays.asList(Status.INFO_NEEDED, Status.REJECTED, Status.FIXED));
        this.statusTransitions.put(Status.FIXED, Arrays.asList(Status.NEW, Status.CLOSED));
        this.statusTransitions.put(Status.INFO_NEEDED, Arrays.asList(Status.IN_PROGRESS));
        this.statusTransitions.put(Status.REJECTED, Arrays.asList(Status.CLOSED, Status.REJECTED));
        this.statusTransitions.put(Status.CLOSED, Arrays.asList());
    }

    /**
     * Method for checking if there is a transition between two statuses.
     *
     * @param statusOne
     * @param statusTwo
     * @return true/false
     */
    public boolean statusIsReachable(Status statusOne, Status statusTwo) {
        if(statusOne == statusTwo){
            return true;
        }
        List<Status> visited = new ArrayList<>();
        LinkedList<Status> queue = new LinkedList<>();

        //mark current node (status) as visited
        visited.add(statusOne);
        queue.add(statusOne);

        //there are nodes to be visited
        while (queue.size() != 0) {
            Status actualStatus = queue.poll();

            //adjacent nodes
            List<Status> adjStatuses = this.statusTransitions.get(actualStatus);
            for (Status s : adjStatuses) {
                if (s.equals(statusTwo)) {
                    return true;
                } else {
                    if (!visited.contains(s)) {
                        visited.add(s);
                        queue.add(s);
                    }
                }
            }
        }

        return false;
    }

    public BugAttachmentWrapperDTO insertBug(BugAttachmentWrapperDTO bugAttachmentWrapper) throws BusinessException {
        if (bugAttachmentWrapper.getBugDTO() == null) {
            throw new BusinessException("msg-009", "Empty entity received.");
        }

        Bug bugToBePersisted = BugDTOEntityMapper.getBugFromDto(bugAttachmentWrapper.getBugDTO());

        if (bugToBePersisted.getAssignedId() != null) {
            User assignedUser = userDao.findUser(bugToBePersisted.getAssignedId().getId());
            if (assignedUser != null) {
                bugToBePersisted.setAssignedId(assignedUser);
            } else {
                throw new BusinessException("msg-009", "Assigned user does not exist.");
            }
        } else {
            bugToBePersisted.setAssignedId(null);
        }

        if (bugToBePersisted.getCreatedId() != null) {
            User createdUser = userDao.findUser(bugToBePersisted.getCreatedId().getId());
            if (createdUser != null) {
                bugToBePersisted.setCreatedId(createdUser);
            } else {
                throw new BusinessException("msg-009", "The user who created the bug does not exist.");
            }
        } else {
            throw new BusinessException("msg-009", "The user who created the bug must be assigned");
        }

        Bug persistedBug = bugDao.insertBug(bugToBePersisted);
        if (persistedBug.getId() == null) {
            throw new BusinessException("msg-009", "Created bug could not be persisted");
        }

        if (bugAttachmentWrapper.getAttachmentDTO().getAttContent() != null && bugAttachmentWrapper.getAttachmentDTO().getAttContent().length != 0) {
            AttachmentDTO persistedAttachmentDTO = insertAttachment(bugAttachmentWrapper.getAttachmentDTO(), persistedBug);
            return new BugAttachmentWrapperDTO(
                    BugDTOEntityMapper.getDtoFromBug(persistedBug),
                    persistedAttachmentDTO
            );
        } else {
            return new BugAttachmentWrapperDTO(
                    BugDTOEntityMapper.getDtoFromBug(persistedBug),
                    null
            );
        }
    }


    public AttachmentDTO insertAttachment(AttachmentDTO attachmentDTO, Bug assignedBug) throws BusinessException {
        attachmentDTO.setBug(BugDTOEntityMapper.getDtoFromBug(assignedBug));
        Attachment attachmentToBePersisted = AttachmentDTOEntityMapper.getAttachmentFromDto(attachmentDTO);
        //attachmentToBePersisted.setBug(assignedBug);

        AttachmentDTO persistedAttachment = attachmentManager.insertAttachment(attachmentToBePersisted);
        if (persistedAttachment.getId() == null) {
            throw new BusinessException("msg-009", "CCreated attachment could not be persisted");
        } else {
            return persistedAttachment;
        }
    }


    public BugDTO findABug(Integer id) {
        Bug bugToBeFound = bugDao.findBug(id);
        BugDTO bugDTO = BugDTOEntityMapper.getDtoFromBug(bugToBeFound);
        return bugDTO;
    }

    public List<BugDTO> findAllBugs() {
        List<Bug> bugs = bugDao.findAllBugs();
        return bugs.stream().map(BugDTOEntityMapper::getDtoFromBug).collect(Collectors.toList());
    }

    public List<BugDTO> findBugsByCreatedId(UserDTO userDTO) {
        User u = UserDTOEntityMapper.getUserFromUserDto(userDTO);
        List<Bug> bugs = bugDao.findByCreatedId(u);
        return bugs.stream().map(BugDTOEntityMapper::getDtoFromBug).collect(Collectors.toList());
    }

    public List<BugDTO> findBugsByAssignedId(UserDTO userDTO) {
        User u = UserDTOEntityMapper.getUserFromUserDto(userDTO);
        List<Bug> bugs = bugDao.findByAssignedId(u);
        return bugs.stream().map(BugDTOEntityMapper::getDtoFromBug).collect(Collectors.toList());
    }

    public Integer deleteExceedingBugs() {
        return bugDao.deleteBugs();
    }

    public BugAttachmentWrapperDTO updateBug(BugAttachmentWrapperDTO bugAttWrapper, String username) throws BusinessException {
        Bug searchedBug = bugDao.findBug(bugAttWrapper.getBugDTO().getId());
        if (searchedBug != null) {
            searchedBug.setTitle(bugAttWrapper.getBugDTO().getTitle());
            searchedBug.setDescription(bugAttWrapper.getBugDTO().getDescription());
            searchedBug.setVersion(bugAttWrapper.getBugDTO().getVersion());
            searchedBug.setFixedVersion(bugAttWrapper.getBugDTO().getFixedVersion());
            searchedBug.setSeverity(bugAttWrapper.getBugDTO().getSeverity());
            searchedBug.setTargetDate(bugAttWrapper.getBugDTO().getTargetDate());

            //change status
            try {
                verifyCanSetStatus(bugAttWrapper.getBugDTO(), searchedBug, username);
                searchedBug.setStatus(bugAttWrapper.getBugDTO().getStatus());
                if (bugAttWrapper.getBugDTO().getStatus() == Status.CLOSED) {
                    sendNotification(bugAttWrapper.getBugDTO(), NotificationType.BUG_CLOSED, "Your bug has just been closed.", "BUG_MANAGEMENT");
                } else {
                    sendNotification(bugAttWrapper.getBugDTO(), NotificationType.BUG_STATUS_UPDATED, "Your bug's status has just been updated.", "BUG_CLOSED");

                }
            } catch (BusinessException e) {
                throw new BusinessException("msg-001", e.getMessage());
            }

            //chenge users
            if (bugAttWrapper.getBugDTO().getAssignedId() == null) {
                searchedBug.setAssignedId(null);
            } else if (searchedBug.getAssignedId() == null) {
                User assignedToUser = userDao.findUser(bugAttWrapper.getBugDTO().getAssignedId().getId());
                searchedBug.setAssignedId(assignedToUser);
            } else if (bugAttWrapper.getBugDTO().getAssignedId().getId() != searchedBug.getAssignedId().getId()) {
                searchedBug.setAssignedId(getUserForAssignedId(bugAttWrapper.getBugDTO(), searchedBug));
            }

            if (bugAttWrapper.getBugDTO().getCreatedId().getId() != searchedBug.getCreatedId().getId()) {
                searchedBug.setCreatedId(getUserForCreatedId(bugAttWrapper.getBugDTO(), searchedBug));
            }

            //change attachment
            if(bugAttWrapper.getAttachmentDTO().getAttContent() != null && bugAttWrapper.getAttachmentDTO().getAttContent().length != 0 ){
                AttachmentDTO attachmentInDatabase = attachmentManager.findAttachmentOfBug(BugDTOEntityMapper.getBugFromDto(bugAttWrapper.getBugDTO()));
                //if attachment of bug exists in database, we delete and insert the newly received attachment
                if (attachmentInDatabase != null){
                    attachmentManager.deleteAttachment(attachmentInDatabase.getId());
                    AttachmentDTO persistedNewAttachment = insertAttachment(bugAttWrapper.getAttachmentDTO(), BugDTOEntityMapper.getBugFromDto(bugAttWrapper.getBugDTO()));
                }
                else{
                    AttachmentDTO persistedNewAttachment = insertAttachment(bugAttWrapper.getAttachmentDTO(), BugDTOEntityMapper.getBugFromDto(bugAttWrapper.getBugDTO()));
                }
            }

            sendNotification(bugAttWrapper.getBugDTO(), NotificationType.BUG_UPDATED, "Your bug was just updated.", "BUG_MANAGEMENT");

            return new BugAttachmentWrapperDTO(bugAttWrapper.getBugDTO(), bugAttWrapper.getAttachmentDTO());
        } else {
            throw new BusinessException("msg-001", "There is no such bug.");
        }
    }

    /**
     * Method for sending UPDATE_BUG notification to assignedTo and createdBy Users of the bug.
     * It checks if the two users are the same.
     *
     * @param bugDTO
     */
    public void sendNotification(BugDTO bugDTO, NotificationType type, String message, String permissionType) throws BusinessException {
        //bug has also assignedBy User
        if (bugDTO.getAssignedId() != null && permissionChecker.checkPermission(bugDTO.getAssignedId().getUsername(), permissionType)) {
            //same user => send only one notif
            if (bugDTO.getAssignedId().getId() == bugDTO.getCreatedId().getId()) {
                User user = userDao.findUser(bugDTO.getAssignedId().getId());
                user = UserDTOEntityMapper.getSearchedUserFromUserDto(bugDTO.getAssignedId(), user);
                notificationUtils.sendNotification("http://localhost:4200/dashboard/bugs?bugId="+bugDTO.getId(), user, type, message);
            }
            //two different users
            else {
                //send notif for assigned to user
                User assignedToUser = userDao.findUser(bugDTO.getAssignedId().getId());
                assignedToUser = UserDTOEntityMapper.getSearchedUserFromUserDto(bugDTO.getAssignedId(), assignedToUser);
                notificationUtils.sendNotification("http://localhost:4200/dashboard/bugs?bugId=" + bugDTO.getId(), assignedToUser, type, message);

                //check the permission and send notifs
                if (permissionChecker.checkPermission(bugDTO.getCreatedId().getUsername(), permissionType)) {
                    User createdByUser = userDao.findUser(bugDTO.getCreatedId().getId());
                    createdByUser = UserDTOEntityMapper.getSearchedUserFromUserDto(bugDTO.getCreatedId(), createdByUser);
                    notificationUtils.sendNotification("http://localhost:4200/dashboard/bugs?bugId=" + bugDTO.getId(), createdByUser, type, message);
                }
            }
        }
        //bug has only user for createdBy
        else if (permissionChecker.checkPermission(bugDTO.getCreatedId().getUsername(), "BUG_MANAGEMENT")) {
            User createdByUser = userDao.findUser(bugDTO.getCreatedId().getId());
            createdByUser = UserDTOEntityMapper.getSearchedUserFromUserDto(bugDTO.getCreatedId(), createdByUser);
            notificationUtils.sendNotification("http://localhost:4200/dashboard/bugs?bugId=" + bugDTO.getId(), createdByUser, type, message);
        }
    }

    /**
     * Method that checks: if there is a transition between the old state and the new state of the bug
     * if the user has necessary rights to close a bu that is in FIXED state
     *
     * @param bugDTO
     * @param searchedBug
     * @param username
     * @return
     * @throws BusinessException
     */
    public boolean verifyCanSetStatus(BugDTO bugDTO, Bug searchedBug, String username) throws BusinessException {
        if (bugDTO.getStatus() == Status.CLOSED) {
            if (searchedBug.getStatus() == Status.FIXED) {
                try {
                    return permissionChecker.checkPermission(username, "BUG_CLOSE");
                } catch (BusinessException e) {
                    throw new BusinessException("msg-001", e.getMessage());
                }
            } else {
                //check if status is reachable
                if (statusIsReachable(searchedBug.getStatus(), bugDTO.getStatus())) {
                    return true;
                } else {
                    throw new BusinessException("msg-001", "There is no such status transition.");
                }
            }
        } else {
            if (statusIsReachable(searchedBug.getStatus(), bugDTO.getStatus())) {
                return true;
            } else {
                throw new BusinessException("msg-001", "There is no such status transition.");
            }
        }

    }

    public User getUserForAssignedId(BugDTO bugDTO, Bug searchedBug) throws BusinessException {
        //if user is assigned to bug
        if (searchedBug.getAssignedId() != null) {
            //take user from db => managed state
            User assignedUser = userDao.findUser(bugDTO.getAssignedId().getId());
            return UserDTOEntityMapper.getSearchedUserFromUserDto(bugDTO.getAssignedId(), assignedUser);

        } else {
            return UserDTOEntityMapper.getUserFromUserDto(bugDTO.getAssignedId());
        }
    }

    public User getUserForCreatedId(BugDTO bugDTO, Bug searchedBug) throws BusinessException {
        if (searchedBug.getCreatedId() != null) {
            User createdUser = userDao.findUser(bugDTO.getCreatedId().getId());
            return UserDTOEntityMapper.getSearchedUserFromUserDto(bugDTO.getCreatedId(), createdUser);
        } else {
            return UserDTOEntityMapper.getUserFromUserDto(bugDTO.getCreatedId());
        }
    }

    /**
     * Method that checks if a user has unclosed bugs.
     * @param userDto
     * @return
     * @throws BusinessException
     */
    public boolean canDeactivateUser(UserDTO userDto) throws BusinessException {
        if (bugDao.getClosedBugsByAssignedId(UserDTOEntityMapper.getUserFromUserDto(userDto)) == 0) {
            return true;
        } else {
            throw new BusinessException("msg-001", "The user has unclosed bugs.");
        }
    }

//    public void insertBug(BugDTO bugDTO){
//        Bug bug = BugDTOEntityMapper.getBugFromDto(bugDTO);
//        bugDao.insertBug(bug);
//    }
//
//    public List<BugDTO> findAllBugs(){
//        List<Bug> bugs = bugDao.findAllBugs();
//        return bugs.stream().map(BugDTOEntityMapper :: getDtoFromBug).collect(Collectors.toList());
//    }
//
//    public BugDTO findBug (Integer id){
//        Bug bug = bugDao.findBug(id);
//        BugDTO bugDTO = BugDTOEntityMapper.getDtoFromBug(bug);
//        return bugDTO;
//    }
//
//    public List<BugDTO> findAllBugs(User managers){
//        List<Bug> bugs = bugDao.findAllCreatedByUser(managers);
//
//        return bugs.stream().map(BugDTOEntityMapper :: getDtoFromBug).collect(Collectors.toList());
//    }
//
//    public Integer countBugs(User managers){
//        Integer count = bugDao.countCreatedBugs(managers);
//        return count;
//    }
}
