package com.example.demo.entity;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "view_log")
public class ViewLog {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    //ip
    @Column(name = "ip")
    private String ip;

    //ip 来源
    @Column(name = "origin")
    private String origin;

    @Column(name = "device")
    private String device;

    //viewer 其他信息
    @Column(name="additional")
    private String additional;

    //访问时间
    @Column(name="date_view")
    private Date dateView;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public Date getDateView() {
        return dateView;
    }

    public void setDateView(Date dateView) {
        this.dateView = dateView;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
