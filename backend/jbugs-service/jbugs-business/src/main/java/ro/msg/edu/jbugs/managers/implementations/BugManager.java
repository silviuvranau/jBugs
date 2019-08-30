package ro.msg.edu.jbugs.managers.implementations;

import dao.AttachmentDao;
import dao.BugDao;
import dao.UserDao;
import entity.Attachment;
import entity.Bug;
import entity.User;
import entity.enums.Status;
import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.AttachmentDTO;
import ro.msg.edu.jbugs.dto.BugAttachmentWrapperDTO;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.interceptors.Interceptor;
import ro.msg.edu.jbugs.managers.interfaces.BugManagerRemote;
import ro.msg.edu.jbugs.mappers.AttachmentDTOEntityMapper;
import ro.msg.edu.jbugs.mappers.BugDTOEntityMapper;
import ro.msg.edu.jbugs.mappers.UserDTOEntityMapper;
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

    private Map<Status, List<Status>> statusTransitions;

    public BugManager() {
        this.statusTransitions = new HashMap<>();
        createStatusTransitions();
    }

    /**
     * Generate map from diagramm with transition of the statuses.
     */
    private void createStatusTransitions() {
        this.statusTransitions.put(Status.NEW, Arrays.asList(Status.IN_PROGRESSS, Status.REJECTED));
        this.statusTransitions.put(Status.IN_PROGRESSS, Arrays.asList(Status.INFO_NEEDED, Status.REJECTED, Status.FIXED));
        this.statusTransitions.put(Status.FIXED, Arrays.asList(Status.NEW, Status.CLOSED));
        this.statusTransitions.put(Status.INFO_NEEDED, Arrays.asList(Status.IN_PROGRESSS));
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

        Attachment persistedAttachment = attachmentDao.insertAttachment(attachmentToBePersisted);
        if (persistedAttachment.getId() == null) {
            throw new BusinessException("msg-009", "CCreated attachment could not be persisted");
        } else {
            return AttachmentDTOEntityMapper.getDtoFromAttachment(persistedAttachment);
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

    public BugDTO updateBug(BugDTO bugDTO, String username) throws BusinessException {
        Bug searchedBug = bugDao.findBug(bugDTO.getId());
        if (searchedBug != null) {
            searchedBug.setTitle(bugDTO.getTitle());
            searchedBug.setDescription(bugDTO.getDescription());
            searchedBug.setVersion(bugDTO.getVersion());
            searchedBug.setFixedVersion(bugDTO.getFixedVersion());
            searchedBug.setSeverity(bugDTO.getSeverity());
            searchedBug.setTargetDate(bugDTO.getTargetDate());

            try {
                verifyCanSetStatus(bugDTO, searchedBug, username);
                searchedBug.setStatus(bugDTO.getStatus());
            } catch (BusinessException e) {
                throw new BusinessException("msg-001", e.getMessage());
            }

            if (bugDTO.getAssignedId() == null) {
                searchedBug.setAssignedId(null);
            } else if (searchedBug.getAssignedId() == null) {
                User assignedToUser = userDao.findUser(bugDTO.getAssignedId().getId());
                searchedBug.setAssignedId(assignedToUser);
            } else if (bugDTO.getAssignedId().getId() != searchedBug.getAssignedId().getId()) {
                searchedBug.setAssignedId(setAssignedId(bugDTO, searchedBug));
            }

            if (bugDTO.getCreatedId().getId() != searchedBug.getCreatedId().getId()) {
                searchedBug.setCreatedId(setCreatedId(bugDTO, searchedBug));
            }

            return bugDTO;
        } else {
            throw new BusinessException("msg-001", "There is no such bug.");
        }
    }

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

    public User setAssignedId(BugDTO bugDTO, Bug searchedBug) throws BusinessException {
        //if user is assigned to bug
        if (searchedBug.getAssignedId() != null) {
            //take user from db => managed state
            User assignedUser = userDao.findUser(bugDTO.getAssignedId().getId());
            return UserDTOEntityMapper.getSearchedUserFromUserDto(bugDTO.getAssignedId(), assignedUser);

        } else {
            return UserDTOEntityMapper.getUserFromUserDto(bugDTO.getAssignedId());
        }
    }

    public User setCreatedId(BugDTO bugDTO, Bug searchedBug) throws BusinessException {
        if (searchedBug.getCreatedId() != null) {
            User createdUser = userDao.findUser(bugDTO.getCreatedId().getId());
            return UserDTOEntityMapper.getSearchedUserFromUserDto(bugDTO.getCreatedId(), createdUser);
        } else {
            return UserDTOEntityMapper.getUserFromUserDto(bugDTO.getCreatedId());
        }
    }

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
