package org.asset.mgmt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetTypeDTO extends BaseDTO{
    String type;
    boolean status;
}
