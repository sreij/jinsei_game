import javax.swing.*;
import java.awt.*;

public class InternshipEvent extends Event {

    private Player currentPlayer;
    private JPanel choicePanel;
    private JPanel resultPanel;

    InternshipEvent() {
        super("有名企業のインターン");
        initializePanels();
    }

    private void initializePanels() {
        // 選択肢表示用のパネル
        choicePanel = new JPanel(null);
        choicePanel.setSize(640, 480);
        
        JLabel description = new JLabel("<html><center>有名企業のインターンシップ説明会がある。<br>参加すればコネができるかも？</center></html>");
        description.setFont(new Font("SansSerif", Font.PLAIN, 18));
        description.setHorizontalAlignment(SwingConstants.CENTER);
        description.setBounds(0, 100, 640, 60);
        choicePanel.add(description);

        JButton acceptButton = new JButton("参加する");
        acceptButton.setBounds(120, 250, 150, 40);
        acceptButton.addActionListener(e -> handleChoice(true));
        choicePanel.add(acceptButton);

        JButton declineButton = new JButton("参加しない");
        declineButton.setBounds(370, 250, 150, 40);
        declineButton.addActionListener(e -> handleChoice(false));
        choicePanel.add(declineButton);

        // 結果表示用のパネル
        resultPanel = new JPanel(null);
        resultPanel.setSize(640, 480);
    }

    @Override
    public void execute(Player player) {
        this.currentPlayer = player;
        
        eventPanel.removeAll();
        eventPanel.add(choicePanel);
        eventPanel.revalidate();
        eventPanel.repaint();
    }

    private void handleChoice(boolean accepted) {
        String resultMessage;
        if (accepted) {
            int roll = Dise.roll(2); // 1か2の乱数
            if (roll == 1) { // 成功
                resultMessage = "<html><center>社員と仲良くなれた！<br>コネができて、10000円獲得！</center></html>";
                currentPlayer.addMoney(10000);
            } else { // 失敗
                resultMessage = "<html><center>特に何も得られなかった...<br>交通費で2000円失う。</center></html>";
                currentPlayer.addMoney(-2000);
            }
        } else {
            resultMessage = "今回は見送ることにした。";
        }
        showResult(resultMessage);
    }
    
    private void showResult(String message) {
        resultPanel.removeAll(); // パネルをクリア

        JLabel resultLabel = new JLabel(message);
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setBounds(0, 150, 640, 80); // 高さを確保
        resultPanel.add(resultLabel);

        JButton backButton = new JButton("ゲームに戻る");
        backButton.setBounds(240, 300, 160, 40);
        backButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor((Component)e.getSource());
            window.dispose();
        });
        resultPanel.add(backButton);
        
        eventPanel.removeAll();
        eventPanel.add(resultPanel);
        eventPanel.revalidate();
        eventPanel.repaint();
    }
}