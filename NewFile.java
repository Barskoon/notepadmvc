public class NewFile implements Task {

    private Viewer viewer;
    private SaveFile saveFile;

    public NewFile(Viewer viewer) {
        this.viewer = viewer;
	saveFile = new SaveFile(viewer);
    }

    public void doTask() {
        if(!viewer.getInputText().equals("")) {
	    int n = viewer.getAnswer();
	    if(n == 1) {
	     	viewer.updateText("");
	    }
	    else if(n == 0) {
	        saveFile.doTask();
	    }
	    else {
	     	return;
	    }
	}
    }
}
