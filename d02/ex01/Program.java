import java.io.*;
import java.util.*;

//TODO чекнуть ответ в задании 0.54 по факту 0.55 с учетом сокращений
//Узнать про сортировку словаря
public class Program {

	private static final String DICTIONARY_FILE_NAME = "dictionary.txt";

	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			return;
		}

		BufferedReader reader;

		Set<String> dictionarySet = new HashSet<>();

		List<String> words1;

		List<String> words2;

		int[] vector1;

		int[] vector2;

		reader = new BufferedReader(new FileReader(args[0]));
		words1 = downloadWordList(reader);
		reader.close();

		reader = new BufferedReader(new FileReader(args[1]));
		words2 = downloadWordList(reader);
		reader.close();

		dictionarySet.addAll(words1);
		dictionarySet.addAll(words2);

		List<String> dictionaryArrayList = new ArrayList<>(dictionarySet);
		dictionaryArrayList.sort(Comparator.naturalOrder());

		vector1 = createVector(words1, dictionaryArrayList);
		vector2 = createVector(words2, dictionaryArrayList);

		System.out.printf("Similarity = %.2f\n", countUpSimilarity(vector1, vector2));
		createFile(dictionaryArrayList);
	}

	public static ArrayList<String> downloadWordList(BufferedReader reader) throws IOException {
		String line = reader.readLine();

		ArrayList<String> words = new ArrayList<>();

		while (line != null) {
			words.addAll(Arrays.asList(line.replaceAll(" +", " ").split(" ")));
			line = reader.readLine();
		}

		return words;
	}

	public static int[] createVector(List<String> words, List<String> dictionary) {
		int[] vector = new int[dictionary.size()];

		for (String s : words) {
			vector[dictionary.indexOf(s)]++;
		}

		return vector;
	}

	public static double countUpSimilarity(int[] vector1, int[] vector2) {
		double numerator = 0;

		double denominator;

		double n1 = 0;

		double n2 = 0;

		for (int i = 0; i < vector1.length; i++) {
			numerator += vector1[i] * vector2[i];
		}

		for (int k : vector1) {
			n1 += k * k;
		}

		for (int j : vector2) {
			n2 += j * j;
		}

		denominator = Math.sqrt(n1) * Math.sqrt(n2);
		return numerator / denominator;
	}

	public static void createFile(List<String> dictionary) throws IOException {
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(DICTIONARY_FILE_NAME)), true);

		for (String word : dictionary) {
			writer.println(word);
		}

		writer.close();
	}
}
