package com.example.demo.entity;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column
    private int cardId;

    @Column(name = "type")
    private int type;

    @Column(name="belong")
    private String belong;

    @Column(name="origin")
    private String origin;

    @Column(name = "origin_url")
    private String originUrl;

    @Column(name="origin_type")
    private String originType;

    @Column(name = "name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "standard_price")
    private BigDecimal standardPrice;

    @Column(name="stock")
    private int stock;

    @Column(name="date_created",updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date date_created;

    @Column(name = "date_modified",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date date_modified;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(BigDecimal standardPrice) {
        this.standardPrice = standardPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public Date getDate_modified() {
        return date_modified;
    }

    public void setDate_modified(Date date_modified) {
        this.date_modified = date_modified;
    }

    public String getOriginType() {
        return originType;
    }

    public void setOriginType(String originType) {
        this.originType = originType;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardId=" + cardId +
                ", type=" + type +
                ", belong='" + belong + '\'' +
                ", origin='" + origin + '\'' +
                ", originUrl='" + originUrl + '\'' +
                ", originType='" + originType + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", standardPrice=" + standardPrice +
                ", stock=" + stock +
                ", date_created=" + date_created +
                ", date_modified=" + date_modified +
                '}';
    }
}
