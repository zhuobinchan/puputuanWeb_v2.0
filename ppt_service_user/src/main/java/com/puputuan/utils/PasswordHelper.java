package com.puputuan.utils;

import com.puputuan.generic.userbase.model.UserBase;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public class PasswordHelper {

    public static final String algorithmName = "md5";
    public static final int hashIterations = 2;

     public static String encryptPassword(UserBase user) {
        return new SimpleHash(algorithmName, user.getPassword(), ByteSource.Util.bytes(user.getAccount()), hashIterations).toHex();
    }

    public static String encryptPassword(String account, String password) {
        return new SimpleHash(algorithmName, password, ByteSource.Util.bytes(account), hashIterations).toHex();
    }

}
