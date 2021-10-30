import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import utils.OpenFile;

public class Controller implements ActionListener {
    
    private Viewer viewer;
    
    public Controller(Viewer viewer) {
        this.viewer = viewer;
    }
    
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        switch (command) {
            case "Open_File":
                OpenFile oFile = new OpenFile();
                File file = viewer.getFile();
                if (file == null) {
                    return;
                } else {
                    String text = oFile.openFile(file);
                    viewer.update(text);
                }
                break;
            case "Close_Program":
                System.exit(1);
                break;
        }
    }
}