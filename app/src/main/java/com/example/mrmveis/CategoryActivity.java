package com.example.mrmveis;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mrmveis.Adapters.ProdutoAdapter;
import com.example.mrmveis.DataBase.AppDatabase;
import com.example.mrmveis.Model.Produto;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView rvProdutos;
    private TextView tvCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        initViews();
        setupToolbar();
        loadProdutos();
    }

    private void initViews() {
        rvProdutos = findViewById(R.id.rv_produtos);
        tvCategoria = findViewById(R.id.tv_categoria);
    }

    private void setupToolbar() {
        String categoria = getIntent().getStringExtra("categoria");
        tvCategoria.setText(categoria);

        findViewById(R.id.iv_back).setOnClickListener(v -> finish());
    }

    private void loadProdutos() {
        String categoria = getIntent().getStringExtra("categoria");
        AppDatabase db = AppDatabase.getDatabase(this);
        List<Produto> produtos = db.produtoDAO().getProdutosByCategoria(categoria.toLowerCase());

        ProdutoAdapter adapter = new ProdutoAdapter(this, produtos);
        rvProdutos.setLayoutManager(new GridLayoutManager(this, 2));
        rvProdutos.setAdapter(adapter);
    }
}