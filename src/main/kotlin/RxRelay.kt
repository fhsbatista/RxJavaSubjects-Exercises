import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

fun main(args: Array<String>) {

    exampleOf("Rx Relay") {

        //Creates a disposables objects do store all the subscribers
        val subscriptions = CompositeDisposable()

        //Creates a Relay object that has the Publish subject behavior
        val publishRelay = PublishRelay.create<Int>()


        //Creates a subscriber and inserts it in the subcriptions disposable
        subscriptions.add(publishRelay.subscribeBy(
                onNext = { printWithLabel("1)", it) },
                onError = { printWithLabel("2)", "Completed with error, ${it.message}") },
                onComplete = { printWithLabel("1)", "Completed with success") }
        ))

        //Makes the relay emmit some data
        publishRelay.accept(1)
        publishRelay.accept(2)
        publishRelay.accept(3)

        //Dispose all subscribers
        subscriptions.dispose()
    }
}