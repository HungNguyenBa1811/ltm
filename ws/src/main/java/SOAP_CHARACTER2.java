import java.util.*;

import ptit.dblab.judge.network.server.soap.CharacterService;
import ptit.dblab.judge.network.server.soap.SoapCharacterService;

public class SOAP_CHARACTER2 {
    private final static String[] CREDS = {"B23DCAT120", "XjFZjB73"};
    public static List<String> solve(List<String> a) {
        List<String> res1 = new ArrayList<>();
        List<String> res2 = new ArrayList<>();
        List<String> res3 = new ArrayList<>();
        for (String x : a) {
            String[] w = x.split("\\s+");
            for (int i = 2; i <= 4; i++) {
                String[] ww = w[i].split("=");
                w[i] = ww[0] + "=[" + ww[0].toUpperCase() + "]";
            }
            String ans = "";
            for (int i = 0; i < w.length; i++) {
                ans += w[i] + " ";
            }
            if (w[0].equals("ERROR")) res1.add(ans.trim());
            if (w[0].equals("WARN")) res2.add(ans.trim());
            if (w[0].equals("INFO")) res3.add(ans.trim());
        }
        res1.addAll(res2);
        res1.addAll(res3);
        return res1;
    }
    public static void main(String[] args) {
        SoapCharacterService service = new CharacterService().getSoapCharacterServicePort();
        List<String> a = service.requestStringArray(CREDS[0], CREDS[1]);
        a = solve(a);
        service.submitStringArray(CREDS[0], CREDS[1], a);
    }    
}
