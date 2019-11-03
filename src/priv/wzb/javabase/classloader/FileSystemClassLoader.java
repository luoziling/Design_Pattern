package priv.wzb.javabase.classloader;

import java.io.*;

/**
 * @author Satsuki
 * @time 2019/9/13 13:56
 * @description:
 * 自定义文件系统类加载器
 */
public class FileSystemClassLoader extends ClassLoader{
    //priv.wzb.javabase.user --> g:/myjava/priv/wzb/javabase/User.class
    private String rootDir;

    public FileSystemClassLoader(String rootDir){
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
            byte[] buffer = new byte[1024];
            int temp = 0;
            while ((temp = is.read(buffer))!=-1){
                baos.write(buffer,0,temp);
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
