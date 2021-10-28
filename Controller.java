import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.io.File;

public class Controller implements ActionListener {
    
    private Viewer viewer;
    
    public Controller(Viewer viewer) {
        this.viewer = viewer;
    }
    
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        switch (command) {
            case "Open_File":
                File file = viewer.getFile();
                if (file == null) {
                    return;
                } else {
                    String text = openFile(file);
                    viewer.update(text);
                }
                break;
            case "Close_Program":
                System.exit(1);
                break;
        }
    }

    public String openFile(File file) {

        String text = "";
        FileInputStream fis = null;
        
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