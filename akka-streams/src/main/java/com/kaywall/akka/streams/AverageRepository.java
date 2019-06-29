package com.kaywall.akka.streams;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * sink
 */
public class AverageRepository {
    CompletionStage<Double> save(Double average) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("saving average: " + average);
            return average;
        });
    }
}
