package priv.wzb.netty;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

/**
 * FileChannelTest
 *
 * @author yuzuki
 * @date 2021/7/31 18:14
 * @description:
 * @since 1.0.0
 */
public class FileChannelTest {
    @Test
    public void fileWrite() throws IOException {
        String s = "hello dota2 测试";
        // 创建输出流
        FileOutputStream fileOutputStream = new FileOutputStream("E:\\Learning\\file01.txt");
        // 通过输出流获取对应channel FileChannel是对Java input/output Stream的包装
        // 真实类型是FileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();
        // 创建缓冲区 buffer有提高效率的作用？避免一直写，而是数据集合到了一块再写
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 若s过长则产生风险 若读取的长度固定则无风险
        byteBuffer.put(s.getBytes(StandardCharsets.UTF_8));
        // byteBuffer数据写入文件
        // 反转position 再读取
        byteBuffer.flip();
        // 推论：channel中有数据就自动去写，直接往文件描述符中写入数据
        fileChannel.write(byteBuffer);
        // 关闭流
        fileOutputStream.close();
    }

    @Test
    public void fileRead() throws IOException {
        // input/outputStream包含一个channel
        // 相反执行
        FileInputStream fileInputStream = new FileInputStream("E:\\Learning\\file01.txt");
        // 从stream获取channel
        FileChannel fileChannel = fileInputStream.getChannel();
        // channel写入buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) fileChannel.size());
        fileChannel.read(byteBuffer);
        // byteBuffer转为String
        System.out.println("new String(byteBuffer.array()) = " + new String(byteBuffer.array()));
        fileInputStream.close();

    }

    @Test
    public void fileCopy() throws IOException{
        FileInputStream fileInputStream = new FileInputStream("E:\\Learning\\file01.txt");
        FileChannel file01Channel = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("E:\\Learning\\file02.txt");
        FileChannel file02Channel = fileOutputStream.getChannel();

        // 定义buffer进行数据流动
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file01Channel.size());
        while (file01Channel.read(byteBuffer)>-1){
            byteBuffer.flip();
            file02Channel.write(byteBuffer);
            // 重置，以免limit以及position没有复位导致不断往后可能导致超出限制
            byteBuffer.clear();

        }
        // 流关闭
        fileInputStream.close();
        fileOutputStream.close();
    }

    @Test
    public void transferCopy() throws IOException{
        // 图片拷贝
        FileInputStream fileInputStream = new FileInputStream("E:\\Learning\\netty\\招募海报.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("E:\\Learning\\netty\\樱都招募海报.jpg");
        FileChannel sourceChannel = fileInputStream.getChannel();
        FileChannel destChannel = fileOutputStream.getChannel();
        // 相同channel的数据转移会抛出java.nio.channels.NonReadableChannelException
        destChannel.transferFrom(sourceChannel,0,sourceChannel.size());
        fileInputStream.close();
        fileOutputStream.close();
    }

    @Test
    public void bufferType(){
        // 数据以byte形式存储，一个byte代表一个字节，int占4个字节所以position到4 因此ByteBuffer.allocate()要注意存入数据长度
        ByteBuffer byteBuffer = ByteBuffer.allocate(50);
        byteBuffer.putInt(1);
        byteBuffer.putChar('a');
        byteBuffer.putLong(2);
        byteBuffer.flip();
        System.out.println("byteBuffer.getInt() = " + byteBuffer.getInt());
    }

    @Test
    public void bufferTransfer(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        int x = 128;
        // byte只能表示-128-127 primitive强转导致异常
        System.out.println("x = " + (byte)x);
        for (int i = 0; i < 64; i++) {
            byteBuffer.put((byte) i);
        }
        byteBuffer.flip();
        // 得到一个只读的buffer
        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();

        while (readOnlyBuffer.hasRemaining()){
            System.out.println("readOnlyBuffer.get() = " + readOnlyBuffer.get());
        }
    }

    @Test
    public void mappedBufferTest() throws IOException{
        // 堆外内存修改
        RandomAccessFile randomAccessFile = new RandomAccessFile("E:\\Learning\\file01.txt", "rw");
        // 获取通道
        FileChannel fileChannel = randomAccessFile.getChannel();

        // mappedByteBuffer 堆外内存修改 不用拷贝到虚拟机内存，由操作系统直接执行，效率高，对文件的修改实时生效（不用close也会生效）
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(2, (byte) 'Y');
        randomAccessFile.close();
        System.out.println("FileChannelTest.mappedBufferTest");

    }

    @Test
    public void scatteringAndGathering() throws IOException{
        // 使用socketChannel来进行buffer数组的读写
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8888);
        serverSocketChannel.socket().bind(inetSocketAddress);
        SocketChannel socketChannel = serverSocketChannel.accept();
        int limit = 8;
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        while (true){
            int readLength = 0;
            while (readLength<limit){
                readLength += socketChannel.read(byteBuffers);
//                if (readLength>=8){
//                    break;
//                }
                // 数据打印
                Stream.of(byteBuffers).map(e->"position:"+e.position()+"limit:" + e.limit()).forEach(e-> System.out.println("e = " + e));


            }
            // 翻转读取
            Stream.of(byteBuffers).forEach(Buffer::flip);
            int writeLength = 0;
            while (writeLength<limit){
                writeLength += socketChannel.write(byteBuffers);
            }


            // 清理buffer
            for (ByteBuffer byteBuffer : byteBuffers) {
                byteBuffer.clear();
            }
            System.out.println("read:" + readLength +"  write:" + writeLength+"  buffer:" + byteBuffers.length);

        }
//        System.out.println("FileChannelTest.scatteringAndGathering");
    }
}
