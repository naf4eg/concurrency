package course.concurrency.exams.auction;

public class AuctionStoppablePessimistic implements AuctionStoppable {

    private Notifier notifier;

    public AuctionStoppablePessimistic(Notifier notifier) {
        this.notifier = notifier;
        this.latestBid = new Bid(-1L, -1L, -1L);
    }

    private volatile Bid latestBid;
    private volatile boolean isOpen = true;
    Object lock = new Object();

    public boolean propose(Bid bid) {
        if (bid.getPrice() > latestBid.getPrice() && isOpen) {
            synchronized (lock) {
                notifier.sendOutdatedMessage(latestBid);
                latestBid = bid;
                return true;
            }
        }
        return false;
    }

    public Bid getLatestBid() {
        return latestBid;
    }

    public Bid stopAuction() {
        synchronized (lock) {
            isOpen = false;
            return latestBid;
        }
    }
}
