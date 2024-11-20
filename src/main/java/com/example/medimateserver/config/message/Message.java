package com.example.medimateserver.config.message;

public class Message {

    // Message chào mừng
    public static final String WELCOME_MESSAGE = "Chào mừng bạn đến với hệ thống!";

    // Message đăng nhập thành công
    public static final String LOGIN_SUCCESS_MESSAGE = "Đăng nhập thành công!";
    public static final String REGISTER_SUCCESS_MESSAGE = "Đăng ký thành công!";
    public static final String REGISTER_FAILED_MESSAGE = "Đăng nhập thất bại! Vui lòng kiểm tra lại thông tin đăng nhập.";

    // Message đăng nhập thất bại
    public static final String LOGIN_FAILED_MESSAGE = "Đăng nhập thất bại! Vui lòng kiểm tra lại thông tin đăng nhập.";

    // Message không có quyền truy cập
    public static final String ACCESS_DENIED_MESSAGE = "Bạn không có quyền truy cập vào chức năng này.";

    // Message lỗi hệ thống
    public static final String SYSTEM_ERROR_MESSAGE = "Lỗi hệ thống! Vui lòng liên hệ với quản trị viên.";

    // ...

    // Hàm lấy message theo mã
    public static String getMessage(String code) {
        switch (code) {
            case "WELCOME":
                return WELCOME_MESSAGE;
            case "LOGIN_SUCCESS":
                return LOGIN_SUCCESS_MESSAGE;
            case "LOGIN_FAILED":
                return LOGIN_FAILED_MESSAGE;
            case "ACCESS_DENIED":
                return ACCESS_DENIED_MESSAGE;
            case "SYSTEM_ERROR":
                return SYSTEM_ERROR_MESSAGE;
            default:
                return "";
        }
    }

}
