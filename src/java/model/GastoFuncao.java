/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Davy
 */
public class GastoFuncao {
    
    private String data;
    private ArrayList<String> json;
    private String funcao;

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public ArrayList<String> getJson() {
        return json;
    }

    public void setJson(ArrayList<String> json) {
        this.json = json;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    
}
