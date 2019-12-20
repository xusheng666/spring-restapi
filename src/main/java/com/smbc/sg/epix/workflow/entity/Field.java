package com.smbc.sg.epix.workflow.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "G_WF_FIELD")
@Where(clause = "DELETE_FLAG=0")
@AllArgsConstructor
@NoArgsConstructor
public class Field extends BaseEntity {
  @Getter
  @Setter
  @Column(name = "FIELD_LABEL")
  private String fieldLabel;
  @Getter
  @Setter
  @Column(name = "FIELD_TYPE")
  private String fieldType;
  @Getter
  @Setter
  @Column(name = "FIELD_NAME")
  private String fieldName;
  @Getter
  @Setter
  @Column(name = "FIELD_SEQ")
  private Integer fieldSeq;
  @Getter
  @Setter
  @Column(name = "MANDATORY")
  private Boolean mandatory;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "WF_UID", nullable = false)
  @Getter
  @Setter
  private Workflow workflow;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "field")
  @Getter
  @Setter
  private List<FieldOption> fieldOptionList;
}
