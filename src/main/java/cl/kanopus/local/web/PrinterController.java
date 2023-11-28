package cl.kanopus.local.web;

import cl.kanopus.MyApplication;
import cl.kanopus.local.service.to.Document;
import cl.kanopus.local.service.PrinterService;
import cl.kanopus.local.service.to.Printer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Base64;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Pablo Diaz Saavedra
 * @email pabloandres.diazsaavedra@gmail.com
 */
@CrossOrigin
@RestController
@RequestMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = MyApplication.TAG_PRINTER)
public class PrinterController {

    @Autowired
    private PrinterService printerService;

    public static String PAPER_80mm = "80mm";
    public static String PAPER_58mm = "58mm";

    public static String printer;
    public static String paper;

    @Operation(summary = "Print a PDF file in thermal printer", description = "Allows you to print a file that is delivered, specifying a valid printer name. If the printer does not exist it will throw an error.")
    @PostMapping(value = "/print/thermal-pdf")
    @ResponseStatus(HttpStatus.CREATED)
    public Printer printThermal(@Valid @RequestBody Document document) throws Exception {
        printerService.printThermal(Base64.getDecoder().decode(document.getDataBase64()), printer);
        return new Printer(printer);
    }

    @Operation(summary = "Print a PDF file in default printer", description = "Allows you to print a file that is delivered, specifying a valid printer name. If the printer does not exist it will throw an error.")
    @PostMapping(value = "/print/default-pdf")
    @ResponseStatus(HttpStatus.CREATED)
    public Printer printDefault(@Valid @RequestBody Document document) throws Exception {
        printerService.printDefault(Base64.getDecoder().decode(document.getDataBase64()), printer);
        return new Printer(printer);
    }

    @Operation(summary = "Gets the list of connected printers", description = "It allows obtaining the list of printers that are configured within the local environment.")
    @GetMapping(value = "/printers")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getPrinters() throws Exception {
        return printerService.getPrinters();
    }

}
