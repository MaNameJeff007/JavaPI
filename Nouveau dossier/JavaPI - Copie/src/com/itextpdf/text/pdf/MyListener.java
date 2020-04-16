/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itextpdf.text.pdf;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author dell
 */
public class MyListener {
    
    public void addActionListener()
    {
      if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File("C:\\Users\\dell\\Desktop\\test.pdf");
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
               System.err.println(ex);
            }
        }   
    }
   
}
