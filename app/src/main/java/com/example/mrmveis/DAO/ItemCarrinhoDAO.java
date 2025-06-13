package com.example.mrmveis.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mrmveis.Model.ItemCarrinho;

import java.util.List;

@Dao
public interface ItemCarrinhoDAO {
    @Query("SELECT * FROM itens_carrinho")
    List<ItemCarrinho> getAllItensCarrinho();

    @Query("SELECT * FROM itens_carrinho WHERE produtoId = :produtoId")
    ItemCarrinho getItemByProdutoId(int produtoId);

    @Insert
    void insertItem(ItemCarrinho item);

    @Delete
    void deleteItem(ItemCarrinho item);

    @Query("DELETE FROM itens_carrinho WHERE produtoId = :produtoId")
    void deleteByProdutoId(int produtoId);

    @Query("DELETE FROM itens_carrinho")
    void clearCarrinho();
}