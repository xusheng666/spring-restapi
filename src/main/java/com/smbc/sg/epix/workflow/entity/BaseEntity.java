package com.smbc.sg.epix.workflow.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Setter
  @Column(name = "UID")
  private Long uid;
  @Getter
  @Setter
  @Column(name = "CREATED_BY")
  private String createdBy;
  @Getter
  @Setter
  @Column(name = "CREATED_DATE")
  private LocalDateTime createdDate;
  @Getter
  @Setter
  @Column(name = "UPDATED_BY")
  private String updatedBy;
  @Getter
  @Setter
  @Column(name = "UPDATED_DATE")
  private LocalDateTime updatedDate;
  @Getter
  @Setter
  @Column(name = "DELETE_FLAG")
  private Boolean deleteFlag = false;

}
