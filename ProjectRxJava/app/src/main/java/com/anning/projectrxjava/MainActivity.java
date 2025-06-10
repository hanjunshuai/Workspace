package com.anning.projectrxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.UnicastSubject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 200, 400, 600, 800, 1000 毫秒依次输出了 1 到 5
     */
    public void testSubject(View view) {
        PublishSubject<Integer> subject = PublishSubject.create();
        subject.subscribe(System.out::println);

        Executor executor = Executors.newFixedThreadPool(5);
        Disposable disposable = Observable.range(1, 5)
                .subscribe(integer -> {
                    executor.execute(() -> {
                        try {
                            Thread.sleep(integer * 200);
                            subject.onNext(integer);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                });
    }

    /**
     * 1、PublishSubject: 不会改变事件的发送顺序；在已经发送了一部分事件之后注册的 Observer 不会收到之前发送的事件。
     * 输出 ：(1: 1) (1: 2) (1: 3) (2: 3) (1: 4) (2: 4) (1: 5) (2: 5)
     */
    public void testPublishSubject(View view) throws InterruptedException {
        PublishSubject<Integer> subject = PublishSubject.create();
        subject.subscribe(integer -> {
            System.out.println("(1:" + integer + ")");
        });
        Executor executor = Executors.newFixedThreadPool(5);
        Disposable disposable = Observable.range(1, 5)
                .subscribe(integer -> {
                            executor.execute(() -> {
                                try {
                                    Thread.sleep(integer * 200);
                                    subject.onNext(integer);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                );

        Thread.sleep(500);
        subject.subscribe(integer -> {
            System.out.println("(2:" + integer + ")");
        });

        Observable.timer(2, TimeUnit.SECONDS).subscribe(i -> ((ExecutorService) executor).shutdown());
    }

    /**
     * 2、ReplaySubject: 无论什么时候注册 Observer 都可以接收到任何时候通过该 Observable 发射的事件。
     * 输出：(1: 1) (1: 2) (2: 1) (2: 2) (1: 3) (2: 3) (1: 4) (2: 4) (1: 5) (2: 5)
     */
    public void testReplaySubject(View view) throws InterruptedException {
        ReplaySubject<Integer> subject = ReplaySubject.create();
        subject.subscribe(integer -> {
            System.out.println("(1:" + integer + ")");
        });

        Executor executor = Executors.newFixedThreadPool(5);
        Disposable disposable = Observable.range(1, 5)
                .subscribe(integer -> {
                    executor.execute(() -> {
                        try {
                            Thread.sleep(integer * 200);
                            subject.onNext(integer);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                });

        Thread.sleep(500);
        subject.subscribe(integer -> {
            System.out.println("(2:" + integer + ")");
        });
        Observable.timer(2, TimeUnit.SECONDS).subscribe(i -> ((ExecutorService) executor).shutdown());
    }

    /**
     * 3、AsyncSubject: 只有当 Subject 调用 onComplete() 方法时，才会将 Subject 中的最后一个事件传递给所有的 Observer。
     */
    public void testAsyncSubject(View view) throws InterruptedException {
        AsyncSubject<Integer> subject = AsyncSubject.create();

        subject.subscribe(integer -> {
            System.out.println("(1:" + integer + ")");
        });

        Executor executor = Executors.newFixedThreadPool(5);
        Disposable disposable = Observable.range(1, 5)
                .subscribe(integer -> {
                    executor.execute(() -> {
                        try {
                            Thread.sleep(integer * 200);
                            subject.onNext(integer);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                });

        Thread.sleep(500);
        subject.subscribe(integer -> {
            System.out.println("(2:" + integer + ")");
        });

        subject.onComplete();
        Observable.timer(2, TimeUnit.SECONDS).subscribe(i -> ((ExecutorService) executor).shutdown());
    }

    /**
     * 4、BehaviorSubject: 该类有创建时需要一个默认参数，该默认参数会在 Subject 未发送过其他的事件时，向注册的 Observer 发送；
     * <p>
     * 新注册的 Observer 会收到注册之前最新发送的一次事件。
     */
    public void testBehaviorSubject(View view) throws InterruptedException {
        BehaviorSubject<Integer> subject = BehaviorSubject.create();
        subject.subscribe(integer -> {
            System.out.println("(1:" + integer + ")");
        });

        Executor executor = Executors.newFixedThreadPool(5);
        Disposable disposable = Observable.range(1, 5)
                .subscribe(integer -> {
                    executor.execute(() -> {
                        try {
                            Thread.sleep(integer * 200);
                            subject.onNext(integer);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                });

        Thread.sleep(500);
        subject.subscribe(integer -> {
            System.out.println("(2:" + integer + ")");
        });

        Observable.timer(2, TimeUnit.SECONDS).subscribe(i -> ((ExecutorService) executor).shutdown());
    }

    /**
     * 5、UnicastSubject: 只允许一个 Observer 进行监听，在该 Observer 注册之前会将发射的所有的事件放进一个队列中，并在 Observer 注册的时候一起通知给它。
     */
    public void testUnicastSubject(View view) throws InterruptedException {
        UnicastSubject<Integer> subject = UnicastSubject.create();
        subject.subscribe(integer -> {
            System.out.println("(1:" + integer + ")");
        });


        Executor executor = Executors.newFixedThreadPool(5);
        Disposable disposable = Observable.range(1, 5)
                .subscribe(integer -> {
                    executor.execute(() -> {
                        try {
                            Thread.sleep(integer * 200);
                            subject.onNext(integer);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                });


        Observable.timer(2, TimeUnit.SECONDS).subscribe(i -> ((ExecutorService) executor).shutdown());
    }
}