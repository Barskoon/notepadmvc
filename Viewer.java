import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class Viewer {

    private JFrame frame;
    private JTextArea textArea;

    public Viewer() {
        Controller controller = new Controller(this);

        JMenuBar menuBar = createJMenuBar(controller);

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        frame = new JFrame("Notepad MVC");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(scrollPane);
        frame.setJMenuBar(menuBar);
        frame.setSize(800, 600);
        frame.setLocation(500, 50);
        frame.setVisible(true);
    }

    public void update(String value) {
        textArea.setText(value);
    }

    public File getFile() {
        JFileChooser fileChooser = new JFileChooser();
        int answer = fileChooser.showOpenDialog(frame);
        if (answer == 0) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    private JMenuBar createJMenuBar(Controller controller) {
        JMenu fileMenu = createFileMenu(controller);
        JMenu editMenu = createEditMenu();
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        return menuBar;
    }

    private JMenu createFileMenu(Controller controller) {
        JMenuItem newMenuItem = new JMenuItem("New", new ImageIcon("Pictures/images/new.gif"));
        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newMenuItem.addActionListener(controller);
        newMenuItem.setActionCommand("Create_New_Document");


        JMenuItem openMenuItem = new JMenuItem("Open", new ImageIcon("Pictures/images/open.gif"));
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        openMenuItem.addActionListener(controller);
        openMenuItem.setActionCommand("Open_File");

        JMenuItem saveMenuItem = new JMenuItem("Save", new ImageIcon("Pictures/images/save.gif"));
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

        JMenuItem saveAsMenuItem = new JMenuItem("Save As", new ImageIcon("Pictures/images/save_as.gif"));

        JMenuItem printMenuItem = new JMenuItem("Print", new ImageIcon("Pictures/images/new.gif"));
        printMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));

        JMenuItem closeMenuItem = new JMenuItem("Exit");
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

    private JMenu createEditMenu() {
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic('F');
        return editMenu;
    }
}