package com.tridiots.cms.models;

public class Contestant extends User{
    private int contestantId;
    private int userId;
    private String contestantImageDir;
    private boolean contestantVerified;

    public int getContestantId() {
        return contestantId;
    }

    public void setContestantId(int contestantId) {
        this.contestantId = contestantId;
    }
    
    public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getContestantImageDir() {
        return contestantImageDir;
    }

    public void setContestantImageDir(String contestantImageDir) {
        this.contestantImageDir = contestantImageDir;
    }

	public boolean isContestantVerified() {
		return contestantVerified;
	}

	public void setContestantVerified(boolean contestantVerified) {
		this.contestantVerified = contestantVerified;
	}
    
    
}
