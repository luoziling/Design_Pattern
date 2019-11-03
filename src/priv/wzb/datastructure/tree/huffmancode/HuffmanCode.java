package priv.wzb.datastructure.tree.huffmancode;

import jdk.internal.util.xml.impl.Input;

import java.io.*;
import java.util.*;

/**
 * @author Satsuki
 * @time 2019/11/2 21:37
 * @description:
 */
public class HuffmanCode {
    public static void main(String[] args) {
        /**
        String content = "i like like like java do you like a java";
        byte[] contentBytes = content.getBytes();
        System.out.println(contentBytes.length);

        byte[] huffmanCodesBytes = huffmanZip(contentBytes);
        System.out.println("压缩后的结果：" + Arrays.toString(huffmanCodesBytes));
        System.out.println("长度：" + huffmanCodesBytes.length);

        byteToBitString(false,(byte)-88);
        byte[] sourceBytes = decode(huffmanCodes, huffmanCodesBytes);


//        System.out.println("原来的字符串：" + Arrays.toString(sourceBytes));
        System.out.println("原来的字符串：" + new String(sourceBytes));
        **/

        // 测试压缩文件
//        String srcFile = "G:\\java_project_idea\\Design_Pattern\\pic\\chiya.jpg";
//        String dstFile = "G:\\java_project_idea\\Design_Pattern\\pic\\chiya.zip";
//        zipFile(srcFile,dstFile);
//        System.out.println("压缩文件成功");

        // 测试解压
        String zipFile = "G:\\java_project_idea\\Design_Pattern\\pic\\chiya.zip";
        String dstFile = "G:\\java_project_idea\\Design_Pattern\\pic\\chiya2.jpg";
        unZipFile(zipFile,dstFile);
        System.out.println("解压成功");




        // 如何将数据进行解压（解码）
        /*
        // 分步过程
        List<Node> nodes = getNodes(contentBytes);
        System.out.println(nodes);
        System.out.println("huffmanTree");
        Node huffmanTree = createHuffmanTree(nodes);
        preOrder(huffmanTree);
//        System.out.println();
        // 测试是否生成了对应的Huffman编码
        getCodes(huffmanTree);
        System.out.println("生成的哈夫曼编码表：" + huffmanCodes);

        byte[] huffmanCodeBytes = zip(contentBytes, huffmanCodes);
        System.out.println("huffmanCodeBytes" + Arrays.toString(huffmanCodeBytes));

        // 发送
        */

    }
    // 解压文件

