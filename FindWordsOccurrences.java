import javax.swing.text.BadLocationException;
import java.util.ArrayList;

public class FindWordsOccurrences implements Task {
    private Viewer viewer;
    private int currentWordPosition;
    private ArrayList<Integer> firstIndexesOfSearchWord;

    public FindWordsOccurrences(Viewer viewer) {
        this.viewer = viewer;
        currentWordPosition = viewer.getCursorPosition();
    }

    @Override
    public void doTask() {
        if(viewer.getInputSearchField()!=null){
            String text = viewer.getInputText();
            String searchWord = viewer.getInputSearchField();
            viewer.removeHilits();

            if(viewer.getRadioBtnDownValue()){
//                int firstDownIndex = getFirstIndexSearchWord(text, searchWord, viewer.getMatchCaseValue(), currentWordPosition);
                int firstDownIndex = getDownIndex(currentWordPosition);
                try {
                    viewer.setHilitFindingWord(firstDownIndex, firstDownIndex+searchWord.length());
                    currentWordPosition = firstDownIndex + searchWord.length();
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }else{
                firstIndexesOfSearchWord = indexesOfSearchWord(text, searchWord,
                        viewer.getMatchCaseValue(), viewer.getWrapTextValue(), viewer.getCursorPosition());

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
    }

    private ArrayList<Integer> indexesOfSearchWord(String text, String searchWord,
         boolean matchCase, boolean wrapTextValue,  int caretPosition){
        if(!matchCase){
           text = text.toLowerCase();
        }
        int index = text.indexOf(searchWord);

        if(!wrapTextValue){
            index = text.indexOf(searchWord, caretPosition);
        }
        ArrayList<Integer> listOfIndexOccurrences = new ArrayList<>();
        while (index>=0){
            listOfIndexOccurrences.add(index);
            index = text.indexOf(searchWord, index+searchWord.length()+1);
        }
        return listOfIndexOccurrences;
    }

    private int getFirstIndexSearchWord(String text, String searchWord, boolean matchCase, int caretPosition){
        if(!matchCase){
            text = text.toLowerCase();
        }
        return text.indexOf(searchWord, caretPosition);
    }

    private int getDownIndex(int caretPosition){
        if(firstIndexesOfSearchWord != null){
            int firstWordIndex = getNearestIndex(caretPosition, firstIndexesOfSearchWord);
            return firstWordIndex;
        } else{
            firstIndexesOfSearchWord = indexesOfSearchWord(viewer.getInputText(), viewer.getInputSearchField(),
                    viewer.getMatchCaseValue(), false, currentWordPosition);

            int firstWordIndex = getNearestIndex(caretPosition, firstIndexesOfSearchWord);
            return firstWordIndex;
        }
    }

    private int getNearestIndex(int caretPosition, ArrayList<Integer> indexesArr){
        for(int i: indexesArr){
            if(i>=caretPosition) return i;
        }
        return 0;
    }

}
