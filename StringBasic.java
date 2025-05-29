public class StringBasic {

    private void practice () {
        // string builder
        StringBuilder result = new StringBuilder();
        for () {
            result.append(str).append(" "); // append로 붙이기
        }
        // result.reverse(); // 뒤집기
        result.toString(); // String 타입으로 변환

        // split
        String[] words = input.split("\\s+"); // 공백 문자 하나 이상 이어진 부분을 구분자로 문자열을 나눔
        // split(" ") => 공백 여러 개 있으면 빈 문자열도 생겨서, 배열에 들어감!

        String[] parts = line.split("#", 2); // 최대 분할 개수, 2개로 나누기

        // join
        String[] items = {"apple", "banana", "cherry"};
        String result = String.join(", ", items);
        System.out.println(result);  // 출력: apple, banana, cherry

        List<String> names = List.of("Kim", "Lee", "Park");
        String joined = String.join(" | ", names);

        String[] lines = {"line1", "line2", "line3"};
        String result = String.join("\n", lines);

        // index
        int index = str.indexOf(String target); // str: 원본 문자열, target: 찾고자 하는 문자열 또는 문자
        int index = str.indexOf(String target, int fromIndex); // fromIndex: 몇 번째 문자부터 찾을지 지정 (선택)

        String text = "hello world";
        int index = text.indexOf("java");
        System.out.println(index);  // 출력: -1

        // substring
        String str = "HelloWorld";
        String result = str.substring(0, 5);  // "Hello", endIndex는 포함하지 않음

        String str = "HelloWorld";
        String result = str.substring(5);  // "World"

        // replaceAll
        String cleaned = input.replaceAll("\\s+", ""); // 모든 화이트 캐릭터
    }
}
