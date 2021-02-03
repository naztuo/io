package com.naztuo.io.nio;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest {
    /**
     * nio传输文件
     * @param source
     * @param des
     * @return
     */
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


    private static void testBuffer() {
        // 创建一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 看一下初始时4个核心变量的值
        System.out.println("初始时-->limit--->"+byteBuffer.limit());
        System.out.println("初始时-->position--->"+byteBuffer.position());
        System.out.println("初始时-->capacity--->"+byteBuffer.capacity());
        System.out.println("初始时-->mark--->" + byteBuffer.mark());

        System.out.println("--------------------------------------");

        // 添加一些数据到缓冲区中
        String s = "Java3y";
        byteBuffer.put(s.getBytes());

        // 看一下初始时4个核心变量的值
        System.out.println("put完之后-->limit--->"+byteBuffer.limit());
        System.out.println("put完之后-->position--->"+byteBuffer.position());
        System.out.println("put完之后-->capacity--->"+byteBuffer.capacity());
        System.out.println("put完之后-->mark--->" + byteBuffer.mark());


        System.out.println("--------------------------------------");

        byteBuffer.flip();
        System.out.println("flip完之后-->limit--->"+byteBuffer.limit());
        System.out.println("flip完之后-->position--->"+byteBuffer.position());
        System.out.println("flip完之后-->capacity--->"+byteBuffer.capacity());
        System.out.println("flip完之后-->mark--->" + byteBuffer.mark());

        // 创建一个limit()大小的字节数组(因为就只有limit这么多个数据可读)
        byte[] bytes = new byte[byteBuffer.limit()];
        // 将读取的数据装进我们的字节数组中
        byteBuffer.get(bytes);
        // 输出数据
        System.out.println(new String(bytes, 0, bytes.length));

        System.out.println("--------------------------------------");

        System.out.println("get完之后-->limit--->"+byteBuffer.limit());
        System.out.println("get完之后-->position--->"+byteBuffer.position());
        System.out.println("get完之后-->capacity--->"+byteBuffer.capacity());
        System.out.println("get完之后-->mark--->" + byteBuffer.mark());



    }


    public static void main(String[] args) {
        testBuffer();

    }
}
