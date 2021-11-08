import java.io.File;       
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;

public class SaveAsFile implements Task {

    private Viewer viewer;
    private SaveFile saveFile;    

    public SaveAsFile(Viewer viewer) {
    	this.viewer = viewer;
	saveFile = new SaveFile(viewer);            
    }

    public void doTask() {
	String textFromTextArea = viewer.getInputText();
	File file = viewer.getFileForSaving();
	File file1 = viewer.getFileName();
	if(file != null) {
	    if(file.exists() && file.isFile() && !file.isDirectory()) {
	    	viewer.setFileName(file);
	    	int n = viewer.getAnswerConfirmReplace();	
	    	if(n == 0) {
	    	    try{
	    	    	saveFile.saveText(textFromTextArea, file);
		    	viewer.setFrameTitle(file);
	    	    }
	    	    catch (IOException e) {
	     	    	e.printStackTrace();
	    	    }  
	    	}
	    	else {
	     	    viewer.setFileName(file1);
	    	}	
	    }
	    else {
	    	try{
	    	    saveFile.saveText(textFromTextArea, file);
		    viewer.setFrameTitle(file);	
		    viewer.setFileName(file);
	     	}	
	    	catch (IOException e) {
	     	    e.printStackTrace();
	    	}	
	    }  
	}
	viewer.setBool(false);
    } 	    	 	
}