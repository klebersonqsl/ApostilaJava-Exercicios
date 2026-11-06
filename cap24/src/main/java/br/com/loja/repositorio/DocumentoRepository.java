package br.com.loja.repositorio;

import br.com.loja.modelo.Documento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    List<Documento> findAllByOrderByNome();
}
