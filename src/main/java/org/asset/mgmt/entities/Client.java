package org.asset.mgmt.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Client extends BaseEntity {
    private String company;
    private String email;
    private String mobile;
    private String address;
    private String status;
}
