package LearnovationAcademy.WebApplication.ServiceClass;

import java.util.List;

import javax.security.sasl.AuthenticationException;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import LearnovationAcademy.WebApplication.AuthenticationRequest.AuthenticationRequest;
import LearnovationAcademy.WebApplication.CustomException.UserNotFoundException;
import LearnovationAcademy.WebApplication.Student.Student;
import LearnovationAcademy.WebApplication.StudentJPAInitializer.StudentRepository;


@Component
public class studentService {
	
	
	
	@Autowired
	StudentRepository studentRepository ; 
	
	@Autowired
	 PasswordEncoder passwordEncoder ; 
	
//	@Autowired
//	UserDetailsService userDetailsService ;
	
	
	@Autowired
	LearnovationAcademy.WebApplication.Security.JwtGenerator  JwtGenerator ;
	
	
	@Autowired
	javax.sql.DataSource dataSource; 
	
	
	@Autowired
	BCryptPasswordEncoder passwordEncode ;




	public ResponseEntity<MappingJacksonValue> GetStudents()
	{



		List<Student>studentList =	studentRepository.findAll() ;
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(studentList) ;


		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("email");

		FilterProvider filters = new SimpleFilterProvider().addFilter("StudentFilter" ,filter ) ;


		mappingJacksonValue.setFilters(filters);




return	ResponseEntity.ok(mappingJacksonValue) ;

	}

	 
	
	
	
	public ResponseEntity<String>  createStudent( AuthenticationRequest authenticationRequest)
	{
		
		String encodedPassword = passwordEncoder.encode(authenticationRequest.getPassword());
	    Student newStudent = new Student(authenticationRequest.getUsername() ,encodedPassword , authenticationRequest.getEmail() ) ;
	    
	    
	    
	    
	    studentRepository.save(newStudent);
	    
	    

//		var user = 
//	    		User.withUsername(authenticationRequest.getUsername()).password(authenticationRequest.getPassword()).passwordEncoder(str-> passwordEncode.encode(str)).roles("USER").build() ;
//	    
//	    JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource) ; 
//	    
//	   
//	    
//	    jdbcUserDetailsManager.createUser(user);
//	   
	   
	    
	    
	    
	    return ResponseEntity.ok("Student created ") ;
		
		
		
		
		
		
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	

	public ResponseEntity<String> AuthenticateStudent( String email , String password) throws AuthenticationException, org.springframework.security.core.AuthenticationException
	
	{
		
		  Student student = studentRepository.findByEmail(email);

		    if (student == null) {
 
		    	  throw new UserNotFoundException("User cannot be found, please sign up");
		        
		    }
		   
		    if (passwordEncoder.matches(password, student.getPassword())) {
		        // Authentication successful, generate JWT
		    	
		   
		    	Authentication authentication = new UsernamePasswordAuthenticationToken(student.getUserName(), student.getPassword());
		        

          String jwtToken =JwtGenerator.CreateString(authentication); 
          
          

      return ResponseEntity.ok()
				.header( org.springframework.http.HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
				    .body(jwtToken);
	    }
		    	
		    	  
		     else {
		        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid username or password");
		    }
		}

	
	
public ResponseEntity<String> AuthenticateStudentGoogle( String email ) throws AuthenticationException, org.springframework.security.core.AuthenticationException
	
	{
		
		  Student student = studentRepository.findByEmail(email);

		    if (student == null) {
 
		    	  throw new UserNotFoundException("User cannot be found, please sign up");
		        
		    }
		    else
		    {
		        // Authentication successful, generate JWT
		    	
		   
		    	Authentication authentication = new UsernamePasswordAuthenticationToken(student.getUserName(), student.getPassword());
		        

          String jwtToken =JwtGenerator.CreateString(authentication); 
          
          

      return ResponseEntity.ok()
				.header( org.springframework.http.HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
				    .body(jwtToken);
	     }
		    	
		    	  

		    
}
















public ResponseEntity<String> DeleteUser(String email) {
	
	Student student = studentRepository.findByEmail(email);

    if (student == null) {

    	  throw new UserNotFoundException("User cannot be found in the database to delete");
        
    }
    else
    {
    	
    	  
        
    	studentRepository.deleteById(student.getId());
   
  

return ResponseEntity.ok().body(" User deleted successfullly") ;
		
    	
	

}











}


}
