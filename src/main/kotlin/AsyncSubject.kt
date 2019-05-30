import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.AsyncSubject

fun main (args: Array<String>) {

    exampleOf("Async Subject") {

        //Creates an object to keep all disposables
        val subscriptions = CompositeDisposable()

        //Creates a new subjecy of Asyn type
        val asyncSubject = AsyncSubject.create<Int>()

        //Creates a new subscriber and stores it into the subscriptions object
        subscriptions.add(asyncSubject.subscribeBy(
                onNext = { printWithLabel("1)", it) },
                onError = { printWithLabel("2)", "Completed with error, ${it.message}") },
                onComplete = { printWithLabel("1)", "Completed with success") }
        ))

        //Makes the subject emmit some data
        asyncSubject.onNext(0)
        asyncSubject.onNext(1)
        asyncSubject.onNext(2)

        //Makes the subject emmit a "complete" event
        asyncSubject.onComplete()


        //Dispose all subscribers used so far
        subscriptions.dispose()


    }
}