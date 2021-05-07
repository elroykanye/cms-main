package com.tridiots.cms.models;

public class Contestant extends User{
    private int contestantId;
    private String contestantImageDir;

    public int getContestantId() {
        return contestantId;
    }

    public void setContestantId(int contestantId) {
        this.contestantId = contestantId;
    }

    public String getContestantImageDir() {
        return contestantImageDir;
    }

    public void setContestantImageDir(String contestantImageDir) {
        this.contestantImageDir = contestantImageDir;
    }
}
