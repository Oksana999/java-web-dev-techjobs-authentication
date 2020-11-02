package org.launchcode.javawebdevtechjobsauthentication.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author Oksana
 */
@Entity
public class User extends AbstractEntity {

   private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


   @NotBlank(message = "User first name is required")
   private String username;

   @NotBlank(message = "Password is required")
   private String pwHash;


   public User() {
   }

   public User(String username, String password) {
      this.username = username;
      this.pwHash = encoder.encode(password);
   }

   public boolean isMatchingPassword(String password){
      return encoder.matches(password, pwHash);
   }
   public String getFirstName() {
      return username;
   }

   public void setFirstName(String firstName) {
      this.username = firstName;
   }

   public String getPassword() {
      return pwHash;
   }

   public void setPassword(String password) {
      this.pwHash = password;
   }
}
