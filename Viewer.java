import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class Viewer {

    private JFrame frame;
    private JTextArea textArea;
    private JFileChooser fileChooser;
    private JMenuItem closeMenuItem;
    private JLabel tabSize;
    private JLabel caretPosition;
    private JLabel symbolCount;
    private JPanel footer;
    private boolean b;
    private File file;
    private Font font = new Font("Dialog",Font.PLAIN,12);

    private JDialog findDialog;
    private JDialog replaceDialog;
    private JDialog gotoDialog;
    private JTextField inputSearchField;
    private JTextField inputReplaceFromField;
    private JTextField inputReplaceToField;
    private JTextField inputGotoLineField;
    private JCheckBox caseCheckBox;
    private JCheckBox wrapCheckBox;

    private Highlighter hilit;
    private Highlighter.HighlightPainter painter;
    private Color entryBg;
    private final Color HILIT_COLOR = Color.LIGHT_GRAY;

    public Viewer() {
        Controller controller = new Controller(this);
        DocumentController documentController = new DocumentController(this);
        CaretController caretController = new CaretController(this);

        JMenuBar menuBar = createJMenuBar(controller);

        fileChooser = new JFileChooser();

        textArea = new JTextArea();
        textArea.addCaretListener(caretController);
        textArea.getDocument().addDocumentListener(documentController);
        textArea.setLineWrap(true);
        textArea.setFont(font);
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

        ImageIcon logo = new ImageIcon("Pictures/logo.png");

        frame = new JFrame("New - Notepad MVC");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeMenuItem.doClick();
            }
        });
        frame.setIconImage(logo.getImage());
        frame.setJMenuBar(menuBar);
        frame.add(scrollPane);
        frame.add(footer, BorderLayout.SOUTH);
        frame.setSize(800, 600);
        frame.setLocation(500, 50);
        frame.setVisible(true);

        b = false;
        file = null;
    }

    public void setBool(boolean b) {
        this.b = b;
    }

    public boolean getBool() {
        return b;
    }

    public void setFrameTitle(File fileName) {
        frame.setTitle(fileName.getName() + " - Notepad MVC");
    }

    public void setFileName(File file) {
        this.file = file;
    }

    public File getFileName() {
        return file;
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
        int cursorPosition;
        try {
            cursorPosition = textArea.getCaretPosition();
        } catch (NullPointerException e){
            cursorPosition = 0;
        }
        return cursorPosition;
    }

    public File getFile() {
        int answer = fileChooser.showOpenDialog(frame);
        if (answer == 0) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    public File getFileForSaving() {
        int answer = fileChooser.showSaveDialog(frame);
        if (answer == 0) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    public int getAnswer() {
        String temp = "Do you want to save the changes to \n";
        if (getFileName() == null) {
            temp = temp + "New?";
        } else {
            temp = temp + getFileName().getPath() + "?";
        }
        Object[] options = {"Save", "Don't save", "Cancel"};
        int n = JOptionPane.showOptionDialog(frame, temp, "Notepad MVC", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
        return n;
    }

    public int getAnswerConfirmReplace() {
        Object[] options = {"Yes", "No"};
        int n = JOptionPane.showOptionDialog(frame, "Do you want to replace\n" + getFileName().getName() + "?",
                // do not use a custom Icon
                "Notepad MVC", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                // the titles of buttons
                options,
                // default button title
                options[0]);
        return n;
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

    public void updateFont(Font font) {
        this.font = font;
        textArea.setFont(font);
    }

    public Font getFonts() { return font; }

    public void cutText() {
        if (textArea.getSelectedText() != null) {
            textArea.cut();
        } else {
            showMessage("Nothing to cut");
        }
    }

    public void copyText() {
        if (textArea.getSelectedText() != null) {
            textArea.copy();
        } else {
            showMessage("Nothing to copy");
        }
    }

    public void pasteText() {
        textArea.paste();
        textArea.getDocument();
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

        closeMenuItem = new JMenuItem("Exit", new ImageIcon("Pictures/exit.png"));
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
        JMenuItem undoMenuItem = new JMenuItem("Undo", new ImageIcon("Pictures/undo.png"));
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
        replaceMenuItem.setActionCommand("Open_Replace_Dialog");

        JMenuItem gotoMenuItem = new JMenuItem("Go to", new ImageIcon("Pictures/go.png"));
        gotoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        gotoMenuItem.addActionListener(controller);
        gotoMenuItem.setActionCommand("Go_To");

        JMenuItem selectAllMenuItem = new JMenuItem("Select all", new ImageIcon("Pictures/marker.png"));
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
        JCheckBoxMenuItem checkBoxMenuItem = new JCheckBoxMenuItem("Word-wrap", new ImageIcon("Pictures/wrap.png"), true);
        checkBoxMenuItem.setActionCommand("word_wrap");

        ActionListener actionListener = actionEvent -> textArea.setLineWrap(checkBoxMenuItem.isSelected());
        checkBoxMenuItem.addActionListener(actionListener);

        JMenuItem fontMenuItem = new JMenuItem("Fonts", new ImageIcon("Pictures/font.png"));
        fontMenuItem.addActionListener(controller);
        fontMenuItem.setActionCommand("Choose_font");

        JMenu formatMenu = new JMenu("Format");
        formatMenu.setMnemonic('m');
        formatMenu.add(checkBoxMenuItem);
        formatMenu.add(fontMenuItem);

        return formatMenu;
    }

    private JMenu createViewMenu(Controller controller) {
        JCheckBoxMenuItem statusBarMenuItem = new JCheckBoxMenuItem("Status bar", new ImageIcon("Pictures/showCross.png"), true);
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
        JMenuItem helpMenuItem = new JMenuItem("Help", new ImageIcon("Pictures/help.png"));
        helpMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        helpMenuItem.addActionListener(controller);
        helpMenuItem.setActionCommand("Help");

        JMenuItem aboutMenuItem = new JMenuItem("About", new ImageIcon("Pictures/info.png"));
        aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
        aboutMenuItem.addActionListener(controller);
        aboutMenuItem.setActionCommand("About");

        JMenu faqMenu = new JMenu("FAQ");
        faqMenu.add(helpMenuItem);
        faqMenu.add(aboutMenuItem);

        return faqMenu;
    }

    public void createReplaceDialog(Controller controller){
        replaceDialog = new JDialog(frame, "Replace");

        Container contentPanel = replaceDialog.getContentPane();
        GroupLayout layout = new GroupLayout(contentPanel);
        contentPanel.setLayout(layout);

        inputReplaceFromField = new JTextField();
        JLabel labelReplaceFrom = new JLabel();
        labelReplaceFrom.setText("From:");

        inputReplaceToField = new JTextField();
        JLabel labelReplaceTo = new JLabel();
        labelReplaceTo.setText("To:");

        JButton replaceButton = new JButton("Replace");
        replaceButton.addActionListener(controller);
        replaceButton.setActionCommand("Replace_Word_Button");

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(controller);
        cancelButton.setActionCommand("Cancel_Replace_Dialog");

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

        replaceDialog.setSize(550, 120);
        replaceDialog.setVisible(true);
    }

    public void createFindDialog(Controller controller){
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

        JButton cancelReplaceButton = new JButton("Cancel");
        cancelReplaceButton.addActionListener(controller);
        cancelReplaceButton.setActionCommand("Cancel_Find_Dialog");

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
                        .addComponent(cancelReplaceButton))

        );
        layout.linkSize(SwingConstants.HORIZONTAL, findButton, cancelReplaceButton);

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
                        .addComponent(cancelReplaceButton))
        );

        findDialog.setSize(550, 150);
        findDialog.setVisible(true);
    }

    public void createGotoDialog(Controller controller){
        gotoDialog = new JDialog(frame, "Go to");

        Container contentPanel = gotoDialog.getContentPane();
        GroupLayout layout = new GroupLayout(contentPanel);
        contentPanel.setLayout(layout);

        inputGotoLineField = new JTextField();

        JLabel labelGoto = new JLabel();
        labelGoto.setText("Row number");

        JButton goToButton = new JButton("Go to");
        goToButton.addActionListener(controller);
        goToButton.setActionCommand("Go_To_Button");

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


        gotoDialog.setSize(250, 70);
        gotoDialog.setVisible(true);
    }

    public void closeFindDialog(){
        removeHilits();
        findDialog.dispose();
    }
    public void closeReplaceDialog() {
        replaceDialog.dispose();
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

    public boolean getMatchCaseValue() {
        return caseCheckBox.isSelected();
    }

    public boolean getWrapTextValue() {
        return wrapCheckBox.isSelected();
    }


    public String getSelectedText() {
        try {
            return textArea.getSelectedText();
        } catch (NullPointerException e) {
            return "There is not selected text";
        }

    }
    public String getReplaceFromWord() {
        return inputReplaceFromField.getText();
    }

    public String getReplaceToWord() {
        return inputReplaceToField.getText();
    }

    public String getGotoRowNumber(){
        return inputGotoLineField.getText();
    }

    public void setCursorPosition(int position){
        textArea.setCaretPosition(position);
    }
}