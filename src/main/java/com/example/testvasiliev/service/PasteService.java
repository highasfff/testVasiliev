package com.example.testvasiliev.service;

import com.example.testvasiliev.model.Paste;
import com.example.testvasiliev.model.Person;
import com.example.testvasiliev.repository.PasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PasteService {

    private final PasteRepository pasteRepository;

    @Autowired
    public PasteService(PasteRepository pasteRepository) {
        this.pasteRepository = pasteRepository;
    }
    public List<Paste> findAll() {
        List<Paste> pastes = pasteRepository.findAll();
        for(Paste paste : pastes) {
            if(paste.getExpirationTime().isBefore(LocalDateTime.now())) {
                paste.setExpired(true);
            }
        }
        return pastes;
    }
    @Transactional
    public void add(Paste paste, Person person) {
        paste.setAuthor(person);
        pasteRepository.save(paste);
    }

    public Paste findByHash(int hash) {
        Paste paste = pasteRepository.findById(hash).get();
        if(paste.getExpirationTime().isBefore(LocalDateTime.now())) {
            paste.setExpired(true);
            return paste;
        }
        return null;
    }

    @Transactional
    public void update(Paste updatedPaste, int hash) {
        Paste pasteToBeUpdated = pasteRepository.findById(hash).get();

        updatedPaste.setHash(hash);
        updatedPaste.setAuthor(pasteToBeUpdated.getAuthor());

        pasteRepository.save(updatedPaste);
    }
}
