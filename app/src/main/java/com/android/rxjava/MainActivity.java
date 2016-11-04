package com.android.rxjava;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.rxjava.Student.Course;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String tag="--";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flatmapMethod();
    }

    private void flatmapMethod(){
        Student student1 =new Student("pek",15,new Course[]{new Course("A"),new Course("J")});
        Student student2 =new Student("kjg",42,new Course[]{new Course("G"),new Course("U")});
        Student[] students=new Student[]{student1,student2};
        Observable.from(students)
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        return Observable.from(student.getCourse());
                    }
                })
                .subscribe(new Action1<Course>() {
                    @Override
                    public void call(Course course) {
                        Log.d(tag, course.getCourseName());
                    }
                });
    }

    private void mapMethod(){
        final int drawableRes=R.mipmap.ic_launcher;
        final ImageView imageView= (ImageView) findViewById(R.id.imageView);
        Observable.just(drawableRes)
                .map(new Func1<Integer, Drawable>() {
                    @Override
                    public Drawable call(Integer integer) {
                        return getResources().getDrawable(drawableRes);
                    }
                })
                .subscribe(new Action1<Drawable>() {
            @Override
            public void call(Drawable drawable) {
                imageView.setImageDrawable(drawable);
            }
        });
    }

    private void schedulerMethod(){
        Observable.just(1,2,3,4)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d(tag, "number:" + integer);
                    }
                });
    }

    @TargetApi(21)
    private void printImage(){
        final int drawableRes=R.mipmap.ic_launcher;
        final ImageView imageView= (ImageView) findViewById(R.id.imageView);
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable=getTheme().getDrawable(drawableRes);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribe(new Subscriber<Drawable>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNext(Drawable drawable) {
                imageView.setImageDrawable(drawable);
            }
        });


    }


    private void printCharacter(){
        String[] name={"ali","lik","oep"};
        Observable.from(name)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(tag, s);
                    }
                });
    }

    private void subscriberMethod(){
        observable.subscribe(observer);
    }


    private void actionMethod(){
        // 自动创建 Subscriber ，并使用 onNextAction 来定义 onNext()
        //observable.subscribe(onNextAction);
        // 自动创建 Subscriber ，并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
        //observable.subscribe(onNextAction, onErrorAction);
        // 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);
    }

    Observer<String> observer = new Observer<String>() {
        @Override
        public void onNext(String s) {
            Log.d(tag, "Item: " + s);
        }

        @Override
        public void onCompleted() {
            Log.d(tag, "Completed!");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(tag, "Error!");
        }
    };
    Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onNext(String s) {
            Log.d(tag, "Item: " + s);
        }

        @Override
        public void onCompleted() {
            Log.d(tag, "Completed!");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(tag, "Error!");
        }

    };

    Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("Hello");
            subscriber.onNext("Hi");
            subscriber.onNext("Aloha");
            subscriber.onCompleted();
        }
    });

//     Observable observable = Observable.just("Hello", "Hi", "Aloha");
//     将会依次调用：
//     onNext("Hello");
//     onNext("Hi");
//     onNext("Aloha");
//     onCompleted();

//     String[] words = {"Hello", "Hi", "Aloha"};
//     Observable observable = Observable.from(words);
//     将会依次调用：
//     onNext("Hello");
//     onNext("Hi");
//     onNext("Aloha");
//     onCompleted();


    Action1<String> onNextAction = new Action1<String>() {
        // onNext()
        @Override
        public void call(String s) {
            Log.d(tag, s);
        }
    };
    Action1<Throwable> onErrorAction = new Action1<Throwable>() {
        // onError()
        @Override
        public void call(Throwable throwable) {
            // Error handling
        }
    };
    Action0 onCompletedAction = new Action0() {
        // onCompleted()
        @Override
        public void call() {
            Log.d(tag, "completed");
        }
    };


}
