package com.mainsys.challenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mainsys.challenge.model.user.User;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "sales")
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String productName;
    private double productProfit;
    private Date date;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    public Sales(String productName, double productProfit, Date date, User user) {
        this.productName = productName;
        this.productProfit = productProfit;
        this.date = date;
        this.user = user;
    }

    public Sales() {

    }

    public long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductProfit() {
        return productProfit;
    }

    public void setProductProfit(long productProfit) {
        this.productProfit = productProfit;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<String, Object> getAllParamsToCheck() {
        Map<String, Object> paramsToCheck = new HashMap<>();
        paramsToCheck.put("Id", getId());
        paramsToCheck.put("ProductName", getProductName());
        paramsToCheck.put("ProductProfit", getProductProfit());
        paramsToCheck.put("Date", getDate());
        paramsToCheck.put("User", getUser());

        return paramsToCheck;
    }
}
