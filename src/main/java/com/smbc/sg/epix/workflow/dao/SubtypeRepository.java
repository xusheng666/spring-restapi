package com.smbc.sg.epix.workflow.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.smbc.sg.epix.workflow.entity.Subtype;

public interface SubtypeRepository
    extends JpaRepository<Subtype, Long>, JpaSpecificationExecutor<Subtype> {
}
