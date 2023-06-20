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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.biblioteca.Models.Livro;
import com.biblioteca.biblioteca.Models.Reserva;
import com.biblioteca.biblioteca.Repositories.LivroRepository;
import com.biblioteca.biblioteca.Repositories.ReservaRepository;
import com.biblioteca.biblioteca.Repositories.UsuarioRepository;

@CrossOrigin(origins = "localhost:8080")
@RestController
@RequestMapping("/poo/biblioteca")
public class BibliotecaController {
    @Autowired
    LivroRepository livroRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ReservaRepository reservaRepository;

    //#region Possibilitar adicionar/remover/consultar livros (caminho “livro”), utilizando os atributos id ou título (caminho “porTitulo”)
    @GetMapping("/livro/{id}")
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
    @GetMapping("/livro/porTitulo/{titulo}")
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

    @PostMapping("/livro/")
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

    @DeleteMapping("/livro/{id}")
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
    @DeleteMapping("/livro/porTitulo/{titulo}")
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
    }//#endregion

    // Possibilitar adicionar/remover/consultar empréstimos (caminho “emprestimo”) e reservas (caminho “reserva”) , Informando os conjuntos: Id do empréstimo, Id da reserva, Id do livro e id do usuário (caminho “porIdLivroIdUsuario”), Nome do livro e RA do usuário (caminho “porNomeLivroRAUsuario”), Nome do livro e email do usuário (caminho “porNomeLivroEmailUsuario”).
    // ▪ A reserva ou empréstimo deve alterar a disponibilidade do livro. 
    // ▪ Caso haja a tentativa de emprestar um livro indisponível, o empréstimo não deve ser efetuado. Uma exceção à regra é se já houver uma reserva do livro para o usuário em questão.
        //#region Reqs de "Reservas"
        @PostMapping("/reserva/")
        public ResponseEntity<Reserva> createReserva(@RequestBody Reserva reserva) {
            try {
                Reserva add = new Reserva(reserva.getId(), reserva.getLivro(), reserva.getUsario(), reserva.getDataReserva());
                //if (add.getDataReserva() == null) add.setDataReserva();
                Reserva __reserva = reservaRepository.save(add);
                return new ResponseEntity<>(__reserva, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }  
        }
        //#endregion
    //#endregion

    // TODO função hasBiblioteca
}
