package com.ridesforme.ridesforme.basicas;

import java.io.Serializable;
import java.util.Date;

public class Usuario implements Serializable{

    private String nome;
    private String senha;
    private String confirmarSenha;
    private Date dataNascimento;
    private String sexo;
    private String empresa;
    private String email;
    private String telefone;
    private long matricula;
    private String imagemPerfilUrl;
    private long usuarioId;
    private String estado;
    private String cidade;
    private String login;
    private String rua;
    private String numero;
    private String bairro;
    private String  cep;
    private String celular;
    private String localEmpresa;

    public Usuario(){}

    @Override
    public String toString() {
        return  "usuarioId: " + usuarioId +
                " nome: " + nome +
                " senha: " + senha +
                " confirmarSenha: " + confirmarSenha +
                " dataNascimento: " + dataNascimento +
                " sexo: " + sexo +
                " empresa: " + empresa +
                " telefone: " + telefone +
                " matricula: " + matricula +
                " estado: " + estado +
                " cidade: " + cidade +
                " rua: " + rua +
                " numero: " + numero +
                " bairro: " + bairro +
                " cep: " + cep +
                " celular: " + celular +
                " localEmpresa: " + localEmpresa +
                " imagemPerfilUrl: " + imagemPerfilUrl;

    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getLocalEmpresa() {
        return localEmpresa;
    }

    public void setLocalEmpresa(String localEmpresa) {
        this.localEmpresa = localEmpresa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public long getMatricula() {
        return matricula;
    }

    public void setMatricula(long matricula) {
        this.matricula = matricula;
    }

    public String getImagemPerfilUrl() {
        return imagemPerfilUrl;
    }

    public void setImagemPerfilUrl(String imagemPerfilUrl) {
        this.imagemPerfilUrl = imagemPerfilUrl;
    }


}
