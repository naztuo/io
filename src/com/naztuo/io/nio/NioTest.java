package com.naztuo.io.nio;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest {

    public long nioTranserFile(File source, File des) {
        long startTime = System.currentTimeMillis();
        try {
            if (!des.exists()) {
                des.createNewFile();
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
        try {
            RandomAccessFile read = new RandomAccessFile(source, "rw");
            RandomAccessFile write = new RandomAccessFile(des, "rw");
            FileChannel readChannel = read.getChannel();
            FileChannel writeChanel = write.getChannel();
            //申请1M的缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);
            while (readChannel.read(byteBuffer) > 0) {
                //切换为写模式
                byteBuffer.flip();
                writeChanel.write(byteBuffer);
                byteBuffer.clear();
            }
            writeChanel.close();
            readChannel.close();
            return System.currentTimeMillis() - startTime;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;

    }
}
