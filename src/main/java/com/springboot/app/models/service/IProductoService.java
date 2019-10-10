package com.springboot.app.models.service;

import java.util.List;

import com.springboot.app.models.entity.Producto;

public interface IProductoService {

	public List<Producto> findByNombre(String termino);
	public Producto findById(Long id);

}
