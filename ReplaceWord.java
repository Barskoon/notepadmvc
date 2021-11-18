import java.awt.event.ActionListener;
import javax.swing.SwingConstants;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.JTextField;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Container;

public class ReplaceWord implements Task {
    private Viewer viewer;
    private JDialog button;

    public ReplaceWord(Viewer viewer) {
        this.viewer = viewer;
        button =  new JDialog();
    }

    @Override
    public void doTask() {
        OpenReplaceDialog openReplaceDialog = new OpenReplaceDialog(this);
    }

    private class OpenReplaceDialog {
        public static final int CANCEL = 0;
        private int returnStatus = CANCEL;

        private JTextField inputReplaceFromField;
        private JTextField inputReplaceToField;

        public OpenReplaceDialog(ReplaceWord replaceWord) {
            initComponents();
            button.setTitle("Replace");
            replaceWord.button.setVisible(true);
        }

        private void initComponents() {
            Container container = button.getContentPane();
            button.setModal(true);
            GroupLayout layout = new GroupLayout(container);
            container.setLayout(layout);

            inputReplaceFromField = new JTextField();
            JLabel labelReplaceFrom = new JLabel();
            labelReplaceFrom.setText("From:");

            inputReplaceToField = new JTextField();
            JLabel labelReplaceTo = new JLabel();
            labelReplaceTo.setText("To:");

            JButton replaceButton = new JButton("Replace");
            replaceButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    replaceWord();
                }
            });

            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    cancelButtonActionPerformed(evt);
                }
            });

            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);

            layout.setHorizontalGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(labelReplaceFrom)
                            .addComponent(labelReplaceTo)
                    )
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(inputReplaceFromField)
                            .addComponent(inputReplaceToField))

                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(replaceButton)
                            .addComponent(cancelButton))
            );
            layout.linkSize(SwingConstants.HORIZONTAL, replaceButton, cancelButton);

            layout.setVerticalGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(labelReplaceFrom)
                            .addComponent(inputReplaceFromField)
                            .addComponent(replaceButton)
                    )
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(labelReplaceTo)
                            .addComponent(inputReplaceToField)
                            .addComponent(cancelButton)
                            .addComponent(inputReplaceFromField)
                            .addComponent(inputReplaceToField))
            );

            button.setSize(550, 120);
        }

        private void replaceWord(){
            String text = viewer.getInputText();
            String replaceFromWord = inputReplaceFromField.getText();
            String replaceToWord = inputReplaceToField.getText();
            String newReplacedText = text.replaceAll(replaceFromWord, replaceToWord);
            viewer.updateText(newReplacedText);
        }

        private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
            doClose(CANCEL);
        }

        private void doClose(int retStatus) {
            returnStatus = retStatus;
            button.setVisible(false);
        }
    }
}