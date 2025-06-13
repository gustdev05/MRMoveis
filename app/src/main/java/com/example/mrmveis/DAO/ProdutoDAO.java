package com.example.mrmveis.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mrmveis.Model.Produto;

import java.util.List;

@Dao
public interface ProdutoDAO {
    @Query("SELECT * FROM produtos")
    List<Produto> getAllProdutos();

    @Query("SELECT * FROM produtos WHERE categoria = :categoria")
    List<Produto> getProdutosByCategoria(String categoria);

    @Query("SELECT * FROM produtos WHERE maisVendido = 1")
    List<Produto> getMaisVendidos();

    @Query("SELECT * FROM produtos WHERE id = :id")
    Produto getProdutoById(int id);

    @Query("DELETE FROM produtos")
    void deleteAllProdutos();

    @Insert
    void insertProduto(Produto produto);

    @Insert
    void insertProdutos(List<Produto> produtos);

    @Update
    void updateProduto(Produto produto);

    @Delete
    void deleteProduto(Produto produto);
}