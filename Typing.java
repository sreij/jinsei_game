import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

//タイピングミニゲーム実装
public class Typing extends Event {
    private final List<String> themeList;
    private final Random rand;

    // --- ゲームの状態を管理する変数 ---
    private Timer timer;
    private int remainingTime;
    private boolean gameActive;
    private String currentTheme;

    // --- GUIコンポーネント ---
    private JLabel themeLabel;      // お題を表示
    private JTextField inputField;    // ユーザーの入力欄
    private JLabel timerLabel;      // 残り時間を表示
    private JLabel resultLabel;     // 結果を表示

    Typing() {
        super("タイピングバイト"); // イベント名を「タイピングバイト」に変更

        this.rand = new Random();
        this.themeList = new ArrayList<>();

        // タイピングのお題リスト (自由に追加・変更してください)
        themeList.add("public static void main");
        themeList.add("System.out.println");
        themeList.add("ArrayList<String> list");
        themeList.add("java.util.Scanner");
        themeList.add("NullPointerException");
        themeList.add("for (int i = 0; i < 10; i++)");
        themeList.add("eventPanel.add(component)");
        themeList.add("ActionListener");
        themeList.add("javax.swing.Timer");
    }

    // ランダムにお題を一つ選ぶメソッド
    public String getRandomTheme() {
        return themeList.get(rand.nextInt(themeList.size()));
    }

    @Override
    public void execute(Player player) {
        // --- ゲームの初期設定 ---
        gameActive = true;
        remainingTime = 15; // 制限時間（秒）
        currentTheme = getRandomTheme();
        final int reward = 5000; // 成功時の報酬

        // --- GUIコンポーネントの初期化と配置 ---
        // 説明ラベル
        JLabel descriptionLabel = new JLabel("制限時間内に以下を正確に入力せよ！成功で" + reward + "円ゲット！");
        descriptionLabel.setBounds(50, 30, 800, 30);

        // お題ラベル
        themeLabel = new JLabel("お題: " + currentTheme);
        themeLabel.setBounds(50, 80, 800, 30);

        // 入力フィールド
        inputField = new JTextField();
        inputField.setBounds(50, 130, 400, 40);

        // タイマーラベル
        timerLabel = new JLabel("残り時間: " + remainingTime + "秒");
        timerLabel.setBounds(50, 180, 200, 30);

        // 結果表示ラベル
        resultLabel = new JLabel();
        resultLabel.setBounds(50, 230, 400, 30);

        // --- タイマーの設定 ---
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameActive) {
                    timer.stop();
                    return;
                }
                remainingTime--;
                timerLabel.setText("残り時間: " + remainingTime + "秒");

                // 時間切れの処理
                if (remainingTime <= 0) {
                    gameActive = false;
                    timer.stop();
                    resultLabel.setText("時間切れ！残念...");
                    inputField.setEnabled(false);
                }
            }
        });
        timer.start();

        // --- 入力フィールドのアクションリスナー ---
        // ユーザーがEnterキーを押したときの処理
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameActive) return;

                String userInput = inputField.getText();
                // 正誤判定
                if (userInput.equals(currentTheme)) {
                    player.addMoney(reward); // 報酬を渡す
                    resultLabel.setText("成功！ " + reward + "円獲得！ (現在の所持金: " + player.getMoney() + "円)");
                } else {
                    resultLabel.setText("失敗！タイプミス！");
                }
                gameActive = false;
                timer.stop();
                inputField.setEnabled(false);
                eventPanel.add(backToGameButton); // ゲームが終わったら「ゲームに戻る」
            }
        });

        // --- パネルにコンポーネントを追加 ---
        eventPanel.add(descriptionLabel);
        eventPanel.add(themeLabel);
        eventPanel.add(inputField);
        eventPanel.add(timerLabel);
        eventPanel.add(resultLabel);
    }
}