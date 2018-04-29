package com.liezh.domain.dto.recipe;

/**
 * Created by Administrator on 2018/2/24.
 */
public class MaterialDto {

    private String name;

    private String unit;

    private Float amount;

    public MaterialDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }
}
