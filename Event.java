import java.awt.Color;
import javax.swing.JPanel;

//各マスのイベントの抽象クラス
abstract class Event{
    protected String eventName;
    public JPanel eventPanel;
    Event(String name){
        this.eventName = name;
        eventPanel = new JPanel(null);
        eventPanel.setBackground(Color.WHITE);
        eventPanel.setSize(640,480);
    }

    //マスにイベントの名前を返せるようにするためのゲッターメソッド
    public String getEventName(){
        return eventName;
    }

    //サブクラスで設計した画面構成を返すメソッド
    public JPanel getPane(){
        return eventPanel;
    }

    //イベントマスのイベントを実行するためのメソッド
    public void run(Player p){
        eventPanel.removeAll();
        eventPanel.setLayout(null);
        execute(p);
        eventPanel.revalidate();
        eventPanel.repaint();
    }

    //画面実装をする抽象メソッド
    public abstract void execute(Player player);
}