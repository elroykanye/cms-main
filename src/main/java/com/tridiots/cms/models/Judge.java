package com.tridiots.cms.models;

public class Judge extends User {
    private int judgeId;
    private int judgeLevel;

    public int getJudgeId() {
        return judgeId;
    }

    public void setJudgeId(int judgeId) {
        this.judgeId = judgeId;
    }

    public int getJudgeLevel() {
        return judgeLevel;
    }

    public void setJudgeLevel(int judgeLevel) {
        this.judgeLevel = judgeLevel;
    }
}
