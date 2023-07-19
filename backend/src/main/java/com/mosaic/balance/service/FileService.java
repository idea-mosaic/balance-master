package com.mosaic.balance.service;

import com.amazonaws.SdkClientException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    /**
     * Upload one file
     * @param multipartFile  multipart file. Can be null
     * @param dirName Directory name : 'image' recommended'
     * @return image url returns null if given file was null
     * @throws IllegalArgumentException if file extension is invalid or absent
     * @throws IOException Failed to create File Stream
     * @throws SdkClientException AWS S3 error
     */
    public String upload(MultipartFile multipartFile, String dirName) throws IllegalArgumentException, IOException, SdkClientException;

    /**
     * Upload multiple files. Ignore null args and return null for corresponding index
     * @param multipartFiles Array of multipart files. Elements can be null
     * @param dirName Directory name : 'image' recommended
     * @return array of image urls. Elements can be null if given file was null
     * @throws IllegalArgumentException if file extension is invalid or absent
     * @throws IOException Failed to create File Stream
     * @throws SdkClientException AWS S3 error
     */
    public String[] upload(MultipartFile[] multipartFiles, String dirName) throws IllegalArgumentException, IOException, SdkClientException;

    public int delete(String imageUrl);

    public int delete(String[] imageUrls);

}
