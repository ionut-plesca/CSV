package com.db.csv;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class BigCSVFile<T> implements BigCSV<T> {

	private final String filename;
	private final Function<T, String> toCsvLine;
	private final Function<String, T> fromCsvLine;

	public BigCSVFile(String filename, Function<T, String> toCsvLine, Function<String, T> fromCsvLine) {
		super();
		this.filename = filename;
		this.toCsvLine = toCsvLine;
		this.fromCsvLine = fromCsvLine;
	}

	public void write(Stream<T> stream) {
		// stream.map(toCsvLine).forEach(line -> {
		// try {
		// Files.write(Paths.get(filename), Arrays.asList(line),
		// StandardOpenOption.CREATE,
		// StandardOpenOption.APPEND);
		// } catch (IOException e) {
		// // 
		// e.printStackTrace();
		// }
		// });
		try {
			Files.write(Paths.get(filename), (Iterable<String>) () -> stream.map(toCsvLine).iterator(),
					StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public Stream<T> read(Predicate<T> filter) {
		try {
			return Files.lines(Paths.get(filename)).map(fromCsvLine).filter(filter);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
