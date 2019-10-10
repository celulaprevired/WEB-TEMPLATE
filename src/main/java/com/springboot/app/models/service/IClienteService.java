package com.springboot.app.models.service;

import java.util.List;
import com.springboot.app.models.entity.Cliente;


public interface IClienteService {
	
	public List<Cliente>findAll();
	public void  save(Cliente cliente);
	public Cliente clienteFindById(Long id);
	public void delete(Cliente cliente);

}
