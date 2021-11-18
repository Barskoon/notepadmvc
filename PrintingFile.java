import java.awt.print.*;

public class PrintingFile implements Task {

    private Viewer viewer;

    public PrintingFile(Viewer viewer) {
        this.viewer = viewer;
    }

    public void doTask() {
        String textForPrinting = viewer.getInputText();
        PrinterJob printing = PrinterJob.getPrinterJob();
        printing.setPrintable(new OutputPrinter(viewer.getFonts(), textForPrinting));

        if (printing.printDialog()) {
            try {
                printing.print();
                viewer.showMessage("Printout was successful!");
            } catch (PrinterException ex) {
                System.out.println(ex);
                viewer.showMessage("Printout was fail!");
            }
        }
    }
}
