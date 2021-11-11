
public class Paste implements Task {
    private Viewer viewer;

    public Paste(Viewer viewer) {
        this.viewer = viewer;
    }

    public void doTask() {
        viewer.pasteText();
    }
}
