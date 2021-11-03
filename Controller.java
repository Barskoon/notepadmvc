import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.File;
import utils.*;

public class Controller implements ActionListener, CaretListener {
    
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

            case "Printing_File":
                PrintingFile pFile = new PrintingFile();
                String text = viewer.getTextForPrinting();
                pFile.printingFile(text);
                break;

            case "Close_Program":
                System.exit(1);
                break;
        }
    }

    public void caretUpdate(CaretEvent caretEvent) {
        viewer.footerUpdate();
    }
}