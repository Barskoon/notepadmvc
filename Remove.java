public class Remove implements Task{
    private Viewer viewer;

    public Remove(Viewer viewer) {
        this.viewer = viewer;
    }

    public void doTask() {
        viewer.removeText();
    }
}
