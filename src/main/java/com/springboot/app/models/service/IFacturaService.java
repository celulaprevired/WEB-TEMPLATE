package com.springboot.app.models.service;


import com.springboot.app.models.entity.Factura;


public interface IFacturaService {

	public Factura facturaFindById(Long id);
	public void save(Factura factura);
}
