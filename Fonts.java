import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class Fonts implements Task {
    private final Viewer viewer;

    public Fonts (Viewer viewer) {
        this.viewer = viewer;
    }

    public void doTask() {
        JFontChooser Fonts = new JFontChooser();
        Fonts.setVisible(true);
        viewer.updateFont(Fonts.getFont());
    }

    private class JFontChooser extends JDialog {
        /** A return status code - returned if Cancel button has been pressed */
        public static final int CANCEL = 0;
        /** A return status code - returned if OK button has been pressed */
        public static final int OK = 1;
        //the font
        private Font font;
        private JPanel fontPanel;
        private JScrollPane jScrollPane1;
        private JLabel jLabel1;
        private JLabel jLabel3;
        private JLabel jLabel2;
        private JList lstSize;
        private JButton okButton;
        private JList<String> lstFont;
        private JScrollPane jScrollPane2;
        private JList lstStyle;
        private JPanel mainPanel;
        private JButton cancelButton;
        private JPanel previewPanel;
        private JLabel lblPreview;
        private JPanel buttonPanel;
        private JScrollPane jScrollPane3;
        private int returnStatus = CANCEL;


        //Constructor
        public JFontChooser() {
            this.font = viewer.getFonts();
            initComponents();
            lblPreview.setFont(font);
        }

        /** @return the font chosen by the user */
        public Font getFont() {
            return font;
        }

        /** @return the return status of this dialog - one of OK or CANCEL */
        public int getReturnStatus() {
            return returnStatus;
        }

        /** This method is called from within the constructor to
         * initialize the form.
         */
        private void initComponents() {//GEN-BEGIN:initComponents
            GridBagConstraints gridBagConstraints;

            mainPanel = new JPanel();
            fontPanel = new JPanel();
            jLabel1 = new JLabel();
            jLabel2 = new JLabel();
            jLabel3 = new JLabel();
            jScrollPane1 = new JScrollPane();
            /* The code below queries the system for all of the available fonts,
              and loads them in the list box, satisfying the first requirement of the dialog.
             */
            lstFont = new JList<>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
            jScrollPane2 = new JScrollPane();
            lstStyle = new JList();
            jScrollPane3 = new JScrollPane();
            lstSize = new JList();
            previewPanel = new JPanel();
            lblPreview = new JLabel();
            buttonPanel = new JPanel();
            okButton = new JButton();
            cancelButton = new JButton();

            setTitle("Select Font");
            setModal(true);
            setResizable(false);
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent evt) {
                    closeDialog(evt);
                }
            });

            mainPanel.setLayout(new GridLayout(2, 1));
            mainPanel.setBorder(new EmptyBorder(10,10,10,10));
            fontPanel.setLayout(new GridBagLayout());

            jLabel1.setText("Font");
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.insets = new Insets(1, 1, 1, 1);
            gridBagConstraints.weightx = 2.0;
            fontPanel.add(jLabel1, gridBagConstraints);

            jLabel2.setText("Style");
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.insets = new Insets(1, 1, 1, 1);
            fontPanel.add(jLabel2, gridBagConstraints);

            jLabel3.setText("Size");
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.insets = new Insets(1, 1, 1, 1);
            gridBagConstraints.weightx = 0.2;
            fontPanel.add(jLabel3, gridBagConstraints);

            lstFont.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            lstFont.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent evt) {
                    lstFontValueChanged(evt);
                }
            });

            jScrollPane1.setViewportView(lstFont);

            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.ipadx = 1;
            gridBagConstraints.insets = new Insets(1, 1, 1, 1);
            gridBagConstraints.weightx = 2.0;
            fontPanel.add(jScrollPane1, gridBagConstraints);

            lstStyle.setModel(new AbstractListModel() {
                final String[] strings = { "Plain", "Bold", "Italic", "Bold Italic" };
                public int getSize() { return strings.length; }
                public Object getElementAt(int i) { return strings[i]; }
            });
            lstStyle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            lstStyle.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent evt) {
                    lstStyleValueChanged(evt);
                }
            });

            jScrollPane2.setViewportView(lstStyle);

            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.ipadx = 1;
            gridBagConstraints.insets = new Insets(1, 1, 1, 1);
            fontPanel.add(jScrollPane2, gridBagConstraints);

            lstSize.setModel(new AbstractListModel() {
                final String[] strings = { "8", "10", "11", "12", "14", "16", "20", "24", "28", "36", "48", "72", "96" };
                public int getSize() { return strings.length; }
                public Object getElementAt(int i) { return strings[i]; }
            });
            lstSize.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            lstSize.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent evt) {
                    lstSizeValueChanged(evt);
                }
            });

            jScrollPane3.setViewportView(lstSize);

            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 2;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            gridBagConstraints.ipadx = 1;
            gridBagConstraints.insets = new Insets(1, 1, 1, 1);
            gridBagConstraints.weightx = 0.2;
            fontPanel.add(jScrollPane3, gridBagConstraints);

            mainPanel.add(fontPanel);

            previewPanel.setLayout(new BorderLayout());

            previewPanel.setBorder(new javax.swing.border.TitledBorder(null, "Preview", javax.swing.border.TitledBorder
                    .DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION));
            lblPreview.setFont(getFont());
            lblPreview.setText("In Xanadu did Kubla Khan");
            lblPreview.setHorizontalAlignment(SwingConstants.CENTER);
            previewPanel.add(lblPreview, BorderLayout.CENTER);

            mainPanel.add(previewPanel);

            getContentPane().add(mainPanel, BorderLayout.CENTER);

            buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

            okButton.setText("OK");
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    okButtonActionPerformed(evt);
                }
            });

            buttonPanel.add(okButton);

            cancelButton.setText("Cancel");
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    cancelButtonActionPerformed(evt);
                }
            });

            buttonPanel.add(cancelButton);

            getContentPane().add(buttonPanel, BorderLayout.SOUTH);

            pack();
            java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            setSize(new java.awt.Dimension(443, 429));
            setLocation((screenSize.width-443)/2,(screenSize.height-429)/2);
        }//GEN-END:initComponents

        private void lstStyleValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstStyleValueChanged
            int style = -1;
            String selStyle = (String)lstStyle.getSelectedValue();
            if(selStyle.equals("Plain"))
                style = Font.PLAIN;
            if(selStyle.equals("Bold"))
                style = Font.BOLD;
            if(selStyle.equals("Italic"))
                style = Font.ITALIC;
            if(selStyle.equals("Bold Italic"))
                style = Font.BOLD + Font.ITALIC;

            font = new Font(font.getFamily(),style,font.getSize());
            lblPreview.setFont(font);
        }//GEN-LAST:event_lstStyleValueChanged

        private void lstSizeValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstSizeValueChanged
            int size = Integer.parseInt((String)lstSize.getSelectedValue());
            font = new Font(font.getFamily(),font.getStyle(),size);
            lblPreview.setFont(font);
        }//GEN-LAST:event_lstSizeValueChanged

        private void lstFontValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstFontValueChanged
            font = new Font(lstFont.getSelectedValue(),font.getStyle(),font.getSize());
            lblPreview.setFont(font);
        }//GEN-LAST:event_lstFontValueChanged

        private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
            doClose(OK);
        }//GEN-LAST:event_okButtonActionPerformed

        private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
            doClose(CANCEL);
        }//GEN-LAST:event_cancelButtonActionPerformed

        /** Closes the dialog */
        private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
            doClose(CANCEL);
        }//GEN-LAST:event_closeDialog

        private void doClose(int retStatus) {
            returnStatus = retStatus;
            setVisible(false);
        }

    }
}
