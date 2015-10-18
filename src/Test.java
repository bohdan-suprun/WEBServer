import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bod on 01.09.15.
 */


public class Test {
    public static void main(String[] arg){
        Map<String, String> m = new TreeMap<String, String>();
        m.put("1", "1");
        m.put("1", "2");
        System.out.println(m.get("1"));

    }
}
