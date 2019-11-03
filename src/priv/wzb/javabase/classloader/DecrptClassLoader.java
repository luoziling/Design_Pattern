package priv.wzb.javabase.classloader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Satsuki
 * @time 2019/9/13 14:45
 * @description:
 *
 * 加载文件系统中加密后的class字节码的列加载器
 */
public class DecrptClassLoader extends ClassLoader{
    //priv.wzb.javabase.user --> g:/myjava/priv/wzb/javabase/User.class
    private String rootDir;

    public DecrptClassLoader(String rootDir){
        this.rootDir = rootDir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> c = findLoadedClass(name);

        //查询该类是否被加载若被加载则直接返回
        if (c!=null){
            return c;
        }else{
            ClassLoader parent = this.getParent();
            //交给父类加载
            try {
                c = parent.loadClass(name);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (c!=null){
                return c;
            }else {
                byte[] classData = getClassData(name);
                if (classData==null){
                    throw new ClassNotFoundException();
                }else {
                    c = defineClass(name, classData, 0, classData.length);
                }
            }
        }
        return c;
    }

    private byte[] getClassData(String classname){
        String path = rootDir+"/" + classname.replace('.','/')+ ".class";

        InputStream is = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            is = new FileInputStream(path);

            int temp = -1;
            while ((temp = is.read())!=-1){
                //取反加密
                baos.write(temp^0xff);
            }

            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (baos!=null){
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
