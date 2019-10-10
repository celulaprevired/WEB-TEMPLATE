package com.springboot.app.models.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.app.models.entity.Cliente;
import com.springboot.app.models.service.IClienteService;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private IClienteService clienteService;

	@GetMapping("/listar")
	public String listar(Model model) {
		List<Cliente> clientes = this.clienteService.findAll();
		model.addAttribute("titulo", "Listado de Clientes");
		model.addAttribute("clientes", clientes);
		return "listar";

	}

	@GetMapping("/crear")
	public String crear(Model model) {
		Cliente cliente = new Cliente();

		model.addAttribute("titulo", "Creacion de Cliente");
		model.addAttribute("cliente", cliente);
		return "form";

	}

	@PostMapping("/guardar")
	public String guardar(Cliente cliente, @RequestParam("file") MultipartFile foto) {
		try {
			if(!foto.isEmpty()) {
				Path directorio = Paths.get("src//main/resources/static/uploads");
				String rootPath = directorio.toFile().getAbsolutePath();
				try {
					byte bytes[] = foto.getBytes();
					Path rutaCompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename());
					Files.write(rutaCompleta, bytes);
					cliente.setFoto(foto.getOriginalFilename());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			this.clienteService.save(cliente);
			return "redirect:/cliente/listar";

		} catch (NumberFormatException e) {
			return "redirect:/cliente/listar";
		}
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Long id, Model model) {
		try {
			Cliente cliente = this.clienteService.clienteFindById(id);
			if (cliente != null) {
				model.addAttribute("titulo", "Edicion de Cliente");
				model.addAttribute("cliente", cliente);
				return "form";
			}
			return "redirect:/cliente/listar";
		} catch (NumberFormatException e) {
			return "redirect:/cliente/listar";
		}
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id) {
		try {
			this.clienteService.delete(this.clienteService.clienteFindById(id));
			return "redirect:/cliente/listar";
			
		} catch (NumberFormatException e) {
			return "redirect:/cliente/listar";
		}
	}
	
	@GetMapping("/detalle/{id}")
	public String detalle(@PathVariable Long id,Model model) {
		try {
			Cliente cliente = this.clienteService.clienteFindById(id);
			if (cliente != null) {
				model.addAttribute("titulo", "Detalle de Cliente");
				model.addAttribute("cliente", cliente);
				return "ver";
			}
			return "redirect:/cliente/listar";
		} catch (Exception e) {
			return "redirect:/cliente/listar";
		}
	}
}
