package com.tarira.awsimageupload.datastore;

import com.tarira.awsimageupload.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {

  private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

  static {
    USER_PROFILES.add(new UserProfile(UUID.fromString("32e173a9-e43a-4ff7-830f-1314e9e65814"), "jtarira", null));
    USER_PROFILES.add(new UserProfile(UUID.fromString("62b6a807-d4b5-4fe6-bb4d-9f709d70db77"), "acevallos", null));
  }

  public List<UserProfile> getUserProfiles() {
    return USER_PROFILES;
  }

}
