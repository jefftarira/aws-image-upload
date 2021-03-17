package com.tarira.awsimageupload.profile;

import com.tarira.awsimageupload.datastore.FakeUserProfileDataStore;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class UserProfileDataAccessService {

  private final FakeUserProfileDataStore fakeUserProfileDataStore;

  List<UserProfile> getUserProfiles() {
    return fakeUserProfileDataStore.getUserProfiles();
  }

}
