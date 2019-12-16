package com.smbc.sg.epix.workflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="G_WF_WORKFLOW")
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Workflow extends BaseEntity{
    @Getter @Setter @Column(name="WF_CODE")private String wfCode;
    @Getter @Setter @Column(name="WF_NAME")private String wfName;
    @Getter @Setter @Column(name="EXPIRY_IN_DAYS")private Integer expiryInDays;
    @Getter @Setter @Column(name="EXPIRY_ACTION")private String expiryAction;
    @Getter @Setter @Column(name="CLOSE_IN_DAYS")private Integer closeInDays;
    @Getter @Setter @Column(name="WF_RECONFIG")private Boolean wfReconfig;
    @Getter @Setter @Column(name="BRANCH_ENABLED") private Boolean branchEnabled;
    @Getter @Setter @Column(name="VERSION")private Integer version;
}
