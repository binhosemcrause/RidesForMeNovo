package com.ridesforme.ridesforme.basicas;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by Marcos on 05/09/2015.
 */
public class Carona implements Serializable{

    public int _id;
    public String EstadoOrigem;
    public String CidadeOrigem;
    public String BairroOrigem;
    public String RuaOrigem;
    public String EstadoDestino;
    public String CidadeDestino;
    public String BairroDestino;
    public String RuaDestino;
    public String Valor;
    public String DescricaoCarona;
    public String TipoVeiculo;
    public String DescricaoVeiculo;
    public String Vagas;
    public String TipoTrajeto;
    public String TipoOferta;
    public String DataHoraSaidaIda;
    public String DataHoraSaidaVolta;
    public String DiaDaSemana;
    public String Status;

    public Carona(int _id, String estadoOrigem, String cidadeOrigem, String bairroOrigem, String ruaOrigem, String estadoDestino, String cidadeDestino, String bairroDestino, String ruaDestino, String valor, String descricaoCarona, String tipoVeiculo, String descricaoVeiculo, String vagas, String tipoTrajeto, String tipoOferta, String dataHoraSaidaIda, String dataHoraSaidaVolta, String diaDaSemana, String status) {
        this._id = _id;
        EstadoOrigem = estadoOrigem;
        CidadeOrigem = cidadeOrigem;
        BairroOrigem = bairroOrigem;
        RuaOrigem = ruaOrigem;
        EstadoDestino = estadoDestino;
        CidadeDestino = cidadeDestino;
        BairroDestino = bairroDestino;
        RuaDestino = ruaDestino;
        Valor = valor;
        DescricaoCarona = descricaoCarona;
        TipoVeiculo = tipoVeiculo;
        DescricaoVeiculo = descricaoVeiculo;
        Vagas = vagas;
        TipoTrajeto = tipoTrajeto;
        TipoOferta = tipoOferta;
        DataHoraSaidaIda = dataHoraSaidaIda;
        DataHoraSaidaVolta = dataHoraSaidaVolta;
        DiaDaSemana = diaDaSemana;
        Status = status;
    }

    public Carona(){}

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getEstadoOrigem() {
        return EstadoOrigem;
    }

    public void setEstadoOrigem(String estadoOrigem) {
        EstadoOrigem = estadoOrigem;
    }

    public String getCidadeOrigem() {
        return CidadeOrigem;
    }

    public void setCidadeOrigem(String cidadeOrigem) {
        CidadeOrigem = cidadeOrigem;
    }

    public String getBairroOrigem() {
        return BairroOrigem;
    }

    public void setBairroOrigem(String bairroOrigem) {
        BairroOrigem = bairroOrigem;
    }

    public String getRuaOrigem() {
        return RuaOrigem;
    }

    public void setRuaOrigem(String ruaOrigem) {
        RuaOrigem = ruaOrigem;
    }

    public String getEstadoDestino() {
        return EstadoDestino;
    }

    public void setEstadoDestino(String estadoDestino) {
        EstadoDestino = estadoDestino;
    }

    public String getCidadeDestino() {
        return CidadeDestino;
    }

    public void setCidadeDestino(String cidadeDestino) {
        CidadeDestino = cidadeDestino;
    }

    public String getBairroDestino() {
        return BairroDestino;
    }

    public void setBairroDestino(String bairroDestino) {
        BairroDestino = bairroDestino;
    }

    public String getRuaDestino() {
        return RuaDestino;
    }

    public void setRuaDestino(String ruaDestino) {
        RuaDestino = ruaDestino;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String valor) {
        Valor = valor;
    }

    public String getDescricaoCarona() {
        return DescricaoCarona;
    }

    public void setDescricaoCarona(String descricaoCarona) {
        DescricaoCarona = descricaoCarona;
    }

    public String getTipoVeiculo() {
        return TipoVeiculo;
    }

    public void setTipoVeiculo(String tipoVeiculo) {
        TipoVeiculo = tipoVeiculo;
    }

    public String getDescricaoVeiculo() {
        return DescricaoVeiculo;
    }

    public void setDescricaoVeiculo(String descricaoVeiculo) {
        DescricaoVeiculo = descricaoVeiculo;
    }

    public String getVagas() {
        return Vagas;
    }

    public void setVagas(String vagas) {
        Vagas = vagas;
    }

    public String getTipoTrajeto() {
        return TipoTrajeto;
    }

    public void setTipoTrajeto(String tipoTrajeto) {
        TipoTrajeto = tipoTrajeto;
    }

    public String getTipoOferta() {
        return TipoOferta;
    }

    public void setTipoOferta(String tipoOferta) {
        TipoOferta = tipoOferta;
    }

    public String getDataHoraSaidaIda() {
        return DataHoraSaidaIda;
    }

    public void setDataHoraSaidaIda(String dataHoraSaidaIda) {
        DataHoraSaidaIda = dataHoraSaidaIda;
    }

    public String getDataHoraSaidaVolta() {
        return DataHoraSaidaVolta;
    }

    public void setDataHoraSaidaVolta(String dataHoraSaidaVolta) {
        DataHoraSaidaVolta = dataHoraSaidaVolta;
    }

    public String getDiaDaSemana() {
        return DiaDaSemana;
    }

    public void setDiaDaSemana(String diaDaSemana) {
        DiaDaSemana = diaDaSemana;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}