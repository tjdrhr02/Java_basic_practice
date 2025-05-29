import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

class FileIOBasic {

    private static Something loadSomething(String filename) {
        Something something = null; // Maybe ArrayList

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) { // "OUTPUT.TXT"
            String line;
            while ((line = reader.readLine()) != null) {
                // String[] parts = line.split("#", 2);

                // line.split("\\s+"); // white char
                // line.indexOf("#", 0); // from index
                // line.substring(0, 5); // from index, length
                // line.replaceAll("\\s+", "");
                // String.join(", ", parts);

                // String word = parts[0].trim();
            }
            return something;
            // Java 객체 반환
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return something;
    }

    private static Something loadSomething(String filename) {
        Something something = null; // Maybe ArrayList

        String somethingJson;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) { // "SOMETHING.JSON"
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            somethingJson = sb.toString();

            // Json String -> Java 객체, fromJson

            return something;
            // Java 객체 반환
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return something;
    }

    private void writeSomething(String filename) {
        try (FileWriter writer = new FileWriter(filename)) { // 이어쓰기 : new FileWriter(filename, true)
            writer.write("Hello, world!\n");
            writer.write("This is a file writing example.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

