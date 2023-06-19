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

import com.biblioteca.biblioteca.Models.Livro;
import com.biblioteca.biblioteca.Repositories.LivroRepository;

@CrossOrigin(origins = "localhost:8080")
@RestController
@RequestMapping("/poo/livro")
public class LivroController {
    @Autowired
    LivroRepository livroRepository;

    // Permitir a busca, criação, edição e exclusão utilizando como busca os atributos id ou título (caminho “porTitulo”).
    @GetMapping("/{id}")
    public ResponseEntity<Livro> getLivro (@PathVariable Long id) {
        try {
            Optional<Livro> optLivro = livroRepository.findById(id);

            if (optLivro.isPresent()) {                
                return new ResponseEntity<Livro>(optLivro.get(), HttpStatus.OK);
            } 
            return new ResponseEntity<Livro>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/porTitulo/{titulo}")
    public ResponseEntity<Livro> getLivroPorTitulo (@PathVariable String titulo) {
        try {
            Optional<Livro> optLivro = livroRepository.findByTitulo(titulo);

            if (optLivro.isPresent()) {                
                return new ResponseEntity<Livro>(optLivro.get(), HttpStatus.OK);
            } 
            return new ResponseEntity<Livro>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Livro> createLivro(@RequestBody Livro livro) {
        try {
            Livro add = new Livro(livro.getId(), livro.getTitulo(), livro.getAutor(), livro.getAnoPublicacao(), livro.getDisponivel());
            if (add.getDisponivel() == null) add.setDisponivel(true);
            Livro __livro = livroRepository.save(add);
            return new ResponseEntity<>(__livro, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }  
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> editLivro (@RequestBody Livro newLivro, @PathVariable Long id) {
        try {
            Optional<Livro> optLivro = livroRepository.findById(id);

            if (optLivro.isPresent()) {      
                Livro _livro = optLivro.get();

                if (newLivro.getTitulo() != null) _livro.setTitulo(newLivro.getTitulo());
                if (newLivro.getAutor() != null) _livro.setAutor(newLivro.getAutor());
                if (newLivro.getAnoPublicacao() != null) _livro.setAnoPublicacao(newLivro.getAnoPublicacao());
                if (newLivro.getDisponivel() != null) _livro.setDisponivel(newLivro.getDisponivel());

                livroRepository.save(_livro);

                return new ResponseEntity<Livro>(_livro, HttpStatus.ACCEPTED);
            } 
            return new ResponseEntity<Livro>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/porTitulo/{titulo}")
    public ResponseEntity<Livro> editLivroPorTitulo(@RequestBody Livro newLivro, @PathVariable String titulo) {
        try {
            Optional<Livro> optLivro = livroRepository.findByTitulo(titulo);

            if (optLivro.isPresent()) {      
                Livro _livro = optLivro.get();

                if (newLivro.getTitulo() != null) _livro.setTitulo(newLivro.getTitulo());
                if (newLivro.getAutor() != null) _livro.setAutor(newLivro.getAutor());
                if (newLivro.getAnoPublicacao() != null) _livro.setAnoPublicacao(newLivro.getAnoPublicacao());
                if (newLivro.getDisponivel() != null) _livro.setDisponivel(newLivro.getDisponivel());

                livroRepository.save(_livro);

                return new ResponseEntity<Livro>(_livro, HttpStatus.ACCEPTED);
            } 
            return new ResponseEntity<Livro>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Livro> deleteLivro (@PathVariable Long id) {
        try {
            Optional<Livro> optLivro = livroRepository.findById(id);

            if (optLivro.isPresent()) {  
                livroRepository.deleteById(id);
                
                return new ResponseEntity<Livro>(optLivro.get(), HttpStatus.ACCEPTED);
            } 
            return new ResponseEntity<Livro>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/porTitulo/{titulo}")
    public ResponseEntity<Livro> deleteLivroPorRA (@PathVariable String titulo) {
        try {
            Optional<Livro> optLivro = livroRepository.findByTitulo(titulo);

            if (optLivro.isPresent()) { 
                Livro __livro = optLivro.get();
                livroRepository.delete(__livro);
                
                return new ResponseEntity<Livro>(optLivro.get(), HttpStatus.ACCEPTED);
            } 
            return new ResponseEntity<Livro>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
