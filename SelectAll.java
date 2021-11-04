public class SelectAll implements Task {
    private Viewer viewer;

    public SelectAll(Viewer viewer) {
        this.viewer = viewer;
    }

    public void doTask() {
        String textForSelect = viewer.getInputText();
        if (!textForSelect.equals("")) {
            viewer.selectAllText();
        } else {
            viewer.showMessage("Input text is empty!");
        }
    }
} 