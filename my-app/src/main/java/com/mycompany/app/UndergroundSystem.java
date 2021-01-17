package com.mycompany.app;

/**
 * https://leetcode.com/problems/design-underground-system/
 * Implement the UndergroundSystem class:
 *
 * void checkIn(int id, string stationName, int t)
 * A customer with a card id equal to id, gets in the station stationName at time t.
 * A customer can only be checked into one place at a time.
 * void checkOut(int id, string stationName, int t)
 * A customer with a card id equal to id, gets out from the station stationName at time t.
 * double getAverageTime(string startStation, string endStation)
 * Returns the average time to travel between the startStation and the endStation.
 * The average time is computed from all the previous traveling from startStation to endStation that happened directly.
 * Call to getAverageTime is always valid.
 * You can assume all calls to checkIn and checkOut methods are consistent. If a customer gets in at time t1 at some station, they get out at time t2 with t2 > t1. All events happen in chronological order.
 *
 * Example 1:
 * Input
 * ["UndergroundSystem","checkIn","checkIn","checkIn","checkOut","checkOut","checkOut","getAverageTime","getAverageTime","checkIn","getAverageTime","checkOut","getAverageTime"]
 * [[],[45,"Leyton",3],[32,"Paradise",8],[27,"Leyton",10],[45,"Waterloo",15],[27,"Waterloo",20],[32,"Cambridge",22],["Paradise","Cambridge"],["Leyton","Waterloo"],[10,"Leyton",24],["Leyton","Waterloo"],[10,"Waterloo",38],["Leyton","Waterloo"]]
 * Output
 * [null,null,null,null,null,null,null,14.00000,11.00000,null,11.00000,null,12.00000]
 * Explanation
 * UndergroundSystem undergroundSystem = new UndergroundSystem();
 * undergroundSystem.checkIn(45, "Leyton", 3);
 * undergroundSystem.checkIn(32, "Paradise", 8);
 * undergroundSystem.checkIn(27, "Leyton", 10);
 * undergroundSystem.checkOut(45, "Waterloo", 15);
 * undergroundSystem.checkOut(27, "Waterloo", 20);
 * undergroundSystem.checkOut(32, "Cambridge", 22);
 * undergroundSystem.getAverageTime("Paradise", "Cambridge");       // return 14.00000. There was only one travel from "Paradise" (at time 8) to "Cambridge" (at time 22)
 * undergroundSystem.getAverageTime("Leyton", "Waterloo");          // return 11.00000. There were two travels from "Leyton" to "Waterloo", a customer with id=45 from time=3 to time=15 and a customer with id=27 from time=10 to time=20. So the average time is ( (15-3) + (20-10) ) / 2 = 11.00000
 * undergroundSystem.checkIn(10, "Leyton", 24);
 * undergroundSystem.getAverageTime("Leyton", "Waterloo");          // return 11.00000
 * undergroundSystem.checkOut(10, "Waterloo", 38);
 * undergroundSystem.getAverageTime("Leyton", "Waterloo");          // return 12.00000
 *
 * Example 2:
 * Input
 * ["UndergroundSystem","checkIn","checkOut","getAverageTime","checkIn","checkOut","getAverageTime","checkIn","checkOut","getAverageTime"]
 * [[],[10,"Leyton",3],[10,"Paradise",8],["Leyton","Paradise"],[5,"Leyton",10],[5,"Paradise",16],["Leyton","Paradise"],[2,"Leyton",21],[2,"Paradise",30],["Leyton","Paradise"]]
 * Output
 * [null,null,null,5.00000,null,null,5.50000,null,null,6.66667]
 *
 * Explanation
 * UndergroundSystem undergroundSystem = new UndergroundSystem();
 * undergroundSystem.checkIn(10, "Leyton", 3);
 * undergroundSystem.checkOut(10, "Paradise", 8);
 * undergroundSystem.getAverageTime("Leyton", "Paradise"); // return 5.00000
 * undergroundSystem.checkIn(5, "Leyton", 10);
 * undergroundSystem.checkOut(5, "Paradise", 16);
 * undergroundSystem.getAverageTime("Leyton", "Paradise"); // return 5.50000
 * undergroundSystem.checkIn(2, "Leyton", 21);
 * undergroundSystem.checkOut(2, "Paradise", 30);
 * undergroundSystem.getAverageTime("Leyton", "Paradise"); // return 6.66667
 *
 *
 * Constraints:
 *
 * There will be at most 20000 operations.
 * 1 <= id, t <= 106
 * All strings consist of uppercase and lowercase English letters, and digits.
 * 1 <= stationName.length <= 10
 * Answers within 10-5 of the actual value will be accepted as correct.
 */

