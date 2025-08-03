package oak.newfeature;

import cn.qingweico.io.Print;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * @author zqw
 * @date 2023/12/9
 */
class Jdk9Flow {
    public static void main(String[] args) throws InterruptedException {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        CountDownLatch latch = new CountDownLatch(1);
        Flow.Subscriber<String> subscriber = new Flow.Subscriber<>() {
            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                subscription.request(1);
            }

            @Override
            public void onNext(String item) {
                Print.grace("Received", item);
                subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                Print.grace("Error", throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onComplete() {
                Print.println("Done!");
                latch.countDown();
            }
        };
        publisher.subscribe(subscriber);
        publisher.submit("Message 1");
        publisher.submit("Message 2");
        publisher.submit("Message 3");
        publisher.close();
        latch.await();
    }
}
