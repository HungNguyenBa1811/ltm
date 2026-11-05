# Pattern TCP

Mở kết nối, gửi `MSV;MA_CAU_HOI`, nhận dữ liệu rồi xử lý và gửi kết quả. Dùng loại stream đề yêu cầu.

## 1. Chuỗi theo dòng

Bài mẫu: [TCP_CHARACTER1](../TCP_CHARACTER1.java) lọc tên miền `.edu`; [TCP_CHARACTER2](../TCP_CHARACTER2.java) đếm ký tự lặp.

Giữ phần đọc/ghi theo dòng, thay nội dung `solve` theo đề mới.

```java
import java.io.*;
import java.net.*;

public class MauTCPDong {
    static final String HOST = "36.50.135.242";
    static final int PORT = 2208;
    static final String CREDS = "MSV;MA_CAU_HOI";

    static String solve(String s) {
        // Thay bang xu ly cua de.
        return s;
    }

    public static void main(String[] args) throws Exception {
        try (Socket client = new Socket(HOST, PORT)) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(client.getOutputStream()));

            out.write(CREDS);
            out.newLine();
            out.flush();

            String data = in.readLine();
            String result = solve(data);

            out.write(result);
            out.newLine();
            out.flush();
        }
    }
}
```

Mỗi lần gửi một dòng dùng `write`, `newLine`, `flush`. `readLine()` trả phần nội dung của dòng.

## 2. Chuỗi qua byte thô

Bài mẫu: [TCP_BYTE_RAW1](../TCP_BYTE_RAW1.java) tìm cặp có chênh lệch nhỏ nhất; [TCP_BYTE_RAW2](../TCP_BYTE_RAW2.java) tìm số lớn thứ hai và vị trí.

Trong khung mục 1, thêm `import java.nio.charset.StandardCharsets;` và thay phần thân của `try` bằng đoạn sau. Giữ `solve(String)` để trả chuỗi kết quả.

```java
InputStream in = client.getInputStream();
OutputStream out = client.getOutputStream();

out.write(CREDS.getBytes(StandardCharsets.UTF_8));
out.flush();

byte[] buf = new byte[100001];
int len = in.read(buf);
String data = new String(buf, 0, len).trim();

out.write(solve(data).getBytes(StandardCharsets.UTF_8));
out.flush();
```

Như TCP_BYTE_RAW1 và TCP_BYTE_RAW2, `solve` nhận chuỗi và trả chuỗi; chuyển kết quả thành byte trước khi gửi.

## 3. Dữ liệu có kiểu

Bài mẫu: [TCP_DATA1](../TCP_DATA1.java), [TCP_DATA2](../TCP_DATA2.java). Dùng `DataInputStream` và `DataOutputStream`.

### Nhận hai số, trả hai số

Đặt đoạn này trong `try (Socket client = new Socket(HOST, PORT))` như khung mục 1. TCP_DATA1 dùng port `2206`.

```java
DataInputStream in = new DataInputStream(client.getInputStream());
DataOutputStream out = new DataOutputStream(client.getOutputStream());

out.writeUTF(CREDS);
out.flush();

int a = in.readInt();
int b = in.readInt();

out.writeInt(a + b);
out.writeInt(a * b);
out.flush();
```

Với dạng TCP_DATA1, gửi tổng trước, tích sau. Khi đổi đề, thay phép tính và các lệnh đọc/ghi theo dữ liệu đề yêu cầu.

### Nhận chuỗi và số, trả chuỗi

TCP_DATA2 dùng port `2207`. Sau khi tạo hai data stream và gửi `CREDS` bằng `writeUTF`, dùng:

```java
String data = in.readUTF();
int k = in.readInt();
out.writeUTF(solve(data, k));
out.flush();
```

Khai báo hàm xử lý dạng `static String solve(String s, int k)`. Trong TCP_DATA2, `k` là độ dịch để giải mã chữ cái; chữ số được giữ nguyên.

## 4. Object

Bài mẫu: [TCP_OBJECT1](../TCP_OBJECT1.java) với [TCP.Laptop](../TCP/Laptop.java), [TCP_OBJECT2](../TCP_OBJECT2.java) với [TCP.Customer](../TCP/Customer.java). Cả hai dùng port `2209`.

```java
import java.io.*;
import java.net.*;
import TCP.Laptop;

public class MauTCPObject {
    static final String HOST = "36.50.135.242";
    static final int PORT = 2209;
    static final String CREDS = "MSV;MA_CAU_HOI";

    static Laptop solve(Laptop a) {
        // Dung getter/setter de sua cac truong theo de.
        return a;
    }

    public static void main(String[] args) throws Exception {
        try (Socket client = new Socket(HOST, PORT)) {
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());

            out.writeObject(CREDS);
            out.flush();

            Laptop data = (Laptop) in.readObject();
            out.writeObject(solve(data));
            out.flush();
        }
    }
}
```

