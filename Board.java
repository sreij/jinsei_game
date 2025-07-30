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
        
        event[1] = new Typing();          //最初のマスはクイズ
        event[2] = new Typing();          //最初のマスはクイズ
        event[3] = new Typing();          //最初のマスはクイズ
        event[4] = new Quiz();          //最初のマスはクイズ
        event[5] = new Quiz();          //最初のマスはクイズ
        event[6] = new Quiz();          //最初のマスはクイズ
        event[event.length-1] = new Graduate(); //最後のマスは卒業式？

        //各マスにランダムにイベントを配置
        for(int i = 7; i < event.length-1; i++){
            int num = Dise.roll(3);
            //System.out.println(num);
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
                default:
                    throw new AssertionError();
            }
        }

        //log
        System.out.println("complete initialize Board");
    }
}