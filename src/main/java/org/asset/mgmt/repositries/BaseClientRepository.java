package org.asset.mgmt.repositries;

import jakarta.validation.constraints.NotNull;
import org.asset.mgmt.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseClientRepository <V, k> extends JpaRepository<V, Long> {

    Page<V> findAllByClient(@NotNull Client client, Pageable pageable);

    Optional<V> findByClient(@NotNull Client client);
}
