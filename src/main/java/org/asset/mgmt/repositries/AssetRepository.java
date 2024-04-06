package org.asset.mgmt.repositries;

import org.asset.mgmt.entities.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset,Long> {
}
