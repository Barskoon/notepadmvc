public class OpenFindDialog implements Task {
    private Viewer viewer;
    private Controller controller;

    public OpenFindDialog(Viewer viewer, Controller controller) {
        this.viewer = viewer;
        this.controller = controller;

    }

    @Override
    public void doTask() {
        viewer.createFindDialog(controller);
    }
}
