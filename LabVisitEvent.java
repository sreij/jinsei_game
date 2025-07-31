import javax.swing.*;
import java.awt.*;

public class LabVisitEvent extends Event {

    private Player currentPlayer;
    private JPanel choicePanel;
    private JPanel resultPanel;

    LabVisitEvent() {
        super("先輩の研究室見学");
        initializePanels();
    }

    private void initializePanels() {
        // 選択肢表示用のパネル
        choicePanel = new JPanel(null);
        choicePanel.setSize(640, 480);
        
        JLabel description = new JLabel("<html><center>先輩から研究室見学に誘われた。<br>参加すると研究の様子がわかるが、時間が奪われる...。</center></html>");
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
                resultMessage = "<html><center>研究室は良い雰囲気だった！<br>有益な話が聞けて、4単位獲得！</center></html>";
                currentPlayer.addCredit(4);
            } else { // 失敗
                resultMessage = "<html><center>研究室の雰囲気が合わなかった...<br>時間を無駄にした... 5000円失う。</center></html>";
                currentPlayer.addMoney(-5000);
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