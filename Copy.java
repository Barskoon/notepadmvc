public class Copy implements Task {
    private Viewer viewer;

    public Copy(Viewer viewer) {
        this.viewer = viewer;
    }

    public void doTask()  {
        viewer.copyText();
    }
}