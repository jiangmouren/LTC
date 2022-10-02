package com.mycompany.app.companies.noom;
import java.util.*;
/**
 * Noom面经：https://www.1point3acres.com/bbs/thread-882463-1-1.html
 * coding 数据OOD那种，题目特别长。大概意思是一个track 运动路线的功能，用户有4种request：start, stop, pause, resume. 意思分别是
 * start: 表示运动开始
 * stop: 表示运动结束
 * pause: 运动并没有结束，只是暂停，所以pause和resume之前的时间和mile都不能算。每一对start和stop之间可以有多个pause/resume
 * resume: 一旦resume表示又要开始计算时间的mile
 * 可以assume 已经有一个gps 的service可以一直告诉你的coordinate
 * 问题：如何设计data model，当用户按了stop之后怎么计算每1Mile运动花了多长时间
 */
public class GpsTrackingDesign {
    class Location{
        double longitude;
        double latitude;
        public Location(double longitude, double latitude){
            this.longitude = longitude;
            this.latitude = latitude;
        }
    }
    class Packet{
        Location location;
        Long timeStamp;
        //0: stopped, 1: running, 2: paused
        int status;
        public Packet(Location location, Long timestamp, int status){
            this.location = location;
            this.timeStamp = timeStamp;
            this.status = status;
        }
    }
    //we only store packets with running and paused flag, avoid storing too much data.
    //Better to have the app stop sending locations once stopped.
    private List<Packet> packets;
    private double distance;
    private long duration;
    public GpsTrackingDesign(){
        this.packets = new ArrayList<>();
    }

    private double calculateDistance(Location point0, Location point1){
        //TODO
        return 0;
    }

    public void addPacket(Packet packet){
        if(packet.status!=2){
            double deltaDistance = calculateDistance(this.packets.get(packets.size()-1).location, packet.location);
            this.distance += deltaDistance;
            long deltaDuration = packet.timeStamp - this.packets.get(packets.size()-1).timeStamp;
            this.duration += deltaDuration;
            this.packets.add(packet);
        }
    }

    //might need to allow unit as a parameter
    public double getDistance(){
        return this.distance;
    }

    public long getDuration(){
        return this.duration;
    }

    public double getSpeed(){
        return this.distance / (double)this.duration;
    }
}
