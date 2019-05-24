import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

fun main(args: Array<String>) {
    exampleOf("PublishSubject") {

        //Creates a disposables object
        val disposables = CompositeDisposable()

        //Creates a Publish Subject
        val publishSubject = PublishSubject.create<Int>()

        //Makes the subject emmit a integer
        publishSubject.onNext(0)

        //Creates a subscriber and subscribes it to the previously created subject
        val subscriptionOne = publishSubject
                .doOnDispose { printWithLabel("1)", "Disposed") }
                .subscribeBy(
                onNext = { printWithLabel("1)", it) },
                onError = { printWithLabel("1)", "Completed with error") },
                onComplete = { printWithLabel("1)", "Completed with success") }
        )

        disposables.add(subscriptionOne)

        //Make the subject emmit a integer
        publishSubject.onNext(1)

        //Creates a subscriber and subscribes it to the previously created subject
        val subscriptionTwo = publishSubject
                .doOnDispose { printWithLabel("2)", "Disposed") }
                .subscribeBy(
                onNext = { printWithLabel("2)", it) },
                onError = { printWithLabel("2)", "Completed with error") },
                onComplete = { printWithLabel("2)", "Completed with success") }
        )

        disposables.add(subscriptionTwo)

        //Make the subject emmit a integer, by doing this, both subscribers created so far will be called
        publishSubject.onNext(2)

        //Makes the first subscriber created stop receiving event from the subject sequence strean
        subscriptionOne.dispose()

        //Make the subject emmit a integer, since the first subscriber is disposed, only the second subscriber will receive the next event
        publishSubject.onNext(3)

        //Terminates the sequence with a complete event
        //Since this moment, subscriptions will no longer receive any other events but terminal events
        //New subscriber will receive again the terminal events
        publishSubject.onComplete()

        //Make the subject emmit a integer, since the first subscriber is disposed, only the second subscriber will receive the next event
        publishSubject.onNext(5)

        //Makes the second subscrber stop receiving event from the subject sequence stream
        //Since the subject is terminated, the subscription will not print the action defined on "doOnDisposed"
        subscriptionTwo.dispose()

        //Creates a subscriber and subscribes it to the previously created subject
        //This subscription will also receive terminal events
        val subscriptionThree = publishSubject
                .doOnDispose { printWithLabel("3)", "Disposed") }
                .subscribeBy(
                onNext = { printWithLabel("3)", it) },
                onError = { printWithLabel("3)", "Completed with error") },
                onComplete = { printWithLabel("3)", "Completed with success") }
        )

        disposables.add(subscriptionThree)

        //Make the subject emmit a integer, since the first subscriber is disposed, only the second subscriber will receive the next event
        publishSubject.onNext(6)

        disposables.dispose()


    }
}