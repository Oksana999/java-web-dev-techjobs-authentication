package org.launchcode.javawebdevtechjobsauthentication.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Oksana
 */
public class RegisterDTO extends LoginDTO{

   private String verifyPassword;

   public String getVerifyPassword() {
      return verifyPassword;
   }

   public void setVerifyPassword(String verifyPassword) {
      this.verifyPassword = verifyPassword;
   }
}
