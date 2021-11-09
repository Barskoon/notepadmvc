import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

public class Viewer {

    private JFrame frame;
    private JTextArea textArea;
    private JLabel tabSize;
    private JLabel caretPosition;
    private JLabel symbolCount;
    private JPanel footer;
    private JDialog findDialog;
    private JTextField inputSearchField;

    private Highlighter hilit;
    private Highlighter.HighlightPainter painter;
    private Color entryBg;
    private final Color HILIT_COLOR = Color.LIGHT_GRAY;

    public Viewer() {
        Controller controller = new Controller(this);

        JMenuBar menuBar = createJMenuBar(controller);

        textArea = new JTextArea();
        textArea.addCaretListener(controller);
        JScrollPane scrollPane = new JScrollPane(textArea);
        TextLineNumber textLineNumber = new TextLineNumber(textArea);
        scrollPane.setRowHeaderView(textLineNumber);

        hilit = new DefaultHighlighter();
        painter = new DefaultHighlighter.DefaultHighlightPainter(HILIT_COLOR);
        textArea.setHighlighter(hilit);

        footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        tabSize = new JLabel("");
        caretPosition = new JLabel("");
        symbolCount = new JLabel("");

        footerUpdate();

        footer.add(caretPosition);
        footer.add(tabSize);
        footer.add(symbolCount);

        frame = new JFrame("Notepad MVC");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(menuBar);
        frame.add(scrollPane);
        frame.add(footer, BorderLayout.SOUTH);
        frame.setSize(800, 600);
        frame.setLocation(500, 50);
        frame.setVisible(true);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    public void updateText(String value) {
        textArea.setText(value);
    }


    public void selectAllText() {
        textArea.selectAll();
    }

    public int getCursorPosition() {
        return textArea.getCaretPosition();
    }

    public File getFile() {
        JFileChooser fileChooser = new JFileChooser();
        int answer = fileChooser.showOpenDialog(frame);
        if (answer == 0) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    public void footerUpdate() {
        int row = 1;
        int column = 1;
        int caretPos = 1;
        try {
            caretPos = textArea.getCaretPosition();
            row = textArea.getLineOfOffset(caretPos);
            column = caretPos - textArea.getLineStartOffset(row);
            row = row + 1;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        caretPosition.setText("col: " + column + " | row: " + row);
        tabSize.setText("| tab width: " + textArea.getTabSize());
        symbolCount.setText("| symbols: " + caretPos);
    }

    public String getInputText() {
        return textArea.getText();
    }

    private JMenuBar createJMenuBar(Controller controller) {
        JMenu fileMenu = createFileMenu(controller);
        JMenu editMenu = createEditMenu(controller);
        JMenu formatMenu = createFormatMenu(controller);
        JMenu viewMenu = createViewMenu(controller);
        JMenu faqMenu = createFaqMenu(controller);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(viewMenu);
        menuBar.add(faqMenu);
        return menuBar;
    }

    private JMenu createFileMenu(Controller controller) {
        JMenuItem newMenuItem = new JMenuItem("New", new ImageIcon("Pictures/new.png"));
        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newMenuItem.addActionListener(controller);
        newMenuItem.setActionCommand("Create_New_Document");

        JMenuItem openMenuItem = new JMenuItem("Open", new ImageIcon("Pictures/open.png"));
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        openMenuItem.addActionListener(controller);
        openMenuItem.setActionCommand("Open_File");

        JMenuItem saveMenuItem = new JMenuItem("Save", new ImageIcon("Pictures/save.png"));
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveMenuItem.addActionListener(controller);
        saveMenuItem.setActionCommand("Save_File");

        JMenuItem saveAsMenuItem = new JMenuItem("Save As", new ImageIcon("Pictures/save_as.png"));
        saveAsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.SHIFT_MASK));
        saveAsMenuItem.addActionListener(controller);
        saveAsMenuItem.setActionCommand("Save_As_File");

        JMenuItem printMenuItem = new JMenuItem("Print", new ImageIcon("Pictures/print.png"));
        printMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        printMenuItem.addActionListener(controller);
        printMenuItem.setActionCommand("Printing_File");

        JMenuItem closeMenuItem = new JMenuItem("Exit", new ImageIcon("Pictures/delete.png"));
        closeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        closeMenuItem.addActionListener(controller);
        closeMenuItem.setActionCommand("Close_Program");

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.add(new JSeparator());
        fileMenu.add(printMenuItem);
        fileMenu.add(new JSeparator());
        fileMenu.add(closeMenuItem);
        return fileMenu;
    }

    private JMenu createEditMenu(Controller controller) {
        JMenuItem undoMenuItem = new JMenuItem("Undo", new ImageIcon("Pictures/back.png"));
        undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        undoMenuItem.addActionListener(controller);
        undoMenuItem.setActionCommand("Undo");

        JMenuItem cutMenuItem = new JMenuItem("Cut", new ImageIcon("Pictures/cut.png"));
        cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        cutMenuItem.addActionListener(controller);
        cutMenuItem.setActionCommand("Cut");

        JMenuItem copyMenuItem = new JMenuItem("Copy", new ImageIcon("Pictures/copy.png"));
        copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        copyMenuItem.addActionListener(controller);
        copyMenuItem.setActionCommand("Copy");

        JMenuItem pasteMenuItem = new JMenuItem("Paste", new ImageIcon("Pictures/past.png"));
        pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        pasteMenuItem.addActionListener(controller);
        pasteMenuItem.setActionCommand("Paste");

        JMenuItem removeMenuItem = new JMenuItem("Remove", new ImageIcon("Pictures/delete.png"));
        removeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        removeMenuItem.addActionListener(controller);
        removeMenuItem.setActionCommand("Remove");

        JMenuItem findMenuItem = new JMenuItem("Find", new ImageIcon("Pictures/find.png"));
        findMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        findMenuItem.addActionListener(controller);
        findMenuItem.setActionCommand("Open_Find_Dialog");

        JMenuItem findNextMenuItem = new JMenuItem("Find next", new ImageIcon("Pictures/findMore.png"));
        findNextMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
        findNextMenuItem.addActionListener(controller);
        findNextMenuItem.setActionCommand("Find_Next");

        JMenuItem replaceMenuItem = new JMenuItem("Replace", new ImageIcon("Pictures/change.png"));
        replaceMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        replaceMenuItem.addActionListener(controller);
        replaceMenuItem.setActionCommand("Replace");

        JMenuItem gotoMenuItem = new JMenuItem("Go to", new ImageIcon("Pictures/go.png"));
        gotoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        gotoMenuItem.addActionListener(controller);
        gotoMenuItem.setActionCommand("Go_To");

        JMenuItem selectAllMenuItem = new JMenuItem("Select all", new ImageIcon("Pictures/font.png"));
        selectAllMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        selectAllMenuItem.addActionListener(controller);
        selectAllMenuItem.setActionCommand("Select_All");

        JMenuItem timeAndDateMenuItem = new JMenuItem("Time and date", new ImageIcon("Pictures/time.png"));
        timeAndDateMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        timeAndDateMenuItem.addActionListener(controller);
        timeAndDateMenuItem.setActionCommand("Time_And_Date");

        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic('E');
        editMenu.add(undoMenuItem);
        editMenu.add(new JSeparator());
        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);
        editMenu.add(removeMenuItem);
        editMenu.add(new JSeparator());
        editMenu.add(findMenuItem);
        editMenu.add(findNextMenuItem);
        editMenu.add(replaceMenuItem);
        editMenu.add(gotoMenuItem);
        editMenu.add(new JSeparator());
        editMenu.add(selectAllMenuItem);
        editMenu.add(timeAndDateMenuItem);
        return editMenu;
    }

