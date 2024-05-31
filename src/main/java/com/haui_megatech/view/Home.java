/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.haui_megatech.view;

import com.haui_megatech.ApplicationContext;

import com.haui_megatech.constant.*;
import com.haui_megatech.controller.*;
import com.haui_megatech.dto.*;
import com.haui_megatech.model.*;
import com.haui_megatech.repository.*;
import com.haui_megatech.repository.impl.*;
import com.haui_megatech.service.ExportBillItemService;
import com.haui_megatech.service.ExportBillService;
import com.haui_megatech.service.ImportBillItemService;
import com.haui_megatech.service.ImportBillService;
import com.haui_megatech.service.InventoryItemService;
import com.haui_megatech.service.ProductService;
import com.haui_megatech.service.ProviderService;
import com.haui_megatech.service.UserService;
import com.haui_megatech.service.impl.*;
import com.haui_megatech.util.*;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author vieth
 */
public class Home extends javax.swing.JFrame {

    private final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
    private final DecimalFormat priceFormatter = new DecimalFormat("0,000");
    private final Font tableHeaderFont = new Font("Segoe UI", Font.BOLD, 14);

    private final int ID_COL_INDEX = 0;

    private final int USERNAME_COL_INDEX = 1;
    private final int FIRSTNAME_COL_INDEX = 2;
    private final int LASTNAME_COL_INDEX = 3;
    private final int PHONENUMBER_COL_INDEX = 4;
    private final int EMAIL_COL_INDEX = 5;

    private final int PRODUCT_NAME_COL_INDEX = 1;
    private final int PRODUCT_PROCESSOR_COL_INDEX = 2;
    private final int PRODUCT_MEMORY_COL_INDEX = 3;
    private final int PRODUCT_STORAGE_COL_INDEX = 4;
    private final int PRODUCT_DISPLAY_COL_INDEX = 5;
    private final int PRODUCT_BATTERY_COL_INDEX = 6;
    private final int PRODUCT_CARD_COL_INDEX = 7;

    private final int IMPORT_BILL_ITEM_PRODUCT_ID_COL_INDEX = 1;
    private final int IMPORT_BILL_ITEM_PRODUCT_NAME_COL_INDEX = 2;
    private final int IMPORT_BILL_ITEM_QUANTITY_COL_INDEX = 3;
    private final int IMPORT_BILL_ITEM_PRICE_COL_INDEX = 4;

    private final int EXPORT_INVENTORY_ITEM_PRODUCT_ID_COL_INDEX = 1;
    private final int EXPORT_INVENTORY_ITEM_PRODUCT_NAME_COL_INDEX = 2;
    private final int EXPORT_INVENTORY_ITEM_QUANTITY_COL_INDEX = 3;
    private final int EXPORT_INVENTORY_ITEM_PRICE_COL_INDEX = 4;

    private final int EXPORT_BILL_ITEM_INVENTORY_ID = 1;
    private final int EXPORT_BILL_ITEM_PRODUCT_ID = 2;
    private final int EXPORT_BILL_ITEM_PRODUCT_NAME = 3;
    private final int EXPORT_BILL_ITEM_QUANTITY = 4;
    private final int EXPORT_BILL_ITEM_PRICE = 5;

    private ImportBill importBill;
    private ExportBill exportBill;

    private final ApplicationContext applicationContext = new ApplicationContext();

    private final UserRepository userRepository = new UserRepositoryImpl(applicationContext);
    private final UserService userService = new UserServiceImpl(userRepository);
    private final UserController userController = new UserController(userService);

    private final ProductRepository productRepository = new ProductRepositoryImpl(applicationContext);
    private final ProductService productService = new ProductServiceImpl(productRepository);
    private final ProductController productController = new ProductController(productService);

    private final ProviderRepository providerRepository = new ProviderRepositoryImpl(applicationContext);
    private final ProviderService providerService = new ProviderServiceImpl(providerRepository);
    private final ProviderController providerController = new ProviderController(providerService);

    private final ImportBillItemRepository importBillItemRepository = new ImportBillItemRepositoryImpl(applicationContext);
    private final ImportBillItemService importBillItemService = new ImportBillItemServiceImpl(importBillItemRepository);
    private final ImportBillItemController importBillItemController = new ImportBillItemController(importBillItemService);

    private final InventoryItemRepository inventoryItemRepository = new InventoryItemRepositoryImpl(applicationContext);
    private final InventoryItemService inventoryItemService = new InventoryItemServiceImpl(inventoryItemRepository);
    private final InventoryItemController inventoryItemController = new InventoryItemController(inventoryItemService);

    private final ImportBillRepository importBillRepository = new ImportBillRepositoryImpl(applicationContext);
    private final ImportBillService importBillService = new ImportBillServiceImpl(
            importBillRepository,
            importBillItemRepository,
            providerRepository,
            inventoryItemRepository
    );
    private final ImportBillController importBillController = new ImportBillController(importBillService);
    
    private final ExportBillItemRepository exportBillItemRepository = new ExportBillItemRepositoryImpl(applicationContext);
    private final ExportBillItemService exportBillItemService = new ExportBillItemServiceImpl(exportBillItemRepository);
    
    private final ExportBillRepository exportBillRepository = new ExportBillRepositoryImpl(applicationContext);
    private final ExportBillService exportBillService = new ExportBillServiceImpl(exportBillRepository, exportBillItemRepository);
    private final ExportBillController exportBillController = new ExportBillController(exportBillService);
    
    private List<InventoryItem> inventoryItems;

    /**
     * Creates new form Home
     */
    public Home() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setActiveTab("product");
        this.setDisplayedPanel("product");
        loadDataToTableProducts(null);
        this.setBackground(Color.WHITE);
        this.loginedUsername.setText(ApplicationContext.getLoginedUser().getUsername());

        importProductBillCreatorLabel.setText(ApplicationContext.getLoginedUser().getUsername());
        exportProductBillCreatorLabel.setText(ApplicationContext.getLoginedUser().getUsername());

        initImportBill();
        initExportBill();

        usersTable.getTableHeader().setFont(tableHeaderFont);
        productsTable.getTableHeader().setFont(tableHeaderFont);
        providersTable.getTableHeader().setFont(tableHeaderFont);
        importProductsTable.getTableHeader().setFont(tableHeaderFont);
        importProductBillTable.getTableHeader().setFont(tableHeaderFont);
        importBillsTable.getTableHeader().setFont(tableHeaderFont);
        viewImportBillDetailItemsTable.getTableHeader().setFont(tableHeaderFont);
        exportInStockProductsTable.getTableHeader().setFont(tableHeaderFont);
        exportProductsBillTable.getTableHeader().setFont(tableHeaderFont);
        exportBillsTable.getTableHeader().setFont(tableHeaderFont);
        inStocksTable.getTableHeader().setFont(tableHeaderFont);

        loadDataToProvidersNameCombobox();

