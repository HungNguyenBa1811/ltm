import java.net.URI;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import RMI.CharacterService;

public class RMI_CHARACTER1 {
    private static final String HOST = "36.50.135.242";
    private static final String[] CREDS = {"B23DCAT120","Cg9wQpud"}; 
    public static String solve(String a) throws Exception {
        return (new URI(null, null, a, null)).toASCIIString(); 
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