    private JMenu createFormatMenu(Controller controller) {
        JCheckBoxMenuItem checkBoxMenuItem = new JCheckBoxMenuItem("Word-wrap", new ImageIcon("Pictures/go.png"), true);
        checkBoxMenuItem.addActionListener(controller);
        checkBoxMenuItem.setActionCommand("word_wrap");

        JMenuItem fontMenuItem = new JMenuItem("Fonts", new ImageIcon("Pictures/color.png"));
        fontMenuItem.addActionListener(controller);
        fontMenuItem.setActionCommand("Choose_font");

        JMenu formatMenu = new JMenu("Format");
        formatMenu.setMnemonic('m');
        formatMenu.add(checkBoxMenuItem);
        formatMenu.add(fontMenuItem);

        return formatMenu;
    }

    private JMenu createViewMenu(Controller controller) {
        JCheckBoxMenuItem statusBarMenuItem = new JCheckBoxMenuItem("Status bar", new ImageIcon("Pictures/options.png"), true);
        statusBarMenuItem.addActionListener(controller);
        statusBarMenuItem.setActionCommand("Status_Bar");

        ActionListener actionListener = actionEvent -> {
            AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
            boolean selected = abstractButton.getModel().isSelected();
            if (selected) {
                footer.setVisible(true);
            } else {
                footer.setVisible(false);
            }
        };

        statusBarMenuItem.addActionListener(actionListener);
        JMenu viewMenu = new JMenu("View");
        viewMenu.setMnemonic(KeyEvent.VK_I);
        viewMenu.add(statusBarMenuItem);

        return viewMenu;
    }

