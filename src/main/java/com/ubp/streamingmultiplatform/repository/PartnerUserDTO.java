package com.ubp.streamingmultiplatform.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class PartnerUserDTO {
    Integer partner_id;
    Integer user_id;
    String status;
    String type;
    Float fee;
    String transaction_id;
    String identity_key;
    String activation_date;
    Date created_at;
    Date updated_at;
    Date deleted_at;
}
