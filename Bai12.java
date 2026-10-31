import java.net.*;

public class Bai12 {
    private static final String HOST = "36.50.135.242";
    private static final int PORT = 2207;
    private static final String CREDS = ";B23DCAT120;ECUuSPkC"; 
    public static String solve(String s) {
        String[] w = s.split(";");
        s = w[1];
        String[] w1 = s.split("\\s+");
        String ans = "";
        for (int i = 0; i < w1.length; i++) {
            ans += w1[i].substring(0, 1).toUpperCase() + w1[i].substring(1).toLowerCase() + " ";
        }
        String res = w[0] + ";" + ans.trim();
        return res;
    }

    public static void main(String[] args) throws Exception {
        DatagramSocket client = new DatagramSocket();
        byte[] s, r; 
        DatagramPacket sp, rp;
        s = CREDS.getBytes();
        sp = new DatagramPacket(s, s.length, InetAddress.getByName(HOST), PORT);
        client.send(sp);
        r = new byte[10001];
        rp = new DatagramPacket(r, r.length);
        client.receive(rp);
        String res = solve(new String(rp.getData()).trim());
        sp = new DatagramPacket(res.getBytes(), res.getBytes().length, InetAddress.getByName(HOST), PORT);
        client.send(sp);
    }
}