
package com.systech.mss.controller.vm;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.systech.mss.util.Ignore;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BIllsEtlVM {

    @Ignore
    private InvalidImports invalidImports;
    @Ignore
    private Boolean success;
    @Ignore
    private ValidImports validImports;

}
