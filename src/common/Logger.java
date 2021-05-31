package common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    static BufferedWriter writer;

    public static void writeMsg(String msg) {
        try {
            writer = new BufferedWriter(new FileWriter("log.txt",true));
            writer.append(msg+"\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
