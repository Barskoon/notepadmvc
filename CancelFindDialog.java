import java.io.IOException;

public class CancelFindDialog implements Task {
    private Viewer viewer;

    public CancelFindDialog(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void doTask() {
        viewer.closeFindDialog();

    }


}
