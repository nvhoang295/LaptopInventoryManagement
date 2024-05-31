/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 *
 * @author vieth
 */
public class PdfUtil {
    
    
    private static final String FILE_NAME = "D:\\itext.pdf";
    public static void main(String[] args) {
        writeUsingIText();
    }
    private static void writeUsingIText() {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(new File(FILE_NAME)));
            //open
            document.open();
            Paragraph p = new Paragraph();
            p.add("This is my paragraph 1");
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);
            Paragraph p2 = new Paragraph();
            p2.add("This is my paragraph 2"); //no alignment
            document.add(p2);
            Font f = new Font();
            f.setStyle(Font.BOLD);
            f.setSize(8);
            document.add(new Paragraph("This is my paragraph 3", f));
            
            
            // table
            PdfPTable table = new PdfPTable(3);
            //Khởi tạo 3 ô header
            PdfPCell header1 = new PdfPCell(new Paragraph("Header 1"));
            PdfPCell header2 = new PdfPCell(new Paragraph("Header 2"));
            PdfPCell header3 = new PdfPCell(new Paragraph("Header 3"));
            //Thêm 3 ô header vào table
            table.addCell(header1);
            table.addCell(header2);
            table.addCell(header3);

            //Khởi tạo 3 ô data: ô số 1 là string, ô số 2 là ảnh, ô số 3 là table
            PdfPCell data1 = new PdfPCell(new Paragraph("Data String"));
            PdfPCell data2 = new PdfPCell(new Paragraph("Data String 2"));

            PdfPTable nestedTable = new PdfPTable(2);
            nestedTable.addCell(new Paragraph("Nested Cell 1"));
            nestedTable.addCell(new Paragraph("Nested Cell 2"));
            PdfPCell data3 = new PdfPCell(nestedTable);
            //Thêm data vào bảng.
            table.addCell(data1);
            table.addCell(data2);
            table.addCell(data3);

            document.add(table);
            
            
            //close
            document.close();
            System.out.println("Done");
         
        } catch (FileNotFoundException | DocumentException e) {
            
        }
    }
}
