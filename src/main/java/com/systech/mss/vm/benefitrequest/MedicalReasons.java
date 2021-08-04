package com.systech.mss.vm.benefitrequest;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MedicalReasons {
    long id;
    private String medicalIssue;
    private String medicalExplanation;
}
