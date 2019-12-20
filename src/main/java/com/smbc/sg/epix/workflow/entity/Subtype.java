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

@Entity(name = "G_WF_SUBTYPE")
@Where(clause = "DELETE_FLAG=0")
@AllArgsConstructor
@NoArgsConstructor
public class Subtype extends BaseEntity {
  @Getter
  @Setter
  @Column(name = "LABEL")
  private String label;
  @Getter
  @Setter
  @Column(name = "FIELD_VALUE")
  private String fieldValue;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "WF_UID", nullable = false)
  @Getter
  @Setter
  private Workflow workflow;
}
