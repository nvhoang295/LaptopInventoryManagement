package com.haui_megatech.util;

import com.haui_megatech.model.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author vieth
 */
public class ExcelUtil {

    private static final String SELECTED_SHEET_NAME = "Sheet1";

    public static ArrayList<User> excelToUsers(File file)  {
        
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExcelUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SELECTED_SHEET_NAME);
            Iterator<Row> rows = sheet.iterator();
            
            ArrayList<User> users = new ArrayList<>();
            
            int rowNumber = 0;
            for (Row row : sheet) {
                if (rowNumber == 0) {
                    ++rowNumber;
                    continue;
                }
                User user = new User();
                
                short firstCellNum = row.getFirstCellNum();
                short lastCellNum = row.getLastCellNum();
                
                for (int cellIndex = firstCellNum; cellIndex < lastCellNum; ++cellIndex) {
                    Cell currentCell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    
                    switch (cellIndex) {
                        case 0 -> {
                            user.setUsername(currentCell.getStringCellValue());
                            break;
                        }
                        case 1 -> {
                            user.setPassword(currentCell.getStringCellValue());
                            break;
                        }
                        case 2 -> {
                            user.setFirstName(currentCell.getStringCellValue());
                            break;
                        }
                        case 3 -> {
                            user.setLastName(currentCell.getStringCellValue());
                            break;
                        }
                        case 4 -> {
                            user.setPhoneNumber(currentCell.getStringCellValue());
                            break;
                        }
                        case 5 -> {
                            user.setEmail(currentCell.getStringCellValue());
                            break;
                        }
                        default -> {
                            break;
                        }
                    }
                }
                users.add(user);
            }
            workbook.close();
            return users;
        } catch (IOException e) {
            throw new RuntimeException("Fail to parse Excel file: " + e.getMessage());
        }
    }
}
