public class CloseProgram implements Task {

    private Viewer viewer;

    public CloseProgram(Viewer viewer) {
        this.viewer = viewer;
    }

    public void doTask() {
        System.exit(1);
    }
} 