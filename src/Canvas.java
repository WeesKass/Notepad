import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

/*
    The main purpose of The Canvas  class is to print a file from text.
 */
public class Canvas implements Printable {

    private Model model;
    private String[] lines;
    private int[] pages;
    
    public Canvas(Model model) {
        this.model = model;
    }

    
    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) {
        int lineHeight = g.getFontMetrics(model.getViewer().getTextArea().getFont()).getHeight();
        String text = model.getViewer().getTextArea().getText();  

        if (pages == null) {
            lines = text.split("\n");  
            int linesPerPage = (int) (pf.getImageableHeight() / lineHeight);
            int amountOfPages = (lines.length - 1) / linesPerPage;
            pages = new int[amountOfPages];
            for (int i = 0; i < amountOfPages; i++) {
                pages[i] = (i + 1) * linesPerPage;
            }
        }

        if (pageIndex > pages.length) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        int y = 0;
        int start = (pageIndex == 0) ? 0 : pages[pageIndex - 1];
        int end = (pageIndex == pages.length)
                ? lines.length : pages[pageIndex];
        for (int line = start; line < end; line++) {
            y += lineHeight;
            g.drawString(lines[line], 0, y);
        }

        return PAGE_EXISTS;  
    }

    public void print() {
        PrinterJob printer = PrinterJob.getPrinterJob();
        printer.setPrintable(this);
        boolean ok = printer.printDialog();
        if (ok) {
            try {
                printer.print();
            } catch (PrinterException ex) {
                System.out.println(ex);
            }

        }

    }
}