package utils;

import java.util.List;
import java.util.concurrent.ExecutionException;
import Entities.Test;
import Services.TestService;

/**
 *
 * @author GENISYS-X
 */
public class answer {

    static String[][] qcans = new String[15][2];

    public static String[][] getAnswsers() {
        TestService ts = new TestService();
        try {
            List<Test> tests = ts.getAllTests();
            for (int i = 0; i < tests.size(); i++) {
                qcans[i][0] = tests.get(i).getQuestion();
                qcans[i][1] = tests.get(i).getReponse();
                //  System.out.println(i + " : " + tests.get(i).getReponse());
                if (i == 14) {
                    break;
                }
            }
        } catch (Exception e) {

        }

        return qcans;
    }

}
