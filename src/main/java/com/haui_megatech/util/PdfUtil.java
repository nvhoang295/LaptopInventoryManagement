/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.util;

import com.haui_megatech.model.ExportBill;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author vieth
 */
public class PdfUtil {
    
    private static final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
    private static final DecimalFormat priceFormatter = new DecimalFormat("0,000");
    
    private static final String PDF_FILENAME_EXTENSION = ".pdf";
    
    public static boolean exportBillToPdf(ExportBill item, String savedFilePath) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(new File(savedFilePath.replace("\\", "/") + PDF_FILENAME_EXTENSION)));
            document.open();

            
            Font headingFont = new Font();
            headingFont.setStyle(Font.BOLD);
            headingFont.setSize(24);
            
            
            Font simpleFont = new Font();
            simpleFont.setStyle(Font.NORMAL);
            simpleFont.setSize(14);
            
            
            Paragraph headingParagraph = new Paragraph("PHIEU XUAT HANG");
            headingParagraph.setAlignment(Element.ALIGN_CENTER);
            headingParagraph.setFont(headingFont);
            
            Paragraph exportBillId = new Paragraph("Ma phieu xuat: " + item.getId());
            Paragraph creator = new Paragraph("Nguoi lap: " + item.getUser().getUsername());
            Paragraph whenCreated = new Paragraph("Thoi gian tao: " + formatter.format(item.getWhenCreated()));
            Paragraph clientName = new Paragraph("Ten khach hang: " + item.getClientName());
            Paragraph clientPhoneNumber = new Paragraph("So dien thoai: " + item.getClientPhoneNumber());
            Paragraph clientAddress = new Paragraph("Dia chi: " + item.getClientAddress());
            exportBillId.setFont(simpleFont);
            creator.setFont(simpleFont);
            whenCreated.setFont(simpleFont);
            clientName.setFont(simpleFont);  
            clientPhoneNumber.setFont(simpleFont);
            clientAddress.setFont(simpleFont);
            clientAddress.setSpacingAfter(30);
            
            final int PROPS_COUNT = 6;
            PdfPTable table = new PdfPTable(PROPS_COUNT);
            PdfPCell indexHeader = new PdfPCell(new Paragraph("STT"));
            PdfPCell productIdHeader = new PdfPCell(new Paragraph("Ma san pham"));
            PdfPCell productNameHeader = new PdfPCell(new Paragraph("Ten san pham"));
            PdfPCell quantityHeader = new PdfPCell(new Paragraph("So luong"));
            PdfPCell exportPriceHeader = new PdfPCell(new Paragraph("Don gia"));
            PdfPCell totalHeader = new PdfPCell(new Paragraph("Thanh tien"));
            
            table.addCell(indexHeader);
            table.addCell(productIdHeader);
            table.addCell(productNameHeader);
            table.addCell(quantityHeader);
            table.addCell(exportPriceHeader);
            table.addCell(totalHeader);

            for (int i = 0; i < item.getExportBillItems().size(); ++i) {
                PdfPCell indexData = new PdfPCell(new Paragraph((i + 1) + ""));
                PdfPCell productIdData = new PdfPCell(new Paragraph(item.getExportBillItems().get(i).getProduct().getId().toString()));
                PdfPCell productNameData = new PdfPCell(new Paragraph(item.getExportBillItems().get(i).getProduct().getName()));
                PdfPCell quantityData = new PdfPCell(new Paragraph(item.getExportBillItems().get(i).getQuantity().toString()));
                PdfPCell exportPriceData = new PdfPCell(new Paragraph(priceFormatter.format(
                        item.getExportBillItems().get(i).getExportPrice())));
                PdfPCell totalData = new PdfPCell(new Paragraph(
                        priceFormatter.format(
                                item.getExportBillItems().get(i).getExportPrice() 
                                        * item.getExportBillItems().get(i).getQuantity()
                )));
                
                table.addCell(indexData);
                table.addCell(productIdData);
                table.addCell(productNameData);
                table.addCell(quantityData);
                table.addCell(exportPriceData);
                table.addCell(totalData);
            }
            
            document.add(headingParagraph);
            
            document.add(exportBillId);
            document.add(creator);
            document.add(whenCreated);
            document.add(clientName);
            document.add(clientPhoneNumber);
            document.add(clientAddress);
            
            document.add(table);
            
            document.close();
        } catch (FileNotFoundException | DocumentException e) {
            return false;
        } finally {
            
        }
        return true;
    }
    
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
