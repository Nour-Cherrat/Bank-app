package ma.ensa.authenticationactivty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class TransactionDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView compte;
        TextView desc;
        TextView montant;
        TextView date;
        TextView solde;
        TextView ref;
        compte = findViewById(R.id.compte);
        desc = findViewById(R.id.description);
        montant = findViewById(R.id.montant);
        date = findViewById(R.id.date);
        solde = findViewById(R.id.solde);
        ref = findViewById(R.id.reference);


        Bundle b = getIntent().getExtras();
        Transaction tr = (Transaction) b.get("transactionObject");

        compte.setText(tr.getNumCompte());
        desc.setText(tr.getLabel());
        montant.setText(tr.getPrice());
        date.setText(tr.getDate());
        solde.setText(""+tr.getSolde());
        ref.setText(tr.getNumRef());

    }

}