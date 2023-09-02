package duelistmod.interfaces;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class ImmutableList<T> implements Iterable<T> {

    private final List<T> list;

    public ImmutableList(List<T> list) {
        this.list = Collections.unmodifiableList(list);
    }

    public T get(int index) {
        return this.list.get(index);
    }

    public boolean contains(T object) {
        return this.list.contains(object);
    }

    public int size() {
        return this.list.size();
    }

    public Stream<T> stream() { return this.list.stream(); }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return this.list.iterator();
    }
}
