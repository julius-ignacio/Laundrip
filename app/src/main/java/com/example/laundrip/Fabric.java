package com.example.laundrip;

    public class Fabric {
        public void setName(String name) {
            this.name = name;
        }

        public void setWashing_instructions(WashingInstructions washing_instructions) {
            this.washing_instructions = washing_instructions;
        }

        public void setDrying_instructions(DryingInstructions drying_instructions) {
            this.drying_instructions = drying_instructions;
        }

        public void setIroning_maintenance(IroningMaintenance ironing_maintenance) {
            this.ironing_maintenance = ironing_maintenance;
        }

        public void setStain_removal_tips(StainRemovalTips stain_removal_tips) {
            this.stain_removal_tips = stain_removal_tips;
        }

        public void setStorage_tips(StorageTips storage_tips) {
            this.storage_tips = storage_tips;
        }

        private String name;
        private WashingInstructions washing_instructions;
        private DryingInstructions drying_instructions;
        private IroningMaintenance ironing_maintenance;
        private StainRemovalTips stain_removal_tips;
        private StorageTips storage_tips;


        public String getName() {
            return name;
        }

        public WashingInstructions getWashingInstructions() {
            return washing_instructions;
        }

        public DryingInstructions getDryingInstructions() {
            return drying_instructions;
        }

        public IroningMaintenance getIroningMaintenance() {
            return ironing_maintenance;
        }

        public StainRemovalTips getStainRemovalTips() {
            return stain_removal_tips;
        }

        public StorageTips getStorageTips() {
            return storage_tips;
        }

}
