package org.launchcode.javawebdevtechjobsauthentication.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Oksana
 */
public class LoginDTO {
   @NotNull
   @NotBlank
   @Size(min = 3, max = 20, message = "Invalid username. Must be between 3 and 20 characters")
   private String userName;

   @NotNull
   @NotBlank
   @Size(min = 3, max = 20, message = "Invalid password. Must be between 3 and 20 characters")
   private String password;

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }
}
