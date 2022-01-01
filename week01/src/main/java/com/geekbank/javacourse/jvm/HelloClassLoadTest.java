package com.geekbank.javacourse.jvm;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author huangjie
 */
public class HelloClassLoadTest extends ClassLoader {

    public static void main(String[] args) {
        Class<?> helloClass = new HelloClassLoadTest().findClass("Hello");
        Method helloMethod = null;
        try {
            helloMethod = helloClass.getMethod("hello");
            helloMethod.invoke(helloClass.newInstance());
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Class<?> findClass(String name) {
        byte[] bytes = new byte[0];
        try {
            bytes = decode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(name, bytes, 0, bytes.length);
    }

    public byte[] decode() throws IOException {
        String path = this.getClass().getResource("/").getPath();
        path = path.substring(1);
        byte[] bytes = Files.readAllBytes(Paths.get(path + "Hello.xlass"));
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (255 - bytes[i]);
        }
        return bytes;
    }
}
