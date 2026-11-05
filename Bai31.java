import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import RMI.ObjectService;
import RMI.TicketSla;

public class Bai31 {
    private static final String HOST = "36.50.135.242";
    private static final String[] CREDS = {"B23DCAT120","cyLoolxE"}; 
    public static Object solve(TicketSla a) {
        String priority = a.getPriority();
        int hours = a.getOpenedHoursAgo();
        boolean isBreached = false;
        if ("CRITICAL".equalsIgnoreCase(priority) && hours > 2) {
            isBreached = true;
        } else if ("HIGH".equalsIgnoreCase(priority) && hours > 8) {
            isBreached = true;
        } else if ("MEDIUM".equalsIgnoreCase(priority) && hours > 24) {
            isBreached = true;
        } else if ("LOW".equalsIgnoreCase(priority) && hours > 72) {
            isBreached = true;
        }
        a.setBreached(isBreached);
        if (!isBreached) {
            a.setAction("MONITOR");
        } else {
            if ("CRITICAL".equalsIgnoreCase(priority) || hours > 96) {
                a.setAction("ESCALATE_L2");
            } else {
                a.setAction("ESCALATE_L1");
            }
        }
        return a;
    }
    public static void main(String[] args) throws Exception {
        try {
            Registry client = LocateRegistry.getRegistry(HOST);
            ObjectService service = (ObjectService) client.lookup("RMIObjectService");
            TicketSla res = (TicketSla) service.requestObject(CREDS[0], CREDS[1]);
            service.submitObject(CREDS[0], CREDS[1], (Serializable) solve(res));
        } catch (Exception e) {
        }
    }
}
