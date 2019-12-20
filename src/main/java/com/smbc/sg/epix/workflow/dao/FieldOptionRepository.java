package com.smbc.sg.epix.workflow.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.smbc.sg.epix.workflow.entity.FieldOption;

public interface FieldOptionRepository
    extends JpaRepository<FieldOption, Long>, JpaSpecificationExecutor<FieldOption> {
}
