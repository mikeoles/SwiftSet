package com.michaeloles.swiftset;

public class YoutubeData{
    public int startTimeMillis = 0;
    public String videoCode = "";

    YoutubeData(String vc, int st){
        this.videoCode = vc;
        this.startTimeMillis = st;
    }
}
