package com.example.mrmveis.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "itens_carrinho",
        foreignKeys = @ForeignKey(entity = Produto.class,
                parentColumns = "id",
                childColumns = "produtoId",
                onDelete = ForeignKey.CASCADE))
public class ItemCarrinho {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int produtoId;
    private int quantidade;

    // Construtores
    public ItemCarrinho() {}

    public ItemCarrinho(int produtoId, int quantidade) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProdutoId() { return produtoId; }
    public void setProdutoId(int produtoId) { this.produtoId = produtoId; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
}