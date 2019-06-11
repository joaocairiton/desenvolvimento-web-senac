/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.joaocairiton.dws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author joaoc
 */
@Component("relatorioControle")
public class RelatorioControle {

    @Autowired
    DataSource datasource;

    public Integer getTotalClientes() {
        Integer tot = null;

        try {
            ResultSet executeQuery = datasource.getConnection().createStatement().executeQuery("select count(*) from cliente");
            executeQuery.next();
            tot = executeQuery.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tot;
    }
    public Integer getValorTotalClientes() {
        Integer vtca = null;

        try {
            ResultSet executeQuery = datasource.getConnection().createStatement().executeQuery("select count(*) from vendas");
            executeQuery.next();
            vtca = executeQuery.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return vtca;
    }

    public String getTotalVendas() {
        return "R$ 5.123,91";
    }
    String ano;
    String semestre;

    List<String[]> resultado = new ArrayList<>();

    public void consultarVendasLojaSemestre() {
        resultado.clear();
        //Consultar no banco de dados o total de vendas por loja
        //Por semestre
        System.out.println(
                "Select loja, sum(valor_vendas"
                + " from pedidos where ano=" + ano
                + " and semestre=" + semestre + " group by loja"
        );

        resultado.add(new String[]{"Crrefour Norte", "10"});
        resultado.add(new String[]{"bosta", "10"});

    }

    public void converterPrajson() throws JsonProcessingException {
        resultado.clear();
        resultado.add(new String[]{"Crrefour Norte", "10"});
        resultado.add(new String[]{"bosta", "10"});
        ObjectMapper conversor = new ObjectMapper();
        String resultadoEmJson = conversor.writeValueAsString(resultado);

        System.out.println("resultado em json:" + resultadoEmJson);
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public List<String[]> getResultado() {
        return resultado;
    }

    public void setResultado(List<String[]> resultado) {
        this.resultado = resultado;
    }

}
