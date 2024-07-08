package org.asset.mgmt.resources;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.asset.mgmt.dto.ClientDTO;
import org.asset.mgmt.entities.Client;
import org.asset.mgmt.mapper.GenericMapper;
import org.asset.mgmt.repositries.ClientRepository;

import org.asset.mgmt.service.TenantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/client")
@Slf4j
@RequiredArgsConstructor
public class ClientResource {

    private final GenericMapper mapper;

    private final ClientRepository clientRepository;

    private final TenantService tenantService;

    @GetMapping
    public ResponseEntity<List<ClientDTO>> listTenants() {
        List<Client> clients = clientRepository.findAll();
        List<ClientDTO> clientDTOList = clients.stream().map(client -> mapper.toDTO(client)).collect(Collectors.toList());
        return ResponseEntity.ok(clientDTOList);
    }

    @PostMapping
    @Transactional
    public  ResponseEntity<ClientDTO> createTenant(@RequestBody @Valid ClientDTO clientDTO) {
        Client client = mapper.toEntity(clientDTO);
        client = clientRepository.save(client);
        clientDTO.setId(client.getId());
        tenantService.addTenant(client.getCompany());
        return ResponseEntity.status(HttpStatus.CREATED).body(clientDTO);
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<ClientDTO> updateTenant(
            @PathVariable Long clientId, @RequestBody ClientDTO clientDTO) {
        Client client = mapper.toEntity(clientDTO);
        client = clientRepository.save(client);
        return ResponseEntity.ok(clientDTO);
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> deleteTenant(@PathVariable Long clientId) {
        clientRepository.deleteById(clientId);
        return ResponseEntity.noContent().build();
    }
}
