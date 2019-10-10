package com.springboot.app.models.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.springboot.app.models.entity.Cliente;
import com.springboot.app.models.entity.Factura;
import com.springboot.app.models.entity.ItemFactura;
import com.springboot.app.models.entity.Producto;
import com.springboot.app.models.service.IClienteService;
import com.springboot.app.models.service.IFacturaService;
import com.springboot.app.models.service.IProductoService;

import aj.org.objectweb.asm.Type;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

	@Autowired
	private IFacturaService facturaService;

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IProductoService productoService;

	@GetMapping("/ver/{id}")
	public String ver(@PathVariable Long id, Model model) {
		try {
			Factura factura = this.facturaService.facturaFindById(id);
			if (factura != null) {
				model.addAttribute("titulo", "Detalle de Factura");
				model.addAttribute("factura", factura);
				return "factura/ver";
			}
			return "redirect:/cliente/listar";
		} catch (NumberFormatException e) {
			return "redirect:/cliente/listar";
		}
	}

	@GetMapping("/crear/{id}")
	public String crear(Model model, @PathVariable Long id) {
		Cliente cliente = this.clienteService.clienteFindById(id);
		if (cliente != null) {
			Factura factura = new Factura();
			factura.setCliente(cliente);
			model.addAttribute("titulo", "Creacion de Factura");
			model.addAttribute("factura", factura);
			return "factura/form";
		}
		return "redirect:/cliente/listar";

	}

	@GetMapping(value = "/cargar-productos/{termino}", produces = { "application/json" })
	public @ResponseBody List<Producto> cargaProductos(@PathVariable String termino) {
		return this.productoService.findByNombre(termino);
	}

	@PostMapping("/save")
	public String guardar(Factura factura,@RequestParam(name = "cantidad[]", required = false) Integer cantidad[],
			@RequestParam(name = "item_id[]", required = false) Long id[],SessionStatus status) {
		
		for (int i = 0; i < id.length; i++) {
			Producto p = this.productoService.findById(id[i]);
			ItemFactura item = new ItemFactura();
			item.setCantidad(cantidad[i]);
			item.setProducto(p);
			factura.facturaAddItems(item);
		}
		this.facturaService.save(factura);
		status.setComplete();
		
		return "redirect:/cliente/listar";
	}

}
