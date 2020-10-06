package com.desafio.client.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.desafio.client.dto.ClientDTO;
import com.desafio.client.entities.Client;
import com.desafio.client.repositories.ClientRepository;
import com.desafio.client.service.exceptions.ResourceNotFoundException;

@Service
@Transactional
public class ClientService {
	
	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPerPage(PageRequest pageRequest){
		
		Page<Client> clientPage = repository.findAll(pageRequest);
		
		Page<ClientDTO> pageDto = clientPage.map(x -> new ClientDTO(x));
		
		return pageDto;
	}
	
	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		
		Optional<Client> obj = repository.findById(id);
		 Client entity = obj.orElseThrow(()-> new ResourceNotFoundException("Client not found"));
		return new ClientDTO(entity);
	}
	

	public ClientDTO insert(ClientDTO dto) {
		Client entity = new Client();
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());
		entity = repository.save(entity);
		return new ClientDTO(entity);
		
	}
	

	public ClientDTO update(Long id , ClientDTO dto) {
		try {
			Client entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity.setCpf(dto.getCpf());
			entity.setIncome(dto.getIncome());
			entity.setBirthDate(dto.getBirthDate());
			entity.setChildren(dto.getChildren());
			entity = repository.save(entity);
			return new ClientDTO(entity);
		}
		
		catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}
	
}
