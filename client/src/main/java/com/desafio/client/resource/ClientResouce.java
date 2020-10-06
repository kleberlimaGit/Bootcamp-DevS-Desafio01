package com.desafio.client.resource;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.desafio.client.dto.ClientDTO;
import com.desafio.client.service.ClientService;

@RestController
@RequestMapping("/clients")
public class ClientResouce {
	
	@Autowired
	private ClientService service;
	
	//======================================== Paginação ====================================================
	@GetMapping
	public ResponseEntity<Page<ClientDTO>> findAllPerPage(
			
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy){
			
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Page<ClientDTO> pageDto = service.findAllPerPage(pageRequest);
			
			
		return ResponseEntity.ok(pageDto);
	}
	//======================================== Busca por ID ====================================================
	
	@GetMapping(value= "/{id}")
	public ResponseEntity<ClientDTO> findById(@PathVariable Long id){
		
		ClientDTO dto = service.findById(id);
		
		return ResponseEntity.ok().body(dto);
	}
	//======================================== Inserir ====================================================
	@PostMapping
	public ResponseEntity<ClientDTO> insert(@RequestBody ClientDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	//======================================== Update ====================================================
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> update(@PathVariable Long id, @RequestBody ClientDTO dto){
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
