package ma.ensa.authenticationactivty;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AgenceDB extends SQLiteOpenHelper {
    private static final String TAG = "LocalisationDB";
    private static final String DATABASE_NAME = "agances.db";
    private static final int DATABASE_VERSION = 1;


    private final String databasePath;
    private Context context;
    public AgenceDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.databasePath = String.valueOf(context.getDatabasePath(DATABASE_NAME));
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public SQLiteDatabase getReadableDatabaseFromAssets() {
        if (!databaseExists()) {
            try {
                copyDatabaseFromAssets();
            } catch (IOException e) {
                Log.e(TAG, "Error", e);
            }
        }

        return SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READONLY);
    }

    private boolean databaseExists() {
        File file = new File(databasePath);
        return file.exists();
    }

    private void copyDatabaseFromAssets() throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = context.getAssets().open(DATABASE_NAME);
            outputStream = new FileOutputStream(databasePath);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.flush();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}
