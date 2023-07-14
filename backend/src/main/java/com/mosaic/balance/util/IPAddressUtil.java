package com.mosaic.balance.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class IPAddressUtil {

    private static final Logger logger = LoggerFactory.getLogger(IPAddressUtil.class);

    public static String getIPAddress(HttpServletRequest request) {

        String ipAddress = request.getHeader("X-Forwarded-For");

        logger.info("remote addr : {}", ipAddress);
        return ipAddress;
    }
}
