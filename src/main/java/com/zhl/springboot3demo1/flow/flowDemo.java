package com.zhl.springboot3demo1.flow;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.CountDownLatch;

public class flowDemo {
    static class MyProcessor extends SubmissionPublisher<String> implements Flow.Processor<String,String>{

        private Flow.Subscription subscription;
        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            System.out.println(Thread.currentThread()+"订阅开始了"+subscription);
            this.subscription = subscription;
            subscription.request(1);
        }

        @Override
        public void onNext(String item) {
            System.out.println(Thread.currentThread()+"接收到数据"+item);
            item = "haha"+item;
            submit(item);
            subscription.request(Long.MAX_VALUE);
        }

        @Override
        public void onError(Throwable throwable) {
            System.out.println("出错了");
        }

        @Override
        public void onComplete() {
            System.out.println("完成了");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        flowDemo.mainaaa();
    }
    public static void mainbbb(String[] args) throws InterruptedException {
        //1.定义一个发布者,发布数据
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        //2.
        MyProcessor processor1 = new MyProcessor();
        MyProcessor processor2 = new MyProcessor();
        MyProcessor processor3 = new MyProcessor();
        //3.
        Flow.Subscriber<String> subscriber = new Flow.Subscriber<String>() {
            @Override
            public void onSubscribe(Flow.Subscription subscription) {

            }

            @Override
            public void onNext(String item) {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        };
        publisher.subscribe(processor1);
        processor1.subscribe(processor2);
        processor2.subscribe(processor3);
        processor3.subscribe(subscriber);
        for (int i = 0; i < 10; i++) {
            publisher.submit("p-"+i);

        }
        publisher.close();
        Thread.sleep(2000);
    }
    public static void mainaaa() throws InterruptedException {
        //1.定义一个发布者,发布数据
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        CountDownLatch latch = new CountDownLatch(1);

        //2.定义接收者
        Flow.Subscriber<String> subscriber = new Flow.Subscriber<String>() {
            private Flow.Subscription subscription;
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                System.out.println(Thread.currentThread()+"订阅开始了"+subscription);
                this.subscription = subscription;
                subscription.request(1);
            }

            @Override
            public void onNext(String item) {
                System.out.println(Thread.currentThread()+"接收到数据"+item);
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(Thread.currentThread()+"出现错误了"+throwable);
            }

            @Override
            public void onComplete() {
                System.out.println(Thread.currentThread()+"完成了");
            }
        };
        //3.绑定数据
        publisher.subscribe(subscriber);
        //4.发布数据
        for (int i = 0; i < 10; i++) {
            publisher.submit("p-"+i);
            //发布的所i有数据在buffer区
        }
        publisher.close();;
        //主线程不能结束
//        Thread.sleep(20000);
        latch.await(); // 等待数据流处理完成
        System.out.println("主线程结束");
    }
}
