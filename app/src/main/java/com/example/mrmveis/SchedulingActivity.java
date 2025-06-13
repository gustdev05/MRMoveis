package com.example.mrmveis;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mrmveis.DataBase.AppDatabase;
import com.example.mrmveis.Model.Agendamento;
import com.example.mrmveis.Model.ItemCarrinho;
import com.example.mrmveis.Model.Produto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SchedulingActivity extends AppCompatActivity {
    private EditText etNome, etData, etHorario;
    private Button btnAgendar, btnVoltar;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduling);

        initViews();
        setupListeners();
        preencherDataAtual();
    }

    private void initViews() {
        etNome = findViewById(R.id.et_nome);
        etData = findViewById(R.id.et_data);
        etHorario = findViewById(R.id.et_horario);
        btnAgendar = findViewById(R.id.btn_agendar);
        btnVoltar = findViewById(R.id.btn_voltar);
        ivBack = findViewById(R.id.iv_back);
    }

    private void preencherDataAtual() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        etData.setText(sdf.format(new Date()));
        etHorario.setText("15:00");
    }

    private void setupListeners() {
        ivBack.setOnClickListener(v -> finish());
        btnVoltar.setOnClickListener(v -> finish());

        btnAgendar.setOnClickListener(v -> {
            if (validarCampos()) {
                criarAgendamento();
            }
        });
    }

    private boolean validarCampos() {
        if (etNome.getText().toString().trim().isEmpty()) {
            etNome.setError("Nome é obrigatório");
            return false;
        }

        if (etData.getText().toString().trim().isEmpty()) {
            etData.setError("Data é obrigatória");
            return false;
        }

        if (etHorario.getText().toString().trim().isEmpty()) {
            etHorario.setError("Horário é obrigatório");
            return false;
        }

        return true;
    }

    private void criarAgendamento() {
        String nome = etNome.getText().toString().trim();
        String data = etData.getText().toString().trim();
        String horario = etHorario.getText().toString().trim();

        AppDatabase db = AppDatabase.getDatabase(this);
        List<ItemCarrinho> itensCarrinho = db.itemCarrinhoDAO().getAllItensCarrinho();

        // Criar string com itens do carrinho
        StringBuilder itensString = new StringBuilder();
        for (ItemCarrinho item : itensCarrinho) {
            Produto produto = db.produtoDAO().getProdutoById(item.getProdutoId());
            if (produto != null) {
                itensString.append("- ").append(produto.getNome())
                        .append(" (R$ ").append(String.format("%.2f", produto.getPreco()))
                        .append(")\n");
            }
        }

        // Salvar agendamento no banco
        Agendamento agendamento = new Agendamento(nome, data, horario, itensString.toString());
        db.agendamentoDAO().insertAgendamento(agendamento);

        // Enviar mensagem pelo WhatsApp
        enviarWhatsApp(nome, data, horario, itensString.toString());

        // Limpar carrinho
        db.itemCarrinhoDAO().clearCarrinho();

        Toast.makeText(this, "Agendamento realizado com sucesso!", Toast.LENGTH_LONG).show();
        finish();
    }

    private void enviarWhatsApp(String nome, String data, String horario, String itens) {
        String numeroLoja = "5591984613136"; // Substitua pelo número da loja

        String mensagem = "🛋️ *AGENDAMENTO DE VISITA* 🛋️\n\n" +
                "👤 *Cliente:* " + nome + "\n" +
                "📅 *Data:* " + data + "\n" +
                "🕐 *Horário:* " + horario + "\n\n" +
                "🛍️ *Produtos de interesse:*\n" + itens + "\n" +
                "Obrigado!";

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + numeroLoja + "&text=" + Uri.encode(mensagem)));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "WhatsApp não encontrado", Toast.LENGTH_SHORT).show();
        }
    }
}