import java.awt.event.ActionListener;
import javax.swing.SwingConstants;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Container;

public class Goto implements Task {
    private Viewer viewer;

    public Goto(Viewer viewer) {
        this.viewer = viewer;
    }

    public void doTask(){
        OpenGotoDialog openGotoDialog = new OpenGotoDialog();
        openGotoDialog.setVisible(true);
    }

    private class OpenGotoDialog extends JDialog {
        private JTextField inputGotoLineField;

        public OpenGotoDialog() {
            initComponents();
            setTitle("Go to");
        }

        private void initComponents() {
            Container container = getContentPane();
            setModal(true);
            GroupLayout layout = new GroupLayout(container);
            container.setLayout(layout);

            inputGotoLineField = new JTextField();

            JLabel labelGoto = new JLabel();
            labelGoto.setText("Row number");

            JButton goToButton = new JButton("Go to");
            goToButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                            String rowNumber = inputGotoLineField.getText();
                            viewer.setCursorPosition(Integer.parseInt(rowNumber));
                }
            });

            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);

            layout.setVerticalGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(labelGoto)
                            .addComponent(inputGotoLineField)
                            .addComponent(goToButton)
                    )
            );
            layout.setHorizontalGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(labelGoto)
                    )
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(inputGotoLineField)
                    )
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(goToButton)
                    )
            );
            layout.linkSize(SwingConstants.VERTICAL,labelGoto, inputGotoLineField, goToButton);


            setSize(new java.awt.Dimension(250, 90));
        }

    }
}
