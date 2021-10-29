import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private Viewer viewer;
    public Controller(Viewer viewer) {
        this.viewer = viewer;
    }
    
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        switch (command) {
            case "Close_Program":
                System.exit(1);
                break;
        }
    }
}