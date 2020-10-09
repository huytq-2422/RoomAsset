package com.tr4n.roomasset.ui.main;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.tr4n.roomasset.R;
import com.tr4n.roomasset.data.db.AppDatabase;
import com.tr4n.roomasset.data.db.StoryDao;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private StoryDao storyDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    private void initData() {
        AppDatabase database = AppDatabase.getInstance(this);
        storyDao = database.storyDao();
        getStories();
        getStoryById(3);
    }

    private void getStories() {
        Disposable disposable = storyDao.getStories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stories -> {
                    Log.d(TAG, "onSuccess: " + stories);
                }, error -> {
                    Log.e(TAG, "onError: ", error);
                });
        compositeDisposable.add(disposable);
    }

    private void getStoryById(int id) {
        Disposable disposable = storyDao.getStoryById(id)
                /*
                Những câu lệnh viết trước .subscribeOn(Schedulers.io()) sẽ được thực hiện dưới background
                 */
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                /*
                Những câu lệnh viết sau  .observeOn(AndroidSchedulers.mainThread()) sẽ được thực hiện trên UI Thread
                 */
                .subscribe(stories -> { // onSuccess (rút gọn anonymous class -> landa, hoạt động với java 8)
                    Log.d(TAG, "onSuccess: " + stories);
                }, error -> { // onError
                    Log.e(TAG, "onError: ", error);
                });
        compositeDisposable.add(disposable); // Dòng này có tác dụng gộp các task đã thực hiện xong để kết thúc subcribe cùng một lúc
    }
}
