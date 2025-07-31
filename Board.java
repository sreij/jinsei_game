/*
 * 盤面を管理するクラス
 * 各盤面にEventオブジェクトを持っている
 * ポジションを受け取り、それに対応するイベントを呼び出す
 */

class Board{
    Event event[];
    Board(int num_event){
        event = new Event[num_event];   //イベント数確定
        event[0] = new Tutorial();      //最初のマスはチュートリアル
        
        /*
        // 特定のマスにイベントを固定で配置する場合
        event[1] = new Typing();
        event[2] = new LabVisitEvent();
        event[3] = new Quiz();
        event[4] = new InternshipEvent();
        event[5] = new GameNightEvent();
        event[6] = new Daily();
        */
        event[event.length-1] = new Graduate(); //最後のマスは卒業式

        // ▼▼▼【ここから修正】▼▼▼
        // 各マスにランダムにイベントを配置
        for(int i = 1; i < event.length-1; i++){
            int num = Dice.roll(6); // 6面ダイスに変更
            switch (num) {
                case 1:
                    event[i] = new Quiz();
                    break;
                case 2:
                    event[i] = new Typing();
                    break;
                case 3:
                    event[i] = new Daily();
                    break;
                case 4:
                    event[i] = new LabVisitEvent(); // 追加
                    break;
                case 5:
                    event[i] = new InternshipEvent(); // 追加
                    break;
                case 6:
                    event[i] = new GameNightEvent(); // 追加
                    break;
                default:
                    throw new AssertionError();
            }
        }
        // ▲▲▲【ここまで修正】▲▲▲

        //log
        System.out.println("complete initialize Board");
    }
}