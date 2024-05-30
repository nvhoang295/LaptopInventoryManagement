/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.constant;

/**
 *
 * @author vieth
 */
public class ErrorMessage {
    public static final String EMPTY_SELECTED_ROWS = "Vui lòng chọn ít nhất 1 bản ghi.";
    public static final String EXCEED_SELECTED_ROWS = "Vui lòng chỉ chọn một bản ghi để thực hiện chức năng này.";
    
    public static final class Auth {

        public static final String NOT_FOUND = "Không tìm thấy người dùng.";
        public static final String PASSWORD_NOT_CORRECT = "Mật khẩu không chính khác.";
        public static final String BLANK_INPUT = "Tên đăng nhập hoặc mật khẩu không được để trống.";
    }

    public static final class File {

        public static final String SAVE_FILE = "Lỗi, lưu file không thành công.";
        public static final String READ_FILE = "Lỗi, đọc file không thành công.";
    }

    public static final class User {

        public static final String SAVE = "Lưu người dùng không thành công.";
        public static final String BLANK_INPUT = "Vui lòng nhập đầy đủ tất cả các trường thuộc tính.";
        public static final String MISMATCHED_PASSWORD = "Mật khẩu xác nhận không đúng.";
        public static final String NOT_FOUND = "Không tìm thấy người dùng";
        public static final String DUPLICATED_USERNAME = "Tên đăng nhập này đã tồn tại.";   

    }
    
    public static final class Product {
        public static final String SAVE = "Lưu sản phẩm không thành công.";
        public static final String NOT_FOUND = "Không tìm thấy sản phẩm.";
        public static final String BLANK_INPUT = "Vui lòng nhập đầy đủ tất cả các trường thuộc tính.";
    }
}
