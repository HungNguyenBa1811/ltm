# Pattern làm bài TCP, UDP, RMI

Khi gặp đề mới, chọn bài cùng kiểu giao tiếp, sao chép khung rồi thay mã câu hỏi và hàm xử lý.

## Chọn khung theo đề

| Đề yêu cầu | Pattern | Bài mẫu |
| --- | --- | --- |
| TCP, chuỗi kết thúc bằng xuống dòng | [TCP theo dòng](tcp.md#1-chuỗi-theo-dòng) | [TCP_CHARACTER1](../TCP_CHARACTER1.java), [TCP_CHARACTER2](../TCP_CHARACTER2.java) |
| TCP, đọc/ghi trực tiếp byte của chuỗi | [TCP byte thô](tcp.md#2-chuỗi-qua-byte-thô) | [TCP_BYTE_RAW1](../TCP_BYTE_RAW1.java), [TCP_BYTE_RAW2](../TCP_BYTE_RAW2.java) |
| TCP, `readInt`, `readUTF`, `writeInt`, `writeUTF` | [TCP có kiểu](tcp.md#3-dữ-liệu-có-kiểu) | [TCP_DATA1](../TCP_DATA1.java), [TCP_DATA2](../TCP_DATA2.java) |
| TCP, nhận và trả object | [TCP object](tcp.md#4-object) | [TCP_OBJECT1](../TCP_OBJECT1.java), [TCP_OBJECT2](../TCP_OBJECT2.java) |
| TCP, nén GZIP | [TCP GZIP](tcp.md#5-gzip) | [TCP_GZIP1](../TCP_GZIP1.java), [TCP_GZIP2](../TCP_GZIP2.java) |
| TCP NIO, frame có độ dài 4 byte | [TCP NIO](tcp.md#6-nio-với-frame-độ-dài) | [TCP_NIO1](../TCP_NIO1.java), [TCP_NIO2](../TCP_NIO2.java) |
| UDP, dãy số có request ID (`UDP_DATA`) | [UDP chuỗi](udp.md#1-chuỗi) | [UDP_DATA1](../UDP_DATA1.java), [UDP_DATA2](../UDP_DATA2.java) |
| UDP, chuỗi có request ID (`UDP_STRING`) | [UDP chuỗi](udp.md#1-chuỗi) | [UDP_STRING1](../UDP_STRING1.java), [UDP_STRING2](../UDP_STRING2.java) |
| UDP, 8 byte ID rồi đến object | [UDP object](udp.md#2-object-kèm-id-8-byte) | [UDP_OBJECT1](../UDP_OBJECT1.java), [UDP_OBJECT2](../UDP_OBJECT2.java) |
| RMI, `byte[]` | [ByteService](rmi.md#1-chọn-service) | [RMI_BYTE1](../RMI_BYTE1.java), [RMI_BYTE2](../RMI_BYTE2.java) |
| RMI, chuỗi | [CharacterService](rmi.md#1-chọn-service) | [RMI_CHARACTER1](../RMI_CHARACTER1.java), [RMI_CHARACTER2](../RMI_CHARACTER2.java) |
| RMI, dữ liệu qua `Object` | [DataService](rmi.md#3-dataservice) | [RMI_DATA1](../RMI_DATA1.java), [RMI_DATA2](../RMI_DATA2.java) |
| RMI, object nghiệp vụ | [ObjectService](rmi.md#4-objectservice) | [RMI_OBJECT](../RMI_OBJECT.java) |

## Cách làm một bài mới

1. Đọc đề và xác định kiểu giao tiếp trong bảng trên.
2. Sao chép bài mẫu cùng kiểu. Đổi tên file và tên class cho khớp nhau.
3. Thay `HOST`, `PORT` nếu đề có, mã sinh viên và mã câu hỏi.
4. Ghi lại dữ liệu nhận và kết quả phải trả: kiểu dữ liệu, thứ tự trường, dấu phân cách, cách đánh chỉ số.
5. Viết `solve(...)`. Với bài object, sửa các thuộc tính đề yêu cầu trên object nhận được.
6. Dùng lệnh gửi có sẵn trong khung để nộp kết quả rồi chạy bài.

Ghi nhanh dữ liệu của đề trước khi viết:

```text
Pattern:
HOST / PORT hoặc tên service:
Mã sinh viên / mã câu hỏi:
Dữ liệu nhận:
Kết quả trả:
Quy tắc xử lý:
```

## Dạng mã gửi ban đầu

| Nhóm | Cách truyền mã trong bài mẫu |
| --- | --- |
| TCP | Chuỗi `MSV;MA_CAU_HOI`, gửi bằng stream tương ứng |
| UDP | Chuỗi `;MSV;MA_CAU_HOI`, có dấu `;` ở đầu |
| RMI | Hai tham số riêng: `studentCode`, `qCode` |

Các bài dùng host `36.50.135.242`. TCP dùng các port từ `2206` đến `2211`; UDP dùng `2207`, `2208`, `2209`. Lấy port theo đề và bài mẫu cùng loại.

## Phần nào giữ, phần nào thay

| Phần | Cách dùng lại |
| --- | --- |
| Tạo socket, stream hoặc lookup service | Giữ khung của pattern đã chọn |
| Gửi mã, nhận dữ liệu, gửi kết quả | Giữ trình tự; điều chỉnh kiểu dữ liệu và số lần đọc/ghi theo đề |
| `solve(...)` hoặc đoạn sửa object | Thay theo yêu cầu tính toán |
| Request ID của UDP | Lấy từ gói nhận và ghép lại vào gói trả |
| Class trong `TCP/`, `UDP/`, `RMI/` | Dùng class tương ứng với object/service của đề |

## Biên dịch và chạy

Chạy lệnh tại thư mục gốc repo. Ví dụ với bài TCP object:

```powershell
javac -encoding UTF-8 TCP_OBJECT1.java TCP/Laptop.java
java TCP_OBJECT1
```

Ví dụ với UDP và RMI:

```powershell
javac -encoding UTF-8 UDP_OBJECT1.java UDP/Student.java
java UDP_OBJECT1
javac -encoding UTF-8 RMI_OBJECT.java RMI/ObjectService.java RMI/TicketSla.java
java RMI_OBJECT
```

Khi tạo bài mới, thay tên bài và các file phụ thuộc trong lệnh. Các client kết nối tới server của đề khi chạy.
