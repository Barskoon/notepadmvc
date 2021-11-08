public class CloseProgram implements Task {

    private Viewer viewer;
    private SaveFile saveFile;

    public CloseProgram(Viewer viewer) {
        this.viewer = viewer;
	saveFile = new SaveFile(viewer);
    }

    public void doTask() {	
	int n;
        if(viewer.getBool() == true) {
	    n = viewer.getAnswer();
	    if(n == 1) {
	     	System.exit(1);
	    }
	    else if(n == 0) {
	        saveFile.doTask();
		System.exit(1);
	    }
	    else {
	     	return;
	    }
	}
	else {
	    System.exit(1);
	}
    }
}