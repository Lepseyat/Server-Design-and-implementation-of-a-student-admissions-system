package org.acme.repository;

import org.acme.entity.Exam;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExamRepository implements PanacheRepositoryBase<Exam, Long> {
}

