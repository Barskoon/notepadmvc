import javax.swing.text.BadLocationException;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Container;

import javax.swing.GroupLayout;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FindWord implements Task {
    private Viewer viewer;

    public FindWord(Viewer viewer) {
        this.viewer = viewer;
    }

    @Override
    public void doTask() {
        OpenFindDialog openFindDialog = new OpenFindDialog();
        openFindDialog.setVisible(true);
    }

    private class OpenFindDialog extends JDialog {
        private JTextField inputSearchField;
        private JCheckBox caseCheckBox;
        private JCheckBox wrapCheckBox;

        private ArrayList<Integer> firstIndexesOfSearchWord;

        private Color entryBg;

        public static final int CANCEL = 0;
        private int returnStatus = CANCEL;

        public OpenFindDialog() {
            initComponents();
            setTitle("Find word");
        }

        private void initComponents() {
            Container container = getContentPane();
            GroupLayout layout = new GroupLayout(container);
            container.setLayout(layout);

            inputSearchField = new JTextField();
            JLabel label = new JLabel();
            label.setText("Enter text to search:");
            entryBg = inputSearchField.getBackground();

            JButton findButton = new JButton("Search");
            findButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    findOccurrences();
                }
            });

            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    cancelButtonActionPerformed(evt);
                }
            });


            caseCheckBox = new JCheckBox("Match case");
            wrapCheckBox = new JCheckBox("Wrap around");

            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);

            layout.setHorizontalGroup(layout.createSequentialGroup()
                    .addComponent(label)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(inputSearchField)
                            .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addComponent(caseCheckBox)
                                    )
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addComponent(wrapCheckBox)
                                    ))

                    )
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(findButton)
                            .addComponent(cancelButton))

            );
            layout.linkSize(SwingConstants.HORIZONTAL, findButton, cancelButton);

            layout.setVerticalGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(label)
                            .addComponent(inputSearchField)
                            .addComponent(findButton))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(caseCheckBox)
                                            .addComponent(wrapCheckBox)
                                    ))
                            .addComponent(cancelButton))
            );

            setSize(550, 150);
        }

        private void findOccurrences(){
            if(inputSearchField!=null){
                String text = viewer.getInputText();
                String searchWord = inputSearchField.getText();
                viewer.removeHilits();

                firstIndexesOfSearchWord = indexesOfSearchWord(text, searchWord,
                        caseCheckBox.isSelected(), wrapCheckBox.isSelected(), viewer.getCursorPosition());

                if(!firstIndexesOfSearchWord.isEmpty()){
                    for(int startIndex: firstIndexesOfSearchWord){
                        int endIndex = startIndex + searchWord.length();
                        try {
                            viewer.setHilitFindingWord(startIndex, endIndex);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        inputSearchField.setBackground(entryBg);
                    }
                }
            }
        }


        private ArrayList<Integer> indexesOfSearchWord(String text, String searchWord,
                                                       boolean matchCase, boolean wrapTextValue, int caretPosition){
            if(!matchCase){
                text = text.toLowerCase();
            }

            int index;

            if(!wrapTextValue){
                index = text.indexOf(searchWord, caretPosition);
            } else {
                index = text.indexOf(searchWord);
                System.out.println(index);

            }
            ArrayList<Integer> listOfIndexOccurrences = new ArrayList<>();
            while (index>=0){
                listOfIndexOccurrences.add(index);
                index = text.indexOf(searchWord, index+searchWord.length()+1);
            }
            return listOfIndexOccurrences;
        }

        private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
            doClose(CANCEL);
        }
        private void doClose(int retStatus) {
            returnStatus = retStatus;
            viewer.removeHilits();
            setVisible(false);
        }
    }
}
