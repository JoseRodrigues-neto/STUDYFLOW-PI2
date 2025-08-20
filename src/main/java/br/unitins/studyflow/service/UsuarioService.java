package br.unitins.studyflow.service;

import java.util.List;

import br.unitins.studyflow.dto.UsuarioRequestDTO;
import br.unitins.studyflow.dto.UsuarioResponseDTO;

public interface UsuarioService {
    
    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO usuarioDTO);
    
    public List<UsuarioResponseDTO> buscarTodos();
    
    public UsuarioResponseDTO buscarPorId(Long id);
    
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO usuarioDTO);
    
    public boolean excluir(Long id);
    
}
