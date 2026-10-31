import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Bai28 {
    private static final String HOST = "36.50.135.242";
    private static final int PORT = 2211; 
    private static final String CREDS = "B23DCAT120;st0PHsyo";
    public static String solve(String s) {
        s = s.replace("{", "")
        .replace("}", "")
        .replace("\"", "")
        .replace(":", "=");
        String w[] = s.split(",");
        w[3] = w[3].replace("true", "1").replace("false", "0");
        return w[0] + ";" + w[1] + ";" + w[3];
    }
    public static void main(String[] args) throws Exception {
        try (SocketChannel client = SocketChannel.open()) {
            client.connect(new InetSocketAddress(HOST, PORT));
            writeFrame(client, CREDS);
            String a = "";
            for (int i = 0; i < 2; i++) 
                a += readFrame(client);
            // System.out.println(solve(a));
            writeFrame(client, solve(a));
        } catch (Exception e) {
        }
    }

    static void writeFrame(SocketChannel sc, String msg) throws Exception {
        byte[] res = msg.getBytes();
        ByteBuffer bf = ByteBuffer.allocate(4 + res.length);
        bf.putInt(res.length);
        bf.put(res);
        bf.flip();
        while (bf.hasRemaining()) sc.write(bf);
    }

    static String readFrame(SocketChannel sc) throws Exception {
        ByteBuffer hd = ByteBuffer.allocate(4);
        while (hd.hasRemaining()) sc.read(hd);
        hd.flip();
        int len = hd.getInt();
        ByteBuffer bf = ByteBuffer.allocate(len);
        while (bf.hasRemaining()) sc.read(bf);
        bf.flip();
        return new String(bf.array());
    }
}
