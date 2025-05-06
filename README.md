# OrderDrinkApp ☕️🥤

OrderDrinkApp là ứng dụng gọi đồ uống dành cho nền tảng Android, hỗ trợ người dùng đặt món, quản lý bàn thanh toán.
Dự án sử dụng Java kết hợp Firebase và SQLite để lưu trữ dữ liệu, phù hợp với mô hình quán cafe, trà sữa hoặc đồ uống nhanh.

 # 🚀 Tính năng nổi bật

- 📋 Hiển thị menu đồ uống theo danh mục
- 🔍 Tìm kiếm nhanh tên đồ uống
- ➕ Thêm vào giỏ hàng, tăng/giảm số lượng
- 🗃️ Order món theo bàn, thêm sửa xóa bàn, thêm sửa xóa món trên bàn.
- 💳 Đặt hàng và xem tổng hóa đơn
- 🧾 Lưu đơn hàng vào Firebase (Realtime Database hoặc Firestore)
- 🗃️ Lưu giỏ hàng cục bộ bằng SQLite (offline)
- 👤 Đăng ký/Đăng nhập người dùng bằng Firebase Authentication
- 🔧 Quản lý đồ uống và đơn hàng dành cho Admin

# 🛠️ Công nghệ sử dụng

- Ngôn ngữ: Java
- Nền tảng: Android SDK
- Cơ sở dữ liệu online: Firebase (Firestore hoặc Realtime Database)
- Xác thực người dùng: Firebase Authentication
- Lưu trữ cục bộ: SQLite (Android SQLiteOpenHelper)
- Lưu ảnh sản phẩm: Firebase Storage

🧑‍💻 Cấu trúc thư mục (Android Studio)

# 🔧 Hướng dẫn cài đặt

1. Clone dự án:
   ```bash
   git clone https://github.com/Zang201/OrderDrinkApp.git
2. Mở bằng Android Studio
- Import project vào Android Studio
- Kết nối Firebase qua Firebase Assistant (Tools > Firebase)
3. Thêm file cấu hình Firebase
- Tải google-services.json từ Firebase Console
- Đặt vào thư mục: app/
4. Cài đặt các dependency trong build.gradle
- Firebase Auth, Firestore/Realtime Database, Storage
- SQLite đã tích hợp sẵn trong Android
5. Build và chạy ứng dụng trên thiết bị thật hoặc giả lập.




