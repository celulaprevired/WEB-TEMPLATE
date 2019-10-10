package com.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.app.models.dao.IClienteDao;
import com.springboot.app.models.entity.Cliente;

@Service
public class ClienteServiceImpl implements IClienteService{

	@Autowired
	private IClienteDao clienteDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) this.clienteDao.findAll();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		this.clienteDao.save(cliente);
	}
	

	@Override
	@Transactional(readOnly = true)
	public Cliente clienteFindById(Long id) {
		 return this.clienteDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Cliente cliente) {
		this.clienteDao.delete(cliente);	
	}

}
