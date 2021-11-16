public class OpenReplaceDialog implements Task{
    private Viewer viewer;
    private Controller controller;


    public OpenReplaceDialog(Viewer viewer, Controller controller) {
        this.viewer = viewer;
        this.controller = controller;

    }

    @Override
    public void doTask(){
        viewer.createReplaceDialog(controller);
    }
}
