public class CloseProgram implements Task {

    private Viewer viewer;
    private SaveFile saveFile;

    public CloseProgram(Viewer viewer) {
        this.viewer = viewer;
	saveFile = new SaveFile(viewer);
    }

    public void doTask() {
        if(viewer.getBool() == true) {
	    if(viewer.getFileName() == null) {
	    	if(viewer.getInputText().equals("")) {
	            System.exit(1);    
		}
		else {
		    startSaveOptionDialog();
		}
	    }
	    else {
	    	startSaveOptionDialog();    	
	    }
	}
	else {
	    System.exit(1);
	}
    }

    private void startSaveOptionDialog() {
    	int n = viewer.getAnswer();
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
}