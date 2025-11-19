package br.unitins.studyflow.service;

import java.util.List;

import jakarta.ws.rs.core.Response;

import br.unitins.studyflow.dto.UsuarioRequestDTO;
import br.unitins.studyflow.dto.UsuarioResponseDTO;

public interface UsuarioService {

    public UsuarioResponseDTO cadastrar(String uid, UsuarioRequestDTO usuarioDTO);

    public List<UsuarioResponseDTO> buscarTodos();
    
    public UsuarioResponseDTO buscarPorId(String uid);
    
    public UsuarioResponseDTO atualizar(String uid, UsuarioRequestDTO usuarioDTO);
    
    public void excluir(String uid);

    public UsuarioResponseDTO atualizarAvatar(String uid, String urlDaImagem);
    
    public Response loginComGoogle(String idToken);
}
