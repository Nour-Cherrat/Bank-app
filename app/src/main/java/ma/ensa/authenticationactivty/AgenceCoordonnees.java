package ma.ensa.authenticationactivty;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public final class AgenceCoordonnees {

    private AgenceCoordonnees() {
    }

    public static class LocalisationEntry implements BaseColumns {
        public static final String TABLE_NAME = "agences";
        public static final String COLUMN_AGENCE = "nom_agence";
        public static final String COLUMN_RESP = "resp";
        public static final String COLUMN_TEL = "tel";
        public static final String COLUMN_LONG = "longitude";
        public static final String COLUMN_ALT = "altitude";



        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_RESP + " TEXT, " +
                        COLUMN_AGENCE + " TEXT, " +
                        COLUMN_LONG + " INTEGER, " +
                        COLUMN_TEL + " INTEGER, " +
                        COLUMN_ALT + " INTEGER) ";
    }


    public static Localisation getLocalisation(MapsActivity context, String agence) {
        String uppercaseText = agence.toUpperCase();
        Localisation localisation = null;
        AgenceDB agenceDB = new AgenceDB((Context) context);
        SQLiteDatabase db = agenceDB.getReadableDatabaseFromAssets();

        Cursor cursor = db.rawQuery("SELECT * FROM " + AgenceCoordonnees.LocalisationEntry.TABLE_NAME +
                        " WHERE " +
                        AgenceCoordonnees.LocalisationEntry.COLUMN_AGENCE+ "=? ",
                new String[]{(uppercaseText)});


        if (cursor != null && cursor.moveToFirst()) {

            int numColIndex = cursor.getColumnIndex(AgenceCoordonnees.LocalisationEntry.COLUMN_TEL);
            int adminColIndex = cursor.getColumnIndex(AgenceCoordonnees.LocalisationEntry.COLUMN_RESP);
            int agenceColIndex=cursor.getColumnIndex(AgenceCoordonnees.LocalisationEntry.COLUMN_AGENCE);
            int altColIndex = cursor.getColumnIndex(AgenceCoordonnees.LocalisationEntry.COLUMN_ALT);
            int longColIndex = cursor.getColumnIndex(AgenceCoordonnees.LocalisationEntry.COLUMN_LONG);

            String agenceLocalisation = cursor.getString(agenceColIndex);

            int numLocalisation = cursor.getInt(numColIndex);
            String adminLocalisation = cursor.getString(adminColIndex);
            int altLocalisation =  cursor.getInt(altColIndex);
            int longLocalisation = cursor.getInt(longColIndex);

            localisation = new Localisation(agenceLocalisation,
                    adminLocalisation, numLocalisation, altLocalisation, longLocalisation);

        }

        assert cursor != null;
        cursor.close();
        db.close();
        return localisation;

    }

}