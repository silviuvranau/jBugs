//package entity;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * Document me.
// *
// * @author msg systems AG; User Name.
// * @since 19.1.2
// */
//@Entity
//@Table(name="roles")
//public class Role extends BaseEntity implements Serializable {
//    @Column(name="type")
//    private String type;
////    @ManyToMany
////    Set<Permission> permissionRoles;
//
////    @ManyToMany(targetEntity = Permission.class)
////    @JoinTable(
////            name="roles_permissions",
////            joinColumns = @JoinColumn(name = "role_id"),
////            inverseJoinColumns = {@JoinColumn(name = "permission_id")}
////    )
////    Set<Permission> permissionRoles;
//
//    @ManyToMany(targetEntity = Permission.class, mappedBy = "roleList")
//    private Set<Permission> permissionList;
//
//    @ManyToMany(mappedBy = "roles")
//    Set<User> users;
//
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public Set<Permission> getPermissionRoles() {
//        return permissionList;
//    }
//
//    public void setPermissionRoles(Set<Permission> permissionRoles) {
//        this.permissionList = permissionRoles;
//    }
//
//    public Set<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Set<User> users) {
//        this.users = users;
//    }
//
//    @Override
//    public String toString() {
//        return "Role{" +
//                "type='" + type + '\'' +
//                '}';
//    }
//}

package entity;
import javax.persistence.*;
import java.util.List;
/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "type")
    private String type;
    @ManyToMany(targetEntity = Permission.class, mappedBy = "roleList")
    private List<Permission> permissionList;

    @ManyToMany(targetEntity = User.class, mappedBy = "roleList")
    private List<User> userList;
    public Role() {
    }
    public Role(String type, List<Permission> permissionList, List<User> userList) {
        this.type = type;
        this.permissionList = permissionList;
        this.userList = userList;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public List<Permission> getPermissionList() {
        return permissionList;
    }
    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }
    public List<User> getUserList() {
        return userList;
    }
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
