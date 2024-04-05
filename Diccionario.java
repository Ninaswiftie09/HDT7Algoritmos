import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Diccionario {

    private static Set<String> translatedWords = new HashSet<>();

    public static void main(String[] args) {
        Comparator<String> stringComparator = Comparator.naturalOrder();
        BinarySearchTree<String, String> englishTree = new BinarySearchTree<>(stringComparator);
        BinarySearchTree<String, String> spanishTree = new BinarySearchTree<>(stringComparator);
        BinarySearchTree<String, String> frenchTree = new BinarySearchTree<>(stringComparator);

        processDictionaryFile("diccionario.txt", englishTree, spanishTree, frenchTree);
        translateTextFile("texto.txt", englishTree, spanishTree, frenchTree);
    }

    private static void processDictionaryFile(String filename,
            BinarySearchTree<String, String> englishTree,
            BinarySearchTree<String, String> spanishTree,
            BinarySearchTree<String, String> frenchTree) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String englishWord = parts[0].trim();
                    String spanishWord = parts[1].trim();
                    String frenchWord = parts[2].trim();
                    String translation = String.format("%s, %s", spanishWord, frenchWord);
                    englishTree.insert(englishWord, translation);
                    spanishTree.insert(spanishWord, String.format("%s, %s", englishWord, frenchWord));
                    frenchTree.insert(frenchWord, String.format("%s, %s", englishWord, spanishWord));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void translateTextFile(String filename,
            BinarySearchTree<String, String> englishTree,
            BinarySearchTree<String, String> spanishTree,
            BinarySearchTree<String, String> frenchTree) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    if (!translatedWords.contains(word)) {
                        String translation = findTranslation(word, englishTree, spanishTree, frenchTree);
                        System.out.print(translation + " ");
                        translatedWords.add(word);
                    } else {
                        System.out.print("*" + word + "* ");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String findTranslation(String word,
            BinarySearchTree<String, String> englishTree,
            BinarySearchTree<String, String> spanishTree,
            BinarySearchTree<String, String> frenchTree) {
        String englishTranslation = englishTree.find(word);
        if (englishTranslation != null)
            return englishTranslation;
        String spanishTranslation = spanishTree.find(word);
        if (spanishTranslation != null)
            return spanishTranslation;
        String frenchTranslation = frenchTree.find(word);
        return (frenchTranslation != null) ? frenchTranslation : "*" + word + "*";
    }
}
