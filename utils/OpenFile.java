package utils;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class OpenFile {
    String text = "";
    FileInputStream fis = null;

    public String openFile(File file) {
        try {
            char[] tempArray = new char[(int)file.length()];
            fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);

            int unicode;
            int index = 0;
            
            while ((unicode = isr.read()) != -1) {
                tempArray[index] = (char)unicode;
                index = index + 1;
            }

            text = new String(tempArray);
            tempArray = null;
            isr = null;
        } catch (IOException e) {
            return null;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return text;
    }
}