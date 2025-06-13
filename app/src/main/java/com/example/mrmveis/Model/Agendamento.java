package com.example.mrmveis.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "agendamentos")
public class Agendamento {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nomeCliente;
    private String dataVisita;
    private String horarioVisita;
    private String itensCarrinho;
    private long timestamp;

    // Construtores
    public Agendamento() {}

    public Agendamento(String nomeCliente, String dataVisita, String horarioVisita, String itensCarrinho) {
        this.nomeCliente = nomeCliente;
        this.dataVisita = dataVisita;
        this.horarioVisita = horarioVisita;
        this.itensCarrinho = itensCarrinho;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public String getDataVisita() { return dataVisita; }
    public void setDataVisita(String dataVisita) { this.dataVisita = dataVisita; }

    public String getHorarioVisita() { return horarioVisita; }
    public void setHorarioVisita(String horarioVisita) { this.horarioVisita = horarioVisita; }

    public String getItensCarrinho() { return itensCarrinho; }
    public void setItensCarrinho(String itensCarrinho) { this.itensCarrinho = itensCarrinho; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}