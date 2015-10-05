package com.ridesforme.ridesforme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ridesforme.ridesforme.R;
import com.ridesforme.ridesforme.basicas.Carona;

import java.util.List;

/**
 * Created by Marcos Antônio on 30/09/2015.
 */
public class CaronaAdapter extends BaseAdapter{

    Context mContexto;
    List<Carona> mCaronas;

    public CaronaAdapter(Context contexto, List<Carona> caronas){
        this.mContexto = contexto;
        this.mCaronas = caronas;
    }

    @Override
    public int getCount() {
        return mCaronas.size();
    }

    @Override
    public Object getItem(int position) {
        return mCaronas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Carona carona = mCaronas.get(position);

        ViewHolder holder = null;

        if(convertView == null){
            convertView = LayoutInflater.from(mContexto).inflate(R.layout.item_lista_carona, null);

            holder = new ViewHolder();
            holder.imgLogo = (ImageView)convertView.findViewById(R.id.imgLogo);
            holder.mTxtOrigem = (TextView)convertView.findViewById(R.id.txtItemCaronaOrigem);
            holder.mTxtDestino = (TextView)convertView.findViewById(R.id.txtItemCaronaDestino);
        }

        return convertView;
    }

    static class ViewHolder{
        ImageView imgLogo;
        TextView mTxtOrigem;
        TextView mTxtDestino;
    }
}
