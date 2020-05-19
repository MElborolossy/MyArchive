package utils;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;


public class PrintPdf {
    private static final PrintRequestAttributeSet atts = new HashPrintRequestAttributeSet();

    // List All Available Printers
    public static String[] listPrinters() {
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, atts);
        System.out.println("Found " + services.length + " printers");
        String[] printerList = new String[services.length];
        for (int i = 0; i < services.length; i++) {
            printerList[i] = services[i].getName();
        }
        return printerList;
    }

    // Print using PDFtoPrinter.EXE
    public static void print(String filePath, String dPrinter) throws IOException, URISyntaxException {
        CodeSource codeSource = PrintPdf.class.getProtectionDomain().getCodeSource();
        File jarFile = new File(codeSource.getLocation().toURI().getPath());
        String jarDir = jarFile.getParentFile().getPath();
        String programPath = jarDir + "\\PDFtoPrinter.exe";
        String[] printStr = {programPath, filePath, dPrinter};
        Process runtimeProcess = Runtime.getRuntime().exec(printStr);
        System.out.println(runtimeProcess);
    }
}
