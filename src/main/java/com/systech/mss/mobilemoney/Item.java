
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
public class Item {

    private String name;

    @Ignore
    private Double value;

}
