package course.concurrency.m2_async.cf.min_price;

import lombok.SneakyThrows;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PriceAggregator {

    private PriceRetriever priceRetriever = new PriceRetriever();

    public void setPriceRetriever(PriceRetriever priceRetriever) {
        this.priceRetriever = priceRetriever;
    }

    private Collection<Long> shopIds = Set.of(10l, 45l, 66l, 345l, 234l, 333l, 67l, 123l, 768l);

    public void setShops(Collection<Long> shopIds) {
        this.shopIds = shopIds;
    }

    public double getMinPrice(long itemId) {
        var executor = Executors.newCachedThreadPool();
        var cfs = shopIds.stream()
                .map(shopId -> CompletableFuture.supplyAsync(() -> priceRetriever
                        .getPrice(itemId, shopId),executor)
                        .exceptionally(ex-> Double.NaN)
                        .completeOnTimeout(Double.NaN,2900, TimeUnit.MILLISECONDS))
                .toArray(CompletableFuture[]::new);

        var min = Stream.of(cfs)
                .map(CompletableFuture<Double>::join)
                .peek(System.out::println)
                .min(Double::compareTo).get();

        return min;
    }
}
