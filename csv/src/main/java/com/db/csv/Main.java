package com.db.csv;

import java.io.IOException;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		BigCSV<User> csv = new BigCsvFileSplit<User>("users", 1000, user -> {
			return user.getId() + "," + user.getFirstName();
		}, line -> {
			String[] split = line.split(",");
			return new User(Long.valueOf(split[0]), split[1]);
		});
		
//		try {
//			csv.read(user->user.getId()%10==0).forEach(System.out::println);
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}

		BigCSVFile<User> upperCsv = new BigCSVFile<>("upperUsers.csv", user -> {
			return user.getId() + "," + user.getFirstName();
		}, line -> {
			String[] split = line.split(",");
			return new User(Long.valueOf(split[0]), split[1]);
		});
//		csv.write(Stream.of(new User(1L, "Gigi")));
		
		

		writeTestData(10L, csv);
		
		upperCsv.write(csv.read(user->true).map(user -> new User(user.getId(), user.getFirstName().toUpperCase())));
	}
	
	private static void writeTestData (Long limit, BigCSV<User> csv){
		 Random random = new Random();
		 csv.write(Stream.generate(()->{Long l = random.nextLong(); return new User(l, "Gigi"+l);}).limit(limit));
		
	}
}
