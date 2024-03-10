package org.asset.mgmt.resources;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.asset.mgmt.repositries.AssetTypeRepository;
import org.asset.mgmt.dto.AssetTypeDTO;
import org.asset.mgmt.entities.AssetType;
import org.asset.mgmt.entities.Client;
import org.asset.mgmt.mapper.GenericMapper;
import org.asset.mgmt.repositries.ClientRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/client/{clientId}/assetType")
@Slf4j
@RequiredArgsConstructor
public class AssetTypeResource {

    private final GenericMapper mapper;

    private final ClientRepository clientRepository;

    private final AssetTypeRepository assetTypeRepository;

    @GetMapping
    public ResponseEntity<List<AssetTypeDTO>> listAssetTypes(@PathVariable("clientId") Long clientId) {
        Pageable pageable = Pageable.ofSize(100);
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Invalid Client"));
        List<AssetType> assetTypes = assetTypeRepository.findAllByClient(client, pageable).stream().toList();
        List<AssetTypeDTO> assetTypeDTOList = assetTypes.stream()
                .map(assetType -> mapper.toDTO(assetType)).collect(Collectors.toList());
        return ResponseEntity.ok(assetTypeDTOList);
    }

    @PostMapping
    public ResponseEntity<AssetTypeDTO> createAssetType(@PathVariable("clientId") Long clientId,
                                                        @RequestBody @Valid AssetTypeDTO assetDTO) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Invalid Client"));
        AssetType assetType = mapper.toEntity(assetDTO);
        assetType.setClient(client);
        assetType = assetTypeRepository.save(assetType);
        assetDTO.setId(assetType.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(assetDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetTypeDTO> updateAssetType(@PathVariable("clientId") Long clientId,
                                                        @RequestBody AssetTypeDTO assetTypeDTO,
                                                        @PathVariable("id") Long id) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Invalid Client"));
        AssetType assetType = mapper.toEntity(assetTypeDTO);
        assetType = assetTypeRepository.save(assetType);
        return ResponseEntity.ok(assetTypeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssetType(@PathVariable("clientId") Long clientId,
                                                @PathVariable("id") Long id) {
        assetTypeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
