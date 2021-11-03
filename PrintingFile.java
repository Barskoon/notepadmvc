import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.FontMetrics;
import java.awt.print.PrinterJob;
import java.awt.print.PrinterException;
import java.awt.print.Printable;
import java.awt.print.PageFormat;
import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;

public class PrintingFile {

    public void printingFile(String textForPrinting) {
        PrinterJob printing = PrinterJob.getPrinterJob();
        printing.setPrintable(new OutputPrinter(textForPrinting));
        PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();
        boolean doPrint = printing.printDialog(attributeSet);

        if (doPrint) {
            try {
                printing.print();
            } catch (PrinterException e) {
                System.out.println("Print error!\n\n" + e);
            }
        }
    }

    static class OutputPrinter implements Printable {

        private final String printData;

        public OutputPrinter(String printDataIn) {
            this.printData = printDataIn;
        }

        @Override
        public int print(Graphics g, PageFormat pf, int page) {
            if (page > 0) {
                return NO_SUCH_PAGE;
            }

            Graphics2D g2d = (Graphics2D)g;
            int x = (int) pf.getImageableX();
            int y = (int) pf.getImageableY();

            FontMetrics metrics = g.getFontMetrics();
            int lineHeight = metrics.getHeight();

            BufferedReader br = new BufferedReader(new StringReader(printData));

            try {
                String line;
                while ((line = br.readLine()) != null) {
                    y += lineHeight;
                    g2d.drawString(line, x, y);
                }

            } catch (IOException e) {
                System.out.println("Print error!\n\n" + e);
            }

            return PAGE_EXISTS;
        }
    }
}