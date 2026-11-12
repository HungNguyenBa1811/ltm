import ptit.dblab.judge.network.server.soap.CharacterService;
import ptit.dblab.judge.network.server.soap.SoapCharacterService;

public class SOAP_CHARACTER1 {
    private final static String[] CREDS = {"B23DCAT120", "YmV9KU7R"};
    public static String solve(String s) {
        StringBuilder ss = new StringBuilder(s);
        return ss.reverse().toString();
    }
    public static void main(String[] args) {
        SoapCharacterService service = new CharacterService().getSoapCharacterServicePort();
        String a = service.requestString(CREDS[0], CREDS[1]);
        a = solve(a);
        service.submitString(CREDS[0], CREDS[1], a);
    }    
}
