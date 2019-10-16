import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HtmlBreaker {

    private final String pathIn1 = "input.html";
    private final String pathIn2 = "input.in";
    private final String pathOut1 = "output1.txt";
    private final String pathOut2 = "output2.txt";
    private final String pathOut3 = "output3.txt";

    private List<String> text;
    private List<String> words;
    private List<String> tags;

        public HtmlBreaker() throws FileNotFoundException {
            text = new ArrayList<>();
            tags = new ArrayList<>();
            words = new ArrayList<>();

            File fileIn = new File(pathIn1);
            Scanner sc = new Scanner(fileIn);

            while(sc.hasNextLine()){
                text.add(sc.nextLine().toLowerCase());
            }

            StringTokenizer st;
            fileIn = new File(pathIn2);
            sc = new Scanner(fileIn);

            while(sc.hasNextLine()){
                st = new StringTokenizer(sc.nextLine(), ";");
                while(st.hasMoreTokens()){
                    words.add(st.nextToken().toLowerCase());
                }
            }

        }

        public void findTags() throws IOException {
            String tag;

            for(String s : text){
                Pattern p = Pattern.compile("\\<[^>]*>");   // \\< - обозначение '<', [^>] всё, кроме знака '>', * - 0 и более повторов, > - конец поиска
                Matcher m = p.matcher(s);

                while (m.find() && !tags.contains(tag = m.group(0)) && !(tag.charAt(1) == '/')) {
                    tags.add(tag);
                }
            }

            Collections.sort(tags, Comparator.comparingInt(String::length));

            FileWriter fw = new FileWriter(pathOut1);

            for(String s : tags){
                fw.write(s + " </" + s.substring(1) + "\n");
            }

            fw.close();

        }

        public void findWords() throws IOException {

            FileWriter fw1 = new FileWriter(pathOut2);
            FileWriter fw2 = new FileWriter(pathOut3);

            String row;

            for(int j = 0; j < text.size(); j++){
                text.add(j, text.remove(j).replaceAll("\\<[^>]*>", "")); // то же самое, что и сверху, только заменяем на пустую строку, а не просто находим

                row = text.get(j);

                for(int i = 0; i < words.size(); i++){
                    if(row.contains(words.get(i))){
                        fw1.write(words.remove(i) + " " + text.indexOf(row) + "\n");
                    }
                }
            }

            for(String w : words){
                fw1.write(w + " " + -1 + "\n");
                fw2.write(w + "\n");
            }

            fw1.close();
            fw2.close();

        }

}
