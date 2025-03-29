package com.example.laundrip;

    public class Fabric {
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
