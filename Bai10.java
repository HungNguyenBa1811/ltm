import java.io.*;
import java.net.*;

import TCP.Customer;

public class Bai10 {
    private static final String HOST = "36.50.135.242";
    private static final int PORT = 2209;
    private static final String CREDS = "B23DCAT120;kl818WEh"; 
    public static void main(String[] args) {
        try (Socket client = new Socket(HOST, PORT)) {
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            out.writeObject(CREDS);
            out.flush();
            Customer a = (Customer) in.readObject();
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

            out.writeObject(a);
            out.flush();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
