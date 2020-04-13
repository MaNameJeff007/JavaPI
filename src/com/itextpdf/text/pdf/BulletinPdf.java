/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itextpdf.text.pdf;

import com.itextpdf.text.Document;
import java.awt.Desktop;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.URI;
import java.net.URLConnection;
import java.net.URL;
//import com.gnostice.pdfone.PdfDocument;
/**
 *
 * @author dell
 */
public class BulletinPdf {
    
    public void Bullentin(int idel,int trim,int classe) throws IOException
    {
       Desktop d=Desktop.getDesktop();
       d.browse(URI.create("http://localhost/Ecole--Edtech1/Ecole--Edtech1/web/app_dev.php/moyenne1/"+idel+"/"+trim+"/"+classe));  
     // d.browse(URI.create("http://localhost/Ecole--Edtech1/Ecole--Edtech1/web/app_dev.php/moyenne1/"+18+"/"+1+"/"+5));  
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
      
      
     
      
    }
            
        
    /*
       
        URL url1 =
      new URL("http://localhost/Ecole--Edtech1/Ecole--Edtech1/web/app_dev.php/moyenne1/7/1/1");

    byte[] ba1 = new byte[1024];
    int baLength;
    FileOutputStream fos1 = new FileOutputStream("download.pdf");

    try {
      // Contacting the URL
      System.out.print("Connecting to " + url1.toString() + " ... ");
      URLConnection urlConn = url1.openConnection();

      // Checking whether the URL contains a PDF
      if (!urlConn.getContentType().equalsIgnoreCase("application/pdf")) {
          System.out.println("FAILED.\n[Sorry. This is not a PDF.]");
      } else {
        try {

          // Read the PDF from the URL and save to a local file
          InputStream is1 = url1.openStream();
          while ((baLength = is1.read(ba1)) != -1) {
              fos1.write(ba1, 0, baLength);
          }
          fos1.flush();
          fos1.close();
          is1.close();
           
          // Load the PDF document and display its page count
          System.out.print("DONE.\nProcessing the PDF ... ");
          //rt.exec(url1);    
          /*
          PdfDocument doc = new PdfDocument();
          //PdfWriter.getInstance(doc, new FileOutputStream(fileName));
          try {
            doc.load("download.pdf");
            System.out.println("DONE.\nNumber of pages in the PDF is " +
                              doc.getPageCount());
                              //doc.open();
            doc.close();
          } catch (Exception e) {
            System.out.println("FAILED.\n[" + e.getMessage() + "]");
          }
 
        } catch (ConnectException ce) {
          System.out.println("FAILED.\n[" + ce.getMessage() + "]\n");
        }
      }

    } catch (NullPointerException npe) {
      System.out.println("FAILED.\n[" + npe.getMessage() + "]\n");
    }
    }
 */
}