/**
 * Your UndergroundSystem object will be instantiated and called as such:
 * UndergroundSystem obj = new UndergroundSystem();
 * obj.checkIn(id,stationName,t);
 * obj.checkOut(id,stationName,t);
 * double param_3 = obj.getAverageTime(startStation,endStation);
 */

import java.util.HashMap;
import java.util.Map;

/**
 * 这个问题当场一个实际design问题来考虑的话，那么每一个终端都是一个client，我们需要这几的是Service端的3个api.
 * 然后要意识到一下问题：
 * 1. Customer进去了，就一定要出来，才构成一个完整的trip.任何一个Customer都可能反复的进出站。
 * 所以不可能吧一个trip的start & end拆开来记录，否则会出现multi to multi的mapping，处理起来很麻烦.
 * 但是每一个trip在customer "check in"之后都是不完整的，只有等"check out"之后才是完整的。
 * 所以在这个trip的信息完整之前，需要把“check in”的信息buffer在某个地方，然后等“check out”之后再去找到原来Buffer的
 * 信息，把整个trip存储起来，同时清理掉之前Buffer的信息。
 * 2. 很明显的上面的Buffer会想到Hashmap，但是实际做Service，不肯能把大量的用户数据存在Heap里面，是一定要persist到storage
 * 里面的。最多加一层Cache，比如说Redis.
 * 3. 三个method里面，唯一需要read的就是getAverageTime，所以在实际存trip的时候除了简单的append(in real life needed)，
 * 在这个具体的问题里面，只需要把startStation & endStation做key，去把travel time存起来就好.
 * 如此在“getAverageTime”的时候就不用在做MapReduce了，相当于提前就把Map做好了。
 */
public class UndergroundSystem {
    Map<String, Integer> travelTimeMap;
    Map<String, Integer> travelTripMap;
    Map<Integer, String> checkInStatinMap;
    Map<Integer, Integer> checkInTimeMap;

    public UndergroundSystem() {
        this.travelTimeMap = new HashMap<>();
        this.travelTripMap = new HashMap<>();
        this.checkInStatinMap = new HashMap<>();
        this.checkInTimeMap = new HashMap<>();
    }

    public void checkIn(int id, String stationName, int t) {
        this.checkInStatinMap.put(id, stationName);
        this.checkInTimeMap.put(id, t);
    }

    public void checkOut(int id, String stationName, int t) {
        String checkInStation = this.checkInStatinMap.get(id);
        int checkInTime = this.checkInTimeMap.get(id);
        String key = checkInStation + "-" + stationName;
        int travelTime = t - checkInTime;
        int TotalTime = this.travelTimeMap.containsKey(key) ? this.travelTimeMap.get(key)+travelTime : travelTime;
        this.travelTimeMap.put(key, TotalTime);
        int cnt = this.travelTripMap.containsKey(key) ? this.travelTripMap.get(key)+1 : 1;
        this.travelTripMap.put(key, cnt);
        this.checkInStatinMap.remove(id);
    }

    public double getAverageTime(String startStation, String endStation) {
        String key  = startStation + "-" + endStation;
        double cnt = (double)this.travelTripMap.get(key);//注意这里要给换成double
        double totalTime = (double)this.travelTimeMap.get(key);//注意这里要给换成double
        return totalTime/cnt;
    }
}





















