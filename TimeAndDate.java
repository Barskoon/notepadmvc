import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TimeAndDate implements Task {
    private Viewer viewer;
    private DateFormat dateFormat;

    public TimeAndDate(Viewer viewer) {
        this.viewer = viewer;
        dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
    }

    public void doTask() {
        int resultCursorPosition = viewer.getCursorPosition();
        String textForInsertDate = viewer.getInputText();
        StringBuilder stringBuilder = new StringBuilder(textForInsertDate);

        stringBuilder.insert(resultCursorPosition, dateFormat.format(System.currentTimeMillis()));

        viewer.updateText(stringBuilder.toString());
        stringBuilder.setLength(0);
    }
}