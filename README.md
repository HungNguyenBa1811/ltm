# Lập trình mạng Java

Repo gồm các bài mẫu client TCP, UDP và RMI viết bằng Java. Mỗi file Java ở thư mục gốc là một bài độc lập. Client gửi mã sinh viên và mã câu hỏi tới server, nhận dữ liệu rồi xử lý và nộp kết quả.

Tra [pattern làm bài](docs/README.md) để chọn khung phù hợp với đề.

| Giao thức | Nhóm bài mẫu | Hướng dẫn |
| --- | --- | --- |
| TCP | `TCP_CHARACTER`, `TCP_BYTE_RAW`, `TCP_DATA`, `TCP_OBJECT`, `TCP_GZIP`, `TCP_NIO` | [Pattern TCP](docs/tcp.md) |
| UDP | `UDP_DATA`, `UDP_STRING`, `UDP_OBJECT` | [Pattern UDP](docs/udp.md) |
| RMI | `RMI_BYTE`, `RMI_CHARACTER`, `RMI_DATA`, `RMI_OBJECT` | [Pattern RMI](docs/rmi.md) |

Số ở cuối tên file phân biệt các bài trong cùng nhóm, như [TCP_CHARACTER1.java](TCP_CHARACTER1.java) và [TCP_CHARACTER2.java](TCP_CHARACTER2.java).

| Thư mục | Nội dung |
| --- | --- |
| [TCP/](TCP/) | Class dữ liệu cho bài TCP object |
| [UDP/](UDP/) | Class dữ liệu cho bài UDP object |
| [RMI/](RMI/) | Interface service và class dữ liệu RMI |
| [docs/](docs/) | Bảng chọn pattern, khung code và cách dùng lại bài mẫu |

Để biên dịch và chạy bài, cần cài JDK có `javac` và `java`. Chạy các lệnh từ thư mục gốc repo. Ví dụ với TCP object:

```powershell
javac -encoding UTF-8 TCP_OBJECT1.java TCP/Laptop.java
java TCP_OBJECT1
```

Với UDP object:

```powershell
javac -encoding UTF-8 UDP_OBJECT1.java UDP/Student.java
java UDP_OBJECT1
```

Với RMI object:

```powershell
javac -encoding UTF-8 RMI_OBJECT.java RMI/ObjectService.java RMI/TicketSla.java
java RMI_OBJECT
```

Trước khi chạy, thay host, port hoặc tên service cùng mã sinh viên và mã câu hỏi theo đề. Client sẽ kết nối tới server của đề, với mã gửi theo từng giao thức: TCP dùng chuỗi `MSV;MA_CAU_HOI`, UDP dùng `;MSV;MA_CAU_HOI`, còn RMI truyền hai mã thành hai tham số riêng.

Khi làm bài mới, sao chép file cùng pattern rồi đổi tên file và class. Thay hàm `solve(...)` hoặc đoạn cập nhật object theo yêu cầu xử lý của đề; giữ trình tự gửi/nhận của bài mẫu và định dạng kết quả đề yêu cầu.
