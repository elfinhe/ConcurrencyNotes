package org.concurrencynotes.examples.chapter06.item01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
=========================================
Intermediate operations:
1. Always return streams.
2. Lazily executed.
=========================================
IntStream 	map(IntUnaryOperator mapper)
Returns a stream consisting of the results of applying the given function to the elements of this stream.

IntStream 	filter(IntPredicate predicate)
Returns a stream consisting of the elements of this stream that match the given predicate.

IntStream 	distinct()
Returns a stream consisting of the distinct elements of this stream.

IntStream 	sorted()
Returns a stream consisting of the elements of this stream in sorted order.

IntStream 	peek(IntConsumer action)
Returns a stream consisting of the elements of this stream, additionally performing the provided action on each element
as elements are consumed from the resulting stream.

==============================================================
Terminal operations:
1. Return concrete types or produce a side effect.
2. Eagerly executed.
==============================================================
OptionalInt 	reduce(IntBinaryOperator op)
Performs a reduction on the elements of this stream, using an associative accumulation function, and returns an
OptionalInt describing the reduced value, if any.

int 	reduce(int identity, IntBinaryOperator op)
Performs a reduction on the elements of this stream, using the provided identity value and an associative accumulation
function, and returns the reduced value.

<R> R 	collect(Supplier<R> supplier, ObjIntConsumer<R> accumulator, BiConsumer<R,R> combiner)
Performs a mutable reduction operation on the elements of this stream.

void 	forEach(IntConsumer action)
Performs an action for each element of this stream.

==============================================================
Parallel streams
==============================================================
IntStream 	parallel()
Returns an equivalent stream that is parallel.

IntStream   parallelStream()
Returns a possibly parallel stream with this collection as its source.  It is allowable for this method to return a
sequential stream.

==============================================================
Useful operations
==============================================================
static <T,K,A,D> Collector<T,?,Map<K,D>> 	groupingBy(Function<? super T,? extends K> classifier,
                                                        Collector<? super T,A,D> downstream)
Returns a Collector implementing a cascaded "group by" operation on input elements of type T, grouping elements
according to a classification function, and then performing a reduction operation on the values associated with a given
key using the specified downstream Collector.

static IntStream 	range(int startInclusive, int endExclusive)
Returns a sequential ordered IntStream from startInclusive (inclusive) to endExclusive (exclusive) by an incremental
step of 1.

static IntStream 	iterate(int seed, IntUnaryOperator f)
Returns an infinite sequential ordered IntStream produced by iterative application of a function f to an initial element
seed, producing a Stream consisting of seed, f(seed), f(f(seed)), etc.

OptionalInt 	max()
Returns an OptionalInt describing the maximum element of this stream, or an empty optional if this stream is empty.

OptionalInt 	min()
Returns an OptionalInt describing the minimum element of this stream, or an empty optional if this stream is empty.

IntStream 	flatMap(IntFunction<? extends IntStream> mapper)
Returns a stream consisting of the results of replacing each element of this stream with the contents of a mapped stream
produced by applying the provided mapping function to each element.


Function    Count   Type    Order
map         ○       ×       ○
filter      ×       ○       ○
distinct    ×       ○       ○
sorted      ○       ○       ×
peek        ○       ○       ○

○: preserve
×: not preserve

*/

public class StreamExamples {
	public static void main(String[] args) throws InterruptedException {
		ArrayList<Integer> data = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
        data.parallelStream();

        IntStream.range(0, 1000)
                 .map((n) -> n * n)
                 .filter((n) -> n%2 == 1)
                 .distinct()
                 .limit(15)
                 .reduce(0, Integer::sum);


        Map<Integer, Set<Integer>> result = data.stream()
            .collect(Collectors.groupingByConcurrent(n -> n, Collectors.toSet()));

        IntStream.iterate(0, e -> e + 1);
        IntStream.range(1, 1000000).max();

        data.stream().parallel()
            .map(n -> new ArrayList(Arrays.asList(n, 2*n, 3*n)))
            .flatMap(array -> array.stream())
            .collect(Collectors.toList());
	}
}























