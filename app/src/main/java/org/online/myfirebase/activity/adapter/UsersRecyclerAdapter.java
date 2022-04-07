package org.online.myfirebase.activity.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import org.online.myfirebase.R;
import org.online.myfirebase.activity.buyer.DetailProductBuyer;
import org.online.myfirebase.model.user;

import java.util.List;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder> {
    private List<user> listUsers;
    Context mContext;
    public UsersRecyclerAdapter( List<user> listUsers) {
        this.listUsers = listUsers;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_recycler,parent,false);
        return new UserViewHolder(view);

    }

    @Override
    public void onBindViewHolder( UserViewHolder holder, int position) {
        holder.textViewName.setText(listUsers.get(position).getName());
    }

    @Override
    public int getItemCount() {
        Log.v(UsersRecyclerAdapter.class.getSimpleName(),""+listUsers.size());
        return listUsers.size();
    }
    public class UserViewHolder extends RecyclerView.ViewHolder{
        public AppCompatTextView textViewName;
        public AppCompatTextView textViewPassword;
        public Spinner spinnerRole;

        public UserViewHolder(@NonNull View view) {
            super(view);
        textViewName=(AppCompatTextView)view.findViewById(R.id.textViewName);
            }
        }
    }

