package com.housekeeping.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AECSecurity {

    @Value("${AES.key}")
    private final String key = "HoseKeeping2024HoseKeeping2024HK";
    private final AES aes = SecureUtil.aes(SecureUtil.generateKey("AES", key.getBytes()).getEncoded());
    public String encrypt(String str) {
        return aes.encryptHex(str);
    }

    public String decrypt(String str) {
        return aes.decryptStr(str);
    }
}
