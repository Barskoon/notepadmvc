import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeAndDate implements Task {
    private Viewer viewer;
    private DateFormat dateFormat;
    private Date date;

    public TimeAndDate(Viewer viewer) {
        this.viewer = viewer;
        dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        date = new Date();
    }

    public void doTask() {
        int resultCursorPosition = viewer.getCursorPosition();
        String textForInsertDate = viewer.getInputText();
        StringBuilder stringBuilder = new StringBuilder(textForInsertDate);

        stringBuilder.insert(resultCursorPosition, dateFormat.format(date));

        viewer.updateText(stringBuilder.toString());
        stringBuilder.setLength(0);
    }
} 