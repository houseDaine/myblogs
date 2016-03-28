package com.herohuang.io;

import java.io.*;

/**
 * Created by acheron on 16-3-24.
 */
public class IOUtil {

    public static void testSerializble(){
        
    }

    public static void dump(Reader reader ,Writer writer) throws IOException {
        char[] data = new char[4 * 1024];
        int length = 0;
        while ( (length = reader.read(data) ) != -1) {
            writer.write(data,0,length);
        }
        reader.close();
        writer.close();
    }

    public static void main(String[] args) throws IOException{
        FileReader reader = new FileReader("/home/acheron/test.txt");
        StringWriter writer = new StringWriter();
        IOUtil.dump(reader,writer);
        System.out.println(writer.toString());

        IOUtil.dump(
                new InputStreamReader(new FileInputStream("test.txt"),"UTF-8"),
                new OutputStreamWriter(new FileOutputStream("dest.txt"),"UTF-8"));
    }
}
