package org.acme.entity;

public class Ranking {
    
    private String candidateName;
    private Float examGrade;
    private Float averageDiplomaDegree;
    private Float subjectDegree;
    private Float totalScore;

    public Ranking(String candidateName, Float examGrade, Float averageDiplomaDegree, Float subjectDegree, Float totalScore) {
        this.candidateName = candidateName;
        this.examGrade = examGrade;
        this.averageDiplomaDegree = averageDiplomaDegree;
        this.subjectDegree = subjectDegree;
        this.totalScore = totalScore;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public Float getExamGrade() {
        return examGrade;
    }

    public Float getAverageDiplomaDegree() {
        return averageDiplomaDegree;
    }

    public Float getSubjectDegree() {
        return subjectDegree;
    }

    public Float getTotalScore() {
        return totalScore;
    }
}
