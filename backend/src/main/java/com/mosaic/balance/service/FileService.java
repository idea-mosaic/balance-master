package com.mosaic.balance.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    public String upload(MultipartFile multipartFile) throws Exception;

    public int delete(String imageUrl) throws Exception;

}
