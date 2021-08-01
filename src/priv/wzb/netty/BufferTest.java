package priv.wzb.netty;

import org.junit.Test;

import java.nio.IntBuffer;

/**
 * BufferTest
 *
 * @author yuzuki
 * @date 2021/7/31 16:02
 * @description:
 * @since 1.0.0
 */
public class BufferTest {
    @Test
    public void simpleBuffer(){
        IntBuffer intBuffer = IntBuffer.allocate(5);
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i*2);
        }
        intBuffer.flip();
        intBuffer.clear();
        while (intBuffer.hasRemaining()){
            System.out.println("intBuffer.get() = " + intBuffer.get());
        }
//        for (int i = 0; i < intBuffer.capacity(); i++) {
//            intBuffer.put(i*2);
//        }
    }
}
