import javax.swing.event.*;

public class DocumentController implements DocumentListener {

    private Viewer viewer;

    public DocumentController(Viewer viewer) {
        this.viewer = viewer;
    }

    public void insertUpdate(DocumentEvent documentEvent) {
        viewer.setBool(true);
    }

    public void removeUpdate(DocumentEvent documentEvent) {
        viewer.setBool(true);
    }

    public void changedUpdate(DocumentEvent documentEvent) {
        viewer.setBool(true);
        // Plain text components don't fire these events.
    }
}
