package com.tr4n.roomasset.data.base;

import android.os.AsyncTask;

public class BaseAsyncTask<Param, Result> extends AsyncTask<Param, Void, Result> {

    private OnExecuteListener<Param, Result> executeListener;
    private OnExecuteListener<Result, Void> onSuccessListener;
    private OnExecuteListener<Exception, Void> onFailureListener;
    private Exception exception = new Exception();

    public BaseAsyncTask<Param, Result> execute(OnExecuteListener<Param, Result> executeListener) {
        this.executeListener = executeListener;
        return this;
    }

    public BaseAsyncTask<Param, Result> onSuccess(OnExecuteListener<Result, Void> listener) {
        this.onSuccessListener = listener;
        return this;
    }

    public BaseAsyncTask<Param, Result> onFailure(OnExecuteListener<Exception, Void> listener) {
        this.onFailureListener = listener;
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
            onFailureListener.onExecute(exception);
        } else {
            onSuccessListener.onExecute(result);
        }
    }

    public interface OnExecuteListener<Input, Output> {
        Output onExecute(Input input);
    }
}
