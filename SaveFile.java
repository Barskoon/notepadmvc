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
			saveTextWithTryCatch(textFromTextArea, file1); 
	    	    }
		    else {
		    	viewer.setFileName(null);
		    }   
	    	}
 	        else {
		    saveTextWithTryCatch(textFromTextArea, file1); 
	    	}
	    }
	}
	else { 
	    saveTextWithTryCatch(textFromTextArea, file);    
	}		
    }

    private void saveTextWithTryCatch(String text, File file) {
	try{
	    saveText(text, file);
 	    viewer.setFrameTitle(file);
	    viewer.setFileName(file);
     	    viewer.setBool(false);
	}
	catch (IOException e) {
	    e.printStackTrace();
	}

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