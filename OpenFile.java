import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class OpenFile implements Task {

    private Viewer viewer;
    private SaveFile saveFile;
    private String text;
    private FileInputStream fis;
	
    public OpenFile(Viewer viewer) {
        this.viewer = viewer;
	saveFile = new SaveFile(viewer);
        text = "";
        fis = null;
    }

    public void doTask() {
	System.out.println(viewer.getBool());
    	if(viewer.getBool() == true) {
            if(viewer.getFileName() == null) {
		if(viewer.getInputText().equals("")) {
		    openFileMethod();
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
	    openFileMethod();
	}	    
    }

    private void startSaveOptionDialog() {
    	int n = viewer.getAnswer();
	if(n == 1) {
	    openFileMethod();
	}
	else if(n == 0) {
	    saveFile.doTask();
	    openFileMethod();
	}
	else {
	    return;
	}
    }

    private void openFileMethod() {
      	File file = viewer.getFile();
        if (file == null) {                         
            return;

        } else {
            try {
                char[] tempArray = new char[(int)file.length()];
                fis = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);

                int unicode;
                int index = 0;

                while ((unicode = isr.read()) != -1) {
                    tempArray[index] = (char)unicode;
                    index = index + 1;
                }
                text = new String(tempArray);
		viewer.setFrameTitle(file);
		viewer.setFileName(file);

            } catch (IOException e) {
                viewer.showMessage("File not found!");
		viewer.setFileName(null);

            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        viewer.updateText(text);
	viewer.setBool(false);
    }
}