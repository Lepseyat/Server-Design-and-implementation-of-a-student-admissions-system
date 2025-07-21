package org.acme.dto;

public class RankingDTO {
    
    private String fullName;
    private Double examGrade;
    private Float averageDiplomaDegree;
    private Float subjectDegree;
    private Float bal;

     public RankingDTO(String fullName, Double examGrade, Float averageDiplomaDegree, Float subjectDegree, Float bal) {
        this.fullName = fullName;
        this.examGrade = examGrade;
        this.averageDiplomaDegree = averageDiplomaDegree;
        this.subjectDegree = subjectDegree;
        this.bal = bal;
    }

    public String getFullName() {
        return fullName;
    }

    public Double getExamGrade() {
        return examGrade;
    }

    public Float getAverageDiplomaDegree() {
        return averageDiplomaDegree;
    }

    public Float getSubjectDegree() {
        return subjectDegree;
    }

    public Float getBal() {
        return bal;
    }
}

