
package pay.point.sample;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;

class HelloWorldPrinter implements Printable, ActionListener {
    
    
    public int print(Graphics g, PageFormat pf, int page) throws
    PrinterException {
        
        if (page > 0) {
            return NO_SUCH_PAGE;
        }
        
        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        
        /* Now we perform our rendering */
        g.drawString("Hello world!", 100, 100);
        
        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }
    
    public void actionPerformed(ActionEvent e) {
        
    }
    
    public static void main(String args[]) {
        HelloWorldPrinter test = new HelloWorldPrinter();
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(test);
        try{
            job.print();
        }
        catch(PrinterException e){}
        
    }
}