Với dạng TCP_OBJECT2, đổi import và kiểu `Laptop` sang `TCP.Customer` và `Customer`. Giữ class dữ liệu của đề, gồm package, các trường và `serialVersionUID`.

| Bài | Các trường xử lý |
| --- | --- |
| TCP_OBJECT1 | Đổi chỗ từ đầu/cuối của `name`; đảo chữ số của `quantity` |
| TCP_OBJECT2 | Định dạng `name`, tạo `userName`, đổi thứ tự ngày/tháng trong `dayOfBirth` |

## 5. GZIP

Bài mẫu: [TCP_GZIP1](../TCP_GZIP1.java), [TCP_GZIP2](../TCP_GZIP2.java), port `2210`. Nội dung được gửi theo dòng qua luồng nén.

```java
import java.io.*;
import java.net.*;
import java.util.zip.*;

public class MauTCPGzip {
    static final String HOST = "36.50.135.242";
    static final int PORT = 2210;
    static final String CREDS = "MSV;MA_CAU_HOI";

    static String solve(String s) {
        // Thay bang xu ly cua de.
        return s;
    }

    public static void main(String[] args) throws Exception {
        try (Socket client = new Socket(HOST, PORT)) {
            GZIPOutputStream out = new GZIPOutputStream(
                    client.getOutputStream(), true);
            out.write((CREDS + "\n").getBytes());
            out.flush();

            DataInputStream in = new DataInputStream(
                    new GZIPInputStream(client.getInputStream()));
            ByteArrayOutputStream line = new ByteArrayOutputStream();
            int next;
            while ((next = in.readUnsignedByte()) != '\n') {
                line.write(next);
            }

            String result = solve(line.toString());
            out.write((result + "\n").getBytes());
            out.flush();
            out.finish();
        }
    }
}
```

Giữ tham số `true`, các lần `flush()` và `finish()` như bài mẫu. Phần thay theo đề nằm trong `solve`:

- TCP_GZIP1: đảo chuỗi, mã hóa Base64 của chuỗi đã đảo, trả `chuoiDao|base64`.
- TCP_GZIP2: chuyển sang `char[]`, dùng `Arrays.sort`, trả chuỗi đã sắp xếp.

## 6. NIO với frame độ dài

Bài mẫu: [TCP_NIO1](../TCP_NIO1.java), [TCP_NIO2](../TCP_NIO2.java), port `2211`.

Mỗi frame gồm 4 byte độ dài rồi đến phần dữ liệu. Dùng hai hàm `writeFrame` và `readFrame` cho cả mã ban đầu lẫn dữ liệu bài làm.

```java
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class MauTCPNio {
    static final String HOST = "36.50.135.242";
    static final int PORT = 2211;
    static final String CREDS = "MSV;MA_CAU_HOI";
    static final int FRAME_COUNT = 3; // TCP_NIO1: 3; TCP_NIO2: 2.

    static String solve(String s) {
        // Thay bang xu ly cua de.
        return s;
    }

    public static void main(String[] args) throws Exception {
        try (SocketChannel client = SocketChannel.open()) {
            client.connect(new InetSocketAddress(HOST, PORT));
            writeFrame(client, CREDS);

            String data = "";
            for (int i = 0; i < FRAME_COUNT; i++) {
                data += readFrame(client);
            }
            writeFrame(client, solve(data));
        }
    }

    static void writeFrame(SocketChannel sc, String msg) throws Exception {
        byte[] data = msg.getBytes();
        ByteBuffer buf = ByteBuffer.allocate(4 + data.length);
        buf.putInt(data.length);
        buf.put(data);
        buf.flip();
        while (buf.hasRemaining()) sc.write(buf);
    }

    static String readFrame(SocketChannel sc) throws Exception {
        ByteBuffer header = ByteBuffer.allocate(4);
        while (header.hasRemaining()) sc.read(header);
        header.flip();

        int len = header.getInt();
        ByteBuffer body = ByteBuffer.allocate(len);
        while (body.hasRemaining()) sc.read(body);
        return new String(body.array());
    }
}
```

Chọn số frame theo đề. TCP_NIO1 ghép 3 frame rồi lấy các trường từ những dòng phân cách bằng `\r\n`. TCP_NIO2 ghép 2 frame, xử lý chuỗi dạng JSON và trả các trường cách nhau bằng `;`.
