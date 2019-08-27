package ro.msg.edu.jbugs.managers.implementations;

import dao.BugDao;
import dao.UserDao;
import entity.Bug;
import entity.User;
import entity.enums.Status;
import exceptions.BusinessException;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.interceptors.Interceptor;
import ro.msg.edu.jbugs.managers.interfaces.BugManagerRemote;
import ro.msg.edu.jbugs.mappers.BugDTOEntityMapper;
import ro.msg.edu.jbugs.mappers.UserDTOEntityMapper;

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

    public BugDTO insertBug(BugDTO bugDTO) throws BusinessException {
        if (bugDao.findBug(bugDTO.getId()) == null) {
            Bug bugToBeInserted = BugDTOEntityMapper.getBugFromDto(bugDTO);

            //if Users are already in db
            if (bugToBeInserted.getAssignedId() != null) {
                User assignedUser = userDao.findUser(bugToBeInserted.getAssignedId().getId());
                bugToBeInserted.setAssignedId(assignedUser);
            }
            if (bugToBeInserted.getCreatedId() != null) {
                User createdByUser = userDao.findUser(bugToBeInserted.getCreatedId().getId());
                bugToBeInserted.setCreatedId(createdByUser);
            }

            bugDao.insertBug(bugToBeInserted);

            return bugDTO;
        } else {
            throw new BusinessException("msg-001", "The given user is already in the database.");
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

    public BugDTO updateBug(Integer id, BugDTO bugDTO) throws BusinessException {
        if (id == bugDTO.getId()) {
            Bug searchedBug = bugDao.findBug(bugDTO.getId());

            if (searchedBug != null) {

                searchedBug.setTitle(bugDTO.getTitle());
                searchedBug.setDescription(bugDTO.getDescription());
                searchedBug.setVersion(bugDTO.getVersion());
                searchedBug.setFixedVersion(bugDTO.getFixedVersion());
                searchedBug.setSeverity(bugDTO.getSeverity());
                searchedBug.setTargetDate(bugDTO.getTargetDate());

                //check if status is reachable
                if (statusIsReachable(searchedBug.getStatus(), bugDTO.getStatus())) {
                    searchedBug.setStatus(bugDTO.getStatus());
                } else {
                    throw new BusinessException("msg-001", "There is no such status transition.");
                }

                //changed assigned to and created by
                if (bugDTO.getAssignedId().getId() != searchedBug.getAssignedId().getId()) {
                    //if user is assigned to bug
                    if (searchedBug.getAssignedId() != null) {
                        //take user from db => managed state
                        User assignedUser = userDao.findUser(searchedBug.getId());
                        searchedBug.setAssignedId(UserDTOEntityMapper.getSearchedUserFromUserDto(bugDTO.getAssignedId(), assignedUser));
                    } else {
                        searchedBug.setAssignedId(UserDTOEntityMapper.getUserFromUserDto(bugDTO.getAssignedId()));
                    }
                }
                //same but with created by User
                if (bugDTO.getCreatedId().getId() != searchedBug.getCreatedId().getId()) {
                    if (searchedBug.getCreatedId() != null) {
                        User createdUser = userDao.findUser(searchedBug.getId());
                        searchedBug.setCreatedId(UserDTOEntityMapper.getSearchedUserFromUserDto(bugDTO.getCreatedId(), createdUser));
                    } else {
                        searchedBug.setCreatedId(UserDTOEntityMapper.getUserFromUserDto(bugDTO.getCreatedId()));
                    }
                }

                return bugDTO;
            } else {
                throw new BusinessException("msg-001", "There is no such bug.");
            }
        } else {
            throw new BusinessException("msg-001", "Given id's are not equal.");
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
