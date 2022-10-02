package com.mycompany.app.companies.noom;

import java.util.TreeMap;

/**
 * Noom 面经（From https://www.1point3acres.com/bbs/thread-848331-1-1.html）：
 * 题目是 Responsibility Allocation.
 * 给定一个coach的基本responsibility allocation 和 一些不同时间段的temporary responsibility alloation override,
 * 计算一个给定时间段内coach的final responsibility allocation.
 *
 * global baseline responsibility allocation: (A: a%, B: b%, C: c%)
 * 每一个 temporary allocation override 会覆盖一段时间段： (start_date, end_date, (A: a%, B: b%, C: c%) )
 * 有override的date 按照override 计算， 没有override的date 按照baseline 计算。override 不会 overlap。
 * 给定时间段 start date, end date. 计算这个时间段内的average responsibility allocation: (A: a%, B: b%, C: c%)
 * 算法很简单，就是把list of overrides 过一遍，计算sum of percentages 再 average 就可以了。
 * Tricky 的地方在于：
 * 要validate input
 * 要检查 override 的date range 和 output date range 的区间关系
 */

public class CoachResponsibility {
    class Assignment{
        float percentageA;
        float percentageB;
        float percentageC;
        long startDate;
        long endDate;
        //Assume inputs checked outside
        public Assignment(float percentageA, float percentageB, float percentageC, long startDate, long endDate){
            this.percentageA = percentageA;
            this.percentageB = percentageB;
            this.percentageC = percentageC;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }
    float baseA;
    float baseB;
    float baseC;
    TreeMap<Long, Assignment> map;
    public CoachResponsibility(float percentageA, float percentageB, float percentageC){
        checkPercentage(percentageA, percentageB, percentageC);
        this.baseA = percentageA;
        this.baseB = percentageB;
        this.baseC = percentageC;
        this.map = new TreeMap<>();
    }

    //Integer is not enough to represent the current or future date
    //return true if successfully added override, otherwise return false
    public boolean overRide(long startDate, long endDate, float percentageA, float percentageB, float percentageC){
        checkPercentage(percentageA, percentageB, percentageC);
        if(startDate>endDate){
            throw new IllegalArgumentException("startDate cannot be later than endDate");
        }
        //check if overRide overlaps with any former overRides
        //for now, just assume not overlap allowed
        Long pre = this.map.floorKey(startDate);
        Long next = this.map.ceilingKey(startDate);
        if(pre!=null && this.map.get(pre).endDate>=startDate || next!=null && this.map.get(next).startDate<=endDate){
            return false;
        }
        Assignment overRide = new Assignment(percentageA, percentageB, percentageC, startDate, endDate);
        this.map.put(startDate, overRide);
        return true;
    }

    public Assignment getReport(long startDate, long endDate){
        long dayCount = endDate - startDate;
        float sumA = 0;
        float sumB = 0;
        float sumC = 0;
        for(long i=startDate; i<=endDate; i++){
            sumA += getValue(i)[0];
            sumB += getValue(i)[1];
            sumC += getValue(i)[2];
        }
        float percentageA = sumA / (float)dayCount;
        float percentageB = sumB / (float)dayCount;
        float percentageC = sumC / (float)dayCount;
        return new Assignment(percentageA, percentageB, percentageC, startDate, endDate);
    }

    private float[] getValue(long date){
        Long pre = this.map.floorKey(date);
        Long next = this.map.ceilingKey(date);
        float[] res = {this.baseA, this.baseB, this.baseC};
        if(pre!=null && this.map.get(pre).endDate>=date){
            res[0] = this.map.get(pre).percentageA;
            res[1] = this.map.get(pre).percentageB;
            res[2] = this.map.get(pre).percentageC;
            return res;
        }
        if(next!=null && next==date){
            res[0] = this.map.get(next).percentageA;
            res[1] = this.map.get(next).percentageB;
            res[2] = this.map.get(next).percentageC;
            return res;
        }
        return res;
    }

    private void checkPercentage(float percentageA, float percentageB, float percentageC){
        if(percentageA<0 || percentageB<0 || percentageC<0){
            throw new IllegalArgumentException("Percentage cannot be negative values");
        }
        //less or equal to 1 is valid
        if(percentageA+percentageB+percentageC>1){
            throw new IllegalArgumentException("Total percentage cannot exceed 100%");
        }
    }
}
