List of problems

1. Caching number of requests grouped by IP address. (concurent hash map)                   
    https://dzone.com/articles/how-concurrenthashmap-works-internally-in-java
2. Implementation of Bounded Blocking Queue (producer consumer problem)                     Done
3. FizzBuzz multithreaded (running code)                                                    Done
4. The dining philosphers problem  (with and without deadlock)                              Done                 
5. Web Crawler multithreaded {uses concurrent hash map}                                     
6. Multithreaded merge sort                                                                 Done
7. Deadlock demonstration using semaphores                                                  Done
8. Coffee machine. 5 ingredients, 3 dispencers
9. Uber with republicans & democrats                                                        Done

Review key terms

Mutex, Semaphores


Readers writers problem

num_readers = 0
mutex = semaphore(1)
writerlock = semaphore(1)

def reader():
    mutex.acquire()
    num_readers += 1
    mutex.release()

    # critical section : perform read

    mutex.acquire()
    num_readers -= 1
    mutex.release()

def writer():
    mutex.acquire()
    while

    mutex.release()