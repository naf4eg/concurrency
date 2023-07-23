package course.concurrency.exams.auction;

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;

public class AuctionStoppableOptimistic implements AuctionStoppable {

    private Notifier notifier;

    public AuctionStoppableOptimistic(Notifier notifier) {
        this.notifier = notifier;
        latestBid = new AtomicMarkableReference<>(new Bid(-1L, -1L, -1L), false);
    }

    private AtomicMarkableReference<Bid> latestBid;

    public boolean propose(Bid bid) {
       if (latestBid.isMarked()) {
           return false;
       }
        Bid currentBid;
        do {
            currentBid = latestBid.getReference();
            if (bid.getPrice() <= currentBid.getPrice()) return false;
        } while (!latestBid.compareAndSet(currentBid, bid,false, false));
        notifier.sendOutdatedMessage(currentBid);
        return true;
    }

    public Bid getLatestBid() {
        return latestBid.getReference();
    }

    public Bid stopAuction() {
        // to prevent multiple stop actions and incorrect latestBid value
        if (latestBid.isMarked()) {
            return latestBid.getReference();
        }
        Bid latest = latestBid.getReference();
        latestBid.set(latest, true);
        return latest;
    }
}
