package com.example.kanbrunco.model.usuario;
import com.example.kanbrunco.model.usuario.Usuario;

//Atende ao requisito de herança.
public class UsuarioAdmin extends Usuario {

 public UsuarioAdmin(String id, String nome, String email) {
     super(id, nome, email);
 }

 @Override
 public void criarQuadro() {
     // Lógica para criar um novo quadro.
     // Um usuário admin pode criar quadros e compartilhá-los.
     System.out.println("Administrador " + getNome() + " criou um novo quadro.");
 }
 
 @Override
 public void gerenciarUsuarios() {
     // Lógica para gerenciar usuários, como apagar quadros de outros usuários ou apagar usuários.
     System.out.println("Administrador " + getNome() + " pode gerenciar usuários e quadros.");
 }

 public void apagarQuadroDeUsuario(Usuario usuario, String idQuadro) {
     // Lógica para apagar um quadro específico de outro usuário.
     System.out.println("Administrador " + getNome() + " apagou o quadro " + idQuadro + " do usuário " + usuario.getNome());
 }

 public void apagarUsuario(Usuario usuario) {
     // Lógica para apagar um usuário.
     System.out.println("Administrador " + getNome() + " apagou o usuário " + usuario.getNome());
 }
}
