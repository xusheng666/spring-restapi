package com.smbc.sg.epix.workflow.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class WorkflowDTO {
    @Getter @Setter private Long uid;
    @Getter @Setter private String wfCode;
    @Getter @Setter private String wfName;
    @Getter @Setter private Integer expiryInDays;
    @Getter @Setter private String expiryAction;
    @Getter @Setter private Integer closeInDays;
    @Getter @Setter private Boolean wfReconfig;
    @Getter @Setter private Boolean branchEnabled;
    @Getter @Setter private Integer version;
    @Getter @Setter private String createdBy;
    @Getter @Setter private LocalDate createdDate;
    @Getter @Setter private String updatedBy;
    @Getter @Setter private LocalDate updatedDate;
    @Getter @Setter private List<SubtypeDTO> stDTOList;
}
