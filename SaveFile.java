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
	File file = viewer.getFileName();
	String textFromTextArea = viewer.getInputText();
	if(file == null) {                             
	    File file1 = viewer.getFileForSaving();
	    if(file1 != null) {
	    	if(file1.exists()) {
		    viewer.setFileName(file1);                    
	    	    int n = viewer.getAnswerConfirmReplace();	
	    	    if(n == 0) {
	    	    	try{
	    	    	    saveText(textFromTextArea, file1);
		    	    viewer.setFrameTitle(file1);
		    	    viewer.setFileName(file1);
	    	    	}
	    	    	catch (IOException e) {
	     	    	    e.printStackTrace();
	    	    	}  
	    	    }
		    else {
		    	viewer.setFileName(null);
		    }   
	    	}
 	        else {
	    	    try{
	       	    	saveText(textFromTextArea, file1);
		    	viewer.setFrameTitle(file1);	
		    	viewer.setFileName(file1);
	     	    }	
	    	    catch (IOException e) {
	     	    	e.printStackTrace();
	    	    }   
	    	}
	    }
	}
	else {                                         
	    try{                                              
	    	saveText(textFromTextArea, file);    	
	    	viewer.setFrameTitle(file);
		viewer.setFileName(file);
	    }
	    catch (IOException e) {
	     	e.printStackTrace();
	    }    
	}	
	viewer.setBool(false);	
    }

    public void saveText(String textFromTextArea, File fileName) 
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