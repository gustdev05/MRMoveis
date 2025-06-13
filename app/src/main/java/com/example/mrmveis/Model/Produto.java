package com.example.mrmveis.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "produtos")
public class Produto {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nome;
    private double preco;
    private String descricao;
    private int imagemResourceId; // ← MUDANÇA: era String imagemUrl
    private String categoria;
    private boolean maisVendido;

    // Construtores
    public Produto() {}

    public Produto(String nome, double preco, String descricao, int imagemResourceId, String categoria, boolean maisVendido) {
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.imagemResourceId = imagemResourceId;
        this.categoria = categoria;
        this.maisVendido = maisVendido;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public int getImagemResourceId() { return imagemResourceId; }
    public void setImagemResourceId(int imagemResourceId) { this.imagemResourceId = imagemResourceId; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public boolean isMaisVendido() { return maisVendido; }
    public void setMaisVendido(boolean maisVendido) { this.maisVendido = maisVendido; }
}