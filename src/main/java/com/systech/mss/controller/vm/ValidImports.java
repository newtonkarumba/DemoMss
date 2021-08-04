
package com.systech.mss.controller.vm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systech.mss.util.Ignore;
import lombok.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidImports {

    @Ignore
    private List<Row> rows;
    @Ignore
    private Long totalCount;


    public static ValidImports from(JSONObject jsonObject) {
        String s=jsonObject.toString();
        try {
            return  new ObjectMapper().readValue(s,ValidImports.class);
        } catch (IOException e) {
            return null;
        }
    }
}
