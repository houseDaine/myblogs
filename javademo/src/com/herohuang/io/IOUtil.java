package com.herohuang.io;

import java.io.*;

/**
 * Created by acheron on 16-3-24.
 */
public class IOUtil {

    public static void main(String[] args) throws IOException{
        FileInputStream input = new FileInputStream("/home/acheron/test.txt");
        InputStreamReader reader = new InputStreamReader(input,"UTF-8");

/*        int c;
        while((c = reader.read()) != -1){
            System.out.println((char)c);
        }*/

        char[] buffer = new char[4 * 1024];
        int d;
        while ((d = reader.read(buffer,0,buffer.length)) != -1) {
            String s = new String(buffer,0,d);
            System.out.println(s);
        }


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

}
