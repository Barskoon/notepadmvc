import java.awt.event.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.event.*;

public class Controller implements ActionListener, CaretListener {

    private Map<String, Task> map;
    private Viewer viewer;

    public Controller(Viewer viewer) {
        this.viewer = viewer;
        initializeCommand();
    }

    private void initializeCommand() {
        if(map == null) {
            map = new HashMap<>();
        }

        initializeCommand("Create_New_Document", new NewFile(viewer));
        initializeCommand("Open_File", new OpenFile(viewer));
        initializeCommand("Printing_File", new PrintingFile(viewer));
        initializeCommand("Close_Program", new CloseProgram(viewer));
        initializeCommand("Select_All", new SelectAll(viewer));
        initializeCommand("Time_And_Date", new TimeAndDate(viewer));
        initializeCommand("Open_Find_Dialog", new OpenFindDialog(viewer, this));
        initializeCommand("Find_Word_Button", new FindWordsOccurrences(viewer));
        initializeCommand("Cancel_Find_Dialog", new CancelFindDialog(viewer));
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
            try {
                map.get(command).doTask();
            } catch (IOException e) {
                viewer.showMessage("Something went wrong! Please repeat the action!");
            }
        }
    }

    public void caretUpdate(CaretEvent caretEvent) {
        viewer.footerUpdate();
    }
}
