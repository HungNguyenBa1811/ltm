import java.io.*;
import java.net.*;

import TCP.Laptop;

public class TCP_OBJECT1 {
    private static final String HOST = "36.50.135.242";
    private static final int PORT = 2209;
    private static final String CREDS = "B23DCAT120;MY513Pro"; 
    public static void main(String[] args) {
        try (Socket client = new Socket(HOST, PORT)) {
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            out.writeObject(CREDS);
            out.flush();
            Laptop a = (Laptop) in.readObject();
            String name = a.getName();
            String wn[] = name.split("\\s+");
            String resname = wn[wn.length - 1] + " ";
            for (int i = 1; i < wn.length - 1; i++) {
                resname += wn[i] + " ";
            }
            resname += wn[0];
            a.setName(resname);

            String quant = a.getQuantity() + "";
            a.setQuantity(Integer.valueOf(new StringBuilder(quant + "").reverse().toString()));
            out.writeObject(a);
            out.flush();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
