package jwei26.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jwei26.service.Exception.InvalidFileException;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {
    @Autowired
    AmazonS3 s3Client;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String bucket_name = "find-your-dish-storage";

    public void uploadFile(File file) {
        s3Client.putObject(bucket_name, file.getName(), file);
    }

    public String upload(MultipartFile file) throws IOException {
        if(file == null) {
            logger.error("Cannot upload empty file");
            throw new InvalidFileException("Unable to upload empty file to bucket.");
        }
        String uuid = UUID.randomUUID().toString();
        String originalFileName = file.getOriginalFilename();
        String newFileName = FilenameUtils.removeExtension(originalFileName) + uuid + "." + FilenameUtils.getExtension(originalFileName);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        PutObjectRequest request = new PutObjectRequest(bucket_name,
                newFileName, file.getInputStream(), objectMetadata);
        s3Client.putObject(request);

        return s3Client.getUrl(bucket_name, newFileName).toString();
    }
}

