package dsdbahw2.model;

import dsdbahw2.compute.Scale;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class ComputeLog implements Serializable {

    private Integer key;
    private Date time;
    private Integer value;

    public ComputeLog (Integer key,Date time,Integer value){
        this.key = key;
        this.time = time;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public Date getTime() {
        return time;
    }

    public Integer getValue() {
        return value;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void scaleTimeStamp(String scale){
        Scale util = new Scale(scale);
        Date date = new Timestamp(util.getNewTime(this.time.getTime()));
        this.time = date;
    }
}