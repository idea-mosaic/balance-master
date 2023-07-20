package com.mosaic.balance.app.game.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.mosaic.balance.service.S3ServiceImpl;
import jdk.jfr.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class S3ServiceImplTest {

    @InjectMocks
    private S3ServiceImpl s3Service;

    @Mock
    private AmazonS3 amazonS3;

    private String bucket = "idea-balance";

    /*
    Upload
        - single file
        - multiple files
        - no file
        - wrong extension
     */
    @Test
    public void uploadFileTest() throws IOException {
        // given
        MultipartFile multipartFile = new MockMultipartFile("test_img.png", "C:\\images\\test_img.jpg", "png", new byte[2]);
        when(amazonS3.putObject(any(), anyString(), any(), any(ObjectMetadata.class)))
                .thenReturn(new PutObjectResult());
        when(amazonS3.getUrl(any(), anyString()))
                .thenReturn(new URL("https://s3"));

        // when
        String imgUrl = s3Service.upload(multipartFile, "image");

        // then
        assertNotNull(imgUrl);
        assertEquals(imgUrl, "https://s3");
    }

    @Test
    public void uploadFilesTest() throws IOException {
        // given
        MultipartFile[] multipartFiles = new MultipartFile[] {
                new MockMultipartFile("red_img.jpg", "C:\\images\\red_img.jpg", "jpg", new byte[1]),
                new MockMultipartFile("blue_img.png", "C:\\images\\blue_img.jpg", "png", new byte[2])
        };
        when(amazonS3.putObject(any(), anyString(), any(), any(ObjectMetadata.class)))
                .thenReturn(new PutObjectResult());
        when(amazonS3.getUrl(any(), anyString()))
                .thenReturn(new URL("https://s3"));

        // when
        String[] imgUrls = s3Service.upload(multipartFiles, "image");

        // then
        assertNotNull(imgUrls);
        assertEquals(2, imgUrls.length);
    }

    @Test
    public void uploadEmptyFileTest() throws IOException {
        // given
        MultipartFile[] multipartFiles = new MultipartFile[] {
                null,
                new MockMultipartFile("blue_img.jpg", "C:\\images\\blue_img.jpg", "jpg", new byte[2])
        };
        when(amazonS3.putObject(any(), anyString(), any(), any(ObjectMetadata.class)))
                .thenReturn(new PutObjectResult());
        when(amazonS3.getUrl(any(), anyString()))
                .thenReturn(new URL("https://s3"));

        // when
        String[] imgUrls = s3Service.upload(multipartFiles, "image");

        // then
        assertNull(imgUrls[0]);
        assertEquals("https://s3", imgUrls[1]);
    }

    @Test
    public void uploadInvalidExtensionFileTest() throws IOException {
        // given
        MultipartFile[] multipartFiles = new MultipartFile[] {
                new MockMultipartFile("valid_img.jpg", "C:\\images\\valid_img.jpg", "jpg", new byte[1]),
                new MockMultipartFile("invalid_file.exe", "C:\\images\\invalid_file.exe", "exe", new byte[2])
        };
        when(amazonS3.putObject(any(), anyString(), any(), any(ObjectMetadata.class)))
                .thenReturn(new PutObjectResult());
        when(amazonS3.getUrl(any(), anyString()))
                .thenReturn(new URL("https://s3"));

        // when & then
        Assertions.assertThatThrownBy(() ->
                s3Service.upload(multipartFiles, "image")
        ).isInstanceOf(IllegalArgumentException.class);

    }
    /*
    Delete
        - single url
        - multiple urls
        - not found : AmazonS3.deleteObject does not return any results
     */

}