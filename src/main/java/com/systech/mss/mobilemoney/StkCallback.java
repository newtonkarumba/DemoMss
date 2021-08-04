
package com.systech.mss.mobilemoney;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;
import com.systech.mss.util.Ignore;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StkCallback {


    @Ignore
    private CallbackMetadata callbackMetadata;

    private String checkoutRequestID;

    private String merchantRequestID;

    private int resultCode;

    private String resultDesc;

}
