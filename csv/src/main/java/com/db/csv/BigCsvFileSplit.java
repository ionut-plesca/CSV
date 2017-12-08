package com.db.csv;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BigCsvFileSplit<T> implements BigCSV<T> {
	private final Integer bufferSize;
	private final String fileName;
	private int seq;
	private final Function<T, String> toCsvLine;
	private final Function<String, T> fromCsvLine;

	public BigCsvFileSplit(String fileName, Integer bufferSize, Function<T, String> toCsvLine,
			Function<String, T> fromCsvLine) {
		super();
		this.fileName = fileName;
		this.bufferSize = bufferSize;
		this.toCsvLine = toCsvLine;
		this.fromCsvLine = fromCsvLine;
	}

	public void write(Stream<T> stream) {
		List<T> buffer = new ArrayList<>();
		stream.forEach(item -> {
			if (buffer.size() < bufferSize) {
				write(buffer);
				buffer.add(item);
			} else {
				buffer.clear();
			}
		});
		write(buffer);
	}

	private void write(List<T> buffer) {
		// TODO Auto-generated method stub
		new BigCSVFile<>(fileName + (++seq) + ".csv", toCsvLine, fromCsvLine).write(buffer.stream());

	}

	public Stream<T> read(Predicate<T> filter) {
		return IntStream.range(1, seq).mapToObj(i -> {
			return new BigCSVFile<>(fileName + i + ".csv", toCsvLine, fromCsvLine).read(filter);
		}).flatMap(stream -> stream);

	}
}
