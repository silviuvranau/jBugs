//package entity;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
///**
// * Document me.
// *
// * @author msg systems AG; User Name.
// * @since 19.1.2
// */
//@Entity
//@Table(name="permissions")
//public class Permission extends BaseEntity implements Serializable {
//    @Column(name="description")
//    private String description;
//    @Column(name="type")
//    private String type;
////    @ManyToMany(targetEntity = Role.class, mappedBy = "permissionRoles")
////    Set<Role> roles;
//
//    @ManyToMany(targetEntity = Role.class)
//    @JoinTable(name = "roles_permissions", joinColumns = @JoinColumn(name = "role_id"),inverseJoinColumns = {@JoinColumn(name= "permission_id")})
//    private Set<Role> roleList;
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public Set<Role> getRoles() {
//        return roleList;
//    }
//
//    public void setRoles(Set<Role> roles) {
//        this.roleList = roles;
//    }
//
//    @Override
//    public String toString() {
//        return "Permission{" +
//                "description='" + description + '\'' +
//                ", type='" + type + '\'' +
//                '}';
//    }
//}


package entity;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Entity
@Table(name = "permissions")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();

    public Permission() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}