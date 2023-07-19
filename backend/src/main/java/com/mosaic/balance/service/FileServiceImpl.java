package com.mosaic.balance.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String upload(MultipartFile multipartFile) throws Exception {
        return null;
    }

    @Override
    public int delete(String imageUrl) throws Exception {
        return 0;
    }
}
