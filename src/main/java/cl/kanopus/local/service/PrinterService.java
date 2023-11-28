package cl.kanopus.local.service;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttribute;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import org.springframework.stereotype.Service;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;

/**
 *
 * @author Pablo Diaz Saavedra
 * @email pabloandres.diazsaavedra@gmail.com
 *
 */
@Service
public class PrinterService {

    private final int THERMAL_WIDTH = (int) (80 * 2.83); //80mm
    private final int THERMAL_HEIGHT = (int) (2000 * 2.83);  //297mm (This generates the maximum length, it is multiplied by 3 to avoid cuts)

    public List<String> getPrinters() throws PrinterException {
        List<String> availables = new ArrayList<>();
        PrintService[] printers = PrinterJob.lookupPrintServices();
        if (printers != null && printers.length > 0) {
            for (PrintService ps : printers) {
                availables.add(ps.getName());
            }
        }
        return availables;
    }

    public void printDefault(byte[] pdf, String name) throws PrinterException {
        printPdf(pdf, getDefaultPrinter(name));
    }

    public void printThermal(byte[] pdf, String name) throws PrinterException {
        printPdf(pdf, getThermalPrinter(name));
    }

    private void printPdf(byte[] pdf, PrinterJob job) throws PrinterException {
        PDDocument document = null;
        try {
            int marginLeft = 0;
            document = PDDocument.load(pdf);

            MediaSize media = MediaSize.getMediaSizeForName(MediaSizeName.ISO_A4);
            PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
            attributes.add(media.getMediaSizeName());

            PageFormat pageFormat = (PageFormat) job.getPageFormat(attributes).clone();

            Paper paper = pageFormat.getPaper();
            paper.setImageableArea(marginLeft, 0, paper.getWidth(), paper.getHeight());
            pageFormat.setPaper(paper);

            PDFPrintable printable = new PDFPrintable(document, Scaling.ACTUAL_SIZE, false, 0, false);

            Book book = new Book();
            book.append(printable, pageFormat);
            job.setPageable(book);
            job.print(attributes);

        } catch (PrinterException pe) {
            throw pe;
        } catch (Exception ex) {
            throw new PrinterException(ex.getMessage());
        } finally {
            if (document != null) {
                try {
                    document.close();
                } catch (IOException ex) {
                    Logger.getLogger(PrinterService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    private PrinterJob getDefaultPrinter(String name) throws PrinterException {
        PrinterJob defaultPrinter = null;
        PrintService[] printers = PrinterJob.lookupPrintServices();
        PrinterJob job = PrinterJob.getPrinterJob();
        for (PrintService ps : printers) {
            if (name != null && name.equalsIgnoreCase(ps.getName())) {
                job.setPrintService(ps);
                defaultPrinter = job;
                break;
            }
        }

        if (defaultPrinter == null) {
            throw createPrinterException(name);
        }
        return defaultPrinter;
    }

    private PrinterJob getThermalPrinter(String name) throws PrinterException {
        PrinterJob printerThermal = getDefaultPrinter(name);

        PrintRequestAttributeSet printReqAttrs = new HashPrintRequestAttributeSet();
        PrintRequestAttribute jobPrinArea = new MediaPrintableArea(0, 0, THERMAL_WIDTH, THERMAL_HEIGHT, MediaPrintableArea.MM);
        printReqAttrs.add(jobPrinArea);
        //pass specific attributes to printer
        printerThermal.print(printReqAttrs);

        return printerThermal;
    }

    private PrinterException createPrinterException(String name) throws PrinterException {
        StringBuilder sb = new StringBuilder();
        sb.append("The printer named \"").append(name).append("\" does not exist on your computer.\n");
        List<String> printers = getPrinters();
        if (printers.isEmpty()) {
            sb.append("No printers detected on your computer!\n");
        } else {
            sb.append("The following printers have been detected on your computer:\n");
            for (String impresora : printers) {
                sb.append("    printer: ").append(impresora).append("\n");
            }
        }
        sb.append("\nContact your administrator and indicate this information");
        throw new PrinterException(sb.toString());
    }
}
