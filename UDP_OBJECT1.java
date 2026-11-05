import java.io.*;
import java.net.*;

import UDP.Student;

public class UDP_OBJECT1 {
    private static final String HOST = "36.50.135.242";
    private static final int PORT = 2209;
    private static final String CREDS = ";B23DCAT120;QhdUvdJS"; 
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
        Student a = (Student) new ObjectInputStream(ba).readObject();

        String wn[] = a.getName().toLowerCase().split("\\s+");
        String resname = "", resmail = "";
        for (int i = 0; i < wn.length; i++) {
            if (i == wn.length - 1) {
                resmail = wn[i] + resmail + "@ptit.edu.vn";
            } else resmail += wn[i].substring(0, 1);
            resname += wn[i].substring(0, 1).toUpperCase() + wn[i].substring(1) + " ";
        }
        a.setName(resname.trim());
        a.setEmail(resmail);
        
        ByteArrayOutputStream bb = new ByteArrayOutputStream();
        bb.write(id.getBytes());
        new ObjectOutputStream(bb).writeObject(a);
        s = bb.toByteArray();
        sp = new DatagramPacket(s, s.length, InetAddress.getByName(HOST), PORT);
        client.send(sp);
    }    
}