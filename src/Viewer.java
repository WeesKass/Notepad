/*
  Notepad program made with Java and Swing with MVC pattern
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/*
    The Viewer class is our client side application, our front-end
 All the designs, styles, and appearances of our program implemented is here.
 */

public class Viewer {

    private Controller controller;
    private JTextArea textArea;
    private JFrame frame;
    private JFileChooser fileChooser;
    private JPanel status;
    private JTextField findText;
    private JLabel position;
    private Canvas canvas;
    private Model model;
    private JDialog fontChooser;
    private JLabel sampleText;
    private String sampleFont;
    private int sampleStyle, sampleSize;
    private JPanel searchPanel;

    public Viewer() {
        controller = new Controller(this);
        model = controller.getModel();
        canvas = new Canvas(model);

        fontChooser = setJFontChooser();
        textArea = new JTextArea();
        textArea.setFont(new java.awt.Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.addCaretListener(controller);
        JMenuBar menuBar = setMenuBar();

        frame = new JFrame("notepad");
        frame.setSize(700, 500);
        frame.setMinimumSize(new Dimension(600, 310));
        frame.setJMenuBar(menuBar);
        frame.add(scrollPane);
        frame.setVisible(true);
        frame.add(BorderLayout.SOUTH, setStatusPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel tools = setToolBar();
        frame.add(BorderLayout.WEST, tools);
    }

    
    public void hideSearch(){
        searchPanel.setVisible(false);
    }
    public void showSearch(){
        searchPanel.setVisible(true);
    }
    public boolean statusSearch(){
        return searchPanel.isVisible();
    }


    public JDialog getFontDialog() {
        return fontChooser;
    }

    public JLabel getSampleText() {
        return sampleText;
    }

    public int getSampleStyle() {
        return sampleStyle;
    }

    public String getSampleFont() {
        return sampleFont;
    }

    public int getSampleSize() {
        return sampleSize;
    }

    public void setSampleFont(String sampleFont) {
        this.sampleFont = sampleFont;
    }

    public void setSampleStyle(int sampleStyle) {
        this.sampleStyle = sampleStyle;
    }

    public void setSampleSize(int sampleSize) {
        this.sampleSize = sampleSize;
    }

    public JLabel getPosition(){
        return position;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void update(String text) {
        textArea.setText(text);
    }

    public String openFileChooser() {
        if (fileChooser == null) {
            fileChooser = new JFileChooser();
        }
        fileChooser.showOpenDialog(frame);
        return fileChooser.getSelectedFile().getAbsolutePath();
    }

    public int showYesNoCancelDialog() {
        String[] options = {"YES", "NO", "CANCEL"};
        return JOptionPane.showOptionDialog(new JFrame(),
                "All non-saved data will be erased!",
                "Save this text somewhere?",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);
    }

    public void showHelp() {
        JOptionPane.showMessageDialog(new JFrame(),
                "SHORTCUTS:\n" +
                        "1. CTRL + N : New File\n" +
                        "2. CTRL + O : Open...\n" +
                        "3. CTRL + S : Save\n" +
                        "4. CTRL + C : Copy\n" +
                        "5. CTRL + V : Paste\n" +
                        "6. CTRL + X : Cut\n" +
                        "7. CTRL + D : Clear\n" +
                        "8. CTRL + F : Find \n" +
                        "9. CTRL + A : Select All\n" +
                        "10 CTRL + T : Font Chooser\n" +
                        "11 CTRL + I : Status Bar" +
                        "author: Timur Kasenov",
                "Help?", JOptionPane.QUESTION_MESSAGE);
    }


    public JTextField inputWord() {
        status.setVisible(true);
        return findText;
    }

    public void enableStatusBar() {
        if (status.isVisible()) {
            status.setVisible(false);
        } else
            status.setVisible(true);
    }

    public void enableFontDialog() {
        if (fontChooser.isVisible()) {
            fontChooser.setVisible(false);
        } else
            fontChooser.setVisible(true);
    }

    private JPanel setToolBar() {
        JButton newFileButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("images/new.png"));
            newFileButton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println(e);
        }
        newFileButton.setActionCommand("New");
        newFileButton.addActionListener(controller);

        JButton openFileButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("images/open.png"));
            openFileButton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println(e);
        }
        openFileButton.setActionCommand("Open");
        openFileButton.addActionListener(controller);

