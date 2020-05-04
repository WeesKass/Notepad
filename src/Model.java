import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/*
    The model class represents the business layer of application.
  So the controller when caught the action commands, immediately sends to the Model class
  */

public class Model {

    private Viewer viewer;
    private String text;
    private ArrayList<Integer> indexList;
    private Integer selectedIndex;
    private Highlighter highlighter;
    private Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
    private Highlighter.HighlightPainter selectedPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.PINK);
    private String[] fontList, styleList, sizes;

    public Model(Viewer viewer) {
        this.viewer = viewer;
        fontList = getAllFonts();
        styleList = getAllStyles();
        sizes = Arrays.toString(getAllSizes()).split("[\\[\\]]")[1].split(", ");
        indexList = new ArrayList<>();
    }


    public String[] getFontList() {
        return fontList;
    }

    public String[] getStyleList() {
        return styleList;
    }

    public String[] getSizes() {
        return sizes;
    }

    private String[] getAllFonts() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    }

    private String[] getAllStyles() {
        return new String[]{"Regular", "Italic", "Bold"};
    }

    private int[] getAllSizes() {
        return new int[]{
                8, 9, 10, 11, 12, 14, 16, 18, 20,
                22, 24, 26, 28, 36, 48, 72
        };
    }

    public void doAction(String value) {
        switch (value) {
            case "New":
                newDocument();
                break;
            case "Open":
                openDocument();
                break;
            case "Save":
                saveDocument();
                break;
            case "Print":
                print();
                break;
            case "Close":
                exit();
            case "Cut":
                cutText();
                break;
            case "Copy":
                copyText();
                break;
            case "Paste":
                pasteText();
                break;
            case "Clear":
                clearText();
                break;
            case "Find":
                findText();
                break;
            case "Next":
                goToNext();
                break;
            case "Prev":
                goToPrev();
                break;
            case "Select All":
                selectAll();
                break;
            case "Help":
                viewHelp();
                break;
            case "Auto":
                auto();
                break;
            case "Font":
            case "Cancel":
                viewer.enableFontDialog();
                break;
            case "Status":
                viewStatusBar();
                break;
            case "OK":
                changeFont();
                break;
            case "Color":
                changeTextColor();
                break;
            default:
        }
    }

    public void doCaretAction() {
        viewer.getPosition().setText("Symbols: " + viewer.getTextArea().getText().length() + "  ||  Lines: " + viewer.getTextArea().getLineCount());
    }

    public void doFontAction(JComboBox<String> list) {
        String selectedValue = (String) list.getSelectedItem();
        int sampleStyle;
        if (Arrays.asList(fontList).contains(selectedValue)) {
            viewer.setSampleFont(selectedValue);
        } else if (Arrays.asList(styleList).contains(selectedValue)) {
            sampleStyle = styleList[list.getSelectedIndex()].equals("Regular") ? Font.PLAIN
                    : styleList[list.getSelectedIndex()].equals("Italic") ? Font.ITALIC
                    : Font.BOLD;
            viewer.setSampleStyle(sampleStyle);
        } else {
            viewer.setSampleSize(Integer.parseInt(sizes[list.getSelectedIndex()]));
        }
        viewer.getSampleText().setFont(new Font(viewer.getSampleFont(), viewer.getSampleStyle(), viewer.getSampleSize()));
    }

    public Viewer getViewer() {
        return viewer;
    }

    private String open(String fileName) {
        return WriteAndRead.readFromFile(fileName);
    }

    private void save(String fileName, String text) {
        WriteAndRead.writeToFile(fileName, text);
    }

    private void print() {
        viewer.getCanvas().print();
    }

    private void exit() {
        System.exit(0);
    }

    private void newDocument() {
        String text = viewer.getTextArea().getText();
        if (!text.equals("")) {
            int returnValue = viewer.showYesNoCancelDialog();
            if (returnValue == 0) {
                if (saveDocument()) {
                    viewer.update("");
                }
            } else if (returnValue == 1) {
                viewer.update("");
            }
        }
    }

    private void openDocument() {
        try {
            String fileName = viewer.openFileChooser();
            text = open(fileName);
            viewer.update(text);
        } catch (NullPointerException npe) {
            System.out.println("File was not chosen!");
        }
    }

    private boolean saveDocument() {
        try {
            String fileName = viewer.openFileChooser();
            text = viewer.getTextArea().getText();
            save(fileName, text);
            return true;
        } catch (NullPointerException npe) {
            System.out.println("File was not chosen!");
            return false;
        }
    }

    private void cutText() {
        viewer.getTextArea().cut();
    }

    private void copyText() {
        viewer.getTextArea().copy();
    }

    private void pasteText() {
        viewer.getTextArea().paste();
    }

    private void clearText() {
        int start = viewer.getTextArea().getSelectionStart();
        int end = viewer.getTextArea().getSelectionEnd();
        viewer.getTextArea().replaceRange("", start, end);
    }

    private void findText() {
        DefaultHighlighter highlighter = (DefaultHighlighter) viewer.getTextArea().getHighlighter();
        highlighter.setDrawsLayeredHighlights(false);
        highlighter.removeAllHighlights();
        indexList.clear();
        String text = viewer.getTextArea().getText();
        String input;
        if (viewer.statusSearch()) {
            input = viewer.inputWord().getText();
        } else {
            viewer.showSearch();
            return;
        }
        int length = text.length();
        int inputLength = input.length();
        int lastIndex = 0;
        int counter = 0;
        do {
            System.out.println(counter++);
            System.out.println("Text length " + length);
            System.out.println("Word from " + lastIndex + " till " + (lastIndex + inputLength));
            System.out.println("Last index in list " + (indexList.isEmpty() ? "empty" : indexList.get(indexList.size() - 1)));
            if ((lastIndex = text.indexOf(input, lastIndex)) == -1) {
                if (indexList.isEmpty()) {
                    viewer.notFound();
                } else {
                    selectedIndex = -1;
                }
                break;
            }
            indexList.add(lastIndex);
            try {
                highlighter.addHighlight(lastIndex, lastIndex += inputLength, painter);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }

        } while (!input.isEmpty() && lastIndex <= length);
    }

    public void goToNext() {
        if (indexList.isEmpty()) {
            findText();
        } else {
            if (selectedIndex != null && selectedIndex < indexList.size() - 1) {
                int from = indexList.get(++selectedIndex == -1 ? ++selectedIndex : selectedIndex);
                int to = from + viewer.inputWord().getText().length();
                viewer.getTextArea().getCaret().setSelectionVisible(true);
                viewer.getTextArea().setSelectionStart(from);
                viewer.getTextArea().setSelectionEnd(to);
            }
        }
    }

    public void goToPrev() {
        if (indexList.isEmpty()) {
            findText();
        } else {
            if (selectedIndex != null && selectedIndex > 0) {
                int from = indexList.get(--selectedIndex == indexList.size() ? --selectedIndex : selectedIndex);
                int to = from + viewer.inputWord().getText().length();
                viewer.getTextArea().getCaret().setSelectionVisible(true);
                viewer.getTextArea().select(from, to);
            }
        }
    }

    private void selectAll() {
        viewer.getTextArea().selectAll();
    }

    private void auto() {
        if (viewer.getTextArea().getLineWrap()) {
            viewer.getTextArea().setLineWrap(false);
        } else {
            viewer.getTextArea().setLineWrap(true);
        }
    }

    private void changeFont() {
        viewer.getTextArea().setFont((viewer.getSampleText().getFont()));
        viewer.getTextArea().setForeground(viewer.getSampleText().getForeground());
        viewer.enableFontDialog();
    }

    private void viewHelp() {
        viewer.showHelp();
    }

    private void viewStatusBar() {
        viewer.enableStatusBar();
    }

    private void changeTextColor() {
        Color color = JColorChooser.showDialog(viewer.getFontDialog(), "Font", Color.BLACK);
        System.out.println(color);
        if (color != null) {
            viewer.getSampleText().setForeground(color);
        }
    }
}
