import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject

fun main(args: Array<String>) {
    //Creates a new subject of "Behavior Subject" type
    exampleOf("Behavior Subject") {

        //Creates a disaposables object to be able to dispose all subscribers at once later
        val subscriptions = CompositeDisposable()

        //Creates a new subject of "Behavior Subject" type
        //This subject is being created with an initial value, so any subscriber that subscribes to it before it emit an another event will receive this initial value
        //Obs: It is possible to create this kind of subject without an initial value, using the create() method instead, type has to be defined in that case.
        //ex: val behaviorSubject: BehaviorSubject<Int> = BehaviorSubject.create()
        val behaviorSubject = BehaviorSubject.createDefault("initial value")

        //Prints the last "next" event emitted by the subject, this a perfect example of how we can handle "state" of things
        println("Current state of behavior subject is : \" ${behaviorSubject.value} \" " )

        behaviorSubject.onNext("X")

        //Creates a new subscriber and also add itself into the disposables object
        subscriptions.add(behaviorSubject.subscribeBy(
                onNext = { printWithLabel("1)", it) },
                onError = { printWithLabel("2)", "Completed with error, ${it.message}") },
                onComplete = { printWithLabel("1)", "Completed with success") }
        ))



        //Terminates the subject with an error event
        behaviorSubject.onError(RuntimeException("Error!"))

        //Creates a new subscriber and also add itself into the disposables object
        //At this point, the subject has been terminated, so this subscriber will immediatelly receive an error event
        subscriptions.add(behaviorSubject.subscribeBy(
                onNext = { printWithLabel("2)", it) },
                onError = { printWithLabel("2)", "Completed with error, ${it.message}") },
                onComplete = { printWithLabel("2)", "Completed with success") }
        ))




        //Dispose all subscribers
        subscriptions.dispose()

    }
}