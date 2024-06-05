package com.haui_megatech.util;

import com.haui_megatech.dto.CommonResponseDTO;
import com.haui_megatech.model.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
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
    private static final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
    private static final DecimalFormat priceFormatter = new DecimalFormat("0,000");
    
    private static final String SELECTED_SHEET_NAME = "Sheet1";
    private static final String EXCEL_FILENAME_EXTENSION = ".xlsx";

    public static ArrayList<User> excelToUsers(File file) {

        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            System.out.println("Không tìm thấy file.");
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

    public static CommonResponseDTO usersToExcel(List<User> users, String savedPath) {
        String savedFilePath = "";
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadsheet = workbook.createSheet("Sheet1");

            XSSFRow row = null;
            Cell cell = null;

            row = spreadsheet.createRow((short) 0);
            row.setHeight((short) 500);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("id");
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("username");
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("first_name");
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("last_name");
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("phone_number");
            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("email");

            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                row = spreadsheet.createRow(i + 1);
                row.setHeight((short) 400);
                row.createCell(0).setCellValue(user.getId());
                row.createCell(1).setCellValue(user.getUsername());
                row.createCell(2).setCellValue(user.getFirstName());
                row.createCell(3).setCellValue(user.getLastName());
                row.createCell(4).setCellValue(user.getPhoneNumber());
                row.createCell(5).setCellValue(user.getEmail());
            }
            try (FileOutputStream out = new FileOutputStream(new File(
                    savedPath.replace("\\", "/") + EXCEL_FILENAME_EXTENSION
            ))) {
                workbook.write(out);
            }
        } catch (IOException e) {
            return CommonResponseDTO
                    .builder()
                    .success(false)
                    .message("Có lỗi trong quá trình ghi file.")
                    .build();
        }

        return CommonResponseDTO
                .builder()
                .success(true)
                .message(String.format("Xuất thành công %d người dùng.", users.size()))
                .build();
    }

    public static ArrayList<Product> excelToProducts(File file) {

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

            ArrayList<Product> products = new ArrayList<>();

            int rowNumber = 0;
            for (Row row : sheet) {
                if (rowNumber == 0) {
                    ++rowNumber;
                    continue;
                }
                Product product = new Product();

                short firstCellNum = row.getFirstCellNum();
                short lastCellNum = row.getLastCellNum();

                for (int cellIndex = firstCellNum; cellIndex < lastCellNum; ++cellIndex) {
                    Cell currentCell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                    switch (cellIndex) {
                        case 0 -> {
                            product.setName(currentCell.getStringCellValue());
                            break;
                        }
                        case 1 -> {
                            product.setProcessor(currentCell.getStringCellValue());
                            break;
                        }
                        case 2 -> {
                            product.setMemory(currentCell.getStringCellValue());
                            break;
                        }
                        case 3 -> {
                            product.setStorage(currentCell.getStringCellValue());
                            break;
                        }
                        case 4 -> {
                            product.setDisplay(currentCell.getStringCellValue());
                            break;
                        }
                        case 5 -> {
                            product.setBattery(currentCell.getStringCellValue());
                            break;
                        }
                        case 6 -> {
                            product.setCard(currentCell.getStringCellValue());
                            break;
                        }
                        case 7 -> {
                            product.setWeight(currentCell.getStringCellValue());
                            break;
                        }
                        default -> {
                            break;
                        }
                    }
                }
                products.add(product);
            }
            workbook.close();
            return products;
        } catch (IOException e) {
            throw new RuntimeException("Fail to parse Excel file: " + e.getMessage());
        }
    }

    public static CommonResponseDTO productsToExcel(List<Product> products, String savedPath) {
        String savedFilePath = "";
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadsheet = workbook.createSheet("Sheet1");

            XSSFRow row = null;
            Cell cell = null;

            row = spreadsheet.createRow((short) 0);
            row.setHeight((short) 500);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("id");
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("name");
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("memory");
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("storage");
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("display");
            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("battery");
            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue("graphic_card");
            cell = row.createCell(7, CellType.STRING);
            cell.setCellValue("weight");

            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                row = spreadsheet.createRow(i + 1);
                row.setHeight((short) 400);
                row.createCell(0).setCellValue(product.getId());
                row.createCell(1).setCellValue(product.getName());
                row.createCell(2).setCellValue(product.getMemory());
                row.createCell(3).setCellValue(product.getStorage());
                row.createCell(4).setCellValue(product.getDisplay());
                row.createCell(5).setCellValue(product.getBattery());
                row.createCell(6).setCellValue(product.getCard());
                row.createCell(7).setCellValue(product.getWeight());
            }
            try (FileOutputStream out = new FileOutputStream(new File(
                    savedPath.replace("\\", "/") + EXCEL_FILENAME_EXTENSION
            ))) {
                workbook.write(out);
            }
        } catch (IOException e) {
            return CommonResponseDTO
                    .builder()
                    .success(false)
                    .message("Có lỗi trong quá trình ghi file.")
                    .build();
        }

        return CommonResponseDTO
                .builder()
                .success(true)
                .message(String.format("Xuất thành công %d sản phẩm.", products.size()))
                .build();
    }

    public static ArrayList<Provider> excelToProviders(File file) {

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

            ArrayList<Provider> providers = new ArrayList<>();

            int rowNumber = 0;
            for (Row row : sheet) {
                if (rowNumber == 0) {
                    ++rowNumber;
                    continue;
                }
                Provider provider = new Provider();

                short firstCellNum = row.getFirstCellNum();
                short lastCellNum = row.getLastCellNum();

                for (int cellIndex = firstCellNum; cellIndex < lastCellNum; ++cellIndex) {
                    Cell currentCell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                    switch (cellIndex) {
                        case 0 -> {
                            provider.setName(currentCell.getStringCellValue());
                            break;
                        }
                        case 1 -> {
                            provider.setPhoneNumber(currentCell.getStringCellValue());
                            break;
                        }
                        case 2 -> {
                            provider.setEmail(currentCell.getStringCellValue());
                            break;
                        }
                        case 3 -> {
                            provider.setAddress(currentCell.getStringCellValue());
                            break;
                        }
                        default -> {
                            break;
                        }
                    }
                }
                providers.add(provider);
            }
            workbook.close();
            return providers;
        } catch (IOException e) {
            throw new RuntimeException("Fail to parse Excel file: " + e.getMessage());
        }
    }
    
    public static CommonResponseDTO providersToExcel(List<Provider> providers, String savedPath) {
        String savedFilePath = "";
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadsheet = workbook.createSheet("Sheet1");

            XSSFRow row = null;
            Cell cell = null;

            row = spreadsheet.createRow((short) 0);
            row.setHeight((short) 500);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("id");
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("name");
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("phone_number");
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("email");
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("address");

            for (int i = 0; i < providers.size(); i++) {
                Provider provider = providers.get(i);
                row = spreadsheet.createRow(i + 1);
                row.setHeight((short) 400);
                row.createCell(0).setCellValue(provider.getId());
                row.createCell(1).setCellValue(provider.getName());
                row.createCell(2).setCellValue(provider.getPhoneNumber());
                row.createCell(3).setCellValue(provider.getEmail());
                row.createCell(4).setCellValue(provider.getAddress());
            }
            try (FileOutputStream out = new FileOutputStream(new File(
                    savedPath.replace("\\", "/") + EXCEL_FILENAME_EXTENSION
            ))) {
                workbook.write(out);
            }
        } catch (IOException e) {
            return CommonResponseDTO
                    .builder()
                    .success(false)
                    .message("Có lỗi trong quá trình ghi file.")
                    .build();
        }

        return CommonResponseDTO
                .builder()
                .success(true)
                .message(String.format("Xuất thành công %d nhà cung cấp.", providers.size()))
                .build();
    }
    
    public static CommonResponseDTO exportBillsToExcel(List<ExportBill> items, String savedPath) {
        String savedFilePath = "";
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadsheet = workbook.createSheet("Sheet1");

            XSSFRow row = null;
            Cell cell = null;

            row = spreadsheet.createRow((short) 0);
            row.setHeight((short) 500);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("id");
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("client_name");
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("client_phone_number");
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("client_address");
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("total");
            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("when_created");

            for (int i = 0; i < items.size(); i++) {
                ExportBill item = items.get(i);
                row = spreadsheet.createRow(i + 1);
                row.setHeight((short) 400);
                row.createCell(0).setCellValue(item.getId());
                row.createCell(1).setCellValue(item.getClientName());
                row.createCell(2).setCellValue(item.getClientPhoneNumber());
                row.createCell(3).setCellValue(item.getClientAddress());
                row.createCell(4).setCellValue(priceFormatter.format(item.getTotal()) + "đ");
                row.createCell(5).setCellValue(formatter.format(item.getWhenCreated()));
            }
            try (FileOutputStream out = new FileOutputStream(new File(
                    savedPath.replace("\\", "/") + EXCEL_FILENAME_EXTENSION
            ))) {
                workbook.write(out);
            }
        } catch (IOException e) {
            return CommonResponseDTO
                    .builder()
                    .success(false)
                    .message("Có lỗi trong quá trình ghi file.")
                    .build();
        }

        return CommonResponseDTO
                .builder()
                .success(true)
                .message(String.format("Xuất thành công %d phiếu xuất.", items.size()))
                .build();
    }
    
    public static CommonResponseDTO importBillsToExcel(List<ImportBill> items, String savedPath) {
        String savedFilePath = "";
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadsheet = workbook.createSheet("Sheet1");

            XSSFRow row = null;
            Cell cell = null;

            row = spreadsheet.createRow((short) 0);
            row.setHeight((short) 500);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("id");
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("provider_name");
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("receiver");
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("when_created");
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("total");

            for (int i = 0; i < items.size(); i++) {
                ImportBill item = items.get(i);
                row = spreadsheet.createRow(i + 1);
                row.setHeight((short) 400);
                row.createCell(0).setCellValue(item.getId());
                row.createCell(1).setCellValue(item.getProvider().getName());
                row.createCell(2).setCellValue(item.getUser().getUsername());
                row.createCell(3).setCellValue(formatter.format(item.getWhenCreated()));
                row.createCell(4).setCellValue(priceFormatter.format(item.getTotal()) + "đ");
            }
            try (FileOutputStream out = new FileOutputStream(new File(
                    savedPath.replace("\\", "/") + EXCEL_FILENAME_EXTENSION
            ))) {
                workbook.write(out);
            }
        } catch (IOException e) {
            return CommonResponseDTO
                    .builder()
                    .success(false)
                    .message("Có lỗi trong quá trình ghi file.")
                    .build();
        }

        return CommonResponseDTO
                .builder()
                .success(true)
                .message(String.format("Xuất thành công %d phiếu nhập.", items.size()))
                .build();
    }
    
    public static CommonResponseDTO inventoryItemsToExcel(List<InventoryItem> items, String savedPath) {
        String savedFilePath = "";
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadsheet = workbook.createSheet("Sheet1");

            XSSFRow row = null;
            Cell cell = null;

            row = spreadsheet.createRow((short) 0);
            row.setHeight((short) 500);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("stt");
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("product_id");
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("product_name");
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("remaining");
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("import_price");
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("imported_at");

            for (int i = 0; i < items.size(); i++) {
                InventoryItem item = items.get(i);
                row = spreadsheet.createRow(i + 1);
                row.setHeight((short) 400);
                row.createCell(0).setCellValue(item.getId());
                row.createCell(1).setCellValue(item.getImportBillItem().getProduct().getId());
                row.createCell(2).setCellValue(item.getImportBillItem().getProduct().getName());
                row.createCell(3).setCellValue(item.getQuantity());
                row.createCell(4).setCellValue(priceFormatter.format(item.getImportPrice()) + "đ");
                row.createCell(5).setCellValue(formatter.format(item.getImportBillItem().getImportBill().getWhenCreated()));
            }
            try (FileOutputStream out = new FileOutputStream(new File(
                    savedPath.replace("\\", "/") + EXCEL_FILENAME_EXTENSION
            ))) {
                workbook.write(out);
            }
        } catch (IOException e) {
            return CommonResponseDTO
                    .builder()
                    .success(false)
                    .message("Có lỗi trong quá trình ghi file.")
                    .build();
        }

        return CommonResponseDTO
                .builder()
                .success(true)
                .message(String.format("Xuất thành công %d mặt hàng tồn kho.", items.size()))
                .build();
    }

}
