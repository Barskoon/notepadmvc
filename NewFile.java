import java.io.File;

public class NewFile implements Task {

    private Viewer viewer;
    private SaveFile saveFile;

    public NewFile(Viewer viewer) {
        this.viewer = viewer;
	saveFile = new SaveFile(viewer);
    }

    public void doTask() {
	int n;
        if(!viewer.getInputText().equals("") && viewer.getBool()) {
	    n = viewer.getAnswer();
	    if(n == 1) {
	     	viewer.updateText("");
	        viewer.setFrameTitle(new File("New"));
		viewer.setFileName(null);
	    }
	    else if(n == 0) {
	        saveFile.doTask();
	        viewer.updateText("");
		viewer.setFrameTitle(new File("New"));
		viewer.setFileName(null);
	    }
	    else {
	     	return;
	    }
	}
	else {
	    viewer.updateText("");
	    viewer.setFrameTitle(new File("New"));
	    viewer.setFileName(null);
	}
    }
}
