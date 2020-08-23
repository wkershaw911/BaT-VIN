import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//import javax.swing.text.Document;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reader {
    public static void WebReader() throws IOException {
        ArrayList<String> VIN=new ArrayList<>();
        ArrayList<String> links= new ArrayList<>();
        for(int j=0; j<18;j++) {
            String html = "https://bringatrailer.com/porsche/993/?pagedl=" + j;

            Document doc = Jsoup.connect(html).timeout(0).get();
            Elements auctions = doc.select("div.auctions-item-container");
            for (Element x : auctions) {
                //System.out.println(x.select("h3.auctions-item-title")+"END OF AUCTION*******************");
                Elements y = x.select("h3.auctions-item-title").select("a[href]");
                links.add(y.attr("abs:href"));

            }
            System.out.println(j);
        }
            for (String s : links) {

                Document car = Jsoup.connect(s).timeout(0).get();
                String items = car.select("li.listing-essentials-item").text();
                String[] words = items.split(" ");
                System.out.println(Arrays.toString(words));
                List<String> temp = Arrays.asList(words);
                for (int i = 0; i < temp.size(); i++) {
                    if (temp.get(i).equals("Chassis:")) {
                        VIN.add(temp.get(i + 1));
                    }
                }
            }

        System.out.println(VIN);
        FileWriter(VIN);


        //for(Elements link:links){
          //  VIN.add(link.attr("abs:href"));
            //System.out.println(VIN);


    }
    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width - 1) + ".";
        else
            return s;
    }
    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }
    private static void FileWriter(ArrayList<String> vins){
        try {
            FileWriter writer=new FileWriter("993vin.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for(String s:vins){
                bufferedWriter.write(s);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws IOException {
        WebReader();
    }
}