        JButton saveFileButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("images/save.png"));
            saveFileButton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println(e);
        }
        saveFileButton.setActionCommand("Save");
        saveFileButton.addActionListener(controller);

        JButton cutTextButton = new JButton();
        try {

            Image img = ImageIO.read(getClass().getResource("images/cut.png"));
            cutTextButton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println(e);
        }
        cutTextButton.setActionCommand("Cut");
        cutTextButton.addActionListener(controller);

        JButton copyTextButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("images/copy.png"));
            copyTextButton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println(e);
        }
        copyTextButton.setActionCommand("Copy");
        copyTextButton.addActionListener(controller);

        JButton pasteTextButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("images/paste.png"));
            pasteTextButton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println(e);
        }
        pasteTextButton.setActionCommand("Paste");
        pasteTextButton.addActionListener(controller);

        JButton fontTextButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("images/font.png"));
            fontTextButton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println(e);
        }
        fontTextButton.setActionCommand("Font");
        fontTextButton.addActionListener(controller);

        JPanel toolBar = new JPanel();
        toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.Y_AXIS));
        toolBar.add(newFileButton);
        toolBar.add(openFileButton);
        toolBar.add(saveFileButton);
        toolBar.add(cutTextButton);
        toolBar.add(copyTextButton);
        toolBar.add(pasteTextButton);
        toolBar.add(fontTextButton);

        return toolBar;
    }

    private JMenuBar setMenuBar() {

        JMenuItem createDocumentJMenuItem = new JMenuItem("New");
        createDocumentJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        createDocumentJMenuItem.addActionListener(controller);
        createDocumentJMenuItem.setActionCommand("New");

        JMenuItem openDocumentJMenuItem = new JMenuItem("Open ...");
        openDocumentJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        openDocumentJMenuItem.addActionListener(controller);
        openDocumentJMenuItem.setActionCommand("Open");

        JMenuItem saveDocumentJMenuItem = new JMenuItem("Save");
        saveDocumentJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        saveDocumentJMenuItem.addActionListener(controller);
        saveDocumentJMenuItem.setActionCommand("Save");

        JMenuItem printDocumentJMenuItem = new JMenuItem("Print ...");
        printDocumentJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        printDocumentJMenuItem.addActionListener(controller);
        printDocumentJMenuItem.setActionCommand("Print");

        JMenuItem closeJMenuItem = new JMenuItem("Exit");
        closeJMenuItem.addActionListener(controller);
        closeJMenuItem.setActionCommand("Close");

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        fileMenu.add(createDocumentJMenuItem);
        fileMenu.add(openDocumentJMenuItem);
        fileMenu.add(saveDocumentJMenuItem);
        fileMenu.add(new JSeparator());
        fileMenu.add(printDocumentJMenuItem);
        fileMenu.add(new JSeparator());
        fileMenu.add(closeJMenuItem);

        JMenuItem cutTextJMenuItem = new JMenuItem("Cut");
        cutTextJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
        cutTextJMenuItem.addActionListener(controller);
        cutTextJMenuItem.setActionCommand("Cut");

        JMenuItem copyTextJMenuItem = new JMenuItem("Copy");
        copyTextJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        copyTextJMenuItem.addActionListener(controller);
        copyTextJMenuItem.setActionCommand("Copy");

        JMenuItem pasteTextJMenuItem = new JMenuItem("Paste");
        pasteTextJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
        pasteTextJMenuItem.addActionListener(controller);
        pasteTextJMenuItem.setActionCommand("Paste");

        JMenuItem clearTextJMenuItem = new JMenuItem("Clear");
        clearTextJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
        clearTextJMenuItem.addActionListener(controller);
        clearTextJMenuItem.setActionCommand("Clear");

        JMenuItem findTextItem = new JMenuItem("Find");
        findTextItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
        findTextItem.addActionListener(controller);
        findTextItem.setActionCommand("Find");

        JMenuItem markAllTextItem = new JMenuItem("Select All");
        markAllTextItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        markAllTextItem.addActionListener(controller);
        markAllTextItem.setActionCommand("Select All");
        
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic('E');
        editMenu.add(cutTextJMenuItem);
        editMenu.add(copyTextJMenuItem);
        editMenu.add(pasteTextJMenuItem);
        editMenu.add(clearTextJMenuItem);
        editMenu.add(new JSeparator());
        editMenu.add(findTextItem);
        editMenu.add(new JSeparator());
        editMenu.add(markAllTextItem);
        
        JMenuItem auto = new JCheckBoxMenuItem("Auto");
        auto.addActionListener(controller);
        auto.setActionCommand("Auto");



        JMenuItem font = new JMenuItem("Font");
        font.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK));
        font.addActionListener(controller);
        font.setActionCommand("Font");

        JMenu formatMenu = new JMenu("Format");
        formatMenu.setMnemonic('o');
        formatMenu.add(auto);
        formatMenu.add(font);

        JMenuItem status = new JCheckBoxMenuItem("StatusBar");
        status.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
        status.addActionListener(controller);
        status.setActionCommand("Status");
        
        JMenu viewMenu = new JMenu("View");
        viewMenu.add(status);
        
        JMenuItem helpWindow = new JMenuItem(" Help");
        helpWindow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
        helpWindow.addActionListener(controller);
        helpWindow.setActionCommand("Help");

        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(helpWindow);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(formatMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);

        return menuBar;

    }
    private JPanel setStatusPanel() {
        status = new JPanel(new BorderLayout());
        position = new JLabel("Symbols: 0  ||  Lines: 0");
        status.add(setSearchPanel());
        status.add(Box.createHorizontalStrut(100));
        status.add(position);
        status.setLayout(new FlowLayout(FlowLayout.LEFT));
        status.setVisible(true);
        return status;
    }

    public JPanel setSearchPanel(){
        searchPanel = new JPanel();
        findText = new JTextField("", 15);
        findText.setFont(new java.awt.Font("Monospaced", Font.PLAIN, 15));
        findText.addActionListener(controller);
        findText.setActionCommand("Find");

        JButton searchButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("images/find.png"));
            searchButton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println(e);
        }
        searchButton.addActionListener(controller);
        searchButton.setActionCommand("Find");

        JButton nextButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("images/next.png"));
            nextButton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println(e);
        }
        nextButton.addActionListener(controller);
        nextButton.setActionCommand("Next");
        JButton prevButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("images/prev.png"));
            prevButton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println(e);
        }
        prevButton.addActionListener(controller);
        prevButton.setActionCommand("Prev");
        searchPanel.add(findText);
        searchPanel.add(searchButton);
        searchPanel.add(nextButton);
        searchPanel.add(prevButton);
        hideSearch();
        return searchPanel;
    }

    private JDialog setJFontChooser() {
        fontChooser = new JDialog();
        fontChooser.setModal(true);
        fontChooser.setTitle("Font");
        fontChooser.setSize(434, 260);
        fontChooser.setLayout(new FlowLayout());
        fontChooser.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JLabel fontLabel = new JLabel("Font:");
        JLabel styleLabel = new JLabel("Font Style:");
        JLabel sizeLabel = new JLabel("Size:");
        JButton okButton = new JButton("OK");
        fontChooser.getRootPane().setDefaultButton(okButton);
        JButton cancelButton = new JButton("Cancel");
        JButton colorButton = new JButton("Color");
        sampleText = new JLabel("XxYyZz");
        sampleFont = "Lucida Console";
        sampleStyle = Font.PLAIN;
        sampleSize = 12;
        JComboBox fontList = new JComboBox(model.getFontList());
        JComboBox styleList = new JComboBox(model.getStyleList());
        JComboBox sizeList = new JComboBox(model.getSizes());

        JPanel font = new JPanel();
        font.setLayout(new BoxLayout(font, BoxLayout.Y_AXIS));
        font.add(fontLabel);
        font.add(Box.createRigidArea(new Dimension(0, 5)));
        font.add(fontList);

        JPanel style = new JPanel();
        style.setLayout(new BoxLayout(style, BoxLayout.Y_AXIS));
        style.add(styleLabel);
        style.add(Box.createRigidArea(new Dimension(0, 5)));
        style.add(styleList);

        JPanel size = new JPanel();
        size.setLayout(new BoxLayout(size, BoxLayout.Y_AXIS));
        size.add(sizeLabel);
        size.add(Box.createRigidArea(new Dimension(0, 5)));
        size.add(sizeList);

        JPanel colorOkCancelButton = new JPanel();
        colorOkCancelButton.setLayout(new BoxLayout(colorOkCancelButton, BoxLayout.X_AXIS));
        okButton.setMaximumSize(cancelButton.getMaximumSize());
        colorButton.setMaximumSize(okButton.getMaximumSize());
        colorOkCancelButton.add(colorButton);
        colorOkCancelButton.add(Box.createRigidArea(new Dimension(5, 0)));
        colorOkCancelButton.add(okButton);
        colorOkCancelButton.add(Box.createRigidArea(new Dimension(5, 0)));
        colorOkCancelButton.add(cancelButton);
        JPanel samplePanel = new JPanel();
        TitledBorder sampleBorder = BorderFactory.createTitledBorder("Sample");
        samplePanel.setBorder(sampleBorder);
        samplePanel.add(sampleText);
        samplePanel.setPreferredSize(new Dimension(300, 130));
        fontList.addActionListener(controller);
        styleList.addActionListener(controller);
        sizeList.addActionListener(controller);
        okButton.addActionListener(controller);
        cancelButton.addActionListener(controller);
        colorButton.addActionListener(controller);
        fontChooser.add(font);
        fontChooser.add(style);
        fontChooser.add(size);
        fontChooser.add(samplePanel);
        fontChooser.add(colorOkCancelButton);


        return fontChooser;
    }

    public void nullFindWord(){
        if(JOptionPane.showConfirmDialog(frame, "Do you want to close search panel?") == JOptionPane.YES_OPTION){
            hideSearch();
        }
    }

    public void notFound() {
        JOptionPane.showMessageDialog(frame, "The \'" + inputWord().getText() + "\' not found!");
    }
}

