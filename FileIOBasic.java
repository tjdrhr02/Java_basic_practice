import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;

class FileIOBasic {

    private static Something loadSomething(String filename) {
        Something something = null; // new

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) { // "OUTPUT.TXT"
            String line;
            while ((line = reader.readLine()) != null) {
                // making something
            }
            return something;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Something loadSomething(String filename) {
        Something something = null; // new

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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeSomething(String filename) {
        File file = new File(filename);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            // 파일에 한 줄 쓰기 (append 모드)
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(line);
                writer.newLine();
            }

            // 500ms 일시정지
            Thread.sleep(sleepMillis);

        } catch (IOException e) {
            System.err.println("파일 쓰기 오류: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("스레드 sleep 중 인터럽트 발생");
            Thread.currentThread().interrupt();  // 인터럽트 상태 복원 권장
        }
    }
}

