package org.asset.mgmt.repositries;

import org.asset.mgmt.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
}
