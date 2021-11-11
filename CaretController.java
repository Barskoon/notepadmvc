import javax.swing.event.*;

public class CaretController implements CaretListener {

    private Viewer viewer;

    public CaretController(Viewer viewer) {
        this.viewer = viewer;
    }

    public void caretUpdate(CaretEvent caretEvent) {
        viewer.footerUpdate();
    }
}
