import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileWriter;
import java.io.IOException;

public class StaplesScraper {
    public static void main(String[] args) {
        String url = "https://www.staples.com/Computer-office-desks/cat_CL210795/663ea?icid=BTS:2020:STUDYSPACE:DESKS";
        
        try {
            Document doc = Jsoup.connect(url).get();
            Elements products = doc.select("div.product-details");
            FileWriter csvWriter = new FileWriter("staples_products.csv");
            
            // Write CSV header
            csvWriter.append("Product Name,Product Price,Item Number,Model Number,Product Category,Product Description\n");
            
            for (int i = 0; i < 10; i++) {
                Element product = products.get(i);
                String name = product.select("h3.product-title").text().trim();
                String price = product.select("span[itemprop=price]").text().trim();
                String itemNumber = product.select("span[itemprop=sku]").text().trim();
                String modelNumber = product.select("span[itemprop=mpn]").text().trim();
                String category = product.select("a[itemprop=category]").text().trim();
                String description = product.select("div.description").text().trim();
                
                // Write product details to CSV
                csvWriter.append(String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"\n", name, price, itemNumber, modelNumber, category, description));
            }
            
            csvWriter.flush();
            csvWriter.close();
            System.out.println("Successfully wrote product details to staples_products.csv");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



