package com.eric.daydayexpress.Gson;

import android.widget.ListView;

import java.util.List;

public class logistics {
    private String LogisticCode ;
    private String ShipperCode;
    private List<Trace> Traces;
    private String State;
    private String EBusinessID;
    private String Success;
    public void setLogisticCode(String LogisticCode) {
        this.LogisticCode = LogisticCode;
    }
    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setShipperCode(String ShipperCode) {
        this.ShipperCode = ShipperCode;
    }
    public String getShipperCode() {
        return ShipperCode;
    }

    public void setTraces(List<Trace> Traces) {
        this.Traces= Traces;
    }
    public List<Trace> getTraces() {
        return Traces;
    }

    public void setState(String State) {
        this.State = State;
    }
    public String getState() {
        return State;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }
    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setSuccess(String Success) {
        this.Success = Success;
    }
    public String getSuccess() {
        return Success;
    }


}
