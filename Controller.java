import java.awt.event.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Controller implements ActionListener {

    private Map<String, Task> map;
    private Viewer viewer;

    public Controller(Viewer viewer) {
        this.viewer = viewer;
        initializeCommand();
    }

    private void initializeCommand() {
        if (map == null) {
            map = new HashMap<>();
        }

        initializeCommand("Create_New_Document", new NewFile(viewer));
        initializeCommand("Open_File", new OpenFile(viewer));
        initializeCommand("Save_File", new SaveFile(viewer));
        initializeCommand("Save_As_File", new SaveAsFile(viewer));
        initializeCommand("Printing_File", new PrintingFile(viewer));
        initializeCommand("Close_Program", new CloseProgram(viewer));
        initializeCommand("Undo", new Undo(viewer));
        initializeCommand("Redo", new Redo(viewer));
        initializeCommand("Cut", new Cut(viewer));
        initializeCommand("Copy", new Copy(viewer));
        initializeCommand("Paste", new Paste(viewer));
        initializeCommand("Remove", new Remove(viewer));
        initializeCommand("Select_All", new SelectAll(viewer));
        initializeCommand("Time_And_Date", new TimeAndDate(viewer));
        initializeCommand("Choose_font", new Fonts(viewer));
        initializeCommand("Find_Next", new FindNext(viewer));
        initializeCommand("Open_Find_Dialog", new FindWord(viewer));
        initializeCommand("Open_Replace_Dialog", new ReplaceWord(viewer));
        initializeCommand("Go_To", new Goto(viewer));
        initializeCommand("Help", new Help(viewer));
    }

    private boolean initializeCommand(String command, Task task) {
        if (map != null) {
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
}