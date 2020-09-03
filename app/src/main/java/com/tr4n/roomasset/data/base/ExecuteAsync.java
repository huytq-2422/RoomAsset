package com.tr4n.roomasset.data.base;

import android.os.AsyncTask;

public class ExecuteAsync<Param, Result> extends AsyncTask<Param, Void, Result> {

    private OnExecuteListener<Param, Result> executeListener;
    private OnDataLoadedListener<Result> onDataLoadedListener;
    private Exception exception = new Exception();

    public ExecuteAsync<Param, Result> onExecute(OnExecuteListener<Param, Result> executeListener) {
        this.executeListener = executeListener;
        return this;
    }

    public ExecuteAsync setOnDataLoadedListener(OnDataLoadedListener<Result> onDataLoadedListener) {
        this.onDataLoadedListener = onDataLoadedListener;
        return this;
    }

    @Override
    protected Result doInBackground(Param... params) {
        try{
            Param param = params.length == 0 ? null : params[0];
            return executeListener.onExecute(param);
        }catch (Exception e){
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Result result) {
        if(result == null){
            onDataLoadedListener.onFailure(exception);
        } else {
            onDataLoadedListener.onSuccess(result);
        }
    }

    public interface OnExecuteListener<Input, Output> {
        Output onExecute(Input input);
    }
    public interface OnDataLoadedListener<T> {
        void onSuccess(T data);

        void onFailure(Exception e);
    }
}