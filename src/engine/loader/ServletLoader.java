package engine.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by bod on 03.08.15.
 */
public class ServletLoader extends ClassLoader {

    private String dirPath;

    public ServletLoader(String dir, ClassLoader parent){
        dirPath = dir;
    }
    public void setDirPath(String dir) {
        if(dir == null) throw new NullPointerException("Null pointer is not allowed");
        dirPath = dir;

    }
    @Override
    public Class<?> findClass(String className) throws ClassNotFoundException{

            byte[] buffer = loadFromFile(className);
            return defineClass(className, buffer, 0, buffer.length);
    }
    private byte[] loadFromFile(String className) throws ClassNotFoundException {

        FileInputStream fis = null;
        byte[] buffer = null;

        try {
            File f = new File(dirPath + className + ".class");
            fis = new FileInputStream(f);
            buffer = new byte[fis.available()];
            fis.read(buffer);
            if(buffer.length != f.length())
                throw new IOException();
        }catch (IOException ex){
            super.findClass(className);
        }finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buffer;
    }
}
