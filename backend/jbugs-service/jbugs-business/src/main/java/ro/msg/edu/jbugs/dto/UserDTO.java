package ro.msg.edu.jbugs.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */

public class UserDTO implements Serializable {
    private Integer id;
    private Integer counter;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$",
            message = "INVALID_FIRST_NAME_EXCEPTION")
    private String firstName;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$",
            message = "INVALID_LAST_NAME_EXCEPTION")
    private String lastName;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@msggroup\\.com$",
            message = "INVALID_EMAIL_EXCEPTION")
    private String email;
    @NotNull
    @Pattern(regexp = "(\\+4[0][7][0-9]{8})|(\\(?\\+\\(?49\\)?[ ()]?([- ()]?\\d[- ()]?){10})",
            message = "INVALID_MOBILE_NUMBER_EXCEPTION")
    private String mobileNumber;
    private String username;
    private String password;
    private boolean status;
    private List<Integer> roleIds;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer ID) {
        this.id = ID;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", counter=" + counter +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", roleIds=" + roleIds +
                '}';
    }
}
