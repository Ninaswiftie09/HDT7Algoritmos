import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import java.util.Comparator;

public class DiccionarioTest {
    private BinarySearchTree<String, String> englishTree;
    private BinarySearchTree<String, String> spanishTree;
    private BinarySearchTree<String, String> frenchTree;

    @Before
    public void setUp() {
        Comparator<String> comparar = Comparator.naturalOrder();

        englishTree = new BinarySearchTree<>(comparar);
        spanishTree = new BinarySearchTree<>(comparar);
        frenchTree = new BinarySearchTree<>(comparar);
        englishTree.insert("house", "casa, loger");
        spanishTree.insert("casa", "house, loger");
        frenchTree.insert("loger", "house, casa");
    }

    @Test
    public void testTranslateExistingWord() {
        String translatedEnglish = Diccionario.findTranslation("house", englishTree, spanishTree, frenchTree);
        assertEquals("casa, loger", translatedEnglish);

        String translatedSpanish = Diccionario.findTranslation("casa", englishTree, spanishTree, frenchTree);
        assertEquals("house, loger", translatedSpanish);

        String translatedFrench = Diccionario.findTranslation("loger", englishTree, spanishTree, frenchTree);
        assertEquals("house, casa", translatedFrench);
    }

    @Test
    public void testTranslateNonExistingWord() {
        String translatedEnglish = Diccionario.findTranslation("car", englishTree, spanishTree, frenchTree);
        assertEquals("*car*", translatedEnglish);

        String translatedSpanish = Diccionario.findTranslation("coche", englishTree, spanishTree, frenchTree);
        assertEquals("*coche*", translatedSpanish);

        String translatedFrench = Diccionario.findTranslation("voiture", englishTree, spanishTree, frenchTree);
        assertEquals("*voiture*", translatedFrench);
    }
}
