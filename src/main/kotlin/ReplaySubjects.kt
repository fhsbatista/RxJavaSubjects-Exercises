import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.ReplaySubject

fun main(args: Array<String>) {


    exampleOf("Replay Subjecy") {

        /*
        In this examples, it is expected that only the second and third events emitted will be shown,
        cause we are creating a subject that has 2 as its buffer size, therefore the first element will not be held.
         */

        //Creates a disaposables object to be able to dispose all subscribers at once later
        val subscriptions = CompositeDisposable()

        //Creates a subject of "Replay" type, with 2 as its buffer max size
        val replaySubject = ReplaySubject.createWithSize<String>(2)

        //Makes the previously created subject emmit 3 events
        replaySubject.onNext("1")
        replaySubject.onNext("2")
        replaySubject.onNext("3")

        //Created a subscriber to the subject and add it to the disposables object
        subscriptions.add(replaySubject.subscribeBy(
                onNext = { printWithLabel("1)", it) },
                onComplete = { printWithLabel("1)", "Replay subject has been completed")},
                onError = { printWithLabel("1)", it) }
        ))

        //Created a second subscriber to the subject and add it to the disposables object
        subscriptions.add(replaySubject.subscribeBy(
                onNext = { printWithLabel("2)", it) },
                onComplete = { printWithLabel("2)", "Replay subject has been completed")},
                onError = { printWithLabel("2)", it) }
        ))

        replaySubject.onNext("4")
        replaySubject.onError(RuntimeException(" Error !"))


        //This new subcriber will receiver the event "4" and then will receive an error event
        //Even though the subject is terminated, since the buffer still has that element emitted before the error, the subscriber will receive that element.
        subscriptions.add(replaySubject.subscribeBy(
                onNext = { printWithLabel("3)", it) },
                onComplete = { printWithLabel("3)", "Replay subject has been completed")},
                onError = { printWithLabel("3)", it) }
        ))

        //Dispose all subscribers
        subscriptions.dispose()




    }
}