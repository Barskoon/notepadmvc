import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Help implements Task {

    private Viewer viewer;

    public Help(Viewer viewer) {
        this.viewer = viewer;
    }

    public void doTask() throws IOException {
        try {
            File htmlFile = new File("Documentation/index.html");
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException e) {
            System.out.println("Documentation template not found!");
        }
    }
}