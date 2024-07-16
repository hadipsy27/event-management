/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mii.co.id.emsserverside.controller;

import mii.co.id.emsserverside.model.data.LoginRequestDto;
import mii.co.id.emsserverside.model.data.LoginResponseDto;
import mii.co.id.emsserverside.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Richo Dea
 */
@RestController
@RequestMapping
public class CredentialController {
    private CredentialService credentialService;

    @Autowired
    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto login){
        return new ResponseEntity(credentialService.login(login), HttpStatus.OK);
    }
    
}
