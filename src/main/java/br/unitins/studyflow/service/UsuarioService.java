package br.unitins.studyflow.service;

import java.util.List;

import br.unitins.studyflow.dto.UsuarioRequestDTO;
import br.unitins.studyflow.dto.UsuarioResponseDTO;

public interface UsuarioService {

    public UsuarioResponseDTO cadastrar(String uid, UsuarioRequestDTO usuarioDTO);

    public List<UsuarioResponseDTO> buscarTodos();
    
    public UsuarioResponseDTO buscarPorId(String uid);
    
    public UsuarioResponseDTO atualizar(String uid, UsuarioRequestDTO usuarioDTO);
    
    public boolean excluir(String uid);
    
}
