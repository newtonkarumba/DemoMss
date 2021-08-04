package com.systech.mss.vm.benefitrequest;

//import com.systech.mss.domain.YesNo;
//<<<<<<< HEAD
////import jdk.nashorn.internal.ir.annotations.Ignore;
//=======
//import com.systech.mss.util.Ignore;
//>>>>>>> d357e6b54d630297c9bb6af39c448de4d2130095
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BankDetailsVM {
    long id;  //record id
    String bankName;
    String bankBranch;
    String accountName;
    String accountNumber;

//    @Ignore
//    private YesNo submitted=YesNo.YES;
}
