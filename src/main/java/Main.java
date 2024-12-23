import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }
}

class App {
    public void run() {

        Scanner scanner = new Scanner(System.in);
        Map<Integer, String> wiseMap = new HashMap<>();


        System.out.println("== 명언 앱 ==");

        int cnt = 1; // 몇 번 명언인 지 구분하는 카운트 값
        while (true) {
            String cmd;
            String wise;
            String author;
            String wiseAuthorSet;
            String removeKey;
            String updateKey;
            String parseMapValue;

            System.out.print("명령)");
            cmd = scanner.nextLine(); // 입력값 가져옴 / 입력값이 없으면 기다린다.

            if(cmd.equals("종료")){
                System.out.println("명언 앱을 종료합니다.");
                break;
            }else if (cmd.equals("등록")) {

                System.out.print("명언 : ");
                wise = scanner.nextLine(); // 입력값 가져옴 / 입력값이 없으면 기다린다.

                System.out.print("작가 : ");
                author = scanner.nextLine();

                System.out.println(cnt+"번 명언이 등록되었습니다.");
                wiseAuthorSet = cnt+"/"+wise+"/"+author;
                wiseMap.put(cnt,wiseAuthorSet);

//                JSONObject obj = new JSONObject();
//                obj.put("id",cnt);
//                obj.put("content",wise+" "+cnt);
//                obj.put("author",author+" "+cnt);

                ObjectNode obj = createjsonObj(cnt,wise,author);

                try{
                    String beautyJson = beautyJson(obj);

                    FileWriter file = new FileWriter("D:/dev/dev_back4/wise-saying/db/wiseSaying/"+cnt+".json");
                    file.write(beautyJson);
                    file.flush();
                    file.close();

                }catch(IOException e){
                    e.printStackTrace();
                }

                cnt++;

            }else if(cmd.equals("목록")){

                List<Integer> keySet = new ArrayList<>(wiseMap.keySet());
                Collections.reverse(keySet);

                for(Integer key : keySet){
                    System.out.println(wiseMap.get(key));
                }
            }else if(cmd.equals("삭제")){
                System.out.print("몇번 명언을 삭제하시겠습니까?:");

                removeKey = scanner.nextLine();

                if(!wiseMap.containsKey(Integer.parseInt(removeKey))){
                    System.out.println(removeKey+"번 명언은 존재하지 않습니다.");
                }else{
                    wiseMap.remove(Integer.parseInt(removeKey));
                    System.out.println(removeKey + "번 명언이 삭제되었습니다.");
                };

            }else if(cmd.equals("수정")){
                System.out.print("몇번 명언을 수정하시겠습니까?:");

                updateKey = scanner.nextLine();

                if(!wiseMap.containsKey(Integer.parseInt(updateKey))){
                    System.out.println(updateKey+"번 명언은 존재하지 않습니다.");
                }else{
                    parseMapValue = wiseMap.get(Integer.parseInt(updateKey));
                    String[] parseMapValueArr = parseMapValue.split("/");

                    System.out.println("명언(기존): "+parseMapValueArr[1]);
                    System.out.print("명언: ");
                    wise = scanner.nextLine();
                    System.out.println("작가(기존): "+parseMapValueArr[2]);
                    System.out.print("작가: ");
                    author = scanner.nextLine();

                    wiseAuthorSet = updateKey+"/"+wise+"/"+author;
                    wiseMap.put(Integer.parseInt(updateKey),wiseAuthorSet);

                    ObjectNode obj = createjsonObj(Integer.parseInt(updateKey),wise,author);

                    try{
                        String beautyJson = beautyJson(obj);

                        FileWriter file = new FileWriter("D:/dev/dev_back4/wise-saying/db/wiseSaying/"+updateKey+".json");
                        file.write(beautyJson);
                        file.flush();
                        file.close();

                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // 입력 받은 값 json파싱
    public ObjectNode createjsonObj(int cnt, String wise, String author){

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode obj = mapper.createObjectNode();

        obj.put("id",cnt);
        obj.put("content",wise+" "+cnt);
        obj.put("author",author+" "+cnt);

        System.out.println(obj.toString());

        return obj;

    }

    // json 데이터 줄 맞춤 메소드
    public String beautyJson(ObjectNode obj) throws IOException{

            ObjectMapper objectMapper = new ObjectMapper();

            Object json = objectMapper.readValue(obj.toString(), Object.class);
            String beautyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            return beautyJson;

    }
}
