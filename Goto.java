public class Goto implements Task {
    private Viewer viewer;

    public Goto(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void doTask(){
        String rowNumber = viewer.getGotoRowNumber();
        viewer.setCursorPosition(Integer.parseInt(rowNumber));

    }
}
