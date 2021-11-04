import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Controller implements ActionListener, CaretListener {

    private Map<String, Task> map;
    private Viewer viewer;
    
    public Controller(Viewer viewer) {
        this.viewer = viewer;
        initializeCommand();
    }

    private void initializeCommand() {
        if(map == null) {
            map = new HashMap<String, Task>();
        }
        initializeCommand("Open_File", new OpenFile(viewer));
        initializeCommand("Printing_File", new PrintingFile(viewer));
        initializeCommand("Close_Program", new CloseProgram(viewer));
    }

    private boolean initializeCommand(String command, Task task) {
        if(map != null) {
            map.put(command, task);
            return true;
        }
        return false;
    }
    
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();

        if (map.containsKey(command)) {
            map.get(command).doTask();
        } else {
            viewer.showMessage("Bad action command!");
        }

    }

    public void caretUpdate(CaretEvent caretEvent) {
        viewer.footerUpdate();
    }
}