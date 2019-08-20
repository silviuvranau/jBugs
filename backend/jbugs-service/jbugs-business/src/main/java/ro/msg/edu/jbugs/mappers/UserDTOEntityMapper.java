package ro.msg.edu.jbugs.mappers;

import entity.User;
import ro.msg.edu.jbugs.dto.UserDTO;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class UserDTOEntityMapper {

    private UserDTOEntityMapper(){}

    public static User getUserFromUserDto(UserDTO userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setCounter(userDTO.getCounter());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setMobileNumber(userDTO.getMobileNumber());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setStatus(userDTO.getStatus());

//        List<Bug> assignedBugs = userDTO.getAssignedBugs().stream().map(BugDTOEntityMapper::getBugFromDto).collect(Collectors.toList());
//        user.setAssignedBugs(new HashSet<Bug>(assignedBugs));
//
//        List<Bug> createdBugs = userDTO.getCreatedBugs().stream().map(BugDTOEntityMapper::getBugFromDto).collect(Collectors.toList());
//        user.setCreatedBugs(new HashSet<Bug>(createdBugs));
//
//        List<Notification> notifications = userDTO.getNotifications().stream().map(NotificationDTOEntityMapper::getNotificationFromDto).collect(Collectors.toList());
//        user.setNotifications(new HashSet<Notification>(notifications));
//
//        List<Comment> comments = userDTO.getComments().stream().map(CommentDTOEntityMapper::getCommentFromDto).collect(Collectors.toList());
//        user.setComments(new HashSet<Comment>(comments));

        return user;
    }

    public static UserDTO getDtoFromUser(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setCounter(user.getCounter());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setMobileNumber(user.getMobileNumber());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setStatus(user.isStatus());

//        List<BugDTO> assignedBugs = user.getAssignedBugs().stream().map(BugDTOEntityMapper::getDtoFromBug).collect(Collectors.toList());
//        userDTO.setAssignedBugs(new HashSet<BugDTO>(assignedBugs));
//
//        List<BugDTO> createdBugs = user.getCreatedBugs().stream().map(BugDTOEntityMapper::getDtoFromBug).collect(Collectors.toList());
//        userDTO.setCreatedBugs(new HashSet<BugDTO>(createdBugs));
//
//        List<NotificationDTO> notifications = user.getNotifications().stream().map(NotificationDTOEntityMapper::getDtoFromNotification).collect(Collectors.toList());
//        userDTO.setNotifications(new HashSet<NotificationDTO>(notifications));
//
//        List<CommentDTO> comments = user.getComments().stream().map(CommentDTOEntityMapper::getDtoFromComment).collect(Collectors.toList());
//        userDTO.setComments(new HashSet<CommentDTO>(comments));

        return userDTO;
    }
}
