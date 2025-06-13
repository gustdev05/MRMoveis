package com.example.mrmveis.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mrmveis.Model.Agendamento;

import java.util.List;

@Dao
public interface AgendamentoDAO {
    @Query("SELECT * FROM agendamentos ORDER BY timestamp DESC")
    List<Agendamento> getAllAgendamentos();

    @Insert
    void insertAgendamento(Agendamento agendamento);
}