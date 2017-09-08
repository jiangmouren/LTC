package finished;

import org.junit.Test;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by eljian on 8/31/2017.
 */
public class ShortestWordDistanceIITest {
    List<String> list = new ArrayList<>();
    ShortestWordDistanceII obj;
    {
        this.list.add("practice");
        this.list.add("makes");
        this.list.add("perfect");
        this.list.add("coding");
        this.list.add("makes");
        this.obj = new ShortestWordDistanceII(list);
    }

    @Test
    public void test() throws Exception {
        assertEquals(3, this.obj.find1("coding", "practice"));
        assertEquals(1, this.obj.find1("coding", "makes"));
        assertEquals(3, this.obj.find2("coding", "practice"));
        assertEquals(1, this.obj.find2("coding", "makes"));
    }
}