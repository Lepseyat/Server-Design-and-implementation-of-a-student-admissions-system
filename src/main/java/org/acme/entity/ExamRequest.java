package org.acme.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDateTime;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType; 
import jakarta.persistence.Table;
import org.jboss.resteasy.reactive.PartType;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.ws.rs.core.MediaType; 

@Entity
@Table(name = "exam_requests")
public class ExamRequest extends PanacheEntityBase{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "id")
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "exam_id", referencedColumnName = "id")
    private Exam exam;

    @Column(name = "exam_score", nullable = false)
    private Double examScore = 0.0;

    @Column(name = "exam_grade", nullable = false)
    private Double examGrade = 0.0;

    @Column(name = "request_date")
    private LocalDateTime requestDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ExamStatus status;

    @Column(name = "receipt")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    private byte[] receipt;

    @Column(name = "average_diploma_degree")
    private Float averageDiplomaDegree;

    @Column(name = "subject_degree")
    private Float subjectDegree;

    @Column(name = "bal")
    private Float bal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Candidate getCandidate() { 
        return candidate;
    }

    public void setCandidate(Candidate candidate) { 
        this.candidate = candidate;
    }

    public Double getExamScore() {
        return examScore;
    }

    public void setExamScore(Double examScore) {
        this.examScore = examScore;
    }


    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public Double getExamGrade() {
        return examGrade;
    }

    public void setExamGrade(Double examGrade) {
        this.examGrade = examGrade;
    }
    
    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public Exam getExam() { 
        return exam;
    }

    public void setExam(Exam exam) { 
        this.exam = exam;
    }

    public ExamStatus getStatus() {
        return status;
    }

    public void setStatus(ExamStatus status) {
        this.status = status;
    }

    public byte[] getReceipt() {
        return receipt;
    }

    public void setReceipt(byte[] receipt) {
        this.receipt = receipt;
    }

    public Float getAverageDiplomaDegree() {
    return averageDiplomaDegree;
    }

    public void setAverageDiplomaDegree(Float averageDiplomaDegree) {
        this.averageDiplomaDegree = averageDiplomaDegree;
    }

    public Float getSubjectDegree() {
        return subjectDegree;
    }

    public void setSubjectDegree(Float subjectDegree) {
        this.subjectDegree = subjectDegree;
    }
    
    public Float getBal() {
    return bal;
    }
    
    public void setBal(Float bal) {
        this.bal = bal;
    }
}