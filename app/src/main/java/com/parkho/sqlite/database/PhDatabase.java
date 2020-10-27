package com.parkho.sqlite.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.parkho.sqlite.PhDatabaseContract;

@Database(
        entities = {PhStudentEntity.class},
        version = 3
)
public abstract class PhDatabase extends RoomDatabase {

    public abstract PhStudentDao studentDao();

    /**
     * Migrate from:
     * version 2 - using the SQLiteDatabase API
     * to
     * version 3 - using Room
     */
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase a_database) {
            // Since we didn't alter the table, there's nothing else to do here.

            // Create the new table
            a_database.execSQL(
                    "CREATE TABLE student_new (" +
                            "_id INTEGER NOT NULL, " +
                            "column_grade INTEGER NOT NULL, " +
                            "column_number INTEGER NOT NULL, " +
                            "column_name TEXT, " +
                            "column_age INTEGER NOT NULL, " +
                            "PRIMARY KEY(_id))"
            );

            // Copy the data
            a_database.execSQL(
                    "INSERT INTO student_new (_id, column_grade, column_number, column_name, column_age) " +
                            "SELECT _id, column_grade, column_number, column_name, column_age FROM student");

            // Remove the old table
            a_database.execSQL("DROP TABLE student");

            // Change the table name to the correct one
            a_database.execSQL("ALTER TABLE student_new RENAME TO student");
        }
    };

    private static PhDatabase INSTANCE;

    public static PhDatabase getInstance(final Context a_context) {
        if (INSTANCE == null) {
            synchronized (PhDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(a_context.getApplicationContext(),
                            PhDatabase.class, PhDatabaseContract.DATABASE_NAME)
                            .addMigrations(MIGRATION_2_3)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}