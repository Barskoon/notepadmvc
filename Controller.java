import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Controller implements ActionListener, CaretListener {

    Map<String, Task> map = new HashMap<String, Task>();
    private Viewer viewer;
    
    public Controller(Viewer viewer) {
        this.viewer = viewer;
        map.put("Open_File", new OpenFile());
        map.put("Printing_File", new PrintingFile());
        map.put("Close_Program", new CloseProgram());
    }
    
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();

        if (map.containsKey(command)) {
            map.get(command).doTask(viewer);
        }
        
    }

    public void caretUpdate(CaretEvent caretEvent) {
        viewer.footerUpdate();
    }
}