package com.tridiots.cms.models;

public class Grade {
	private int submissionId;
	private int judgeId;
	private Double submissionGrade;
	
	public int getSubmissionId() {
		return submissionId;
	}
	public void setSubmissionId(int submissionId) {
		this.submissionId = submissionId;
	}
	public int getJudgeId() {
		return judgeId;
	}
	public void setJudgeId(int judgeId) {
		this.judgeId = judgeId;
	}
	public Double getSubmissionGrade() {
		return submissionGrade;
	}
	public void setSubmissionGrade(Double submissionGrade) {
		this.submissionGrade = submissionGrade;
	}
	
	

}
