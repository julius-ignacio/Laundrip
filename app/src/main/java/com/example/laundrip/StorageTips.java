package com.example.laundrip;

public class StorageTips {
    public void setFold_or_hang(String fold_or_hang) {
        this.fold_or_hang = fold_or_hang;
    }

    public void setSpecial_storage_tips(String special_storage_tips) {
        this.special_storage_tips = special_storage_tips;
    }

    private String fold_or_hang;


    private String special_storage_tips;

    public String getFoldOrHang() { return fold_or_hang; }

    public String getSpecial_storage_tips() {
        return special_storage_tips;
    }

}
