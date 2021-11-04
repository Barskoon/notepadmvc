import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class OpenFile implements Task {
    String text = "";
    FileInputStream fis = null;

    public void doTask(Viewer viewer) {
        File file = viewer.getFile();
        if (file == null) {
            viewer.update("file not found");
        } else {
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
                return;
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        viewer.update(text);
    }
}