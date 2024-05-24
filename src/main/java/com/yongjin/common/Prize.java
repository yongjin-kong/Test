package com.yongjin.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Prize implements Serializable {
    private int id;
    private String name;
    private int quantity;
    private double winningProbability;

    @JsonCreator
    public Prize(@JsonProperty("id") int id,
                 @JsonProperty("name") String name,
                 @JsonProperty("quantity") int quantity,
                 @JsonProperty("winningProbability") double winningProbability) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.winningProbability = winningProbability;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getWinningProbability() {
        return winningProbability;
    }

    public void setWinningProbability(double winningProbability) {
        this.winningProbability = winningProbability;
    }
}
