package com.example.contactlistapp.Database;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.example.contactlistapp.Contact;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.channels.FileChannel;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Contact.class}, version = 2)
public abstract class ContactRoomDatabase extends RoomDatabase {

    public abstract ContactDao contactDao();

    private static volatile ContactRoomDatabase INSTANCE;

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final ContactDao dao;

        PopulateDbAsync(ContactRoomDatabase db){
            dao = db.contactDao();
        }

        @Override
        protected Void doInBackground(final Void... params){
            /*
            dao.deleteAll();
            Contact contact = new Contact(0,"Person1", "testing@gmail.com",
                    "0615569087"); // id=0 to autogenerate a unique key
            dao.insert(contact);
            contact = new Contact(0,"Person2", "moretesting@hotmail.com",
                    "0675386714");
            dao.insert(contact);*/
            return null;
        }
    }

    private static RoomDatabase.Callback roomDatabaseCallBack =
        new RoomDatabase.Callback(){

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    // singleton to prevent multiple instances of the database to be opened
    static ContactRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ContactRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ContactRoomDatabase.class, "contact_database")
                            .addCallback(roomDatabaseCallBack)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
