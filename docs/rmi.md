# Pattern RMI

Lấy registry, lookup service rồi gọi hàm nhận dữ liệu. Xử lý xong thì gọi hàm nộp. Mỗi lần gọi truyền mã sinh viên và mã câu hỏi thành hai tham số riêng.

## 1. Chọn service

| Dữ liệu | Interface | Tên lookup | Hàm nhận / nộp | Bài mẫu |
| --- | --- | --- | --- | --- |
| `byte[]` | [ByteService](../RMI/ByteService.java) | `RMIByteService` | `requestData` / `submitData` | [RMI_BYTE1](../RMI_BYTE1.java), [RMI_BYTE2](../RMI_BYTE2.java) |
| `String` | [CharacterService](../RMI/CharacterService.java) | `RMICharacterService` | `requestCharacter` / `submitCharacter` | [RMI_CHARACTER1](../RMI_CHARACTER1.java), [RMI_CHARACTER2](../RMI_CHARACTER2.java) |
| `Object` | [DataService](../RMI/DataService.java) | `RMIDataService` | `requestData` / `submitData` | [RMI_DATA1](../RMI_DATA1.java), [RMI_DATA2](../RMI_DATA2.java) |
| `Serializable` | [ObjectService](../RMI/ObjectService.java) | `RMIObjectService` | `requestObject` / `submitObject` | [RMI_OBJECT](../RMI_OBJECT.java) |

Các bài dùng `LocateRegistry.getRegistry(HOST)` và các interface có sẵn trong `RMI/`.

## 2. Khung ByteService và CharacterService

```java
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import RMI.ByteService;

public class MauRMIByte {
    static final String HOST = "36.50.135.242";
    static final String STUDENT_CODE = "MSV";
    static final String Q_CODE = "MA_CAU_HOI";

    static byte[] solve(byte[] data) {
        // Thay bang xu ly cua de.
        return data;
    }

    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry(HOST);
        ByteService service = (ByteService) registry.lookup("RMIByteService");

        byte[] data = service.requestData(STUDENT_CODE, Q_CODE);
        byte[] result = solve(data);
        service.submitData(STUDENT_CODE, Q_CODE, result);
    }
}
```

Với dạng chuỗi, đổi import sang `RMI.CharacterService`, đổi hàm xử lý thành `static String solve(String data)`, rồi thay phần lấy service và gọi hàm bằng:

```java
CharacterService service = (CharacterService) registry.lookup("RMICharacterService");
String data = service.requestCharacter(STUDENT_CODE, Q_CODE);
String result = solve(data);
service.submitCharacter(STUDENT_CODE, Q_CODE, result);
```

| Bài | Thân hàm `solve` |
| --- | --- |
| RMI_BYTE1 | Đếm tần suất, chọn byte xuất hiện ít nhất, trả `new byte[] {giaTri, (byte) soLan}` |
| RMI_BYTE2 | Đếm tần suất, chọn byte xuất hiện nhiều nhất, trả cùng dạng RMI_BYTE1 |
| RMI_CHARACTER1 | Dùng `new URI(null, null, data, null).toASCIIString()`; thêm import `java.net.URI` và `throws Exception` cho `solve` |
| RMI_CHARACTER2 | Chuyển số trong chuỗi sang số La Mã theo bảng giá trị giảm dần |

RMI_BYTE1 và RMI_BYTE2 duyệt lại mảng ban đầu để chọn kết quả. Khi có nhiều giá trị cùng tần suất được chọn, phép so sánh `<` hoặc `>` giữ giá trị gặp trước.

## 3. DataService

Bài mẫu: [RMI_DATA1](../RMI_DATA1.java), [RMI_DATA2](../RMI_DATA2.java). `requestData` trả `Object`; chọn cách lấy dữ liệu theo đề. `submitData` nhận kết quả qua tham số `Object`.

### Nhận mảng, trả danh sách

```java
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import RMI.DataService;

public class MauRMIData {
    static final String HOST = "36.50.135.242";
    static final String STUDENT_CODE = "MSV";
    static final String Q_CODE = "MA_CAU_HOI";

    static List<Integer> solve(int[] a) {
        List<Integer> result = new ArrayList<>();
        // Them cac vi tri theo yeu cau cua de.
        return result;
    }

    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry(HOST);
        DataService service = (DataService) registry.lookup("RMIDataService");

        int[] data = (int[]) service.requestData(STUDENT_CODE, Q_CODE);
        service.submitData(STUDENT_CODE, Q_CODE, solve(data));
    }
}
```

RMI_DATA1 trả `List<Integer>` chứa các vị trí cực đại cục bộ, đánh số từ `1`. Xét riêng hai đầu mảng; với phần tử ở giữa, so sánh với cả hai hàng xóm.

### Nhận chuỗi, trả chuỗi

Với RMI_DATA2, dùng `static String solve(String csvData)` rồi thay hai dòng nhận/nộp bằng:

```java
String data = service.requestData(STUDENT_CODE, Q_CODE).toString();
service.submitData(STUDENT_CODE, Q_CODE, solve(data));
```

Hàm xử lý tách số bằng `split("\\s*,\\s*")`, tính trung bình, độ lệch chuẩn với mẫu số `n` và p95 tại chỉ số `ceil(n * 0.95) - 1` sau khi sắp tăng dần. Chuỗi trả có dạng:

```text
average=%.2f;stddev=%.2f;p95=%.2f
```

Dùng `String.format(Locale.US, ...)` như RMI_DATA2 để định dạng hai chữ số thập phân bằng dấu chấm.

## 4. ObjectService

Bài mẫu: [RMI_OBJECT](../RMI_OBJECT.java), class [TicketSla](../RMI/TicketSla.java).

```java
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import RMI.ObjectService;
import RMI.TicketSla;

public class MauRMIObject {
    static final String HOST = "36.50.135.242";
    static final String STUDENT_CODE = "MSV";
    static final String Q_CODE = "MA_CAU_HOI";

    static TicketSla solve(TicketSla a) {
        // Dung getter/setter de sua cac truong theo de.
        return a;
    }

    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry(HOST);
        ObjectService service = (ObjectService) registry.lookup("RMIObjectService");

        TicketSla data = (TicketSla) service.requestObject(STUDENT_CODE, Q_CODE);
        service.submitObject(STUDENT_CODE, Q_CODE, solve(data));
    }
}
```

`TicketSla` triển khai `Serializable`, nên kết quả của `solve` truyền được vào `submitObject`. Khung dùng trực tiếp kiểu `TicketSla`; RMI_OBJECT khai báo kết quả xử lý là `Object` rồi ép sang `Serializable` lúc nộp.

Quy tắc xử lý trong RMI_OBJECT:

| Priority | Điều kiện đặt `breached = true` |
| --- | --- |
| `CRITICAL` | `openedHoursAgo > 2` |
| `HIGH` | `openedHoursAgo > 8` |
| `MEDIUM` | `openedHoursAgo > 24` |
| `LOW` | `openedHoursAgo > 72` |

Nếu chưa vi phạm, đặt `action` thành `MONITOR`. Nếu đã vi phạm và priority là `CRITICAL` hoặc số giờ lớn hơn `96`, đặt `ESCALATE_L2`; các trường hợp vi phạm còn lại đặt `ESCALATE_L1`.

Với bài object khác, dùng class của đề và thay phần xử lý bằng getter/setter trong `solve`. Giữ khung request/submit.
