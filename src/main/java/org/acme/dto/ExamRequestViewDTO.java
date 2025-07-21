package org.acme.dto;

import org.acme.entity.ExamRequest;

public class ExamRequestViewDTO {
    private Long id;
    private String candidateFullName;
    private String specialty;
    private String examName;
    private Double examScore;
    private Double examGrade;
    private String status;
    private byte[] receipt;

    public ExamRequestViewDTO(ExamRequest er) {
        this.id = er.getId();
        
        if (er.getCandidate() != null) {
            this.candidateFullName = String.join(" ",
                er.getCandidate().getName(),
                er.getCandidate().getSurname(),
                er.getCandidate().getLastName()
            );
        } else {
            this.candidateFullName = "Unknown";
        }

        if (er.getExam() != null) {
            this.specialty = er.getExam().getSpecialty();
            this.examName = er.getExam().getSubject();
        }

        this.examScore = er.getExamScore();
        this.examGrade = er.getExamGrade();
        this.status = er.getStatus() != null ? er.getStatus().name() : "Unknown"    ;
        this.receipt = er.getReceipt() != null ? er.getReceipt() : new byte[0];
    }

    public Long getId() { return id; }
    public String getCandidateFullName() { return candidateFullName; }
    public String getSpecialty() { return specialty; }
    public String getExamName() { return examName; }
    public Double getExamScore() { return examScore; }
    public Double getExamGrade() { return examGrade; }
    public String getStatus() { return status; }
    public byte[] getReceipt() { return receipt; }
}
