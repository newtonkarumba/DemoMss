package com.systech.mss.service.dto;

import lombok.*;

import java.io.Serializable;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientOrdinal extends GenericModel<ClientOrdinal> implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;
    private String abbreviation;
    private String fullname;


}
