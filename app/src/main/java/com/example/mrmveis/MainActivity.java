package com.example.mrmveis;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mrmveis.Adapters.BannerAdapter;
import com.example.mrmveis.Adapters.CategoriaAdapter;
import com.example.mrmveis.Adapters.ProdutoAdapter;
import com.example.mrmveis.DataBase.AppDatabase;
import com.example.mrmveis.Model.Categoria;
import com.example.mrmveis.Model.Produto;
import me.relex.circleindicator.CircleIndicator3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvCategorias, rvMaisVendidos;
    private ViewPager2 vpBanners;
    private CircleIndicator3 indicator;
    private ImageView ivHome, ivCarrinho, ivAgendamentos;
    private Handler bannerHandler = new Handler();
    private Runnable bannerRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupDatabase();
        setupCategorias();
        setupMaisVendidos();
        setupBanners();
        setupBottomNavigation();
    }

    private void initViews() {
        rvCategorias = findViewById(R.id.rv_categorias);
        rvMaisVendidos = findViewById(R.id.rv_mais_vendidos);
        vpBanners = findViewById(R.id.vp_banners);
        indicator = findViewById(R.id.indicator);
        ivHome = findViewById(R.id.iv_home);
        ivCarrinho = findViewById(R.id.iv_carrinho);
        ivAgendamentos = findViewById(R.id.iv_agendamentos);
    }

    private void setupDatabase() {
        AppDatabase db = AppDatabase.getDatabase(this);

        // Inserir dados de exemplo se não existirem
        if (db.produtoDAO().getAllProdutos().isEmpty()) {
            List<Produto> produtos = Arrays.asList(
                    new Produto("Sofá Moderno", 1599.99,
                            "Sofá confortável e elegante para sua sala.",
                            R.drawable.sofa_moderno, "sala", true),
                    new Produto("Mesa de Jantar", 899.99,
                            "Mesa de jantar para 6 pessoas.",
                            R.drawable.mesa_de_jantar, "cozinha", true),
                    new Produto("Guarda-roupa", 1299.99,
                            "Guarda-roupa espaçoso com espelho.",
                            R.drawable.guarda_roupa, "quarto", false),
                    new Produto("Cama Box", 799.99,
                            "Cama box queen size confortável.",
                            R.drawable.cama_box, "quarto", true)
            );
            db.produtoDAO().insertProdutos(produtos);
        }
    }

    private void setupCategorias() {
        List<Categoria> categorias = Arrays.asList(
                new Categoria("Fabricação", R.drawable.fabricacao),
                new Categoria("Cozinha", R.drawable.cozinha),
                new Categoria("Sala", R.drawable.sala),
                new Categoria("Quarto", R.drawable.quarto)
        );

        CategoriaAdapter adapter = new CategoriaAdapter(this, categorias);
        rvCategorias.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvCategorias.setAdapter(adapter);
    }

    private void setupMaisVendidos() {
        AppDatabase db = AppDatabase.getDatabase(this);
        List<Produto> maisVendidos = db.produtoDAO().getMaisVendidos();

        ProdutoAdapter adapter = new ProdutoAdapter(this, maisVendidos);
        rvMaisVendidos.setLayoutManager(new GridLayoutManager(this, 2));
        rvMaisVendidos.setAdapter(adapter);
    }

    private void setupBanners() {
        List<Integer> banners = Arrays.asList(
                R.drawable.banner1,
                R.drawable.banner2,
                R.drawable.banner3
        );

        BannerAdapter adapter = new BannerAdapter(banners);
        vpBanners.setAdapter(adapter);
        indicator.setViewPager(vpBanners);

        // Auto-scroll dos banners
        bannerRunnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = vpBanners.getCurrentItem();
                int nextItem = (currentItem + 1) % banners.size();
                vpBanners.setCurrentItem(nextItem);
                bannerHandler.postDelayed(this, 3000);
            }
        };
        bannerHandler.postDelayed(bannerRunnable, 3000);
    }

    private void setupBottomNavigation() {
        ivHome.setOnClickListener(v -> {
            // Já estamos na home
        });

        ivCarrinho.setOnClickListener(v -> {
            startActivity(new Intent(this, CartActivity.class));
        });

        ivAgendamentos.setOnClickListener(v -> {
            startActivity(new Intent(this, SchedulingActivity.class));
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bannerHandler != null && bannerRunnable != null) {
            bannerHandler.removeCallbacks(bannerRunnable);
        }
    }
}