import javax.swing.text.BadLocationException;
import java.util.ArrayList;

public class FindWordsOccurrences implements Task {
    private Viewer viewer;

    public FindWordsOccurrences(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void doTask() {
        if(viewer.getInputSearchField()!=null){
            String text = viewer.getInputText();
            String searchWord = viewer.getInputSearchField();
            viewer.removeHilits();
            ArrayList<Integer> firstIndexesOfSearchWord = indexesOfSearchWord(text, searchWord);

            if(!firstIndexesOfSearchWord.isEmpty()){
                for(int startIndex: firstIndexesOfSearchWord){
                    int endIndex = startIndex + searchWord.length();
                    try {
                        viewer.setHilitFindingWord(startIndex, endIndex);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                    viewer.setFindWordBackGround();
                }
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