    private JMenu createFaqMenu(Controller controller) {
        JMenuItem helpMenuItem = new JMenuItem("Help", new ImageIcon("Pictures/findMore.png"));
        helpMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        helpMenuItem.addActionListener(controller);
        helpMenuItem.setActionCommand("Help");

        JMenuItem aboutMenuItem = new JMenuItem("About", new ImageIcon("Pictures/marker.png"));
        aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
        aboutMenuItem.addActionListener(controller);
        aboutMenuItem.setActionCommand("About");

        JMenu faqMenu = new JMenu("FAQ");
        faqMenu.add(helpMenuItem);
        faqMenu.add(aboutMenuItem);

        return faqMenu;
    }

    public void showFindDialog(Controller controller){
        findDialog = new JDialog(frame, "Find");

        Container contentPanel = findDialog.getContentPane();
        GroupLayout layout = new GroupLayout(contentPanel);
        contentPanel.setLayout(layout);

        inputSearchField = new JTextField();
        JLabel label = new JLabel();
        label.setText("Enter text to search:");
        entryBg = inputSearchField.getBackground();

        JButton findButton = new JButton("Search");
        findButton.addActionListener(controller);
        findButton.setActionCommand("Find_Word_Button");

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(controller);
        cancelButton.setActionCommand("Cancel_Find_Dialog");

        JCheckBox caseCheckBox = new JCheckBox("Match case");
        JCheckBox wrapCheckBox = new JCheckBox("Wrap around");
        JRadioButton radioButtonUp = new JRadioButton("Find up");
        JRadioButton radioButtonDown = new JRadioButton("Find down");

        ButtonGroup group = new ButtonGroup();
        group.add(radioButtonUp);
        group.add(radioButtonDown);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addComponent(label)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(inputSearchField)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(caseCheckBox)
                                        .addComponent(radioButtonUp)
                                )
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(wrapCheckBox)
                                        .addComponent(radioButtonDown)
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
                                )
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(radioButtonUp)
                                        .addComponent(radioButtonDown)
                                ))
                .addComponent(cancelButton))
        );

        findDialog.setSize(550, 150);
        findDialog.setVisible(true);
    }

    public void closeFindDialog(){
        findDialog.dispose();
    }

    public void setHilitFindingWord(int startIndex, int endIndex) throws BadLocationException{
        hilit.addHighlight(startIndex, endIndex, painter);
    }

    public String getInputSearchField() {
        return inputSearchField.getText();
    }

    public void removeHilits(){
        hilit.removeAllHighlights();
    }

    public void setFindWordBackGround(){
        inputSearchField.setBackground(entryBg);
    }

}