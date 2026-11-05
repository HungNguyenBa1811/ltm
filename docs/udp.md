# Pattern UDP

Các bài UDP dùng một `DatagramSocket` để gửi mã, nhận gói đề và gửi gói kết quả. Chuỗi mở đầu có dạng `;MSV;MA_CAU_HOI`.

## 1. Chuỗi

Bài mẫu: [UDP_DATA1](../UDP_DATA1.java), [UDP_STRING1](../UDP_STRING1.java), [UDP_DATA2](../UDP_DATA2.java), [UDP_STRING2](../UDP_STRING2.java).

Nhóm `UDP_DATA` xử lý dãy số; nhóm `UDP_STRING` xử lý chuỗi ký tự hoặc các từ. Cả hai dùng chung khung gửi/nhận chuỗi có request ID.

```text
Gửi: ;MSV;MA_CAU_HOI
Nhận: requestId;duLieu
Trả: requestId;ketQua
```

Khung này tách request ID trước khi gọi `solve` và ghép lại lúc gửi. Các bài mẫu tách và ghép ID ngay trong `solve`.

```java
import java.net.*;

public class MauUDPChuoi {
    static final String HOST = "36.50.135.242";
    static final int PORT = 2207;
    static final String CREDS = ";MSV;MA_CAU_HOI";

    static String solve(String s) {
        // Thay bang xu ly cua de, tra ket qua chua kem requestId.
        return s;
    }

    public static void main(String[] args) throws Exception {
        try (DatagramSocket client = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName(HOST);
            byte[] hello = CREDS.getBytes();
            client.send(new DatagramPacket(hello, hello.length, address, PORT));

            byte[] buf = new byte[10001];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            client.receive(packet);

            String message = new String(
                    packet.getData(), 0, packet.getLength()).trim();
            String[] parts = message.split(";", 2);
            String requestId = parts[0];
            String result = requestId + ";" + solve(parts[1]);

            byte[] answer = result.getBytes();
            client.send(new DatagramPacket(answer, answer.length, address, PORT));
        }
    }
}
```

Khi sao chép nguyên `solve` từ UDP_DATA1, UDP_STRING1, UDP_DATA2 hoặc UDP_STRING2, truyền toàn bộ `message` vào hàm rồi gửi thẳng kết quả, vì hàm đã ghép request ID.

| Bài | Port | Nội dung nhận sau ID | Kết quả sau ID |
| --- | --- | --- | --- |
| UDP_DATA1 | `2207` | `n;danhSachSo`, code tách bằng `[;,]` | Các số thiếu từ `1` đến `n`, cách nhau bằng `,` |
| UDP_STRING1 | `2208` | Chuỗi ký tự | `kyTu:viTri1,viTri2,...,`, vị trí bắt đầu từ `1` |
| UDP_DATA2 | `2207` | 50 số, code tách bằng `[;,]` | `max,min` |
| UDP_STRING2 | `2207` | Chuỗi các từ | Chuỗi viết hoa đầu mỗi từ |

Giữ dấu phân cách theo từng bài: UDP_DATA1 bỏ dấu phẩy cuối; UDP_STRING1 giữ dấu phẩy sau danh sách vị trí.

## 2. Object kèm ID 8 byte

Bài mẫu: [UDP_OBJECT1](../UDP_OBJECT1.java) với [UDP.Student](../UDP/Student.java), [UDP_OBJECT2](../UDP_OBJECT2.java) với [UDP.Customer](../UDP/Customer.java), port `2209`.

```text
Gửi ban đầu: ;MSV;MA_CAU_HOI
Nhận: [8 byte ID][object đã serialize]
Trả: [8 byte ID][object sau xử lý đã serialize]
```

Lấy 8 byte đầu trước khi tạo `ObjectInputStream`. Khi trả lời, ghi lại ID rồi ghi object vào cùng một mảng byte.

```java
import java.io.*;
import java.net.*;
import UDP.Student;

public class MauUDPObject {
    static final String HOST = "36.50.135.242";
    static final int PORT = 2209;
    static final String CREDS = ";MSV;MA_CAU_HOI";

    static Student solve(Student a) {
        // Dung getter/setter de sua cac truong theo de.
        return a;
    }

    public static void main(String[] args) throws Exception {
        try (DatagramSocket client = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName(HOST);
            byte[] hello = CREDS.getBytes();
            client.send(new DatagramPacket(hello, hello.length, address, PORT));

            byte[] buf = new byte[10001];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            client.receive(packet);

            ByteArrayInputStream input = new ByteArrayInputStream(
                    packet.getData(), 0, packet.getLength());
            byte[] id = new byte[8];
            input.read(id, 0, 8);
            Student data = (Student) new ObjectInputStream(input).readObject();

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            output.write(id);
            ObjectOutputStream objectOut = new ObjectOutputStream(output);
            objectOut.writeObject(solve(data));
            objectOut.flush();

            byte[] answer = output.toByteArray();
            client.send(new DatagramPacket(answer, answer.length, address, PORT));
        }
    }
}
```

Với dạng UDP_OBJECT2, đổi import và kiểu `Student` sang `UDP.Customer` và `Customer`. Giữ class dữ liệu tương ứng trong thư mục `UDP/`.

| Bài | Xử lý trên object |
| --- | --- |
| UDP_OBJECT1 | Chuẩn hóa `name`; tạo `email` bằng tên cuối, chữ cái đầu của các từ trước và đuôi `@ptit.edu.vn` |
| UDP_OBJECT2 | Định dạng `name`, tạo `userName`, đổi thứ tự ngày/tháng trong `dayOfBirth` |

`TCP.Customer` và `UDP.Customer` là hai class ở hai package riêng. Chọn import theo giao thức của bài.
