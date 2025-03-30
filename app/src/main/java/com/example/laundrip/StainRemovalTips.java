package com.example.laundrip;

import java.util.List;

public class StainRemovalTips {
    public void setCommon_stains(String common_stains) {
        this.common_stains = common_stains;
    }

    public void setBest_removal_techniques(String best_removal_techniques) {
        this.best_removal_techniques = best_removal_techniques;
    }

    private String common_stains;
    private String best_removal_techniques;

    public String getBest_removal_techniques() {
        return best_removal_techniques;
    }

    public String getCommonStains() { return common_stains; }
}
