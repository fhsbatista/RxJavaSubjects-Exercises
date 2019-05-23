import io.reactivex.subjects.PublishSubject

fun main(args: Array<String>) {
    exampleOf("PublishSubject") {
        //Creates a Publish Subject
        val publishSubject = PublishSubject.create<Int>()

        //Makes the subject emmit a integer
        publishSubject.onNext(0)

        //Creates a subscriber and subscribes it to the previously created subject
        val subscriptionOne = publishSubject.subscribe { int -> println(int) }

        //Make the subject emmit a integer
        publishSubject.onNext(1)

        //Creates a subscriber and subscribes it to the previously created subject
        val subscriptionTwo = publishSubject.subscribe { int -> printWithLabel("2)", int)}

        //Make the subject emmit a integer, by doing this, both subscribers created so far will be called
        publishSubject.onNext(2)

        //Makes the first subscriber created stop receiving event from the subject sequence strean
        subscriptionOne.dispose()

        //Make the subject emmit a integer, since the first subscriber is disposed, only the second subscriber will receive the next event
        publishSubject.onNext(3)
    }
}