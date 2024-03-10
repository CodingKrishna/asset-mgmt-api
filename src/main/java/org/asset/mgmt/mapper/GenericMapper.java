package org.asset.mgmt.mapper;


import org.asset.mgmt.entities.Asset;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.asset.mgmt.dto.AssetDTO;
import org.asset.mgmt.dto.AssetTypeDTO;
import org.asset.mgmt.dto.ClientDTO;
import org.asset.mgmt.entities.AssetType;
import org.asset.mgmt.entities.Client;

@Mapper(componentModel = "spring", uses = {}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface GenericMapper {

    ClientDTO toDTO(Client entity);

    Client toEntity(ClientDTO dto);

    AssetTypeDTO toDTO(AssetType entity);

    AssetType toEntity(AssetTypeDTO dto);

    AssetDTO toDTO(Asset entity);

    Asset toEntity(AssetDTO dto);
}
