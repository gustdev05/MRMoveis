package com.example.mrmveis;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mrmveis.DataBase.AppDatabase;
import com.example.mrmveis.Model.ItemCarrinho;
import com.example.mrmveis.Model.Produto;

public class ProductDetailActivity extends AppCompatActivity {
    private ImageView ivProduto, ivBack;
    private TextView tvNome, tvPreco, tvDescricao;
    private Button btnAddCarrinho;
    private Produto produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        initViews();
        loadProduto();
        setupListeners();
    }

    private void initViews() {
        ivProduto = findViewById(R.id.iv_produto_detail);
        ivBack = findViewById(R.id.iv_back);
        tvNome = findViewById(R.id.tv_produto_nome);
        tvPreco = findViewById(R.id.tv_produto_preco);
        tvDescricao = findViewById(R.id.tv_produto_descricao);
        btnAddCarrinho = findViewById(R.id.btn_add_carrinho);
    }

    private void loadProduto() {
        int produtoId = getIntent().getIntExtra("produto_id", -1);
        if (produtoId != -1) {
            AppDatabase db = AppDatabase.getDatabase(this);
            produto = db.produtoDAO().getProdutoById(produtoId);

            if (produto != null) {
                tvNome.setText(produto.getNome());
                tvPreco.setText(String.format("R$ %.2f", produto.getPreco()));
                tvDescricao.setText(produto.getDescricao());

                // MÉTODO DIRETO - SEM GLIDE
                if (produto.getImagemResourceId() != 0) {
                    ivProduto.setImageResource(produto.getImagemResourceId());
                } else {
                    ivProduto.setImageResource(R.drawable.placeholder);
                }
            }
        }
    }

    private void setupListeners() {
        ivBack.setOnClickListener(v -> finish());

        btnAddCarrinho.setOnClickListener(v -> {
            if (produto != null) {
                AppDatabase db = AppDatabase.getDatabase(this);

                // Verificar se já existe no carrinho
                ItemCarrinho existingItem = db.itemCarrinhoDAO().getItemByProdutoId(produto.getId());

                if (existingItem == null) {
                    ItemCarrinho novoItem = new ItemCarrinho(produto.getId(), 1);
                    db.itemCarrinhoDAO().insertItem(novoItem);
                    Toast.makeText(this, "Produto adicionado ao carrinho!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Produto já está no carrinho!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}