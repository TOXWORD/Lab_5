import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lab_5 {
    public static void main(String[] args) {
        try {
            HtmlBreaker htm = new HtmlBreaker();

            htm.findTags();
            htm.findWords();

        }
        catch (FileNotFoundException e) {
            System.out.println("FNF");
        } catch (IOException e) {
            System.out.println("IOE");
        }

    }
}
