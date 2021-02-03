package com.naztuo.io.bio;

import java.io.*;


/**
 * 测试bio
 */
public class BioTest {

    public long bioTranserFile(File source, File des) {
        long startTime = System.currentTimeMillis();
        try {
            if (!des.exists()) {
                des.createNewFile();
            }
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source));
                 BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(des))) {
                byte[] b = new byte[1024 * 1024];
                int len;
                while ((len = bis.read(b)) != -1) {
                    bos.write(b,0,len);
                }
            }
            return System.currentTimeMillis() - startTime;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
