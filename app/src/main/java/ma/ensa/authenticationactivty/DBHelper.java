package ma.ensa.authenticationactivty;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "transactionsManager";
    private static final String TABLE_TRANSACTIONS = "transactions";
    private static final String ICON = "icon";
    private static final String LABEL = "label";
    private static final String PRICE = "price";
    private static final String DATE = "date";
    private static final String ACCOUNT_NUMBER = "accountnumber";
    private static final String REFERENCE = "reference";
    private static final String BALANCE = "balance";

    // Constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TRANSACTIONS_TABLE = "CREATE TABLE " + TABLE_TRANSACTIONS + "("
                + ICON + " INTEGER,"
                + LABEL + " STRING,"
                + PRICE + " STRING,"
                + DATE + " STRING,"
                + ACCOUNT_NUMBER + " STRING,"
                + REFERENCE + " STRING,"
                + BALANCE + " REAL" + ")";
        db.execSQL(CREATE_TRANSACTIONS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);

        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    void addTransaction(Transaction trans) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ICON, trans.getIcon());
        values.put(LABEL, trans.getLabel());
        values.put(PRICE, trans.getPrice());
        values.put(DATE, trans.getDate());
        values.put(ACCOUNT_NUMBER, trans.getNumCompte());
        values.put(REFERENCE, trans.getNumRef());
        values.put(BALANCE, trans.getSolde());

        // Inserting Row
        db.insert(TABLE_TRANSACTIONS, null, values);
        db.close(); // Closing database connection
    }

    // code to get the single contact
    Transaction getTransaction(int ref) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TRANSACTIONS, new String[] {
                        ICON, LABEL,PRICE,DATE,ACCOUNT_NUMBER,REFERENCE,BALANCE
                }, REFERENCE + "=?",
                new String[] { String.valueOf(ref) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Transaction transaction = new Transaction(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                Double.parseDouble(cursor.getString(6)));
        // return contact
        return transaction;
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactionList = new ArrayList<Transaction>();
        String selectQuery = "SELECT  * FROM " + TABLE_TRANSACTIONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Transaction transaction = new Transaction();

                transaction.setIcon(Integer.parseInt(cursor.getString(0)));
                transaction.setLabel(cursor.getString(1));
                transaction.setPrice(cursor.getString(2));
                transaction.setDate(cursor.getString(3));
                transaction.setNumCompte(cursor.getString(4));
                transaction.setNumRef(cursor.getString(5));
                transaction.setSolde(Double.parseDouble(cursor.getString(6)));

                transactionList.add(transaction);
            } while (cursor.moveToNext());
        }

        return transactionList;
    }


    // Deleting single contact
    public void deleteTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRANSACTIONS, REFERENCE + " = ?",
                new String[] { String.valueOf(transaction.getNumRef()) });
        db.close();
    }

    public int getTransactionsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TRANSACTIONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
