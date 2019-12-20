package com.smbc.sg.epix.workflow.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "G_WF_STEP")
@Where(clause = "DELETE_FLAG=0")
@AllArgsConstructor
@NoArgsConstructor
public class Step extends BaseEntity {
  @Getter
  @Setter
  @Column(name = "CURRENT_OWNER")
  private String currentOwner;
  @Getter
  @Setter
  @Column(name = "OWNER_GROUP")
  private String ownerGroup;
  @Getter
  @Setter
  @Column(name = "STEP_NAME")
  private String stepName;
  @Getter
  @Setter
  @Column(name = "TIME_LIMIT_DAYS")
  private Integer timeLimitDays;
  @Getter
  @Setter
  @Column(name = "STEP_SEQ")
  private Integer stepSeq;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "WF_UID", nullable = false)
  @Getter
  @Setter
  private Workflow workflow;
}
