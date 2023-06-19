package com.biblioteca.biblioteca.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.biblioteca.Models.Usuario;
import com.biblioteca.biblioteca.Repositories.UsuarioRepository;

@CrossOrigin(origins = "localhost:8080")
@RestController
@RequestMapping("/poo/usuario")
public class UsuarioController {
    @Autowired
    UsuarioRepository usuarioRepository;

    // Permitir a busca (get), criação (post), edição (put) e exclusão (delete) utilizando como busca os atributos id, RA (caminho “porRA”) ou email (caminho “porEmail”).
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuario (@PathVariable Long id) {
        try {
            Optional<Usuario> optUsuario = usuarioRepository.findById(id);

            if (optUsuario.isPresent()) {                
                return new ResponseEntity<Usuario>(optUsuario.get(), HttpStatus.OK);
            } 
            return new ResponseEntity<Usuario>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/porRA/{RA}")
    public ResponseEntity<Usuario> getUsuarioPorRA (@PathVariable String RA) {
        try {
            Optional<Usuario> optUsuario = usuarioRepository.findByRA(RA);

            if (optUsuario.isPresent()) {                
                return new ResponseEntity<Usuario>(optUsuario.get(), HttpStatus.OK);
            } 
            return new ResponseEntity<Usuario>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/porEmail/{email}")
    public ResponseEntity<Usuario> getUsuarioPorEmail (@PathVariable String email) {
        try {
            Optional<Usuario> optUsuario = usuarioRepository.findByEmail(email);

            if (optUsuario.isPresent()) {                
                return new ResponseEntity<Usuario>(optUsuario.get(), HttpStatus.OK);
            } 
            return new ResponseEntity<Usuario>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario add = new Usuario(usuario.getId(), usuario.getRA(), usuario.getNome(), usuario.getEmail());
            Usuario __usuario = usuarioRepository.save(add);
            return new ResponseEntity<>(__usuario, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }  
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> editUsuario (@RequestBody Usuario newUsuario, @PathVariable Long id) {
        try {
            Optional<Usuario> optUsuario = usuarioRepository.findById(id);

            if (optUsuario.isPresent()) {      
                Usuario _usuario = optUsuario.get();

                if (newUsuario.getEmail() != null) _usuario.setEmail(newUsuario.getEmail());
                if (newUsuario.getNome() != null) _usuario.setNome(newUsuario.getNome());
                if (newUsuario.getRA() != null) _usuario.setRA(newUsuario.getRA());

                usuarioRepository.save(_usuario);

                return new ResponseEntity<Usuario>(_usuario, HttpStatus.ACCEPTED);
            } 
            return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/porRA/{RA}")
    public ResponseEntity<Usuario> editUsuarioPorRA (@RequestBody Usuario newUsuario, @PathVariable String RA) {
        try {
            Optional<Usuario> optUsuario = usuarioRepository.findByRA(RA);

            if (optUsuario.isPresent()) {      
                Usuario _usuario = optUsuario.get();

                if (newUsuario.getEmail() != null) _usuario.setEmail(newUsuario.getEmail());
                if (newUsuario.getNome() != null) _usuario.setNome(newUsuario.getNome());
                if (newUsuario.getRA() != null) _usuario.setRA(newUsuario.getRA());

                usuarioRepository.save(_usuario);

                return new ResponseEntity<Usuario>(_usuario, HttpStatus.ACCEPTED);
            } 
            return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/porEmail/{email}")
    public ResponseEntity<Usuario> editUsuarioPorEmail (@RequestBody Usuario newUsuario, @PathVariable String email) {
        try {
            Optional<Usuario> optUsuario = usuarioRepository.findByEmail(email);

            if (optUsuario.isPresent()) {      
                Usuario _usuario = optUsuario.get();

                if (newUsuario.getEmail() != null) _usuario.setEmail(newUsuario.getEmail());
                if (newUsuario.getNome() != null) _usuario.setNome(newUsuario.getNome());
                if (newUsuario.getRA() != null) _usuario.setRA(newUsuario.getRA());

                usuarioRepository.save(_usuario);

                return new ResponseEntity<Usuario>(_usuario, HttpStatus.ACCEPTED);
            } 
            return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Usuario> deleteUsuario (@PathVariable Long id) {
        try {
            Optional<Usuario> optUsuario = usuarioRepository.findById(id);

            if (optUsuario.isPresent()) {  
                usuarioRepository.deleteById(id);
                
                return new ResponseEntity<Usuario>(optUsuario.get(), HttpStatus.ACCEPTED);
            } 
            return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/porRA/{RA}")
    public ResponseEntity<Usuario> deleteUsuarioPorRA (@PathVariable String RA) {
        try {
            Optional<Usuario> optUsuario = usuarioRepository.findByRA(RA);

            if (optUsuario.isPresent()) { 
                Usuario _usuario = optUsuario.get();
                usuarioRepository.delete(_usuario);
                
                return new ResponseEntity<Usuario>(optUsuario.get(), HttpStatus.ACCEPTED);
            } 
            return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/porEmail/{email}")
    public ResponseEntity<Usuario> deleteUsuarioPorEmail (@PathVariable String email) {
        try {
            Optional<Usuario> optUsuario = usuarioRepository.findByEmail(email);

            if (optUsuario.isPresent()) { 
                Usuario _usuario = optUsuario.get();
                usuarioRepository.delete(_usuario);
                
                return new ResponseEntity<Usuario>(optUsuario.get(), HttpStatus.ACCEPTED);
            } 
            return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
