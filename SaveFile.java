import java.io.File;
import java.io.IOException; 
import java.io.FileWriter;
import java.io.PrintWriter;

public class SaveFile implements Task {

    private Viewer viewer;

    public SaveFile(Viewer viewer) {
	this.viewer = viewer;
    }

    public void doTask() {
	String textFromTextArea;
	File file = viewer.getFileName();
	if(file == null) {
	    textFromTextArea = viewer.getInputText();
	    File file1 = viewer.getFileForSaving();
	    if(file1 == null){
	    	viewer.showMessage("File not saved!");
	    }
            else {
	    	if(!textFromTextArea.equals("") && file1 != null){
	    	    try{
	       	    	saveText(textFromTextArea, file1);
		    	viewer.setFrameTitle(file1);
	    	    }
	    	    catch (IOException e) {
	     	    	e.printStackTrace();
	    	    }
	        }
 	    }
	}
	else {
	    textFromTextArea = viewer.getInputText();
	    try{                                              
	    	saveText(textFromTextArea, file);
	    	viewer.setFrameTitle(file);
	    }
	    catch (IOException e) {
	     	e.printStackTrace();
	    }    
	}	
	viewer.setBool(false);	
    }

    private void saveText(String textFromTextArea, File fileName) 
	throws IOException {
    	PrintWriter printWriter = null;

	try {
	    FileWriter fileWriter = new FileWriter(fileName);
	    printWriter = new PrintWriter(fileWriter);
	    printWriter.println(textFromTextArea);
	}
	finally {
	    if(printWriter != null) {
	     	printWriter.close();
	    }
	}	
    }
}