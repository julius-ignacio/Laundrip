package com.example.laundrip;

public class WashingInstructions {
    private boolean machine_washable;
    private String water_temperature;
    private String cycle_type;
    private String recommended_detergent;
    private boolean use_fabric_softener;
    private boolean can_be_bleached;

    public boolean isMachineWashable() {
        return machine_washable;
    }

    public String getWaterTemperature() {
        return water_temperature;
    }

    public String getCycleType() {
        return cycle_type;
    }

    public String getRecommendedDetergent() {
        return recommended_detergent;
    }

    public boolean isUseFabricSoftener() {
        return use_fabric_softener;
    }

    public boolean isCanBeBleached() {
        return can_be_bleached;
    }
}
