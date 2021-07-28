package com.api.crm.controller;

import com.api.crm.model.Client;
import com.api.crm.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping
    public List<Client> findAll (){
        return clientRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity findById (@PathVariable long id){
        return clientRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client create (@RequestBody Client client) {
        return clientRepository.save(client);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity update (@PathVariable long id, @RequestBody Client body){
        return clientRepository.findById(id)
                .map(client -> {
                    client.setName(body.getName());
                    Client updated = clientRepository.save(client);

                    return ResponseEntity.ok().body(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity destroy (@PathVariable long id) {
        return clientRepository.findById(id)
                .map(client -> {
                    clientRepository.deleteById(id);

                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
