java 网络编程踩坑

```java
 @Override
    public void run() {
        while (true){
            byte[] container = new byte[1024*60];
            DatagramPacket packet = new DatagramPacket(container,0,container.length);
            //                * 准备容器 封装成 DatagramPacket包裹
// * 阻塞式接受包裹 receive(DatagramPacket d)
            try {
                server.receive(packet);
                // * 分析数据
//                *  byte[] getData
// *  getLength
                byte[] data = packet.getData();
                String datas = new String(data, 0, packet.getLength());
                System.out.println(datas);
                System.out.println(packet.getLength());
                System.out.println(datas.equals(new String("bye")));
                System.out.println("bye".length());
                System.out.println("bye".equals("bye"));
                if (datas.equals("bye")){
                    System.out.println("equal");
                    server.close();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

//                *
        }
    }
```

这段代码中if (datas.equals("bye")){总是出错研究后发现

 String datas = new String(data, 0, packet.getLength());第三个参数不应该是data.length而需要改成package.length

package.length是接收从发送方发过来数据的长度

如果用data.length则回因为接收的数据存入了容器中从而获得容器的长度

从而new出来的String看似只有bye其实还带了很多的int为0的byte进入

所以无法对比正确