package com.cls;

import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * 学习类加载，以及双亲委派模式
 */
public class LoadClassTesting {
    public static void main(String[] args) {
        try {
            MyClassLoader classLoader = new MyClassLoader("/Users/guliang/codes/jdk9test/");
            Class clazz = classLoader.loadClass("com.cls.Student");
            Object obj = clazz.newInstance();
            Method nameMethod = clazz.getDeclaredMethod("getName",null);
            nameMethod.invoke(obj,null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    static class MyClassLoader extends ClassLoader{
        private String classPath;

        public MyClassLoader(String classPath){
            this.classPath = classPath;
        }

        private byte[] loadByte(String name)throws Exception{
            name = name.replace("\\.","/");
            FileInputStream fis = new FileInputStream(classPath+"/"+name+".class");
            int len = fis.available();
            byte[] data = new byte[len];
            fis.read(data);
            fis.close();
            return data;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException{
            try{
                byte[] data = loadByte(name);
                return defineClass(name,data,0,data.length);
            }catch (Exception e){
                 e.printStackTrace();
                 throw new ClassNotFoundException();
            }
        }
    }
}
