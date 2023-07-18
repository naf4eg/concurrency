package course.concurrency.exams.auction;

public class AuctionPessimistic implements Auction {


    private Notifier notifier;
    private final Object lock = new Object();

    public AuctionPessimistic(Notifier notifier) {
        this.notifier = notifier;
        latestBid = new Bid(-1L, -1L, -1L);
    }

    private volatile Bid latestBid;

    public boolean propose(Bid bid) {
        // this condition filter bids with lower price and seriously reduce contention
        if (bid.getPrice() > latestBid.getPrice()) {
            synchronized (lock) {
                // double-check is required to prevent data races
                if (bid.getPrice() > latestBid.getPrice()) {
                    notifier.sendOutdatedMessage(latestBid);
                    latestBid = bid;
                    return true;
                }
            }
        }

        return false;
    }
    public Bid getLatestBid() {
        return latestBid;
    }
}
