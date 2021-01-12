package com.mycompany.app;
import com.mycompany.app.TopologicalSort.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TopologicalSortTest {
    @Test
    public void TopoSortTest(){
        TopologicalSort obj = new TopologicalSort();
        String[] projects1 = {"a", "b", "c", "d", "e", "f"};
        String[][] dependencies1 = {{"a", "d"}, {"f", "b"}, {"b", "d"}, {"f", "a"}, {"d", "c"}};

        String[] projects2 = {"a", "b", "c", "d", "e", "f"};
        String[][] dependencies2 = {{"a", "d"}, {"b", "f"}, {"d", "b"}, {"f", "a"}, {"d", "c"}};

        String[] orderList1 = obj.inBondBasedTopoSort(projects1, dependencies1);
        for(String project : orderList1){
            System.out.println(project);
        }

        String[] orderList2 = obj.inBondBasedTopoSort(projects2, dependencies2);
        assertNull(orderList2);
        System.out.println("no result found");

        orderList1 = obj.dfsBasedTopoSort(projects1, dependencies1);
        for(String project : orderList1){
            System.out.println(project);
        }

        orderList2 = obj.dfsBasedTopoSort(projects2, dependencies2);
        assertNull(orderList2);
        System.out.println("no result found");
    }
}
