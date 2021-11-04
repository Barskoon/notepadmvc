import javax.swing.text.BadLocationException;
import java.util.ArrayList;

public class FindUtil implements Task {
    private Viewer viewer;

    public FindUtil(Viewer viewer) {
        this.viewer = viewer;

    }

    @Override
    public void doTask() {
        String text = this.viewer.getTextArea().getText();
        String searchWord = this.viewer.getInputSearchField().getText();
        viewer.getHilit().removeAllHighlights();
        ArrayList<Integer> firstIndexesOfSearchWord = indexesOfSearchWord(text, searchWord);

        if(!firstIndexesOfSearchWord.isEmpty()){
            for(int startIndex: firstIndexesOfSearchWord){
                int endIndex = startIndex + searchWord.length();
                try {
                    viewer.getHilit().addHighlight(startIndex, endIndex, viewer.getPainter());
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
                viewer.getInputSearchField().setBackground(viewer.getEntryBg());
            }
        }
    }

    private ArrayList<Integer> indexesOfSearchWord(String text, String searchWord){
        int index = text.indexOf(searchWord);
        ArrayList<Integer> listOfIndexOccurrences = new ArrayList<>();
        while (index>=0){
            listOfIndexOccurrences.add(index);
            index = text.indexOf(searchWord, index+searchWord.length()+1);
        }
        return listOfIndexOccurrences;
    }
}
