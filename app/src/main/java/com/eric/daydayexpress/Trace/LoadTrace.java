package com.eric.daydayexpress.Trace;

public class LoadTrace {


    private String traceInfo;
    private String time;

    public LoadTrace(String traceInfo,String time){
        this.traceInfo = traceInfo;
        this.time = time;
    }

    public String getTraceInfo() {
        return traceInfo;
    }

    public String getTime() {
        return time;
    }
}
