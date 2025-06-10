package com.anning.projectrxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.anning.projectrxjava.entity.Person;
import com.anning.projectrxjava.entity.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observables.GroupedObservable;
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

    // --- 创建

    public void testBase(View view) {
        // 内部使用 emitter 发射了一些数据和信息（其实就相当于调用了被观察对象内部的方法，通知所有的观察者）
        Observable<Integer> observable = Observable.create(emitter -> {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
        });

        observable.subscribe(System.out::println);
    }

    /**
     * Range 、Stream 区别
     * 1、所谓的“推”和“拉”的区别：Stream 中通过从流中读取数据来实现链式操作，而 RxJava 除了 Stream 中的功能外，
     * 还可以通过发射数据，来实现通知功能，即 RxJava 在 Stream 上多了一个观察者。
     * 2、Java8 中的 Stream 可以通过 parall() 来实现并行，即基于分治算法将任务分解并计算得到结果之后将结果合并起来；而 RxJava 只能通过 subscribeOn() 方法将所有的操作切换到某个线程中去。
     * 3、Stream 只能被消费一次，但是 Observable 可以被多次进行订阅
     */
    public void testRange(View view) {
        // Observable.range() 方法产生一个序列，然后用 map() 方法将该整数序列映射成一个字符序列，最后将得到的序列输出来。
        Observable.range(1, 5).map(String::valueOf).forEach(System.out::println);
    }

    public void testInterval(View view) {
        // Observable.interval(3, TimeUnit.SECONDS).subscribe(System.out::println);


        //@参数 start 范围的起始值
        //@参数 count 总共要发出的值的数量，如果为零，则在初始延迟后发出完成。
        //@参数 initialDelay 在发出第一个值(开始)之前的时间延迟
        //@参数 period 各个值之间的间隔
        //@参数 unit 初始延迟和周期量的度量单位
        System.out.println("start : " + DateUtil.long2String(System.currentTimeMillis(), DateUtil.DATE_PATTERN));
        Observable.intervalRange(3, 3, 1, 3, TimeUnit.SECONDS).subscribe(aLong -> {
            System.out.println(DateUtil.long2String(System.currentTimeMillis(), DateUtil.DATE_PATTERN) + " , " + aLong);
        });
    }

    public void testDefer(View view) {
        Observable<Long> observable = Observable.defer((Callable<ObservableSource<Long>>) () -> Observable.just(System.currentTimeMillis()));
        observable.subscribe(System.out::println);
        System.out.println();
        observable.subscribe(System.out::println);
    }

    public void testEmpty(View view) {
        // 创建一个不发射任何数据但是正常终止的 Observable；
//        Observable.empty().subscribe(o -> System.out.println("next"), throwable -> System.out.println("error"), () -> System.out.println("complete"));
        // 创建一个不发射数据也不终止的 Observable；
//        Observable.never().subscribe(i -> System.out.println("next"), i -> System.out.println("error"), () -> System.out.println("complete"));
        // 创建一个不发射数据以一个错误终止的 Observable
        Observable.error(new Exception("null"))
                .subscribe(i -> System.out.println("next"), i -> System.out.println("error"), () -> System.out.println("complete"));
    }

    public void testFrom(View view) {

    }

    public void testRepeat(View view) {
        Observable.range(5, 10).repeat().subscribe(i -> System.out.println(i));
    }

    // --- 变换

    /**
     * 1、map 操作符对原始 Observable 发射的每一项数据应用一个你选择的函数，然后返回一个发射这些结果的 Observable。默认不在任何特定的调度器上执行。
     * 2、cast 操作符将原始 Observable 发射的每一项数据都强制转换为一个指定的类型（多态），然后再发射数据，它是 map 的一个特殊版本。
     */
    public void testMap(View view) {
        Observable.range(5, 10).map(String::valueOf).subscribe(System.out::println);
        Observable.just(new Date()).cast(Object.class).subscribe(System.out::println);
    }

    /**
     * flatMap 将一个发送事件的上游 Observable 变换为多个发送事件的 Observables，然后将它们发射的事件合并后放进一个单独的 Observable 里。
     * 需要注意的是, flatMap() 并不保证事件的顺序，也就是说转换之后的 Observables 的顺序不必与转换之前的序列的顺序一致。
     * 比如下面的代码用于将一个序列构成的整数转换成多个单个的 Observable，然后组成一个 Observable，并被订阅。
     * 下面输出的结果仍将是一个字符串数字序列，只是顺序不一定是增序的
     */
    public void testFlatMap(View view) {
        Observable.range(5, 10)
                .flatMap((Function<Integer, ObservableSource<String>>) integer -> Observable.just(String.valueOf(integer)))
                .subscribe(System.out::println);
    }

    /**
     * 与 flatMat 一样，contactMap() 能够保证最终输出的顺序与上游发送的顺序一致
     *
     * @param view
     */
    public void testConcatMap(View view) {
        Observable.range(5, 10)
                .concatMap((Function<Integer, ObservableSource<String>>) integer -> Observable.just(String.valueOf(integer)))
                .subscribe(System.out::println);
    }

    /**
     * flatMapIterable() 可以用来将上流的任意一个元素转换成一个 Iterable 对象，然后我们可以对其进行消费。
     * 在下面的代码中，我们先生成一个整数的序列，然后将每个整数映射成一个 Iterable<string> 类型，最后，我们对其进行订阅和消费：
     */
    public void testFlatMapIterable(View view) {
        Observable.range(1, 5)
                .flatMapIterable((Function<Integer, Iterable<String>>) integer -> Collections.singletonList(String.valueOf(integer)))
                .subscribe(s -> System.out.println(s));
    }

    /**
     * buffer用于将整个流进行分组。
     * 以下面的程序为例，我们会先生成一个 7 个整数构成的流，然后使用 buffer() 之后，这些整数会被 3 个作为一组进行输出，
     * 所以当我们订阅了 buffer() 转换之后的 Observable 之后得到的是一个列表构成的 Observable：
     */
    public void testBuffer(View view) {
        Observable.range(1, 7).buffer(3).subscribe(System.out::println);
    }

    /**
     * groupBy() 用于分组元素，它可以被用来根据指定的条件将元素分成若干组。它将得到一个 Observable<GroupedObservable<T, M>> 类型的 Observable。
     * 如下面的程序所示，这里我们使用 concat() 方法先将两个 Observable 拼接成一个 Observable，然后对其元素进行分组。
     * 这里我们的分组依据是整数的值，这样我们将得到一个 Observable<GroupedObservable<Integer, Integer>>类型的 Observable。
     * 然后，我们再将得到的序列拼接成一个并进行订阅输出。
     */
    public void testGroupBy(View view) {
        Observable<GroupedObservable<Integer, Integer>> observable = Observable.concat(Observable.range(1, 4), Observable.range(1, 6))
                .groupBy(integer -> integer);

        observable.subscribe(integerIntegerGroupedObservable -> {
            System.out.println("key : " + integerIntegerGroupedObservable.getKey());
        });

        Observable.concat(observable).subscribe(System.out::println);
    }

    public void testList(View view) {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            people.add(new Person(i, "name " + i));
        }

        for (int i = 1; i < 10; i++) {
            people.add(new Person(i, "name : " + i + i));
        }


        List<Person> users = Arrays.asList(new Person(1, "Alice"), new Person(2, "Bob"), new Person(1, "Alice Jr."), new Person(3, "Charlie"));

        Observable.fromIterable(users)
                .groupBy(person -> person.id)
                .subscribe(integerPersonGroupedObservable -> System.out.println(integerPersonGroupedObservable.getKey()));


        Observable.fromIterable(people).groupBy(person -> person.id) // 根据ID分组
                .flatMap(group -> group.toList().toObservable() // 将每个分组转换为一个包含所有元素的Observable列表
                        .map(usersInGroup -> "Group ID: " + group.getKey() + ", Users: " + usersInGroup)) // 对每个分组进行处理
                .subscribe(System.out::println);

        Observable.fromIterable(people).groupBy(person -> person.id) // 根据ID分组
                .flatMap(group -> group.toList().toObservable() // 将每个分组转换为一个包含所有元素的Observable列表
                        .map(groupPeople -> groupPeople)) // 对每个分组进行处理
                .subscribe(people1 -> System.out.println(people1));
    }

    public void testScan(View view) {
        Observable.range(1, 6).scan((i1, i2) -> i1 * i2).subscribe(System.out::println);
    }

    public void testWindow(View view) {
        Observable.range(1, 10)
                .window(3)
                .subscribe(observable -> observable.subscribe(integer -> System.out.println(observable.hashCode() + " : " + integer)));
    }

    // --- 过滤操作
    public void testFilter(View view) {
        Observable.range(1, 10).filter(integer -> integer % 2 == 0).subscribe(System.out::println);
    }

    /**
     * elementAt & firstElement & lastElement
     */
    public void testElement(View view) {
        Observable.range(1, 10)
                .elementAt(11)
                .subscribe(integer -> System.out.println(integer), throwable -> System.out.println("not find element"), () -> System.out.println("complete"));
        Observable.range(1, 10).firstElement().subscribe(System.out::println);
        Observable.range(1, 10).lastElement().subscribe(System.out::println);
    }

    public void testDistinct(View view) {
        // 去重
        // Observable.just(1, 2, 3, 4, 5, 6, 7, 7).distinct().subscribe(System.out::println);

        // 当相邻的两个元素相同的时候才会将它们过滤掉
        Observable.just(1, 2, 2, 3, 4, 5, 5, 6, 7, 6)
                .distinctUntilChanged()
                .subscribe(System.out::println);
    }

    public void testTask(View view) {
        Observable.range(1, 10).take(2).subscribe(System.out::println);

        long currentTimeMillis = System.currentTimeMillis();
        Observable.range(1, 10)
                .repeatUntil(() -> System.currentTimeMillis() - currentTimeMillis > TimeUnit.SECONDS.toMillis(5))
                .takeLast(2)
                .subscribe(System.out::println);
    }

    /**
     * 200, 400, 600, 800, 1000 毫秒依次输出了 1 到 5
     */
    public void testSubject(View view) {
        PublishSubject<Integer> subject = PublishSubject.create();
        subject.subscribe(System.out::println);

        Executor executor = Executors.newFixedThreadPool(5);
        Disposable disposable = Observable.range(1, 5).subscribe(integer -> {
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
        Disposable disposable = Observable.range(1, 5).subscribe(integer -> {
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

        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(i -> ((ExecutorService) executor).shutdown());
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
        Disposable disposable = Observable.range(1, 5).subscribe(integer -> {
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
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(i -> ((ExecutorService) executor).shutdown());
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
        Disposable disposable = Observable.range(1, 5).subscribe(integer -> {
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
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(i -> ((ExecutorService) executor).shutdown());
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
        Disposable disposable = Observable.range(1, 5).subscribe(integer -> {
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

        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(i -> ((ExecutorService) executor).shutdown());
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
        Disposable disposable = Observable.range(1, 5).subscribe(integer -> {
            executor.execute(() -> {
                try {
                    Thread.sleep(integer * 200);
                    subject.onNext(integer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });


        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(i -> ((ExecutorService) executor).shutdown());
    }
}