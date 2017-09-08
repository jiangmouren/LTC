package finished;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jiangmouren on 8/16/17.
 */
public class NumberOfConnectedComponentsInAnUndirectedGraphTest {
    NumberOfConnectedComponentsInAnUndirectedGraph objectUnderTest = new NumberOfConnectedComponentsInAnUndirectedGraph();
    int[][] edges1 = {
            {0, 1},
            {1, 2},
            {3, 4}
    };
    int[][] edges2 = {
            {0, 1},
            {1, 2},
            {2, 3},
            {3, 4}
    };
    @Test
    public void solution() throws Exception {
        assertEquals(objectUnderTest.solution(5, edges1), 2);
        assertEquals(objectUnderTest.solution(5, edges2), 1);
    }

}