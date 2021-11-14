public class Cut implements Task {
    private Viewer viewer;

    public Cut(Viewer viewer) {
        this.viewer = viewer;
    }

    public void doTask() {
        viewer.cutText();
    }
}