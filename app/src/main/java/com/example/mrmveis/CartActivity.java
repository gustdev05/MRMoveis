package com.example.mrmveis;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mrmveis.Adapters.CarrinhoAdapter;
import com.example.mrmveis.DataBase.AppDatabase;
import com.example.mrmveis.Model.ItemCarrinho;

import java.util.List;

public class CartActivity extends AppCompatActivity implements CarrinhoAdapter.OnItemRemovedListener {
    private RecyclerView rvCarrinho;
    private Button btnAgendar, btnVoltar;
    private TextView tvTotal;
    private ImageView ivBack;
    private CarrinhoAdapter adapter;
    private List<ItemCarrinho> itensCarrinho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initViews();
        loadCarrinho();
        setupListeners();
    }

    private void initViews() {
        rvCarrinho = findViewById(R.id.rv_carrinho);
        btnAgendar = findViewById(R.id.btn_agendar);
        btnVoltar = findViewById(R.id.btn_voltar);
        tvTotal = findViewById(R.id.tv_total);
        ivBack = findViewById(R.id.iv_back);
    }

    private void loadCarrinho() {
        AppDatabase db = AppDatabase.getDatabase(this);
        itensCarrinho = db.itemCarrinhoDAO().getAllItensCarrinho();

        adapter = new CarrinhoAdapter(this, itensCarrinho, this);
        rvCarrinho.setLayoutManager(new LinearLayoutManager(this));
        rvCarrinho.setAdapter(adapter);

        calculateTotal();
    }

    private void calculateTotal() {
        double total = 0;
        AppDatabase db = AppDatabase.getDatabase(this);

        for (ItemCarrinho item : itensCarrinho) {
            com.example.mrmveis.Model.Produto produto = db.produtoDAO().getProdutoById(item.getProdutoId());
            if (produto != null) {
                total += produto.getPreco() * item.getQuantidade();
            }
        }

        tvTotal.setText(String.format("Total: R$ %.2f", total));
    }

    private void setupListeners() {
        ivBack.setOnClickListener(v -> finish());
        btnVoltar.setOnClickListener(v -> finish());

        btnAgendar.setOnClickListener(v -> {
            if (!itensCarrinho.isEmpty()) {
                Intent intent = new Intent(this, SchedulingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemRemoved() {
        calculateTotal();
    }
}