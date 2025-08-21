package com.example.kanbrunco.model.usuario;

//Atende ao requisito de abstração.
public abstract class Usuario {
 
 // Atributos privados para encapsulamento.
	private Long id;
	private String nome;
	private String email;
 
 //Construtor vazio exigido pelo DAO para instanciar a classe ao ler o arquivo
	public Usuario() {	 
 }
 
 // Construtor
	public Usuario(Long id, String nome, String email) {
		this.id = id;
		this.nome = nome;
		this.email = email;
 }

 // Métodos abstratos para polimorfismo, que serão implementados nas subclasses.
	public abstract void criarQuadro();
	public abstract void gerenciarUsuarios();

	public Long getId() {
		return id;
 }

	public void setId(Long id) {
		this.id = id;
 }

	public String getNome() {
		return nome;
     }
	public void setNome(String nome) {
		this.nome = nome;
 }

	public String getEmail() {
		return email;
 }

	public void setEmail(String email) {
		this.email = email;
 }
}

