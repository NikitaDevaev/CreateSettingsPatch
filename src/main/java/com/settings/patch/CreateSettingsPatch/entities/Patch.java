package com.settings.patch.CreateSettingsPatch.entities;

public class Patch {
    private String mnemonicName; //example: NESS
    private String numberPatch; // example: 144
    private String fsd; // http:/...
    private String task; // http:/..
    private String brd; // http:/..

    public Patch() {
    }

    public Patch(String mnemonicName, String numberPatch, String fsd, String task, String brd) {
        this.mnemonicName = mnemonicName;
        this.numberPatch = numberPatch;
        this.fsd = fsd;
        this.task = task;
        this.brd = brd;
    }


    private class Recreate{
        public Recreate(Boolean recreate, String level) {
            this.recreate = recreate;
            this.level = level;
        }

        private Boolean recreate;
        private String level;

        public Boolean getRecreate() {
            return recreate;
        }

        public void setRecreate(Boolean recreate) {
            this.recreate = recreate;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }
    }
    public String getMnemonicName() {
        return mnemonicName;
    }

    public void setMnemonicName(String mnemonicName) {
        this.mnemonicName = mnemonicName;
    }

    public String getNumberPatch() {
        return numberPatch;
    }

    public void setNumberPatch(String numberPatch) {
        this.numberPatch = numberPatch;
    }

    public String getFsd() {
        return fsd;
    }

    public void setFsd(String fsd) {
        this.fsd = fsd;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getBrd() {
        return brd;
    }

    public void setBrd(String brd) {
        this.brd = brd;
    }
}
