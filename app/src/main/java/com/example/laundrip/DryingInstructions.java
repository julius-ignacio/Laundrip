package com.example.laundrip;

public class DryingInstructions {

    public void setAir_dry_or_machine_dry(String air_dry_or_machine_dry) {
        this.air_dry_or_machine_dry = air_dry_or_machine_dry;
    }

    public void setHeat_settings(String heat_settings) {
        this.heat_settings = heat_settings;
    }

    public void setSpecial_handling(String special_handling) {
        this.special_handling = special_handling;
    }

    private String air_dry_or_machine_dry;
    private String heat_settings;
    private String special_handling;

    public String getHeatSettings() { return heat_settings; }
    public String getSpecialHandling() { return special_handling; }

    public String getAir_dry_or_machine_dry() {
        return air_dry_or_machine_dry;
    }

}
