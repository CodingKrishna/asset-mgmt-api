package org.asset.mgmt.resources;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.asset.mgmt.dto.AssetDTO;
import org.asset.mgmt.entities.Asset;
import org.asset.mgmt.entities.Client;
import org.asset.mgmt.mapper.GenericMapper;
import org.asset.mgmt.repositries.AssetRepository;
import org.asset.mgmt.repositries.ClientRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/client/{clientId}/asset")
@Slf4j
@RequiredArgsConstructor
public class AssetResource {

    private final GenericMapper mapper;

    private final ClientRepository clientRepository;

    private final AssetRepository assetTypeRepository;

    @GetMapping
    public ResponseEntity<List<AssetDTO>> listAssetTypes(@PathVariable("clientId") Long clientId) {
        Pageable pageable = Pageable.ofSize(100);
//        Client client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Invalid Client"));
        List<Asset> assets = assetTypeRepository.findAll( pageable).stream().toList();
        List<AssetDTO> assetDTOList = assets.stream()
                .map(assetType -> mapper.toDTO(assetType)).collect(Collectors.toList());
        return ResponseEntity.ok(assetDTOList);
    }

    @PostMapping
    public ResponseEntity<AssetDTO> createAssetType(@PathVariable("clientId") Long clientId,
                                                        @RequestBody @Valid AssetDTO assetDTO) {
//        Client client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Invalid Client"));
        Asset assetType = mapper.toEntity(assetDTO);
//        assetType.setClient(client);
        assetType = assetTypeRepository.save(assetType);
        assetDTO.setId(assetType.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(assetDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetDTO> updateAssetType(@PathVariable("clientId") Long clientId,
                                                        @RequestBody AssetDTO assetTypeDTO,
                                                        @PathVariable("id") Long id) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Invalid Client"));
        Asset assetType = mapper.toEntity(assetTypeDTO);
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
