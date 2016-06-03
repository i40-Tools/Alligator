

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.cs3.prolog.connector.Connector;
import org.cs3.prolog.connector.common.QueryUtils;
import org.cs3.prolog.connector.process.PrologProcess;
import org.cs3.prolog.connector.process.PrologProcessException;

public class ConnectorDemo {

	public static void main(String[] args) {
        try {
            PrologProcess process = Connector.newPrologProcess();
            String consultQuery = QueryUtils.bT("reconsult", "'C:/DIC/WorkspaceI40/Rules4AMLIntegration/src/resources/father.pl'");
            process.queryOnce(consultQuery);
            String query = QueryUtils.bT("father_of", "tim", "B");
            List<Map<String, Object>> results = process.queryAll(query);
            for (Map<String, Object> r : results) {
                System.out.println(r.get("B") + " is a child of john");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
