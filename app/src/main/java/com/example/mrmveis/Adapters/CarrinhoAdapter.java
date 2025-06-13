package com.example.mrmveis.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mrmveis.R;
import com.example.mrmveis.DataBase.AppDatabase;
import com.example.mrmveis.Model.ItemCarrinho;
import com.example.mrmveis.Model.Produto;

import java.util.List;

public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoAdapter.CarrinhoViewHolder> {
    private List<ItemCarrinho> itensCarrinho;
    private Context context;
    private OnItemRemovedListener listener;

    public interface OnItemRemovedListener {
        void onItemRemoved();
    }

    public CarrinhoAdapter(Context context, List<ItemCarrinho> itensCarrinho, OnItemRemovedListener listener) {
        this.context = context;
        this.itensCarrinho = itensCarrinho;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CarrinhoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrinho, parent, false);
        return new CarrinhoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarrinhoViewHolder holder, int position) {
        ItemCarrinho item = itensCarrinho.get(position);
        AppDatabase db = AppDatabase.getDatabase(context);
        Produto produto = db.produtoDAO().getProdutoById(item.getProdutoId());

        if (produto != null) {
            holder.tvNome.setText(produto.getNome());
            holder.tvPreco.setText(String.format("R$ %.2f", produto.getPreco()));

            // MÉTODO DIRETO - SEM GLIDE
            if (produto.getImagemResourceId() != 0) {
                holder.ivImagem.setImageResource(produto.getImagemResourceId());
            } else {
                holder.ivImagem.setImageResource(R.drawable.placeholder);
            }
        }

        holder.ivRemover.setOnClickListener(v -> {
            AppDatabase.getDatabase(context).itemCarrinhoDAO().deleteItem(item);
            itensCarrinho.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, itensCarrinho.size());
            if (listener != null) {
                listener.onItemRemoved();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itensCarrinho.size();
    }

    static class CarrinhoViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImagem, ivRemover;
        TextView tvNome, tvPreco;

        public CarrinhoViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImagem = itemView.findViewById(R.id.iv_carrinho_produto_imagem);
            ivRemover = itemView.findViewById(R.id.iv_carrinho_remover);
            tvNome = itemView.findViewById(R.id.tv_carrinho_produto_nome);
            tvPreco = itemView.findViewById(R.id.tv_carrinho_produto_preco);
        }
    }
}