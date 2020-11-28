package dsdbahw2.model;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;
import java.util.Date;

public class Log implements Serializable{
    @QuerySqlField
    private Integer id;
    @QuerySqlField
    private Integer key;
    @QuerySqlField
    private Date time;
    @QuerySqlField
    private Integer value;

    public Integer getId() {return id;}

    public Integer getKey() {
        return key;
    }

    public Date getTime() {
        return time;
    }

    public Integer getValue() {
        return value;
    }

    public void setId(Integer id) { this.id = id; }

    public void setKey(Integer key) {
        this.key = key;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}