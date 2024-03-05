package com.example.testvasiliev.controller;

import com.example.testvasiliev.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personService.findById(id));
        model.addAttribute("pastes", personService.getPastes(id));
        return "persons/index";
    }
}
