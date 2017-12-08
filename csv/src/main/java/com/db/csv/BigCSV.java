package com.db.csv;

import java.util.function.Predicate;
import java.util.stream.Stream;

public interface BigCSV<T> {
	public void write(Stream<T> stream);
	public Stream<T> read(Predicate<T> filter);

}