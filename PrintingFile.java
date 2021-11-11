import java.awt.*;
import java.awt.print.*;
import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class PrintingFile implements Task {

    private Viewer viewer;

    public PrintingFile(Viewer viewer) {
        this.viewer = viewer;
    }

    public void doTask() {
        try {
            String textForPrinting = viewer.getInputText();
            PrinterJob printing = PrinterJob.getPrinterJob();
            PageFormat pageFormat = printing.defaultPage();

            System.out.println(printing.getPrintService());
            printing.setPrintable(new OutputPrinter(textForPrinting, pageFormat));

            if (printing.printDialog()) {
                printing.print();
            }

        } catch (PrinterException | IOException e) {
                System.out.println("Print error!\n\n" + e);
        }
    }
}

class OutputPrinter implements Printable {
    private int currentPageIndex;
    private List<String> lineList;
    private List<List<String>> pageList;
    private List<String> page;
    private List<String> currentPage;
    private int fontSize;

    OutputPrinter(String printData, PageFormat pageFormat) throws IOException {
        fontSize = 12;
        BufferedReader br = new BufferedReader(new StringReader(printData));
        String line;
        lineList = new ArrayList<>();
        pageList = new ArrayList<>();
        page = new ArrayList<>();

        while ((line = br.readLine()) != null)
            lineList.add(line);

        br.close();
        pageInit(pageFormat);
    }

    public void pageInit(PageFormat pageFormat) {
        currentPageIndex = 0;
        float y = fontSize;

        for (String line : lineList) {
            page.add(line);
            y += fontSize;
            if (y + fontSize * 2 > pageFormat.getImageableHeight()) {
                y = 0;
                pageList.add(page);
                page = new ArrayList<>();
            }
        }

        if (page.size() > 0)
            pageList.add(page);
    }

    public void paintComponent(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics;
        currentPage = pageList.get(currentPageIndex);
        int x = 0;
        int y = fontSize;

        for (String line : currentPage) {
            if (line.length() > 0)
                g2.drawString(line, x, y);
            y += fontSize;
        }
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
        if (pageIndex >= pageList.size())
            return NO_SUCH_PAGE;

        currentPageIndex = pageIndex;
        Graphics2D g2 = (Graphics2D) graphics;
        g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        paintComponent(g2);
        return PAGE_EXISTS;
    }
}