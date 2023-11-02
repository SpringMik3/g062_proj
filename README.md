# g062_proj
# Chủ đề: Trò chơi cờ caro online sử dụng giao thức TCP/IP
# Nhóm: 062
# Thành viên : 
1. Nguyễn Quốc Anh-B20DCCN062
# Công nghệ: 
Ngôn ngữ Java
Socket và kĩ thuật multithread trong Java 
Giao thức TCP/IP
Cổng kết nối 9000
# Kiến trúc Client Server : 
              1.Server:
              + Lắng nghe
              + Phục vụ yêu cầu của Client
              + Xử lý nước đi của Client để gửi kết quả thắng thua trong bàn cờ cho các Client đang chơi trong bàn.
              2.Client:
              + Kết nối Server
              + Gửi yêu cầu cho Server phục vụ : Mời chơi, chấp nhận lời mơi, nước đi, chơi lại.
# Giao diện :
1.Sever:
+ Start Sever
+ Listbox những người chơi đang online
+ Listbox cập nhật những người chơi đang chơi
+ Text Area lưu thông tin kết nối đến Server
2.Client:
+ Địa chỉ IP của Server
+ Tên của bạn
+ Danh sách người chơi đang online : những người chưa chơi
+ Button mời chơi: chọn người chơi muốn chơi trong danh sách rồi nhấn mời chơi sẽ hiện ra hộp thông báo "Yes" or "No"
+ Giao diện bàn cờ [10x10]  
              
