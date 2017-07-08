/**
 * Question:
 * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei),
 * find the minimum number of conference rooms required.
 * For example,
 * Given [[0, 30],[5, 10],[15, 20]],
 * return 2.
 */

/**
 * Analysis:
 * This one essentially is asking what is the maximum parallelism of all the meetings?
 * This is also similar to using streaming to count the number of cars in a garage:
 * Whenever there ia a car in, count++; whenever there is a car out, count--.
 * I don't care which car is in or which car is out.
 * In this case, whenever there is a start, count++; whenever there is an end, count--.
 * I don't care if it is meeting_1 overlaps with meeting_3 or meeting_2 overlaps with meeting_3.
 *
 */
package Finished.arrayANDstring;

import java.util.*;
public class MeetingRoomsII {
    public static class Meeting{
        int start, end;
        Meeting(int start, int end){
            this.start = start;
            this.end = end;
        }
    }

    private class Node{
        boolean start;
        int val;
        Node(boolean type, int x){
            this.start = type;
            this.val = x;
        }
    }
    public int minMeetingRooms(Meeting[] meetings){
        List<Node> list = new ArrayList<>();
        for(Meeting tmp : meetings){
            list.add(new Node(true, tmp.start));
            list.add(new Node(false, tmp.end));
        }
        Collections.sort(list, new helperComparator());
        int count = 0, max = 0;
        for(Node node : list){
            if(node.start){
                count++;
                max = Math.max(max, count);
            }
            else{
                count--;
            }
        }
        return max;
    }

    class helperComparator implements Comparator<Node> {
        @Override
        public int compare(Node a, Node b){
            return a.val-b.val;
        }
    }

}
