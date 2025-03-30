package com.example.laundrip;

public class WashingInstructions {


    private String machine_washable;

    public void setMachine_washable(String machine_washable) {
        this.machine_washable = machine_washable;
    }

    public void setWater_temperature(String water_temperature) {
        this.water_temperature = water_temperature;
    }

    public void setCycle_type(String cycle_type) {
        this.cycle_type = cycle_type;
    }

    public void setRecommended_detergent(String recommended_detergent) {
        this.recommended_detergent = recommended_detergent;
    }

    public void setUse_fabric_softener(String use_fabric_softener) {
        this.use_fabric_softener = use_fabric_softener;
    }

    public void setCan_be_bleached(String can_be_bleached) {
        this.can_be_bleached = can_be_bleached;
    }

    private String water_temperature;
    private String cycle_type;
    private String recommended_detergent;
    private String use_fabric_softener;
    private String can_be_bleached;


    public String getMachine_washable() {
        return machine_washable;
    }

    public String getCan_be_bleached() {
        return can_be_bleached;
    }

    public String getUse_fabric_softener() {
        return use_fabric_softener;
    }

    public String getWaterTemperature() { return water_temperature; }
    public String getCycleType() { return cycle_type; }
    public String getRecommendedDetergent() { return recommended_detergent; }
}
