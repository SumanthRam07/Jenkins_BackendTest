package LearnovationAcademy.WebApplication.Student;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CascadeType;

import LearnovationAcademy.WebApplication.Subject.Courses;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
@JsonFilter("StudentFilter")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;
    
    @Column(nullable=false , unique =true)

    String  email ; 
    
//    
//    @ManyToMany(mappedBy = "students",cascade = { CascadeType.PERSIST, CascadeType.MERGE })
//    @
//   
//    private LinkedList<Courses> courses ;

   
    
    
    

    // Default constructor
    public Student() {}

    public Student(String username, String password  , String email) {
        this.username = username;
        this.password = password;   
        this.email=email ; 
//        this.courses  = courses ;
    }

   
//
//	public LinkedList<Courses> getCourses() {
//		return courses;
//	}
//
//	public void setCourses(LinkedList<Courses> courses) {
//		this.courses = courses;
//	}

	public Integer getId() {
        return id;
    }

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
	
	
	 
	

}
