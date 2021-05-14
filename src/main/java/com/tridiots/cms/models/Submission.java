package com.tridiots.cms.models;

import java.sql.Date;

public class Submission {
	private int submissionId;
	private Date submissionDate;
	private String submissionPoemTitle;
	private String submissionPoemEn;
	private String submissionPoemKom;
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
	public String getSubmissionPoemTitle() {
		return submissionPoemTitle.equals(null) ? submissionPoemTitle: "My Poem";
	}
	public void setSubmissionPoemTitle(String submissionPoemTitle) {
		this.submissionPoemTitle = submissionPoemTitle;
	}
	public String getSubmissionPoemEn() {
		return submissionPoemEn;
	}
	public void setSubmissionPoemEn(String submissionFileEn) {
		this.submissionPoemEn = submissionFileEn;
	}
	public String getSubmissionPoemKom() {
		return submissionPoemKom;
	}
	public void setSubmissionPoemKom(String submissionFileKom) {
		this.submissionPoemKom = submissionFileKom;
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
