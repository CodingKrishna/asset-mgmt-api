package org.asset.mgmt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO extends BaseDTO{

    private String company;
    private String email;
    private String mobile;
    private String address;
    private String status;
}
