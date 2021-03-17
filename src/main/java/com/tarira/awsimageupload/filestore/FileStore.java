package com.tarira.awsimageupload.filestore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FileStore {

  private final AmazonS3 s3;

  public void save(String path,
                   String fileName,
                   Optional<Map<String, String>> optionalMetadata,
                   InputStream inputStream) {

    ObjectMetadata objectMetadata = new ObjectMetadata();
    optionalMetadata.ifPresent(map -> {
      if (!map.isEmpty())
        map.forEach(objectMetadata::addUserMetadata);
    });

    try {
      s3.putObject(path, fileName, inputStream, objectMetadata);
    } catch (AmazonServiceException e) {
      throw new IllegalStateException("Failed to store file to S3", e);
    }
  }

}
