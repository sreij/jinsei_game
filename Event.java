import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Window;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

//各マスのイベントの抽象クラス
abstract class Event{
    protected String eventName;
    public JPanel eventPanel;
    public final JButton backToGameButton;

    Event(String name){
        this.eventName = name;
        eventPanel = new JPanel(null);
        eventPanel.setBackground(Color.WHITE);
        eventPanel.setSize(640,480);

        backToGameButton = new JButton("ゲームに戻る");
        setBackToGameButton();
    }

    private void setBackToGameButton() {
        backToGameButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        backToGameButton.setBounds(240, 300, 160, 40);
        eventPanel.add(backToGameButton);

        backToGameButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor((Component) e.getSource());
            window.dispose();
        });
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