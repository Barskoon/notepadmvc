public class ReplaceWord implements Task{
    private Viewer viewer;

    public ReplaceWord(Viewer viewer) {
        this.viewer = viewer;

    }

    @Override
    public void doTask(){
        String text = viewer.getInputText();
        String replaceFromWord = viewer.getReplaceFromWord();
        String replaceToWord = viewer.getReplaceToWord();
        String newReplacedText = text.replaceAll(replaceFromWord, replaceToWord);
        viewer.updateText(newReplacedText);
    }



}
