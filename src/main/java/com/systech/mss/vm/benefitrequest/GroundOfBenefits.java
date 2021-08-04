package com.systech.mss.vm.benefitrequest;

import com.systech.mss.util.Ignore;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GroundOfBenefits {
    long id;
    long reasonId;
    String reason;

    @Ignore
    boolean requiresDocuments;
}
