public class NewFile implements Task {

    private Viewer viewer;

    public NewFile(Viewer viewer) {
        this.viewer = viewer;
    }

    public void doTask() {
        viewer.updateText("");
    }
}