    /**
     *
     * @param zipFile 准备解压的zip文件
     * @param dstFile 将文件解压到哪个路径
     */
    public static void unZipFile(String zipFile,String dstFile){
        // 定义文件的输入流
        InputStream is = null;
        // 定义一个对象输入流
        ObjectInputStream ois = null;
        // 定义文件的输出流
        OutputStream os = null;
        try {
            // 创建文件输入流
            is = new FileInputStream(zipFile);
            // 创建一个和is关联的对象输入流
            ois = new ObjectInputStream(is);
            //读取byte数组 huffmanBytes
            byte[] huffmanBytes = (byte[])ois.readObject();
            // 读取Huffman编码表
            Map<Byte,String> huffmanCodes = (Map<Byte,String>)ois.readObject();

            // 解码
            byte[] bytes = decode(huffmanCodes, huffmanBytes);

            // 将bytes写入到目标文件
            os = new FileOutputStream(dstFile);
            // 写出数据到文件中
            os.write(bytes);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                os.close();
                ois.close();
                is.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // 将一个文件进行压缩

    /**
     *
     * @param srcFile 希望压缩的文件的全路径
     * @param dstFile 压缩后将压缩文件放到哪个路径
     */
    public static void zipFile(String srcFile,String dstFile){
        // 创建输出流
        // 创建一个文件的输入流
        FileInputStream is = null;
        OutputStream os = null;
        ObjectOutputStream oos = null;
        try {
            is = new FileInputStream(srcFile);
            // 创建一个和源文件大小一样的byte[]数组
            byte[] b = new byte[is.available()];
            // 读取文件
            is.read(b);
            // 直接对源文件压缩
            byte[] huffmanBytes = huffmanZip(b);
            // 创建文件的输出流，存放压缩文件
            os = new FileOutputStream(dstFile);
            // 创建一个和文件输出流关联的ObjectOutputStream
            oos = new ObjectOutputStream(os);

            /// 把Huffman编码后的字节数组写入压缩文件
            oos.writeObject(huffmanBytes); // 我们把


            // 这里我们以对象流的方式写入Huffman编码
            // 为了以后恢复源文件时使用
            // 注意一定要把Huffman编码写入压缩文件
            oos.writeObject(huffmanCodes);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
                os.close();
                oos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    // 数据的解压
    // 将[-88, -65, -56, -65, -56, -65, -55, 77, -57, 6, -24, -14, -117, -4, -60, -90, 28]
    // 转为Huffman编码对应的二进制字符串
    // 将二进制字符串对照Huffman编码表转为原来的二进制字符串
    // 将原来的二进制字符串转为字符串
    // 转为"i like like like java do you like a java"

    /**
     *
     * @param huffmanCodes Huffman编码表
     * @param huffmanBytes Huffman编码得到的字节数组
     * @return
     */
    private static byte[] decode(Map<Byte,String> huffmanCodes,byte[] huffmanBytes){
        // byte中存放的是字节可以按照十进制进行打印，要将这些十进制重新转为一长串字符串
        // 先得到huffmanBytes对应的二进制字符串 其实就是将-88转为10101000
        StringBuilder stringBuilder = new StringBuilder();
        // 将byte数组转为=二进制字符串
        for (int i = 0; i < huffmanBytes.length; i++) {
            // 判断是否是最后一个字节
            // 如果是最后一个字节那么就是true
            boolean flag = (i==huffmanBytes.length-1);
            byte b = huffmanBytes[i];
            // 但是这边传递的是!flag,有点反人类了
            // 说明true代表不是最后一个字节
            stringBuilder.append(byteToBitString(!flag,b));
        }
        System.out.println("huffman字节数组对应的二进制字符串：" + stringBuilder.toString());
        // 把字符串按照指定的Huffman编码表进行解码
        // 把Huffman编码表进行调换，因为要反向查询
        // 原先:a->转为二进制byte->100
        // 现在:100->找对应的二进制byte->原初字符串
        Map<String,Byte> map = new HashMap<>();

        for (Map.Entry<Byte,String> entry : huffmanCodes.entrySet()){
            map.put(entry.getValue(),entry.getKey());
        }
        System.out.println("reverse map:" + map);
        // 创建集合存放byte
        List<Byte> list = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        // 自己的想法
        // 遍历整个转换后（由字节数组转为了二进制字符串）后的二进制字符串
        // 在整个遍历过程中i不停的向后移动即可
        for (int i = 0; i < stringBuilder.length(); i++) {
            // StringBuilder用来保存单次可能在Huffman解码表中出现的字符串
            builder.append(stringBuilder.charAt(i));
            // 判断是否出现该字符串，如果第一次循环则StringBuilder中只保存了一个字符
            // 多次循环后StringBuilder中可保存了多个字符
            // 判断map中是否包含要找的字符串
            if (map.containsKey(builder.toString())){
                //如果找到则添加到list集合
                list.add(map.get(builder.toString()));
                // 将builder清空，以便下一次使用
                builder = new StringBuilder();
            }
        }

        // 官方答案
//        for (int i = 0; i < stringBuilder.length(); ) {
//            int count = 1;// 小的计数器
//            boolean flag = true;
//            Byte b = null;
//            while (flag){
//                // 取出一个‘1’/‘0’
//                // i 不动让count移动，直到匹配到一个字符
//                String key = stringBuilder.substring(i,i+count);
//                b = map.get(key);
//                if (b==null){
//                    count++;
//                }else {
//                    flag = false;
//                }
//
//            }
//            list.add(b);
//            i+=count;// i移动到count位置
//        }
        
        // 当for循环结束后list中就存放了所有的字符
        // 把list中的数据放入byte[] 并返回
        byte[] b= new byte[list.size()];
        for (int i = 0; i < b.length; i++) {
            b[i] = list.get(i);
        }

        return b;

    }

    /**
     *  将一个byte转成一个二进制的字符串
     *  就是对byte b = (byte) Integer.parseInt(strByte, 2);
     *  的反向解码
     * @param b 传入的byte
     * @param flag true:需要补高位，，上面传过来的flag如果为true代表不是最后一个字节
     * @return 是该b对应的二进制的字符串（补码返回，因为编码也是补码
     */
    private static String byteToBitString(boolean flag,byte b){
        // 使用变量保存b
        int temp = b;// 将b转为int
//        System.out.println("temp:" + temp);
        // 如果是正数我们还存在补高位
        // 其实这里也针对只有最后以为byte可能不是八位的就直接添加即可
        // 在进行Huffman编码时，八位八位的转换为byte但是除了最后一位
        // 最后一位是直接转换的不一定是八位
        if (flag){
            temp |= 256; //按位与 // temp 1 => 0000 0001 | 1 0000 0000 = 1 0000 0001
        }

        String str = Integer.toBinaryString(temp);// 返回的是temp对应的二进制补码
//        System.out.println("str:" + str);

        // str:11111111111111111111111110101000 截取后八位即为byte对应的二进制补码
        // 10101000 -1 取反 1101 1000 就是88
        // 二进制在计算机中是以补码保存的
        // 所以toBinaryString得到的是补码并且过长要截取后八位转为原码

        if (flag){
            // 如果不是最后一个字节就需要去后八位
            return str.substring(str.length()-8);
        }else {
            // 如果是最后一个字节，则直接返回即可
            return str;
        }

    }



    // 封装压缩方法，便于调用

    /**
     * Huffman压缩是无损压缩，通过压缩相同的内容减小文件大小
     * @param bytes 原始的字符串对应的字节数组，一定要使用字节数组，只有这样才可以数字化原来的文本
     * @return 经过Huffman编码处理后的数组，压缩后的数组
     */
    private static byte[] huffmanZip(byte[] bytes){
        // 转为list
        List<Node> nodes = getNodes(bytes);
        // 根据节点创建Huffman树
        Node huffmanTree = createHuffmanTree(nodes);
        // 形成Huffman编码表
        Map<Byte, String> huffmanCodes = getCodes(huffmanTree);
        // 编码
        byte[] huffmanCodeBytes = zip(bytes, HuffmanCode.huffmanCodes);
        // 返回
        return huffmanCodeBytes;

    }

    // 编写一个方法，将字符串对应的byte[]数组，通过生成的Huffman编码表，返回一个Huffman压缩后的byte[]

    /**
     *
     * @param bytes 原始数组
     * @param huffmanCodes Huffman编码表
     * @return 返回Huffman编码处理后的byte
     */
    private static byte[] zip(byte[] bytes,Map<Byte,String> huffmanCodes){
        // 先利用Huffman编码表将byte数组转为Huffman编码对应的字符串
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println("byte.length:" + bytes.length);
        // 遍历
        for (byte b:bytes){
            stringBuilder.append(huffmanCodes.get(b));
        }
        System.out.println("stringBuilder:" + stringBuilder.toString());
        // 将编码后的内容转为byte数组
        // 统计返回byte[] huffmanCodeBytes长度
        int len;
        if (stringBuilder.length() % 8 == 0){
            len = stringBuilder.length()/8;
        }else {
            len = stringBuilder.length()/8+1;
        }
        // 创建huffmanCodeBytes
        // 创建存储压缩后的byte数组
        byte[] huffmanCodeBytes = new byte[len];
        int index = 0; // 记录是第几个byte
        for (int i = 0; i < stringBuilder.length(); i+=8) {
            // 每八位对应一个byte所以步长为0
            String strByte;
            if (i+8>stringBuilder.length()){
                strByte = stringBuilder.substring(i);//从当前取值到最后
            }else {
                strByte = stringBuilder.substring(i,i+8);//[)
            }

            byte b = (byte) Integer.parseInt(strByte, 2);

            // 将strByte转为一个byte放入by
            huffmanCodeBytes[index] = b;
            index++;
        }
        return huffmanCodeBytes;
    }

    // 生成huffman树对应的huffman编码
    // 思路
    // 将哈夫曼编码表存放在Map<Byte,String>形式
    static Map<Byte,String> huffmanCodes = new HashMap<>();
    // 在生成Huffman编码表，需要拼接路径，定义一个StringBuilder存储某个叶子节点的路径
    static StringBuilder stringBuilder = new StringBuilder();

    // 重载
    private static Map<Byte,String> getCodes(Node root){
        if (root == null){
            return null;
        }
        getCodes(root,"",stringBuilder);
        return huffmanCodes;
    }

    /**
     *
     * 将传入就的node节点的所有叶子节点的Huffman编码得到并放入到HuffmanCode集合
     * @param node 传入节点
     * @param code 路径：左子节点0右子节点1
     * @param stringBuilder  用于拼接路径
     * 其实生成Huffman编码的时候还是类似于树的先序遍历
     *  在先序遍历是根左右，其实根就代表了要对这棵树采取的操作，
     *                       真正的遍历其实只要遍历左右子数即可完成全部节点的遍历
     *                       只不过在遍历过程中进行了一些操作从而形成了三种遍历，前序中序后序
     *                       在遍历左右子树的前中后分别做出了一些操作
     */
    private static Map<Byte,String> getCodes(Node node,String code,StringBuilder stringBuilder){
        StringBuilder stringBuilder2 = new StringBuilder(stringBuilder);
        // 将code加入到stringBuilder2
        stringBuilder2.append(code);
        if (node!=null){
            // 如果node== null 补充不利
            //判断当前node是叶子节点还是非叶子节点
            if (node.data == null){
                // 非叶子节点
                // 递归处理
                // 向左
                getCodes(node.left,"0",stringBuilder2);
                // 向右递归
                getCodes(node.right,"1",stringBuilder2);
            }else {
                // 说明是一个叶子节点
                // 就表示找到了某个叶子节点的最后
                huffmanCodes.put(node.data,stringBuilder2.toString());
            }
        }
        return huffmanCodes;
    }


    private static void preOrder(Node root){
        if (root==null){
            System.out.println("树空不能遍历");
        }else{
            root.preOrder();
        }
    }

    // 通过List，创建对应的huffman树
    private static Node createHuffmanTree(List<Node> nodes){
        Node firstNode;
        Node secNode;
        Node newNode;
        // nodes排序
        Collections.sort(nodes);
        // 只有当list中只存在一个node的时候就说明这个node是root节点也代表了huffman树创建完毕就返回
        while (nodes.size()>1){
            // 每次都取第一个和第二个节点(也就是权值最小的两个节点
            firstNode = nodes.get(0);
            secNode = nodes.get(1);
            // 合成一个新的节点权值为这两个节点之和
            newNode = new Node(null,firstNode.weight+secNode.weight);
            // 在删除旧节点之前把他们加入到huffman树中
            newNode.left = firstNode;
            newNode.right = secNode;
            // 把新节点加入list并且删除用于创建这个节点的两个节点
            nodes.add(newNode);
            nodes.remove(firstNode);
            nodes.remove(secNode);
            //再排序
            Collections.sort(nodes);

        }
        return nodes.get(0);
    }

    /**
     *
     * @param bytes 接收的字符数组
     * @return 返回Node的List
     */
    private static List<Node> getNodes(byte[] bytes){
        // 创建一个ArrayList
        List<Node> nodes = new ArrayList<>();

        //遍历bytes，统计每个byte出现的次数。 存储每个byte出现的次数-->map
        HashMap<Byte, Integer> counts = new HashMap<>();
        for(byte b:bytes){
            Integer count = counts.get(b);
            if (count == null){
                // map没有该字符
                // 第一次统计到该字符
                counts.put(b,1);
            }else {
                // 已经保存过该字符，计数
                counts.put(b,count+1);
            }
        }
        // 遍历map把每个entryt转为Node
        for(Map.Entry<Byte,Integer> entry:counts.entrySet()){
            nodes.add(new Node(entry.getKey(),entry.getValue()));
        }

        return nodes;
    }
}

// 创建Node，带数据和权值
class Node implements Comparable<Node>{
    Byte data; // 存放数据本身'a'=>97
    int weight; // 权值，表示字符出现次数
    Node left;
    Node right;

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public int compareTo(Node o) {
        // 从小到大排序
        return this.weight-o.weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }

    // 前序遍历
    public void preOrder(){
        System.out.println(this);
        if (this.left!=null){
            this.left.preOrder();
        }
        if (this.right!=null){
            this.right.preOrder();
        }
    }
}
