package com.smbc.sg.epix.workflow.service.impl;

import java.time.LocalDateTime;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import com.smbc.sg.epix.workflow.entity.BaseEntity;
import com.smbc.sg.epix.workflow.search.CustomRsqlVisitor;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;

public class BaseService<T> {
  private static final Logger logger = LoggerFactory.getLogger(BaseService.class);


  /**
   * Parse the search criteria filter and put search criteria into JPA Specification object.
   * 
   * @param filter
   */
  protected Specification<T> parseSearchCriteria(String filter) {

    Specification<T> spec = null;

    if (Objects.nonNull(filter) && StringUtils.isNotBlank(filter)) {
      Node rootNode = new RSQLParser().parse(filter);
      spec = rootNode.accept(new CustomRsqlVisitor<T>());
    }

    return spec;
  }

  protected void setCreatedUpdatedByDate(BaseEntity baseEntity, String userId) {
    if (Objects.nonNull(baseEntity) && Objects.nonNull(userId)) {
      baseEntity.setCreatedBy(userId);
      baseEntity.setCreatedDate(LocalDateTime.now());
      setUpdatedByDate(baseEntity, userId);
    }
  }

  protected void setUpdatedByDate(BaseEntity baseEntity, String userId) {
    if (Objects.nonNull(baseEntity) && Objects.nonNull(userId)) {
      baseEntity.setUpdatedBy(userId);
      baseEntity.setUpdatedDate(LocalDateTime.now());
    }
  }
}
