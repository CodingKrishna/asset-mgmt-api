package org.asset.mgmt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetDTO extends BaseDTO {
    String assetTypeId;
    String name;
    String slNo;
    String refNo;
    long issuedDate;
    String moreDetails;
    boolean status;
}
