package com.springboot.app.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.app.models.dao.IFacturaDao;
import com.springboot.app.models.entity.Factura;

@Service
public class FacturaServiceImpl implements IFacturaService {

	@Autowired
	private IFacturaDao facturaDao;
	
	@Override
	public Factura facturaFindById(Long id) {
		return this.facturaDao.findById(id).orElse(null);
	}

	@Override
	public void save(Factura factura) {
		this.facturaDao.save(factura);
	}

}
