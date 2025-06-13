package com.example.mrmveis.Model;

public class Categoria {
    private String nome;
    private int iconeResId;

    public Categoria(String nome, int iconeResId) {
        this.nome = nome;
        this.iconeResId = iconeResId;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getIconeResId() { return iconeResId; }
    public void setIconeResId(int iconeResId) { this.iconeResId = iconeResId; }
}