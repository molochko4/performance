import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class Loader {
    public static void main(String[] args) throws Exception {
        String fileName = "res/data-1572M.xml";

        long start = System.currentTimeMillis();
        parseSAX(fileName);
        System.out.println(System.currentTimeMillis() - start + " ms");
    }

    private static void parseSAX(String fileName) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandler handler = new XMLHandler();
        parser.parse(new File(fileName), handler);
    }

}