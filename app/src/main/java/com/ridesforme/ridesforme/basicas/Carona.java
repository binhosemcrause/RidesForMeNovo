package com.ridesforme.ridesforme.basicas;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by Marcos on 05/09/2015.
 */
public class Carona implements Serializable{



    public int _id;
    public String endereco;
    public String numero;
    public String cidade;
    public String bairro;
    public String enderecoDestino;
    public String numeroDestino;

    public String cidadeDestino;
    public String bairroDestino;


    public Carona(){}

    public Carona(String endereco, String numero, String bairro, String cidade, String enderecoDestino, String numeroDestino, String cidadeDestino, String bairroDestino){
        this.endereco = endereco;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.enderecoDestino = enderecoDestino;
        this.numeroDestino = numeroDestino;
        this.cidadeDestino = cidadeDestino;
        this.bairroDestino = bairroDestino;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getEnderecoDestino() {
        return enderecoDestino;
    }

    public void setEnderecoDestino(String enderecoDestino) {
        this.enderecoDestino = enderecoDestino;
    }

    public String getNumeroDestino() {
        return numeroDestino;
    }

    public void setNumeroDestino(String numeroDestino) {
        this.numeroDestino = numeroDestino;
    }

    public String getCidadeDestino() {
        return cidadeDestino;
    }

    public void setCidadeDestino(String cidadeDestino) {
        this.cidadeDestino = cidadeDestino;
    }

    public String getBairroDestino() {
        return bairroDestino;
    }

    public void setBairroDestino(String bairroDestino) {
        this.bairroDestino = bairroDestino;
    }
}
