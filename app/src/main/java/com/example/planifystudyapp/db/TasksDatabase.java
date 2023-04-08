package com.example.planifystudyapp.db;

import android.content.Context;
import android.os.Handler;

import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {Tasks.class}, version = 1,exportSchema = false)
public abstract class TasksDatabase extends RoomDatabase {

    public interface TaskListener {
        void onTaskReturned(Tasks tasks);
    }

    public abstract TaskDao taskDao();

    private static TasksDatabase INSTANCE;

    public static TasksDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (TasksDatabase.class){
                if(INSTANCE == null)
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TasksDatabase.class, "tasks_database")
                            .addCallback(createTasksDatabaseCallBack)
                            .build();
                }
            }
        return INSTANCE;
    }
    private static RoomDatabase.Callback createTasksDatabaseCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            createTasksTable();
        }
    };

    public static void getTask(int id, TaskListener listener){
        Handler handler = new Handler(Looper.getMainLooper()) {

            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                listener.onTaskReturned((Tasks) msg.obj);

            }
        };
        (new Thread(()->{
            Message msg = handler.obtainMessage();
            msg.obj = INSTANCE.taskDao().getById(id);
            handler.sendMessage(msg);

        })).start();
    }
    public static void insert(Tasks task) {
        (new Thread(()-> INSTANCE.taskDao().insert(task))).start();
    }

    public static void delete(int taskid) {
        (new Thread(() -> INSTANCE.taskDao().delete(taskid))).start();
    }


    public static void update(Tasks task) {
        (new Thread(() -> INSTANCE.taskDao().update(task))).start();
    }

   // public Tasks(int id,String title, String date, String description ,int priority, boolean isCompleted){

    private static void createTasksTable() {
       // for (int i = 0; i < 1; i++) {
            insert(new Tasks(0,"task1", "10/21/2000","First Task",1,true));
       // }
    }








}
