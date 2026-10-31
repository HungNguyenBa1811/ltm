import java.io.*;
import java.net.*;

import UDP.Customer;

public class Bai14 {
    private static final String HOST = "36.50.135.242";
    private static final int PORT = 2209;
    private static final String CREDS = ";B23DCAT120;Z9adyTH8"; 
    public static void main(String[] args) throws Exception {
        DatagramSocket client = new DatagramSocket();
        DatagramPacket sp, rp;
        byte[] s, r, ix;
        s = CREDS.getBytes();
        sp = new DatagramPacket(s, s.length, InetAddress.getByName(HOST), PORT);
        client.send(sp);

        r = new byte[10001];
        rp = new DatagramPacket(r, r.length);
        client.receive(rp);
        
        ByteArrayInputStream ba = new ByteArrayInputStream(rp.getData());
        ix = new byte[8];
        ba.read(ix, 0, 8);
        String id = new String(ix);
        Customer a = (Customer) new ObjectInputStream(ba).readObject();

        String name = a.getName();
        String wn[] = name.split("\\s+");
        String wd[] = a.getDayOfBirth().split("-");
        String resname = wn[wn.length - 1].toUpperCase() + ",";
        for (int i = 0; i < wn.length - 1; i++) {
            resname += " " + wn[i].substring(0, 1).toUpperCase() + wn[i].substring(1).toLowerCase();
        }
        String rescode = "";
        for (int i = 0; i < wn.length - 1; i++){
            rescode += wn[i].substring(0, 1).toLowerCase();
        }
        rescode += wn[wn.length - 1].toLowerCase();
        a.setName(resname);
        a.setUserName(rescode);
        a.setDayOfBirth(wd[1] + "/" + wd[0] + "/" + wd[2]);
        
        ByteArrayOutputStream bb = new ByteArrayOutputStream();
        bb.write(id.getBytes());
        new ObjectOutputStream(bb).writeObject(a);
        s = bb.toByteArray();
        sp = new DatagramPacket(s, s.length, InetAddress.getByName(HOST), PORT);
        client.send(sp);
    }    
}