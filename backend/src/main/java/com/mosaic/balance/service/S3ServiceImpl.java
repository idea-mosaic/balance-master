package com.mosaic.balance.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class S3ServiceImpl implements FileService {

    private final Logger logger = LoggerFactory.getLogger(S3ServiceImpl.class);

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String upload(MultipartFile multipartFile, String dirName) throws IllegalArgumentException, IOException, SdkClientException {
        String ret = null;
        try {
            logger.info("Upload File : {}", multipartFile.getName());
            if(checkExtension(multipartFile))
                ret = uploadFile(multipartFile, dirName);
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            logger.info("Invalid File extension");
            throw new IllegalArgumentException();
        }
        return ret;
    }

    @Override
    public String[] upload(MultipartFile[] multipartFiles, String dirName) throws IllegalArgumentException, IOException, SdkClientException {
        String[] ret = new String[multipartFiles.length];

        try {
            for(int i=0; i<multipartFiles.length; i++) {
                if(checkExtension(multipartFiles[i]))
                    // upload only file is valid
                    // skip if file is null
                    ret[i] = uploadFile(multipartFiles[i], "image");
            }
        } catch (IOException | SdkClientException e) {
            logger.info("Failed to upload file. Caused by : {}", e.getMessage());
            delete(ret);
            throw e;
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            logger.info("Invalid file extension");
            delete(ret);
            throw new IllegalArgumentException();
        }

        return ret;
    }

    @Override
    public int delete(String imageUrl) {
        try {
            deleteFile(imageUrl);
        } catch (UnsupportedEncodingException | ArrayIndexOutOfBoundsException e) {
            return -1;
        }
        return 0;
    }

    @Override
    public int delete(String[] imageUrls) {
        int len = imageUrls.length;
        int ret = 0;
        for(int i=0; i<len; i++) {
            try {
                deleteFile(imageUrls[i]);
            } catch (UnsupportedEncodingException | ArrayIndexOutOfBoundsException e) {
                ret = -1;
            }
        }

        return ret;
    }

    /**
     * Upload single file to S3
     * @param multipartFile
     * @param dirName
     * @return image URL
     * @throws IOException : InputStream Error
     * @throws SdkClientException : SDK error
     * @throws AmazonServiceException : S3 error
     */
    private String uploadFile(MultipartFile multipartFile, String dirName) throws IOException {
        String filePath = dirName + "/" + UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        logger.info("Upload File as : {}", filePath);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getInputStream().available());

        // throws SDKClientException & AmazonServiceException
        amazonS3.putObject(bucket, filePath, multipartFile.getInputStream(), objectMetadata);
        logger.info("Successfully uploaded file : {} bytes", multipartFile.getSize());

        return amazonS3.getUrl(bucket, filePath).toString();
    }

    /**
     * Delete file of S3 server
     * @param fileURL File url
     * @throws UnsupportedEncodingException wrong input
     * @throws ArrayIndexOutOfBoundsException wrong input
     */
    private void deleteFile(String fileURL) throws UnsupportedEncodingException, ArrayIndexOutOfBoundsException {
        String[] urlParts = fileURL.split("/", 4);
        String fileName = urlParts[3];

        logger.info("Delete file : {}", fileName);
        String objKey = URLDecoder.decode(fileName, "UTF-8").trim();
        logger.info("Delete object : {}", objKey);
        DeleteObjectRequest request = new DeleteObjectRequest(bucket, objKey);
        amazonS3.deleteObject(request);
    }

    /**
     * Extract file extension and check whether it is image or not
     * @param multipartFile
     * @return false for NULL, true for valid files
     * @throws IllegalArgumentException if file is not image
     * @throws IndexOutOfBoundsException if file has no extension
     */
    private boolean checkExtension(MultipartFile multipartFile) throws IllegalArgumentException, IndexOutOfBoundsException {
        if(multipartFile == null || multipartFile.getOriginalFilename() == null)
            return false;
        String fileName = multipartFile.getOriginalFilename();
        logger.info("File name : {}", fileName);

        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        if(isImage.get(extension.toLowerCase()) == null) {
            logger.info("File is not Image : {}", extension);
            throw new IllegalArgumentException();
        }

        return true;
    }

    private static Map<String, Boolean> isImage = Map.ofEntries(
            Map.entry("jpg", true), Map.entry("jpeg", true),
            Map.entry("png", true), Map.entry("gif", true),
            Map.entry("bmp", true), Map.entry("svg", true),
            Map.entry("heic", true)
    );
}
