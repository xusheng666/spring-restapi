package com.smbc.sg.epix.workflow.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.smbc.sg.epix.workflow.entity.Field;

public interface FieldRepository
    extends JpaRepository<Field, Long>, JpaSpecificationExecutor<Field> {
}
