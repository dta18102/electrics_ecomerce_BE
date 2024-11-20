@echo off

rem Nhập tên từ người dùng
set "name="
echo "Nhập tên của bạn: "
set /p "name="

rem Xác nhận tên
if not defined name (
    echo "Bạn chưa nhập tên!"
    exit /b 1
)

rem Nội dung tệp
set content="Đây là tệp %name%."

rem Thư mục đích
set directories=(
    "example\medimateserver\dto"
    "example\medimateserver\entity"
    "example\medimateserver\service"
    "example\medimateserver\service\impl"
)

rem Tạo tệp
for %%d in (%directories%) do (
    cd "%%d" 2>nul
    echo "%%content%%" > "%%d\%%name%%.txt"
)

rem Thông báo thành công
echo "Tạo tệp thành công!"