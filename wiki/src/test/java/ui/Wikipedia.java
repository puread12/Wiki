package ui;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.testng.annotations.Test;

import com.opencsv.CSVWriter;

public class Wikipedia extends BaseClass {

	@Test
	public void wiki_Link_Check() {

		try {

			Scanner userNumber = new Scanner(System.in);
			System.out.println("Please enter number between 1 - 20");
			String temp = userNumber.nextLine();
			int n = Integer.parseInt(temp);
			userNumber.close();
			if (n < 1 || n > 20) {
				throw new IllegalArgumentException("Wrong Entry!! - (" + n + ") must be between 1 and 20");
			}

			File links1 = new File(".//Links1.txt");
			File links2 = new File(".//Links2.txt");
			Scanner myReader1 = new Scanner(links1);
			Scanner myReader2 = new Scanner(links2);
			ArrayList<String> myWikiLinks = new ArrayList<String>();

			ArrayList<String> checkVisitLinks = new ArrayList<String>();

			int number = 1;

			while (myReader1.hasNextLine()) {
				number++;
				String data = myReader1.nextLine();

				if (checkVisitLinks.contains(data)) {
					System.out.println(data + " already visited\n");
					continue;

				} else {
					initialize("chrome");
					driver.get(data);
					checkLink(data);
					System.out.println();
					driver.close();
				}

				if (number > n) {
					System.out.println("We reached the n max number\n");
					if (wikipedia == true) {
						myWikiLinks.add(data);
					}
					checkVisitLinks.add(data);
					break;
				}

				if (wikipedia == true) {
					myWikiLinks.add(data);
				}

				checkVisitLinks.add(data);
			}

			while (myReader2.hasNextLine()) {

				String data2 = myReader2.nextLine();

				if (number > n) {
					break;
				}

				if (checkVisitLinks.contains(data2)) {
					System.out.println(data2 + " already visited\n");
					number++;
					continue;

				} else {
					initialize("chrome");
					driver.get(data2);
					checkLink(data2);
					System.out.println();
					driver.close();
				}

				if (number >= n) {
					System.out.println("We reached the n max number\n");
					if (wikipedia == true) {
						myWikiLinks.add(data2);
					}
					checkVisitLinks.add(data2);
					break;
				}

				if (wikipedia == true) {
					myWikiLinks.add(data2);
				}
				checkVisitLinks.add(data2);
				number++;
			}
			System.out.println("My Wiki List: ");
			for (String link : myWikiLinks) {
				System.out.println(link);
			}

			// write to csv file

			String totalCount = Integer.toString(checkVisitLinks.size());

			String unique_Count = Integer.toString(myWikiLinks.size());

			File file = new File(".//test.csv");

			FileWriter outputfile = new FileWriter(file);

			CSVWriter writer = new CSVWriter(outputfile);

			String listString = String.join("\r\n", checkVisitLinks);

			List<String[]> data = new ArrayList<String[]>();
			data.add(new String[] { "all found links", "total count", "unique count" }); // unique count = Wiki links
																							// counts
			data.add(new String[] { listString, totalCount, unique_Count });
			writer.writeAll(data);

			writer.close();
			myReader1.close();
			myReader2.close();

		} catch (Exception e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}
