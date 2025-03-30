package com.example.laundrip;

public class IroningMaintenance {
    public void setIron_temperature(String iron_temperature) {
        this.iron_temperature = iron_temperature;
    }

    public void setSteam(String steam) {
        this.steam = steam;
    }

    public void setPrevent_shrinking_fading(String prevent_shrinking_fading) {
        this.prevent_shrinking_fading = prevent_shrinking_fading;
    }

    private String iron_temperature;
    private String steam;

    public String getPrevent_shrinking_fading() {
        return prevent_shrinking_fading;
    }

    public String getSteam() {
        return steam;
    }

    private String prevent_shrinking_fading;

    public String getIronTemperature() { return iron_temperature; }
}
