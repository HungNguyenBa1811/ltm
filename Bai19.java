import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import RMI.CharacterService;

public class Bai19 {
    private static final String HOST = "36.50.135.242";
    private static final String[] CREDS = {"B23DCAT120","k4S6AruV"}; 
    public static String solve(String a) {
        int n = Integer.valueOf(a);
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder roman = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (n >= values[i]) {
                n -= values[i];
                roman.append(symbols[i]);
            }
        }
        return roman.toString();
    }
    public static void main(String[] args) throws Exception {
        try {
            Registry client = LocateRegistry.getRegistry(HOST);
            CharacterService service = (CharacterService) client.lookup("RMICharacterService");
            String res = service.requestCharacter(CREDS[0], CREDS[1]);
            service.submitCharacter(CREDS[0], CREDS[1], solve(res));
        } catch (Exception e) {
        }
    }
}
