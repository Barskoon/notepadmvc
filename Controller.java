import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.File;

public class Controller implements ActionListener, CaretListener {
    
    private Viewer viewer;
    
    public Controller(Viewer viewer) {
        this.viewer = viewer;
    }
    
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        switch (command) {
            case "Open_File":
                OpenFile oFile = new OpenFile(this.viewer);
                File file = viewer.getFile();
                if (file == null) {
                    return;
                } else {
                    String text = oFile.openFile(file);
                    viewer.update(text);
                }
                break;

            case "Printing_File":
                PrintingFile pFile = new PrintingFile(this.viewer);
                String text = viewer.getTextForPrinting();
                pFile.printingFile(text);
                break;

            case "Close_Program":
                System.exit(1);
                break;

            case "Find":
                viewer.showFindDialog(this);
                break;
            case "Find_Word_Action":
                FindUtil findUtil = new FindUtil(this.viewer);
                findUtil.doTask();
                break;
            case "Cancel_Find_Dialog":
                viewer.closeFindDialog();
        }
    }

    public void caretUpdate(CaretEvent caretEvent) {
        viewer.footerUpdate();
    }
}