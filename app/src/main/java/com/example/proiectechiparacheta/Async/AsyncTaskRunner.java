package com.example.proiectechiparacheta.Async;

import android.os.Handler;
import android.os.Looper;

import com.example.proiectechiparacheta.Async.Callback;
import com.example.proiectechiparacheta.Async.RunnableTask;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncTaskRunner {
    private static final Executor executor = Executors.newCachedThreadPool();
    private static final Handler handler = new Handler(Looper.getMainLooper());

    public static <R> void executeAsync(Callable<R> asyncOperation, Callback<R> mainThreadOperation){
        try{
            executor.execute(new RunnableTask<>(handler,asyncOperation,mainThreadOperation));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
