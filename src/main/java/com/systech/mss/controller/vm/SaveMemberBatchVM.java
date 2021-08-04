package com.systech.mss.controller.vm;

import com.systech.mss.service.dto.MemberUploadDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SaveMemberBatchVM {

    private List<MemberUploadDTO> rows;
}
