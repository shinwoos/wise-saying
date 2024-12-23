import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        App app = new App();
        app.run();
    }
}

class App {
    public void run() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("== 명언 앱 ==");

        int cnt = 1; // 몇 번 명언인 지 구분하는 카운트 값
        while (true) {
            String cmd;

            System.out.print("명령)");
            cmd = scanner.nextLine(); // 입력값 가져옴 / 입력값이 없으면 기다린다.

            if (cmd.equals("등록")) {

                System.out.print("명언 : ");
                scanner.nextLine(); // 입력값 가져옴 / 입력값이 없으면 기다린다.

                System.out.print("작가 : ");
                scanner.nextLine();

                System.out.println(cnt+"번 명언이 등록되었습니다.");
                cnt++;

            }else if(cmd.equals("종료")){
                System.out.println("명언 앱을 종료합니다.");
                break;
            }
        }
    }
}
