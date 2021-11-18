import java.io.IOException;

public class About implements Task {
    private Viewer viewer;

    public About(Viewer viewer) {
        this.viewer = viewer;
    }

    public void doTask() throws IOException {
        String message = "NotepadMVC is a small and lightweight text editor\n\n" + "version 1.0\n\n" + "DreamTeam:\n"
                + "Aman Sarbagyshev\n" + "Ilgiz Bakytov\n" + "Artem Ilin\n" + "Mikhail Cheshev\n" + "Imambek Mukanov\n" + "Tattygul Abdimamatova\n" + "Rashid Nazarbekov\n" + "Bekatan Satyev\n" + "Aidar Ryskulov\n" + "Tilek Tekinov\n\n" + "Copyright (c) 2021 - the NotepadMVC team";
        viewer.showMessageIcon(message);
    }
}