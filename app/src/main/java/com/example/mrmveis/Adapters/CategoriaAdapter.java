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

import com.example.mrmveis.CategoryActivity;
import com.example.mrmveis.R;
import com.example.mrmveis.Model.Categoria;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder> {
    private List<Categoria> categorias;
    private Context context;

    public CategoriaAdapter(Context context, List<Categoria> categorias) {
        this.context = context;
        this.categorias = categorias;
    }

    @NonNull
    @Override
    public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria, parent, false);
        return new CategoriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaViewHolder holder, int position) {
        Categoria categoria = categorias.get(position);
        holder.tvNome.setText(categoria.getNome());
        holder.ivIcone.setImageResource(categoria.getIconeResId());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CategoryActivity.class);
            intent.putExtra("categoria", categoria.getNome());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    static class CategoriaViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcone;
        TextView tvNome;

        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcone = itemView.findViewById(R.id.iv_categoria_icone);
            tvNome = itemView.findViewById(R.id.tv_categoria_nome);
        }
    }
}