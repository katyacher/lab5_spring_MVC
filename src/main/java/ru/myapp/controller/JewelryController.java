package ru.myapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.myapp.dao.JewelDao;
import ru.myapp.model.Jewel;

@Controller
@RequestMapping("/jewelry")
public class JewelryController {

	private final JewelDao jewelDao;

	@Autowired
	public JewelryController(JewelDao jewelDao) {
		this.jewelDao = jewelDao;
	}

	@GetMapping()
	public String show(Model model) {
		/**
		 * Получим все ювелирные изделия из DAO и передадим на отображеие в
		 * представление
		 */
		model.addAttribute("jewelry", jewelDao.findAll());

		return "jewelry/index";
	}

	@GetMapping("/{id}")
	public String showById(@PathVariable("id") int id, Model model) {
		/**
		 * Получим одно ювелирное изделие по его id из DAO и передадим на отображеие в
		 * представление
		 */
		model.addAttribute("jewel", jewelDao.findById(id));

		return "jewelry/showById";
	}

	@GetMapping("/new")
	public String newJewel(@ModelAttribute("jewel") Jewel jewel) {
		return "jewelry/new";
	}

	@PostMapping
	public String create(@ModelAttribute("jewel") @Valid Jewel jewel, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "jewelry/new";
		}
		jewelDao.insert(jewel);
		return "redirect:jewelry";// or "succsesPage"
	}

	@GetMapping("/{id}/edit")
	public String edit(Model model, @PathVariable("id") int id) {
		/**
		 * Получим объект для редактирования, чтобы в полях были значения редактируемого
		 * объекта
		 */
		model.addAttribute("jewel", jewelDao.findById(id));
		return "jewelry/edit";
	}

	// @PatchMapping("/{id}")
	@PostMapping("/{id}")
	public String update(@ModelAttribute("jewel") @Valid Jewel jewel, BindingResult bindingResult,
			@PathVariable("id") int id) {
		if (bindingResult.hasErrors()) {
			return "jewelry/edit";
		}
		jewelDao.update(id, jewel);
		return "redirect:jewelry";
	}

	// DeleteMapping("/{id}")
	@PostMapping("/{id}")
	public String delete(@PathVariable("id") int id) {
		jewelDao.delete(id);
		return "redirect:jewelry";// or "succsesPage"
	}

	/*
	 * @GetMapping("/{price}") public String showUpTo(Model
	 * model, @PathVariable("price") int price){
	 *//**
		 * Получим все ювелирные изделия из DAO и передадим на отображеие в
		 * представление
		 *//*
			 * model.addAttribute("jewelry", jewelDao.searchUpTo(price));
			 * 
			 * return "jewelry/showUpTo"; }
			 */
	/*
	 * @ModelAttribute public String headerMessage() { return
	 * "Welcome to our website!"; }
	 */
}
