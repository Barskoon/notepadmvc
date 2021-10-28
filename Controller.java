import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
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
                    viewer.update("");
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
        
        String text;

        try {
            char[] tempArray = new char[(int)file.length()];
            FileInputStream in = new FileInputStream(file);

            int unicode;
            int index = 0;

            while ((unicode = in.read()) != -1) {
                tempArray[index] = (char)unicode;
                index = index + 1;
            }
            
            text = new String(tempArray);
            in.close();
        } catch (IOException ioe) {
            text = "File not found!";
        }
        return text;
    }

}