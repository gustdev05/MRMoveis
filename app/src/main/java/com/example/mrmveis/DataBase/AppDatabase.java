package com.example.mrmveis.DataBase;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.mrmveis.DAO.AgendamentoDAO;
import com.example.mrmveis.DAO.ItemCarrinhoDAO;
import com.example.mrmveis.DAO.ProdutoDAO;
import com.example.mrmveis.Model.Agendamento;
import com.example.mrmveis.Model.ItemCarrinho;
import com.example.mrmveis.Model.Produto;

@Database(entities = {Produto.class, ItemCarrinho.class, Agendamento.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProdutoDAO produtoDAO();
    public abstract ItemCarrinhoDAO itemCarrinhoDAO();
    public abstract AgendamentoDAO agendamentoDAO();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "loja_database")
                            .fallbackToDestructiveMigration() // ← ADICIONE ESTA LINHA
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}