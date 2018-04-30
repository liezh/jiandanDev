package com.liezh.domain.entity;

/**
 * Created by Administrator on 2018/4/30.
 */
public class Heat {

    private Long id;

    private String name;

    private Float energy;

    private String unit;

    public Heat() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getEnergy() {
        return energy;
    }

    public void setEnergy(Float energy) {
        this.energy = energy;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
