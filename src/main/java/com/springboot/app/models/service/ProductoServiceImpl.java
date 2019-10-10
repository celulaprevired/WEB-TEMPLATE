package com.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.app.models.dao.IProductoDao;
import com.springboot.app.models.entity.Producto;

@Service
public class ProductoServiceImpl implements IProductoService {

	@Autowired
	private IProductoDao productoDao;

	@Override
	public List<Producto> findByNombre(String termino) {
		return this.productoDao.findByNombre(termino);
	}

	@Override
	public Producto findById(Long id) {
		return this.productoDao.findById(id).orElse(null);
	}
	


}
