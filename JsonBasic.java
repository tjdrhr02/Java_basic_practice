import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.nio.charset.StandardCharsets;
import java.util.List;


public class JsonBasic {

    private void practice() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // 1. JSON 문자열 → JsonElement 변환
        String jsonStr = "[{\"name\":\"Alice\",\"age\":30},{\"name\":\"Bob\",\"age\":25}]";
        JsonElement jsonElement = JsonParser.parseString(jsonStr);
        // parseString 후 얻는 것은 JsonElement다.
        // 이 JsonElement를 getAsInt(), getAsBoolean(), getAsJsonArray(), getAsJsonObject() 하자.
        System.out.println("JsonElement:\n" + gson.toJson(jsonElement));

        // 2. JsonElement → JsonArray 변환 (배열인 경우)
        if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            System.out.println("JsonArray size: " + jsonArray.size());

            for (JsonElement element : jsonArray) {
                JsonObject obj = element.getAsJsonObject();

                System.out.println("Name: " + obj.get("name").getAsString());
                System.out.println("Age: " + obj.get("age").getAsInt());
            }
        }

        // 3. JsonElement → JsonObject 변환 (객체인 경우)
        String jsonObjStr = "{\"name\":\"Charlie\",\"age\":28}";
        JsonElement objElement = JsonParser.parseString(jsonObjStr);
        if (objElement.isJsonObject()) {
            JsonObject jsonObject = objElement.getAsJsonObject();
            System.out.println("JsonObject name: " + jsonObject.get("name").getAsString());
            // get("name") 후 얻는 것은 JsonElement다.
            // 이 JsonElement를 getAsInt(), getAsBoolean(), getAsJsonArray(), getAsJsonObject() 하자.

            //// 방법 1
            //JsonObject joDetail1 = joModel.getAsJsonObject("details");
            //
            //// 방법 2, 방법 1과 동일
            //JsonObject joDetail2 = joModel.get("details").getAsJsonObject();
        }

        // 4. JSON 문자열 → Java 객체 리스트 변환
        List<Person> people = gson.fromJson(jsonStr, new TypeToken<List<Person>>() {}.getType());
        System.out.println("Java List<Person>:");

        // 중요. 5. Java 객체/JsonElement → JSON 문자열 변환
        String jsonOut = gson.toJson(people);
        // String jsonBackToString = new Gson().toJson(jsonElement);
        System.out.println("Serialized JSON:\n" + jsonOut);

        // 6. JSON 문자열 → 바이트 배열 변환
        byte[] jsonBytes = jsonOut.getBytes(StandardCharsets.UTF_8);
        System.out.println("JSON byte length: " + jsonBytes.length);

        // 7. 바이트 배열 → JSON 문자열 변환
        String jsonFromBytes = new String(jsonBytes, StandardCharsets.UTF_8);
        System.out.println("JSON from bytes:\n" + jsonFromBytes);

        // 8. 바이트 배열 → Java 객체 다시 변환
        List<Person> peopleFromBytes = gson.fromJson(jsonFromBytes, new TypeToken<List<Person>>() {}.getType());
        System.out.println("Deserialized from bytes:");
    }

    // dto
    class Person {
        String name;
        int age;
    }

}
