import com.gruppometa.sbntecaremota.upgrade.UpgradeComponent;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestPartiotions {
    @Test
    public void test(){
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("6");
        list.add("7");
        list.add("8");
        List<List<String>> lista = UpgradeComponent.getPartition(list, 10);
        for(List<String> l: lista){
            System.out.println(l.stream().collect(Collectors.joining(",")));
        }
    }
}

