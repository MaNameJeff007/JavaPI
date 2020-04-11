/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itextpdf.text.pdf;

import Entities.Classe;
import Entities.Matiere;
import Entities.Salle;
import Entities.Seance;
import Entities.User;
import Services.ServiceCoeff;
import Services.ServiceSeance;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dell
 */
public class testPdf {

    
    public void OpenPdf(int idC ) throws DocumentException
    {
             
        try {
            String fileName="C:\\Users\\dell\\Documents\\NetBeansProjects\\EcoleTest_Java\\test.pdf";
              Document document =new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            
        document.open();
        Paragraph p=new Paragraph("this is text tst");
             ServiceSeance sr=new   ServiceSeance();
              PdfPTable table1 = new PdfPTable(2);
               PdfPCell c1=new PdfPCell(new Paragraph ("Emploi de temps "));
               c1.setColspan(8);
               c1.setHorizontalAlignment(Element.ALIGN_CENTER);
               c1.setBackgroundColor(BaseColor.GREEN);
               c1.setPadding(10.0f);
               table1.addCell(c1);
          document.add(table1);  
         /*  c1=new PdfPCell(new Phrase ("mardi"));
            table.addCell(c1);
           
            c1=new PdfPCell(new Phrase ("mercredi"));
            table.addCell(c1); 
            
            c1=new PdfPCell(new Phrase ("jeudi"));
            table.addCell(c1); 
            
             c1=new PdfPCell(new Phrase ("vendredi"));
            table.addCell(c1); 
            
             c1=new PdfPCell(new Phrase ("samedi"));
            table.addCell(c1); 
            
           // table.setHeaderRows(6);*/
       // List<Seance> res = sr.afficherSeance();
         List<Seance> res = sr.SelectSeance(idC);
        
          for (Seance e:res)
    {
         Paragraph p1=new Paragraph("------------------------------------------");
         p1.setAlignment(Element.ALIGN_CENTER);
        int id=e.getId();
        int m=e.getMatiere();
        String j=e.getJour();
          String hdeb=e.getHdeb();
         String hfin=e.getHfin();
         int salleId=e.getSalle();
         int ensS=e.getEnseignant();
                 
                 
          PdfPTable table = new PdfPTable(6);
         List<Matiere>  cl =sr.findM(m);
           List<User>  c2 =sr.findEns(ensS);
         List <Salle> c3 =sr.findSalleE(salleId);
         for (Matiere c : cl)
         {
              String matiere=c.getNom();
           //table.addCell("Matiere");
           PdfPCell cm=new PdfPCell(new Paragraph ("Matiere :  "));
               cm.setBackgroundColor(BaseColor.ORANGE);
               table.addCell(cm);
               for (User u : c2)
        {
             String ens=u.getNom();
             String ensP=u.getPrenom();
             
             PdfPCell jr=new PdfPCell(new Paragraph ("Jour :   "));
               jr.setBackgroundColor(BaseColor.ORANGE);
                table.addCell(jr);
               //table.addCell("Jour :  ");
        
          // table.addCell("Heure Début :  ");
          PdfPCell hd=new PdfPCell(new Paragraph ("Heure Début :   "));
               hd.setBackgroundColor(BaseColor.ORANGE);
                table.addCell(hd);
           // table.addCell("Heure fin :  ");
           PdfPCell hf=new PdfPCell(new Paragraph ("Heure Fin :   "));
               hf.setBackgroundColor(BaseColor.ORANGE);
                table.addCell(hf);
                
           for (Salle sl : c3)
         {
             String salleL=sl.getLibelle();
           //table.addCell("salle :  ");
         
           PdfPCell sc=new PdfPCell(new Paragraph ("Salle :   "));
               sc.setBackgroundColor(BaseColor.ORANGE);
                table.addCell(sc);
            //table.addCell("Enseignant :  ");
               PdfPCell sc1=new PdfPCell(new Paragraph ("Enseignant :   "));
               sc1.setBackgroundColor(BaseColor.ORANGE);
                table.addCell(sc1);
                
           table.addCell(matiere); 
           
            table.addCell(j);
             table.addCell(hdeb);
             table.addCell(hfin);
               table.addCell(salleL);
               table.addCell(ens+"  "+ensP);
               //table.addCell(ensS);
         }
         
         }
        
         }
           
           
           
           //i++;
           document.add(p1);     
            document.add(table);
   
 /*       
 if(    j.equals(c1.phrase.getContent()))
 {
     
     PdfPTable nested = new PdfPTable(3);

      nested.addCell(j);

       nested.addCell(hdeb);

       nested.addCell(hfin);

       PdfPCell nesthousing = new PdfPCell(nested);

       table.addCell(nesthousing);
 }
         */
         //  table.addCell(Integer.parseInt(id));
          // table.addCell(j);
          // table.addCell(table);
           
          // table.addCell("2.3");
           
         
    
 //   
 }
          
          document.add(p);  
         
         
        document.close();
        } 
        
        catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
    }
    
    
    
    
    
     public void OpenPdfEns(int idC) throws DocumentException
    {
             
        try {
            String fileName="C:\\Users\\dell\\Documents\\NetBeansProjects\\EcoleTest_Java\\test.pdf";
              Document document =new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            
        document.open();
        
             ServiceSeance sr=new   ServiceSeance();
              PdfPTable table1 = new PdfPTable(2);
               PdfPCell c1=new PdfPCell(new Paragraph ("Emploi de temps "));
               c1.setColspan(8);
               c1.setHorizontalAlignment(Element.ALIGN_CENTER);
               c1.setBackgroundColor(BaseColor.ORANGE);
               c1.setPadding(10.0f);
               table1.addCell(c1);
          document.add(table1);  
         
         List<Seance> res = sr.SelectSeanceEns(idC);
        
          for (Seance e:res)
    {
         Paragraph p1=new Paragraph("------------------------------------------");
         p1.setAlignment(Element.ALIGN_CENTER);
        int id=e.getId();
        String m=e.getNomMatiere();
        String j=e.getJour();
          String hdeb=e.getHdeb();
         String hfin=e.getHfin();
         String salleId=e.getSalleCours();
         String ensS=e.getNiveau();
                 
            ServiceCoeff sco= new ServiceCoeff();     
          PdfPTable table = new PdfPTable(6);
        
           PdfPCell cm=new PdfPCell(new Paragraph ("Matiere :  "));
               cm.setBackgroundColor(BaseColor.WHITE);
               table.addCell(cm);
               
            
             
             PdfPCell jr=new PdfPCell(new Paragraph ("Jour :   "));
               jr.setBackgroundColor(BaseColor.WHITE);
               table.addCell(jr);
               //table.addCell("Jour :  ");
        
          // table.addCell("Heure Début :  ");
          PdfPCell hd=new PdfPCell(new Paragraph ("Heure Début :   "));
               hd.setBackgroundColor(BaseColor.WHITE);
               table.addCell(hd);
           // table.addCell("Heure fin :  ");
           PdfPCell hf=new PdfPCell(new Paragraph ("Heure Fin :   "));
               hf.setBackgroundColor(BaseColor.WHITE);
          table.addCell(hf);
             
           table.addCell("salle :  ");
         
           
            table.addCell("Classe :  ");
               
           table.addCell(m); 
           
            table.addCell(j);
             table.addCell(hdeb);
             table.addCell(hfin);
               table.addCell(salleId);
               table.addCell(ensS);
       
           
           
           
           //i++;
           document.add(p1);     
            document.add(table);
   
 
 }
        
        document.close();
        } 
        
        catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws DocumentException  {
        
     
   
    }
    
}
