public class Redo implements Task {

    private Viewer viewer;

    public Redo(Viewer viewer) {
        this.viewer = viewer;
    }

    public void doTask() {
        viewer.redoText();
    }
}