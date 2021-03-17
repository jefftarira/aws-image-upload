package com.tarira.awsimageupload.profile;

import com.tarira.awsimageupload.bucket.BucketName;
import com.tarira.awsimageupload.filestore.FileStore;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
@AllArgsConstructor
public class UserProfileService {

  private final UserProfileDataAccessService userProfileDataAccessService;
  private final FileStore fileStore;

  List<UserProfile> getUserProfiles() {
    return userProfileDataAccessService.getUserProfiles();
  }

  public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
    //TODO: implement and check files for upload  to s3
    isFileEmpty(file);

    isImage(file);

    UserProfile user = getUserProfileOrThrow(userProfileId);

    Map<String, String> metadata = extractMetadata(file);

    String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfileId());
    String fileName = String.format("%s", UUID.randomUUID());
    try {
      fileStore.save(path, fileName, Optional.of(metadata), file.getInputStream());
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  private Map<String, String> extractMetadata(MultipartFile file) {
    Map<String, String> metadata = new HashMap<>();
    metadata.put("Content-Type", file.getContentType());
    metadata.put("Content-Length", String.valueOf(file.getSize()));
    return metadata;
  }

  private UserProfile getUserProfileOrThrow(UUID userProfileId) {
    return userProfileDataAccessService
            .getUserProfiles()
            .stream()
            .filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found", userProfileId)));
  }

  private void isImage(MultipartFile file) {
    if (!Arrays.asList(
            IMAGE_JPEG.getMimeType(),
            IMAGE_PNG.getMimeType(),
            IMAGE_GIF.getMimeType()).contains(file.getContentType()))
      throw new IllegalStateException("File must be an image [" + file.getContentType() + "]");
  }

  private void isFileEmpty(MultipartFile file) {
    if (file.isEmpty())
      throw new IllegalStateException("Cannot upload empty file [" + file.getSize() + " ]");
  }
}
