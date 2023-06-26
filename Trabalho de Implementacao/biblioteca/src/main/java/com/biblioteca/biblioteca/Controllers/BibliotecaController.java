package com.biblioteca.biblioteca.Controllers;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

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

import com.biblioteca.biblioteca.Models.Biblioteca;
import com.biblioteca.biblioteca.Models.Emprestimo;
import com.biblioteca.biblioteca.Models.Livro;
import com.biblioteca.biblioteca.Models.Reserva;
import com.biblioteca.biblioteca.Models.Usuario;
import com.biblioteca.biblioteca.Repositories.BibliotecaRepository;
import com.biblioteca.biblioteca.Repositories.EmprestimoRepository;
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
    BibliotecaRepository bibliotecaRepository;
    @Autowired
    ReservaRepository reservaRepository;
    @Autowired
    EmprestimoRepository emprestimoRepository;

    // Possibilitar adicionar/remover/consultar livros (caminho “livro”), utilizando os atributos id ou título (caminho “porTitulo”)
    //#region Requerimentos de "Livros"
    @GetMapping("/{idBib}/livro/{id}")
    public ResponseEntity<Livro> getLivro (@PathVariable Long idBib, @PathVariable Long id) {
        try {
            Optional<Livro> optLivro = livroRepository.findById(id);
            Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(idBib);

            if (optLivro.isPresent() && optBiblioteca.get().getLivros().contains(optLivro.get())) {                
                return new ResponseEntity<Livro>(optLivro.get(), HttpStatus.OK);
            } 
            return new ResponseEntity<Livro>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<Livro>(HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/{idBib}/livro/porTitulo/{titulo}")
    public ResponseEntity<Livro> getLivroPorTitulo (@PathVariable Long idBib, @PathVariable String titulo) {
        try {
            Optional<Livro> optLivro = livroRepository.findByTitulo(titulo);
            Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(idBib);

            if (optLivro.isPresent() && optBiblioteca.get().getLivros().contains(optLivro.get())) { 
                return new ResponseEntity<Livro>(optLivro.get(), HttpStatus.OK);
            } 
            return new ResponseEntity<Livro>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<Livro>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/{idBib}/livro/")
    public ResponseEntity<Livro> createLivro(@PathVariable Long idBib, @RequestBody Livro livro) {
        try {
            Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(idBib);

            if (optBiblioteca.isPresent()) {
                Livro add = new Livro(livro.getId(), livro.getTitulo(), livro.getAutor(), livro.getAnoPublicacao(), livro.getDisponivel());
                if (add.getDisponivel() == null) add.setDisponivel(true);
                Livro __livro = livroRepository.save(add);

                Biblioteca __biblioteca = optBiblioteca.get();
                __biblioteca.getLivros().add(__livro);
                bibliotecaRepository.saveAndFlush(__biblioteca);

                return new ResponseEntity<Livro>(__livro, HttpStatus.CREATED);
            }
            return new ResponseEntity<Livro>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<Livro>(HttpStatus.BAD_REQUEST);
        }  
    }

    @DeleteMapping("/{idBib}/livro/{id}")
    public ResponseEntity<Livro> deleteLivro (@PathVariable Long idBib, @PathVariable Long id) {
        try {
            Optional<Livro> optLivro = livroRepository.findById(id);
            Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(idBib);

            if (optLivro.isPresent() && optBiblioteca.get().getLivros().contains(optLivro.get())) { 
                Livro __livro = optLivro.get();

                if (emprestimoRepository.findByLivro(__livro).isPresent()) 
                emprestimoRepository.deleteByLivro(__livro);

                if (reservaRepository.findByLivro(__livro).isPresent()) emprestimoRepository.deleteByLivro(__livro);
                livroRepository.deleteById(id);
                
                return new ResponseEntity<Livro>(optLivro.get(), HttpStatus.ACCEPTED);
            } 
            return new ResponseEntity<Livro>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<Livro>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{idBib}/livro/porTitulo/{titulo}")
    public ResponseEntity<Livro> deleteLivroPorRA (@PathVariable Long idBib, @PathVariable String titulo) {
        try {
            Optional<Livro> optLivro = livroRepository.findByTitulo(titulo);
            Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(idBib);

            if (optLivro.isPresent() && optBiblioteca.get().getLivros().contains(optLivro.get())) { 
                Livro __livro = optLivro.get();

                if (emprestimoRepository.findByLivro(__livro).isPresent()) 
                emprestimoRepository.deleteByLivro(__livro);

                if (reservaRepository.findByLivro(__livro).isPresent()) emprestimoRepository.deleteByLivro(__livro);
                livroRepository.delete(__livro);

                Biblioteca __biblioteca = optBiblioteca.get();
                __biblioteca.getLivros().add(__livro);
                bibliotecaRepository.save(__biblioteca);
                
                return new ResponseEntity<Livro>(__livro, HttpStatus.ACCEPTED);
            } 
            return new ResponseEntity<Livro>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<Livro>(HttpStatus.BAD_REQUEST);
        }
    }//#endregion

    // CRUD da biblioteca, busca só por Id
    //#region Requerimentos de "Biblioteca"
    @GetMapping("/{id}")
    public ResponseEntity<Biblioteca> getBiblioteca (@PathVariable Long id) {
        try {
            Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(id);

            if (optBiblioteca.isPresent()) {                
                return new ResponseEntity<Biblioteca>(optBiblioteca.get(), HttpStatus.OK);
            } 
            return new ResponseEntity<Biblioteca>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<Biblioteca>(HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/porNome/{nome}")
    public ResponseEntity<Biblioteca> getBibliotecaPorTitulo (@PathVariable String nome) {
        try {
            Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findByNome(nome);

            if (optBiblioteca.isPresent()) {                
                return new ResponseEntity<Biblioteca>(optBiblioteca.get(), HttpStatus.OK);
            } 
            return new ResponseEntity<Biblioteca>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<Biblioteca>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Biblioteca> createBiblioteca(@RequestBody Biblioteca biblioteca) {
        try {
            Biblioteca add = new Biblioteca(biblioteca.getId(), biblioteca.getNome(), biblioteca.getEmprestimo(), biblioteca.getReserva(), biblioteca.getLivros());
            Biblioteca __biblioteca = bibliotecaRepository.save(add);
            return new ResponseEntity<Biblioteca>(__biblioteca, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Biblioteca>(HttpStatus.BAD_REQUEST);
        }  
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Biblioteca> deleteBiblioteca (@PathVariable Long id) {
        try {
            Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(id);

            if (optBiblioteca.isPresent()) {
                Biblioteca __biblioteca = optBiblioteca.get();

                List<Emprestimo> listEmprestimos = __biblioteca.getEmprestimo();
                for (Emprestimo e : listEmprestimos)
                    emprestimoRepository.delete(e);

                List<Reserva> listReservas = __biblioteca.getReserva();
                for (Reserva r : listReservas)
                    reservaRepository.delete(r);

                List<Livro> listLivros = __biblioteca.getLivros();
                for (Livro l : listLivros)
                    livroRepository.delete(l);

                bibliotecaRepository.deleteById(id);
                
                return new ResponseEntity<Biblioteca>(optBiblioteca.get(), HttpStatus.ACCEPTED);
            } 
            return new ResponseEntity<Biblioteca>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<Biblioteca>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/porNome/{nome}")
    public ResponseEntity<Biblioteca> deleteBibliotecaPorRA (@PathVariable String nome) {
        try {
            Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findByNome(nome);

            if (optBiblioteca.isPresent()) {
               Biblioteca __biblioteca = optBiblioteca.get();
                
                List<Emprestimo> listEmprestimos = __biblioteca.getEmprestimo();
                for (Emprestimo e : listEmprestimos)
                    emprestimoRepository.delete(e);

                List<Reserva> listReservas = __biblioteca.getReserva();
                for (Reserva r : listReservas)
                    reservaRepository.delete(r);

                List<Livro> listLivros = __biblioteca.getLivros();
                for (Livro l : listLivros)
                    livroRepository.delete(l);

                bibliotecaRepository.delete(__biblioteca);

                return new ResponseEntity<Biblioteca>(__biblioteca, HttpStatus.ACCEPTED);
            } 
            return new ResponseEntity<Biblioteca>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<Biblioteca>(HttpStatus.BAD_REQUEST);
        }
    }
    //#endregion

    /* Possibilitar adicionar/remover/consultar empréstimos (caminho “emprestimo”) e reservas (caminho “reserva”) , informando os conjuntos: Id do empréstimo, Id da reserva, Id do livro e id do usuário (caminho “porIdLivroIdUsuario”), Nome do livro e RA do usuário (caminho “porNomeLivroRAUsuario”), Nome do livro e email do usuário (caminho “porNomeLivroEmailUsuario”).
    ▪ A reserva ou empréstimo deve alterar a disponibilidade do livro. 
    ▪ Caso haja a tentativa de emprestar um livro indisponível, o empréstimo não deve ser efetuado. Uma exceção à regra é se já houver uma reserva do livro para o usuário em questão. */
        //#region Reqs de "Reservas"
        @GetMapping("/{idBib}/reserva/{id}")
        public ResponseEntity<Reserva> getReserva (@PathVariable Long idBib, @PathVariable Long id) {
            try {
                Optional<Reserva> optReserva = reservaRepository.findById(id);
                Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(idBib);

                if (optReserva.isPresent() && optReserva.get().getBiblioteca().equals(optBiblioteca.get())) {
                    return new ResponseEntity<Reserva>(optReserva.get(), HttpStatus.OK);
                } 
                return new ResponseEntity<Reserva>(HttpStatus.NO_CONTENT);

            } catch (Exception e) {
                return new ResponseEntity<Reserva>(HttpStatus.NO_CONTENT);
            }
        }
        @GetMapping("/{idBib}/reserva/porIdLivroIdUsuario/{idLivro}/{idUsuario}")
        public ResponseEntity<Reserva> getReservaPorIdLivroIdUsuario (@PathVariable Long idBib, @PathVariable Long idLivro, @PathVariable Long idUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findById(idLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);
                Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(idBib);

                if (optLivro.isPresent() && optUsuario.isPresent() && optBiblioteca.get().getLivros().contains(optLivro.get())) {
                    Livro __livro = optLivro.get();
                    Usuario __usuario = optUsuario.get();
                    Optional<Reserva> optReserva = reservaRepository.findByLivro(__livro);
                    
                    if (optReserva.isPresent() && optReserva.get().getUsuario().equals(__usuario)) {
                        return new ResponseEntity<Reserva>(optReserva.get(), HttpStatus.OK);
                    }
                }
                
                return new ResponseEntity<Reserva>(HttpStatus.NO_CONTENT);

            } catch (Exception e) {
                return new ResponseEntity<Reserva>(HttpStatus.NO_CONTENT);
            }
        }
        @GetMapping("/{idBib}/reserva/porNomeLivroRAUsuario/{nomeLivro}/{RAUsuario}")
        public ResponseEntity<Reserva> getReservaPorNomeLivroRAUsuario (@PathVariable Long idBib, @PathVariable String nomeLivro, @PathVariable String raUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findByTitulo(nomeLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findByRA(raUsuario);
                Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(idBib);

                if (optLivro.isPresent() && optUsuario.isPresent() && optBiblioteca.get().getLivros().contains(optLivro.get())) {
                    Livro __livro = optLivro.get();
                    Usuario __usuario = optUsuario.get();
                    Optional<Reserva> optReserva = reservaRepository.findByLivro(__livro);
                    
                    if (optReserva.isPresent() && optReserva.get().getUsuario().equals(__usuario)) {
                        return new ResponseEntity<Reserva>(optReserva.get(), HttpStatus.OK);
                    }
                }
                
                return new ResponseEntity<Reserva>(HttpStatus.NO_CONTENT);

            } catch (Exception e) {
                return new ResponseEntity<Reserva>(HttpStatus.NO_CONTENT);
            }
        }
        @GetMapping("/{idBib}/reserva/porNomeLivroEmailUsuario/{nomeLivro}/{emailUsuario}")
        public ResponseEntity<Reserva> getReservaPorNomeLivroEmailUsuario (@PathVariable Long idBib, @PathVariable String nomeLivro, @PathVariable String emailUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findByTitulo(nomeLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findByEmail(emailUsuario);
                Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(idBib);

                if (optLivro.isPresent() && optUsuario.isPresent() && optBiblioteca.get().getLivros().contains(optLivro.get())) {
                    Livro __livro = optLivro.get();
                    Usuario __usuario = optUsuario.get();
                    Optional<Reserva> optReserva = reservaRepository.findByLivro(__livro);
                    
                    if (optReserva.isPresent() && optReserva.get().getUsuario().equals(__usuario)) {
                        return new ResponseEntity<Reserva>(optReserva.get(), HttpStatus.OK);
                    }
                }
                
                return new ResponseEntity<Reserva>(HttpStatus.NO_CONTENT);

            } catch (Exception e) {
                return new ResponseEntity<Reserva>(HttpStatus.NO_CONTENT);
            }
        }

        @PostMapping("/{idBib}/reserva/porIdLivroIdUsuario/{idLivro}/{idUsuario}")
        public ResponseEntity<Reserva> createReservaPorIdLivroIdUsuario(@PathVariable Long idBib, @PathVariable Long idLivro, @PathVariable Long idUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findById(idLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);
                Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(idBib);

                if (optLivro.isPresent() && optUsuario.isPresent() && optBiblioteca.get().getLivros().contains(optLivro.get())) {
                    Reserva add = new Reserva(optLivro.get(), optUsuario.get(), optBiblioteca.get(), Calendar.getInstance().getTime());
                    optLivro.get().setDisponivel(false);
                    add.setLivro(livroRepository.save(optLivro.get())); 

                    optBiblioteca.get().getReserva().add(add);
                    Biblioteca novaBiblioteca = bibliotecaRepository.save(optBiblioteca.get());

                    add.setBiblioteca(novaBiblioteca);
                    //Reserva novaReserva = reservaRepository.save(add);

                    return new ResponseEntity<Reserva>(add, HttpStatus.CREATED);
                }
                return new ResponseEntity<Reserva>(HttpStatus.BAD_REQUEST);
                
            } catch (Exception e) {
                return new ResponseEntity<Reserva>(HttpStatus.BAD_REQUEST);
            }  
        }
        @PostMapping("/{idBib}/reserva/porNomeLivroRAUsuario/{nomeLivro}/{raUsuario}")
        public ResponseEntity<Reserva> createReservaPorNomeLivroRAUsuario(@PathVariable Long idBib, @PathVariable String nomeLivro, @PathVariable String raUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findByTitulo(nomeLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findByRA(raUsuario);
                Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(idBib);

                if (optLivro.isPresent() && optUsuario.isPresent() && optBiblioteca.get().getLivros().contains(optLivro.get())) {
                    Reserva add = new Reserva(optLivro.get(), optUsuario.get(), optBiblioteca.get(), Calendar.getInstance().getTime());
                    optLivro.get().setDisponivel(false);
                    livroRepository.save(optLivro.get());         

                    optBiblioteca.get().getReserva().add(add);
                    Biblioteca novaBiblioteca = bibliotecaRepository.save(optBiblioteca.get());

                    add.setBiblioteca(novaBiblioteca);
                    //Reserva novaReserva = reservaRepository.save(add);

                    return new ResponseEntity<Reserva>(add, HttpStatus.CREATED);
                }
                return new ResponseEntity<Reserva>(HttpStatus.BAD_REQUEST);

            } catch (Exception e) {
                return new ResponseEntity<Reserva>(HttpStatus.BAD_REQUEST);
            }  
        }
        @PostMapping("/{idBib}/reserva/porNomeLivroEmailUsuario/{nomeLivro}/{emailUsuario}")
        public ResponseEntity<Reserva> createReservaPorNomeLivroEmailUsuario(@PathVariable Long idBib, @PathVariable String nomeLivro, @PathVariable String emailUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findByTitulo(nomeLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findByEmail(emailUsuario);
                Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(idBib);

                if (optLivro.isPresent() && optUsuario.isPresent() && optBiblioteca.get().getLivros().contains(optLivro.get())) {
                    Reserva add = new Reserva(optLivro.get(), optUsuario.get(), optBiblioteca.get(), Calendar.getInstance().getTime());
                    optLivro.get().setDisponivel(false);
                    livroRepository.save(optLivro.get());  
                    Reserva novaReserva = reservaRepository.save(add);
                    return new ResponseEntity<Reserva>(novaReserva, HttpStatus.CREATED);
                }
                return new ResponseEntity<Reserva>(HttpStatus.BAD_REQUEST);
                
            } catch (Exception e) {
                return new ResponseEntity<Reserva>(HttpStatus.BAD_REQUEST);
            }  
        }

        @DeleteMapping("/{idBib}/reserva/{id}")
        public ResponseEntity<Reserva> deleteReservaPorId (@PathVariable Long idBib, @PathVariable Long id) {
            try {
                Optional<Reserva> optReserva = reservaRepository.findById(id);
                Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(idBib);

                if (optReserva.isPresent() && optReserva.get().getBiblioteca().equals(optBiblioteca.get())) {
                    Livro _livro = optReserva.get().getLivro();
                    if (_livro != null && emprestimoRepository.findByLivro(_livro).isEmpty()) {
                        _livro.setDisponivel(true);
                        livroRepository.save(_livro);
                    }
                    Reserva _reserva = optReserva.get();

                    optBiblioteca.get().getReserva().remove(_reserva);
                    Biblioteca novaBiblioteca = bibliotecaRepository.save(optBiblioteca.get());

                    _reserva.setBiblioteca(novaBiblioteca);
                    
                    reservaRepository.delete(_reserva);
                    return new ResponseEntity<Reserva>(_reserva, HttpStatus.ACCEPTED);
                }
                return new ResponseEntity<Reserva>(HttpStatus.BAD_REQUEST);

            } catch (Exception e) {
                return new ResponseEntity<Reserva>(HttpStatus.BAD_REQUEST);
            }
        }
        @DeleteMapping("/{idBib}/reserva/porIdLivroIdUsuario/{idLivro}/{idUsuario}")
        public ResponseEntity<Reserva> deleteReservaPorIdLivroIdUsuario (@PathVariable Long idBib, @PathVariable Long idLivro, @PathVariable Long idUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findById(idLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);
                Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(idBib);

                if (optLivro.isPresent() && optUsuario.isPresent() && optBiblioteca.get().getLivros().contains(optLivro.get())) {
                    Optional<Reserva> optReserva = reservaRepository.findByLivro(optLivro.get());

                    if (optReserva.isPresent() && optReserva.get().getUsuario().equals(optUsuario.get())) {
                        Livro _livro = optLivro.get();
                        if (emprestimoRepository.findByLivro(_livro).isEmpty()) {
                            _livro.setDisponivel(true);
                            livroRepository.save(_livro);
                        }
                        Reserva _reserva = optReserva.get();
                        
                        reservaRepository.delete(_reserva);
                        return new ResponseEntity<Reserva>(_reserva, HttpStatus.ACCEPTED);
                    }
                }
                return new ResponseEntity<Reserva>(HttpStatus.BAD_REQUEST);
            
            } catch (Exception e) {
                return new ResponseEntity<Reserva>(HttpStatus.BAD_REQUEST);
            }
        }
        @DeleteMapping("/{idBib}/reserva/porNomeLivroRAUsuario/{nomeLivro}/{raUsuario}")
        public ResponseEntity<Reserva> deleteReservaPorNomeLivroRAUsuario (@PathVariable Long idBib, @PathVariable String nomeLivro, @PathVariable String raUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findByTitulo(nomeLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findByRA(raUsuario);
                Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(idBib);

                if (optLivro.isPresent() && optUsuario.isPresent() && optBiblioteca.get().getLivros().contains(optLivro.get())) {
                    Optional<Reserva> optReserva = reservaRepository.findByLivro(optLivro.get());

                    if (optReserva.isPresent() && optReserva.get().getUsuario().equals(optUsuario.get())) {
                        Livro _livro = optLivro.get();
                        if (emprestimoRepository.findByLivro(_livro).isEmpty()) {
                            _livro.setDisponivel(true);
                            livroRepository.save(_livro);
                        }
                        Reserva _reserva = optReserva.get();

                        reservaRepository.delete(_reserva); 
                        
                        return new ResponseEntity<Reserva>(_reserva, HttpStatus.ACCEPTED);
                    }
                }
                return new ResponseEntity<Reserva>(HttpStatus.BAD_REQUEST);

            } catch (Exception e) {
                return new ResponseEntity<Reserva>(HttpStatus.BAD_REQUEST);
            }
        }
        @DeleteMapping("/{idBib}/reserva/porNomeLivroEmailUsuario/{nomeLivro}/{emailUsuario}")
        public ResponseEntity<Reserva> deleteReservaPorNomeLivroEmailUsuario (@PathVariable Long idBib, @PathVariable String nomeLivro, @PathVariable String emailUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findByTitulo(nomeLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findByEmail(emailUsuario);
                Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(idBib);

                if (optLivro.isPresent() && optUsuario.isPresent() && optBiblioteca.get().getLivros().contains(optLivro.get())) {
                    Optional<Reserva> optReserva = reservaRepository.findByLivro(optLivro.get());

                    if (optReserva.isPresent() && optReserva.get().getUsuario().equals(optUsuario.get())) {
                        Livro _livro = optLivro.get();
                        if (emprestimoRepository.findByLivro(_livro).isEmpty()) {
                            _livro.setDisponivel(true);
                            livroRepository.save(_livro);
                        }
                        Reserva _reserva = optReserva.get();

                        Biblioteca __biblioteca = optBiblioteca.get();
                        __biblioteca.getReserva().remove(_reserva);
                        bibliotecaRepository.save(__biblioteca);

                        reservaRepository.delete(_reserva); 
                        
                        return new ResponseEntity<Reserva>(_reserva, HttpStatus.ACCEPTED);
                    }
                }
                return new ResponseEntity<Reserva>(HttpStatus.BAD_REQUEST);

            } catch (Exception e) {
                return new ResponseEntity<Reserva>(HttpStatus.BAD_REQUEST);
            }
        }
        //#endregion
        //#region Reqs de "Emprestimos"
        @GetMapping("/{idBib}/emprestimo/{id}")
        public ResponseEntity<Emprestimo> getEmprestimo (@PathVariable Long  idBib, @PathVariable Long id) {
            try {
                Optional<Emprestimo> optEmprestimo = emprestimoRepository.findById(id);
                Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(idBib);

                if (optEmprestimo.isPresent() && optEmprestimo.get().getBiblioteca().equals(optBiblioteca.get())) {                
                    return new ResponseEntity<Emprestimo>(optEmprestimo.get(), HttpStatus.OK);
                } 
                return new ResponseEntity<Emprestimo>(HttpStatus.NO_CONTENT);

            } catch (Exception e) {
                return new ResponseEntity<Emprestimo>(HttpStatus.NO_CONTENT);
            }
        }
        @GetMapping("/{idBib}/emprestimo/porIdLivroIdUsuario/{idLivro}/{idUsuario}")
        public ResponseEntity<Emprestimo> getEmprestimoPorIdLivroIdUsuario (@PathVariable Long  idBib, @PathVariable Long idLivro, @PathVariable Long idUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findById(idLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);
                Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(idBib);

                if (optLivro.isPresent() && optUsuario.isPresent() && optBiblioteca.get().getLivros().contains(optLivro.get())) {
                    Livro _livro = optLivro.get();
                    Usuario _usuario = optUsuario.get();
                    Optional<Emprestimo> optEmprestimo = emprestimoRepository.findByLivro(_livro);            

                    if (optEmprestimo.get().getUsuario().equals(_usuario)) {
                        return new ResponseEntity<Emprestimo>(optEmprestimo.get(), HttpStatus.OK);
                    }
                }                
                return new ResponseEntity<Emprestimo>(HttpStatus.NO_CONTENT);

            } catch (Exception e) {
                return new ResponseEntity<Emprestimo>(HttpStatus.NO_CONTENT);
            }
        }
        @GetMapping("/{idBib}/emprestimo/porNomeLivroRAUsuario/{nomeLivro}/{RAUsuario}")
        public ResponseEntity<Emprestimo> getEmprestimoPorNomeLivroRAUsuario (@PathVariable Long  idBib, @PathVariable String nomeLivro, @PathVariable String raUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findByTitulo(nomeLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findByRA(raUsuario);
                Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(idBib);

                if (optLivro.isPresent() && optUsuario.isPresent() && optBiblioteca.get().getLivros().contains(optLivro.get())) {
                    Livro _livro = optLivro.get();
                    Usuario _usuario = optUsuario.get();
                    Optional<Emprestimo> optEmprestimo = emprestimoRepository.findByLivro(_livro);            

                    if (optEmprestimo.get().getUsuario().equals(_usuario)) {
                        return new ResponseEntity<Emprestimo>(optEmprestimo.get(), HttpStatus.OK);
                    }
                }                
                return new ResponseEntity<Emprestimo>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<Emprestimo>(HttpStatus.NO_CONTENT);
            }
        }
        @GetMapping("/{idBib}/emprestimo/porNomeLivroEmailUsuario/{nomeLivro}/{emailUsuario}")
        public ResponseEntity<Emprestimo> getEmprestimoPorNomeLivroEmailUsuario (@PathVariable Long  idBib, @PathVariable String nomeLivro, @PathVariable String emailUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findByTitulo(nomeLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findByEmail(emailUsuario);
                Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(idBib);

                if (optLivro.isPresent() && optUsuario.isPresent() && optBiblioteca.get().getLivros().contains(optLivro.get())) {
                    Livro _livro = optLivro.get();
                    Usuario _usuario = optUsuario.get();
                    Optional<Emprestimo> optEmprestimo = emprestimoRepository.findByLivro(_livro);            

                    if (optEmprestimo.get().getUsuario().equals(_usuario)) {
                        return new ResponseEntity<Emprestimo>(optEmprestimo.get(), HttpStatus.OK);
                    }
                }                
                return new ResponseEntity<Emprestimo>(HttpStatus.NO_CONTENT);

            } catch (Exception e) {
                return new ResponseEntity<Emprestimo>(HttpStatus.NO_CONTENT);
            }
        }

        @PostMapping("/{idBib}/emprestimo/porIdLivroIdUsuario/{idLivro}/{idUsuario}")
        public ResponseEntity<Emprestimo> createEmprestimoPorIdLivroIdUsuario(@PathVariable Long  idBib, @PathVariable Long idLivro, @PathVariable Long idUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findById(idLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);
                Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(idBib);

                if (optLivro.isPresent() && optUsuario.isPresent() && optBiblioteca.get().getLivros().contains(optLivro.get())) {
                    Livro __livro = optLivro.get();
                    Usuario __usuario = optUsuario.get(); 
                    Biblioteca _biblioteca = optBiblioteca.get();                   
                    if (__livro.getDisponivel()) {
                        Emprestimo add = new Emprestimo(__livro, __usuario, _biblioteca, Calendar.getInstance().getTime());
                        add.getLivro().setDisponivel(false);
                        add.setLivro(livroRepository.save(add.getLivro()));
                        add.setUsuario(usuarioRepository.save(add.getUsuario()));
                        Emprestimo novoEmprestimo = emprestimoRepository.save(add);
                        return new ResponseEntity<Emprestimo>(novoEmprestimo, HttpStatus.CREATED);
                    }
                    Optional<Emprestimo> optEmprestimo = emprestimoRepository.findByLivro(__livro);
                    if (optEmprestimo.isEmpty()) {
                        Optional<Reserva> optReserva = reservaRepository.findByLivro(__livro);
                        if (optReserva.isPresent() && optReserva.get().getUsuario().equals(__usuario)) {
                            Emprestimo add = new Emprestimo(__livro, __usuario, _biblioteca, Calendar.getInstance().getTime());
                            reservaRepository.delete(optReserva.get());
                            add.getLivro().setDisponivel(false);
                            add.setLivro(livroRepository.save(add.getLivro()));
                            add.setUsuario(usuarioRepository.save(add.getUsuario()));                            
                            Emprestimo novoEmprestimo = emprestimoRepository.save(add);
                            return new ResponseEntity<Emprestimo>(novoEmprestimo, HttpStatus.CREATED);
                        }
                    }
                }
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);
            }  
        }
        @PostMapping("/{idBib}/emprestimo/porNomeLivroRAUsuario/{nomeLivro}/{raUsuario}")
        public ResponseEntity<Emprestimo> createEmprestimoPorNomeLivroRAUsuario(@PathVariable Long  idBib, @PathVariable String nomeLivro, @PathVariable String raUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findByTitulo(nomeLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findByRA(raUsuario);
                Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(idBib);

                if (optLivro.isPresent() && optUsuario.isPresent() && optBiblioteca.get().getLivros().contains(optLivro.get())) {
                    Livro __livro = optLivro.get();
                    Usuario __usuario = optUsuario.get(); 
                    Biblioteca _biblioteca = optBiblioteca.get();                   
                    if (__livro.getDisponivel()) {
                        Emprestimo add = new Emprestimo(__livro, __usuario, _biblioteca, Calendar.getInstance().getTime());
                        add.getLivro().setDisponivel(false);
                        add.setLivro(livroRepository.save(add.getLivro()));
                        add.setUsuario(usuarioRepository.save(add.getUsuario()));
                        Emprestimo novoEmprestimo = emprestimoRepository.save(add);
                        return new ResponseEntity<Emprestimo>(novoEmprestimo, HttpStatus.CREATED);
                    }
                    Optional<Emprestimo> optEmprestimo = emprestimoRepository.findByLivro(__livro);
                    if (optEmprestimo.isEmpty()) {
                        Optional<Reserva> optReserva = reservaRepository.findByLivro(__livro);
                        if (optReserva.isPresent() && optReserva.get().getUsuario().equals(__usuario)) {
                            Emprestimo add = new Emprestimo(__livro, __usuario, _biblioteca, Calendar.getInstance().getTime());
                            reservaRepository.delete(optReserva.get());
                            add.getLivro().setDisponivel(false);
                            add.setLivro(livroRepository.save(add.getLivro()));
                            add.setUsuario(usuarioRepository.save(add.getUsuario()));
                            Emprestimo novoEmprestimo = emprestimoRepository.save(add);
                            return new ResponseEntity<Emprestimo>(novoEmprestimo, HttpStatus.CREATED);
                        }
                    }
                }
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);
            }  
        }
        @PostMapping("/{idBib}/emprestimo/porNomeLivroEmailUsuario/{nomeLivro}/{emailUsuario}")
        public ResponseEntity<Emprestimo> createEmprestimoPorNomeLivroEmailUsuario(@PathVariable Long  idBib, @PathVariable String nomeLivro, @PathVariable String emailUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findByTitulo(nomeLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findByEmail(emailUsuario);
                Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(idBib);
                
                if (optLivro.isPresent() && optUsuario.isPresent() && optBiblioteca.get().getLivros().contains(optLivro.get())) {
                    Livro __livro = optLivro.get();
                    Usuario __usuario = optUsuario.get(); 
                    Biblioteca _biblioteca = optBiblioteca.get();                   
                    if (__livro.getDisponivel()) {
                        Emprestimo add = new Emprestimo(__livro, __usuario, _biblioteca, Calendar.getInstance().getTime());
                        add.getLivro().setDisponivel(false);
                        add.setLivro(livroRepository.save(add.getLivro()));
                        add.setUsuario(usuarioRepository.save(add.getUsuario()));
                        Emprestimo novoEmprestimo = emprestimoRepository.save(add);
                        return new ResponseEntity<Emprestimo>(novoEmprestimo, HttpStatus.CREATED);
                    }
                    Optional<Emprestimo> optEmprestimo = emprestimoRepository.findByLivro(__livro);
                    if (optEmprestimo.isEmpty()) {
                        Optional<Reserva> optReserva = reservaRepository.findByLivro(__livro);
                        if (optReserva.isPresent() && optReserva.get().getUsuario().equals(__usuario)) {
                            Emprestimo add = new Emprestimo(__livro, __usuario, _biblioteca, Calendar.getInstance().getTime());
                            reservaRepository.delete(optReserva.get());
                            add.getLivro().setDisponivel(false);
                            add.setLivro(livroRepository.save(add.getLivro()));
                            add.setUsuario(usuarioRepository.save(add.getUsuario()));
                            Emprestimo novoEmprestimo = emprestimoRepository.save(add);
                            return new ResponseEntity<Emprestimo>(novoEmprestimo, HttpStatus.CREATED);
                        }
                    }
                }
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);
            }  
        }

        @DeleteMapping("/{idBib}/emprestimo/{id}")
        public ResponseEntity<Emprestimo> deleteEmprestimoPorId (@PathVariable Long  idBib, @PathVariable Long id) {
            try {
                Optional<Emprestimo> optEmprestimo = emprestimoRepository.findById(id);
                Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(idBib);

                if (optEmprestimo.isPresent() && optEmprestimo.get().getBiblioteca().equals(optBiblioteca.get())) {
                    Livro _livro = optEmprestimo.get().getLivro();
                    if (reservaRepository.findByLivro(_livro).isEmpty()) {
                        _livro.setDisponivel(true);
                        livroRepository.save(_livro);
                    }
                    Emprestimo _emprestimo = optEmprestimo.get();

                    optBiblioteca.get().getEmprestimo().remove(_emprestimo);
                    Biblioteca novaBiblioteca = bibliotecaRepository.save(optBiblioteca.get());

                    _emprestimo.setBiblioteca(novaBiblioteca);
                    
                    emprestimoRepository.delete(_emprestimo);                
                    return new ResponseEntity<Emprestimo>(_emprestimo, HttpStatus.ACCEPTED);
                } 
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);

            } catch (Exception e) {
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);
            }
        }
        @DeleteMapping("/{idBib}/emprestimo/porIdLivroIdUsuario/{idLivro}/{idUsuario}")
        public ResponseEntity<Emprestimo> deleteEmprestimoPorIdLivroIdUsuario (@PathVariable Long  idBib, @PathVariable Long idLivro, @PathVariable Long idUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findById(idLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);
                Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(idBib);

                if (optLivro.isPresent() && optUsuario.isPresent() && optBiblioteca.get().getLivros().contains(optLivro.get())) {
                    Livro _livro = optLivro.get();
                    Usuario _usuario = optUsuario.get();
                    Optional<Emprestimo> optEmprestimo = emprestimoRepository.findByLivro(_livro);
                    if (optEmprestimo.isPresent() && optEmprestimo.get().getUsuario().equals(_usuario)) {
                        if (reservaRepository.findByLivro(_livro).isEmpty()) {
                            _livro.setDisponivel(true);
                            livroRepository.save(_livro);
                            }
                        Emprestimo _emprestimo = optEmprestimo.get();

                        optBiblioteca.get().getEmprestimo().remove(_emprestimo);
                        Biblioteca novaBiblioteca = bibliotecaRepository.save(optBiblioteca.get());

                        _emprestimo.setBiblioteca(novaBiblioteca);
                        
                        emprestimoRepository.delete(_emprestimo);                 
                        return new ResponseEntity<Emprestimo>(optEmprestimo.get(), HttpStatus.ACCEPTED);
                    } 
                }
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);

            } catch (Exception e) {
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);
            }
        }
        @DeleteMapping("/{idBib}/emprestimo/porNomeLivroRAUsuario/{nomeLivro}/{raUsuario}")
        public ResponseEntity<Emprestimo> deleteEmprestimoPorNomeLivroRAUsuario (@PathVariable Long  idBib, @PathVariable String nomeLivro, @PathVariable String raUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findByTitulo(nomeLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findByRA(raUsuario);
                Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(idBib);

                if (optLivro.isPresent() && optUsuario.isPresent() && optBiblioteca.get().getLivros().contains(optLivro.get())) {
                    Livro _livro = optLivro.get();
                    Usuario _usuario = optUsuario.get();
                    Optional<Emprestimo> optEmprestimo = emprestimoRepository.findByLivro(_livro);
                    if (optEmprestimo.isPresent() && optEmprestimo.get().getUsuario().equals(_usuario)) {
                        if (reservaRepository.findByLivro(_livro).isEmpty()) {
                            _livro.setDisponivel(true);
                            livroRepository.save(_livro);
                        }
                        Emprestimo _emprestimo = optEmprestimo.get();

                        optBiblioteca.get().getEmprestimo().remove(_emprestimo);
                        Biblioteca novaBiblioteca = bibliotecaRepository.save(optBiblioteca.get());

                        _emprestimo.setBiblioteca(novaBiblioteca);
                        
                        emprestimoRepository.delete(_emprestimo);                
                        return new ResponseEntity<Emprestimo>(optEmprestimo.get(), HttpStatus.ACCEPTED);
                    } 
                }
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);

            } catch (Exception e) {
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);
            }
        }
        @DeleteMapping("/{idBib}/emprestimo/porNomeLivroEmailUsuario/{nomeLivro}/{emailUsuario}")
        public ResponseEntity<Emprestimo> deleteEmprestimoPorNomeLivroEmailUsuario (@PathVariable Long  idBib, @PathVariable String nomeLivro, @PathVariable String emailUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findByTitulo(nomeLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findByEmail(emailUsuario);
                Optional<Biblioteca> optBiblioteca = bibliotecaRepository.findById(idBib);

                if (optLivro.isPresent() && optUsuario.isPresent() && optBiblioteca.get().getLivros().contains(optLivro.get())) {
                    Livro _livro = optLivro.get();
                    Usuario _usuario = optUsuario.get();
                    Optional<Emprestimo> optEmprestimo = emprestimoRepository.findByLivro(_livro);
                    if (optEmprestimo.isPresent() && optEmprestimo.get().getUsuario().equals(_usuario)) {
                        if (reservaRepository.findByLivro(_livro).isEmpty()) {
                            _livro.setDisponivel(true);
                            livroRepository.save(_livro);
                        }
                        Emprestimo _emprestimo = optEmprestimo.get();

                        optBiblioteca.get().getEmprestimo().remove(_emprestimo);
                        Biblioteca novaBiblioteca = bibliotecaRepository.save(optBiblioteca.get());

                        _emprestimo.setBiblioteca(novaBiblioteca);
                        
                        emprestimoRepository.delete(_emprestimo);                
                        return new ResponseEntity<Emprestimo>(optEmprestimo.get(), HttpStatus.ACCEPTED);
                    } 
                }
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);

            } catch (Exception e) {
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);
            }
        }    
        //#endregion
}
