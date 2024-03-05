package com.example.testvasiliev.service;

import com.example.testvasiliev.model.Paste;
import com.example.testvasiliev.model.Person;
import com.example.testvasiliev.repository.PersonRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findById(int id) {
        Optional<Person> person = personRepository.findById(id);
        return person.orElse(null);
    }

    public List<Paste> getPastes(int id) {
        Optional<Person> person = personRepository.findById(id);

        if(person.isPresent()) {
            Hibernate.initialize(person);
            List<Paste> pastes = person.get().getPastes();
            for(Paste paste : pastes) {
                if(paste.getExpirationTime().isBefore(LocalDateTime.now())) {
                    paste.setExpired(true);
                }
            }
            return pastes;
        } else {
            return Collections.emptyList();
        }
    }

    public Person findByUsername(String username) {
        Optional<Person> person = personRepository.findByUsername(username);
        return person.orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return personRepository.findByUsername(username).
                map(person -> new org.springframework.security.core.userdetails.User(
                        person.getUsername(),
                        person.getPassword(),
                        Collections.singleton(person.getRole())
                )).
                orElseThrow(() -> new UsernameNotFoundException("Не удалось получить пользователя: " + username));
    }
}
