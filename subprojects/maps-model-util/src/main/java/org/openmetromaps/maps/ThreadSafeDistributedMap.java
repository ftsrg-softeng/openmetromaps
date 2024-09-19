package org.openmetromaps.maps;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadSafeDistributedMap<K, V> extends AbstractMap<K, V>
{
    private final List<List<Entry<K, V>>> buckets;
    private final List<Lock> bucketLocks;
    private final Random random;
    private final AtomicInteger size;

    public ThreadSafeDistributedMap(int bucketCount, Random random)
    {
        this.random = random;
        this.buckets = new ArrayList<>(bucketCount);
        this.bucketLocks = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new LinkedList<>());
            bucketLocks.add(new ReentrantLock());
        }
        this.size = new AtomicInteger(0);
    }

    public ThreadSafeDistributedMap(int bucketCount)
    {
        this(bucketCount, ThreadLocalRandom.current());
    }

    public ThreadSafeDistributedMap()
    {
        this(1024, ThreadLocalRandom.current());
    }

    @Override
    public V put(K key, V value)
    {
        int idx = random.nextInt(buckets.size());
        Lock lock = bucketLocks.get(idx);
        lock.lock();
        try {
            List<Entry<K, V>> bucket = buckets.get(idx);

            for (Entry<K, V> e : bucket) {
                if (Objects.equals(e.getKey(), key)) {
                    V old = e.getValue();
                    e.setValue(value);
                    return old;
                }
            }

            bucket.add(new AbstractMap.SimpleEntry<>(key, value));
            size.incrementAndGet();
            return null;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Set<Entry<K, V>> entrySet()
    {
        return new AbstractSet<>() {
            @Override
            public Iterator<Entry<K, V>> iterator() {
                // Snapshot under all bucket locks → safe iteration
                List<Entry<K, V>> snapshot = new LinkedList<>();
                for (int i = 0; i < buckets.size(); i++) {
                    Lock lock = bucketLocks.get(i);
                    lock.lock();
                    try {
                        snapshot.addAll(buckets.get(i));
                    } finally {
                        lock.unlock();
                    }
                }
                return snapshot.iterator();
            }

            @Override
            public int size() {
                return ThreadSafeDistributedMap.this.size();
            }
        };
    }

    @Override
    public int size()
    {
        return size.get();
    }
}