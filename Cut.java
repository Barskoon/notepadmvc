
public class Cut implements Task {
    private Viewer viewer;
    private String textForCut;

    public Cut(Viewer viewer) {
        this.viewer = viewer;
    }

    public void doTask() {
        viewer.cutText();
    }
}
