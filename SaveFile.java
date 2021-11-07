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
    	String textFromTextArea = viewer.getInputText();
	File file = viewer.getFileForSaving();
	if(file == null){
	    viewer.showMessage("File not saved!");
	}
	else {
	    if(!textFromTextArea.equals("") && file != null){
	    	try{
	       	    saveText(textFromTextArea, file);
	    	}
	    	catch (IOException e) {
	     	    e.printStackTrace();
	    	}
	    }
	}	
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