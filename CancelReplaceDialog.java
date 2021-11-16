public class CancelReplaceDialog implements Task {
    private Viewer viewer;

    public CancelReplaceDialog(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void doTask() {
        viewer.closeReplaceDialog();

    }


}