        inventoryItems = inventoryItemController.getList().data();
    }

    private void initImportBill() {
        importBill = new ImportBill();
        importBill.setImportBillItems(new ArrayList<>());
        importBill.setUser(ApplicationContext.getLoginedUser());
    }

    private void initExportBill() {
        exportBill = new ExportBill();
        exportBill.setExportBillItems(new ArrayList<>());
        exportBill.setUser(ApplicationContext.getLoginedUser());
    }

    private void loadDataToTableUsers(String keyword) {
        String[] tableHeader = {
            "ID",
            "Tên đăng nhập",
            "Tên",
            "Họ đệm",
            "Số điện thoại",
            "Email"
        };
        DefaultTableModel tableModel = new DefaultTableModel(null, tableHeader) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        List<User> users = keyword != null && !keyword.isEmpty() && !keyword.isBlank()
                ? userController.searchList(keyword).data()
                : userController.getList().data();

        users.forEach(item -> {
            tableModel.addRow(
                    new Object[]{
                        item.getId() != null ? item.getId() : "",
                        item.getUsername() != null ? item.getUsername() : "",
                        item.getFirstName() != null ? item.getFirstName() : "",
                        item.getLastName() != null ? item.getLastName() : "",
                        item.getPhoneNumber() != null ? item.getPhoneNumber() : "",
                        item.getEmail() != null ? item.getEmail() : "",}
            );
        });
        usersTable.setModel(tableModel);
    }

    private void loadDataToTableProducts(String keyword) {
        String[] tableHeader = {
            "ID",
            "Tên sản phẩm",
            "CPU",
            "RAM",
            "Bộ nhớ trong",
            "Màn hình",
            "Dung lượng pin",
            "Card màn hình",};

        DefaultTableModel tableModel = new DefaultTableModel(null, tableHeader) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<Product> products = keyword != null && !keyword.isEmpty() && !keyword.isBlank()
                ? productController.searchList(keyword).data()
                : productController.getList().data();

        products.forEach(item -> {
            tableModel.addRow(
                    new Object[]{
                        item.getId() != null ? item.getId() : "",
                        item.getName() != null ? item.getName() : "",
                        item.getProcessor() != null ? item.getProcessor() : "",
                        item.getMemory() != null ? item.getMemory() : "",
                        item.getStorage() != null ? item.getStorage() : "",
                        item.getDisplay() != null ? item.getDisplay() : "",
                        item.getBattery() != null ? item.getBattery() : "",
                        item.getCard() != null ? item.getCard() : "",}
            );
        });
        productsTable.setModel(tableModel);
    }

    private void loadDataToTableProviders(String keyword) {
        String[] tableHeader = {
            "ID",
            "Tên nhà cung cấp",
            "Số điện thoại",
            "Email",
            "Địa chỉ",
            "Số đơn đã nhập"
        };

        DefaultTableModel tableModel = new DefaultTableModel(null, tableHeader) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<Provider> providers = keyword != null && !keyword.isEmpty() && !keyword.isBlank()
                ? providerController.searchList(keyword).data()
                : providerController.getList().data();

        providers.forEach(item -> {
            tableModel.addRow(
                    new Object[]{
                        item.getId() != null ? item.getId() : "",
                        item.getName() != null ? item.getName() : "",
                        item.getPhoneNumber() != null ? item.getPhoneNumber() : "",
                        item.getEmail() != null ? item.getEmail() : "",
                        item.getAddress() != null ? item.getAddress() : "",
                        item.getImportBills().size()
                    }
            );
        });
        providersTable.setModel(tableModel);
    }

    private void loadDataToTableImportProducts(String keyword) {
        String[] tableHeader = {
            "ID",
            "Tên sản phẩm",};

        DefaultTableModel tableModel = new DefaultTableModel(null, tableHeader) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<Product> products = keyword != null && !keyword.isEmpty() && !keyword.isBlank()
                ? productController.searchList(keyword).data()
                : productController.getList().data();

        products.forEach(item -> {
            tableModel.addRow(
                    new Object[]{
                        item.getId() != null ? item.getId() : "",
                        item.getName() != null ? item.getName() : ""
                    }
            );
        });
        importProductsTable.setModel(tableModel);
    }

    private void loadDataToProvidersNameCombobox() {
        List<Provider> providers = providerController.getList().data();
        providerNameComboBox.addItem("------- Chọn -------");
        providers.forEach(item -> {
            providerNameComboBox.addItem(item.getName());
        });
    }

    private void loadDataToImportProductBillTable(ImportBill importBill) {
        String[] tableHeader = {
            "STT",
            "Mã sản phẩm",
            "Tên sản phẩm",
            "Số lượng",
            "Giá nhập",
            "Thành tiền"
        };

        DefaultTableModel tableModel = new DefaultTableModel(null, tableHeader) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (int i = 1; i <= importBill.getImportBillItems().size(); ++i) {
            tableModel.addRow(
                    new Object[]{
                        i,
                        importBill.getImportBillItems().get(i - 1).getProduct().getId(),
                        importBill.getImportBillItems().get(i - 1).getProduct().getName(),
                        importBill.getImportBillItems().get(i - 1).getQuantity(),
                        priceFormatter.format(importBill.getImportBillItems().get(i - 1).getImportPrice()) + "đ",
                        priceFormatter.format(importBill.getImportBillItems().get(i - 1).getQuantity()
                                * importBill.getImportBillItems().get(i - 1).getImportPrice()) + "đ"
                    }
            );
        }
        importProductBillTable.setModel(tableModel);

        totalImportPriceLabel.setText(priceFormatter.format(
                importBill.getImportBillItems()
                        .stream()
                        .mapToDouble(item -> item.getImportPrice() * item.getQuantity())
                        .sum()
        ) + "đ");
    }

    private void loadDataToTableImportBills(String keyword) {
        String[] tableHeader = {
            "Mã phiếu nhập",
            "Tên nhà cung cấp",
            "Người nhận",
            "Ngày tạo",
            "Tổng tiền"
        };

        DefaultTableModel tableModel = new DefaultTableModel(null, tableHeader) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<ImportBill> products = keyword != null && !keyword.isEmpty() && !keyword.isBlank()
                ? importBillController.searchList(keyword).data()
                : importBillController.getList().data();

        products.forEach(item -> {
            tableModel.addRow(
                    new Object[]{
                        item.getId(),
                        item.getProvider().getName(),
                        item.getUser().getUsername(),
                        formatter.format(item.getWhenCreated()),
                        priceFormatter.format(item.getTotal())
                    }
            );
        });
        importBillsTable.setModel(tableModel);
    }

    private void loadDataToViewImportBillDetailItems(ImportBill importBill) {
        String[] tableHeader = {
            "STT",
            "Mã sản phẩm",
            "Tên sản phẩm",
            "Số lượng",
            "Đơn giá",
            "Thành tiền"
        };

        DefaultTableModel tableModel = new DefaultTableModel(null, tableHeader) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (int i = 1; i <= importBill.getImportBillItems().size(); ++i) {
            tableModel.addRow(
                    new Object[]{
                        i,
                        importBill.getImportBillItems().get(i - 1).getProduct().getId(),
                        importBill.getImportBillItems().get(i - 1).getProduct().getName(),
                        importBill.getImportBillItems().get(i - 1).getQuantity(),
                        priceFormatter.format(importBill.getImportBillItems().get(i - 1).getImportPrice()) + "đ",
                        priceFormatter.format(importBill.getImportBillItems().get(i - 1).getQuantity()
                                * importBill.getImportBillItems().get(i - 1).getImportPrice()) + "đ"
                    }
            );
        }

        viewImportBillDetailItemsTable.setModel(tableModel);
    }

    private void loadDataToInStocksTable(String keyword) {
        String[] tableHeader = {
            "STT",
            "Mã sản phẩm",
            "Tên sản phẩm",
            "Trong kho",
            "Giá nhập",
            "Ngày nhập"
        };

        DefaultTableModel tableModel = new DefaultTableModel(null, tableHeader) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        inventoryItems.forEach(item -> {
            tableModel.addRow(
                    new Object[]{
                        item.getId(),
                        item.getImportBillItem().getProduct().getId(),
                        item.getImportBillItem().getProduct().getName(),
                        item.getQuantity(),
                        priceFormatter.format(item.getImportPrice()),
                        formatter.format(item.getImportBillItem().getImportBill().getWhenCreated())
                    }
            );
        });
        inStocksTable.setModel(tableModel);
    }

    private void loadDataToExportInStockProductsTable(List<InventoryItem> items) {
        String[] tableHeader = {
            "ID",
            "Mã sản phẩm",
            "Tên sản phẩm",
            "Trong kho",
            "Giá nhập"
        };

        DefaultTableModel tableModel = new DefaultTableModel(null, tableHeader) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        items.forEach(item -> {
            tableModel.addRow(
                    new Object[]{
                        item.getId(),
                        item.getImportBillItem().getProduct().getId(),
                        item.getImportBillItem().getProduct().getName(),
                        item.getQuantity(),
                        priceFormatter.format(item.getImportPrice()),}
            );
        });
        exportInStockProductsTable.setModel(tableModel);
    }

    private void loadDataToExportProductsBillTable(ExportBill exportBill) {
        String[] tableHeader = {
            "STT",
            "Mã kho",
            "Mã sản phẩm",
            "Tên sản phẩm",
            "Số lượng",
            "Giá xuất",
            "Thành tiền"
        };

        DefaultTableModel tableModel = new DefaultTableModel(null, tableHeader) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (int i = 1; i <= exportBill.getExportBillItems().size(); ++i) {
            tableModel.addRow(
                    new Object[]{
                        i,
                        exportBill.getExportBillItems().get(i - 1).getInventoryItem().getId(),
                        exportBill.getExportBillItems().get(i - 1).getProduct().getId(),
                        exportBill.getExportBillItems().get(i - 1).getProduct().getName(),
                        exportBill.getExportBillItems().get(i - 1).getQuantity(),
                        priceFormatter.format(exportBill.getExportBillItems().get(i - 1).getExportPrice()) + "đ",
                        priceFormatter.format(exportBill.getExportBillItems().get(i - 1).getQuantity()
                                * exportBill.getExportBillItems().get(i - 1).getExportPrice()) + "đ"
                    }
            );
        }
        exportProductsBillTable.setModel(tableModel);

        totalExportPriceLabel.setText(priceFormatter.format(
                exportBill.getExportBillItems()
                        .parallelStream()
                        .mapToDouble(item -> item.getExportPrice() * item.getQuantity())
                        .sum()
        ) + "đ");
    }
    
    private void loadDataToExportBillsTable(String keyword) {
        String[] tableHeader = {
            "ID",
            "Tên khách hàng",
            "Số điện thoại",
            "Địa chỉ",
            "Tổng tiền",
            "Ngày lập"
        };

        DefaultTableModel tableModel = new DefaultTableModel(null, tableHeader) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<ExportBill> items = keyword != null && !keyword.isEmpty() && !keyword.isBlank()
                ? exportBillController.searchList(keyword).data()
                : exportBillController.getList().data();

        items.forEach(item -> {
            tableModel.addRow(
                    new Object[]{
                        item.getId(),
                        item.getClientName(),
                        item.getClientPhoneNumber(),
                        item.getClientAddress(),
                        priceFormatter.format(item.getTotal()) + "đ",
                        formatter.format(item.getWhenCreated())
                    }
            );
        });
        exportBillsTable.setModel(tableModel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        logoutDiaglog = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        addUserDiaglog = new javax.swing.JDialog();
        addUserDiaglogPanel = new javax.swing.JPanel();
        usernameLabel = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        confirmPasswordLabel = new javax.swing.JLabel();
        confirmPasswordField = new javax.swing.JPasswordField();
        firstNameLabel = new javax.swing.JLabel();
        firstNameTextField = new javax.swing.JTextField();
        lastNameLabel = new javax.swing.JLabel();
        lastNameTextField = new javax.swing.JTextField();
        phoneNumberLabel = new javax.swing.JLabel();
        phoneNumberTextField = new javax.swing.JTextField();
        emailLabel = new javax.swing.JLabel();
        emailTextField = new javax.swing.JTextField();
        confirmEmailOTPLabel = new javax.swing.JLabel();
        confirmEmailOTPTextField = new javax.swing.JTextField();
        addUserDiaglogButton = new javax.swing.JButton();
        cancelAddUserDiaglogButton = new javax.swing.JButton();
        sendCodeAddUserDiaglogButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        diaglogMessage = new javax.swing.JDialog();
        diaglogMessagePanel = new javax.swing.JPanel();
        diaglogMessageLabel = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        diaglogMessageOkButton = new javax.swing.JButton();
        deleteUserConfirmDiaglog = new javax.swing.JDialog();
        deleteUserConfirmDiaglogPanel = new javax.swing.JPanel();
        deleteUserConfirmDiaglogLabel = new javax.swing.JLabel();
        confirmDeleteUser = new javax.swing.JButton();
        cancelDeleteUser = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        editUserDiaglog = new javax.swing.JDialog();
        editUserDiaglogPanel = new javax.swing.JPanel();
        editUsernameLabel = new javax.swing.JLabel();
        editUsernameTextField = new javax.swing.JTextField();
        editFirstNameLabel = new javax.swing.JLabel();
        editFirstNameTextField = new javax.swing.JTextField();
        editLastNameLabel = new javax.swing.JLabel();
        editLastNameTextField = new javax.swing.JTextField();
        editPhoneNumberLabel = new javax.swing.JLabel();
        editPhoneNumberTextField = new javax.swing.JTextField();
        editEmailLabel = new javax.swing.JLabel();
        editEmailTextField = new javax.swing.JTextField();
        editUserDiaglogButton = new javax.swing.JButton();
        cancelEditUserDiaglogButton = new javax.swing.JButton();
        editUserDiaglogLabel = new javax.swing.JLabel();
        editUserIdLabel = new javax.swing.JLabel();
        editUserIdTextField = new javax.swing.JTextField();
        viewUserDiaglog = new javax.swing.JDialog();
        viewUserDiaglogPanel = new javax.swing.JPanel();
        viewUsernameLabel = new javax.swing.JLabel();
        viewUserLastNameTextField = new javax.swing.JTextField();
        viewUserFirstNameLabel = new javax.swing.JLabel();
        viewUserLastNameLabel = new javax.swing.JLabel();
        viewUserPhoneNumberLabel = new javax.swing.JLabel();
        viewUserPhoneNumberTextField = new javax.swing.JTextField();
        viewUserEmailLabel = new javax.swing.JLabel();
        viewUserEmailTextField = new javax.swing.JTextField();
        viewUserWhenCreatedLabel = new javax.swing.JLabel();
        viewUserWhenCreatedTextField = new javax.swing.JTextField();
        viewUserLastUpdatedLabel = new javax.swing.JLabel();
        viewUserLastUpdatedTextField = new javax.swing.JTextField();
        cancelAddUserDiaglogButton1 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        editUserDiaglogButton1 = new javax.swing.JButton();
        viewUserLastLoggedInLabel = new javax.swing.JLabel();
        viewUserLastLoggedInTextField = new javax.swing.JTextField();
        viewUserLoggedInLabel = new javax.swing.JLabel();
        viewUserLoggedInTextField = new javax.swing.JTextField();
        viewUsernameTextField = new javax.swing.JTextField();
        viewUserFirstNameTextField = new javax.swing.JTextField();
        deleteProductConfirmDiaglog = new javax.swing.JDialog();
        deleteProductConfirmDiaglogPanel = new javax.swing.JPanel();
        deleteProductConfirmDiaglogLabel = new javax.swing.JLabel();
        confirmDeleteProduct = new javax.swing.JButton();
        cancelDeleteProduct = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        editProductDiaglog = new javax.swing.JDialog();
        editProductDiaglogPanel = new javax.swing.JPanel();
        editProductNameLabel = new javax.swing.JLabel();
        editProductProcessorTextField = new javax.swing.JTextField();
        editProductProcessorLabel = new javax.swing.JLabel();
        editProductMemoryLabel = new javax.swing.JLabel();
        editProductStorageLabel = new javax.swing.JLabel();
        editProductStorageTextField = new javax.swing.JTextField();
        editProductDisplayLabel = new javax.swing.JLabel();
        editProductDisplayTextField = new javax.swing.JTextField();
        editProductBatteryLabel = new javax.swing.JLabel();
        editProductBatteryTextField = new javax.swing.JTextField();
        editProductCardLabel = new javax.swing.JLabel();
        editProductCardTextField = new javax.swing.JTextField();
        editProductDiaglogButton = new javax.swing.JButton();
        cancelEditProductDiaglogButton = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        editProductWeightLabel = new javax.swing.JLabel();
        editProductWeightTextField = new javax.swing.JTextField();
        editProductMemoryTextField = new javax.swing.JTextField();
        editProductNameTextField = new javax.swing.JTextField();
        editProductIdLabel = new javax.swing.JLabel();
        editProductIdTextField = new javax.swing.JTextField();
        viewProductDiaglog = new javax.swing.JDialog();
        viewProductDiaglogPanel = new javax.swing.JPanel();
        viewProductNameLabel = new javax.swing.JLabel();
        viewProductProcessorTextField = new javax.swing.JTextField();
        viewProductProcessorLabel = new javax.swing.JLabel();
        viewProductMemoryLabel = new javax.swing.JLabel();
        viewProductStorageLabel = new javax.swing.JLabel();
        viewProductStorageTextField = new javax.swing.JTextField();
        viewProductDisplayLabel = new javax.swing.JLabel();
        viewProductDisplayTextField = new javax.swing.JTextField();
        viewProductBatteryLabel = new javax.swing.JLabel();
        viewProductBatteryTextField = new javax.swing.JTextField();
        viewProductCardLabel = new javax.swing.JLabel();
        viewProductCardTextField = new javax.swing.JTextField();
        updateProductDiaglogButton = new javax.swing.JButton();
        cancelViewProductDiaglogButton = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        viewProductWeightLabel = new javax.swing.JLabel();
        viewProductWeightTextField = new javax.swing.JTextField();
        viewProductMemoryTextField = new javax.swing.JTextField();
        viewProductNameTextField = new javax.swing.JTextField();
        viewProductIdLabel = new javax.swing.JLabel();
        viewProductIdTextField = new javax.swing.JTextField();
        viewProductWhenCreatedLabel = new javax.swing.JLabel();
        viewProductWhenCreatedTextField = new javax.swing.JTextField();
        viewProductLastUpdatedLabel = new javax.swing.JLabel();
        viewProductLastUpdatedTextField = new javax.swing.JTextField();
        addProductDiaglog = new javax.swing.JDialog();
        addProductDiaglogPanel = new javax.swing.JPanel();
        productNameLabel = new javax.swing.JLabel();
        productProcessorTextField = new javax.swing.JTextField();
        productProcessorLabel = new javax.swing.JLabel();
        productMemoryLabel = new javax.swing.JLabel();
        productStorageLabel = new javax.swing.JLabel();
        productStorageTextField = new javax.swing.JTextField();
        productDisplayLabel = new javax.swing.JLabel();
        productDisplayTextField = new javax.swing.JTextField();
        productBatteryLabel = new javax.swing.JLabel();
        productBatteryTextField = new javax.swing.JTextField();
        productCardLabel = new javax.swing.JLabel();
        productCardTextField = new javax.swing.JTextField();
        addProductDiaglogButton = new javax.swing.JButton();
        cancelAddProductDiaglogButton = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        productWeightLabel = new javax.swing.JLabel();
        productWeightTextField = new javax.swing.JTextField();
        productMemoryTextField = new javax.swing.JTextField();
        productNameTextField = new javax.swing.JTextField();
        addProviderDiaglog = new javax.swing.JDialog();
        addProductDiaglogPanel1 = new javax.swing.JPanel();
        addProviderNameLabel = new javax.swing.JLabel();
        addProviderPhoneNumberTextField = new javax.swing.JTextField();
        addProviderPhoneNumberLabel = new javax.swing.JLabel();
        addProviderEmailLabel = new javax.swing.JLabel();
        addProviderAddressLabel = new javax.swing.JLabel();
        addProviderAddressTextField = new javax.swing.JTextField();
        addProviderDiaglogButton = new javax.swing.JButton();
        cancelAddProviderDiaglogButton = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        addProviderEmailTextField = new javax.swing.JTextField();
        addProviderNameTextField = new javax.swing.JTextField();
        viewProviderDiaglog = new javax.swing.JDialog();
        viewProviderDiaglogPanel = new javax.swing.JPanel();
        viewProviderNameLabel = new javax.swing.JLabel();
        viewProviderPhoneNumberTextField = new javax.swing.JTextField();
        viewProviderPhoneNumberLabel = new javax.swing.JLabel();
        viewProviderEmailLabel = new javax.swing.JLabel();
        viewProviderAddressLabel = new javax.swing.JLabel();
        viewProviderAddressTextField = new javax.swing.JTextField();
        updateProviderDiaglogButton = new javax.swing.JButton();
        cancelViewProviderDiaglogButton = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        viewProviderEmailTextField = new javax.swing.JTextField();
        viewProviderNameTextField = new javax.swing.JTextField();
        viewProviderIdLabel = new javax.swing.JLabel();
        viewProviderIdTextField = new javax.swing.JTextField();
        viewProductStorageTextField2 = new javax.swing.JTextField();
        viewProviderWhenCreatedTextField = new javax.swing.JTextField();
        viewProviderWhenCreatedLabel = new javax.swing.JLabel();
        viewProviderLastUpdatedTextField = new javax.swing.JTextField();
        viewProviderLastUpdatedLabel = new javax.swing.JLabel();
        editProviderDiaglog = new javax.swing.JDialog();
        editProviderDiaglogPanel = new javax.swing.JPanel();
        editProviderNameLabel = new javax.swing.JLabel();
        editProviderPhoneNumberTextField = new javax.swing.JTextField();
        editProviderPhoneNumberLabel = new javax.swing.JLabel();
        editProviderEmailLabel = new javax.swing.JLabel();
        editProviderAddressLabel = new javax.swing.JLabel();
        editProviderAddressTextField = new javax.swing.JTextField();
        editProviderDiaglogButton = new javax.swing.JButton();
        cancelEditProviderDiaglogButton = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        editProviderEmailTextField = new javax.swing.JTextField();
        editProviderNameTextField = new javax.swing.JTextField();
        editProviderIdLabel = new javax.swing.JLabel();
        editProviderIdTextField = new javax.swing.JTextField();
        deleteProviderConfirmDiaglog = new javax.swing.JDialog();
        deleteProviderConfirmDiaglogPanel = new javax.swing.JPanel();
        deleteProviderConfirmDiaglogLabel = new javax.swing.JLabel();
        confirmDeleteProvider = new javax.swing.JButton();
        cancelDeleteProvider = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        editImportBillItemDiaglog = new javax.swing.JDialog();
        editImportBillItemDiaglogPanel = new javax.swing.JPanel();
        editImportBillItemQuantityLabel = new javax.swing.JLabel();
        editImportBillItemPriceTextField = new javax.swing.JTextField();
        editImportBillItemPriceLabel = new javax.swing.JLabel();
        editImportBillItemDiaglogButton = new javax.swing.JButton();
        cancelEditImportBillItemDiaglogButton = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        editImportBillItemQuantityTextField = new javax.swing.JTextField();
        editImportBillItemProductNameLabel = new javax.swing.JLabel();
        editImportBillItemProductNameTextField = new javax.swing.JTextField();
        editImportBillItemProductIdLabel = new javax.swing.JLabel();
        editImportBillItemProductIdTextField = new javax.swing.JTextField();
        editImportBillItemIndexLabel = new javax.swing.JLabel();
        editImportBillItemIndexTextField = new javax.swing.JTextField();
        deleteImportBillItemDiaglog = new javax.swing.JDialog();
        deleteImportBillItemDiaglogPanel = new javax.swing.JPanel();
        deleteImportBillItemDiaglogButton = new javax.swing.JButton();
        cancelDeleteImportBillItemDiaglogButton = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        deleteImportBillItemDiaglogLabel = new javax.swing.JLabel();
        viewImportBillDetailDiaglog = new javax.swing.JDialog();
        viewImportBillDetailPanel = new javax.swing.JPanel();
        viewImportBillDetailHeaderPanel = new javax.swing.JPanel();
        viewImportBillDetailHeaderLabel = new javax.swing.JLabel();
        viewImportBillIdLabel = new javax.swing.JLabel();
        viewImportBillProviderNameLabel = new javax.swing.JLabel();
        viewImportBillCreatorLabel = new javax.swing.JLabel();
        viewImportBillWhenCreatedLabel = new javax.swing.JLabel();
        viewImportBillProductsScrollTable = new javax.swing.JScrollPane();
        viewImportBillDetailItemsTable = new javax.swing.JTable();
        viewImportBillTotalLabel = new javax.swing.JLabel();
        viewImportBillIdValueLabel = new javax.swing.JLabel();
        viewImportBillProviderNameValueLabel = new javax.swing.JLabel();
        viewImportBillCreatorValueLabel = new javax.swing.JLabel();
        viewImportBillWhenCreatedValueLabel = new javax.swing.JLabel();
        viewImportBillTotalValueLabel = new javax.swing.JLabel();
        deleteImportBillDiaglog = new javax.swing.JDialog();
        deleteImportBillPanel = new javax.swing.JPanel();
        deleteImportBillDiaglogButton = new javax.swing.JButton();
        cancelDeleteImportBillDiaglogButton = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        deleteImportBillDiaglogLabel = new javax.swing.JLabel();
        deleteExportBillItemDiaglog = new javax.swing.JDialog();
        deleteExportBillItemPanel = new javax.swing.JPanel();
        deleteExportBillItemDiaglogButton = new javax.swing.JButton();
        cancelDeleteExportBillItemDiaglogButton = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        deleteExportBillItemDiaglogLabel = new javax.swing.JLabel();
        editExportBillItemDiaglog = new javax.swing.JDialog();
        editExportBillItemDiaglogPanel = new javax.swing.JPanel();
        editExportBillItemQuantityLabel = new javax.swing.JLabel();
        editExportBillItemPriceTextField = new javax.swing.JTextField();
        editExportBillItemPriceLabel = new javax.swing.JLabel();
        editExportBillItemDiaglogButton = new javax.swing.JButton();
        cancelEditExportBillItemDiaglogButton = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        editExportBillItemQuantityTextField = new javax.swing.JTextField();
        editExportBillItemProductNameLabel = new javax.swing.JLabel();
        editExportBillItemProductNameTextField = new javax.swing.JTextField();
        editExportBillItemProductIdLabel = new javax.swing.JLabel();
        editExportBillItemProductIdTextField = new javax.swing.JTextField();
        editExportBillItemIndexLabel = new javax.swing.JLabel();
        editExportBillItemIndexTextField = new javax.swing.JTextField();
        editExportBillItemInventoryIdLabel = new javax.swing.JLabel();
        editExportBillItemInventoryIdTextField = new javax.swing.JTextField();
        sidebarPanel = new javax.swing.JPanel();
        loginedUsername = new javax.swing.JLabel();
        productTab = new javax.swing.JPanel();
        productLabel = new javax.swing.JLabel();
        providerTab = new javax.swing.JPanel();
        providerLabel = new javax.swing.JLabel();
        importProductTab = new javax.swing.JPanel();
        importProductLabel = new javax.swing.JLabel();
        importBillTab = new javax.swing.JPanel();
        importBillLabel = new javax.swing.JLabel();
        exportProductTab = new javax.swing.JPanel();
        exportProductLabel = new javax.swing.JLabel();
        exportBillTab = new javax.swing.JPanel();
        exportBillLabel = new javax.swing.JLabel();
        inStockTab = new javax.swing.JPanel();
        inStockLabel = new javax.swing.JLabel();
        userTab = new javax.swing.JPanel();
        userLabel = new javax.swing.JLabel();
        statisticsTab = new javax.swing.JPanel();
        statisticsLabel = new javax.swing.JLabel();
        updateInfoTab = new javax.swing.JPanel();
        updateInfoLabel = new javax.swing.JLabel();
        logoutTab = new javax.swing.JPanel();
        logoutLabel = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        productPanel = new javax.swing.JPanel();
        productsScrollPanel = new javax.swing.JScrollPane();
        productsTable = new javax.swing.JTable();
        searchProductsPanel = new javax.swing.JPanel();
        searchProductsTextField = new javax.swing.JTextField();
        searchProductsButton = new javax.swing.JButton();
        productFunctionPanel = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        importProductsFromExcelButton = new javax.swing.JButton();
        exportProductsToExcelButton = new javax.swing.JButton();
        editProductButton = new javax.swing.JButton();
        addProductButton = new javax.swing.JButton();
        deleteProductButton = new javax.swing.JButton();
        viewProductButton = new javax.swing.JButton();
        providerPanel = new javax.swing.JPanel();
        providerFunctionPanel = new javax.swing.JPanel();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        importProvidersFromExcelButton = new javax.swing.JButton();
        exportProvidersToExcelButton = new javax.swing.JButton();
        editProviderButton = new javax.swing.JButton();
        addProviderButton = new javax.swing.JButton();
        deleteProviderButton = new javax.swing.JButton();
        viewProviderButton = new javax.swing.JButton();
        searchProvidersPanel = new javax.swing.JPanel();
        searchProvidersTextField = new javax.swing.JTextField();
        resetSearchProvidersButton = new javax.swing.JButton();
        providersScrollPanel = new javax.swing.JScrollPane();
        providersTable = new javax.swing.JTable();
        importProductPanel = new javax.swing.JPanel();
        searchImportProductPanel = new javax.swing.JPanel();
        searchImportProductTextField = new javax.swing.JTextField();
        searchImportProductRefreshButton = new javax.swing.JButton();
        importProductScrollPanel = new javax.swing.JScrollPane();
        importProductsTable = new javax.swing.JTable();
        importProductPriceLabel = new javax.swing.JLabel();
        importProductPriceTextField = new javax.swing.JTextField();
        importProductAddButton = new javax.swing.JButton();
        providerNameLabel = new javax.swing.JLabel();
        providerNameComboBox = new javax.swing.JComboBox<>();
        importProductBillCreatorLabel = new javax.swing.JTextField();
        importProductBillCreatorTextField = new javax.swing.JLabel();
        importProductBillScrollPane = new javax.swing.JScrollPane();
        importProductBillTable = new javax.swing.JTable();
        editBillItemButton = new javax.swing.JButton();
        removeBillItemButton = new javax.swing.JButton();
        totalImportPriceLabel = new javax.swing.JLabel();
        totalImportBillLabel = new javax.swing.JLabel();
        importBillProductButton = new javax.swing.JButton();
        importProductQuantityTextField = new javax.swing.JTextField();
        importProductQuantityLabel = new javax.swing.JLabel();
        importBillPanel = new javax.swing.JPanel();
        importBillsScrollPanel = new javax.swing.JScrollPane();
        importBillsTable = new javax.swing.JTable();
        searchImportBillsPanel = new javax.swing.JPanel();
        searchImportBillsTextField = new javax.swing.JTextField();
        resetSearchImportBillsButton1 = new javax.swing.JButton();
        importBillsFunctionPanel = new javax.swing.JPanel();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        exportImportBillsToExcelButton = new javax.swing.JButton();
        editImportBillButton = new javax.swing.JButton();
        deleteImportBillButton = new javax.swing.JButton();
        viewImportBillButton = new javax.swing.JButton();
        exportProductPanel = new javax.swing.JPanel();
        searchExportProductPanel = new javax.swing.JPanel();
        searchExportProductTextField = new javax.swing.JTextField();
        searchExportProductRefreshButton = new javax.swing.JButton();
        exportProductScrollPanel = new javax.swing.JScrollPane();
        exportInStockProductsTable = new javax.swing.JTable();
        exportProductPriceLabel = new javax.swing.JLabel();
        exportProductPriceTextField = new javax.swing.JTextField();
        exportProductAddButton = new javax.swing.JButton();
        clientNameLabel = new javax.swing.JLabel();
        exportProductBillCreatorLabel = new javax.swing.JTextField();
        exportProductBillCreatorTextField = new javax.swing.JLabel();
        exportProductBillScrollPane = new javax.swing.JScrollPane();
        exportProductsBillTable = new javax.swing.JTable();
        editExportBillItemButton = new javax.swing.JButton();
        removeExportBillItemButton = new javax.swing.JButton();
        totalExportPriceLabel = new javax.swing.JLabel();
        totalExportBillLabel = new javax.swing.JLabel();
        exportBillProductButton = new javax.swing.JButton();
        exportProductQuantityTextField = new javax.swing.JTextField();
        exportProductQuantityLabel = new javax.swing.JLabel();
        clientNameTextField = new javax.swing.JTextField();
        clientPhoneNumberTextField = new javax.swing.JTextField();
        clientPhoneNumberLabel = new javax.swing.JLabel();
        clientAddressTextField = new javax.swing.JTextField();
        clientAddressLabel = new javax.swing.JLabel();
        exportBillPanel = new javax.swing.JPanel();
        exportBillsScrollPanel = new javax.swing.JScrollPane();
        exportBillsTable = new javax.swing.JTable();
        searchExportBillsPanel = new javax.swing.JPanel();
        searchExportBillsTextField = new javax.swing.JTextField();
        resetSearchExportBillsButton = new javax.swing.JButton();
        exportBillsFunctionPanel = new javax.swing.JPanel();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        exportImportBillsToExcelButton1 = new javax.swing.JButton();
        editExportBillButton = new javax.swing.JButton();
        deleteExportBillButton = new javax.swing.JButton();
        viewExportBillButton = new javax.swing.JButton();
        inStockPanel = new javax.swing.JPanel();
        inStocksScrollPanel = new javax.swing.JScrollPane();
        inStocksTable = new javax.swing.JTable();
        searchInStocksPanel = new javax.swing.JPanel();
        searchInStocksTextField = new javax.swing.JTextField();
        resetSearchInStocksButton = new javax.swing.JButton();
        inStocksFunctionPanel = new javax.swing.JPanel();
        jSeparator9 = new javax.swing.JToolBar.Separator();
        exportInStocksToExcelButton = new javax.swing.JButton();
        editInStockButton = new javax.swing.JButton();
        deleteInStockButton = new javax.swing.JButton();
        viewInStockButton = new javax.swing.JButton();
        userPanel = new javax.swing.JPanel();
        usersScrollPanel = new javax.swing.JScrollPane();
        usersTable = new javax.swing.JTable();
        searchUsersPanel = new javax.swing.JPanel();
        searchUsersTextField = new javax.swing.JTextField();
        searchUsersButton = new javax.swing.JButton();
        userFunctionPanel = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        importUsersFromExcelButton = new javax.swing.JButton();
        exportUsersToExcelButton = new javax.swing.JButton();
        editUserButton = new javax.swing.JButton();
        viewUserButton = new javax.swing.JButton();
        deleteUserButton = new javax.swing.JButton();
        addUserButton = new javax.swing.JButton();
        statisticsPanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();

        logoutDiaglog.setMinimumSize(new java.awt.Dimension(360, 250));
        logoutDiaglog.setSize(new java.awt.Dimension(360, 250));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(360, 250));
        jPanel1.setName(""); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(360, 250));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("THOÁT");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 27, 354, -1));

        jTextField1.setEditable(false);
        jTextField1.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("Bạn có chắc chắn muốn thoát ứng dụng?");
        jTextField1.setActionCommand("<Not Set>");
        jTextField1.setAutoscrolls(false);
        jTextField1.setBorder(null);
        jTextField1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextField1.setEnabled(false);
        jTextField1.setFocusable(false);
        jTextField1.setScrollOffset(250);
        jTextField1.setVerifyInputWhenFocusTarget(false);
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 60, 340, 90));

        jButton1.setBackground(new java.awt.Color(44, 43, 196));
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Huỷ");
        jButton1.setBorderPainted(false);
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 80, 30));

        jButton2.setBackground(new java.awt.Color(255, 51, 51));
        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Thoát");
        jButton2.setBorderPainted(false);
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 80, 30));

        javax.swing.GroupLayout logoutDiaglogLayout = new javax.swing.GroupLayout(logoutDiaglog.getContentPane());
        logoutDiaglog.getContentPane().setLayout(logoutDiaglogLayout);
        logoutDiaglogLayout.setHorizontalGroup(
            logoutDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        logoutDiaglogLayout.setVerticalGroup(
            logoutDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        addUserDiaglog.setTitle("Thêm người dùng");
        addUserDiaglog.setBackground(new java.awt.Color(255, 255, 255));
        addUserDiaglog.setMinimumSize(new java.awt.Dimension(450, 500));
        addUserDiaglog.setSize(new java.awt.Dimension(450, 500));

        addUserDiaglogPanel.setBackground(new java.awt.Color(255, 255, 255));
        addUserDiaglogPanel.setMinimumSize(new java.awt.Dimension(430, 450));
        addUserDiaglogPanel.setPreferredSize(new java.awt.Dimension(430, 450));
        addUserDiaglogPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        usernameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        usernameLabel.setText("Tên đăng nhập");
        addUserDiaglogPanel.add(usernameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        usernameTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        usernameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameTextFieldActionPerformed(evt);
            }
        });
        addUserDiaglogPanel.add(usernameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 200, 30));

        passwordLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        passwordLabel.setText("Mật khẩu");
        addUserDiaglogPanel.add(passwordLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        passwordField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        addUserDiaglogPanel.add(passwordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 200, 30));

        confirmPasswordLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        confirmPasswordLabel.setText("Xác nhận mật khẩu");
        addUserDiaglogPanel.add(confirmPasswordLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));

        confirmPasswordField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        addUserDiaglogPanel.add(confirmPasswordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 200, 30));

        firstNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        firstNameLabel.setText("Tên");
        addUserDiaglogPanel.add(firstNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, -1));

        firstNameTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        firstNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstNameTextFieldActionPerformed(evt);
            }
        });
        addUserDiaglogPanel.add(firstNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 190, 200, 30));

        lastNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lastNameLabel.setText("Họ đệm");
        addUserDiaglogPanel.add(lastNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        lastNameTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lastNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastNameTextFieldActionPerformed(evt);
            }
        });
        addUserDiaglogPanel.add(lastNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 230, 200, 30));

        phoneNumberLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        phoneNumberLabel.setText("Số điện thoại");
        addUserDiaglogPanel.add(phoneNumberLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, -1));

        phoneNumberTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        phoneNumberTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneNumberTextFieldActionPerformed(evt);
            }
        });
        addUserDiaglogPanel.add(phoneNumberTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 270, 200, 30));

        emailLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        emailLabel.setText("Địa chỉ email");
        addUserDiaglogPanel.add(emailLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, -1, -1));

        emailTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        emailTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailTextFieldActionPerformed(evt);
            }
        });
        addUserDiaglogPanel.add(emailTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 310, 200, 30));

        confirmEmailOTPLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        confirmEmailOTPLabel.setText("Mã xác nhận email");
        addUserDiaglogPanel.add(confirmEmailOTPLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, -1, -1));

        confirmEmailOTPTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        confirmEmailOTPTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmEmailOTPTextFieldActionPerformed(evt);
            }
        });
        addUserDiaglogPanel.add(confirmEmailOTPTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 100, 30));

        addUserDiaglogButton.setBackground(new java.awt.Color(0, 122, 249));
        addUserDiaglogButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        addUserDiaglogButton.setForeground(new java.awt.Color(255, 255, 255));
        addUserDiaglogButton.setText("Thêm mới");
        addUserDiaglogButton.setBorderPainted(false);
        addUserDiaglogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserDiaglogButtonActionPerformed(evt);
            }
        });
        addUserDiaglogPanel.add(addUserDiaglogButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 400, -1, -1));

        cancelAddUserDiaglogButton.setBackground(new java.awt.Color(212, 57, 68));
        cancelAddUserDiaglogButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cancelAddUserDiaglogButton.setForeground(new java.awt.Color(255, 255, 255));
        cancelAddUserDiaglogButton.setText("Huỷ bỏ");
        cancelAddUserDiaglogButton.setBorderPainted(false);
        cancelAddUserDiaglogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelAddUserDiaglogButtonActionPerformed(evt);
            }
        });
        addUserDiaglogPanel.add(cancelAddUserDiaglogButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 400, -1, -1));

        sendCodeAddUserDiaglogButton.setBackground(new java.awt.Color(36, 169, 65));
        sendCodeAddUserDiaglogButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        sendCodeAddUserDiaglogButton.setForeground(new java.awt.Color(255, 255, 255));
        sendCodeAddUserDiaglogButton.setText("Gửi mã");
        sendCodeAddUserDiaglogButton.setBorderPainted(false);
        addUserDiaglogPanel.add(sendCodeAddUserDiaglogButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 350, 90, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(44, 43, 196));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("THÊM NGƯỜI DÙNG");
        addUserDiaglogPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 440, -1));

        javax.swing.GroupLayout addUserDiaglogLayout = new javax.swing.GroupLayout(addUserDiaglog.getContentPane());
        addUserDiaglog.getContentPane().setLayout(addUserDiaglogLayout);
        addUserDiaglogLayout.setHorizontalGroup(
            addUserDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addUserDiaglogPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
        );
        addUserDiaglogLayout.setVerticalGroup(
            addUserDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addUserDiaglogPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
        );

        diaglogMessage.setTitle("Thông báo");
        diaglogMessage.setMinimumSize(new java.awt.Dimension(605, 250));
        diaglogMessage.setSize(new java.awt.Dimension(605, 250));

        diaglogMessagePanel.setBackground(new java.awt.Color(255, 255, 255));
        diaglogMessagePanel.setMinimumSize(new java.awt.Dimension(605, 250));
        diaglogMessagePanel.setPreferredSize(new java.awt.Dimension(605, 250));
        diaglogMessagePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        diaglogMessageLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        diaglogMessageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        diaglogMessageLabel.setText("Thêm mới người dùng thành công!");
        diaglogMessagePanel.add(diaglogMessageLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 610, 65));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(212, 57, 68));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("THÔNG BÁO");
        diaglogMessagePanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 388, -1));

        diaglogMessageOkButton.setBackground(new java.awt.Color(44, 43, 196));
        diaglogMessageOkButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        diaglogMessageOkButton.setForeground(new java.awt.Color(255, 255, 255));
        diaglogMessageOkButton.setText("OK");
        diaglogMessageOkButton.setBorderPainted(false);
        diaglogMessageOkButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        diaglogMessageOkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diaglogMessageOkButtonActionPerformed(evt);
            }
        });
        diaglogMessagePanel.add(diaglogMessageOkButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 170, 82, 30));

        javax.swing.GroupLayout diaglogMessageLayout = new javax.swing.GroupLayout(diaglogMessage.getContentPane());
        diaglogMessage.getContentPane().setLayout(diaglogMessageLayout);
        diaglogMessageLayout.setHorizontalGroup(
            diaglogMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(diaglogMessageLayout.createSequentialGroup()
                .addComponent(diaglogMessagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        diaglogMessageLayout.setVerticalGroup(
            diaglogMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(diaglogMessagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        deleteUserConfirmDiaglog.setTitle("Xác nhận xoá");
        deleteUserConfirmDiaglog.setMinimumSize(new java.awt.Dimension(420, 230));
        deleteUserConfirmDiaglog.setResizable(false);
        deleteUserConfirmDiaglog.setSize(new java.awt.Dimension(420, 230));

        deleteUserConfirmDiaglogPanel.setBackground(new java.awt.Color(255, 255, 255));
        deleteUserConfirmDiaglogPanel.setMinimumSize(new java.awt.Dimension(420, 230));
        deleteUserConfirmDiaglogPanel.setPreferredSize(new java.awt.Dimension(420, 230));
        deleteUserConfirmDiaglogPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        deleteUserConfirmDiaglogLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        deleteUserConfirmDiaglogLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        deleteUserConfirmDiaglogLabel.setText("Bạn chắc chắn muốn xoá người dùng abcdeskskskskksd");
        deleteUserConfirmDiaglogLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deleteUserConfirmDiaglogPanel.add(deleteUserConfirmDiaglogLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 67, 400, 50));

        confirmDeleteUser.setBackground(new java.awt.Color(44, 43, 196));
        confirmDeleteUser.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        confirmDeleteUser.setForeground(new java.awt.Color(255, 255, 255));
        confirmDeleteUser.setText("Đồng ý");
        confirmDeleteUser.setBorderPainted(false);
        confirmDeleteUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmDeleteUserActionPerformed(evt);
            }
        });
        deleteUserConfirmDiaglogPanel.add(confirmDeleteUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, -1, 30));

        cancelDeleteUser.setBackground(new java.awt.Color(212, 57, 68));
        cancelDeleteUser.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cancelDeleteUser.setForeground(new java.awt.Color(255, 255, 255));
        cancelDeleteUser.setText("Huỷ bỏ");
        cancelDeleteUser.setBorderPainted(false);
        cancelDeleteUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelDeleteUserActionPerformed(evt);
            }
        });
        deleteUserConfirmDiaglogPanel.add(cancelDeleteUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, -1, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("XÁC NHẬN XOÁ");
        deleteUserConfirmDiaglogPanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 29, 408, -1));

        javax.swing.GroupLayout deleteUserConfirmDiaglogLayout = new javax.swing.GroupLayout(deleteUserConfirmDiaglog.getContentPane());
        deleteUserConfirmDiaglog.getContentPane().setLayout(deleteUserConfirmDiaglogLayout);
        deleteUserConfirmDiaglogLayout.setHorizontalGroup(
            deleteUserConfirmDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deleteUserConfirmDiaglogPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        deleteUserConfirmDiaglogLayout.setVerticalGroup(
            deleteUserConfirmDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deleteUserConfirmDiaglogPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        editUserDiaglog.setTitle("Cập nhật người dùng");
        editUserDiaglog.setBackground(new java.awt.Color(255, 255, 255));
        editUserDiaglog.setMinimumSize(new java.awt.Dimension(450, 500));
        editUserDiaglog.setSize(new java.awt.Dimension(450, 500));

        editUserDiaglogPanel.setBackground(new java.awt.Color(255, 255, 255));
        editUserDiaglogPanel.setMinimumSize(new java.awt.Dimension(430, 450));
        editUserDiaglogPanel.setPreferredSize(new java.awt.Dimension(430, 450));
        editUserDiaglogPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        editUsernameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editUsernameLabel.setText("Tên đăng nhập");
        editUserDiaglogPanel.add(editUsernameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, 30));

        editUsernameTextField.setEditable(false);
        editUsernameTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editUsernameTextField.setEnabled(false);
        editUsernameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editUsernameTextFieldActionPerformed(evt);
            }
        });
        editUserDiaglogPanel.add(editUsernameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 200, 30));

        editFirstNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editFirstNameLabel.setText("Tên");
        editUserDiaglogPanel.add(editFirstNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, 30));

        editFirstNameTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editFirstNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editFirstNameTextFieldActionPerformed(evt);
            }
        });
        editUserDiaglogPanel.add(editFirstNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, 200, 30));

        editLastNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editLastNameLabel.setText("Họ đệm");
        editUserDiaglogPanel.add(editLastNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, -1, 30));

        editLastNameTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editLastNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editLastNameTextFieldActionPerformed(evt);
            }
        });
        editUserDiaglogPanel.add(editLastNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 210, 200, 30));

        editPhoneNumberLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editPhoneNumberLabel.setText("Số điện thoại");
        editUserDiaglogPanel.add(editPhoneNumberLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, 30));

        editPhoneNumberTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editPhoneNumberTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editPhoneNumberTextFieldActionPerformed(evt);
            }
        });
        editUserDiaglogPanel.add(editPhoneNumberTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 260, 200, 30));

        editEmailLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editEmailLabel.setText("Địa chỉ email");
        editUserDiaglogPanel.add(editEmailLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, -1, 30));

        editEmailTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editEmailTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editEmailTextFieldActionPerformed(evt);
            }
        });
        editUserDiaglogPanel.add(editEmailTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 310, 200, 30));

        editUserDiaglogButton.setBackground(new java.awt.Color(0, 122, 249));
        editUserDiaglogButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editUserDiaglogButton.setForeground(new java.awt.Color(255, 255, 255));
        editUserDiaglogButton.setText("Cập nhật");
        editUserDiaglogButton.setBorderPainted(false);
        editUserDiaglogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editUserDiaglogButtonActionPerformed(evt);
            }
        });
        editUserDiaglogPanel.add(editUserDiaglogButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 380, -1, 30));

        cancelEditUserDiaglogButton.setBackground(new java.awt.Color(212, 57, 68));
        cancelEditUserDiaglogButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cancelEditUserDiaglogButton.setForeground(new java.awt.Color(255, 255, 255));
        cancelEditUserDiaglogButton.setText("Huỷ bỏ");
        cancelEditUserDiaglogButton.setBorderPainted(false);
        cancelEditUserDiaglogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelEditUserDiaglogButtonActionPerformed(evt);
            }
        });
        editUserDiaglogPanel.add(cancelEditUserDiaglogButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 380, -1, 30));

        editUserDiaglogLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        editUserDiaglogLabel.setForeground(new java.awt.Color(44, 43, 196));
        editUserDiaglogLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        editUserDiaglogLabel.setText("CẬP NHẬT NGƯỜI DÙNG");
        editUserDiaglogPanel.add(editUserDiaglogLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 440, -1));

        editUserIdLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editUserIdLabel.setText("Mã người dùng");
        editUserDiaglogPanel.add(editUserIdLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, 30));

        editUserIdTextField.setEditable(false);
        editUserIdTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editUserIdTextField.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        editUserIdTextField.setEnabled(false);
        editUserIdTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editUserIdTextFieldActionPerformed(evt);
            }
        });
        editUserDiaglogPanel.add(editUserIdTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, 200, 30));

        javax.swing.GroupLayout editUserDiaglogLayout = new javax.swing.GroupLayout(editUserDiaglog.getContentPane());
        editUserDiaglog.getContentPane().setLayout(editUserDiaglogLayout);
        editUserDiaglogLayout.setHorizontalGroup(
            editUserDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(editUserDiaglogPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
        );
        editUserDiaglogLayout.setVerticalGroup(
            editUserDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(editUserDiaglogPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
        );

        viewUserDiaglog.setTitle("Thông tin người dùng");
        viewUserDiaglog.setBackground(new java.awt.Color(255, 255, 255));
        viewUserDiaglog.setMinimumSize(new java.awt.Dimension(448, 530));
        viewUserDiaglog.setSize(new java.awt.Dimension(448, 530));

        viewUserDiaglogPanel.setBackground(new java.awt.Color(255, 255, 255));
        viewUserDiaglogPanel.setMinimumSize(new java.awt.Dimension(448, 530));
        viewUserDiaglogPanel.setPreferredSize(new java.awt.Dimension(448, 530));
        viewUserDiaglogPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        viewUsernameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewUsernameLabel.setText("Tên đăng nhập");
        viewUserDiaglogPanel.add(viewUsernameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, 30));

        viewUserLastNameTextField.setEditable(false);
        viewUserLastNameTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewUserLastNameTextField.setEnabled(false);
        viewUserLastNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewUserLastNameTextFieldActionPerformed(evt);
            }
        });
        viewUserDiaglogPanel.add(viewUserLastNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 200, 30));

        viewUserFirstNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewUserFirstNameLabel.setText("Tên");
        viewUserDiaglogPanel.add(viewUserFirstNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, 30));

        viewUserLastNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewUserLastNameLabel.setText("Họ đệm");
        viewUserDiaglogPanel.add(viewUserLastNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, 30));

        viewUserPhoneNumberLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewUserPhoneNumberLabel.setText("Số điện thoại");
        viewUserDiaglogPanel.add(viewUserPhoneNumberLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, 30));

        viewUserPhoneNumberTextField.setEditable(false);
        viewUserPhoneNumberTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewUserPhoneNumberTextField.setEnabled(false);
        viewUserPhoneNumberTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewUserPhoneNumberTextFieldActionPerformed(evt);
            }
        });
        viewUserDiaglogPanel.add(viewUserPhoneNumberTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 190, 200, 30));

        viewUserEmailLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewUserEmailLabel.setText("Địa chỉ email");
        viewUserDiaglogPanel.add(viewUserEmailLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, 30));

        viewUserEmailTextField.setEditable(false);
        viewUserEmailTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewUserEmailTextField.setEnabled(false);
        viewUserEmailTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewUserEmailTextFieldActionPerformed(evt);
            }
        });
        viewUserDiaglogPanel.add(viewUserEmailTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 230, 200, 30));

        viewUserWhenCreatedLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewUserWhenCreatedLabel.setText("Ngày tạo");
        viewUserDiaglogPanel.add(viewUserWhenCreatedLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, 30));

        viewUserWhenCreatedTextField.setEditable(false);
        viewUserWhenCreatedTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewUserWhenCreatedTextField.setEnabled(false);
        viewUserWhenCreatedTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewUserWhenCreatedTextFieldActionPerformed(evt);
            }
        });
        viewUserDiaglogPanel.add(viewUserWhenCreatedTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 270, 200, 30));

        viewUserLastUpdatedLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewUserLastUpdatedLabel.setText("Cập nhật lần cuối");
        viewUserDiaglogPanel.add(viewUserLastUpdatedLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, -1, 30));

        viewUserLastUpdatedTextField.setEditable(false);
        viewUserLastUpdatedTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewUserLastUpdatedTextField.setEnabled(false);
        viewUserLastUpdatedTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewUserLastUpdatedTextFieldActionPerformed(evt);
            }
        });
        viewUserDiaglogPanel.add(viewUserLastUpdatedTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 310, 200, 30));

        cancelAddUserDiaglogButton1.setBackground(new java.awt.Color(212, 57, 68));
        cancelAddUserDiaglogButton1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cancelAddUserDiaglogButton1.setForeground(new java.awt.Color(255, 255, 255));
        cancelAddUserDiaglogButton1.setText("Đóng");
        cancelAddUserDiaglogButton1.setBorderPainted(false);
        cancelAddUserDiaglogButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelAddUserDiaglogButton1ActionPerformed(evt);
            }
        });
        viewUserDiaglogPanel.add(cancelAddUserDiaglogButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 440, -1, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(44, 43, 196));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("THÔNG TIN NGƯỜI DÙNG");
        viewUserDiaglogPanel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 440, -1));

        editUserDiaglogButton1.setBackground(new java.awt.Color(0, 122, 249));
        editUserDiaglogButton1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editUserDiaglogButton1.setForeground(new java.awt.Color(255, 255, 255));
        editUserDiaglogButton1.setText("Cập nhật");
        editUserDiaglogButton1.setBorderPainted(false);
        editUserDiaglogButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editUserDiaglogButton1ActionPerformed(evt);
            }
        });
        viewUserDiaglogPanel.add(editUserDiaglogButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 440, -1, 30));

        viewUserLastLoggedInLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewUserLastLoggedInLabel.setText("Đăng nhập lần cuối");
        viewUserDiaglogPanel.add(viewUserLastLoggedInLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, -1, 30));

        viewUserLastLoggedInTextField.setEditable(false);
        viewUserLastLoggedInTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewUserLastLoggedInTextField.setEnabled(false);
        viewUserLastLoggedInTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewUserLastLoggedInTextFieldActionPerformed(evt);
            }
        });
        viewUserDiaglogPanel.add(viewUserLastLoggedInTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 200, 30));

        viewUserLoggedInLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewUserLoggedInLabel.setText("Số lần đăng nhập");
        viewUserDiaglogPanel.add(viewUserLoggedInLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, -1, 30));

        viewUserLoggedInTextField.setEditable(false);
        viewUserLoggedInTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewUserLoggedInTextField.setEnabled(false);
        viewUserLoggedInTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewUserLoggedInTextFieldActionPerformed(evt);
            }
        });
        viewUserDiaglogPanel.add(viewUserLoggedInTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 390, 200, 30));

        viewUsernameTextField.setEditable(false);
        viewUsernameTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewUsernameTextField.setEnabled(false);
        viewUsernameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewUsernameTextFieldActionPerformed(evt);
            }
        });
        viewUserDiaglogPanel.add(viewUsernameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 200, 30));

        viewUserFirstNameTextField.setEditable(false);
        viewUserFirstNameTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewUserFirstNameTextField.setEnabled(false);
        viewUserFirstNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewUserFirstNameTextFieldActionPerformed(evt);
            }
        });
        viewUserDiaglogPanel.add(viewUserFirstNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 200, 30));

        javax.swing.GroupLayout viewUserDiaglogLayout = new javax.swing.GroupLayout(viewUserDiaglog.getContentPane());
        viewUserDiaglog.getContentPane().setLayout(viewUserDiaglogLayout);
        viewUserDiaglogLayout.setHorizontalGroup(
            viewUserDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewUserDiaglogPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        viewUserDiaglogLayout.setVerticalGroup(
            viewUserDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewUserDiaglogPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        deleteProductConfirmDiaglog.setTitle("Xác nhận xoá");
        deleteProductConfirmDiaglog.setMinimumSize(new java.awt.Dimension(420, 230));
        deleteProductConfirmDiaglog.setResizable(false);
        deleteProductConfirmDiaglog.setSize(new java.awt.Dimension(420, 230));

        deleteProductConfirmDiaglogPanel.setBackground(new java.awt.Color(255, 255, 255));
        deleteProductConfirmDiaglogPanel.setMinimumSize(new java.awt.Dimension(420, 230));
        deleteProductConfirmDiaglogPanel.setPreferredSize(new java.awt.Dimension(420, 230));
        deleteProductConfirmDiaglogPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        deleteProductConfirmDiaglogLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        deleteProductConfirmDiaglogLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        deleteProductConfirmDiaglogLabel.setText("Bạn chắc chắn muốn xoá người dùng abcdeskskskskksd");
        deleteProductConfirmDiaglogLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deleteProductConfirmDiaglogPanel.add(deleteProductConfirmDiaglogLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 67, 400, 50));

        confirmDeleteProduct.setBackground(new java.awt.Color(44, 43, 196));
        confirmDeleteProduct.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        confirmDeleteProduct.setForeground(new java.awt.Color(255, 255, 255));
        confirmDeleteProduct.setText("Đồng ý");
        confirmDeleteProduct.setBorderPainted(false);
        confirmDeleteProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmDeleteProductActionPerformed(evt);
            }
        });
        deleteProductConfirmDiaglogPanel.add(confirmDeleteProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, -1, 30));

        cancelDeleteProduct.setBackground(new java.awt.Color(212, 57, 68));
        cancelDeleteProduct.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cancelDeleteProduct.setForeground(new java.awt.Color(255, 255, 255));
        cancelDeleteProduct.setText("Huỷ bỏ");
        cancelDeleteProduct.setBorderPainted(false);
        cancelDeleteProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelDeleteProductActionPerformed(evt);
            }
        });
        deleteProductConfirmDiaglogPanel.add(cancelDeleteProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, -1, 30));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 0, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("XÁC NHẬN XOÁ");
        deleteProductConfirmDiaglogPanel.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 29, 408, -1));

        javax.swing.GroupLayout deleteProductConfirmDiaglogLayout = new javax.swing.GroupLayout(deleteProductConfirmDiaglog.getContentPane());
        deleteProductConfirmDiaglog.getContentPane().setLayout(deleteProductConfirmDiaglogLayout);
        deleteProductConfirmDiaglogLayout.setHorizontalGroup(
            deleteProductConfirmDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deleteProductConfirmDiaglogPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        deleteProductConfirmDiaglogLayout.setVerticalGroup(
            deleteProductConfirmDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deleteProductConfirmDiaglogPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        editProductDiaglog.setTitle("Cập nhật sản phẩm");
        editProductDiaglog.setBackground(new java.awt.Color(255, 255, 255));
        editProductDiaglog.setMinimumSize(new java.awt.Dimension(450, 540));
        editProductDiaglog.setSize(new java.awt.Dimension(450, 540));

        editProductDiaglogPanel.setBackground(new java.awt.Color(255, 255, 255));
        editProductDiaglogPanel.setMinimumSize(new java.awt.Dimension(450, 540));
        editProductDiaglogPanel.setPreferredSize(new java.awt.Dimension(450, 540));
        editProductDiaglogPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        editProductNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProductNameLabel.setText("Tên sản phẩm");
        editProductDiaglogPanel.add(editProductNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, 30));

        editProductProcessorTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProductProcessorTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProductProcessorTextFieldActionPerformed(evt);
            }
        });
        editProductDiaglogPanel.add(editProductProcessorTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, 200, 30));

        editProductProcessorLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProductProcessorLabel.setText("Vi xử lý");
        editProductDiaglogPanel.add(editProductProcessorLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, 30));

        editProductMemoryLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProductMemoryLabel.setText("Dung lượng RAM");
        editProductDiaglogPanel.add(editProductMemoryLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1, 30));

        editProductStorageLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProductStorageLabel.setText("Dung lượng lưu trữ");
        editProductDiaglogPanel.add(editProductStorageLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, -1, 30));

        editProductStorageTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProductStorageTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProductStorageTextFieldActionPerformed(evt);
            }
        });
        editProductDiaglogPanel.add(editProductStorageTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 220, 200, 30));

        editProductDisplayLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProductDisplayLabel.setText("Màn hình");
        editProductDiaglogPanel.add(editProductDisplayLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, 30));

        editProductDisplayTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProductDisplayTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProductDisplayTextFieldActionPerformed(evt);
            }
        });
        editProductDiaglogPanel.add(editProductDisplayTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 260, 200, 30));

        editProductBatteryLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProductBatteryLabel.setText("Dung lượng pin");
        editProductDiaglogPanel.add(editProductBatteryLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, -1, 30));

        editProductBatteryTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProductBatteryTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProductBatteryTextFieldActionPerformed(evt);
            }
        });
        editProductDiaglogPanel.add(editProductBatteryTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 300, 200, 30));

        editProductCardLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProductCardLabel.setText("Card đồ hoạ");
        editProductDiaglogPanel.add(editProductCardLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, -1, 30));

        editProductCardTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProductCardTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProductCardTextFieldActionPerformed(evt);
            }
        });
        editProductDiaglogPanel.add(editProductCardTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 340, 200, 30));

        editProductDiaglogButton.setBackground(new java.awt.Color(0, 122, 249));
        editProductDiaglogButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProductDiaglogButton.setText("Cập nhật");
        editProductDiaglogButton.setBorderPainted(false);
        editProductDiaglogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProductDiaglogButtonActionPerformed(evt);
            }
        });
        editProductDiaglogPanel.add(editProductDiaglogButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 440, -1, -1));

        cancelEditProductDiaglogButton.setBackground(new java.awt.Color(212, 57, 68));
        cancelEditProductDiaglogButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cancelEditProductDiaglogButton.setText("Huỷ bỏ");
        cancelEditProductDiaglogButton.setBorderPainted(false);
        cancelEditProductDiaglogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelEditProductDiaglogButtonActionPerformed(evt);
            }
        });
        editProductDiaglogPanel.add(cancelEditProductDiaglogButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 440, -1, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(44, 43, 196));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("CẬP NHẬT SẢN PHẨM");
        editProductDiaglogPanel.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 440, -1));

        editProductWeightLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProductWeightLabel.setText("Trọng lượng");
        editProductDiaglogPanel.add(editProductWeightLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, -1, 30));

        editProductWeightTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProductWeightTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProductWeightTextFieldActionPerformed(evt);
            }
        });
        editProductDiaglogPanel.add(editProductWeightTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 380, 200, 30));

        editProductMemoryTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProductMemoryTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProductMemoryTextFieldActionPerformed(evt);
            }
        });
        editProductDiaglogPanel.add(editProductMemoryTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 200, 30));

        editProductNameTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProductNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProductNameTextFieldActionPerformed(evt);
            }
        });
        editProductDiaglogPanel.add(editProductNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, 200, 30));

        editProductIdLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProductIdLabel.setText("Mã sản phẩm");
        editProductDiaglogPanel.add(editProductIdLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, 30));

        editProductIdTextField.setEditable(false);
        editProductIdTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProductIdTextField.setEnabled(false);
        editProductIdTextField.setFocusable(false);
        editProductIdTextField.setRequestFocusEnabled(false);
        editProductIdTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProductIdTextFieldActionPerformed(evt);
            }
        });
        editProductDiaglogPanel.add(editProductIdTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, 200, 30));

        javax.swing.GroupLayout editProductDiaglogLayout = new javax.swing.GroupLayout(editProductDiaglog.getContentPane());
        editProductDiaglog.getContentPane().setLayout(editProductDiaglogLayout);
        editProductDiaglogLayout.setHorizontalGroup(
            editProductDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(editProductDiaglogPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        editProductDiaglogLayout.setVerticalGroup(
            editProductDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(editProductDiaglogPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        viewProductDiaglog.setTitle("Thông tin sản phẩm");
        viewProductDiaglog.setBackground(new java.awt.Color(255, 255, 255));
        viewProductDiaglog.setMinimumSize(new java.awt.Dimension(450, 600));
        viewProductDiaglog.setSize(new java.awt.Dimension(450, 600));

        viewProductDiaglogPanel.setBackground(new java.awt.Color(255, 255, 255));
        viewProductDiaglogPanel.setMinimumSize(new java.awt.Dimension(450, 600));
        viewProductDiaglogPanel.setPreferredSize(new java.awt.Dimension(450, 600));
        viewProductDiaglogPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        viewProductNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProductNameLabel.setText("Tên sản phẩm");
        viewProductDiaglogPanel.add(viewProductNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, 30));

        viewProductProcessorTextField.setEditable(false);
        viewProductProcessorTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProductProcessorTextField.setEnabled(false);
        viewProductProcessorTextField.setFocusable(false);
        viewProductProcessorTextField.setRequestFocusEnabled(false);
        viewProductProcessorTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewProductProcessorTextFieldActionPerformed(evt);
            }
        });
        viewProductDiaglogPanel.add(viewProductProcessorTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, 200, 30));

        viewProductProcessorLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProductProcessorLabel.setText("Vi xử lý");
        viewProductDiaglogPanel.add(viewProductProcessorLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, 30));

        viewProductMemoryLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProductMemoryLabel.setText("Dung lượng RAM");
        viewProductDiaglogPanel.add(viewProductMemoryLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1, 30));

        viewProductStorageLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProductStorageLabel.setText("Dung lượng lưu trữ");
        viewProductDiaglogPanel.add(viewProductStorageLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, -1, 30));

        viewProductStorageTextField.setEditable(false);
        viewProductStorageTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProductStorageTextField.setEnabled(false);
        viewProductStorageTextField.setFocusable(false);
        viewProductStorageTextField.setRequestFocusEnabled(false);
        viewProductStorageTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewProductStorageTextFieldActionPerformed(evt);
            }
        });
        viewProductDiaglogPanel.add(viewProductStorageTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 220, 200, 30));

        viewProductDisplayLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProductDisplayLabel.setText("Màn hình");
        viewProductDiaglogPanel.add(viewProductDisplayLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, 30));

        viewProductDisplayTextField.setEditable(false);
        viewProductDisplayTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProductDisplayTextField.setEnabled(false);
        viewProductDisplayTextField.setFocusable(false);
        viewProductDisplayTextField.setRequestFocusEnabled(false);
        viewProductDisplayTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewProductDisplayTextFieldActionPerformed(evt);
            }
        });
        viewProductDiaglogPanel.add(viewProductDisplayTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 260, 200, 30));

        viewProductBatteryLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProductBatteryLabel.setText("Dung lượng pin");
        viewProductDiaglogPanel.add(viewProductBatteryLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, -1, 30));

        viewProductBatteryTextField.setEditable(false);
        viewProductBatteryTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProductBatteryTextField.setEnabled(false);
        viewProductBatteryTextField.setFocusable(false);
        viewProductBatteryTextField.setRequestFocusEnabled(false);
        viewProductBatteryTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewProductBatteryTextFieldActionPerformed(evt);
            }
        });
        viewProductDiaglogPanel.add(viewProductBatteryTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 300, 200, 30));

        viewProductCardLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProductCardLabel.setText("Card đồ hoạ");
        viewProductDiaglogPanel.add(viewProductCardLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, -1, 30));

        viewProductCardTextField.setEditable(false);
        viewProductCardTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProductCardTextField.setEnabled(false);
        viewProductCardTextField.setFocusable(false);
        viewProductCardTextField.setRequestFocusEnabled(false);
        viewProductCardTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewProductCardTextFieldActionPerformed(evt);
            }
        });
        viewProductDiaglogPanel.add(viewProductCardTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 340, 200, 30));

        updateProductDiaglogButton.setBackground(new java.awt.Color(0, 122, 249));
        updateProductDiaglogButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        updateProductDiaglogButton.setForeground(new java.awt.Color(255, 255, 255));
        updateProductDiaglogButton.setText("Cập nhật");
        updateProductDiaglogButton.setBorderPainted(false);
        updateProductDiaglogButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updateProductDiaglogButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        updateProductDiaglogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateProductDiaglogButtonActionPerformed(evt);
            }
        });
        viewProductDiaglogPanel.add(updateProductDiaglogButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 510, 100, 30));

        cancelViewProductDiaglogButton.setBackground(new java.awt.Color(212, 57, 68));
        cancelViewProductDiaglogButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cancelViewProductDiaglogButton.setForeground(new java.awt.Color(255, 255, 255));
        cancelViewProductDiaglogButton.setText("Đóng");
        cancelViewProductDiaglogButton.setBorderPainted(false);
        cancelViewProductDiaglogButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cancelViewProductDiaglogButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cancelViewProductDiaglogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelViewProductDiaglogButtonActionPerformed(evt);
            }
        });
        viewProductDiaglogPanel.add(cancelViewProductDiaglogButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 510, 100, 30));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(44, 43, 196));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("THÔNG TIN SẢN PHẨM");
        viewProductDiaglogPanel.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 440, -1));

        viewProductWeightLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProductWeightLabel.setText("Trọng lượng");
        viewProductDiaglogPanel.add(viewProductWeightLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, -1, 30));

        viewProductWeightTextField.setEditable(false);
        viewProductWeightTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProductWeightTextField.setEnabled(false);
        viewProductWeightTextField.setFocusable(false);
        viewProductWeightTextField.setRequestFocusEnabled(false);
        viewProductWeightTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewProductWeightTextFieldActionPerformed(evt);
            }
        });
        viewProductDiaglogPanel.add(viewProductWeightTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 380, 200, 30));

        viewProductMemoryTextField.setEditable(false);
        viewProductMemoryTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProductMemoryTextField.setEnabled(false);
        viewProductMemoryTextField.setFocusable(false);
        viewProductMemoryTextField.setRequestFocusEnabled(false);
        viewProductMemoryTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewProductMemoryTextFieldActionPerformed(evt);
            }
        });
        viewProductDiaglogPanel.add(viewProductMemoryTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 200, 30));

        viewProductNameTextField.setEditable(false);
        viewProductNameTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProductNameTextField.setEnabled(false);
        viewProductNameTextField.setFocusable(false);
        viewProductNameTextField.setRequestFocusEnabled(false);
        viewProductNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewProductNameTextFieldActionPerformed(evt);
            }
        });
        viewProductDiaglogPanel.add(viewProductNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, 200, 30));

        viewProductIdLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProductIdLabel.setText("Mã sản phẩm");
        viewProductDiaglogPanel.add(viewProductIdLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, 30));

        viewProductIdTextField.setEditable(false);
        viewProductIdTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProductIdTextField.setEnabled(false);
        viewProductIdTextField.setFocusable(false);
        viewProductIdTextField.setRequestFocusEnabled(false);
        viewProductIdTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewProductIdTextFieldActionPerformed(evt);
            }
        });
        viewProductDiaglogPanel.add(viewProductIdTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, 200, 30));

        viewProductWhenCreatedLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProductWhenCreatedLabel.setText("Ngày tạo");
        viewProductDiaglogPanel.add(viewProductWhenCreatedLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 420, -1, 30));

        viewProductWhenCreatedTextField.setEditable(false);
        viewProductWhenCreatedTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProductWhenCreatedTextField.setEnabled(false);
        viewProductWhenCreatedTextField.setFocusable(false);
        viewProductWhenCreatedTextField.setRequestFocusEnabled(false);
        viewProductWhenCreatedTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewProductWhenCreatedTextFieldActionPerformed(evt);
            }
        });
        viewProductDiaglogPanel.add(viewProductWhenCreatedTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, 200, 30));

        viewProductLastUpdatedLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProductLastUpdatedLabel.setText("Sửa đổi lần cuối");
        viewProductDiaglogPanel.add(viewProductLastUpdatedLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 460, -1, 30));

        viewProductLastUpdatedTextField.setEditable(false);
        viewProductLastUpdatedTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProductLastUpdatedTextField.setEnabled(false);
        viewProductLastUpdatedTextField.setFocusable(false);
        viewProductLastUpdatedTextField.setRequestFocusEnabled(false);
        viewProductLastUpdatedTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewProductLastUpdatedTextFieldActionPerformed(evt);
            }
        });
        viewProductDiaglogPanel.add(viewProductLastUpdatedTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 460, 200, 30));

        javax.swing.GroupLayout viewProductDiaglogLayout = new javax.swing.GroupLayout(viewProductDiaglog.getContentPane());
        viewProductDiaglog.getContentPane().setLayout(viewProductDiaglogLayout);
        viewProductDiaglogLayout.setHorizontalGroup(
            viewProductDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewProductDiaglogPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        viewProductDiaglogLayout.setVerticalGroup(
            viewProductDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewProductDiaglogPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        addProductDiaglog.setTitle("Thêm sản phẩm");
        addProductDiaglog.setBackground(new java.awt.Color(255, 255, 255));
        addProductDiaglog.setMinimumSize(new java.awt.Dimension(450, 500));
        addProductDiaglog.setSize(new java.awt.Dimension(450, 500));

        addProductDiaglogPanel.setBackground(new java.awt.Color(255, 255, 255));
        addProductDiaglogPanel.setMinimumSize(new java.awt.Dimension(430, 450));
        addProductDiaglogPanel.setPreferredSize(new java.awt.Dimension(430, 450));
        addProductDiaglogPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        productNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        productNameLabel.setText("Tên sản phẩm");
        addProductDiaglogPanel.add(productNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, 30));

        productProcessorTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        productProcessorTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productProcessorTextFieldActionPerformed(evt);
            }
        });
        addProductDiaglogPanel.add(productProcessorTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 200, 30));

        productProcessorLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        productProcessorLabel.setText("Vi xử lý");
        addProductDiaglogPanel.add(productProcessorLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, 30));

        productMemoryLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        productMemoryLabel.setText("Dung lượng RAM");
        addProductDiaglogPanel.add(productMemoryLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, 30));

        productStorageLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        productStorageLabel.setText("Dung lượng lưu trữ");
        addProductDiaglogPanel.add(productStorageLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, 30));

        productStorageTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        productStorageTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productStorageTextFieldActionPerformed(evt);
            }
        });
        addProductDiaglogPanel.add(productStorageTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 190, 200, 30));

        productDisplayLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        productDisplayLabel.setText("Màn hình");
        addProductDiaglogPanel.add(productDisplayLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, 30));

        productDisplayTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        productDisplayTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productDisplayTextFieldActionPerformed(evt);
            }
        });
        addProductDiaglogPanel.add(productDisplayTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 230, 200, 30));

        productBatteryLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        productBatteryLabel.setText("Dung lượng pin");
        addProductDiaglogPanel.add(productBatteryLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, 30));

        productBatteryTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        productBatteryTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productBatteryTextFieldActionPerformed(evt);
            }
        });
        addProductDiaglogPanel.add(productBatteryTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 270, 200, 30));

        productCardLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        productCardLabel.setText("Card đồ hoạ");
        addProductDiaglogPanel.add(productCardLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, -1, 30));

        productCardTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        productCardTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productCardTextFieldActionPerformed(evt);
            }
        });
        addProductDiaglogPanel.add(productCardTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 310, 200, 30));

        addProductDiaglogButton.setBackground(new java.awt.Color(0, 122, 249));
        addProductDiaglogButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        addProductDiaglogButton.setForeground(new java.awt.Color(255, 255, 255));
        addProductDiaglogButton.setText("Thêm mới");
        addProductDiaglogButton.setBorderPainted(false);
        addProductDiaglogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductDiaglogButtonActionPerformed(evt);
            }
        });
        addProductDiaglogPanel.add(addProductDiaglogButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 410, 110, 30));

        cancelAddProductDiaglogButton.setBackground(new java.awt.Color(212, 57, 68));
        cancelAddProductDiaglogButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cancelAddProductDiaglogButton.setForeground(new java.awt.Color(255, 255, 255));
        cancelAddProductDiaglogButton.setText("Huỷ bỏ");
        cancelAddProductDiaglogButton.setBorderPainted(false);
        cancelAddProductDiaglogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelAddProductDiaglogButtonActionPerformed(evt);
            }
        });
        addProductDiaglogPanel.add(cancelAddProductDiaglogButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 410, 110, 30));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(44, 43, 196));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("THÊM MỚI SẢN PHẨM");
        addProductDiaglogPanel.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 440, -1));

        productWeightLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        productWeightLabel.setText("Trọng lượng");
        addProductDiaglogPanel.add(productWeightLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, -1, 30));

        productWeightTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        productWeightTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productWeightTextFieldActionPerformed(evt);
            }
        });
        addProductDiaglogPanel.add(productWeightTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 200, 30));

        productMemoryTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        productMemoryTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productMemoryTextFieldActionPerformed(evt);
            }
        });
        addProductDiaglogPanel.add(productMemoryTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 200, 30));

        productNameTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        productNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productNameTextFieldActionPerformed(evt);
            }
        });
        addProductDiaglogPanel.add(productNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 200, 30));

        javax.swing.GroupLayout addProductDiaglogLayout = new javax.swing.GroupLayout(addProductDiaglog.getContentPane());
        addProductDiaglog.getContentPane().setLayout(addProductDiaglogLayout);
        addProductDiaglogLayout.setHorizontalGroup(
            addProductDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addProductDiaglogPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
        );
        addProductDiaglogLayout.setVerticalGroup(
            addProductDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addProductDiaglogPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );

        addProviderDiaglog.setTitle("Thêm nhà cung cấp");
        addProviderDiaglog.setBackground(new java.awt.Color(255, 255, 255));
        addProviderDiaglog.setMinimumSize(new java.awt.Dimension(450, 320));
        addProviderDiaglog.setSize(new java.awt.Dimension(450, 320));

        addProductDiaglogPanel1.setBackground(new java.awt.Color(255, 255, 255));
        addProductDiaglogPanel1.setMinimumSize(new java.awt.Dimension(450, 320));
        addProductDiaglogPanel1.setPreferredSize(new java.awt.Dimension(450, 320));
        addProductDiaglogPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        addProviderNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        addProviderNameLabel.setText("Tên nhà cung cấp");
        addProductDiaglogPanel1.add(addProviderNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, 30));

        addProviderPhoneNumberTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        addProviderPhoneNumberTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProviderPhoneNumberTextFieldActionPerformed(evt);
            }
        });
        addProductDiaglogPanel1.add(addProviderPhoneNumberTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 200, 30));

        addProviderPhoneNumberLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        addProviderPhoneNumberLabel.setText("Số điện thoại");
        addProductDiaglogPanel1.add(addProviderPhoneNumberLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, 30));

        addProviderEmailLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        addProviderEmailLabel.setText("Email");
        addProductDiaglogPanel1.add(addProviderEmailLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, 30));

        addProviderAddressLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        addProviderAddressLabel.setText("Địa chỉ");
        addProductDiaglogPanel1.add(addProviderAddressLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, 30));

        addProviderAddressTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        addProviderAddressTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProviderAddressTextFieldActionPerformed(evt);
            }
        });
        addProductDiaglogPanel1.add(addProviderAddressTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 190, 200, 30));

        addProviderDiaglogButton.setBackground(new java.awt.Color(0, 122, 249));
        addProviderDiaglogButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        addProviderDiaglogButton.setForeground(new java.awt.Color(255, 255, 255));
        addProviderDiaglogButton.setText("Thêm mới");
        addProviderDiaglogButton.setBorderPainted(false);
        addProviderDiaglogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProviderDiaglogButtonActionPerformed(evt);
            }
        });
        addProductDiaglogPanel1.add(addProviderDiaglogButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 240, 110, 30));

        cancelAddProviderDiaglogButton.setBackground(new java.awt.Color(212, 57, 68));
        cancelAddProviderDiaglogButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cancelAddProviderDiaglogButton.setForeground(new java.awt.Color(255, 255, 255));
        cancelAddProviderDiaglogButton.setText("Huỷ bỏ");
        cancelAddProviderDiaglogButton.setBorderPainted(false);
        cancelAddProviderDiaglogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelAddProviderDiaglogButtonActionPerformed(evt);
            }
        });
        addProductDiaglogPanel1.add(cancelAddProviderDiaglogButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 240, 110, 30));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(44, 43, 196));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("THÊM MỚI NHÀ CUNG CẤP");
        addProductDiaglogPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 440, -1));

        addProviderEmailTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        addProviderEmailTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProviderEmailTextFieldActionPerformed(evt);
            }
        });
        addProductDiaglogPanel1.add(addProviderEmailTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, 200, 30));

        addProviderNameTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        addProviderNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProviderNameTextFieldActionPerformed(evt);
            }
        });
        addProductDiaglogPanel1.add(addProviderNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 200, 30));

        javax.swing.GroupLayout addProviderDiaglogLayout = new javax.swing.GroupLayout(addProviderDiaglog.getContentPane());
        addProviderDiaglog.getContentPane().setLayout(addProviderDiaglogLayout);
        addProviderDiaglogLayout.setHorizontalGroup(
            addProviderDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addProductDiaglogPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        addProviderDiaglogLayout.setVerticalGroup(
            addProviderDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addProductDiaglogPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        viewProviderDiaglog.setTitle("Thông tin nhà cung cấp");
        viewProviderDiaglog.setBackground(new java.awt.Color(255, 255, 255));
        viewProviderDiaglog.setMinimumSize(new java.awt.Dimension(450, 430));
        viewProviderDiaglog.setSize(new java.awt.Dimension(450, 430));

        viewProviderDiaglogPanel.setBackground(new java.awt.Color(255, 255, 255));
        viewProviderDiaglogPanel.setMinimumSize(new java.awt.Dimension(450, 430));
        viewProviderDiaglogPanel.setPreferredSize(new java.awt.Dimension(450, 430));
        viewProviderDiaglogPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        viewProviderNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProviderNameLabel.setText("Tên nhà cung cấp");
        viewProviderDiaglogPanel.add(viewProviderNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, 30));

        viewProviderPhoneNumberTextField.setEditable(false);
        viewProviderPhoneNumberTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProviderPhoneNumberTextField.setEnabled(false);
        viewProviderPhoneNumberTextField.setFocusable(false);
        viewProviderPhoneNumberTextField.setRequestFocusEnabled(false);
        viewProviderPhoneNumberTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewProviderPhoneNumberTextFieldActionPerformed(evt);
            }
        });
        viewProviderDiaglogPanel.add(viewProviderPhoneNumberTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, 200, 30));

        viewProviderPhoneNumberLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProviderPhoneNumberLabel.setText("Số điện thoại");
        viewProviderDiaglogPanel.add(viewProviderPhoneNumberLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, 30));

        viewProviderEmailLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProviderEmailLabel.setText("Email");
        viewProviderDiaglogPanel.add(viewProviderEmailLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1, 30));

        viewProviderAddressLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProviderAddressLabel.setText("Địa chỉ");
        viewProviderDiaglogPanel.add(viewProviderAddressLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, -1, 30));

        viewProviderAddressTextField.setEditable(false);
        viewProviderAddressTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProviderAddressTextField.setEnabled(false);
        viewProviderAddressTextField.setFocusable(false);
        viewProviderAddressTextField.setRequestFocusEnabled(false);
        viewProviderAddressTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewProviderAddressTextFieldActionPerformed(evt);
            }
        });
        viewProviderDiaglogPanel.add(viewProviderAddressTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 220, 200, 30));

        updateProviderDiaglogButton.setBackground(new java.awt.Color(0, 122, 249));
        updateProviderDiaglogButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        updateProviderDiaglogButton.setForeground(new java.awt.Color(255, 255, 255));
        updateProviderDiaglogButton.setText("Cập nhật");
        updateProviderDiaglogButton.setBorderPainted(false);
        updateProviderDiaglogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateProviderDiaglogButtonActionPerformed(evt);
            }
        });
        viewProviderDiaglogPanel.add(updateProviderDiaglogButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 350, 100, 30));

        cancelViewProviderDiaglogButton.setBackground(new java.awt.Color(212, 57, 68));
        cancelViewProviderDiaglogButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cancelViewProviderDiaglogButton.setForeground(new java.awt.Color(255, 255, 255));
        cancelViewProviderDiaglogButton.setText("Đóng");
        cancelViewProviderDiaglogButton.setBorderPainted(false);
        cancelViewProviderDiaglogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelViewProviderDiaglogButtonActionPerformed(evt);
            }
        });
        viewProviderDiaglogPanel.add(cancelViewProviderDiaglogButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 350, 100, 30));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(44, 43, 196));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("THÔNG TIN NHÀ CUNG CẤP");
        viewProviderDiaglogPanel.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 440, -1));

        viewProviderEmailTextField.setEditable(false);
        viewProviderEmailTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProviderEmailTextField.setEnabled(false);
        viewProviderEmailTextField.setFocusable(false);
        viewProviderEmailTextField.setRequestFocusEnabled(false);
        viewProviderEmailTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewProviderEmailTextFieldActionPerformed(evt);
            }
        });
        viewProviderDiaglogPanel.add(viewProviderEmailTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 200, 30));

        viewProviderNameTextField.setEditable(false);
        viewProviderNameTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProviderNameTextField.setEnabled(false);
        viewProviderNameTextField.setFocusable(false);
        viewProviderNameTextField.setRequestFocusEnabled(false);
        viewProviderNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewProviderNameTextFieldActionPerformed(evt);
            }
        });
        viewProviderDiaglogPanel.add(viewProviderNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, 200, 30));

        viewProviderIdLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProviderIdLabel.setText("Mã nhà cung cấp");
        viewProviderDiaglogPanel.add(viewProviderIdLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, 30));

        viewProviderIdTextField.setEditable(false);
        viewProviderIdTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProviderIdTextField.setEnabled(false);
        viewProviderIdTextField.setFocusable(false);
        viewProviderIdTextField.setRequestFocusEnabled(false);
        viewProviderIdTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewProviderIdTextFieldActionPerformed(evt);
            }
        });
        viewProviderDiaglogPanel.add(viewProviderIdTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, 200, 30));

        viewProductStorageTextField2.setEditable(false);
        viewProductStorageTextField2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProductStorageTextField2.setEnabled(false);
        viewProductStorageTextField2.setFocusable(false);
        viewProductStorageTextField2.setRequestFocusEnabled(false);
        viewProductStorageTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewProductStorageTextField2ActionPerformed(evt);
            }
        });
        viewProviderDiaglogPanel.add(viewProductStorageTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 220, 200, 30));

        viewProviderWhenCreatedTextField.setEditable(false);
        viewProviderWhenCreatedTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProviderWhenCreatedTextField.setEnabled(false);
        viewProviderWhenCreatedTextField.setFocusable(false);
        viewProviderWhenCreatedTextField.setRequestFocusEnabled(false);
        viewProviderWhenCreatedTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewProviderWhenCreatedTextFieldActionPerformed(evt);
            }
        });
        viewProviderDiaglogPanel.add(viewProviderWhenCreatedTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 260, 200, 30));

        viewProviderWhenCreatedLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProviderWhenCreatedLabel.setText("Ngày tạo");
        viewProviderDiaglogPanel.add(viewProviderWhenCreatedLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, 30));

        viewProviderLastUpdatedTextField.setEditable(false);
        viewProviderLastUpdatedTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProviderLastUpdatedTextField.setEnabled(false);
        viewProviderLastUpdatedTextField.setFocusable(false);
        viewProviderLastUpdatedTextField.setRequestFocusEnabled(false);
        viewProviderLastUpdatedTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewProviderLastUpdatedTextFieldActionPerformed(evt);
            }
        });
        viewProviderDiaglogPanel.add(viewProviderLastUpdatedTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 300, 200, 30));

        viewProviderLastUpdatedLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProviderLastUpdatedLabel.setText("Sửa đổi lần cuối");
        viewProviderDiaglogPanel.add(viewProviderLastUpdatedLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, -1, 30));

        javax.swing.GroupLayout viewProviderDiaglogLayout = new javax.swing.GroupLayout(viewProviderDiaglog.getContentPane());
        viewProviderDiaglog.getContentPane().setLayout(viewProviderDiaglogLayout);
        viewProviderDiaglogLayout.setHorizontalGroup(
            viewProviderDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewProviderDiaglogPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        viewProviderDiaglogLayout.setVerticalGroup(
            viewProviderDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewProviderDiaglogPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        editProviderDiaglog.setTitle("Cập nhật nhà cung cấp");
        editProviderDiaglog.setBackground(new java.awt.Color(255, 255, 255));
        editProviderDiaglog.setMinimumSize(new java.awt.Dimension(450, 350));
        editProviderDiaglog.setSize(new java.awt.Dimension(450, 350));

        editProviderDiaglogPanel.setBackground(new java.awt.Color(255, 255, 255));
        editProviderDiaglogPanel.setMinimumSize(new java.awt.Dimension(450, 350));
        editProviderDiaglogPanel.setPreferredSize(new java.awt.Dimension(450, 350));
        editProviderDiaglogPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        editProviderNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProviderNameLabel.setText("Tên nhà cung cấp");
        editProviderDiaglogPanel.add(editProviderNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, 30));

        editProviderPhoneNumberTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProviderPhoneNumberTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProviderPhoneNumberTextFieldActionPerformed(evt);
            }
        });
        editProviderDiaglogPanel.add(editProviderPhoneNumberTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, 200, 30));

        editProviderPhoneNumberLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProviderPhoneNumberLabel.setText("Số điện thoại");
        editProviderDiaglogPanel.add(editProviderPhoneNumberLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, 30));

        editProviderEmailLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProviderEmailLabel.setText("Email");
        editProviderDiaglogPanel.add(editProviderEmailLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1, 30));

        editProviderAddressLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProviderAddressLabel.setText("Địa chỉ");
        editProviderDiaglogPanel.add(editProviderAddressLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, -1, 30));

        editProviderAddressTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProviderAddressTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProviderAddressTextFieldActionPerformed(evt);
            }
        });
        editProviderDiaglogPanel.add(editProviderAddressTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 220, 200, 30));

        editProviderDiaglogButton.setBackground(new java.awt.Color(0, 122, 249));
        editProviderDiaglogButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProviderDiaglogButton.setForeground(new java.awt.Color(255, 255, 255));
        editProviderDiaglogButton.setText("Cập nhật");
        editProviderDiaglogButton.setBorderPainted(false);
        editProviderDiaglogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProviderDiaglogButtonActionPerformed(evt);
            }
        });
        editProviderDiaglogPanel.add(editProviderDiaglogButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, 100, 30));

        cancelEditProviderDiaglogButton.setBackground(new java.awt.Color(212, 57, 68));
        cancelEditProviderDiaglogButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cancelEditProviderDiaglogButton.setForeground(new java.awt.Color(255, 255, 255));
        cancelEditProviderDiaglogButton.setText("Huỷ bỏ");
        cancelEditProviderDiaglogButton.setBorderPainted(false);
        cancelEditProviderDiaglogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelEditProviderDiaglogButtonActionPerformed(evt);
            }
        });
        editProviderDiaglogPanel.add(cancelEditProviderDiaglogButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 270, 100, 30));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(44, 43, 196));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("CẬP NHẬT NHÀ CUNG CẤP");
        editProviderDiaglogPanel.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 440, -1));

        editProviderEmailTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProviderEmailTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProviderEmailTextFieldActionPerformed(evt);
            }
        });
        editProviderDiaglogPanel.add(editProviderEmailTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 200, 30));

        editProviderNameTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProviderNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProviderNameTextFieldActionPerformed(evt);
            }
        });
        editProviderDiaglogPanel.add(editProviderNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, 200, 30));

        editProviderIdLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProviderIdLabel.setText("Mã nhà cung cấp");
        editProviderDiaglogPanel.add(editProviderIdLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, 30));

        editProviderIdTextField.setEditable(false);
        editProviderIdTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProviderIdTextField.setEnabled(false);
        editProviderIdTextField.setFocusable(false);
        editProviderIdTextField.setRequestFocusEnabled(false);
        editProviderIdTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProviderIdTextFieldActionPerformed(evt);
            }
        });
        editProviderDiaglogPanel.add(editProviderIdTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, 200, 30));

        javax.swing.GroupLayout editProviderDiaglogLayout = new javax.swing.GroupLayout(editProviderDiaglog.getContentPane());
        editProviderDiaglog.getContentPane().setLayout(editProviderDiaglogLayout);
        editProviderDiaglogLayout.setHorizontalGroup(
            editProviderDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(editProviderDiaglogPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        editProviderDiaglogLayout.setVerticalGroup(
            editProviderDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(editProviderDiaglogPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        deleteProviderConfirmDiaglog.setTitle("Xác nhận xoá");
        deleteProviderConfirmDiaglog.setMinimumSize(new java.awt.Dimension(420, 230));
        deleteProviderConfirmDiaglog.setResizable(false);
        deleteProviderConfirmDiaglog.setSize(new java.awt.Dimension(420, 230));

        deleteProviderConfirmDiaglogPanel.setBackground(new java.awt.Color(255, 255, 255));
        deleteProviderConfirmDiaglogPanel.setMinimumSize(new java.awt.Dimension(420, 230));
        deleteProviderConfirmDiaglogPanel.setPreferredSize(new java.awt.Dimension(420, 230));
        deleteProviderConfirmDiaglogPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        deleteProviderConfirmDiaglogLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        deleteProviderConfirmDiaglogLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        deleteProviderConfirmDiaglogLabel.setText("Bạn chắc chắn muốn xoá người dùng abcdeskskskskksd");
        deleteProviderConfirmDiaglogLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deleteProviderConfirmDiaglogPanel.add(deleteProviderConfirmDiaglogLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 67, 400, 50));

        confirmDeleteProvider.setBackground(new java.awt.Color(44, 43, 196));
        confirmDeleteProvider.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        confirmDeleteProvider.setForeground(new java.awt.Color(255, 255, 255));
        confirmDeleteProvider.setText("Đồng ý");
        confirmDeleteProvider.setBorderPainted(false);
        confirmDeleteProvider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmDeleteProviderActionPerformed(evt);
            }
        });
        deleteProviderConfirmDiaglogPanel.add(confirmDeleteProvider, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, -1, 30));

        cancelDeleteProvider.setBackground(new java.awt.Color(212, 57, 68));
        cancelDeleteProvider.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cancelDeleteProvider.setForeground(new java.awt.Color(255, 255, 255));
        cancelDeleteProvider.setText("Huỷ bỏ");
        cancelDeleteProvider.setBorderPainted(false);
        cancelDeleteProvider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelDeleteProviderActionPerformed(evt);
            }
        });
        deleteProviderConfirmDiaglogPanel.add(cancelDeleteProvider, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, -1, 30));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 0, 0));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("XÁC NHẬN XOÁ");
        deleteProviderConfirmDiaglogPanel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 29, 408, -1));

        javax.swing.GroupLayout deleteProviderConfirmDiaglogLayout = new javax.swing.GroupLayout(deleteProviderConfirmDiaglog.getContentPane());
        deleteProviderConfirmDiaglog.getContentPane().setLayout(deleteProviderConfirmDiaglogLayout);
        deleteProviderConfirmDiaglogLayout.setHorizontalGroup(
            deleteProviderConfirmDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deleteProviderConfirmDiaglogPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        deleteProviderConfirmDiaglogLayout.setVerticalGroup(
            deleteProviderConfirmDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deleteProviderConfirmDiaglogPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        editImportBillItemDiaglog.setTitle("Thêm người dùng");
        editImportBillItemDiaglog.setBackground(new java.awt.Color(255, 255, 255));
        editImportBillItemDiaglog.setMinimumSize(new java.awt.Dimension(1427, 830));
        editImportBillItemDiaglog.setResizable(false);
        editImportBillItemDiaglog.setSize(new java.awt.Dimension(1427, 830));

        editImportBillItemDiaglogPanel.setBackground(new java.awt.Color(255, 255, 255));
        editImportBillItemDiaglogPanel.setMinimumSize(new java.awt.Dimension(430, 450));
        editImportBillItemDiaglogPanel.setPreferredSize(new java.awt.Dimension(430, 450));
        editImportBillItemDiaglogPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        editImportBillItemQuantityLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editImportBillItemQuantityLabel.setText("Số lượng");
        editImportBillItemDiaglogPanel.add(editImportBillItemQuantityLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, -1, -1));

        editImportBillItemPriceTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editImportBillItemPriceTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editImportBillItemPriceTextFieldActionPerformed(evt);
            }
        });
        editImportBillItemDiaglogPanel.add(editImportBillItemPriceTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 270, 540, 30));

        editImportBillItemPriceLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editImportBillItemPriceLabel.setText("Giá nhập");
        editImportBillItemDiaglogPanel.add(editImportBillItemPriceLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, -1));

        editImportBillItemDiaglogButton.setBackground(new java.awt.Color(0, 122, 249));
        editImportBillItemDiaglogButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editImportBillItemDiaglogButton.setText("Cập nhật");
        editImportBillItemDiaglogButton.setBorderPainted(false);
        editImportBillItemDiaglogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editImportBillItemDiaglogButtonActionPerformed(evt);
            }
        });
        editImportBillItemDiaglogPanel.add(editImportBillItemDiaglogButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 380, -1, -1));

        cancelEditImportBillItemDiaglogButton.setBackground(new java.awt.Color(212, 57, 68));
        cancelEditImportBillItemDiaglogButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cancelEditImportBillItemDiaglogButton.setText("Huỷ bỏ");
        cancelEditImportBillItemDiaglogButton.setBorderPainted(false);
        cancelEditImportBillItemDiaglogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelEditImportBillItemDiaglogButtonActionPerformed(evt);
            }
        });
        editImportBillItemDiaglogPanel.add(cancelEditImportBillItemDiaglogButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 380, -1, -1));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(44, 43, 196));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("CẬP NHẬT MẶT HÀNG NHẬP");
        editImportBillItemDiaglogPanel.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 440, -1));

        editImportBillItemQuantityTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editImportBillItemQuantityTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editImportBillItemQuantityTextFieldActionPerformed(evt);
            }
        });
        editImportBillItemDiaglogPanel.add(editImportBillItemQuantityTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 220, 540, 30));

        editImportBillItemProductNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editImportBillItemProductNameLabel.setText("Tên sản phẩm");
        editImportBillItemDiaglogPanel.add(editImportBillItemProductNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, -1));

        editImportBillItemProductNameTextField.setEditable(false);
        editImportBillItemProductNameTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editImportBillItemProductNameTextField.setEnabled(false);
        editImportBillItemProductNameTextField.setFocusable(false);
        editImportBillItemProductNameTextField.setRequestFocusEnabled(false);
        editImportBillItemProductNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editImportBillItemProductNameTextFieldActionPerformed(evt);
            }
        });
        editImportBillItemDiaglogPanel.add(editImportBillItemProductNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 170, 540, 30));

        editImportBillItemProductIdLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editImportBillItemProductIdLabel.setText("Mã sản phẩm");
        editImportBillItemDiaglogPanel.add(editImportBillItemProductIdLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, -1, -1));

        editImportBillItemProductIdTextField.setEditable(false);
        editImportBillItemProductIdTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editImportBillItemProductIdTextField.setEnabled(false);
        editImportBillItemProductIdTextField.setFocusable(false);
        editImportBillItemProductIdTextField.setRequestFocusEnabled(false);
        editImportBillItemProductIdTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editImportBillItemProductIdTextFieldActionPerformed(evt);
            }
        });
        editImportBillItemDiaglogPanel.add(editImportBillItemProductIdTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, 540, 30));

        editImportBillItemIndexLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editImportBillItemIndexLabel.setText("Số thứ tự");
        editImportBillItemDiaglogPanel.add(editImportBillItemIndexLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        editImportBillItemIndexTextField.setEditable(false);
        editImportBillItemIndexTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editImportBillItemIndexTextField.setEnabled(false);
        editImportBillItemIndexTextField.setFocusable(false);
        editImportBillItemIndexTextField.setRequestFocusEnabled(false);
        editImportBillItemIndexTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editImportBillItemIndexTextFieldActionPerformed(evt);
            }
        });
        editImportBillItemDiaglogPanel.add(editImportBillItemIndexTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 540, 30));

        javax.swing.GroupLayout editImportBillItemDiaglogLayout = new javax.swing.GroupLayout(editImportBillItemDiaglog.getContentPane());
        editImportBillItemDiaglog.getContentPane().setLayout(editImportBillItemDiaglogLayout);
        editImportBillItemDiaglogLayout.setHorizontalGroup(
            editImportBillItemDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editImportBillItemDiaglogLayout.createSequentialGroup()
                .addComponent(editImportBillItemDiaglogPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        editImportBillItemDiaglogLayout.setVerticalGroup(
            editImportBillItemDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(editImportBillItemDiaglogPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );

        deleteImportBillItemDiaglog.setTitle("Thêm người dùng");
        deleteImportBillItemDiaglog.setBackground(new java.awt.Color(255, 255, 255));
        deleteImportBillItemDiaglog.setMinimumSize(new java.awt.Dimension(450, 500));
        deleteImportBillItemDiaglog.setSize(new java.awt.Dimension(450, 500));

        deleteImportBillItemDiaglogPanel.setBackground(new java.awt.Color(255, 255, 255));
        deleteImportBillItemDiaglogPanel.setMinimumSize(new java.awt.Dimension(430, 450));
        deleteImportBillItemDiaglogPanel.setPreferredSize(new java.awt.Dimension(430, 450));
        deleteImportBillItemDiaglogPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        deleteImportBillItemDiaglogButton.setBackground(new java.awt.Color(0, 122, 249));
        deleteImportBillItemDiaglogButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        deleteImportBillItemDiaglogButton.setText("Xác nhận");
        deleteImportBillItemDiaglogButton.setBorderPainted(false);
        deleteImportBillItemDiaglogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteImportBillItemDiaglogButtonActionPerformed(evt);
            }
        });
        deleteImportBillItemDiaglogPanel.add(deleteImportBillItemDiaglogButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 380, -1, -1));

        cancelDeleteImportBillItemDiaglogButton.setBackground(new java.awt.Color(212, 57, 68));
        cancelDeleteImportBillItemDiaglogButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cancelDeleteImportBillItemDiaglogButton.setText("Huỷ bỏ");
        cancelDeleteImportBillItemDiaglogButton.setBorderPainted(false);
        cancelDeleteImportBillItemDiaglogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelDeleteImportBillItemDiaglogButtonActionPerformed(evt);
            }
        });
        deleteImportBillItemDiaglogPanel.add(cancelDeleteImportBillItemDiaglogButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 380, -1, -1));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(44, 43, 196));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("XOÁ MẶT HÀNG NHẬP");
        deleteImportBillItemDiaglogPanel.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 440, -1));

        deleteImportBillItemDiaglogLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        deleteImportBillItemDiaglogLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        deleteImportBillItemDiaglogLabel.setText("Bạn chắc chắn muốn xoá bản ghi này?");
        deleteImportBillItemDiaglogLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deleteImportBillItemDiaglogPanel.add(deleteImportBillItemDiaglogLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, 400, 50));

        javax.swing.GroupLayout deleteImportBillItemDiaglogLayout = new javax.swing.GroupLayout(deleteImportBillItemDiaglog.getContentPane());
        deleteImportBillItemDiaglog.getContentPane().setLayout(deleteImportBillItemDiaglogLayout);
        deleteImportBillItemDiaglogLayout.setHorizontalGroup(
            deleteImportBillItemDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deleteImportBillItemDiaglogLayout.createSequentialGroup()
                .addComponent(deleteImportBillItemDiaglogPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        deleteImportBillItemDiaglogLayout.setVerticalGroup(
            deleteImportBillItemDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deleteImportBillItemDiaglogPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );

        viewImportBillDetailDiaglog.setMinimumSize(new java.awt.Dimension(880, 520));
        viewImportBillDetailDiaglog.setSize(new java.awt.Dimension(880, 520));

        viewImportBillDetailPanel.setBackground(new java.awt.Color(255, 255, 255));
        viewImportBillDetailPanel.setMinimumSize(new java.awt.Dimension(880, 520));
        viewImportBillDetailPanel.setPreferredSize(new java.awt.Dimension(880, 520));

        viewImportBillDetailHeaderPanel.setBackground(new java.awt.Color(44, 43, 196));

        viewImportBillDetailHeaderLabel.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        viewImportBillDetailHeaderLabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        viewImportBillDetailHeaderLabel.setForeground(new java.awt.Color(255, 255, 255));
        viewImportBillDetailHeaderLabel.setText("CHI TIẾT PHIẾU NHẬP");

        javax.swing.GroupLayout viewImportBillDetailHeaderPanelLayout = new javax.swing.GroupLayout(viewImportBillDetailHeaderPanel);
        viewImportBillDetailHeaderPanel.setLayout(viewImportBillDetailHeaderPanelLayout);
        viewImportBillDetailHeaderPanelLayout.setHorizontalGroup(
            viewImportBillDetailHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewImportBillDetailHeaderPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(viewImportBillDetailHeaderLabel)
                .addGap(310, 310, 310))
        );
        viewImportBillDetailHeaderPanelLayout.setVerticalGroup(
            viewImportBillDetailHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewImportBillDetailHeaderPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(viewImportBillDetailHeaderLabel)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        viewImportBillIdLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewImportBillIdLabel.setText("Mã phiếu:");

        viewImportBillProviderNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewImportBillProviderNameLabel.setText("Nhà cung cấp:");

        viewImportBillCreatorLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewImportBillCreatorLabel.setText("Người tạo:");

        viewImportBillWhenCreatedLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewImportBillWhenCreatedLabel.setText("Thời gian tạo:");

        viewImportBillDetailItemsTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        viewImportBillDetailItemsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã máy", "Tên máy", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        viewImportBillDetailItemsTable.setShowGrid(true);
        viewImportBillProductsScrollTable.setViewportView(viewImportBillDetailItemsTable);

        viewImportBillTotalLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        viewImportBillTotalLabel.setText("TỔNG TIỀN:");

        viewImportBillIdValueLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewImportBillIdValueLabel.setText("jLabel9");

        viewImportBillProviderNameValueLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewImportBillProviderNameValueLabel.setText("jLabel9");

        viewImportBillCreatorValueLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewImportBillCreatorValueLabel.setText("jLabel9");

        viewImportBillWhenCreatedValueLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewImportBillWhenCreatedValueLabel.setText("jLabel9");

        viewImportBillTotalValueLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        viewImportBillTotalValueLabel.setForeground(new java.awt.Color(204, 0, 51));
        viewImportBillTotalValueLabel.setText("...đ");

        javax.swing.GroupLayout viewImportBillDetailPanelLayout = new javax.swing.GroupLayout(viewImportBillDetailPanel);
        viewImportBillDetailPanel.setLayout(viewImportBillDetailPanelLayout);
        viewImportBillDetailPanelLayout.setHorizontalGroup(
            viewImportBillDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImportBillDetailHeaderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(viewImportBillDetailPanelLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(viewImportBillDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(viewImportBillDetailPanelLayout.createSequentialGroup()
                        .addComponent(viewImportBillTotalLabel)
                        .addGap(18, 18, 18)
                        .addComponent(viewImportBillTotalValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(viewImportBillDetailPanelLayout.createSequentialGroup()
                        .addGroup(viewImportBillDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(viewImportBillProviderNameLabel)
                            .addComponent(viewImportBillIdLabel))
                        .addGap(18, 18, 18)
                        .addGroup(viewImportBillDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(viewImportBillIdValueLabel)
                            .addComponent(viewImportBillProviderNameValueLabel))
                        .addGap(244, 244, 244)
                        .addGroup(viewImportBillDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(viewImportBillCreatorLabel)
                            .addComponent(viewImportBillWhenCreatedLabel))
                        .addGap(18, 18, 18)
                        .addGroup(viewImportBillDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(viewImportBillWhenCreatedValueLabel)
                            .addComponent(viewImportBillCreatorValueLabel)))
                    .addComponent(viewImportBillProductsScrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 810, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        viewImportBillDetailPanelLayout.setVerticalGroup(
            viewImportBillDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewImportBillDetailPanelLayout.createSequentialGroup()
                .addComponent(viewImportBillDetailHeaderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(viewImportBillDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viewImportBillIdLabel)
                    .addComponent(viewImportBillCreatorLabel)
                    .addComponent(viewImportBillIdValueLabel)
                    .addComponent(viewImportBillCreatorValueLabel))
                .addGap(18, 18, 18)
                .addGroup(viewImportBillDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viewImportBillProviderNameLabel)
                    .addComponent(viewImportBillWhenCreatedLabel)
                    .addComponent(viewImportBillProviderNameValueLabel)
                    .addComponent(viewImportBillWhenCreatedValueLabel))
                .addGap(18, 18, 18)
                .addComponent(viewImportBillProductsScrollTable, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(viewImportBillDetailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viewImportBillTotalLabel)
                    .addComponent(viewImportBillTotalValueLabel))
                .addGap(0, 83, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout viewImportBillDetailDiaglogLayout = new javax.swing.GroupLayout(viewImportBillDetailDiaglog.getContentPane());
        viewImportBillDetailDiaglog.getContentPane().setLayout(viewImportBillDetailDiaglogLayout);
        viewImportBillDetailDiaglogLayout.setHorizontalGroup(
            viewImportBillDetailDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImportBillDetailPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        viewImportBillDetailDiaglogLayout.setVerticalGroup(
            viewImportBillDetailDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viewImportBillDetailPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        deleteImportBillDiaglog.setTitle("Thêm người dùng");
        deleteImportBillDiaglog.setBackground(new java.awt.Color(255, 255, 255));
        deleteImportBillDiaglog.setMinimumSize(new java.awt.Dimension(450, 500));
        deleteImportBillDiaglog.setSize(new java.awt.Dimension(450, 500));

        deleteImportBillPanel.setBackground(new java.awt.Color(255, 255, 255));
        deleteImportBillPanel.setMinimumSize(new java.awt.Dimension(430, 450));
        deleteImportBillPanel.setPreferredSize(new java.awt.Dimension(430, 450));
        deleteImportBillPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        deleteImportBillDiaglogButton.setBackground(new java.awt.Color(0, 122, 249));
        deleteImportBillDiaglogButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        deleteImportBillDiaglogButton.setText("Xác nhận");
        deleteImportBillDiaglogButton.setBorderPainted(false);
        deleteImportBillDiaglogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteImportBillDiaglogButtonActionPerformed(evt);
            }
        });
        deleteImportBillPanel.add(deleteImportBillDiaglogButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 380, -1, -1));

        cancelDeleteImportBillDiaglogButton.setBackground(new java.awt.Color(212, 57, 68));
        cancelDeleteImportBillDiaglogButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cancelDeleteImportBillDiaglogButton.setText("Huỷ bỏ");
        cancelDeleteImportBillDiaglogButton.setBorderPainted(false);
        cancelDeleteImportBillDiaglogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelDeleteImportBillDiaglogButtonActionPerformed(evt);
            }
        });
        deleteImportBillPanel.add(cancelDeleteImportBillDiaglogButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 380, -1, -1));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(44, 43, 196));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("XOÁ PHIẾU NHẬP HÀNG");
        deleteImportBillPanel.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 440, -1));

        deleteImportBillDiaglogLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        deleteImportBillDiaglogLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        deleteImportBillDiaglogLabel.setText("Bạn có chắc chắn muốn xoá phiếu nhập này?");
        deleteImportBillDiaglogLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deleteImportBillPanel.add(deleteImportBillDiaglogLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, 400, 50));

        javax.swing.GroupLayout deleteImportBillDiaglogLayout = new javax.swing.GroupLayout(deleteImportBillDiaglog.getContentPane());
        deleteImportBillDiaglog.getContentPane().setLayout(deleteImportBillDiaglogLayout);
        deleteImportBillDiaglogLayout.setHorizontalGroup(
            deleteImportBillDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deleteImportBillDiaglogLayout.createSequentialGroup()
                .addComponent(deleteImportBillPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        deleteImportBillDiaglogLayout.setVerticalGroup(
            deleteImportBillDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deleteImportBillPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );

        deleteExportBillItemDiaglog.setTitle("Thêm người dùng");
        deleteExportBillItemDiaglog.setBackground(new java.awt.Color(255, 255, 255));
        deleteExportBillItemDiaglog.setMinimumSize(new java.awt.Dimension(450, 500));
        deleteExportBillItemDiaglog.setSize(new java.awt.Dimension(450, 500));

        deleteExportBillItemPanel.setBackground(new java.awt.Color(255, 255, 255));
        deleteExportBillItemPanel.setMinimumSize(new java.awt.Dimension(430, 450));
        deleteExportBillItemPanel.setPreferredSize(new java.awt.Dimension(430, 450));
        deleteExportBillItemPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        deleteExportBillItemDiaglogButton.setBackground(new java.awt.Color(0, 122, 249));
        deleteExportBillItemDiaglogButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        deleteExportBillItemDiaglogButton.setText("Xác nhận");
        deleteExportBillItemDiaglogButton.setBorderPainted(false);
        deleteExportBillItemDiaglogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteExportBillItemDiaglogButtonActionPerformed(evt);
            }
        });
        deleteExportBillItemPanel.add(deleteExportBillItemDiaglogButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 380, -1, -1));

        cancelDeleteExportBillItemDiaglogButton.setBackground(new java.awt.Color(212, 57, 68));
        cancelDeleteExportBillItemDiaglogButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cancelDeleteExportBillItemDiaglogButton.setText("Huỷ bỏ");
        cancelDeleteExportBillItemDiaglogButton.setBorderPainted(false);
        cancelDeleteExportBillItemDiaglogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelDeleteExportBillItemDiaglogButtonActionPerformed(evt);
            }
        });
        deleteExportBillItemPanel.add(cancelDeleteExportBillItemDiaglogButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 380, -1, -1));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(44, 43, 196));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("XOÁ MẶT HÀNG TRONG PHIẾU XUẤT");
        deleteExportBillItemPanel.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 440, -1));

        deleteExportBillItemDiaglogLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        deleteExportBillItemDiaglogLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        deleteExportBillItemDiaglogLabel.setText("Bạn có chắc chắn muốn xoá mặt hàng này?");
        deleteExportBillItemDiaglogLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deleteExportBillItemPanel.add(deleteExportBillItemDiaglogLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, 400, 50));

        javax.swing.GroupLayout deleteExportBillItemDiaglogLayout = new javax.swing.GroupLayout(deleteExportBillItemDiaglog.getContentPane());
        deleteExportBillItemDiaglog.getContentPane().setLayout(deleteExportBillItemDiaglogLayout);
        deleteExportBillItemDiaglogLayout.setHorizontalGroup(
            deleteExportBillItemDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(deleteExportBillItemDiaglogLayout.createSequentialGroup()
                .addComponent(deleteExportBillItemPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        deleteExportBillItemDiaglogLayout.setVerticalGroup(
            deleteExportBillItemDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deleteExportBillItemPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );

        editExportBillItemDiaglog.setTitle("Thêm người dùng");
        editExportBillItemDiaglog.setBackground(new java.awt.Color(255, 255, 255));
        editExportBillItemDiaglog.setMinimumSize(new java.awt.Dimension(733, 500));
        editExportBillItemDiaglog.setResizable(false);
        editExportBillItemDiaglog.setSize(new java.awt.Dimension(733, 500));

        editExportBillItemDiaglogPanel.setBackground(new java.awt.Color(255, 255, 255));
        editExportBillItemDiaglogPanel.setMinimumSize(new java.awt.Dimension(430, 450));
        editExportBillItemDiaglogPanel.setPreferredSize(new java.awt.Dimension(430, 450));
        editExportBillItemDiaglogPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        editExportBillItemQuantityLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editExportBillItemQuantityLabel.setText("Số lượng");
        editExportBillItemDiaglogPanel.add(editExportBillItemQuantityLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, -1));

        editExportBillItemPriceTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editExportBillItemPriceTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editExportBillItemPriceTextFieldActionPerformed(evt);
            }
        });
        editExportBillItemDiaglogPanel.add(editExportBillItemPriceTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 320, 540, 30));

        editExportBillItemPriceLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editExportBillItemPriceLabel.setText("Giá xuất");
        editExportBillItemDiaglogPanel.add(editExportBillItemPriceLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, -1, -1));

        editExportBillItemDiaglogButton.setBackground(new java.awt.Color(0, 122, 249));
        editExportBillItemDiaglogButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editExportBillItemDiaglogButton.setText("Cập nhật");
        editExportBillItemDiaglogButton.setBorderPainted(false);
        editExportBillItemDiaglogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editExportBillItemDiaglogButtonActionPerformed(evt);
            }
        });
        editExportBillItemDiaglogPanel.add(editExportBillItemDiaglogButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 380, -1, -1));

        cancelEditExportBillItemDiaglogButton.setBackground(new java.awt.Color(212, 57, 68));
        cancelEditExportBillItemDiaglogButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cancelEditExportBillItemDiaglogButton.setText("Huỷ bỏ");
        cancelEditExportBillItemDiaglogButton.setBorderPainted(false);
        cancelEditExportBillItemDiaglogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelEditExportBillItemDiaglogButtonActionPerformed(evt);
            }
        });
        editExportBillItemDiaglogPanel.add(cancelEditExportBillItemDiaglogButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 380, -1, -1));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(44, 43, 196));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("CẬP NHẬT MẶT HÀNG XUẤT");
        editExportBillItemDiaglogPanel.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 440, -1));

        editExportBillItemQuantityTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editExportBillItemQuantityTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editExportBillItemQuantityTextFieldActionPerformed(evt);
            }
        });
        editExportBillItemDiaglogPanel.add(editExportBillItemQuantityTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 270, 540, 30));

        editExportBillItemProductNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editExportBillItemProductNameLabel.setText("Tên sản phẩm");
        editExportBillItemDiaglogPanel.add(editExportBillItemProductNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, -1, -1));

        editExportBillItemProductNameTextField.setEditable(false);
        editExportBillItemProductNameTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editExportBillItemProductNameTextField.setEnabled(false);
        editExportBillItemProductNameTextField.setFocusable(false);
        editExportBillItemProductNameTextField.setRequestFocusEnabled(false);
        editExportBillItemProductNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editExportBillItemProductNameTextFieldActionPerformed(evt);
            }
        });
        editExportBillItemDiaglogPanel.add(editExportBillItemProductNameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 220, 540, 30));

        editExportBillItemProductIdLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editExportBillItemProductIdLabel.setText("Mã sản phẩm");
        editExportBillItemDiaglogPanel.add(editExportBillItemProductIdLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, -1));

        editExportBillItemProductIdTextField.setEditable(false);
        editExportBillItemProductIdTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editExportBillItemProductIdTextField.setEnabled(false);
        editExportBillItemProductIdTextField.setFocusable(false);
        editExportBillItemProductIdTextField.setRequestFocusEnabled(false);
        editExportBillItemProductIdTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editExportBillItemProductIdTextFieldActionPerformed(evt);
            }
        });
        editExportBillItemDiaglogPanel.add(editExportBillItemProductIdTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 170, 540, 30));

        editExportBillItemIndexLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editExportBillItemIndexLabel.setText("Số thứ tự");
        editExportBillItemDiaglogPanel.add(editExportBillItemIndexLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        editExportBillItemIndexTextField.setEditable(false);
        editExportBillItemIndexTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editExportBillItemIndexTextField.setEnabled(false);
        editExportBillItemIndexTextField.setFocusable(false);
        editExportBillItemIndexTextField.setRequestFocusEnabled(false);
        editExportBillItemIndexTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editExportBillItemIndexTextFieldActionPerformed(evt);
            }
        });
        editExportBillItemDiaglogPanel.add(editExportBillItemIndexTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 540, 30));

        editExportBillItemInventoryIdLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editExportBillItemInventoryIdLabel.setText("Mã kho");
        editExportBillItemDiaglogPanel.add(editExportBillItemInventoryIdLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, -1, -1));

        editExportBillItemInventoryIdTextField.setEditable(false);
        editExportBillItemInventoryIdTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editExportBillItemInventoryIdTextField.setEnabled(false);
        editExportBillItemInventoryIdTextField.setFocusable(false);
        editExportBillItemInventoryIdTextField.setRequestFocusEnabled(false);
        editExportBillItemInventoryIdTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editExportBillItemInventoryIdTextFieldActionPerformed(evt);
            }
        });
        editExportBillItemDiaglogPanel.add(editExportBillItemInventoryIdTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 120, 540, 30));

        javax.swing.GroupLayout editExportBillItemDiaglogLayout = new javax.swing.GroupLayout(editExportBillItemDiaglog.getContentPane());
        editExportBillItemDiaglog.getContentPane().setLayout(editExportBillItemDiaglogLayout);
        editExportBillItemDiaglogLayout.setHorizontalGroup(
            editExportBillItemDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editExportBillItemDiaglogLayout.createSequentialGroup()
                .addComponent(editExportBillItemDiaglogPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        editExportBillItemDiaglogLayout.setVerticalGroup(
            editExportBillItemDiaglogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(editExportBillItemDiaglogPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );

        editExportBillItemDiaglog.getAccessibleContext().setAccessibleName("Sửa phiếu xuất");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1400, 830));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sidebarPanel.setBackground(new java.awt.Color(44, 43, 196));
        sidebarPanel.setPreferredSize(new java.awt.Dimension(256, 800));
        sidebarPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        loginedUsername.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        loginedUsername.setForeground(new java.awt.Color(255, 255, 255));
        loginedUsername.setText("Admin");
        sidebarPanel.add(loginedUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 211, 100));

        productTab.setBackground(new java.awt.Color(44, 43, 196));
        productTab.setForeground(new java.awt.Color(255, 255, 255));
        productTab.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        productTab.setPreferredSize(new java.awt.Dimension(256, 50));
        productTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productTabMouseClicked(evt);
            }
        });

        productLabel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        productLabel.setForeground(new java.awt.Color(255, 255, 255));
        productLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/product.png"))); // NOI18N
        productLabel.setText("  SẢN PHẨM");
        productLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout productTabLayout = new javax.swing.GroupLayout(productTab);
        productTab.setLayout(productTabLayout);
        productTabLayout.setHorizontalGroup(
            productTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productTabLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(productLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );
        productTabLayout.setVerticalGroup(
            productTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(productLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

        sidebarPanel.add(productTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 256, 50));

        providerTab.setBackground(new java.awt.Color(44, 43, 196));
        providerTab.setForeground(new java.awt.Color(255, 255, 255));
        providerTab.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        providerTab.setPreferredSize(new java.awt.Dimension(256, 50));
        providerTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                providerTabMouseClicked(evt);
            }
        });

        providerLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        providerLabel.setForeground(new java.awt.Color(255, 255, 255));
        providerLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/teamwork.png"))); // NOI18N
        providerLabel.setText("  NHÀ CUNG CẤP");
        providerLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        providerLabel.setPreferredSize(new java.awt.Dimension(256, 50));
        providerLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                providerLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout providerTabLayout = new javax.swing.GroupLayout(providerTab);
        providerTab.setLayout(providerTabLayout);
        providerTabLayout.setHorizontalGroup(
            providerTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(providerTabLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(providerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );
        providerTabLayout.setVerticalGroup(
            providerTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(providerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        sidebarPanel.add(providerTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 256, 50));

        importProductTab.setBackground(new java.awt.Color(44, 43, 196));
        importProductTab.setForeground(new java.awt.Color(255, 255, 255));
        importProductTab.setPreferredSize(new java.awt.Dimension(256, 50));
        importProductTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                importProductTabMouseClicked(evt);
            }
        });

        importProductLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        importProductLabel.setForeground(new java.awt.Color(255, 255, 255));
        importProductLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/import.png"))); // NOI18N
        importProductLabel.setText("  NHẬP HÀNG");
        importProductLabel.setPreferredSize(new java.awt.Dimension(256, 50));
        importProductLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                importProductLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout importProductTabLayout = new javax.swing.GroupLayout(importProductTab);
        importProductTab.setLayout(importProductTabLayout);
        importProductTabLayout.setHorizontalGroup(
            importProductTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(importProductTabLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(importProductLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );
        importProductTabLayout.setVerticalGroup(
            importProductTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(importProductTabLayout.createSequentialGroup()
                .addComponent(importProductLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        sidebarPanel.add(importProductTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 256, 50));

        importBillTab.setBackground(new java.awt.Color(44, 43, 196));
        importBillTab.setForeground(new java.awt.Color(255, 255, 255));
        importBillTab.setPreferredSize(new java.awt.Dimension(256, 50));
        importBillTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                importBillTabMouseClicked(evt);
            }
        });

        importBillLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        importBillLabel.setForeground(new java.awt.Color(255, 255, 255));
        importBillLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/contract.png"))); // NOI18N
        importBillLabel.setText("  PHIẾU NHẬP");
        importBillLabel.setPreferredSize(new java.awt.Dimension(256, 50));
        importBillLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                importBillLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout importBillTabLayout = new javax.swing.GroupLayout(importBillTab);
        importBillTab.setLayout(importBillTabLayout);
        importBillTabLayout.setHorizontalGroup(
            importBillTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(importBillTabLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(importBillLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(72, Short.MAX_VALUE))
        );
        importBillTabLayout.setVerticalGroup(
            importBillTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(importBillLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        sidebarPanel.add(importBillTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 256, 50));

        exportProductTab.setBackground(new java.awt.Color(44, 43, 196));
        exportProductTab.setForeground(new java.awt.Color(255, 255, 255));
        exportProductTab.setPreferredSize(new java.awt.Dimension(256, 50));
        exportProductTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exportProductTabMouseClicked(evt);
            }
        });

        exportProductLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        exportProductLabel.setForeground(new java.awt.Color(255, 255, 255));
        exportProductLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/export.png"))); // NOI18N
        exportProductLabel.setText("  XUẤT HÀNG");
        exportProductLabel.setPreferredSize(new java.awt.Dimension(256, 50));
        exportProductLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exportProductLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout exportProductTabLayout = new javax.swing.GroupLayout(exportProductTab);
        exportProductTab.setLayout(exportProductTabLayout);
        exportProductTabLayout.setHorizontalGroup(
            exportProductTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exportProductTabLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(exportProductLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(73, Short.MAX_VALUE))
        );
        exportProductTabLayout.setVerticalGroup(
            exportProductTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exportProductTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(exportProductLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

        sidebarPanel.add(exportProductTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 256, 50));

        exportBillTab.setBackground(new java.awt.Color(44, 43, 196));
        exportBillTab.setForeground(new java.awt.Color(255, 255, 255));
        exportBillTab.setPreferredSize(new java.awt.Dimension(256, 50));
        exportBillTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exportBillTabMouseClicked(evt);
            }
        });

        exportBillLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        exportBillLabel.setForeground(new java.awt.Color(255, 255, 255));
        exportBillLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/checklist.png"))); // NOI18N
        exportBillLabel.setText("  PHIẾU XUẤT");
        exportBillLabel.setPreferredSize(new java.awt.Dimension(256, 50));
        exportBillLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exportBillLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout exportBillTabLayout = new javax.swing.GroupLayout(exportBillTab);
        exportBillTab.setLayout(exportBillTabLayout);
        exportBillTabLayout.setHorizontalGroup(
            exportBillTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exportBillTabLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(exportBillLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );
        exportBillTabLayout.setVerticalGroup(
            exportBillTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, exportBillTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(exportBillLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

        sidebarPanel.add(exportBillTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 256, 50));

        inStockTab.setBackground(new java.awt.Color(44, 43, 196));
        inStockTab.setForeground(new java.awt.Color(255, 255, 255));
        inStockTab.setPreferredSize(new java.awt.Dimension(256, 50));
        inStockTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inStockTabMouseClicked(evt);
            }
        });

        inStockLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        inStockLabel.setForeground(new java.awt.Color(255, 255, 255));
        inStockLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/inventory.png"))); // NOI18N
        inStockLabel.setText("  TỒN KHO");
        inStockLabel.setPreferredSize(new java.awt.Dimension(256, 50));
        inStockLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inStockLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout inStockTabLayout = new javax.swing.GroupLayout(inStockTab);
        inStockTab.setLayout(inStockTabLayout);
        inStockTabLayout.setHorizontalGroup(
            inStockTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inStockTabLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(inStockLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );
        inStockTabLayout.setVerticalGroup(
            inStockTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(inStockLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        sidebarPanel.add(inStockTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, 256, 50));

        userTab.setBackground(new java.awt.Color(44, 43, 196));
        userTab.setForeground(new java.awt.Color(255, 255, 255));
        userTab.setPreferredSize(new java.awt.Dimension(256, 50));
        userTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userTabMouseClicked(evt);
            }
        });

        userLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        userLabel.setForeground(new java.awt.Color(255, 255, 255));
        userLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/account.png"))); // NOI18N
        userLabel.setText("  TÀI KHOẢN");
        userLabel.setPreferredSize(new java.awt.Dimension(256, 50));
        userLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout userTabLayout = new javax.swing.GroupLayout(userTab);
        userTab.setLayout(userTabLayout);
        userTabLayout.setHorizontalGroup(
            userTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userTabLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );
        userTabLayout.setVerticalGroup(
            userTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(userLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

        sidebarPanel.add(userTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 620, 256, 50));

        statisticsTab.setBackground(new java.awt.Color(44, 43, 196));
        statisticsTab.setForeground(new java.awt.Color(255, 255, 255));
        statisticsTab.setPreferredSize(new java.awt.Dimension(256, 50));
        statisticsTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                statisticsTabMouseClicked(evt);
            }
        });

        statisticsLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        statisticsLabel.setForeground(new java.awt.Color(255, 255, 255));
        statisticsLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/trend.png"))); // NOI18N
        statisticsLabel.setText("  THỐNG KÊ");
        statisticsLabel.setPreferredSize(new java.awt.Dimension(256, 50));
        statisticsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                statisticsLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout statisticsTabLayout = new javax.swing.GroupLayout(statisticsTab);
        statisticsTab.setLayout(statisticsTabLayout);
        statisticsTabLayout.setHorizontalGroup(
            statisticsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statisticsTabLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(statisticsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );
        statisticsTabLayout.setVerticalGroup(
            statisticsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statisticsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        sidebarPanel.add(statisticsTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 530, 256, 50));

        updateInfoTab.setBackground(new java.awt.Color(44, 43, 196));
        updateInfoTab.setForeground(new java.awt.Color(255, 255, 255));
        updateInfoTab.setPreferredSize(new java.awt.Dimension(256, 50));
        updateInfoTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateInfoTabMouseClicked(evt);
            }
        });

        updateInfoLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        updateInfoLabel.setForeground(new java.awt.Color(255, 255, 255));
        updateInfoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/exchange.png"))); // NOI18N
        updateInfoLabel.setText("  ĐỔI THÔNG TIN");
        updateInfoLabel.setPreferredSize(new java.awt.Dimension(256, 50));
        updateInfoLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateInfoLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout updateInfoTabLayout = new javax.swing.GroupLayout(updateInfoTab);
        updateInfoTab.setLayout(updateInfoTabLayout);
        updateInfoTabLayout.setHorizontalGroup(
            updateInfoTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updateInfoTabLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(updateInfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        updateInfoTabLayout.setVerticalGroup(
            updateInfoTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(updateInfoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        sidebarPanel.add(updateInfoTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 670, 256, 50));

        logoutTab.setBackground(new java.awt.Color(44, 43, 196));
        logoutTab.setForeground(new java.awt.Color(255, 255, 255));
        logoutTab.setPreferredSize(new java.awt.Dimension(240, 40));
        logoutTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutTabMouseClicked(evt);
            }
        });

        logoutLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        logoutLabel.setForeground(new java.awt.Color(255, 255, 255));
        logoutLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logout.png"))); // NOI18N
        logoutLabel.setText(" ĐĂNG XUẤT");
        logoutLabel.setPreferredSize(new java.awt.Dimension(240, 40));
        logoutLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout logoutTabLayout = new javax.swing.GroupLayout(logoutTab);
        logoutTab.setLayout(logoutTabLayout);
        logoutTabLayout.setHorizontalGroup(
            logoutTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logoutTabLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(logoutLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );
        logoutTabLayout.setVerticalGroup(
            logoutTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, logoutTabLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logoutLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        sidebarPanel.add(logoutTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 720, 256, 50));

        jSeparator4.setForeground(new java.awt.Color(255, 255, 255));
        sidebarPanel.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 260, -1));

        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));
        sidebarPanel.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 460, 260, -1));

        jSeparator5.setForeground(new java.awt.Color(255, 255, 255));
        sidebarPanel.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, 256, -1));

        getContentPane().add(sidebarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 840));

        productPanel.setBackground(new java.awt.Color(255, 255, 255));
        productPanel.setPreferredSize(new java.awt.Dimension(1170, 830));

        productsTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        productsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        productsScrollPanel.setViewportView(productsTable);

        searchProductsPanel.setBackground(new java.awt.Color(255, 255, 255));
        searchProductsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        searchProductsTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        searchProductsTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchProductsTextFieldKeyReleased(evt);
            }
        });

        searchProductsButton.setBackground(new java.awt.Color(65, 120, 190));
        searchProductsButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        searchProductsButton.setForeground(new java.awt.Color(255, 255, 255));
        searchProductsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        searchProductsButton.setText("Làm mới");
        searchProductsButton.setBorderPainted(false);
        searchProductsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searchProductsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchProductsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout searchProductsPanelLayout = new javax.swing.GroupLayout(searchProductsPanel);
        searchProductsPanel.setLayout(searchProductsPanelLayout);
        searchProductsPanelLayout.setHorizontalGroup(
            searchProductsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchProductsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchProductsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchProductsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        searchProductsPanelLayout.setVerticalGroup(
            searchProductsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchProductsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchProductsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchProductsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchProductsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        productFunctionPanel.setBackground(new java.awt.Color(255, 255, 255));
        productFunctionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chức năng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N
        productFunctionPanel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        importProductsFromExcelButton.setBackground(new java.awt.Color(1, 169, 84));
        importProductsFromExcelButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        importProductsFromExcelButton.setForeground(new java.awt.Color(255, 255, 255));
        importProductsFromExcelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/sheet.png"))); // NOI18N
        importProductsFromExcelButton.setText("Nhập Excel");
        importProductsFromExcelButton.setBorderPainted(false);
        importProductsFromExcelButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        importProductsFromExcelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importProductsFromExcelButtonActionPerformed(evt);
            }
        });

        exportProductsToExcelButton.setBackground(new java.awt.Color(0, 155, 110));
        exportProductsToExcelButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        exportProductsToExcelButton.setForeground(new java.awt.Color(255, 255, 255));
        exportProductsToExcelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/sheet.png"))); // NOI18N
        exportProductsToExcelButton.setText("Xuất Excel");
        exportProductsToExcelButton.setBorderPainted(false);
        exportProductsToExcelButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exportProductsToExcelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportProductsToExcelButtonActionPerformed(evt);
            }
        });

        editProductButton.setBackground(new java.awt.Color(255, 193, 7));
        editProductButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProductButton.setForeground(new java.awt.Color(255, 255, 255));
        editProductButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit_icon.png"))); // NOI18N
        editProductButton.setText("Sửa");
        editProductButton.setBorderPainted(false);
        editProductButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editProductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProductButtonActionPerformed(evt);
            }
        });

        addProductButton.setBackground(new java.awt.Color(36, 169, 65));
        addProductButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        addProductButton.setForeground(new java.awt.Color(255, 255, 255));
        addProductButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add_circle_icon.png"))); // NOI18N
        addProductButton.setText("Thêm");
        addProductButton.setBorderPainted(false);
        addProductButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addProductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductButtonActionPerformed(evt);
            }
        });

        deleteProductButton.setBackground(new java.awt.Color(212, 57, 68));
        deleteProductButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        deleteProductButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteProductButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/remove_icon.png"))); // NOI18N
        deleteProductButton.setText("Xoá");
        deleteProductButton.setBorderPainted(false);
        deleteProductButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteProductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteProductButtonActionPerformed(evt);
            }
        });

        viewProductButton.setBackground(new java.awt.Color(13, 110, 253));
        viewProductButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProductButton.setForeground(new java.awt.Color(255, 255, 255));
        viewProductButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/show.png"))); // NOI18N
        viewProductButton.setText("Xem");
        viewProductButton.setBorderPainted(false);
        viewProductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewProductButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout productFunctionPanelLayout = new javax.swing.GroupLayout(productFunctionPanel);
        productFunctionPanel.setLayout(productFunctionPanelLayout);
        productFunctionPanelLayout.setHorizontalGroup(
            productFunctionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productFunctionPanelLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(viewProductButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addProductButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editProductButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteProductButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(importProductsFromExcelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exportProductsToExcelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        productFunctionPanelLayout.setVerticalGroup(
            productFunctionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productFunctionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(productFunctionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, productFunctionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(importProductsFromExcelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(exportProductsToExcelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, productFunctionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(addProductButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(editProductButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(viewProductButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(deleteProductButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
            .addGroup(productFunctionPanelLayout.createSequentialGroup()
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout productPanelLayout = new javax.swing.GroupLayout(productPanel);
        productPanel.setLayout(productPanelLayout);
        productPanelLayout.setHorizontalGroup(
            productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(productsScrollPanel)
                    .addGroup(productPanelLayout.createSequentialGroup()
                        .addComponent(productFunctionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchProductsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        productPanelLayout.setVerticalGroup(
            productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(productPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(productFunctionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchProductsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(productsScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        getContentPane().add(productPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 1170, 830));

        providerPanel.setBackground(new java.awt.Color(255, 255, 255));
        providerPanel.setPreferredSize(new java.awt.Dimension(1170, 830));

        providerFunctionPanel.setBackground(new java.awt.Color(255, 255, 255));
        providerFunctionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chức năng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N
        providerFunctionPanel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);

        importProvidersFromExcelButton.setBackground(new java.awt.Color(1, 169, 84));
        importProvidersFromExcelButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        importProvidersFromExcelButton.setForeground(new java.awt.Color(255, 255, 255));
        importProvidersFromExcelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/sheet.png"))); // NOI18N
        importProvidersFromExcelButton.setText("Nhập Excel");
        importProvidersFromExcelButton.setBorderPainted(false);
        importProvidersFromExcelButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        importProvidersFromExcelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importProvidersFromExcelButtonActionPerformed(evt);
            }
        });

        exportProvidersToExcelButton.setBackground(new java.awt.Color(0, 155, 110));
        exportProvidersToExcelButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        exportProvidersToExcelButton.setForeground(new java.awt.Color(255, 255, 255));
        exportProvidersToExcelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/sheet.png"))); // NOI18N
        exportProvidersToExcelButton.setText("Xuất Excel");
        exportProvidersToExcelButton.setBorderPainted(false);
        exportProvidersToExcelButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exportProvidersToExcelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportProvidersToExcelButtonActionPerformed(evt);
            }
        });

        editProviderButton.setBackground(new java.awt.Color(255, 193, 7));
        editProviderButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editProviderButton.setForeground(new java.awt.Color(255, 255, 255));
        editProviderButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit_icon.png"))); // NOI18N
        editProviderButton.setText("Sửa");
        editProviderButton.setBorderPainted(false);
        editProviderButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editProviderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProviderButtonActionPerformed(evt);
            }
        });

        addProviderButton.setBackground(new java.awt.Color(36, 169, 65));
        addProviderButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        addProviderButton.setForeground(new java.awt.Color(255, 255, 255));
        addProviderButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add_circle_icon.png"))); // NOI18N
        addProviderButton.setText("Thêm");
        addProviderButton.setBorderPainted(false);
        addProviderButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addProviderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProviderButtonActionPerformed(evt);
            }
        });

        deleteProviderButton.setBackground(new java.awt.Color(212, 57, 68));
        deleteProviderButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        deleteProviderButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteProviderButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/remove_icon.png"))); // NOI18N
        deleteProviderButton.setText("Xoá");
        deleteProviderButton.setBorderPainted(false);
        deleteProviderButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteProviderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteProviderButtonActionPerformed(evt);
            }
        });

        viewProviderButton.setBackground(new java.awt.Color(13, 110, 253));
        viewProviderButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewProviderButton.setForeground(new java.awt.Color(255, 255, 255));
        viewProviderButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/show.png"))); // NOI18N
        viewProviderButton.setText("Xem");
        viewProviderButton.setBorderPainted(false);
        viewProviderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewProviderButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout providerFunctionPanelLayout = new javax.swing.GroupLayout(providerFunctionPanel);
        providerFunctionPanel.setLayout(providerFunctionPanelLayout);
        providerFunctionPanelLayout.setHorizontalGroup(
            providerFunctionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(providerFunctionPanelLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(viewProviderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addProviderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editProviderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteProviderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(importProvidersFromExcelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exportProvidersToExcelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        providerFunctionPanelLayout.setVerticalGroup(
            providerFunctionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(providerFunctionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(providerFunctionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, providerFunctionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(importProvidersFromExcelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(exportProvidersToExcelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, providerFunctionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(addProviderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(editProviderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(viewProviderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(deleteProviderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        searchProvidersPanel.setBackground(new java.awt.Color(255, 255, 255));
        searchProvidersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        searchProvidersTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        searchProvidersTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchProvidersTextFieldKeyReleased(evt);
            }
        });

        resetSearchProvidersButton.setBackground(new java.awt.Color(65, 120, 190));
        resetSearchProvidersButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        resetSearchProvidersButton.setForeground(new java.awt.Color(255, 255, 255));
        resetSearchProvidersButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        resetSearchProvidersButton.setText("Làm mới");
        resetSearchProvidersButton.setBorderPainted(false);
        resetSearchProvidersButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        resetSearchProvidersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetSearchProvidersButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout searchProvidersPanelLayout = new javax.swing.GroupLayout(searchProvidersPanel);
        searchProvidersPanel.setLayout(searchProvidersPanelLayout);
        searchProvidersPanelLayout.setHorizontalGroup(
            searchProvidersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchProvidersPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchProvidersTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resetSearchProvidersButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        searchProvidersPanelLayout.setVerticalGroup(
            searchProvidersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchProvidersPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchProvidersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchProvidersTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetSearchProvidersButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        providersTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        providersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        providersScrollPanel.setViewportView(providersTable);

        javax.swing.GroupLayout providerPanelLayout = new javax.swing.GroupLayout(providerPanel);
        providerPanel.setLayout(providerPanelLayout);
        providerPanelLayout.setHorizontalGroup(
            providerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(providerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(providerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(providerPanelLayout.createSequentialGroup()
                        .addComponent(providerFunctionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(searchProvidersPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(providersScrollPanel))
                .addContainerGap())
        );
        providerPanelLayout.setVerticalGroup(
            providerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(providerPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(providerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(providerFunctionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchProvidersPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(providersScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        getContentPane().add(providerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 1170, 830));

        importProductPanel.setBackground(new java.awt.Color(255, 255, 255));
        importProductPanel.setPreferredSize(new java.awt.Dimension(1170, 830));

        searchImportProductPanel.setBackground(new java.awt.Color(255, 255, 255));
        searchImportProductPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N
        searchImportProductPanel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        searchImportProductTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        searchImportProductTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchImportProductTextFieldKeyReleased(evt);
            }
        });

        searchImportProductRefreshButton.setBackground(new java.awt.Color(65, 120, 190));
        searchImportProductRefreshButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        searchImportProductRefreshButton.setForeground(new java.awt.Color(255, 255, 255));
        searchImportProductRefreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        searchImportProductRefreshButton.setText("Làm Mới");
        searchImportProductRefreshButton.setBorderPainted(false);
        searchImportProductRefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchImportProductRefreshButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout searchImportProductPanelLayout = new javax.swing.GroupLayout(searchImportProductPanel);
        searchImportProductPanel.setLayout(searchImportProductPanelLayout);
        searchImportProductPanelLayout.setHorizontalGroup(
            searchImportProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchImportProductPanelLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(searchImportProductTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(searchImportProductRefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        searchImportProductPanelLayout.setVerticalGroup(
            searchImportProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchImportProductPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(searchImportProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchImportProductRefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchImportProductTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        importProductScrollPanel.setBackground(new java.awt.Color(255, 255, 255));
        importProductScrollPanel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        importProductsTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        importProductsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        importProductScrollPanel.setViewportView(importProductsTable);

        importProductPriceLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        importProductPriceLabel.setText("Giá nhập");

        importProductPriceTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        importProductPriceTextField.setText("29,000,000");

        importProductAddButton.setBackground(new java.awt.Color(0, 122, 249));
        importProductAddButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        importProductAddButton.setForeground(new java.awt.Color(255, 255, 255));
        importProductAddButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add_circle_icon.png"))); // NOI18N
        importProductAddButton.setText("Thêm");
        importProductAddButton.setBorderPainted(false);
        importProductAddButton.setMaximumSize(new java.awt.Dimension(64, 22));
        importProductAddButton.setMinimumSize(new java.awt.Dimension(64, 22));
        importProductAddButton.setPreferredSize(new java.awt.Dimension(64, 22));
        importProductAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importProductAddButtonActionPerformed(evt);
            }
        });

        providerNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        providerNameLabel.setText("Nhà phân phối");

        providerNameComboBox.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        providerNameComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                providerNameComboBoxActionPerformed(evt);
            }
        });

        importProductBillCreatorLabel.setEditable(false);
        importProductBillCreatorLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        importProductBillCreatorLabel.setEnabled(false);
        importProductBillCreatorLabel.setFocusable(false);
        importProductBillCreatorLabel.setRequestFocusEnabled(false);

        importProductBillCreatorTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        importProductBillCreatorTextField.setText("Người tạo phiếu:");

        importProductBillScrollPane.setBackground(new java.awt.Color(255, 255, 255));

        importProductBillTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        importProductBillTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        importProductBillScrollPane.setViewportView(importProductBillTable);

        editBillItemButton.setBackground(new java.awt.Color(255, 193, 7));
        editBillItemButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editBillItemButton.setForeground(new java.awt.Color(255, 255, 255));
        editBillItemButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit_icon.png"))); // NOI18N
        editBillItemButton.setText("Sửa bản ghi");
        editBillItemButton.setBorderPainted(false);
        editBillItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBillItemButtonActionPerformed(evt);
            }
        });

        removeBillItemButton.setBackground(new java.awt.Color(212, 57, 68));
        removeBillItemButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        removeBillItemButton.setForeground(new java.awt.Color(255, 255, 255));
        removeBillItemButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/remove_icon.png"))); // NOI18N
        removeBillItemButton.setText("Xoá bản ghi");
        removeBillItemButton.setBorderPainted(false);
        removeBillItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeBillItemButtonActionPerformed(evt);
            }
        });

        totalImportPriceLabel.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        totalImportPriceLabel.setForeground(new java.awt.Color(204, 0, 51));
        totalImportPriceLabel.setText("0đ");

        totalImportBillLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        totalImportBillLabel.setText("Tổng tiền:");

        importBillProductButton.setBackground(new java.awt.Color(0, 155, 110));
        importBillProductButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        importBillProductButton.setForeground(new java.awt.Color(255, 255, 255));
        importBillProductButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/import_icon.png"))); // NOI18N
        importBillProductButton.setText("Nhập hàng");
        importBillProductButton.setBorderPainted(false);
        importBillProductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importBillProductButtonActionPerformed(evt);
            }
        });

        importProductQuantityTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        importProductQuantityTextField.setText("5");

        importProductQuantityLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        importProductQuantityLabel.setText("Số Lượng: ");

        javax.swing.GroupLayout importProductPanelLayout = new javax.swing.GroupLayout(importProductPanel);
        importProductPanel.setLayout(importProductPanelLayout);
        importProductPanelLayout.setHorizontalGroup(
            importProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(importProductPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(importProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(importProductScrollPanel)
                    .addComponent(searchImportProductPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(importProductPanelLayout.createSequentialGroup()
                        .addComponent(importProductQuantityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(importProductQuantityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(importProductPriceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(importProductPriceTextField)
                        .addGap(18, 18, 18)
                        .addComponent(importProductAddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(importProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(importProductPanelLayout.createSequentialGroup()
                        .addGroup(importProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(importProductPanelLayout.createSequentialGroup()
                                .addComponent(totalImportBillLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(totalImportPriceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(89, 89, 89))
                            .addGroup(importProductPanelLayout.createSequentialGroup()
                                .addComponent(editBillItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(removeBillItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(76, 76, 76)))
                        .addComponent(importBillProductButton, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(importProductBillScrollPane, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, importProductPanelLayout.createSequentialGroup()
                        .addGroup(importProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(providerNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(importProductBillCreatorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(76, 76, 76)
                        .addGroup(importProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(providerNameComboBox, 0, 429, Short.MAX_VALUE)
                            .addComponent(importProductBillCreatorLabel))))
                .addContainerGap())
        );
        importProductPanelLayout.setVerticalGroup(
            importProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, importProductPanelLayout.createSequentialGroup()
                .addGroup(importProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(importProductPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(searchImportProductPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(importProductScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 607, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(importProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(importProductPriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(importProductQuantityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(importProductPriceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(importProductQuantityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(importProductAddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(editBillItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(importProductPanelLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(importProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(providerNameLabel)
                            .addComponent(providerNameComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(importProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(importProductBillCreatorTextField)
                            .addComponent(importProductBillCreatorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(importProductBillScrollPane)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(importProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(totalImportBillLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(totalImportPriceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(importProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(importBillProductButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(removeBillItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(65, 65, 65))
        );

        getContentPane().add(importProductPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 1170, 830));

        importBillPanel.setBackground(new java.awt.Color(255, 255, 255));
        importBillPanel.setPreferredSize(new java.awt.Dimension(1170, 830));

        importBillsTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        importBillsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        importBillsScrollPanel.setViewportView(importBillsTable);

        searchImportBillsPanel.setBackground(new java.awt.Color(255, 255, 255));
        searchImportBillsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        searchImportBillsTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        searchImportBillsTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchImportBillsTextFieldKeyReleased(evt);
            }
        });

        resetSearchImportBillsButton1.setBackground(new java.awt.Color(65, 120, 190));
        resetSearchImportBillsButton1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        resetSearchImportBillsButton1.setForeground(new java.awt.Color(255, 255, 255));
        resetSearchImportBillsButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        resetSearchImportBillsButton1.setText("Làm mới");
        resetSearchImportBillsButton1.setBorderPainted(false);
        resetSearchImportBillsButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        resetSearchImportBillsButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetSearchImportBillsButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout searchImportBillsPanelLayout = new javax.swing.GroupLayout(searchImportBillsPanel);
        searchImportBillsPanel.setLayout(searchImportBillsPanelLayout);
        searchImportBillsPanelLayout.setHorizontalGroup(
            searchImportBillsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchImportBillsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchImportBillsTextField)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resetSearchImportBillsButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        searchImportBillsPanelLayout.setVerticalGroup(
            searchImportBillsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchImportBillsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchImportBillsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchImportBillsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetSearchImportBillsButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        importBillsFunctionPanel.setBackground(new java.awt.Color(255, 255, 255));
        importBillsFunctionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chức năng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N
        importBillsFunctionPanel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);

        exportImportBillsToExcelButton.setBackground(new java.awt.Color(0, 155, 110));
        exportImportBillsToExcelButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        exportImportBillsToExcelButton.setForeground(new java.awt.Color(255, 255, 255));
        exportImportBillsToExcelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/sheet.png"))); // NOI18N
        exportImportBillsToExcelButton.setText("Xuất Excel");
        exportImportBillsToExcelButton.setBorderPainted(false);
        exportImportBillsToExcelButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exportImportBillsToExcelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportImportBillsToExcelButtonActionPerformed(evt);
            }
        });

        editImportBillButton.setBackground(new java.awt.Color(255, 193, 7));
        editImportBillButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editImportBillButton.setForeground(new java.awt.Color(255, 255, 255));
        editImportBillButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit_icon.png"))); // NOI18N
        editImportBillButton.setText("Sửa");
        editImportBillButton.setBorderPainted(false);
        editImportBillButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editImportBillButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editImportBillButtonActionPerformed(evt);
            }
        });

        deleteImportBillButton.setBackground(new java.awt.Color(212, 57, 68));
        deleteImportBillButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        deleteImportBillButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteImportBillButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/remove_icon.png"))); // NOI18N
        deleteImportBillButton.setText("Xoá");
        deleteImportBillButton.setBorderPainted(false);
        deleteImportBillButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteImportBillButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteImportBillButtonActionPerformed(evt);
            }
        });

        viewImportBillButton.setBackground(new java.awt.Color(13, 110, 253));
        viewImportBillButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewImportBillButton.setForeground(new java.awt.Color(255, 255, 255));
        viewImportBillButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/show.png"))); // NOI18N
        viewImportBillButton.setText("Xem");
        viewImportBillButton.setBorderPainted(false);
        viewImportBillButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewImportBillButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout importBillsFunctionPanelLayout = new javax.swing.GroupLayout(importBillsFunctionPanel);
        importBillsFunctionPanel.setLayout(importBillsFunctionPanelLayout);
        importBillsFunctionPanelLayout.setHorizontalGroup(
            importBillsFunctionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(importBillsFunctionPanelLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(viewImportBillButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editImportBillButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(deleteImportBillButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exportImportBillsToExcelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        importBillsFunctionPanelLayout.setVerticalGroup(
            importBillsFunctionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(importBillsFunctionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(importBillsFunctionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addComponent(exportImportBillsToExcelButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, importBillsFunctionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(editImportBillButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(viewImportBillButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(deleteImportBillButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 7, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout importBillPanelLayout = new javax.swing.GroupLayout(importBillPanel);
        importBillPanel.setLayout(importBillPanelLayout);
        importBillPanelLayout.setHorizontalGroup(
            importBillPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, importBillPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(importBillPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, importBillPanelLayout.createSequentialGroup()
                        .addComponent(importBillsFunctionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchImportBillsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(importBillPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(importBillsScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18))
        );
        importBillPanelLayout.setVerticalGroup(
            importBillPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(importBillPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(importBillPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(importBillsFunctionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchImportBillsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(importBillsScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        getContentPane().add(importBillPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 1170, 830));

        exportProductPanel.setBackground(new java.awt.Color(255, 255, 255));
        exportProductPanel.setPreferredSize(new java.awt.Dimension(1170, 830));

        searchExportProductPanel.setBackground(new java.awt.Color(255, 255, 255));
        searchExportProductPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N
        searchExportProductPanel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        searchExportProductTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        searchExportProductTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchExportProductTextFieldKeyReleased(evt);
            }
        });

        searchExportProductRefreshButton.setBackground(new java.awt.Color(65, 120, 190));
        searchExportProductRefreshButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        searchExportProductRefreshButton.setForeground(new java.awt.Color(255, 255, 255));
        searchExportProductRefreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        searchExportProductRefreshButton.setText("Làm Mới");
        searchExportProductRefreshButton.setBorderPainted(false);
        searchExportProductRefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchExportProductRefreshButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout searchExportProductPanelLayout = new javax.swing.GroupLayout(searchExportProductPanel);
        searchExportProductPanel.setLayout(searchExportProductPanelLayout);
        searchExportProductPanelLayout.setHorizontalGroup(
            searchExportProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchExportProductPanelLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(searchExportProductTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(searchExportProductRefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        searchExportProductPanelLayout.setVerticalGroup(
            searchExportProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchExportProductPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(searchExportProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchExportProductRefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchExportProductTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        exportProductScrollPanel.setBackground(new java.awt.Color(255, 255, 255));
        exportProductScrollPanel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        exportInStockProductsTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        exportInStockProductsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        exportInStockProductsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        exportProductScrollPanel.setViewportView(exportInStockProductsTable);

        exportProductPriceLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        exportProductPriceLabel.setText("Giá xuất:");

        exportProductPriceTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        exportProductPriceTextField.setText("29,000,000");

        exportProductAddButton.setBackground(new java.awt.Color(0, 122, 249));
        exportProductAddButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        exportProductAddButton.setForeground(new java.awt.Color(255, 255, 255));
        exportProductAddButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add_circle_icon.png"))); // NOI18N
        exportProductAddButton.setText("Thêm");
        exportProductAddButton.setBorderPainted(false);
        exportProductAddButton.setMaximumSize(new java.awt.Dimension(64, 22));
        exportProductAddButton.setMinimumSize(new java.awt.Dimension(64, 22));
        exportProductAddButton.setPreferredSize(new java.awt.Dimension(64, 22));
        exportProductAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportProductAddButtonActionPerformed(evt);
            }
        });

        clientNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        clientNameLabel.setText("Tên khách hàng");

        exportProductBillCreatorLabel.setEditable(false);
        exportProductBillCreatorLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        exportProductBillCreatorLabel.setEnabled(false);
        exportProductBillCreatorLabel.setFocusable(false);
        exportProductBillCreatorLabel.setRequestFocusEnabled(false);

        exportProductBillCreatorTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        exportProductBillCreatorTextField.setText("Người tạo phiếu");

        exportProductBillScrollPane.setBackground(new java.awt.Color(255, 255, 255));

        exportProductsBillTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        exportProductsBillTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        exportProductBillScrollPane.setViewportView(exportProductsBillTable);

        editExportBillItemButton.setBackground(new java.awt.Color(255, 193, 7));
        editExportBillItemButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editExportBillItemButton.setForeground(new java.awt.Color(255, 255, 255));
        editExportBillItemButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit_icon.png"))); // NOI18N
        editExportBillItemButton.setText("Sửa bản ghi");
        editExportBillItemButton.setBorderPainted(false);
        editExportBillItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editExportBillItemButtonActionPerformed(evt);
            }
        });

        removeExportBillItemButton.setBackground(new java.awt.Color(212, 57, 68));
        removeExportBillItemButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        removeExportBillItemButton.setForeground(new java.awt.Color(255, 255, 255));
        removeExportBillItemButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/remove_icon.png"))); // NOI18N
        removeExportBillItemButton.setText("Xoá bản ghi");
        removeExportBillItemButton.setBorderPainted(false);
        removeExportBillItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeExportBillItemButtonActionPerformed(evt);
            }
        });

        totalExportPriceLabel.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        totalExportPriceLabel.setForeground(new java.awt.Color(204, 0, 51));
        totalExportPriceLabel.setText("0đ");

        totalExportBillLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        totalExportBillLabel.setText("Tổng tiền:");

        exportBillProductButton.setBackground(new java.awt.Color(0, 155, 110));
        exportBillProductButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        exportBillProductButton.setForeground(new java.awt.Color(255, 255, 255));
        exportBillProductButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/import_icon.png"))); // NOI18N
        exportBillProductButton.setText("Xuất hàng");
        exportBillProductButton.setBorderPainted(false);
        exportBillProductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportBillProductButtonActionPerformed(evt);
            }
        });

        exportProductQuantityTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        exportProductQuantityTextField.setText("1");

        exportProductQuantityLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        exportProductQuantityLabel.setText("Số Lượng: ");

        clientNameTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        clientNameTextField.setText("Anh Hoàng");

        clientPhoneNumberTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        clientPhoneNumberTextField.setText("0336118268");

        clientPhoneNumberLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        clientPhoneNumberLabel.setText("Số điện thoại khách hàng");

        clientAddressTextField.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        clientAddressTextField.setText("Bắc Giang");

        clientAddressLabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        clientAddressLabel.setText("Địa chỉ giao hàng");

        javax.swing.GroupLayout exportProductPanelLayout = new javax.swing.GroupLayout(exportProductPanel);
        exportProductPanel.setLayout(exportProductPanelLayout);
        exportProductPanelLayout.setHorizontalGroup(
            exportProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exportProductPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(exportProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(exportProductScrollPanel)
                    .addComponent(searchExportProductPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(exportProductPanelLayout.createSequentialGroup()
                        .addComponent(exportProductQuantityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exportProductQuantityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(exportProductPriceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exportProductPriceTextField)
                        .addGap(18, 18, 18)
                        .addComponent(exportProductAddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(exportProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(exportProductPanelLayout.createSequentialGroup()
                        .addGroup(exportProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(exportProductPanelLayout.createSequentialGroup()
                                .addComponent(totalExportBillLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(totalExportPriceLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(89, 89, 89))
                            .addGroup(exportProductPanelLayout.createSequentialGroup()
                                .addComponent(editExportBillItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(removeExportBillItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(81, 81, 81)))
                        .addComponent(exportBillProductButton, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(exportProductBillScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
                    .addGroup(exportProductPanelLayout.createSequentialGroup()
                        .addGroup(exportProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clientNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clientPhoneNumberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(exportProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clientPhoneNumberTextField)
                            .addComponent(clientNameTextField)))
                    .addGroup(exportProductPanelLayout.createSequentialGroup()
                        .addGroup(exportProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clientAddressLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(exportProductBillCreatorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(exportProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(exportProductBillCreatorLabel)
                            .addComponent(clientAddressTextField))))
                .addContainerGap())
        );
        exportProductPanelLayout.setVerticalGroup(
            exportProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, exportProductPanelLayout.createSequentialGroup()
                .addGroup(exportProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(exportProductPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(searchExportProductPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(exportProductScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 607, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(exportProductPanelLayout.createSequentialGroup()
                        .addGroup(exportProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(exportProductPanelLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(clientNameLabel))
                            .addGroup(exportProductPanelLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(clientNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(exportProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(exportProductPanelLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(clientPhoneNumberLabel))
                            .addComponent(clientPhoneNumberTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(exportProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(exportProductPanelLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(clientAddressLabel))
                            .addComponent(clientAddressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(exportProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(exportProductBillCreatorTextField)
                            .addComponent(exportProductBillCreatorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(exportProductBillScrollPane)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(exportProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(totalExportBillLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(totalExportPriceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(17, 17, 17)
                .addGroup(exportProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, exportProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(exportProductPriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(exportProductQuantityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(exportProductPriceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(exportProductQuantityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(exportProductAddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(editExportBillItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, exportProductPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(exportBillProductButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(removeExportBillItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(57, 57, 57))
        );

        getContentPane().add(exportProductPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 1170, 830));

        exportBillPanel.setBackground(new java.awt.Color(255, 255, 255));
        exportBillPanel.setPreferredSize(new java.awt.Dimension(1170, 830));

        exportBillsTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        exportBillsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        exportBillsScrollPanel.setViewportView(exportBillsTable);

        searchExportBillsPanel.setBackground(new java.awt.Color(255, 255, 255));
        searchExportBillsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        searchExportBillsTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        searchExportBillsTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchExportBillsTextFieldKeyReleased(evt);
            }
        });

        resetSearchExportBillsButton.setBackground(new java.awt.Color(65, 120, 190));
        resetSearchExportBillsButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        resetSearchExportBillsButton.setForeground(new java.awt.Color(255, 255, 255));
        resetSearchExportBillsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        resetSearchExportBillsButton.setText("Làm mới");
        resetSearchExportBillsButton.setBorderPainted(false);
        resetSearchExportBillsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        resetSearchExportBillsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetSearchExportBillsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout searchExportBillsPanelLayout = new javax.swing.GroupLayout(searchExportBillsPanel);
        searchExportBillsPanel.setLayout(searchExportBillsPanelLayout);
        searchExportBillsPanelLayout.setHorizontalGroup(
            searchExportBillsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchExportBillsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchExportBillsTextField)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resetSearchExportBillsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        searchExportBillsPanelLayout.setVerticalGroup(
            searchExportBillsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchExportBillsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchExportBillsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchExportBillsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetSearchExportBillsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        exportBillsFunctionPanel.setBackground(new java.awt.Color(255, 255, 255));
        exportBillsFunctionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chức năng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N
        exportBillsFunctionPanel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jSeparator8.setOrientation(javax.swing.SwingConstants.VERTICAL);

        exportImportBillsToExcelButton1.setBackground(new java.awt.Color(0, 155, 110));
        exportImportBillsToExcelButton1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        exportImportBillsToExcelButton1.setForeground(new java.awt.Color(255, 255, 255));
        exportImportBillsToExcelButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/sheet.png"))); // NOI18N
        exportImportBillsToExcelButton1.setText("Xuất Excel");
        exportImportBillsToExcelButton1.setBorderPainted(false);
        exportImportBillsToExcelButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exportImportBillsToExcelButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportImportBillsToExcelButton1ActionPerformed(evt);
            }
        });

        editExportBillButton.setBackground(new java.awt.Color(255, 193, 7));
        editExportBillButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editExportBillButton.setForeground(new java.awt.Color(255, 255, 255));
        editExportBillButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit_icon.png"))); // NOI18N
        editExportBillButton.setText("Sửa");
        editExportBillButton.setBorderPainted(false);
        editExportBillButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editExportBillButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editExportBillButtonActionPerformed(evt);
            }
        });

        deleteExportBillButton.setBackground(new java.awt.Color(212, 57, 68));
        deleteExportBillButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        deleteExportBillButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteExportBillButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/remove_icon.png"))); // NOI18N
        deleteExportBillButton.setText("Xoá");
        deleteExportBillButton.setBorderPainted(false);
        deleteExportBillButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteExportBillButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteExportBillButtonActionPerformed(evt);
            }
        });

        viewExportBillButton.setBackground(new java.awt.Color(13, 110, 253));
        viewExportBillButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewExportBillButton.setForeground(new java.awt.Color(255, 255, 255));
        viewExportBillButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/show.png"))); // NOI18N
        viewExportBillButton.setText("Xem");
        viewExportBillButton.setBorderPainted(false);
        viewExportBillButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewExportBillButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout exportBillsFunctionPanelLayout = new javax.swing.GroupLayout(exportBillsFunctionPanel);
        exportBillsFunctionPanel.setLayout(exportBillsFunctionPanelLayout);
        exportBillsFunctionPanelLayout.setHorizontalGroup(
            exportBillsFunctionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exportBillsFunctionPanelLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(viewExportBillButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editExportBillButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(deleteExportBillButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exportImportBillsToExcelButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        exportBillsFunctionPanelLayout.setVerticalGroup(
            exportBillsFunctionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exportBillsFunctionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(exportBillsFunctionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(exportImportBillsToExcelButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, exportBillsFunctionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(editExportBillButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(viewExportBillButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(deleteExportBillButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout exportBillPanelLayout = new javax.swing.GroupLayout(exportBillPanel);
        exportBillPanel.setLayout(exportBillPanelLayout);
        exportBillPanelLayout.setHorizontalGroup(
            exportBillPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exportBillPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(exportBillPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, exportBillPanelLayout.createSequentialGroup()
                        .addComponent(exportBillsFunctionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchExportBillsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(exportBillsScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );
        exportBillPanelLayout.setVerticalGroup(
            exportBillPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exportBillPanelLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(exportBillPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(exportBillsFunctionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchExportBillsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(exportBillsScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        getContentPane().add(exportBillPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 1170, 830));

        inStockPanel.setBackground(new java.awt.Color(255, 255, 255));
        inStockPanel.setPreferredSize(new java.awt.Dimension(1170, 830));

        inStocksTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        inStocksTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        inStocksScrollPanel.setViewportView(inStocksTable);

        searchInStocksPanel.setBackground(new java.awt.Color(255, 255, 255));
        searchInStocksPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        searchInStocksTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        searchInStocksTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchInStocksTextFieldKeyReleased(evt);
            }
        });

        resetSearchInStocksButton.setBackground(new java.awt.Color(65, 120, 190));
        resetSearchInStocksButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        resetSearchInStocksButton.setForeground(new java.awt.Color(255, 255, 255));
        resetSearchInStocksButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        resetSearchInStocksButton.setText("Làm mới");
        resetSearchInStocksButton.setBorderPainted(false);
        resetSearchInStocksButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        resetSearchInStocksButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetSearchInStocksButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout searchInStocksPanelLayout = new javax.swing.GroupLayout(searchInStocksPanel);
        searchInStocksPanel.setLayout(searchInStocksPanelLayout);
        searchInStocksPanelLayout.setHorizontalGroup(
            searchInStocksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchInStocksPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchInStocksTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resetSearchInStocksButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        searchInStocksPanelLayout.setVerticalGroup(
            searchInStocksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchInStocksPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchInStocksPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchInStocksTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetSearchInStocksButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        inStocksFunctionPanel.setBackground(new java.awt.Color(255, 255, 255));
        inStocksFunctionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chức năng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N
        inStocksFunctionPanel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);

        exportInStocksToExcelButton.setBackground(new java.awt.Color(0, 155, 110));
        exportInStocksToExcelButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        exportInStocksToExcelButton.setForeground(new java.awt.Color(255, 255, 255));
        exportInStocksToExcelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/sheet.png"))); // NOI18N
        exportInStocksToExcelButton.setText("Xuất Excel");
        exportInStocksToExcelButton.setBorderPainted(false);
        exportInStocksToExcelButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exportInStocksToExcelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportInStocksToExcelButtonActionPerformed(evt);
            }
        });

        editInStockButton.setBackground(new java.awt.Color(255, 193, 7));
        editInStockButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editInStockButton.setForeground(new java.awt.Color(255, 255, 255));
        editInStockButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit_icon.png"))); // NOI18N
        editInStockButton.setText("Sửa");
        editInStockButton.setBorderPainted(false);
        editInStockButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editInStockButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editInStockButtonActionPerformed(evt);
            }
        });

        deleteInStockButton.setBackground(new java.awt.Color(212, 57, 68));
        deleteInStockButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        deleteInStockButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteInStockButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/remove_icon.png"))); // NOI18N
        deleteInStockButton.setText("Xoá");
        deleteInStockButton.setBorderPainted(false);
        deleteInStockButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteInStockButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteInStockButtonActionPerformed(evt);
            }
        });

        viewInStockButton.setBackground(new java.awt.Color(13, 110, 253));
        viewInStockButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewInStockButton.setForeground(new java.awt.Color(255, 255, 255));
        viewInStockButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/show.png"))); // NOI18N
        viewInStockButton.setText("Xem");
        viewInStockButton.setBorderPainted(false);
        viewInStockButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewInStockButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout inStocksFunctionPanelLayout = new javax.swing.GroupLayout(inStocksFunctionPanel);
        inStocksFunctionPanel.setLayout(inStocksFunctionPanelLayout);
        inStocksFunctionPanelLayout.setHorizontalGroup(
            inStocksFunctionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inStocksFunctionPanelLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(viewInStockButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editInStockButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(deleteInStockButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exportInStocksToExcelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        inStocksFunctionPanelLayout.setVerticalGroup(
            inStocksFunctionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inStocksFunctionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inStocksFunctionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(exportInStocksToExcelButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, inStocksFunctionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(editInStockButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(viewInStockButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(deleteInStockButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout inStockPanelLayout = new javax.swing.GroupLayout(inStockPanel);
        inStockPanel.setLayout(inStockPanelLayout);
        inStockPanelLayout.setHorizontalGroup(
            inStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inStockPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inStockPanelLayout.createSequentialGroup()
                        .addComponent(inStocksFunctionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchInStocksPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18))
                    .addGroup(inStockPanelLayout.createSequentialGroup()
                        .addComponent(inStocksScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        inStockPanelLayout.setVerticalGroup(
            inStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inStockPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inStockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(inStocksFunctionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchInStocksPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inStocksScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(73, Short.MAX_VALUE))
        );

        getContentPane().add(inStockPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 1170, 830));

        userPanel.setBackground(new java.awt.Color(255, 255, 255));
        userPanel.setPreferredSize(new java.awt.Dimension(1170, 830));

        usersTable.setAutoCreateRowSorter(true);
        usersTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        usersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        usersScrollPanel.setViewportView(usersTable);

        searchUsersPanel.setBackground(new java.awt.Color(255, 255, 255));
        searchUsersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N

        searchUsersTextField.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        searchUsersTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchUsersTextFieldKeyReleased(evt);
            }
        });

        searchUsersButton.setBackground(new java.awt.Color(65, 120, 190));
        searchUsersButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        searchUsersButton.setForeground(new java.awt.Color(255, 255, 255));
        searchUsersButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        searchUsersButton.setText("Làm mới");
        searchUsersButton.setBorderPainted(false);
        searchUsersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchUsersButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout searchUsersPanelLayout = new javax.swing.GroupLayout(searchUsersPanel);
        searchUsersPanel.setLayout(searchUsersPanelLayout);
        searchUsersPanelLayout.setHorizontalGroup(
            searchUsersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchUsersPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchUsersTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchUsersButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        searchUsersPanelLayout.setVerticalGroup(
            searchUsersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchUsersPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchUsersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchUsersTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchUsersButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        userFunctionPanel.setBackground(new java.awt.Color(255, 255, 255));
        userFunctionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chức năng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 14))); // NOI18N
        userFunctionPanel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        importUsersFromExcelButton.setBackground(new java.awt.Color(1, 169, 84));
        importUsersFromExcelButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        importUsersFromExcelButton.setForeground(new java.awt.Color(255, 255, 255));
        importUsersFromExcelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/sheet.png"))); // NOI18N
        importUsersFromExcelButton.setText("Nhập Excel");
        importUsersFromExcelButton.setBorderPainted(false);
        importUsersFromExcelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importUsersFromExcelButtonActionPerformed(evt);
            }
        });

        exportUsersToExcelButton.setBackground(new java.awt.Color(0, 155, 110));
        exportUsersToExcelButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        exportUsersToExcelButton.setForeground(new java.awt.Color(255, 255, 255));
        exportUsersToExcelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/sheet.png"))); // NOI18N
        exportUsersToExcelButton.setText("Xuất Excel");
        exportUsersToExcelButton.setBorderPainted(false);
        exportUsersToExcelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportUsersToExcelButtonActionPerformed(evt);
            }
        });

        editUserButton.setBackground(new java.awt.Color(255, 193, 7));
        editUserButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        editUserButton.setForeground(new java.awt.Color(255, 255, 255));
        editUserButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit_icon.png"))); // NOI18N
        editUserButton.setText("Sửa");
        editUserButton.setBorderPainted(false);
        editUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editUserButtonActionPerformed(evt);
            }
        });

        viewUserButton.setBackground(new java.awt.Color(13, 110, 253));
        viewUserButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        viewUserButton.setForeground(new java.awt.Color(255, 255, 255));
        viewUserButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/show.png"))); // NOI18N
        viewUserButton.setText("Xem");
        viewUserButton.setBorderPainted(false);
        viewUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewUserButtonActionPerformed(evt);
            }
        });

        deleteUserButton.setBackground(new java.awt.Color(212, 57, 68));
        deleteUserButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        deleteUserButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteUserButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/remove_icon.png"))); // NOI18N
        deleteUserButton.setText("Xoá");
        deleteUserButton.setBorderPainted(false);
        deleteUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteUserButtonActionPerformed(evt);
            }
        });

        addUserButton.setBackground(new java.awt.Color(36, 169, 65));
        addUserButton.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        addUserButton.setForeground(new java.awt.Color(255, 255, 255));
        addUserButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add_circle_icon.png"))); // NOI18N
        addUserButton.setText("Thêm");
        addUserButton.setBorderPainted(false);
        addUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout userFunctionPanelLayout = new javax.swing.GroupLayout(userFunctionPanel);
        userFunctionPanel.setLayout(userFunctionPanelLayout);
        userFunctionPanelLayout.setHorizontalGroup(
            userFunctionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userFunctionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(viewUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(editUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(deleteUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(importUsersFromExcelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exportUsersToExcelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
        userFunctionPanelLayout.setVerticalGroup(
            userFunctionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userFunctionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(userFunctionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, userFunctionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(importUsersFromExcelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(exportUsersToExcelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, userFunctionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(viewUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(editUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(deleteUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(addUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
            .addGroup(userFunctionPanelLayout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout userPanelLayout = new javax.swing.GroupLayout(userPanel);
        userPanel.setLayout(userPanelLayout);
        userPanelLayout.setHorizontalGroup(
            userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(usersScrollPanel)
                    .addGroup(userPanelLayout.createSequentialGroup()
                        .addComponent(userFunctionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 737, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchUsersPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18))
        );
        userPanelLayout.setVerticalGroup(
            userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(userFunctionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchUsersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(usersScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        getContentPane().add(userPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 1170, 830));

        statisticsPanel.setBackground(new java.awt.Color(204, 255, 255));
        statisticsPanel.setPreferredSize(new java.awt.Dimension(1170, 830));

        jLabel9.setText("Statistic");

        javax.swing.GroupLayout statisticsPanelLayout = new javax.swing.GroupLayout(statisticsPanel);
        statisticsPanel.setLayout(statisticsPanelLayout);
        statisticsPanelLayout.setHorizontalGroup(
            statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statisticsPanelLayout.createSequentialGroup()
                .addGap(452, 452, 452)
                .addComponent(jLabel9)
                .addContainerGap(646, Short.MAX_VALUE))
        );
        statisticsPanelLayout.setVerticalGroup(
            statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statisticsPanelLayout.createSequentialGroup()
                .addGap(284, 284, 284)
                .addComponent(jLabel9)
                .addContainerGap(530, Short.MAX_VALUE))
        );

        getContentPane().add(statisticsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 1170, 830));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void setActiveTab(String tabName) {
        Color defaultColor = new Color(44, 43, 196);
        Color activeColor = new Color(126, 142, 241);
        switch (tabName) {
            case "product" -> {
                this.productTab.setBackground(activeColor);
                this.providerTab.setBackground(defaultColor);
                this.importProductTab.setBackground(defaultColor);
                this.importBillTab.setBackground(defaultColor);
                this.exportProductTab.setBackground(defaultColor);
                this.exportBillTab.setBackground(defaultColor);
                this.inStockTab.setBackground(defaultColor);
                this.userTab.setBackground(defaultColor);
                this.statisticsTab.setBackground(defaultColor);
                break;
            }
            case "provider" -> {
                this.productTab.setBackground(defaultColor);
                this.providerTab.setBackground(activeColor);
                this.importProductTab.setBackground(defaultColor);
                this.importBillTab.setBackground(defaultColor);
                this.exportProductTab.setBackground(defaultColor);
                this.exportBillTab.setBackground(defaultColor);
                this.inStockTab.setBackground(defaultColor);
                this.userTab.setBackground(defaultColor);
                this.statisticsTab.setBackground(defaultColor);
                break;
            }
            case "import-product" -> {
                this.productTab.setBackground(defaultColor);
                this.providerTab.setBackground(defaultColor);
                this.importProductTab.setBackground(activeColor);
                this.importBillTab.setBackground(defaultColor);
                this.exportProductTab.setBackground(defaultColor);
                this.exportBillTab.setBackground(defaultColor);
                this.inStockTab.setBackground(defaultColor);
                this.userTab.setBackground(defaultColor);
                this.statisticsTab.setBackground(defaultColor);
                break;
            }
            case "import-bill" -> {
                this.productTab.setBackground(defaultColor);
                this.providerTab.setBackground(defaultColor);
                this.importProductTab.setBackground(defaultColor);
                this.importBillTab.setBackground(activeColor);
                this.exportProductTab.setBackground(defaultColor);
                this.exportBillTab.setBackground(defaultColor);
                this.inStockTab.setBackground(defaultColor);
                this.userTab.setBackground(defaultColor);
                this.statisticsTab.setBackground(defaultColor);
                break;
            }
            case "export-product" -> {
                this.productTab.setBackground(defaultColor);
                this.providerTab.setBackground(defaultColor);
                this.importProductTab.setBackground(defaultColor);
                this.importBillTab.setBackground(defaultColor);
                this.exportProductTab.setBackground(activeColor);
                this.exportBillTab.setBackground(defaultColor);
                this.inStockTab.setBackground(defaultColor);
                this.userTab.setBackground(defaultColor);
                this.statisticsTab.setBackground(defaultColor);
                break;
            }
            case "export-bill" -> {
                this.productTab.setBackground(defaultColor);
                this.providerTab.setBackground(defaultColor);
                this.importProductTab.setBackground(defaultColor);
                this.importBillTab.setBackground(defaultColor);
                this.exportProductTab.setBackground(defaultColor);
                this.exportBillTab.setBackground(activeColor);
                this.inStockTab.setBackground(defaultColor);
                this.userTab.setBackground(defaultColor);
                this.statisticsTab.setBackground(defaultColor);
                break;
            }
            case "in-stock" -> {
                this.productTab.setBackground(defaultColor);
                this.providerTab.setBackground(defaultColor);
                this.importProductTab.setBackground(defaultColor);
                this.importBillTab.setBackground(defaultColor);
                this.exportProductTab.setBackground(defaultColor);
                this.exportBillTab.setBackground(defaultColor);
                this.inStockTab.setBackground(activeColor);
                this.userTab.setBackground(defaultColor);
                this.statisticsTab.setBackground(defaultColor);
                break;
            }
            case "user" -> {
                this.productTab.setBackground(defaultColor);
                this.providerTab.setBackground(defaultColor);
                this.importProductTab.setBackground(defaultColor);
                this.importBillTab.setBackground(defaultColor);
                this.exportProductTab.setBackground(defaultColor);
                this.exportBillTab.setBackground(defaultColor);
                this.inStockTab.setBackground(defaultColor);
                this.userTab.setBackground(activeColor);
                this.statisticsTab.setBackground(defaultColor);
                break;
            }
            case "statistics" -> {
                this.productTab.setBackground(defaultColor);
                this.providerTab.setBackground(defaultColor);
                this.importProductTab.setBackground(defaultColor);
                this.importBillTab.setBackground(defaultColor);
                this.exportProductTab.setBackground(defaultColor);
                this.exportBillTab.setBackground(defaultColor);
                this.inStockTab.setBackground(defaultColor);
                this.userTab.setBackground(defaultColor);
                this.statisticsTab.setBackground(activeColor);
                break;
            }

            default -> {
                this.productTab.setBackground(defaultColor);
                this.providerTab.setBackground(defaultColor);
                this.importProductTab.setBackground(defaultColor);
                this.importBillTab.setBackground(defaultColor);
                this.exportProductTab.setBackground(defaultColor);
                this.exportBillTab.setBackground(defaultColor);
                this.inStockTab.setBackground(defaultColor);
                this.userTab.setBackground(defaultColor);
                this.statisticsTab.setBackground(defaultColor);
                break;
            }
        }
    }

    private void setDisplayedPanel(String tabName) {
        switch (tabName) {
            case "product" -> {
                this.productPanel.setVisible(true);
                this.providerPanel.setVisible(false);
                this.importProductPanel.setVisible(false);
                this.importBillPanel.setVisible(false);
                this.exportProductPanel.setVisible(false);
                this.exportBillPanel.setVisible(false);
                this.inStockPanel.setVisible(false);
                this.userPanel.setVisible(false);
                this.statisticsPanel.setVisible(false);
                break;
            }
            case "provider" -> {
                this.productPanel.setVisible(false);
                this.providerPanel.setVisible(true);
                this.importProductPanel.setVisible(false);
                this.importBillPanel.setVisible(false);
                this.exportProductPanel.setVisible(false);
                this.exportBillPanel.setVisible(false);
                this.inStockPanel.setVisible(false);
                this.userPanel.setVisible(false);
                this.statisticsPanel.setVisible(false);
                break;
            }
            case "import-product" -> {
                this.productPanel.setVisible(false);
                this.providerPanel.setVisible(false);
                this.importProductPanel.setVisible(true);
                this.importBillPanel.setVisible(false);
                this.exportProductPanel.setVisible(false);
                this.exportBillPanel.setVisible(false);
                this.inStockPanel.setVisible(false);
                this.userPanel.setVisible(false);
                this.statisticsPanel.setVisible(false);
                break;
            }
            case "import-bill" -> {
                this.productPanel.setVisible(false);
                this.providerPanel.setVisible(false);
                this.importProductPanel.setVisible(false);
                this.importBillPanel.setVisible(true);
                this.exportProductPanel.setVisible(false);
                this.exportBillPanel.setVisible(false);
                this.inStockPanel.setVisible(false);
                this.userPanel.setVisible(false);
                this.statisticsPanel.setVisible(false);
                break;
            }
            case "export-product" -> {
                this.productPanel.setVisible(false);
                this.providerPanel.setVisible(false);
                this.importProductPanel.setVisible(false);
                this.importBillPanel.setVisible(false);
                this.exportProductPanel.setVisible(true);
                this.exportBillPanel.setVisible(false);
                this.inStockPanel.setVisible(false);
                this.userPanel.setVisible(false);
                this.statisticsPanel.setVisible(false);
                break;
            }
            case "export-bill" -> {
                this.productPanel.setVisible(false);
                this.providerPanel.setVisible(false);
                this.importProductPanel.setVisible(false);
                this.importBillPanel.setVisible(false);
                this.exportProductPanel.setVisible(false);
                this.exportBillPanel.setVisible(true);
                this.inStockPanel.setVisible(false);
                this.userPanel.setVisible(false);
                this.statisticsPanel.setVisible(false);
                break;
            }
            case "in-stock" -> {
                this.productPanel.setVisible(false);
                this.providerPanel.setVisible(false);
                this.importProductPanel.setVisible(false);
                this.importBillPanel.setVisible(false);
                this.exportProductPanel.setVisible(false);
                this.exportBillPanel.setVisible(false);
                this.inStockPanel.setVisible(true);
                this.userPanel.setVisible(false);
                this.statisticsPanel.setVisible(false);
                break;
            }
            case "user" -> {
                this.productPanel.setVisible(false);
                this.providerPanel.setVisible(false);
                this.importProductPanel.setVisible(false);
                this.importBillPanel.setVisible(false);
                this.exportProductPanel.setVisible(false);
                this.exportBillPanel.setVisible(false);
                this.inStockPanel.setVisible(false);
                this.userPanel.setVisible(true);
                this.statisticsPanel.setVisible(false);
                break;
            }
            case "statistics" -> {
                this.productPanel.setVisible(false);
                this.providerPanel.setVisible(false);
                this.importProductPanel.setVisible(false);
                this.importBillPanel.setVisible(false);
                this.exportProductPanel.setVisible(false);
                this.exportBillPanel.setVisible(false);
                this.inStockPanel.setVisible(false);
                this.userPanel.setVisible(false);
                this.statisticsPanel.setVisible(true);
                break;
            }

            default -> {
                this.productPanel.setVisible(false);
                this.providerPanel.setVisible(false);
                this.importProductPanel.setVisible(false);
                this.importBillPanel.setVisible(false);
                this.exportProductPanel.setVisible(false);
                this.exportBillPanel.setVisible(false);
                this.inStockPanel.setVisible(false);
                this.userPanel.setVisible(false);
                this.statisticsPanel.setVisible(false);
                break;
            }
        }
    }

    private void searchImportProductRefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchImportProductRefreshButtonActionPerformed
        searchImportProductTextField.setText("");
        loadDataToTableImportProducts(null);
    }//GEN-LAST:event_searchImportProductRefreshButtonActionPerformed

    private void importProductAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importProductAddButtonActionPerformed
        int[] rows = importProductsTable.getSelectedRows();
        if (rows.length == 0) {
            showDiaglogMessage(ErrorMessage.EMPTY_SELECTED_ROWS);
            return;
        }

        List<Integer> selectedIds = new ArrayList<>();
        for (int row : rows) {
            selectedIds.add(Integer.valueOf(importProductsTable.getValueAt(row, ID_COL_INDEX).toString()));
        }

        List<Product> selectedProducts = productController.getListByIds(selectedIds);

        if (importProductQuantityTextField.getText().trim().length() == 0) {
            showDiaglogMessage("Vui lòng nhập trường số lượng nhập.");
            return;
        }

        if (importProductPriceTextField.getText().trim().length() == 0) {
            showDiaglogMessage("Vui lòng nhập trường giá nhập.");
            return;
        }

        Integer quantity = 0;
        try {
            quantity = Integer.valueOf(importProductQuantityTextField.getText());
        } catch (NumberFormatException e) {
            showDiaglogMessage("Số lượng phải là một số nguyên.");
            return;
        }
        Float price = 0.0f;
        try {
            price = Float.valueOf(importProductPriceTextField.getText().replace(",", ""));
        } catch (NumberFormatException e) {
            showDiaglogMessage("Giá sản phẩm phải là một số thực.");
            return;
        }

        final Integer finalQuantity = quantity;
        final Float finalPrice = price;

        importBill.getImportBillItems()
                .addAll(selectedProducts
                        .stream()
                        .map(item -> ImportBillItem
                        .builder()
                        .product(item)
                        .quantity(finalQuantity)
                        .importPrice(finalPrice)
                        .importBill(importBill)
                        .build()
                        )
                        .toList()
                );

        loadDataToImportProductBillTable(importBill);
    }//GEN-LAST:event_importProductAddButtonActionPerformed

    private void importUsersFromExcelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importUsersFromExcelButtonActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Workbook (*.xlsx)", "xlsx");
        fileChooser.setFileFilter(filter);
        fileChooser.setMultiSelectionEnabled(false);
        int result = fileChooser.showDialog(this, "Chọn file");
        if (result == JFileChooser.APPROVE_OPTION) {
            File uploadedFile = fileChooser.getSelectedFile();
            ArrayList<User> users = ExcelUtil.excelToUsers(uploadedFile);
            CommonResponseDTO response = userController.addList(users);
            showDiaglogMessage(response.message());
            loadDataToTableUsers(null);
        }

    }//GEN-LAST:event_importUsersFromExcelButtonActionPerformed

    private void exportUsersToExcelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportUsersToExcelButtonActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lựa chọn vị trí lưu file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Workbook (*.xlsx)", "xlsx");
        fileChooser.setFileFilter(filter);
        int userSelection = fileChooser.showSaveDialog(fileChooser);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File directoryToSave = fileChooser.getSelectedFile();
            CommonResponseDTO response = ExcelUtil.usersToExcel(
                    userController.getList().data(),
                    directoryToSave.getAbsolutePath()
            );
            showDiaglogMessage(response.message());
        }
    }//GEN-LAST:event_exportUsersToExcelButtonActionPerformed

    private void editUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editUserButtonActionPerformed
        int[] rows = usersTable.getSelectedRows();
        if (rows.length == 0) {
            showDiaglogMessage(ErrorMessage.EMPTY_SELECTED_ROWS);
            return;
        }
        if (rows.length > 1) {
            showDiaglogMessage(ErrorMessage.EXCEED_SELECTED_ROWS);
            return;
        }

        showEditUserDiaglog(rows[0]);
    }//GEN-LAST:event_editUserButtonActionPerformed

    private void showEditUserDiaglog(int selectedRow) {
        Integer id = Integer.valueOf(usersTable.getValueAt(selectedRow, ID_COL_INDEX).toString());
        String username = usersTable.getValueAt(selectedRow, USERNAME_COL_INDEX).toString();
        String firstName = usersTable.getValueAt(selectedRow, FIRSTNAME_COL_INDEX).toString();
        String lastName = usersTable.getValueAt(selectedRow, LASTNAME_COL_INDEX).toString();
        String phoneNumber = usersTable.getValueAt(selectedRow, PHONENUMBER_COL_INDEX).toString();
        String email = usersTable.getValueAt(selectedRow, EMAIL_COL_INDEX).toString();

        editUserIdTextField.setText(id.toString());
        editUsernameTextField.setText(username);
        editFirstNameTextField.setText(firstName);
        editLastNameTextField.setText(lastName);
        editPhoneNumberTextField.setText(phoneNumber);
        editEmailTextField.setText(email);

        editUserDiaglog.setVisible(true);
        editUserDiaglog.setLocationRelativeTo(this);
    }

    private void viewUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewUserButtonActionPerformed
        int[] rows = usersTable.getSelectedRows();
        if (rows.length == 0) {
            showDiaglogMessage(ErrorMessage.EMPTY_SELECTED_ROWS);
            return;
        }
        if (rows.length > 1) {
            showDiaglogMessage(ErrorMessage.EXCEED_SELECTED_ROWS);
            return;
        }

        showViewUserDiaglog(rows[0]);
    }//GEN-LAST:event_viewUserButtonActionPerformed

    private void showViewUserDiaglog(int selectedRow) {
        Integer id = Integer.valueOf(usersTable.getValueAt(selectedRow, ID_COL_INDEX).toString());
        Optional<User> found = userController.findById(id);
        if (found.isEmpty()) {
            showDiaglogMessage(ErrorMessage.User.NOT_FOUND);
            return;
        }

        User foundUser = found.get();
        viewUsernameTextField.setText(foundUser.getUsername());
        viewUserFirstNameTextField.setText(foundUser.getFirstName());
        viewUserLastNameTextField.setText(foundUser.getLastName());
        viewUserPhoneNumberTextField.setText(foundUser.getPhoneNumber());
        viewUserEmailTextField.setText(foundUser.getEmail());
        if (foundUser.getWhenCreated() != null) {
            viewUserWhenCreatedTextField.setText(formatter.format(foundUser.getWhenCreated()));
        }
        if (foundUser.getLastUpdated() != null) {
            viewUserLastUpdatedTextField.setText(formatter.format(foundUser.getLastUpdated()));
        }
        if (foundUser.getLastLoggedIn() != null) {
            viewUserLastLoggedInTextField.setText(formatter.format(foundUser.getLastLoggedIn()));
        }
        if (foundUser.getLoggedIn() != null) {
            viewUserLoggedInTextField.setText(foundUser.getLoggedIn().toString());
        }

        viewUserDiaglog.setVisible(true);
        viewUserDiaglog.setLocationRelativeTo(this);
    }

    private void deleteUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteUserButtonActionPerformed
        int[] rows = usersTable.getSelectedRows();
        if (rows.length == 0) {
            showDiaglogMessage(ErrorMessage.EMPTY_SELECTED_ROWS);
            return;
        }
        showDeleteUserConfirmDiaglog(String.format("Bạn có chắc chắn xoá %d bản ghi này?", rows.length));

    }//GEN-LAST:event_deleteUserButtonActionPerformed

    private void showDeleteUserConfirmDiaglog(String content) {
        deleteUserConfirmDiaglog.setVisible(true);
        deleteUserConfirmDiaglog.setLocationRelativeTo(this);
        deleteUserConfirmDiaglogLabel.setText(content);
    }

    private void importProductsFromExcelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importProductsFromExcelButtonActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Workbook (*.xlsx)", "xlsx");
        fileChooser.setFileFilter(filter);
        fileChooser.setMultiSelectionEnabled(false);
        int result = fileChooser.showDialog(this, "Chọn file");
        if (result == JFileChooser.APPROVE_OPTION) {
            File uploadedFile = fileChooser.getSelectedFile();
            ArrayList<Product> products = ExcelUtil.excelToProducts(uploadedFile);
            CommonResponseDTO response = productController.addList(products);
            showDiaglogMessage(response.message());
            loadDataToTableProducts(null);
        }
    }//GEN-LAST:event_importProductsFromExcelButtonActionPerformed

    private void exportProductsToExcelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportProductsToExcelButtonActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lựa chọn vị trí lưu file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Workbook (*.xlsx)", "xlsx");
        fileChooser.setFileFilter(filter);
        int userSelection = fileChooser.showSaveDialog(fileChooser);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File directoryToSave = fileChooser.getSelectedFile();
            CommonResponseDTO response = ExcelUtil.productsToExcel(
                    productController.getList().data(),
                    directoryToSave.getAbsolutePath()
            );
            showDiaglogMessage(response.message());
        }
    }//GEN-LAST:event_exportProductsToExcelButtonActionPerformed

    private void editProductButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProductButtonActionPerformed
        int[] rows = productsTable.getSelectedRows();
        if (rows.length == 0) {
            showDiaglogMessage(ErrorMessage.EMPTY_SELECTED_ROWS);
            return;
        }
        if (rows.length > 1) {
            showDiaglogMessage(ErrorMessage.EXCEED_SELECTED_ROWS);
            return;
        }

        this.showEditProductDiaglog(rows[0]);
    }//GEN-LAST:event_editProductButtonActionPerformed

    private void showEditProductDiaglog(int selectedRow) {
        Integer id = Integer.valueOf(productsTable.getValueAt(selectedRow, ID_COL_INDEX).toString());

        Optional<Product> found = productController.findById(id);

        if (found.isEmpty()) {
            showDiaglogMessage(ErrorMessage.Product.BLANK_INPUT);
            return;
        }

        Product foundProduct = found.get();

        editProductIdTextField.setText(foundProduct.getId().toString());
        editProductNameTextField.setText(foundProduct.getName());
        editProductProcessorTextField.setText(foundProduct.getProcessor());
        editProductMemoryTextField.setText(foundProduct.getMemory());
        editProductStorageTextField.setText(foundProduct.getStorage());
        editProductDisplayTextField.setText(foundProduct.getDisplay());
        editProductBatteryTextField.setText(foundProduct.getBattery());
        editProductCardTextField.setText(foundProduct.getCard());
        editProductWeightTextField.setText(foundProduct.getWeight());

        editProductDiaglog.setVisible(true);
        editProductDiaglog.setLocationRelativeTo(this);
    }

    private void addProductButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductButtonActionPerformed
        showAddProductDiaglog();
    }//GEN-LAST:event_addProductButtonActionPerformed

    private void showAddProductDiaglog() {
        addProductDiaglog.setVisible(true);
        addProductDiaglog.setLocationRelativeTo(this);
    }

    private void deleteProductButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteProductButtonActionPerformed
        int[] rows = productsTable.getSelectedRows();

        if (rows.length == 0) {
            this.showDiaglogMessage(ErrorMessage.EMPTY_SELECTED_ROWS);
            return;
        }

        showDeleteProductConfirmDiaglog(String.format("Bạn có chắc chắn xoá %d bản ghi này?", rows.length));
    }//GEN-LAST:event_deleteProductButtonActionPerformed

    private void showDeleteProductConfirmDiaglog(String message) {
        this.deleteProductConfirmDiaglog.setVisible(true);
        this.deleteProductConfirmDiaglog.setLocationRelativeTo(this);
        this.deleteProductConfirmDiaglogLabel.setText(message);
    }

    private void searchUsersTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchUsersTextFieldKeyReleased
        String keyword = searchUsersTextField.getText();
        loadDataToTableUsers(keyword);
    }//GEN-LAST:event_searchUsersTextFieldKeyReleased

    private void usernameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameTextFieldActionPerformed

    private void firstNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstNameTextFieldActionPerformed

    private void lastNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lastNameTextFieldActionPerformed

    private void phoneNumberTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phoneNumberTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_phoneNumberTextFieldActionPerformed

    private void emailTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailTextFieldActionPerformed

    private void confirmEmailOTPTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmEmailOTPTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_confirmEmailOTPTextFieldActionPerformed

    private void logoutTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutTabMouseClicked
        this.logoutLabelMouseClicked(evt);
    }//GEN-LAST:event_logoutTabMouseClicked

    private void logoutLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutLabelMouseClicked
        this.setActiveTab("");
        this.logoutDiaglog.setVisible(true);
        this.logoutDiaglog.setLocationRelativeTo(this);
    }//GEN-LAST:event_logoutLabelMouseClicked

    private void updateInfoTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateInfoTabMouseClicked
        this.updateInfoLabelMouseClicked(evt);
    }//GEN-LAST:event_updateInfoTabMouseClicked

    private void updateInfoLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateInfoLabelMouseClicked
        this.setActiveTab("");
    }//GEN-LAST:event_updateInfoLabelMouseClicked

    private void statisticsTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_statisticsTabMouseClicked
        this.statisticsLabelMouseClicked(evt);
    }//GEN-LAST:event_statisticsTabMouseClicked

    private void statisticsLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_statisticsLabelMouseClicked
        this.setActiveTab("statistics");
        this.setDisplayedPanel("statistics");
    }//GEN-LAST:event_statisticsLabelMouseClicked

    private void userTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userTabMouseClicked
        this.userLabelMouseClicked(evt);
    }//GEN-LAST:event_userTabMouseClicked

    private void userLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userLabelMouseClicked
        this.setActiveTab("user");
        this.setDisplayedPanel("user");
        this.loadDataToTableUsers(null);
    }//GEN-LAST:event_userLabelMouseClicked

    private void inStockTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inStockTabMouseClicked
        this.inStockLabelMouseClicked(evt);
    }//GEN-LAST:event_inStockTabMouseClicked

    private void inStockLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inStockLabelMouseClicked
        this.setActiveTab("in-stock");
        this.setDisplayedPanel("in-stock");
        loadDataToInStocksTable(null);
    }//GEN-LAST:event_inStockLabelMouseClicked

    private void exportBillTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exportBillTabMouseClicked
        this.exportBillLabelMouseClicked(evt);
    }//GEN-LAST:event_exportBillTabMouseClicked

    private void exportBillLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exportBillLabelMouseClicked
        this.setActiveTab("export-bill");
        this.setDisplayedPanel("export-bill");
        loadDataToExportBillsTable(null);
    }//GEN-LAST:event_exportBillLabelMouseClicked

    private void exportProductTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exportProductTabMouseClicked
        this.exportProductLabelMouseClicked(evt);
    }//GEN-LAST:event_exportProductTabMouseClicked

    private void exportProductLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exportProductLabelMouseClicked
        this.setActiveTab("export-product");
        this.setDisplayedPanel("export-product");
        loadDataToExportInStockProductsTable(inventoryItems);
    }//GEN-LAST:event_exportProductLabelMouseClicked

    private void importBillTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_importBillTabMouseClicked
        this.importBillLabelMouseClicked(evt);
    }//GEN-LAST:event_importBillTabMouseClicked

    private void importBillLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_importBillLabelMouseClicked
        this.setActiveTab("import-bill");
        this.setDisplayedPanel("import-bill");
        loadDataToTableImportBills(null);
    }//GEN-LAST:event_importBillLabelMouseClicked

    private void importProductTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_importProductTabMouseClicked
        this.importProductLabelMouseClicked(evt);
    }//GEN-LAST:event_importProductTabMouseClicked

    private void importProductLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_importProductLabelMouseClicked
        this.setActiveTab("import-product");
        this.setDisplayedPanel("import-product");
        loadDataToTableImportProducts(null);
    }//GEN-LAST:event_importProductLabelMouseClicked

    private void providerTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_providerTabMouseClicked
        this.providerLabelMouseClicked(evt);
    }//GEN-LAST:event_providerTabMouseClicked

    private void providerLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_providerLabelMouseClicked
        this.setActiveTab("provider");
        this.setDisplayedPanel("provider");
        loadDataToTableProviders(null);
    }//GEN-LAST:event_providerLabelMouseClicked

    private void productTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productTabMouseClicked
        this.productLabelMouseClicked(evt);
    }//GEN-LAST:event_productTabMouseClicked

    private void productLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productLabelMouseClicked
        this.setActiveTab("product");
        this.setDisplayedPanel("product");
        loadDataToTableProducts(null);
    }//GEN-LAST:event_productLabelMouseClicked

    private void addUserDiaglogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserDiaglogButtonActionPerformed
        String username = usernameTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String email = emailTextField.getText();
        String otp = confirmEmailOTPTextField.getText();
        String password = String.valueOf(passwordField.getPassword());
        String confirmPassword = String.valueOf(confirmPasswordField.getPassword());
        if (InputValidator.anyBlankInput(
                username, firstName, lastName, phoneNumber, email,
                otp, password, confirmPassword)) {
            showDiaglogMessage(ErrorMessage.User.BLANK_INPUT);
            return;
        }

        if (!password.equals(confirmPassword)) {
            showDiaglogMessage(ErrorMessage.User.MISMATCHED_PASSWORD);
            return;
        }

        if (userController.findByUsername(username).isPresent()) {
            showDiaglogMessage(ErrorMessage.User.DUPLICATED_USERNAME);
            return;
        }

        CommonResponseDTO result = userController.addOne(User
                .builder()
                .username(username)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .email(email)
                .build());
        if (result.success()) {
            this.closeAddProductDiaglog();
            showDiaglogMessage(SuccessMessage.User.ADDED);
            loadDataToTableUsers(null);
        }
    }//GEN-LAST:event_addUserDiaglogButtonActionPerformed

    private void showDiaglogMessage(String message) {
        diaglogMessage.setVisible(true);
        diaglogMessage.setLocationRelativeTo(this);
        diaglogMessageLabel.setText(message);
    }

    private void cancelAddUserDiaglogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelAddUserDiaglogButtonActionPerformed
        this.closeAddUserDiaglog();
    }//GEN-LAST:event_cancelAddUserDiaglogButtonActionPerformed

    private void closeAddUserDiaglog() {
        this.clearAddUserDiaglogFields();
        addUserDiaglog.dispose();
    }

    private void clearAddUserDiaglogFields() {
        usernameTextField.setText("");
        firstNameTextField.setText("");
        lastNameTextField.setText("");
        phoneNumberTextField.setText("");
        emailTextField.setText("");
        confirmEmailOTPTextField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
    }

    private void confirmDeleteUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmDeleteUserActionPerformed
        deleteUserConfirmDiaglog.dispose();
        int[] rows = usersTable.getSelectedRows();
        for (int row : rows) {
            userController.deleteOne(Integer.valueOf(usersTable.getValueAt(row, ID_COL_INDEX).toString()));
        }
        loadDataToTableUsers(null);
        showDiaglogMessage(String.format("Xoá thành công %d bản ghi.", rows.length));

    }//GEN-LAST:event_confirmDeleteUserActionPerformed

    private void cancelDeleteUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelDeleteUserActionPerformed
        deleteUserConfirmDiaglog.dispose();
    }//GEN-LAST:event_cancelDeleteUserActionPerformed

    private void diaglogMessageOkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diaglogMessageOkButtonActionPerformed
        diaglogMessage.dispose();
    }//GEN-LAST:event_diaglogMessageOkButtonActionPerformed

    private void editUsernameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editUsernameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editUsernameTextFieldActionPerformed

    private void editFirstNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editFirstNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editFirstNameTextFieldActionPerformed

    private void editLastNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editLastNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editLastNameTextFieldActionPerformed

    private void editPhoneNumberTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editPhoneNumberTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editPhoneNumberTextFieldActionPerformed

    private void editEmailTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editEmailTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editEmailTextFieldActionPerformed

    private void editUserDiaglogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editUserDiaglogButtonActionPerformed
        Integer id = Integer.valueOf(editUserIdTextField.getText());
        String firstName = editFirstNameTextField.getText();
        String lastName = editLastNameTextField.getText();
        String email = editEmailTextField.getText();
        String phoneNumber = editPhoneNumberTextField.getText();

        CommonResponseDTO response = userController.updateOne(id, new UserDTO(firstName, lastName, phoneNumber, email));
        showDiaglogMessage(response.message());
        loadDataToTableUsers(null);
        editUserDiaglog.dispose();
    }//GEN-LAST:event_editUserDiaglogButtonActionPerformed

    private void cancelEditUserDiaglogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelEditUserDiaglogButtonActionPerformed
        // TODO add your handling code here:
        editUserDiaglog.dispose();
    }//GEN-LAST:event_cancelEditUserDiaglogButtonActionPerformed

    private void editUserIdTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editUserIdTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editUserIdTextFieldActionPerformed

    private void addUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserButtonActionPerformed
        addUserDiaglog.setLocationRelativeTo(this);
        addUserDiaglog.setVisible(true);
    }//GEN-LAST:event_addUserButtonActionPerformed

    private void viewUserLastNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewUserLastNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewUserLastNameTextFieldActionPerformed

    private void viewUserPhoneNumberTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewUserPhoneNumberTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewUserPhoneNumberTextFieldActionPerformed

    private void viewUserEmailTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewUserEmailTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewUserEmailTextFieldActionPerformed

    private void viewUserWhenCreatedTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewUserWhenCreatedTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewUserWhenCreatedTextFieldActionPerformed

    private void viewUserLastUpdatedTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewUserLastUpdatedTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewUserLastUpdatedTextFieldActionPerformed

    private void cancelAddUserDiaglogButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelAddUserDiaglogButton1ActionPerformed
        viewUserDiaglog.dispose();
    }//GEN-LAST:event_cancelAddUserDiaglogButton1ActionPerformed

    private void editUserDiaglogButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editUserDiaglogButton1ActionPerformed
        // TODO add your handling code here:
        int[] rows = usersTable.getSelectedRows();
        if (rows.length == 0) {
            showDiaglogMessage(ErrorMessage.EMPTY_SELECTED_ROWS);
            return;
        }
        if (rows.length > 1) {
            showDiaglogMessage(ErrorMessage.EXCEED_SELECTED_ROWS);
            return;
        }
        viewUserDiaglog.dispose();
        showEditUserDiaglog(rows[0]);
    }//GEN-LAST:event_editUserDiaglogButton1ActionPerformed

    private void viewUserLastLoggedInTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewUserLastLoggedInTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewUserLastLoggedInTextFieldActionPerformed

    private void viewUserLoggedInTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewUserLoggedInTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewUserLoggedInTextFieldActionPerformed

    private void viewUsernameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewUsernameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewUsernameTextFieldActionPerformed

    private void viewUserFirstNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewUserFirstNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewUserFirstNameTextFieldActionPerformed

    private void searchUsersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchUsersButtonActionPerformed
        searchUsersTextField.setText("");
        loadDataToTableUsers(null);
    }//GEN-LAST:event_searchUsersButtonActionPerformed

    private void viewProductButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewProductButtonActionPerformed
        int[] rows = productsTable.getSelectedRows();
        if (rows.length == 0) {
            showDiaglogMessage(ErrorMessage.EMPTY_SELECTED_ROWS);
            return;
        }
        if (rows.length > 1) {
            showDiaglogMessage(ErrorMessage.EXCEED_SELECTED_ROWS);
            return;
        }
        this.showViewProductDiaglog(rows[0]);
    }//GEN-LAST:event_viewProductButtonActionPerformed

    private void showViewProductDiaglog(int selectedRow) {
        Integer id = Integer.valueOf(productsTable.getValueAt(selectedRow, ID_COL_INDEX).toString());

        this.viewProductDiaglog.setVisible(true);
        this.viewProductDiaglog.setLocationRelativeTo(this);

        Optional<Product> found = productController.findById(id);

        if (found.isEmpty()) {
            showDiaglogMessage(ErrorMessage.Product.NOT_FOUND);
            return;
        }

        Product foundProduct = found.get();
        viewProductIdTextField.setText(foundProduct.getId().toString());
        viewProductNameTextField.setText(foundProduct.getName());
        viewProductProcessorTextField.setText(foundProduct.getProcessor());
        viewProductMemoryTextField.setText(foundProduct.getMemory());
        viewProductStorageTextField.setText(foundProduct.getStorage());
        viewProductDisplayTextField.setText(foundProduct.getDisplay());
        viewProductBatteryTextField.setText(foundProduct.getBattery());
        viewProductCardTextField.setText(foundProduct.getCard());
        viewProductWeightTextField.setText(foundProduct.getWeight());
        if (foundProduct.getWhenCreated() != null) {
            viewProductWhenCreatedTextField.setText(formatter.format(foundProduct.getWhenCreated()));
        }
        if (foundProduct.getLastUpdated() != null) {
            viewProductLastUpdatedTextField.setText(formatter.format(foundProduct.getLastUpdated()));
        }

    }

    private void confirmDeleteProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmDeleteProductActionPerformed
        int[] rows = productsTable.getSelectedRows();

        for (int row : rows) {
            Integer id = Integer.valueOf(productsTable.getValueAt(row, ID_COL_INDEX).toString());
            productController.deleteOne(id);
        }

        deleteProductConfirmDiaglog.dispose();
        loadDataToTableProducts(null);
        showDiaglogMessage(String.format("Xoá thành công %d sản phẩm.", rows.length));
    }//GEN-LAST:event_confirmDeleteProductActionPerformed

    private void cancelDeleteProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelDeleteProductActionPerformed
        deleteProductConfirmDiaglog.dispose();
    }//GEN-LAST:event_cancelDeleteProductActionPerformed

    private void productProcessorTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productProcessorTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_productProcessorTextFieldActionPerformed

    private void productStorageTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productStorageTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_productStorageTextFieldActionPerformed

    private void productDisplayTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productDisplayTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_productDisplayTextFieldActionPerformed

    private void productBatteryTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productBatteryTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_productBatteryTextFieldActionPerformed

    private void productCardTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productCardTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_productCardTextFieldActionPerformed

    private void addProductDiaglogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductDiaglogButtonActionPerformed
        String name = productNameTextField.getText();
        String processor = productProcessorTextField.getText();
        String ram = productMemoryTextField.getText();
        String storage = productStorageTextField.getText();
        String display = productDisplayTextField.getText();
        String battery = productBatteryTextField.getText();
        String card = productCardTextField.getText();
        String weight = productWeightTextField.getText();

        if (InputValidator.anyBlankInput(name, processor, ram, storage, display, battery, card, weight)) {
            showDiaglogMessage(ErrorMessage.Product.BLANK_INPUT);
            return;
        }

        CommonResponseDTO response = productController.addOne(
                Product.builder()
                        .name(name)
                        .processor(processor)
                        .memory(ram)
                        .storage(storage)
                        .display(display)
                        .battery(battery)
                        .card(card)
                        .weight(weight)
                        .build()
        );

        this.closeAddProductDiaglog();
        showDiaglogMessage(response.message());
        loadDataToTableProducts(null);
    }//GEN-LAST:event_addProductDiaglogButtonActionPerformed

    private void cancelAddProductDiaglogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelAddProductDiaglogButtonActionPerformed
        this.closeAddProductDiaglog();
    }//GEN-LAST:event_cancelAddProductDiaglogButtonActionPerformed

    private void closeAddProductDiaglog() {
        this.addProductDiaglog.dispose();
        clearAddProductDiaglogFields();
    }

    private void clearAddProductDiaglogFields() {
        productNameTextField.setText("");
        productProcessorTextField.setText("");
        productMemoryTextField.setText("");
        productStorageTextField.setText("");
        productDisplayTextField.setText("");
        productBatteryTextField.setText("");
        productCardTextField.setText("");
        productWeightTextField.setText("");
    }

    private void productWeightTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productWeightTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_productWeightTextFieldActionPerformed

    private void productMemoryTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productMemoryTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_productMemoryTextFieldActionPerformed

    private void productNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_productNameTextFieldActionPerformed

    private void editProductProcessorTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProductProcessorTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editProductProcessorTextFieldActionPerformed

    private void editProductStorageTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProductStorageTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editProductStorageTextFieldActionPerformed

    private void editProductDisplayTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProductDisplayTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editProductDisplayTextFieldActionPerformed

    private void editProductBatteryTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProductBatteryTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editProductBatteryTextFieldActionPerformed

    private void editProductCardTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProductCardTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editProductCardTextFieldActionPerformed

    private void editProductDiaglogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProductDiaglogButtonActionPerformed
        Integer id = Integer.valueOf(editProductIdTextField.getText());
        String name = editProductNameTextField.getText();
        String processor = editProductProcessorTextField.getText();
        String memory = editProductMemoryTextField.getText();
        String storage = editProductStorageTextField.getText();
        String display = editProductDisplayTextField.getText();
        String battery = editProductBatteryTextField.getText();
        String card = editProductCardTextField.getText();
        String weight = editProductWeightTextField.getText();

        CommonResponseDTO response = productController.updateOne(
                id,
                Product.builder()
                        .name(name)
                        .processor(processor)
                        .memory(memory)
                        .storage(storage)
                        .display(display)
                        .battery(battery)
                        .card(card)
                        .weight(weight)
                        .build()
        );

        this.loadDataToTableProducts(null);
        this.editProductDiaglog.dispose();
        this.showDiaglogMessage(response.message());

    }//GEN-LAST:event_editProductDiaglogButtonActionPerformed

    private void cancelEditProductDiaglogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelEditProductDiaglogButtonActionPerformed
        this.editProductDiaglog.dispose();
    }//GEN-LAST:event_cancelEditProductDiaglogButtonActionPerformed

    private void editProductWeightTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProductWeightTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editProductWeightTextFieldActionPerformed

    private void editProductMemoryTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProductMemoryTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editProductMemoryTextFieldActionPerformed

    private void editProductNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProductNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editProductNameTextFieldActionPerformed

    private void editProductIdTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProductIdTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editProductIdTextFieldActionPerformed

    private void searchProductsTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchProductsTextFieldKeyReleased
        String keyword = searchProductsTextField.getText();
        loadDataToTableProducts(keyword);
    }//GEN-LAST:event_searchProductsTextFieldKeyReleased

    private void searchProductsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchProductsButtonActionPerformed
        searchProductsTextField.setText("");
        loadDataToTableProducts(null);
    }//GEN-LAST:event_searchProductsButtonActionPerformed

    private void viewProductProcessorTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewProductProcessorTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewProductProcessorTextFieldActionPerformed

    private void viewProductStorageTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewProductStorageTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewProductStorageTextFieldActionPerformed

    private void viewProductDisplayTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewProductDisplayTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewProductDisplayTextFieldActionPerformed

    private void viewProductBatteryTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewProductBatteryTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewProductBatteryTextFieldActionPerformed

    private void viewProductCardTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewProductCardTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewProductCardTextFieldActionPerformed

    private void updateProductDiaglogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateProductDiaglogButtonActionPerformed
        int[] rows = productsTable.getSelectedRows();

        this.viewProductDiaglog.dispose();

        this.showEditProductDiaglog(rows[0]);
    }//GEN-LAST:event_updateProductDiaglogButtonActionPerformed

    private void cancelViewProductDiaglogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelViewProductDiaglogButtonActionPerformed
        this.viewProductDiaglog.dispose();
    }//GEN-LAST:event_cancelViewProductDiaglogButtonActionPerformed

    private void viewProductWeightTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewProductWeightTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewProductWeightTextFieldActionPerformed

    private void viewProductMemoryTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewProductMemoryTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewProductMemoryTextFieldActionPerformed

    private void viewProductNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewProductNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewProductNameTextFieldActionPerformed

    private void viewProductIdTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewProductIdTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewProductIdTextFieldActionPerformed

    private void viewProductWhenCreatedTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewProductWhenCreatedTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewProductWhenCreatedTextFieldActionPerformed

    private void viewProductLastUpdatedTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewProductLastUpdatedTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewProductLastUpdatedTextFieldActionPerformed

    private void importProvidersFromExcelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importProvidersFromExcelButtonActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Workbook (*.xlsx)", "xlsx");
        fileChooser.setFileFilter(filter);
        fileChooser.setMultiSelectionEnabled(false);
        int result = fileChooser.showDialog(this, "Chọn file");
        if (result == JFileChooser.APPROVE_OPTION) {
            File uploadedFile = fileChooser.getSelectedFile();
            ArrayList<Provider> providers = ExcelUtil.excelToProviders(uploadedFile);
            CommonResponseDTO response = providerController.addList(providers);
            showDiaglogMessage(response.message());
            loadDataToTableProviders(null);
        }
    }//GEN-LAST:event_importProvidersFromExcelButtonActionPerformed

    private void exportProvidersToExcelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportProvidersToExcelButtonActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lựa chọn vị trí lưu file");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Workbook (*.xlsx)", "xlsx");
        fileChooser.setFileFilter(filter);
        int userSelection = fileChooser.showSaveDialog(fileChooser);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File directoryToSave = fileChooser.getSelectedFile();
            CommonResponseDTO response = ExcelUtil.providersToExcel(
                    providerController.getList().data(),
                    directoryToSave.getAbsolutePath()
            );
            showDiaglogMessage(response.message());
        }
    }//GEN-LAST:event_exportProvidersToExcelButtonActionPerformed

    private void editProviderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProviderButtonActionPerformed
        int[] rows = providersTable.getSelectedRows();
        if (rows.length == 0) {
            showDiaglogMessage(ErrorMessage.EMPTY_SELECTED_ROWS);
            return;
        }
        if (rows.length > 1) {
            showDiaglogMessage(ErrorMessage.EXCEED_SELECTED_ROWS);
            return;
        }

        this.showEditProviderDiaglog(rows[0]);
    }//GEN-LAST:event_editProviderButtonActionPerformed

    private void showEditProviderDiaglog(int selectedRow) {
        editProviderDiaglog.setVisible(true);
        editProviderDiaglog.setLocationRelativeTo(this);

        Integer id = Integer.valueOf(providersTable.getValueAt(selectedRow, ID_COL_INDEX).toString());
        Optional<Provider> found = providerController.findById(id);
        if (found.isEmpty()) {
            showDiaglogMessage(ErrorMessage.Provider.NOT_FOUND);
            return;
        }

        Provider foundProvider = found.get();

        editProviderIdTextField.setText(foundProvider.getId().toString());
        editProviderNameTextField.setText(foundProvider.getName());
        editProviderPhoneNumberTextField.setText(foundProvider.getPhoneNumber());
        editProviderEmailTextField.setText(foundProvider.getEmail());
        editProviderAddressTextField.setText(foundProvider.getAddress());
    }

    private void addProviderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProviderButtonActionPerformed
        showAddProviderDiaglog();
    }//GEN-LAST:event_addProviderButtonActionPerformed

    private void showAddProviderDiaglog() {
        addProviderDiaglog.setVisible(true);
        addProviderDiaglog.setLocationRelativeTo(this);
    }

    private void deleteProviderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteProviderButtonActionPerformed
        int[] rows = providersTable.getSelectedRows();

        if (rows.length == 0) {
            this.showDiaglogMessage(ErrorMessage.EMPTY_SELECTED_ROWS);
            return;
        }

        showDeleteProviderConfirmDiaglog(String.format("Bạn có chắc chắn xoá %d bản ghi này?", rows.length));
    }//GEN-LAST:event_deleteProviderButtonActionPerformed

    private void showDeleteProviderConfirmDiaglog(String message) {
        this.deleteProviderConfirmDiaglog.setVisible(true);
        this.deleteProviderConfirmDiaglog.setLocationRelativeTo(this);
        this.deleteProviderConfirmDiaglogLabel.setText(message);
    }


    private void viewProviderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewProviderButtonActionPerformed
        int[] rows = providersTable.getSelectedRows();
        if (rows.length == 0) {
            showDiaglogMessage(ErrorMessage.EMPTY_SELECTED_ROWS);
            return;
        }
        if (rows.length > 1) {
            showDiaglogMessage(ErrorMessage.EXCEED_SELECTED_ROWS);
            return;
        }
        this.showViewProviderDiaglog(rows[0]);
    }//GEN-LAST:event_viewProviderButtonActionPerformed

    private void showViewProviderDiaglog(int selectedRow) {
        viewProviderDiaglog.setVisible(true);
        viewProviderDiaglog.setLocationRelativeTo(this);

        Integer id = Integer.valueOf(providersTable.getValueAt(selectedRow, ID_COL_INDEX).toString());
        Optional<Provider> found = providerController.findById(id);
        if (found.isEmpty()) {
            showDiaglogMessage(ErrorMessage.Provider.NOT_FOUND);
            return;
        }

        Provider foundProvider = found.get();
        viewProviderIdTextField.setText(foundProvider.getId().toString());
        viewProviderNameTextField.setText(foundProvider.getName());
        viewProviderPhoneNumberTextField.setText(foundProvider.getPhoneNumber());
        viewProviderEmailTextField.setText(foundProvider.getEmail());
        viewProviderAddressTextField.setText(foundProvider.getAddress());
        viewProviderWhenCreatedTextField.setText(formatter.format(foundProvider.getWhenCreated()));
        if (foundProvider.getLastUpdated() != null) {
            viewProviderLastUpdatedTextField.setText(formatter.format(foundProvider.getLastUpdated()));
        }
    }

    private void searchProvidersTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchProvidersTextFieldKeyReleased
        String keyword = searchProvidersTextField.getText();
        loadDataToTableProviders(keyword);
    }//GEN-LAST:event_searchProvidersTextFieldKeyReleased

    private void resetSearchProvidersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetSearchProvidersButtonActionPerformed
        searchProvidersTextField.setText("");
        loadDataToTableProviders(null);
    }//GEN-LAST:event_resetSearchProvidersButtonActionPerformed

    private void addProviderPhoneNumberTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProviderPhoneNumberTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addProviderPhoneNumberTextFieldActionPerformed

    private void addProviderAddressTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProviderAddressTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addProviderAddressTextFieldActionPerformed

    private void addProviderDiaglogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProviderDiaglogButtonActionPerformed
        String name = addProviderNameTextField.getText();
        String phoneNumber = addProviderPhoneNumberTextField.getText();
        String email = addProviderEmailTextField.getText();
        String address = addProviderAddressTextField.getText();

        if (InputValidator.anyBlankInput(name, phoneNumber, email, address)) {
            showDiaglogMessage(ErrorMessage.Provider.BLANK_INPUT);
            return;
        }

        CommonResponseDTO response = providerController.addOne(
                Provider.builder()
                        .name(name)
                        .phoneNumber(phoneNumber)
                        .email(email)
                        .address(address)
                        .build()
        );
        closeAddProviderDiaglog();
        loadDataToTableProviders(null);
        showDiaglogMessage(response.message());
    }//GEN-LAST:event_addProviderDiaglogButtonActionPerformed

    private void cancelAddProviderDiaglogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelAddProviderDiaglogButtonActionPerformed
        closeAddProviderDiaglog();
    }//GEN-LAST:event_cancelAddProviderDiaglogButtonActionPerformed

    private void closeAddProviderDiaglog() {
        addProviderDiaglog.dispose();
        clearAddProviderDiaglogFields();
    }

    private void clearAddProviderDiaglogFields() {
        addProviderNameTextField.setText("");
        addProviderPhoneNumberTextField.setText("");
        addProviderEmailTextField.setText("");
        addProviderAddressTextField.setText("");
    }

    private void addProviderEmailTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProviderEmailTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addProviderEmailTextFieldActionPerformed

    private void addProviderNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProviderNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addProviderNameTextFieldActionPerformed

    private void viewProviderPhoneNumberTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewProviderPhoneNumberTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewProviderPhoneNumberTextFieldActionPerformed

    private void viewProviderAddressTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewProviderAddressTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewProviderAddressTextFieldActionPerformed

    private void updateProviderDiaglogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateProviderDiaglogButtonActionPerformed
        int[] rows = providersTable.getSelectedRows();

        this.viewProviderDiaglog.dispose();

        this.showEditProviderDiaglog(rows[0]);
    }//GEN-LAST:event_updateProviderDiaglogButtonActionPerformed

    private void cancelViewProviderDiaglogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelViewProviderDiaglogButtonActionPerformed
        viewProviderDiaglog.dispose();
    }//GEN-LAST:event_cancelViewProviderDiaglogButtonActionPerformed

    private void viewProviderEmailTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewProviderEmailTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewProviderEmailTextFieldActionPerformed

    private void viewProviderNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewProviderNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewProviderNameTextFieldActionPerformed

    private void viewProviderIdTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewProviderIdTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewProviderIdTextFieldActionPerformed

    private void editProviderPhoneNumberTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProviderPhoneNumberTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editProviderPhoneNumberTextFieldActionPerformed

    private void editProviderAddressTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProviderAddressTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editProviderAddressTextFieldActionPerformed

    private void editProviderDiaglogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProviderDiaglogButtonActionPerformed
        Integer id = Integer.valueOf(editProviderIdTextField.getText());
        String name = editProviderNameTextField.getText();
        String phoneNumber = editProviderPhoneNumberTextField.getText();
        String email = editProviderEmailTextField.getText();
        String address = editProviderAddressTextField.getText();

        CommonResponseDTO response = providerController.updateOne(
                id,
                Provider.builder()
                        .name(name)
                        .phoneNumber(phoneNumber)
                        .email(email)
                        .address(address)
                        .build()
        );

        this.loadDataToTableProviders(null);
        this.editProviderDiaglog.dispose();
        this.showDiaglogMessage(response.message());
    }//GEN-LAST:event_editProviderDiaglogButtonActionPerformed

    private void cancelEditProviderDiaglogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelEditProviderDiaglogButtonActionPerformed
        editProviderDiaglog.dispose();
    }//GEN-LAST:event_cancelEditProviderDiaglogButtonActionPerformed

    private void editProviderEmailTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProviderEmailTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editProviderEmailTextFieldActionPerformed

    private void editProviderNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProviderNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editProviderNameTextFieldActionPerformed

    private void editProviderIdTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProviderIdTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editProviderIdTextFieldActionPerformed

    private void confirmDeleteProviderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmDeleteProviderActionPerformed
        int[] rows = providersTable.getSelectedRows();

        for (int row : rows) {
            Integer id = Integer.valueOf(providersTable.getValueAt(row, ID_COL_INDEX).toString());
            providerController.deleteOne(id);
        }

        deleteProviderConfirmDiaglog.dispose();
        loadDataToTableProviders(null);
        showDiaglogMessage(String.format("Xoá thành công %d nhà cung cấp.", rows.length));
    }//GEN-LAST:event_confirmDeleteProviderActionPerformed

    private void cancelDeleteProviderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelDeleteProviderActionPerformed
        deleteProductConfirmDiaglog.dispose();
    }//GEN-LAST:event_cancelDeleteProviderActionPerformed

    private void viewProductStorageTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewProductStorageTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewProductStorageTextField2ActionPerformed

    private void viewProviderWhenCreatedTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewProviderWhenCreatedTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewProviderWhenCreatedTextFieldActionPerformed

    private void viewProviderLastUpdatedTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewProviderLastUpdatedTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewProviderLastUpdatedTextFieldActionPerformed

    private void searchImportProductTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchImportProductTextFieldKeyReleased
        String keyword = searchImportProductTextField.getText();
        loadDataToTableImportProducts(keyword);
    }//GEN-LAST:event_searchImportProductTextFieldKeyReleased

    private void providerNameComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_providerNameComboBoxActionPerformed
        Optional<Provider> found = providerController.findByName(providerNameComboBox.getSelectedItem().toString());
        found.ifPresent(item -> importBill.setProvider(item));
    }//GEN-LAST:event_providerNameComboBoxActionPerformed

    private void editBillItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBillItemButtonActionPerformed
        if (importBill.getImportBillItems().isEmpty()) {
            showDiaglogMessage("Danh sách này hiện đang trống.");
            return;
        }

        int[] rows = importProductBillTable.getSelectedRows();
        if (rows.length == 0) {
            showDiaglogMessage(ErrorMessage.EMPTY_SELECTED_ROWS);
            return;
        }

        if (rows.length > 1) {
            showDiaglogMessage(ErrorMessage.EXCEED_SELECTED_ROWS);
            return;
        }

        showEditImportBillItemDiaglog(rows[0]);

    }//GEN-LAST:event_editBillItemButtonActionPerformed

    private void showEditImportBillItemDiaglog(int selectedRow) {
        editImportBillItemDiaglog.setVisible(true);
        editImportBillItemDiaglog.setLocationRelativeTo(this);

        Integer quantity = Integer.valueOf(
                importProductBillTable.getValueAt(selectedRow, IMPORT_BILL_ITEM_QUANTITY_COL_INDEX).toString()
        );

        String price = importProductBillTable
                .getValueAt(
                        selectedRow,
                        IMPORT_BILL_ITEM_PRICE_COL_INDEX
                )
                .toString()
                .replace("đ", "");

        editImportBillItemIndexTextField.setText(importProductBillTable.getValueAt(selectedRow, ID_COL_INDEX).toString());
        editImportBillItemProductIdTextField.setText(importProductBillTable.getValueAt(selectedRow, IMPORT_BILL_ITEM_PRODUCT_ID_COL_INDEX).toString());
        editImportBillItemProductNameTextField.setText(importProductBillTable.getValueAt(selectedRow, IMPORT_BILL_ITEM_PRODUCT_NAME_COL_INDEX).toString());
        editImportBillItemQuantityTextField.setText(quantity.toString());
        editImportBillItemPriceTextField.setText(price);
    }


    private void editImportBillItemPriceTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editImportBillItemPriceTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editImportBillItemPriceTextFieldActionPerformed

    private void editImportBillItemDiaglogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editImportBillItemDiaglogButtonActionPerformed
        int targetIndex = Integer.parseInt(editImportBillItemIndexTextField.getText()) - 1;

        int quantity = Integer.parseInt(editImportBillItemQuantityTextField.getText());
        float price = Float.parseFloat(editImportBillItemPriceTextField.getText().replace(",", ""));

        importBill.getImportBillItems().get(targetIndex).setQuantity(quantity);
        importBill.getImportBillItems().get(targetIndex).setImportPrice(price);

        showDiaglogMessage("Cập nhật thành công.");
        editImportBillItemDiaglog.dispose();
        loadDataToImportProductBillTable(importBill);
    }//GEN-LAST:event_editImportBillItemDiaglogButtonActionPerformed

    private void cancelEditImportBillItemDiaglogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelEditImportBillItemDiaglogButtonActionPerformed
        editImportBillItemDiaglog.dispose();
    }//GEN-LAST:event_cancelEditImportBillItemDiaglogButtonActionPerformed

    private void editImportBillItemQuantityTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editImportBillItemQuantityTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editImportBillItemQuantityTextFieldActionPerformed

    private void editImportBillItemProductNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editImportBillItemProductNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editImportBillItemProductNameTextFieldActionPerformed

    private void editImportBillItemProductIdTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editImportBillItemProductIdTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editImportBillItemProductIdTextFieldActionPerformed

    private void editImportBillItemIndexTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editImportBillItemIndexTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editImportBillItemIndexTextFieldActionPerformed

    private void removeBillItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBillItemButtonActionPerformed
        if (importBill.getImportBillItems().isEmpty()) {
            showDiaglogMessage("Danh sách này hiện đang trống.");
            return;
        }

        int[] rows = importProductBillTable.getSelectedRows();
        if (rows.length == 0) {
            showDiaglogMessage(ErrorMessage.EMPTY_SELECTED_ROWS);
            return;
        }

        showDeleteImportBillItemDiaglog(rows[0]);
    }//GEN-LAST:event_removeBillItemButtonActionPerformed

    private void showDeleteImportBillItemDiaglog(int selectedRow) {
        deleteImportBillItemDiaglog.setVisible(true);
        deleteImportBillItemDiaglog.setLocationRelativeTo(this);
    }

    private void deleteImportBillItemDiaglogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteImportBillItemDiaglogButtonActionPerformed
        int[] rows = importProductBillTable.getSelectedRows();
        int targetIndex = Integer.parseInt(importProductBillTable.getValueAt(rows[0], ID_COL_INDEX).toString()) - 1;
        importBill.getImportBillItems().remove(targetIndex);

        loadDataToImportProductBillTable(importBill);
        deleteImportBillItemDiaglog.dispose();
        showDiaglogMessage("Xoá bản ghi thành công.");

    }//GEN-LAST:event_deleteImportBillItemDiaglogButtonActionPerformed

    private void cancelDeleteImportBillItemDiaglogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelDeleteImportBillItemDiaglogButtonActionPerformed
        deleteImportBillItemDiaglog.dispose();
    }//GEN-LAST:event_cancelDeleteImportBillItemDiaglogButtonActionPerformed

    private void importBillProductButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importBillProductButtonActionPerformed
        if (providerNameComboBox.getSelectedIndex() == 0) {
            showDiaglogMessage("Vui lòng chọn nhà cung cấp.");
            return;
        }

        float total = (float) importBill
                .getImportBillItems()
                .parallelStream()
                .mapToDouble(item -> item.getImportPrice() * item.getQuantity())
                .sum();

        importBill.setTotal(total);

        importBillController.addOne(importBill);
        showDiaglogMessage("Nhập sản phẩm thành công.");

        inventoryItems.addAll(
                importBill.getImportBillItems()
                        .parallelStream()
                        .map(item -> InventoryItem
                        .builder()
                        .id(InventoryItem.counter)
                        .importPrice(item.getImportPrice())
                        .quantity(item.getQuantity())
                        .importBillItem(item)
                        .build()
                        )
                        .toList()
        );

        initImportBill();
        System.gc();
        System.out.println(importBill.getImportBillItems().size());
        providerNameComboBox.setSelectedIndex(0);
        loadDataToImportProductBillTable(importBill);
        loadDataToExportInStockProductsTable(inventoryItems);
    }//GEN-LAST:event_importBillProductButtonActionPerformed

    private void searchImportBillsTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchImportBillsTextFieldKeyReleased
        String keyword = searchImportBillsTextField.getText();
        loadDataToTableImportBills(keyword);
    }//GEN-LAST:event_searchImportBillsTextFieldKeyReleased

    private void resetSearchImportBillsButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetSearchImportBillsButton1ActionPerformed
        searchImportBillsTextField.setText("");
        loadDataToTableImportBills(null);
    }//GEN-LAST:event_resetSearchImportBillsButton1ActionPerformed

    private void exportImportBillsToExcelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportImportBillsToExcelButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_exportImportBillsToExcelButtonActionPerformed

    private void editImportBillButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editImportBillButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editImportBillButtonActionPerformed

    private void deleteImportBillButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteImportBillButtonActionPerformed
        int[] rows = importBillsTable.getSelectedRows();

        if (rows.length == 0) {
            this.showDiaglogMessage(ErrorMessage.EMPTY_SELECTED_ROWS);
            return;
        }

        showDeleteImportBillDiaglog(String.format("Bạn có chắc chắn muốn xoá %d bản ghi này?", rows.length));
    }//GEN-LAST:event_deleteImportBillButtonActionPerformed

    private void showDeleteImportBillDiaglog(String message) {
        deleteImportBillDiaglog.setVisible(true);
        deleteImportBillDiaglog.setLocationRelativeTo(this);
        deleteImportBillDiaglogLabel.setText(message);
    }

    private void viewImportBillButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewImportBillButtonActionPerformed
        int[] rows = importBillsTable.getSelectedRows();
        if (rows.length == 0) {
            showDiaglogMessage(ErrorMessage.EMPTY_SELECTED_ROWS);
            return;
        }
        if (rows.length > 1) {
            showDiaglogMessage(ErrorMessage.EXCEED_SELECTED_ROWS);
            return;
        }
        this.showViewImportBillDetailDiaglog(rows[0]);
    }//GEN-LAST:event_viewImportBillButtonActionPerformed

    private void deleteImportBillDiaglogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteImportBillDiaglogButtonActionPerformed
        int[] rows = importBillsTable.getSelectedRows();

        for (int row : rows) {
            int id = Integer.parseInt(importBillsTable.getValueAt(row, ID_COL_INDEX).toString());
            importBillController.deleteOne(id);
        }

        deleteImportBillDiaglog.dispose();
        loadDataToTableImportBills(null);
        showDiaglogMessage(String.format("Xoá thành công %d phiếu nhập.", rows.length));

    }//GEN-LAST:event_deleteImportBillDiaglogButtonActionPerformed

    private void cancelDeleteImportBillDiaglogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelDeleteImportBillDiaglogButtonActionPerformed
        deleteImportBillDiaglog.dispose();
    }//GEN-LAST:event_cancelDeleteImportBillDiaglogButtonActionPerformed

    private void searchInStocksTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchInStocksTextFieldKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_searchInStocksTextFieldKeyReleased

    private void resetSearchInStocksButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetSearchInStocksButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_resetSearchInStocksButtonActionPerformed

    private void exportInStocksToExcelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportInStocksToExcelButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_exportInStocksToExcelButtonActionPerformed

    private void editInStockButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editInStockButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editInStockButtonActionPerformed

    private void deleteInStockButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteInStockButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteInStockButtonActionPerformed

    private void viewInStockButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewInStockButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewInStockButtonActionPerformed

    private void exportBillProductButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportBillProductButtonActionPerformed
        
        String clientName = clientNameTextField.getText().trim();
        if (clientName.isEmpty() || clientName.isBlank()) {
            showDiaglogMessage("Trường tên khách hàng không được để trống.");
            return;
        }
        
        String clientPhoneNumber = clientPhoneNumberTextField.getText().trim();
        if (clientPhoneNumber.isEmpty() || clientPhoneNumber.isBlank()) {
            showDiaglogMessage("Trường số điện thoại khách hàng không được để trống.");
            return;
        }
        
        String clientAddress = clientAddressTextField.getText().trim();
        if (clientAddress.isEmpty() || clientAddress.isBlank()) {
            showDiaglogMessage("Trường địa chỉ khách hàng không được để trống.");
            return;
        }
        
        Double total = exportBill.getExportBillItems()
                .stream()
                .mapToDouble(item -> item.getQuantity() * item.getExportPrice())
                .sum();
        
        exportBill.setTotal(total);
        exportBill.setClientName(clientName);
        exportBill.setClientPhoneNumber(clientPhoneNumber);
        exportBill.setClientAddress(clientAddress);
        exportBillController.addOne(exportBill);
        exportBill.getExportBillItems().forEach(item -> {
            InventoryItem inventoryItem = item.getInventoryItem();
            inventoryItemController.updateOne(inventoryItem.getId(), inventoryItem);
        });
        
        showDiaglogMessage("Xuất hàng thành công.");
        initExportBill();
        System.gc();
        
        loadDataToExportProductsBillTable(exportBill);
        clientNameTextField.setText("");
        clientPhoneNumberTextField.setText("");
        clientAddressTextField.setText("");
        
    }//GEN-LAST:event_exportBillProductButtonActionPerformed

    private void removeExportBillItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeExportBillItemButtonActionPerformed

        if (exportBill.getExportBillItems().isEmpty()) {
            showDiaglogMessage("Danh sách này hiện đang trống.");
            return;
        }

        int[] rows = exportProductsBillTable.getSelectedRows();
        if (rows.length == 0) {
            showDiaglogMessage(ErrorMessage.EMPTY_SELECTED_ROWS);
            return;
        }

        showDeleteExportBillItemDiaglog();
    }//GEN-LAST:event_removeExportBillItemButtonActionPerformed

    private void showDeleteExportBillItemDiaglog() {
        deleteExportBillItemDiaglog.setVisible(true);
        deleteExportBillItemDiaglog.setLocationRelativeTo(this);
    }

    private void editExportBillItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editExportBillItemButtonActionPerformed

        if (exportBill.getExportBillItems().isEmpty()) {
            showDiaglogMessage("Danh sách này hiện đang trống.");
            return;
        }

        int[] rows = exportProductsBillTable.getSelectedRows();
        if (rows.length == 0) {
            showDiaglogMessage(ErrorMessage.EMPTY_SELECTED_ROWS);
            return;
        }

        showEditExportBillItemDiaglog(rows[0]);
    }//GEN-LAST:event_editExportBillItemButtonActionPerformed

    private void showEditExportBillItemDiaglog(int selectedRow) {
        editExportBillItemDiaglog.setVisible(true);
        editExportBillItemDiaglog.setLocationRelativeTo(this);

        editExportBillItemIndexTextField.setText(exportProductsBillTable.getValueAt(selectedRow, ID_COL_INDEX).toString());
        editExportBillItemInventoryIdTextField.setText(exportProductsBillTable.getValueAt(selectedRow, EXPORT_BILL_ITEM_INVENTORY_ID).toString());
        editExportBillItemProductIdTextField.setText(exportProductsBillTable.getValueAt(selectedRow, EXPORT_BILL_ITEM_PRODUCT_ID).toString());
        editExportBillItemProductNameTextField.setText(exportProductsBillTable.getValueAt(selectedRow, EXPORT_BILL_ITEM_PRODUCT_NAME).toString());
        editExportBillItemQuantityTextField.setText(exportProductsBillTable.getValueAt(selectedRow, EXPORT_BILL_ITEM_QUANTITY).toString());
        editExportBillItemPriceTextField.setText(exportProductsBillTable.getValueAt(selectedRow, EXPORT_BILL_ITEM_PRICE).toString().replace("đ", ""));
    }

    private void exportProductAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportProductAddButtonActionPerformed
        int[] rows = exportInStockProductsTable.getSelectedRows();
        if (rows.length == 0) {
            showDiaglogMessage(ErrorMessage.EMPTY_SELECTED_ROWS);
            return;
        }

        Integer selectedInventoryId = Integer.valueOf(exportInStockProductsTable.getValueAt(rows[0], ID_COL_INDEX).toString());

        //InventoryItem selectedInventoryItem = inventoryItemController.findById(selectedId).get();
        InventoryItem selectedInventoryItem
                = inventoryItems.parallelStream()
                        .filter(item -> item.getId().equals(selectedInventoryId))
                        .findFirst()
                        .get();

        if (exportProductQuantityTextField.getText().trim().length() == 0) {
            showDiaglogMessage("Vui lòng nhập trường số lượng nhập.");
            return;
        }

        if (exportProductPriceTextField.getText().trim().length() == 0) {
            showDiaglogMessage("Vui lòng nhập trường giá nhập.");
            return;
        }

        Integer quantity = 0;
        try {
            quantity = Integer.valueOf(exportProductQuantityTextField.getText());
        } catch (NumberFormatException e) {
            showDiaglogMessage("Số lượng phải là một số nguyên.");
            return;
        }
        Float price = 0.0f;
        try {
            price = Float.valueOf(exportProductPriceTextField.getText().replace(",", ""));
        } catch (NumberFormatException e) {
            showDiaglogMessage("Giá sản phẩm phải là một số thực.");
            return;
        }

        Integer limitQuantity = Integer.valueOf(exportInStockProductsTable.getValueAt(rows[0], EXPORT_INVENTORY_ITEM_QUANTITY_COL_INDEX).toString());
        if (quantity > limitQuantity) {
            showDiaglogMessage("Số lượng xuất vượt quá số lượng trong kho.");
            return;
        }

        Float minExportPrice = Float.valueOf(exportInStockProductsTable.getValueAt(rows[0], EXPORT_INVENTORY_ITEM_PRICE_COL_INDEX).toString().replace(",", ""));
        if (price < minExportPrice) {
            showDiaglogMessage("Giá xuất phải lớn hơn hoặc bằng giá nhập.");
            return;
        }

        selectedInventoryItem.setQuantity(limitQuantity - quantity);

        int foundIndex = IntStream
                .range(0, inventoryItems.size())
                .filter(index -> inventoryItems.get(index).getId().equals(selectedInventoryId))
                .findFirst()
                .orElse(-1);

        inventoryItems.set(foundIndex, selectedInventoryItem);

        final Integer finalQuantity = quantity;
        final Float finalPrice = price;

        Integer selectedProductId
                = Integer.valueOf(exportInStockProductsTable
                        .getValueAt(rows[0], EXPORT_INVENTORY_ITEM_PRODUCT_ID_COL_INDEX)
                        .toString());

        Product selectedProduct = productController.findById(selectedProductId).get();

        Integer foundInventoryIndex = IntStream
                .range(0, exportBill.getExportBillItems().size())
                .filter(index
                        -> exportBill.getExportBillItems()
                        .get(index)
                        .getInventoryItem()
                        .getId()
                        .equals(selectedInventoryItem.getId())
                )
                .findFirst()
                .orElse(-1);

        if (foundInventoryIndex != -1) {
            int oldQuantity = exportBill.getExportBillItems().get(foundInventoryIndex).getQuantity();
            exportBill.getExportBillItems().get(foundInventoryIndex).setQuantity(oldQuantity + quantity);
        } else {
            exportBill.getExportBillItems()
                    .add(ExportBillItem
                            .builder()
                            .product(selectedProduct)
                            .quantity(finalQuantity)
                            .exportPrice(finalPrice)
                            .exportBill(exportBill)
                            .inventoryItem(selectedInventoryItem)
                            .build()
                    );
        }

        loadDataToExportProductsBillTable(exportBill);
        loadDataToExportInStockProductsTable(inventoryItems);
    }//GEN-LAST:event_exportProductAddButtonActionPerformed

    private void searchExportProductRefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchExportProductRefreshButtonActionPerformed
        searchExportProductTextField.setText("");
        loadDataToExportInStockProductsTable(inventoryItems);
    }//GEN-LAST:event_searchExportProductRefreshButtonActionPerformed

    private void searchExportProductTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchExportProductTextFieldKeyReleased
        String keyword = searchExportProductTextField.getText();
        loadDataToExportInStockProductsTable(
                inventoryItems.parallelStream()
                        .filter(item -> item.getImportBillItem().getProduct().getName().contains(keyword))
                        .toList()
        );
    }//GEN-LAST:event_searchExportProductTextFieldKeyReleased

    private void deleteExportBillItemDiaglogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteExportBillItemDiaglogButtonActionPerformed
        int[] rows = exportProductsBillTable.getSelectedRows();

        int selectedInventoryId = Integer.parseInt(exportProductsBillTable.getValueAt(rows[0], EXPORT_BILL_ITEM_INVENTORY_ID).toString());

        int quantity = Integer.parseInt(exportProductsBillTable.getValueAt(rows[0], EXPORT_BILL_ITEM_QUANTITY).toString());

        int foundIndex = IntStream.range(0, inventoryItems.size())
                .filter(index -> inventoryItems.get(index).getId().equals(selectedInventoryId))
                .findFirst()
                .orElse(-1);

        int oldQuantity = inventoryItems.get(foundIndex).getQuantity();
        inventoryItems.get(foundIndex).setQuantity(oldQuantity + quantity);
        inventoryItems.set(foundIndex, inventoryItems.get(foundIndex));

        exportBill.getExportBillItems().removeIf(item -> item.getInventoryItem().getId().equals(selectedInventoryId));

        loadDataToExportProductsBillTable(exportBill);
        loadDataToExportInStockProductsTable(inventoryItems);

        deleteExportBillItemDiaglog.dispose();
        showDiaglogMessage("Xoá thành công.");
    }//GEN-LAST:event_deleteExportBillItemDiaglogButtonActionPerformed

    private void cancelDeleteExportBillItemDiaglogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelDeleteExportBillItemDiaglogButtonActionPerformed
        deleteExportBillItemDiaglog.dispose();
    }//GEN-LAST:event_cancelDeleteExportBillItemDiaglogButtonActionPerformed

    private void editExportBillItemPriceTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editExportBillItemPriceTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editExportBillItemPriceTextFieldActionPerformed

    private void editExportBillItemDiaglogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editExportBillItemDiaglogButtonActionPerformed

        int[] rightRows = exportProductsBillTable.getSelectedRows();
        int rightSelectedRow = rightRows[0];

        if (editExportBillItemQuantityTextField.getText().trim().length() == 0) {
            showDiaglogMessage("Vui lòng nhập trường số lượng xuất.");
            return;
        }

        
        if (editExportBillItemPriceTextField.getText().trim().length() == 0) {
            showDiaglogMessage("Vui lòng nhập trường giá xuất.");
            return;
        }

        
        Integer newQuantity = 0;
        try {
            newQuantity = Integer.valueOf(editExportBillItemQuantityTextField.getText());
        } catch (NumberFormatException e) {
            showDiaglogMessage("Số lượng xuất phải là một số nguyên.");
            return;
        }

        
        Float newPrice = 0.0f;
        try {
            newPrice = Float.valueOf(editExportBillItemPriceTextField.getText().replace(",", ""));
        } catch (NumberFormatException e) {
            showDiaglogMessage("Giá xuất phải là một số thực.");
            return;
        }

        
        Integer selectedInventoryId = Integer.valueOf(exportProductsBillTable.getValueAt(rightSelectedRow, EXPORT_BILL_ITEM_INVENTORY_ID).toString());
        Integer foundInventoryItemIndex = IntStream
                .range(0, inventoryItems.size())
                .filter(index -> inventoryItems.get(index).getId().equals(selectedInventoryId))
                .findFirst()
                .orElse(-1);
        InventoryItem foundInventoryItem = inventoryItems.get(foundInventoryItemIndex);

        Float minExportPrice = foundInventoryItem.getImportPrice();
        if (newPrice < minExportPrice) {
            showDiaglogMessage("Giá xuất phải lớn hơn hoặc bằng giá nhập.");
            return;
        }

        
        Integer maxQuantity = foundInventoryItem.getQuantity();
        int prevQuantity = Integer.parseInt(exportProductsBillTable.getValueAt(rightSelectedRow, EXPORT_BILL_ITEM_QUANTITY).toString());
        int change = newQuantity - prevQuantity;
        if (change > maxQuantity) {
            showDiaglogMessage("Số lượng trong kho không đủ, vui lòng nhập một giá trị nhỏ hơn hoặc bằng " + (prevQuantity + maxQuantity));
            return;
        }

        
        foundInventoryItem.setQuantity(maxQuantity - change);
        inventoryItems.set(foundInventoryItemIndex, foundInventoryItem);
                
        int foundExportBillItemIndex = IntStream.range(0, exportBill.getExportBillItems().size())
                .filter(index -> exportBill.getExportBillItems().get(index).getInventoryItem().getId().equals(selectedInventoryId))
                .findFirst()
                .orElse(-1);
        ExportBillItem selectedExportBillItem = exportBill.getExportBillItems().get(foundExportBillItemIndex);
        selectedExportBillItem.setQuantity(newQuantity);
        selectedExportBillItem.setExportPrice(newPrice);
        exportBill.getExportBillItems().set(foundExportBillItemIndex, selectedExportBillItem);

        
        editExportBillItemDiaglog.dispose();
        showDiaglogMessage("Cập nhật thành công.");
        loadDataToExportInStockProductsTable(inventoryItems);
        loadDataToExportProductsBillTable(exportBill);
    }//GEN-LAST:event_editExportBillItemDiaglogButtonActionPerformed

    private void cancelEditExportBillItemDiaglogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelEditExportBillItemDiaglogButtonActionPerformed
        editExportBillItemDiaglog.dispose();
    }//GEN-LAST:event_cancelEditExportBillItemDiaglogButtonActionPerformed

    private void editExportBillItemQuantityTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editExportBillItemQuantityTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editExportBillItemQuantityTextFieldActionPerformed

    private void editExportBillItemProductNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editExportBillItemProductNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editExportBillItemProductNameTextFieldActionPerformed

    private void editExportBillItemProductIdTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editExportBillItemProductIdTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editExportBillItemProductIdTextFieldActionPerformed

    private void editExportBillItemIndexTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editExportBillItemIndexTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editExportBillItemIndexTextFieldActionPerformed

    private void editExportBillItemInventoryIdTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editExportBillItemInventoryIdTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editExportBillItemInventoryIdTextFieldActionPerformed

    private void searchExportBillsTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchExportBillsTextFieldKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_searchExportBillsTextFieldKeyReleased

    private void resetSearchExportBillsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetSearchExportBillsButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_resetSearchExportBillsButtonActionPerformed

    private void exportImportBillsToExcelButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportImportBillsToExcelButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_exportImportBillsToExcelButton1ActionPerformed

    private void editExportBillButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editExportBillButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editExportBillButtonActionPerformed

    private void deleteExportBillButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteExportBillButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteExportBillButtonActionPerformed

    private void viewExportBillButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewExportBillButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_viewExportBillButtonActionPerformed

    private void showViewImportBillDetailDiaglog(int selectedRow) {
        viewImportBillDetailDiaglog.setVisible(true);
        viewImportBillDetailDiaglog.setLocationRelativeTo(this);

        int id = Integer.parseInt(importBillsTable.getValueAt(selectedRow, ID_COL_INDEX).toString());
        Optional<ImportBill> found = importBillController.findById(id);

        found.ifPresentOrElse(
                (item) -> {
                    viewImportBillIdValueLabel.setText(item.getId().toString());
                    viewImportBillProviderNameValueLabel.setText(item.getProvider().getName());
                    viewImportBillCreatorValueLabel.setText(item.getUser().getUsername());
                    viewImportBillWhenCreatedValueLabel.setText(formatter.format(item.getWhenCreated()));
                    viewImportBillTotalValueLabel.setText(priceFormatter.format(item.getTotal()) + "đ");

                    loadDataToViewImportBillDetailItems(item);
                },
                () -> {
                    showDiaglogMessage(ErrorMessage.ImportBill.NOT_FOUND);
                }
        );
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(() -> {
                if (ApplicationContext.getLoginedUser() == null) {
                    Login.main(new String[]{});
                } else {
                    new Home().setVisible(true);
                }
            });
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addProductButton;
    private javax.swing.JDialog addProductDiaglog;
    private javax.swing.JButton addProductDiaglogButton;
    private javax.swing.JPanel addProductDiaglogPanel;
    private javax.swing.JPanel addProductDiaglogPanel1;
    private javax.swing.JLabel addProviderAddressLabel;
    private javax.swing.JTextField addProviderAddressTextField;
    private javax.swing.JButton addProviderButton;
    private javax.swing.JDialog addProviderDiaglog;
    private javax.swing.JButton addProviderDiaglogButton;
    private javax.swing.JLabel addProviderEmailLabel;
    private javax.swing.JTextField addProviderEmailTextField;
    private javax.swing.JLabel addProviderNameLabel;
    private javax.swing.JTextField addProviderNameTextField;
    private javax.swing.JLabel addProviderPhoneNumberLabel;
    private javax.swing.JTextField addProviderPhoneNumberTextField;
    private javax.swing.JButton addUserButton;
    private javax.swing.JDialog addUserDiaglog;
    private javax.swing.JButton addUserDiaglogButton;
    private javax.swing.JPanel addUserDiaglogPanel;
    private javax.swing.JButton cancelAddProductDiaglogButton;
    private javax.swing.JButton cancelAddProviderDiaglogButton;
    private javax.swing.JButton cancelAddUserDiaglogButton;
    private javax.swing.JButton cancelAddUserDiaglogButton1;
    private javax.swing.JButton cancelDeleteExportBillItemDiaglogButton;
    private javax.swing.JButton cancelDeleteImportBillDiaglogButton;
    private javax.swing.JButton cancelDeleteImportBillItemDiaglogButton;
    private javax.swing.JButton cancelDeleteProduct;
    private javax.swing.JButton cancelDeleteProvider;
    private javax.swing.JButton cancelDeleteUser;
    private javax.swing.JButton cancelEditExportBillItemDiaglogButton;
    private javax.swing.JButton cancelEditImportBillItemDiaglogButton;
    private javax.swing.JButton cancelEditProductDiaglogButton;
    private javax.swing.JButton cancelEditProviderDiaglogButton;
    private javax.swing.JButton cancelEditUserDiaglogButton;
    private javax.swing.JButton cancelViewProductDiaglogButton;
    private javax.swing.JButton cancelViewProviderDiaglogButton;
    private javax.swing.JLabel clientAddressLabel;
    private javax.swing.JTextField clientAddressTextField;
    private javax.swing.JLabel clientNameLabel;
    private javax.swing.JTextField clientNameTextField;
    private javax.swing.JLabel clientPhoneNumberLabel;
    private javax.swing.JTextField clientPhoneNumberTextField;
    private javax.swing.JButton confirmDeleteProduct;
    private javax.swing.JButton confirmDeleteProvider;
    private javax.swing.JButton confirmDeleteUser;
    private javax.swing.JLabel confirmEmailOTPLabel;
    private javax.swing.JTextField confirmEmailOTPTextField;
    private javax.swing.JPasswordField confirmPasswordField;
    private javax.swing.JLabel confirmPasswordLabel;
    private javax.swing.JButton deleteExportBillButton;
    private javax.swing.JDialog deleteExportBillItemDiaglog;
    private javax.swing.JButton deleteExportBillItemDiaglogButton;
    private javax.swing.JLabel deleteExportBillItemDiaglogLabel;
    private javax.swing.JPanel deleteExportBillItemPanel;
    private javax.swing.JButton deleteImportBillButton;
    private javax.swing.JDialog deleteImportBillDiaglog;
    private javax.swing.JButton deleteImportBillDiaglogButton;
    private javax.swing.JLabel deleteImportBillDiaglogLabel;
    private javax.swing.JDialog deleteImportBillItemDiaglog;
    private javax.swing.JButton deleteImportBillItemDiaglogButton;
    private javax.swing.JLabel deleteImportBillItemDiaglogLabel;
    private javax.swing.JPanel deleteImportBillItemDiaglogPanel;
    private javax.swing.JPanel deleteImportBillPanel;
    private javax.swing.JButton deleteInStockButton;
    private javax.swing.JButton deleteProductButton;
    private javax.swing.JDialog deleteProductConfirmDiaglog;
    private javax.swing.JLabel deleteProductConfirmDiaglogLabel;
    private javax.swing.JPanel deleteProductConfirmDiaglogPanel;
    private javax.swing.JButton deleteProviderButton;
    private javax.swing.JDialog deleteProviderConfirmDiaglog;
    private javax.swing.JLabel deleteProviderConfirmDiaglogLabel;
    private javax.swing.JPanel deleteProviderConfirmDiaglogPanel;
    private javax.swing.JButton deleteUserButton;
    private javax.swing.JDialog deleteUserConfirmDiaglog;
    private javax.swing.JLabel deleteUserConfirmDiaglogLabel;
    private javax.swing.JPanel deleteUserConfirmDiaglogPanel;
    private javax.swing.JDialog diaglogMessage;
    private javax.swing.JLabel diaglogMessageLabel;
    private javax.swing.JButton diaglogMessageOkButton;
    private javax.swing.JPanel diaglogMessagePanel;
    private javax.swing.JButton editBillItemButton;
    private javax.swing.JLabel editEmailLabel;
    private javax.swing.JTextField editEmailTextField;
    private javax.swing.JButton editExportBillButton;
    private javax.swing.JButton editExportBillItemButton;
    private javax.swing.JDialog editExportBillItemDiaglog;
    private javax.swing.JButton editExportBillItemDiaglogButton;
    private javax.swing.JPanel editExportBillItemDiaglogPanel;
    private javax.swing.JLabel editExportBillItemIndexLabel;
    private javax.swing.JTextField editExportBillItemIndexTextField;
    private javax.swing.JLabel editExportBillItemInventoryIdLabel;
    private javax.swing.JTextField editExportBillItemInventoryIdTextField;
    private javax.swing.JLabel editExportBillItemPriceLabel;
    private javax.swing.JTextField editExportBillItemPriceTextField;
    private javax.swing.JLabel editExportBillItemProductIdLabel;
    private javax.swing.JTextField editExportBillItemProductIdTextField;
    private javax.swing.JLabel editExportBillItemProductNameLabel;
    private javax.swing.JTextField editExportBillItemProductNameTextField;
    private javax.swing.JLabel editExportBillItemQuantityLabel;
    private javax.swing.JTextField editExportBillItemQuantityTextField;
    private javax.swing.JLabel editFirstNameLabel;
    private javax.swing.JTextField editFirstNameTextField;
    private javax.swing.JButton editImportBillButton;
    private javax.swing.JDialog editImportBillItemDiaglog;
    private javax.swing.JButton editImportBillItemDiaglogButton;
    private javax.swing.JPanel editImportBillItemDiaglogPanel;
    private javax.swing.JLabel editImportBillItemIndexLabel;
    private javax.swing.JTextField editImportBillItemIndexTextField;
    private javax.swing.JLabel editImportBillItemPriceLabel;
    private javax.swing.JTextField editImportBillItemPriceTextField;
    private javax.swing.JLabel editImportBillItemProductIdLabel;
    private javax.swing.JTextField editImportBillItemProductIdTextField;
    private javax.swing.JLabel editImportBillItemProductNameLabel;
    private javax.swing.JTextField editImportBillItemProductNameTextField;
    private javax.swing.JLabel editImportBillItemQuantityLabel;
    private javax.swing.JTextField editImportBillItemQuantityTextField;
    private javax.swing.JButton editInStockButton;
    private javax.swing.JLabel editLastNameLabel;
    private javax.swing.JTextField editLastNameTextField;
    private javax.swing.JLabel editPhoneNumberLabel;
    private javax.swing.JTextField editPhoneNumberTextField;
    private javax.swing.JLabel editProductBatteryLabel;
    private javax.swing.JTextField editProductBatteryTextField;
    private javax.swing.JButton editProductButton;
    private javax.swing.JLabel editProductCardLabel;
    private javax.swing.JTextField editProductCardTextField;
    private javax.swing.JDialog editProductDiaglog;
    private javax.swing.JButton editProductDiaglogButton;
    private javax.swing.JPanel editProductDiaglogPanel;
    private javax.swing.JLabel editProductDisplayLabel;
    private javax.swing.JTextField editProductDisplayTextField;
    private javax.swing.JLabel editProductIdLabel;
    private javax.swing.JTextField editProductIdTextField;
    private javax.swing.JLabel editProductMemoryLabel;
    private javax.swing.JTextField editProductMemoryTextField;
    private javax.swing.JLabel editProductNameLabel;
    private javax.swing.JTextField editProductNameTextField;
    private javax.swing.JLabel editProductProcessorLabel;
    private javax.swing.JTextField editProductProcessorTextField;
    private javax.swing.JLabel editProductStorageLabel;
    private javax.swing.JTextField editProductStorageTextField;
    private javax.swing.JLabel editProductWeightLabel;
    private javax.swing.JTextField editProductWeightTextField;
    private javax.swing.JLabel editProviderAddressLabel;
    private javax.swing.JTextField editProviderAddressTextField;
    private javax.swing.JButton editProviderButton;
    private javax.swing.JDialog editProviderDiaglog;
    private javax.swing.JButton editProviderDiaglogButton;
    private javax.swing.JPanel editProviderDiaglogPanel;
    private javax.swing.JLabel editProviderEmailLabel;
    private javax.swing.JTextField editProviderEmailTextField;
    private javax.swing.JLabel editProviderIdLabel;
    private javax.swing.JTextField editProviderIdTextField;
    private javax.swing.JLabel editProviderNameLabel;
    private javax.swing.JTextField editProviderNameTextField;
    private javax.swing.JLabel editProviderPhoneNumberLabel;
    private javax.swing.JTextField editProviderPhoneNumberTextField;
    private javax.swing.JButton editUserButton;
    private javax.swing.JDialog editUserDiaglog;
    private javax.swing.JButton editUserDiaglogButton;
    private javax.swing.JButton editUserDiaglogButton1;
    private javax.swing.JLabel editUserDiaglogLabel;
    private javax.swing.JPanel editUserDiaglogPanel;
    private javax.swing.JLabel editUserIdLabel;
    private javax.swing.JTextField editUserIdTextField;
    private javax.swing.JLabel editUsernameLabel;
    private javax.swing.JTextField editUsernameTextField;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JTextField emailTextField;
    private javax.swing.JLabel exportBillLabel;
    private javax.swing.JPanel exportBillPanel;
    private javax.swing.JButton exportBillProductButton;
    private javax.swing.JPanel exportBillTab;
    private javax.swing.JPanel exportBillsFunctionPanel;
    private javax.swing.JScrollPane exportBillsScrollPanel;
    private javax.swing.JTable exportBillsTable;
    private javax.swing.JButton exportImportBillsToExcelButton;
    private javax.swing.JButton exportImportBillsToExcelButton1;
    private javax.swing.JTable exportInStockProductsTable;
    private javax.swing.JButton exportInStocksToExcelButton;
    private javax.swing.JButton exportProductAddButton;
    private javax.swing.JTextField exportProductBillCreatorLabel;
    private javax.swing.JLabel exportProductBillCreatorTextField;
    private javax.swing.JScrollPane exportProductBillScrollPane;
    private javax.swing.JLabel exportProductLabel;
    private javax.swing.JPanel exportProductPanel;
    private javax.swing.JLabel exportProductPriceLabel;
    private javax.swing.JTextField exportProductPriceTextField;
    private javax.swing.JLabel exportProductQuantityLabel;
    private javax.swing.JTextField exportProductQuantityTextField;
    private javax.swing.JScrollPane exportProductScrollPanel;
    private javax.swing.JPanel exportProductTab;
    private javax.swing.JTable exportProductsBillTable;
    private javax.swing.JButton exportProductsToExcelButton;
    private javax.swing.JButton exportProvidersToExcelButton;
    private javax.swing.JButton exportUsersToExcelButton;
    private javax.swing.JLabel firstNameLabel;
    private javax.swing.JTextField firstNameTextField;
    private javax.swing.JLabel importBillLabel;
    private javax.swing.JPanel importBillPanel;
    private javax.swing.JButton importBillProductButton;
    private javax.swing.JPanel importBillTab;
    private javax.swing.JPanel importBillsFunctionPanel;
    private javax.swing.JScrollPane importBillsScrollPanel;
    private javax.swing.JTable importBillsTable;
    private javax.swing.JButton importProductAddButton;
    private javax.swing.JTextField importProductBillCreatorLabel;
    private javax.swing.JLabel importProductBillCreatorTextField;
    private javax.swing.JScrollPane importProductBillScrollPane;
    private javax.swing.JTable importProductBillTable;
    private javax.swing.JLabel importProductLabel;
    private javax.swing.JPanel importProductPanel;
    private javax.swing.JLabel importProductPriceLabel;
    private javax.swing.JTextField importProductPriceTextField;
    private javax.swing.JLabel importProductQuantityLabel;
    private javax.swing.JTextField importProductQuantityTextField;
    private javax.swing.JScrollPane importProductScrollPanel;
    private javax.swing.JPanel importProductTab;
    private javax.swing.JButton importProductsFromExcelButton;
    private javax.swing.JTable importProductsTable;
    private javax.swing.JButton importProvidersFromExcelButton;
    private javax.swing.JButton importUsersFromExcelButton;
    private javax.swing.JLabel inStockLabel;
    private javax.swing.JPanel inStockPanel;
    private javax.swing.JPanel inStockTab;
    private javax.swing.JPanel inStocksFunctionPanel;
    private javax.swing.JScrollPane inStocksScrollPanel;
    private javax.swing.JTable inStocksTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JToolBar.Separator jSeparator9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lastNameLabel;
    private javax.swing.JTextField lastNameTextField;
    private javax.swing.JLabel loginedUsername;
    private javax.swing.JDialog logoutDiaglog;
    private javax.swing.JLabel logoutLabel;
    private javax.swing.JPanel logoutTab;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JLabel phoneNumberLabel;
    private javax.swing.JTextField phoneNumberTextField;
    private javax.swing.JLabel productBatteryLabel;
    private javax.swing.JTextField productBatteryTextField;
    private javax.swing.JLabel productCardLabel;
    private javax.swing.JTextField productCardTextField;
    private javax.swing.JLabel productDisplayLabel;
    private javax.swing.JTextField productDisplayTextField;
    private javax.swing.JPanel productFunctionPanel;
    private javax.swing.JLabel productLabel;
    private javax.swing.JLabel productMemoryLabel;
    private javax.swing.JTextField productMemoryTextField;
    private javax.swing.JLabel productNameLabel;
    private javax.swing.JTextField productNameTextField;
    private javax.swing.JPanel productPanel;
    private javax.swing.JLabel productProcessorLabel;
    private javax.swing.JTextField productProcessorTextField;
    private javax.swing.JLabel productStorageLabel;
    private javax.swing.JTextField productStorageTextField;
    private javax.swing.JPanel productTab;
    private javax.swing.JLabel productWeightLabel;
    private javax.swing.JTextField productWeightTextField;
    private javax.swing.JScrollPane productsScrollPanel;
    private javax.swing.JTable productsTable;
    private javax.swing.JPanel providerFunctionPanel;
    private javax.swing.JLabel providerLabel;
    private javax.swing.JComboBox<String> providerNameComboBox;
    private javax.swing.JLabel providerNameLabel;
    private javax.swing.JPanel providerPanel;
    private javax.swing.JPanel providerTab;
    private javax.swing.JScrollPane providersScrollPanel;
    private javax.swing.JTable providersTable;
    private javax.swing.JButton removeBillItemButton;
    private javax.swing.JButton removeExportBillItemButton;
    private javax.swing.JButton resetSearchExportBillsButton;
    private javax.swing.JButton resetSearchImportBillsButton1;
    private javax.swing.JButton resetSearchInStocksButton;
    private javax.swing.JButton resetSearchProvidersButton;
    private javax.swing.JPanel searchExportBillsPanel;
    private javax.swing.JTextField searchExportBillsTextField;
    private javax.swing.JPanel searchExportProductPanel;
    private javax.swing.JButton searchExportProductRefreshButton;
    private javax.swing.JTextField searchExportProductTextField;
    private javax.swing.JPanel searchImportBillsPanel;
    private javax.swing.JTextField searchImportBillsTextField;
    private javax.swing.JPanel searchImportProductPanel;
    private javax.swing.JButton searchImportProductRefreshButton;
    private javax.swing.JTextField searchImportProductTextField;
    private javax.swing.JPanel searchInStocksPanel;
    private javax.swing.JTextField searchInStocksTextField;
    private javax.swing.JButton searchProductsButton;
    private javax.swing.JPanel searchProductsPanel;
    private javax.swing.JTextField searchProductsTextField;
    private javax.swing.JPanel searchProvidersPanel;
    private javax.swing.JTextField searchProvidersTextField;
    private javax.swing.JButton searchUsersButton;
    private javax.swing.JPanel searchUsersPanel;
    private javax.swing.JTextField searchUsersTextField;
    private javax.swing.JButton sendCodeAddUserDiaglogButton;
    private javax.swing.JPanel sidebarPanel;
    private javax.swing.JLabel statisticsLabel;
    private javax.swing.JPanel statisticsPanel;
    private javax.swing.JPanel statisticsTab;
    private javax.swing.JLabel totalExportBillLabel;
    private javax.swing.JLabel totalExportPriceLabel;
    private javax.swing.JLabel totalImportBillLabel;
    private javax.swing.JLabel totalImportPriceLabel;
    private javax.swing.JLabel updateInfoLabel;
    private javax.swing.JPanel updateInfoTab;
    private javax.swing.JButton updateProductDiaglogButton;
    private javax.swing.JButton updateProviderDiaglogButton;
    private javax.swing.JPanel userFunctionPanel;
    private javax.swing.JLabel userLabel;
    private javax.swing.JPanel userPanel;
    private javax.swing.JPanel userTab;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameTextField;
    private javax.swing.JScrollPane usersScrollPanel;
    private javax.swing.JTable usersTable;
    private javax.swing.JButton viewExportBillButton;
    private javax.swing.JButton viewImportBillButton;
    private javax.swing.JLabel viewImportBillCreatorLabel;
    private javax.swing.JLabel viewImportBillCreatorValueLabel;
    private javax.swing.JDialog viewImportBillDetailDiaglog;
    private javax.swing.JLabel viewImportBillDetailHeaderLabel;
    private javax.swing.JPanel viewImportBillDetailHeaderPanel;
    private javax.swing.JTable viewImportBillDetailItemsTable;
    private javax.swing.JPanel viewImportBillDetailPanel;
    private javax.swing.JLabel viewImportBillIdLabel;
    private javax.swing.JLabel viewImportBillIdValueLabel;
    private javax.swing.JScrollPane viewImportBillProductsScrollTable;
    private javax.swing.JLabel viewImportBillProviderNameLabel;
    private javax.swing.JLabel viewImportBillProviderNameValueLabel;
    private javax.swing.JLabel viewImportBillTotalLabel;
    private javax.swing.JLabel viewImportBillTotalValueLabel;
    private javax.swing.JLabel viewImportBillWhenCreatedLabel;
    private javax.swing.JLabel viewImportBillWhenCreatedValueLabel;
    private javax.swing.JButton viewInStockButton;
    private javax.swing.JLabel viewProductBatteryLabel;
    private javax.swing.JTextField viewProductBatteryTextField;
    private javax.swing.JButton viewProductButton;
    private javax.swing.JLabel viewProductCardLabel;
    private javax.swing.JTextField viewProductCardTextField;
    private javax.swing.JDialog viewProductDiaglog;
    private javax.swing.JPanel viewProductDiaglogPanel;
    private javax.swing.JLabel viewProductDisplayLabel;
    private javax.swing.JTextField viewProductDisplayTextField;
    private javax.swing.JLabel viewProductIdLabel;
    private javax.swing.JTextField viewProductIdTextField;
    private javax.swing.JLabel viewProductLastUpdatedLabel;
    private javax.swing.JTextField viewProductLastUpdatedTextField;
    private javax.swing.JLabel viewProductMemoryLabel;
    private javax.swing.JTextField viewProductMemoryTextField;
    private javax.swing.JLabel viewProductNameLabel;
    private javax.swing.JTextField viewProductNameTextField;
    private javax.swing.JLabel viewProductProcessorLabel;
    private javax.swing.JTextField viewProductProcessorTextField;
    private javax.swing.JLabel viewProductStorageLabel;
    private javax.swing.JTextField viewProductStorageTextField;
    private javax.swing.JTextField viewProductStorageTextField2;
    private javax.swing.JLabel viewProductWeightLabel;
    private javax.swing.JTextField viewProductWeightTextField;
    private javax.swing.JLabel viewProductWhenCreatedLabel;
    private javax.swing.JTextField viewProductWhenCreatedTextField;
    private javax.swing.JLabel viewProviderAddressLabel;
    private javax.swing.JTextField viewProviderAddressTextField;
    private javax.swing.JButton viewProviderButton;
    private javax.swing.JDialog viewProviderDiaglog;
    private javax.swing.JPanel viewProviderDiaglogPanel;
    private javax.swing.JLabel viewProviderEmailLabel;
    private javax.swing.JTextField viewProviderEmailTextField;
    private javax.swing.JLabel viewProviderIdLabel;
    private javax.swing.JTextField viewProviderIdTextField;
    private javax.swing.JLabel viewProviderLastUpdatedLabel;
    private javax.swing.JTextField viewProviderLastUpdatedTextField;
    private javax.swing.JLabel viewProviderNameLabel;
    private javax.swing.JTextField viewProviderNameTextField;
    private javax.swing.JLabel viewProviderPhoneNumberLabel;
    private javax.swing.JTextField viewProviderPhoneNumberTextField;
    private javax.swing.JLabel viewProviderWhenCreatedLabel;
    private javax.swing.JTextField viewProviderWhenCreatedTextField;
    private javax.swing.JButton viewUserButton;
    private javax.swing.JDialog viewUserDiaglog;
    private javax.swing.JPanel viewUserDiaglogPanel;
    private javax.swing.JLabel viewUserEmailLabel;
    private javax.swing.JTextField viewUserEmailTextField;
    private javax.swing.JLabel viewUserFirstNameLabel;
    private javax.swing.JTextField viewUserFirstNameTextField;
    private javax.swing.JLabel viewUserLastLoggedInLabel;
    private javax.swing.JTextField viewUserLastLoggedInTextField;
    private javax.swing.JLabel viewUserLastNameLabel;
    private javax.swing.JTextField viewUserLastNameTextField;
    private javax.swing.JLabel viewUserLastUpdatedLabel;
    private javax.swing.JTextField viewUserLastUpdatedTextField;
    private javax.swing.JLabel viewUserLoggedInLabel;
    private javax.swing.JTextField viewUserLoggedInTextField;
    private javax.swing.JLabel viewUserPhoneNumberLabel;
    private javax.swing.JTextField viewUserPhoneNumberTextField;
    private javax.swing.JLabel viewUserWhenCreatedLabel;
    private javax.swing.JTextField viewUserWhenCreatedTextField;
    private javax.swing.JLabel viewUsernameLabel;
    private javax.swing.JTextField viewUsernameTextField;
    // End of variables declaration//GEN-END:variables
}
