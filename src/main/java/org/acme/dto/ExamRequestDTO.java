package org.acme.dto;

import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;
import jakarta.ws.rs.core.MediaType;
import java.io.InputStream;
import java.time.LocalDateTime;

public class ExamRequestDTO {

    @RestForm
    private Long candidateId;

    @RestForm
    private Long examId;

    @RestForm
    private Double examScore;

    @RestForm
    private Double examGrade;

    @RestForm
    private String status;

    @RestForm
    private Float averageDiplomaDegree;

    @RestForm
    private Float subjectDegree;

    @RestForm
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private InputStream receipt;

    @RestForm
    private LocalDateTime requestDate = LocalDateTime.now();

    @RestForm
    private Float bal;

    // Getters and Setters
    public Long getCandidateId() { return candidateId; }
    public void setCandidateId(Long candidateId) { this.candidateId = candidateId; }

    public Long getExamId() { return examId; }
    public void setExamId(Long examId) { this.examId = examId; }

    public Double getExamScore() { return examScore; }
    public void setExamScore(Double examScore) { this.examScore = examScore; }

    public Double getExamGrade() { return examGrade; }
    public void setExamGrade(Double examGrade) { this.examGrade = examGrade; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public InputStream getReceipt() { return receipt; }
    public void setReceipt(InputStream receipt) { this.receipt = receipt; }

    public LocalDateTime getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDateTime requestDate) { this.requestDate = requestDate; }

    public Float getAverageDiplomaDegree() { return averageDiplomaDegree; }
    public void setAverageDiplomaDegree(Float averageDiplomaDegree) { this.averageDiplomaDegree = averageDiplomaDegree; }

    public Float getSubjectDegree() { return subjectDegree; }
    public void setSubjectDegree(Float subjectDegree) { this.subjectDegree = subjectDegree; }

    public Float getBal() { return bal; }
    public void setBal(Float bal) { this.bal = bal; }
}
