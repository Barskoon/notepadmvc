import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class OpenFile implements Task {

    private Viewer viewer;
    private String text;
    private FileInputStream fis;
	
    public OpenFile(Viewer viewer) {
        this.viewer = viewer;
        text = "";
        fis = null;
    }

    public void doTask() {
        File file = viewer.getFile();
        if (file == null) {
            //viewer.showMessage("File not found!");
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