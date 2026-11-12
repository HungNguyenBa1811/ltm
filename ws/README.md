# Bài SOAP (JAX-WS)

Thư mục này là project Maven cho các bài web service SOAP như `WS_DATA1`, `WS_DATA2`, `SOAP_CHARACTER1`. Mỗi file `.java` trong `src/main/java/` là một bài độc lập. Client gọi service để lấy dữ liệu, xử lý trong `solve(...)` rồi nộp kết quả.

Code client của service (`DataService`, `CharacterService`, `ObjectService`, ...) được sinh từ WSDL vào package `ptit.dblab.judge.network.server.soap`. Không sửa tay các file trong package này, vì mỗi lần build chúng được sinh lại.

## Yêu cầu

- JDK 17 trở lên, kiểm tra bằng `java -version`.
- Maven 3.9 trở lên, kiểm tra bằng `mvn -version`.
- Máy kết nối được tới server `36.50.135.242:2221`. Mỗi lần build, Maven tải WSDL từ server này.

## Chạy bài

Đứng trong thư mục `ws` và truyền tên file hoặc tên class cho script:

```powershell
cd ws
.\run.ps1 WS_DATA2.java
```

Với Git Bash, Linux hoặc macOS:

```bash
cd ws
./run.sh WS_DATA2.java
```

Script sinh code từ WSDL, biên dịch rồi chạy class đó, nên không cần `mvn compile` trước. Có thể truyền `WS_DATA2`, `WS_DATA2.java` hoặc `src/main/java/WS_DATA2.java`.

Chạy bài là nộp kết quả thật lên server. Kiểm tra `solve(...)` và mã sinh viên trong `CREDS` trước khi chạy.

Nếu PowerShell chặn chạy script:

```powershell
powershell -ExecutionPolicy Bypass -File .\run.ps1 WS_DATA2
```

## Làm bài mới

1. Sao chép một bài cùng service, ví dụ `WS_DATA2.java` thành `WS_DATA3.java`, rồi đổi tên class cho khớp tên file.
2. Sửa mã sinh viên trong `CREDS` và viết lại `solve(...)` theo đề.
3. Chạy `.\run.ps1 WS_DATA3`.

Cách lấy client của từng service:

```java
SoapDataService data = new DataService().getSoapDataServicePort();
SoapCharacterService chars = new CharacterService().getSoapCharacterServicePort();
SoapObjectService objects = new ObjectService().getSoapObjectServicePort();
```

Xem các hàm `request...` và `submit...` của service trong file `Soap...Service.java` tương ứng ở `src/main/java/ptit/dblab/judge/network/server/soap/`.

## Thêm service mới

Thêm URL WSDL vào `<wsdlUrls>` trong `pom.xml`:

```xml
<wsdlUrl>http://36.50.135.242:2221/TenService?wsdl</wsdlUrl>
```

Lần chạy script tiếp theo sẽ tự sinh code cho service mới. Muốn sinh code để đọc trước mà chưa chạy bài, dùng `mvn -Pcli generate-sources`.

JDK 11 trở lên không còn lệnh `wsimport`, nên project dùng plugin `jaxws-maven-plugin` thay cho lệnh này.

## Vì sao script build vào `target-cli/`

Script chạy Maven với profile `cli` (khai báo trong `pom.xml`), nên kết quả build nằm ở `target-cli/` thay vì `target/`. Extension Java của VS Code tự biên dịch vào `target/` bằng JDK riêng của nó (hiện là 25). Nếu dùng chung thư mục, Maven chạy bằng JDK 17 sẽ gặp lỗi:

```
... has been compiled by a more recent version of the Java Runtime (class file version 69.0),
this version of the Java Runtime only recognizes class file versions up to 61.0
```

Vì vậy nên chạy bài bằng script, không chạy `mvn compile` thường. Nếu vẫn gặp lỗi này, xoá `target-cli/` rồi chạy lại.

## Khi thi với JDK 8

JDK 8 có sẵn `wsimport`, không cần Maven. Đứng ở thư mục gốc project có thư mục `src`:

```bash
wsimport -keep -s src http://36.50.135.242:2221/DataService?wsdl
javac -d out -sourcepath src src/WS_DATA2.java
java -cp out WS_DATA2
```

Với IDE, chỉ cần chạy lệnh `wsimport` rồi bấm Run file bài làm. NetBeans còn tạo được client qua New → Web Service Client mà không cần gõ lệnh.
