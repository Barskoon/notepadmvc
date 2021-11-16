public class OpenGotoDialog implements Task {
    private Viewer viewer;
    private Controller controller;

    public OpenGotoDialog(Viewer viewer, Controller controller) {
        this.viewer = viewer;
        this.controller = controller;
    }

    @Override
    public void doTask() {
        viewer.createGotoDialog(controller);
    }
}
