package com.parkho.sqlite.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PhStudentRepository {

    private PhStudentDao mStudentDao;

    public PhStudentRepository(Application a_application) {
        mStudentDao = PhDatabase.getInstance(a_application).studentDao();
    }

    public LiveData<List<PhStudentEntity>> getAllStudents() {
        LiveData<List<PhStudentEntity>> students = null;
        try {
            students = new getAllUsersAsyncTask(mStudentDao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return students;
    }

    public void insert(PhStudentEntity a_studentEntity) {
        new insertAsyncTask(mStudentDao).execute(a_studentEntity);
    }

    public void deleteById(int a_id) {
        new deleteAsyncTask(mStudentDao).execute(a_id);
    }

    private static class getAllUsersAsyncTask extends AsyncTask<Void, Void, LiveData<List<PhStudentEntity>>> {
        private PhStudentDao mAsyncTaskDao;

        getAllUsersAsyncTask(PhStudentDao a_dao) {
            mAsyncTaskDao = a_dao;
        }

        @Override
        protected LiveData<List<PhStudentEntity>> doInBackground(Void... url) {
            return mAsyncTaskDao.getAllStudents();
        }
    }

    private static class insertAsyncTask extends AsyncTask<PhStudentEntity, Void, Void> {
        private PhStudentDao asyncTaskDao;

        insertAsyncTask(PhStudentDao a_dao) {
            asyncTaskDao = a_dao;
        }

        @Override
        protected Void doInBackground(final PhStudentEntity... studentEntities) {
            asyncTaskDao.insert(studentEntities[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Integer, Void, Void> {
        private PhStudentDao asyncTaskDao;

        deleteAsyncTask(PhStudentDao a_dao) {
            asyncTaskDao = a_dao;
        }

        @Override
        protected Void doInBackground(final Integer... a_id) {
            asyncTaskDao.deleteById(a_id[0]);
            return null;
        }
    }
}