package ca.ualberta.goqueer.server.cache_provider;

/**
 * Created by bamdad on 8/22/16.
 */
public interface CacheHolder {
    void expire();
    void clear();
    long getFileSize();
    String getName();
}
