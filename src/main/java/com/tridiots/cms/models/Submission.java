package com.tridiots.cms.models;

import java.sql.Date;

public class Submission {
	private int submissionId;
	private Date submissionDate;
	private String submissionFileEn;
	private String submissionFileKom;
	private int contestantId;
	private double submissionFinalGrade;
	public int getSubmissionId() {
		return submissionId;
	}
	public void setSubmissionId(int submissionId) {
		this.submissionId = submissionId;
	}
	public Date getSubmissionDate() {
		return submissionDate;
	}
	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}
	public String getSubmissionFileEn() {
		return submissionFileEn;
	}
	public void setSubmissionFileEn(String submissionFileEn) {
		this.submissionFileEn = submissionFileEn;
	}
	public String getSubmissionFileKom() {
		return submissionFileKom;
	}
	public void setSubmissionFileKom(String submissionFileKom) {
		this.submissionFileKom = submissionFileKom;
	}
	public int getContestantId() {
		return contestantId;
	}
	public void setContestantId(int contestantId) {
		this.contestantId = contestantId;
	}
	public double getSubmissionFinalGrade() {
		return submissionFinalGrade;
	}
	public void setSubmissionFinalGrade(double submissionFinalGrade) {
		this.submissionFinalGrade = submissionFinalGrade;
	}
	
	
}
