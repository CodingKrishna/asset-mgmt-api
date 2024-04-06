package org.asset.mgmt.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Asset extends BaseEntity {
    String name;
    String slNo;
    String refNo;
    long issuedDate;
    String moreDetails;
    boolean status;
}
