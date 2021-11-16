import javax.swing.text.BadLocationException;

public class FindNext implements Task{
    private Viewer viewer;
    private int currentWordPosition;

    public FindNext(Viewer viewer) {
        this.viewer = viewer;
        currentWordPosition = viewer.getCursorPosition();
    }

    @Override
    public void doTask(){
        viewer.removeHilits();
        String selectedText = viewer.getSelectedText();
        String text = viewer.getInputText();
        int firstDownIndex = getFirstIndexSearchWord(text, selectedText, currentWordPosition);
        try {
            viewer.setHilitFindingWord(firstDownIndex, firstDownIndex+selectedText.length());
            currentWordPosition = firstDownIndex + selectedText.length();

        } catch (BadLocationException | NullPointerException e) {
            currentWordPosition = 0;
        }
    }

    private int getFirstIndexSearchWord(String text, String searchWord, int caretPosition){
        try {
            return text.indexOf(searchWord, caretPosition+searchWord.length());
        } catch (NullPointerException e) {
            return 0;
        }
    }
}
