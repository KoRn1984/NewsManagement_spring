package by.itacademy.matveenko.jd2.bean;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.base.Objects;
import by.itacademy.matveenko.jd2.bean.User;

@Entity
@Table(name="users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@Column(name="id")
	private Integer id;
	
	@Column(name="login")
	private String login;
	
	@Column(name="password")
    private String password;
    
	@Column(name="name")
	private String userName;
	
	@Column(name="surname")
    private String userSurname;
    
	@Column(name="email")
    private String email;
    
	@Enumerated(EnumType.STRING)
	@JoinColumn(name="role")	
	private UserRole role;   

    public User() {
    }    
     
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
    	this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
    
    public static class Builder {
        private User newUser;
        
        public Builder() {
            this.newUser = new User();
        }
        
        public Builder withId(Integer id) {
            newUser.setId(id);
            return this;
        }

        public Builder withLogin(String login) {
            newUser.setLogin(login);
            return this;
        }
        public Builder withPassword(String password) {
            newUser.setPassword(password);
            return this;
        }
        public Builder withUserName(String userName) {
            newUser.setUserName(userName);
            return this;
        }
        public Builder withUserSurname(String userSurname) {
            newUser.setUserSurname(userSurname);
            return this;
        }

        public Builder withEmail(String email) {
            newUser.setEmail(email);
            return this;
        }

        public Builder withRole(UserRole role) {
            newUser.setRole(role);
            return this;
        }
        
        public User build() {
            return newUser;
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User that = (User) obj;
        return Objects.equal(id, that.id) && Objects.equal(login, that.login) && Objects.equal(password, that.password) 
        	   && Objects.equal(userName, that.userName) && Objects.equal(userSurname, that.userSurname) 
        	   && Objects.equal(email, that.email) && Objects.equal(role, that.role);
    }
    
    @Override
	public int hashCode() {
    	int result = (int) (id ^ (id >>> 32));
    	result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userSurname != null ? userSurname.hashCode() : 0);		
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (role != null ? role.hashCode() : 0);
		return result;
	}

	@Override
    public String toString() {
        return "User {" +
        		"id='" + id + '\'' +
        		", login='" + login + '\'' +
                ", password=" + password +
                ", userName='" + userName + '\'' +
                ", userSurname='" + userSurname + '\'' +
                ", email='" + email + '\'' +                
                ", role='" + role + '\'' +
                '}';
    }  
}