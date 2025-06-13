package com.example.mrmveis.Adapters;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mrmveis.ProductDetailActivity;
import com.example.mrmveis.R;
import com.example.mrmveis.Model.Produto;

import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {
    private List<Produto> produtos;
    private Context context;

    public ProdutoAdapter(Context context, List<Produto> produtos) {
        this.context = context;
        this.produtos = produtos;
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produto, parent, false);
        return new ProdutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        Produto produto = produtos.get(position);
        holder.tvNome.setText(produto.getNome());
        holder.tvPreco.setText(String.format("R$ %.2f", produto.getPreco()));

        // MÉTODO DIRETO - SEM GLIDE
        if (produto.getImagemResourceId() != 0) {
            holder.ivImagem.setImageResource(produto.getImagemResourceId());
        } else {
            holder.ivImagem.setImageResource(R.drawable.placeholder);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("produto_id", produto.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    static class ProdutoViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImagem;
        TextView tvNome, tvPreco;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImagem = itemView.findViewById(R.id.iv_produto_imagem);
            tvNome = itemView.findViewById(R.id.tv_produto_nome);
            tvPreco = itemView.findViewById(R.id.tv_produto_preco);
        }
    }
}