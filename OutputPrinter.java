import java.awt.*;
import java.awt.print.*;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class OutputPrinter implements Printable {
    String printData;
    Font font;
    List<String> textLines;
    int[] pageBreaks;
    int x;
    int y;

    public OutputPrinter(Font font, String printData) {
        this.font = font;
        this.printData = printData;
        x = 20;
        y = 20;
    }

    private void setLines(BufferedReader br, FontMetrics metrics, int pageWidth) {
        String line;
        try {
            while ((line = br.readLine()) != null) {
                if (metrics.stringWidth(line) > pageWidth) {
                    cutTextLines(line, metrics, pageWidth);
                } else {
                    textLines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        line = null;
    }

    private void cutTextLines(String lineText, FontMetrics metrics, int pageWidth) {
        String temp;
        int startIndex = 0;

        for (int lastIndex = 1; lastIndex < lineText.length() - 1; lastIndex++) {

            temp = lineText.substring(startIndex, lastIndex);

            if (metrics.stringWidth(temp) >= pageWidth) {
                lastIndex = lastIndex - 1;
                temp = lineText.substring(startIndex, lastIndex);
                textLines.add(temp);
                startIndex = lastIndex;
            }
        }
        textLines.add(lineText.substring(startIndex, lineText.length()));
        temp = null;
    }

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
        graphics.setFont(font);
        FontMetrics metrics = graphics.getFontMetrics(font);
        int lineHeight = metrics.getHeight();
        int pageHeight = (int) pageFormat.getImageableHeight() - (y * 2);
        int pageWidth = (int) pageFormat.getImageableWidth() - (x * 2);
        int linesPerPage = pageHeight / lineHeight;

        if (textLines == null) {
            textLines = new ArrayList<>();
            
            BufferedReader br = new BufferedReader(new StringReader(printData));
            setLines(br, metrics, pageWidth);
            br = null;

            int numBreaks = (textLines.size() - 1) / linesPerPage;
            pageBreaks = new int[numBreaks];
            for (int b = 0; b < numBreaks; b++) {
                pageBreaks[b] = (b + 1) * linesPerPage; 
            }
        }

        if (pageIndex > pageBreaks.length) {
            pageBreaks = null;
            textLines.clear();
            return NO_SUCH_PAGE;
        }

        graphics.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
        graphics.drawRect(x, y, pageWidth, pageHeight);

        graphics.setFont(new Font("Dialog", Font.PLAIN, 12));
        graphics.setColor(Color.RED);
        String pageNumber = "Notepad MVC | Team #1 | page-" + (pageIndex + 1);
        graphics.drawString(pageNumber, pageWidth + x - metrics.stringWidth(pageNumber), pageHeight + (y * 2) - metrics.getDescent());
        graphics.setFont(font);
        graphics.setColor(Color.BLACK);

        int start = (pageIndex == 0) ? 0 : pageBreaks[pageIndex-1];
        int end = (pageIndex == pageBreaks.length) ? textLines.size() : pageBreaks[pageIndex];

        for (int line = start; line < end; line++) {
            y = y + lineHeight;
            graphics.drawString(textLines.get(line), x, y);
        }

        y = 20;

        return PAGE_EXISTS;
    }
}