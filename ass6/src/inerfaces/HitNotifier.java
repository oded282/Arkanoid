package inerfaces;

/**
 * This func will notify all the hit listeners that was a hit.
 */
public interface HitNotifier {
    /**
     * @param hl add it as a listener.
     */
    void addHitListener(HitListener hl);

    /**
     * @param hl remove it as a listener.
     */
    void removeHitListener(HitListener hl);

}
