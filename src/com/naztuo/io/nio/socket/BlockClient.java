package com.naztuo.io.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class BlockClient {

    public static void main(String[] args) throws IOException {
        sendToServer();
    }

    private static void sendToServer() {
        try {
            //1. 获取通道
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",6666));
            //发送文件给服务端
            FileChannel fileChannel = FileChannel.open(Paths.get("D:\\360downloads\\323761.jpg"), StandardOpenOption.READ);
            //有channel就必须要有buffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while(fileChannel.read(byteBuffer) != -1) { //读取文件
                byteBuffer.flip(); //切换模式
                socketChannel.write(byteBuffer);
                byteBuffer.clear();
            }
            //告诉服务端已经写完数据
            socketChannel.shutdownOutput();
            int len;
            while ((len =socketChannel.read(byteBuffer)) != -1) {
                byteBuffer.flip();
                System.out.println(new String(byteBuffer.array(),0,len));
                byteBuffer.clear();
            }
            // 5. 关闭流
            fileChannel.close();
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
