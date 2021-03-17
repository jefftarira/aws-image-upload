package com.tarira.awsimageupload.profile;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class UserProfile implements Serializable {

  private UUID userProfileId;
  private String username;
  private String userProfileImageLink;  //S3 key

}
