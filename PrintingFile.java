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
            PageFormat pageFormat = printing.pageDialog(printing.defaultPage());
            printing.setPrintable(new OutputPrinter(viewer, textForPrinting, pageFormat));

            if (printing.printDialog()) {
                printing.print();
            }

        } catch (PrinterException | IOException e) {
                System.out.println("Print error!\n\n" + e);
        }
    }
}

class OutputPrinter implements Printable {
    private int currentPageIndex = 0;
    private List<String> lineList;
    private List<List<String>> pageList;
    private List<String> page;
    private List<String> currentPage;
    private Font font;

    OutputPrinter(Viewer viewer, String printData, PageFormat pageFormat) throws IOException {
        BufferedReader br = new BufferedReader(new StringReader(printData));
        lineList = new ArrayList<>();
        pageList = new ArrayList<>();
        page = new ArrayList<>();
        font = viewer.getFonts();
        setLines(br);
        br.close();
        pageInit(pageFormat);
    }

    private void setLines(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            lineList.add(line);
        }
    }

    public void pageInit(PageFormat pageFormat) {
        float y = font.getSize();

        for (String line : lineList) {
            page.add(line);
            y += font.getSize();
            if (y + font.getSize() * 3 > pageFormat.getImageableHeight()) {
                y = 0;
                pageList.add(page);
                page = new ArrayList<>();
            }
        }

        if (page.size() > 0)
            pageList.add(page);
    }

    public void paintComponent(Graphics graphics) {
        currentPage = pageList.get(currentPageIndex);
        int x = 0;
        int y = font.getSize();

        for (String line : currentPage) {
            if (line.length() > 0)
                graphics.drawString(line, x, y);
                y += font.getSize();
        }

        graphics.drawString("Notepad MVC | Team #1 | page " + (currentPageIndex + 1), font.getSize() / 2, 695);
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
        if (pageIndex >= pageList.size())
            return NO_SUCH_PAGE;

        currentPageIndex = pageIndex;
        graphics.setFont(font);
        graphics.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
        paintComponent(graphics);
        return PAGE_EXISTS;
    }
}