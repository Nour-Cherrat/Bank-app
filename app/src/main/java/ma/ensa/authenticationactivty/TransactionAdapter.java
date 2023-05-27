package ma.ensa.authenticationactivty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TransactionAdapter extends ArrayAdapter<Transaction> {

    ArrayList<Transaction> trans;
    public TransactionAdapter(@NonNull Context context, int resource, ArrayList<Transaction> trans) {
        super(context, resource,trans);
        this.trans = trans;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.cellule,parent,false);

        ImageView img = convertView.findViewById(R.id.icon);
        img.setBackgroundResource(trans.get(position).getIcon());

        TextView label = convertView.findViewById(R.id.label);
        label.setText(trans.get(position).getLabel());

        TextView price = convertView.findViewById(R.id.price);
        price.setText(trans.get(position).getPrice());

        TextView date = convertView.findViewById(R.id.date);
        date.setText(trans.get(position).getDate());

        return convertView;
    }
}

