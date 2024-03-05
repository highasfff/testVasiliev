package com.example.testvasiliev.controller;

import com.example.testvasiliev.model.Paste;
import com.example.testvasiliev.service.PasteService;
import com.example.testvasiliev.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pastes")
public class PasteController {
    private final PasteService pasteService;
    private final PersonService personService;

    @Autowired
    public PasteController(PasteService pasteService, PersonService personService) {
        this.pasteService = pasteService;
        this.personService = personService;
    }

    @GetMapping()
    public String show(Model model) {
        model.addAttribute("pastes", pasteService.findAll());
        return "pastes/show";
    }

    @GetMapping("/new")
    public String addForm(@ModelAttribute("paste") Paste paste, Model model) {
        model.addAttribute("persons", personService.findAll());
        return "pastes/new";
    }

    @PostMapping()
    public String add(@ModelAttribute("paste") Paste paste) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        pasteService.add(paste, personService.findByUsername(user.getUsername()));
        return "redirect:/pastes";
    }

    @GetMapping("{hash}/edit")
    public String updateForm(Model model, @PathVariable("hash") int hash) {
        model.addAttribute("paste", pasteService.findByHash(hash));
        return "pastes/edit";
    }

    @PatchMapping("/{hash}")
    public String update(@ModelAttribute("updatedPaste") Paste updatedPaste, @PathVariable("hash") int hash) {
        pasteService.update(updatedPaste,hash);
        return "redirect:/pastes";
    }

    @GetMapping("/{hash}")
    public String index(@PathVariable("hash") int hash, Model model) {
        model.addAttribute("paste", pasteService.findByHash(hash));
        return "pastes/index";
    }
}
