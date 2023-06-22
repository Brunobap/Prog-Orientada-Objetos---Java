package com.biblioteca.biblioteca.Controllers;

import java.util.Calendar;
import java.util.List;
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

import com.biblioteca.biblioteca.Models.Emprestimo;
import com.biblioteca.biblioteca.Models.Livro;
import com.biblioteca.biblioteca.Models.Reserva;
import com.biblioteca.biblioteca.Models.Usuario;
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
    ReservaRepository reservaRepository;
    @Autowired
    EmprestimoRepository emprestimoRepository;

    // Possibilitar adicionar/remover/consultar livros (caminho “livro”), utilizando os atributos id ou título (caminho “porTitulo”)
    //#region Requerimentos de "Livros"
    @GetMapping("/livro/{id}")
    public ResponseEntity<Livro> getLivro (@PathVariable Long id) {
        try {
            Optional<Livro> optLivro = livroRepository.findById(id);

            if (optLivro.isPresent()) {                
                return new ResponseEntity<Livro>(optLivro.get(), HttpStatus.OK);
            } 
            return new ResponseEntity<Livro>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<Livro>(HttpStatus.NO_CONTENT);
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
            return new ResponseEntity<Livro>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/livro/")
    public ResponseEntity<Livro> createLivro(@RequestBody Livro livro) {
        try {
            Livro add = new Livro(livro.getId(), livro.getTitulo(), livro.getAutor(), livro.getAnoPublicacao(), livro.getDisponivel());
            if (add.getDisponivel() == null) add.setDisponivel(true);
            Livro __livro = livroRepository.save(add);
            return new ResponseEntity<Livro>(__livro, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Livro>(HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity<Livro>(HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity<Livro>(HttpStatus.BAD_REQUEST);
        }
    }//#endregion

    /* Possibilitar adicionar/remover/consultar empréstimos (caminho “emprestimo”) e reservas (caminho “reserva”) , informando os conjuntos: Id do empréstimo, Id da reserva, Id do livro e id do usuário (caminho “porIdLivroIdUsuario”), Nome do livro e RA do usuário (caminho “porNomeLivroRAUsuario”), Nome do livro e email do usuário (caminho “porNomeLivroEmailUsuario”).
    ▪ A reserva ou empréstimo deve alterar a disponibilidade do livro. 
    ▪ Caso haja a tentativa de emprestar um livro indisponível, o empréstimo não deve ser efetuado. Uma exceção à regra é se já houver uma reserva do livro para o usuário em questão. */
        //#region Reqs de "Reservas"
        @GetMapping("/reserva/{id}")
        public ResponseEntity<Reserva> getReserva (@PathVariable Long id) {
            try {
                Optional<Reserva> optReserva = reservaRepository.findById(id);

                if (optReserva.isPresent()) {                
                    return new ResponseEntity<Reserva>(optReserva.get(), HttpStatus.OK);
                } 
                return new ResponseEntity<Reserva>(HttpStatus.NO_CONTENT);

            } catch (Exception e) {
                return new ResponseEntity<Reserva>(HttpStatus.NO_CONTENT);
            }
        }
        @GetMapping("/reserva/porIdLivroIdUsuario/{idLivro}/{idUsuario}")
        public ResponseEntity<Reserva> getReservaPorIdLivroIdUsuario (@PathVariable Long idLivro, @PathVariable Long idUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findById(idLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);

                if (optLivro.isPresent() && optUsuario.isPresent()) {
                    List<Reserva> allReservas = reservaRepository.findAll();            

                    for (Reserva reserva : allReservas) {
                        if ((reserva.getLivro().getId() == idLivro) && (reserva.getUsuario().getId() == idUsuario)) {
                            return new ResponseEntity<Reserva>(reserva, HttpStatus.OK);
                        }
                    }
                }
                
                return new ResponseEntity<Reserva>(HttpStatus.NO_CONTENT);

            } catch (Exception e) {
                return new ResponseEntity<Reserva>(HttpStatus.NO_CONTENT);
            }
        }
        @GetMapping("/reserva/porNomeLivroRAUsuario/{nomeLivro}/{RAUsuario}")
        public ResponseEntity<Reserva> getReservaPorNomeLivroRAUsuario (@PathVariable String nomeLivro, @PathVariable String raUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findByTitulo(nomeLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findByRA(raUsuario);

                if (optLivro.isPresent() && optUsuario.isPresent()) {
                    List<Reserva> allReservas = reservaRepository.findAll();            

                    for (Reserva reserva : allReservas) {
                        if (reserva.getLivro().getTitulo().equals(nomeLivro) && reserva.getUsuario().getRA().equals(raUsuario)) {
                            return new ResponseEntity<Reserva>(reserva, HttpStatus.OK);
                        }
                    }
                }
                
                return new ResponseEntity<Reserva>(HttpStatus.NO_CONTENT);

            } catch (Exception e) {
                return new ResponseEntity<Reserva>(HttpStatus.NO_CONTENT);
            }
        }
        @GetMapping("/reserva/porNomeLivroEmailUsuario/{nomeLivro}/{emailUsuario}")
        public ResponseEntity<Reserva> getReservaPorNomeLivroEmailUsuario (@PathVariable String nomeLivro, @PathVariable String emailUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findByTitulo(nomeLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findByEmail(emailUsuario);

                if (optLivro.isPresent() && optUsuario.isPresent()) {
                    List<Reserva> allReservas = reservaRepository.findAll();            

                    for (Reserva reserva : allReservas) {
                        if (reserva.getLivro().getTitulo().equals(nomeLivro) && reserva.getUsuario().getEmail().equals(emailUsuario)) {
                            return new ResponseEntity<Reserva>(reserva, HttpStatus.OK);
                        }
                    }
                }
                
                return new ResponseEntity<Reserva>(HttpStatus.NO_CONTENT);

            } catch (Exception e) {
                return new ResponseEntity<Reserva>(HttpStatus.NO_CONTENT);
            }
        }

        @PostMapping("/reserva/porIdLivroIdUsuario/{idLivro}/{idUsuario}")
        public ResponseEntity<Reserva> createReservaPorIdLivroIdUsuario(@PathVariable Long idLivro, @PathVariable Long idUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findById(idLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);

                Reserva add = new Reserva(optLivro.get(), optUsuario.get(), Calendar.getInstance().getTime());
                add.getLivro().setDisponivel(false);
                Reserva novaReserva = reservaRepository.save(add);
                return new ResponseEntity<Reserva>(novaReserva, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<Reserva>(HttpStatus.NO_CONTENT);
            }  
        }
        @PostMapping("/reserva/porNomeLivroRAUsuario/{nomeLivro}/{raUsuario}")
        public ResponseEntity<Reserva> createReservaPorNomeLivroRAUsuario(@PathVariable String nomeLivro, @PathVariable String raUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findByTitulo(nomeLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findByRA(raUsuario);
                Reserva add = new Reserva(optLivro.get(), optUsuario.get(), Calendar.getInstance().getTime());
                add.getLivro().setDisponivel(false);
                Reserva novaReserva = reservaRepository.save(add);
                return new ResponseEntity<Reserva>(novaReserva, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<Reserva>(HttpStatus.NO_CONTENT);
            }  
        }
        @PostMapping("/reserva/porNomeLivroEmailUsuario/{nomeLivro}/{emailUsuario}")
        public ResponseEntity<Reserva> createReservaPorNomeLivroEmailUsuario(@PathVariable String nomeLivro, @PathVariable String emailUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findByTitulo(nomeLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findByEmail(emailUsuario);
                Reserva add = new Reserva(optLivro.get(), optUsuario.get(), Calendar.getInstance().getTime());
                add.getLivro().setDisponivel(false);
                Reserva novaReserva = reservaRepository.save(add);
                return new ResponseEntity<Reserva>(novaReserva, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<Reserva>(HttpStatus.NO_CONTENT);
            }  
        }

        @DeleteMapping("/reserva/{id}")
        public ResponseEntity<Reserva> deleteReservaPorId (@PathVariable Long id) {
            try {
                Optional<Reserva> optReserva = reservaRepository.findById(id);

                if (optReserva.isPresent()) { 
                    Livro _livro = optReserva.get().getLivro();
                    if (reservaRepository.findAllByLivro(_livro).isEmpty() && emprestimoRepository.findByLivro(_livro).isEmpty()) {
                        _livro.setDisponivel(true);
                        livroRepository.save(_livro);
                    }
                    reservaRepository.deleteById(id);                
                    return new ResponseEntity<Reserva>(optReserva.get(), HttpStatus.ACCEPTED);
                } 
                return new ResponseEntity<Reserva>(HttpStatus.BAD_REQUEST);

            } catch (Exception e) {
                return new ResponseEntity<Reserva>(HttpStatus.NO_CONTENT);
            }
        }
        @DeleteMapping("/reserva/porIdLivroIdUsuario/{idLivro}/{idUsuario}")
        public ResponseEntity<Reserva> deleteReservaPorIdLivroIdUsuario (@PathVariable Long idLivro, @PathVariable Long idUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findById(idLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);

                if (optLivro.isPresent() && optUsuario.isPresent()) {
                    List<Reserva> allReservas = reservaRepository.findAll();
                    for (Reserva reserva : allReservas) {
                        if (reserva.getLivro().getId() == idLivro && reserva.getUsuario().getId() == idUsuario) {
                            Livro _livro = optLivro.get();
                            if (reservaRepository.findAllByLivro(_livro).isEmpty() && emprestimoRepository.findByLivro(_livro).isEmpty()) {
                                _livro.setDisponivel(true);
                                livroRepository.save(_livro);
                            }
                            reservaRepository.delete(reserva);
                            return new ResponseEntity<Reserva>(reserva, HttpStatus.OK);
                        }
                    }
                }
                return new ResponseEntity<Reserva>(HttpStatus.BAD_REQUEST);

            } catch (Exception e) {
                return new ResponseEntity<Reserva>(HttpStatus.NO_CONTENT);
            }
        }
        @DeleteMapping("/reserva/porNomeLivroRAUsuario/{nomeLivro}/{raUsuario}")
        public ResponseEntity<Reserva> deleteReservaPorNomeLivroRAUsuario (@PathVariable String nomeLivro, @PathVariable String raUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findByTitulo(nomeLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findByRA(raUsuario);

                if (optLivro.isPresent() && optUsuario.isPresent()) {
                    List<Reserva> allReservas = reservaRepository.findAll();
                    for (Reserva reserva : allReservas) {
                        if (reserva.getLivro().getTitulo().equals(nomeLivro) && reserva.getUsuario().getRA().equals(raUsuario)) {
                            Livro _livro = optLivro.get();
                            if (reservaRepository.findAllByLivro(_livro).isEmpty() && emprestimoRepository.findByLivro(_livro).isEmpty()) {
                                _livro.setDisponivel(true);
                                livroRepository.save(_livro);
                            }
                            reservaRepository.delete(reserva);
                            return new ResponseEntity<Reserva>(reserva, HttpStatus.OK);
                        }
                    }
                }
                return new ResponseEntity<Reserva>(HttpStatus.BAD_REQUEST);

            } catch (Exception e) {
                return new ResponseEntity<Reserva>(HttpStatus.NO_CONTENT);
            }
        }
        @DeleteMapping("/reserva/porNomeLivroEmailUsuario/{nomeLivro}/{emailUsuario}")
        public ResponseEntity<Reserva> deleteReservaPorNomeLivroEmailUsuario (@PathVariable String nomeLivro, @PathVariable String emailUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findByTitulo(nomeLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findByEmail(emailUsuario);

                if (optLivro.isPresent() && optUsuario.isPresent()) {
                    List<Reserva> allReservas = reservaRepository.findAll();
                    for (Reserva reserva : allReservas) {
                        if (reserva.getLivro().getTitulo().equals(nomeLivro) && reserva.getUsuario().getEmail().equals(emailUsuario)) {
                            Livro _livro = optLivro.get();
                            if (reservaRepository.findAllByLivro(_livro).isEmpty() && emprestimoRepository.findByLivro(_livro).isEmpty()) {
                                _livro.setDisponivel(true);
                                livroRepository.save(_livro);
                            }
                            reservaRepository.delete(reserva);
                            return new ResponseEntity<Reserva>(reserva, HttpStatus.OK);
                        }
                    }
                }
                return new ResponseEntity<Reserva>(HttpStatus.BAD_REQUEST);

            } catch (Exception e) {
                return new ResponseEntity<Reserva>(HttpStatus.NO_CONTENT);
            }
        }
        //#endregion
        //#region Reqs de "Emprestimos"
        @GetMapping("/emprestimo/{id}")
        public ResponseEntity<Emprestimo> getEmprestimo (@PathVariable Long id) {
            try {
                Optional<Emprestimo> optEmprestimo = emprestimoRepository.findById(id);

                if (optEmprestimo.isPresent()) {                
                    return new ResponseEntity<Emprestimo>(optEmprestimo.get(), HttpStatus.OK);
                } 
                return new ResponseEntity<Emprestimo>(HttpStatus.NO_CONTENT);

            } catch (Exception e) {
                return new ResponseEntity<Emprestimo>(HttpStatus.NO_CONTENT);
            }
        }
        @GetMapping("/emprestimo/porIdLivroIdUsuario/{idLivro}/{idUsuario}")
        public ResponseEntity<Emprestimo> getEmprestimoPorIdLivroIdUsuario (@PathVariable Long idLivro, @PathVariable Long idUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findById(idLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);

                if (optLivro.isPresent() && optUsuario.isPresent()) {
                    List<Emprestimo> allEmprestimos = emprestimoRepository.findAll();            

                    for (Emprestimo emprestimo : allEmprestimos) {
                        if ((emprestimo.getLivro().getId() == idLivro) && (emprestimo.getUsuario().getId() == idUsuario)) {
                            return new ResponseEntity<Emprestimo>(emprestimo, HttpStatus.OK);
                        }
                    }
                }                
                return new ResponseEntity<Emprestimo>(HttpStatus.NO_CONTENT);

            } catch (Exception e) {
                return new ResponseEntity<Emprestimo>(HttpStatus.NO_CONTENT);
            }
        }
        @GetMapping("/emprestimo/porNomeLivroRAUsuario/{nomeLivro}/{RAUsuario}")
        public ResponseEntity<Emprestimo> getEmprestimoPorNomeLivroRAUsuario (@PathVariable String nomeLivro, @PathVariable String raUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findByTitulo(nomeLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findByRA(raUsuario);

                if (optLivro.isPresent() && optUsuario.isPresent()) {
                    List<Emprestimo> allEmprestimos = emprestimoRepository.findAll();            

                    for (Emprestimo emprestimo : allEmprestimos) {
                        if (emprestimo.getLivro().getTitulo().equals(nomeLivro) && emprestimo.getUsuario().getRA().equals(raUsuario)) {
                            return new ResponseEntity<Emprestimo>(emprestimo, HttpStatus.OK);
                        }
                    }
                }                
                return new ResponseEntity<Emprestimo>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<Emprestimo>(HttpStatus.NO_CONTENT);
            }
        }
        @GetMapping("/emprestimo/porNomeLivroEmailUsuario/{nomeLivro}/{emailUsuario}")
        public ResponseEntity<Emprestimo> getEmprestimoPorNomeLivroEmailUsuario (@PathVariable String nomeLivro, @PathVariable String emailUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findByTitulo(nomeLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findByEmail(emailUsuario);

                if (optLivro.isPresent() && optUsuario.isPresent()) {
                    List<Emprestimo> allEmprestimos = emprestimoRepository.findAll();            

                    for (Emprestimo emprestimo : allEmprestimos) {
                        if (emprestimo.getLivro().getTitulo().equals(nomeLivro) && emprestimo.getUsuario().getEmail().equals(emailUsuario)) {
                            return new ResponseEntity<Emprestimo>(emprestimo, HttpStatus.OK);
                        }
                    }
                }
                return new ResponseEntity<Emprestimo>(HttpStatus.NO_CONTENT);

            } catch (Exception e) {
                return new ResponseEntity<Emprestimo>(HttpStatus.NO_CONTENT);
            }
        }

        @PostMapping("/emprestimo/porIdLivroIdUsuario/{idLivro}/{idUsuario}")
        public ResponseEntity<Emprestimo> createEmprestimoPorIdLivroIdUsuario(@PathVariable Long idLivro, @PathVariable Long idUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findById(idLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);

                if (optLivro.isPresent() && optUsuario.isPresent()) {
                    Livro __livro = optLivro.get();
                    Usuario __usuario = optUsuario.get();                    
                    if (__livro.getDisponivel()) {
                        Emprestimo add = new Emprestimo(__livro, __usuario, Calendar.getInstance().getTime());
                        add.getLivro().setDisponivel(false);
                        Emprestimo novoEmprestimo = emprestimoRepository.save(add);
                        return new ResponseEntity<Emprestimo>(novoEmprestimo, HttpStatus.CREATED);
                    }
                    Optional<Emprestimo> optEmprestimo = emprestimoRepository.findByLivro(__livro);
                    if (optEmprestimo.isEmpty()) {
                        Optional<List<Reserva>> optListReservas = reservaRepository.findAllByLivro(__livro);
                        for (Reserva reserva : optListReservas.get()) {
                            if (reserva.getUsuario().equals(__usuario)) {                                
                                Emprestimo add = new Emprestimo(__livro, __usuario, Calendar.getInstance().getTime());
                                add.getLivro().setDisponivel(false);
                                Emprestimo novoEmprestimo = emprestimoRepository.save(add);
                                return new ResponseEntity<Emprestimo>(novoEmprestimo, HttpStatus.CREATED);
                            }
                        }
                    }
                }
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);
            }  
        }
        @PostMapping("/emprestimo/porNomeLivroRAUsuario/{nomeLivro}/{raUsuario}")
        public ResponseEntity<Emprestimo> createEmprestimoPorNomeLivroRAUsuario(@PathVariable String nomeLivro, @PathVariable String raUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findByTitulo(nomeLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findByRA(raUsuario);

                if (optLivro.isPresent() && optUsuario.isPresent()) {
                    Livro __livro = optLivro.get();
                    Usuario __usuario = optUsuario.get();                    
                    if (__livro.getDisponivel()) {
                        Emprestimo add = new Emprestimo(__livro, __usuario, Calendar.getInstance().getTime());
                        add.getLivro().setDisponivel(false);
                        Emprestimo novoEmprestimo = emprestimoRepository.save(add);
                        return new ResponseEntity<Emprestimo>(novoEmprestimo, HttpStatus.CREATED);
                    }
                    Optional<Emprestimo> optEmprestimo = emprestimoRepository.findByLivro(__livro);
                    if (optEmprestimo.isEmpty()) {
                        Optional<List<Reserva>> optListReservas = reservaRepository.findAllByLivro(__livro);
                        for (Reserva reserva : optListReservas.get()) {
                            if (reserva.getUsuario().equals(__usuario)) {                                
                                Emprestimo add = new Emprestimo(__livro, __usuario, Calendar.getInstance().getTime());
                                add.getLivro().setDisponivel(false);
                                Emprestimo novoEmprestimo = emprestimoRepository.save(add);
                                return new ResponseEntity<Emprestimo>(novoEmprestimo, HttpStatus.CREATED);
                            }
                        }
                    }
                }
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);
            }  
        }
        @PostMapping("/emprestimo/porNomeLivroEmailUsuario/{nomeLivro}/{emailUsuario}")
        public ResponseEntity<Emprestimo> createEmprestimoPorNomeLivroEmailUsuario(@PathVariable String nomeLivro, @PathVariable String emailUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findByTitulo(nomeLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findByEmail(emailUsuario);
                
                if (optLivro.isPresent() && optUsuario.isPresent()) {
                    Livro __livro = optLivro.get();
                    Usuario __usuario = optUsuario.get();                    
                    if (__livro.getDisponivel()) {
                        Emprestimo add = new Emprestimo(__livro, __usuario, Calendar.getInstance().getTime());
                        add.getLivro().setDisponivel(false);
                        Emprestimo novoEmprestimo = emprestimoRepository.save(add);
                        return new ResponseEntity<Emprestimo>(novoEmprestimo, HttpStatus.CREATED);
                    }
                    Optional<Emprestimo> optEmprestimo = emprestimoRepository.findByLivro(__livro);
                    if (optEmprestimo.isEmpty()) {
                        Optional<List<Reserva>> optListReservas = reservaRepository.findAllByLivro(__livro);
                        for (Reserva reserva : optListReservas.get()) {
                            if (reserva.getUsuario().equals(__usuario)) {                                
                                Emprestimo add = new Emprestimo(__livro, __usuario, Calendar.getInstance().getTime());
                                add.getLivro().setDisponivel(false);
                                Emprestimo novoEmprestimo = emprestimoRepository.save(add);
                                return new ResponseEntity<Emprestimo>(novoEmprestimo, HttpStatus.CREATED);
                            }
                        }
                    }
                }
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);
            }  
        }

        @DeleteMapping("/emprestimo/{id}")
        public ResponseEntity<Emprestimo> deleteEmprestimoPorId (@PathVariable Long id) {
            try {
                Optional<Emprestimo> optEmprestimo = emprestimoRepository.findById(id);

                if (optEmprestimo.isPresent()) {
                    Livro livro = optEmprestimo.get().getLivro();
                    livro.setDisponivel(true);
                    livroRepository.save(livro);
                    emprestimoRepository.deleteById(id);                
                    return new ResponseEntity<Emprestimo>(optEmprestimo.get(), HttpStatus.ACCEPTED);
                } 
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);

            } catch (Exception e) {
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);
            }
        }
        @DeleteMapping("/emprestimo/porIdLivroIdUsuario/{idLivro}/{idUsuario}")
        public ResponseEntity<Emprestimo> deleteEmprestimoPorIdLivroIdUsuario (@PathVariable Long idLivro, @PathVariable Long idUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findById(idLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findById(idUsuario);

                if (optLivro.isPresent() && optUsuario.isPresent()) {
                    List<Emprestimo> allEmprestimos = emprestimoRepository.findAll();
                    for (Emprestimo emprestimo : allEmprestimos) {
                        if (emprestimo.getLivro().getId() == idLivro && emprestimo.getUsuario().getId() == idUsuario) {
                            optLivro.get().setDisponivel(true);
                            livroRepository.save(optLivro.get());
                            emprestimoRepository.delete(emprestimo);
                            return new ResponseEntity<Emprestimo>(emprestimo, HttpStatus.OK);
                        }
                    }
                }
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);

            } catch (Exception e) {
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);
            }
        }
        @DeleteMapping("/emprestimo/porNomeLivroRAUsuario/{nomeLivro}/{raUsuario}")
        public ResponseEntity<Emprestimo> deleteEmprestimoPorNomeLivroRAUsuario (@PathVariable String nomeLivro, @PathVariable String raUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findByTitulo(nomeLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findByRA(raUsuario);

                if (optLivro.isPresent() && optUsuario.isPresent()) {
                    List<Emprestimo> allReservas = emprestimoRepository.findAll();
                    for (Emprestimo emprestimo : allReservas) {
                        if (emprestimo.getLivro().getTitulo().equals(nomeLivro) && emprestimo.getUsuario().getRA().equals(raUsuario)) {                            
                            optLivro.get().setDisponivel(true);
                            livroRepository.save(optLivro.get());
                            emprestimoRepository.delete(emprestimo);
                            return new ResponseEntity<Emprestimo>(emprestimo, HttpStatus.OK);
                        }
                    }
                }
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);

            } catch (Exception e) {
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);
            }
        }
        @DeleteMapping("/emprestimo/porNomeLivroEmailUsuario/{nomeLivro}/{emailUsuario}")
        public ResponseEntity<Emprestimo> deleteEmprestimoPorNomeLivroEmailUsuario (@PathVariable String nomeLivro, @PathVariable String emailUsuario) {
            try {
                Optional<Livro> optLivro = livroRepository.findByTitulo(nomeLivro);
                Optional<Usuario> optUsuario = usuarioRepository.findByEmail(emailUsuario);

                if (optLivro.isPresent() && optUsuario.isPresent()) {
                    List<Emprestimo> allEmprestimos = emprestimoRepository.findAll();
                    for (Emprestimo emprestimo : allEmprestimos) {
                        if (emprestimo.getLivro().getTitulo().equals(nomeLivro) && emprestimo.getUsuario().getEmail().equals(emailUsuario)) {
                            optLivro.get().setDisponivel(true);
                            livroRepository.save(optLivro.get());
                            emprestimoRepository.delete(emprestimo);
                            return new ResponseEntity<Emprestimo>(emprestimo, HttpStatus.OK);
                        }
                    }
                }
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);

            } catch (Exception e) {
                return new ResponseEntity<Emprestimo>(HttpStatus.BAD_REQUEST);
            }
        }    
        //#endregion

    // TODO função hasBiblioteca
}
