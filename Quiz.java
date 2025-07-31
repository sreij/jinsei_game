import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 3択形式で一問ごとに結果を表示するクイズイベントのクラス。
 * Game.javaのポップアップウィンドウ形式に対応。
 */
public class Quiz extends Event {

    private final QuizLogic logic = new QuizLogic();
    private Player currentPlayer;
    
    // --- クイズ進行状況を管理する変数 ---
    private List<String[]> currentQuizSet;
    private int currentQuestionIndex;
    private int correctAnswers;
    private String correctAnswer;

    // --- GUI部品 ---
    private JLabel questionLabel;
    private JLabel progressLabel;
    private JLabel resultLabel;
    private JButton[] choiceButtons = new JButton[3];
    private JButton nextButton;

    Quiz() {
        super("クイズチャレンジ");

        Font japaneseFont = new Font("SansSerif", Font.PLAIN, 14);

        progressLabel = new JLabel("1 / 5 問目");
        progressLabel.setFont(japaneseFont);
        progressLabel.setBounds(50, 20, 100, 30);
        eventPanel.add(progressLabel);
        
        questionLabel = new JLabel("ここに問題文が表示される");
        questionLabel.setFont(japaneseFont);
        questionLabel.setBounds(50, 50, 540, 60);
        eventPanel.add(questionLabel);

        resultLabel = new JLabel("ここに正解・不正解が表示される");
        resultLabel.setFont(japaneseFont);
        resultLabel.setBounds(50, 250, 500, 30);
        eventPanel.add(resultLabel);

        for (int i = 0; i < 3; i++) {
            choiceButtons[i] = new JButton();
            choiceButtons[i].setFont(japaneseFont);
            choiceButtons[i].setBounds(50, 100 + (i * 50), 500, 30);
            eventPanel.add(choiceButtons[i]);
            choiceButtons[i].addActionListener(e -> {
                processAnswer(((JButton)e.getSource()).getText());
            });
        }
        
        nextButton = new JButton("次へ");
        nextButton.setFont(japaneseFont);
        nextButton.setBounds(480, 300, 120, 30);
        eventPanel.add(nextButton);
        nextButton.addActionListener(e -> nextStep());
    }

    @Override
    public void execute(Player player) {
        eventPanel.add(progressLabel);
        eventPanel.add(questionLabel);
        eventPanel.add(resultLabel);
        for (JButton button : choiceButtons) {
            eventPanel.add(button);
        }
        eventPanel.add(nextButton);
        this.currentPlayer = player;
        this.currentQuizSet = logic.getQuizSet();
        this.currentQuestionIndex = 0;
        this.correctAnswers = 0;
        
        displayQuestion();
    }

    private void displayQuestion() {
        setChoiceButtonsVisible(true);
        resultLabel.setVisible(false);
        nextButton.setVisible(false);
        nextButton.setText("次へ");

        String[] quizData = currentQuizSet.get(currentQuestionIndex);
        String question = quizData[0];
        this.correctAnswer = quizData[1];
        
        List<String> choices = Arrays.asList(quizData[1], quizData[2], quizData[3]);
        Collections.shuffle(choices);

        progressLabel.setText((currentQuestionIndex + 1) + " / 5 問目");
        questionLabel.setText("<html>Q" + (currentQuestionIndex + 1) + ": " + question + "</html>");
        for (int i = 0; i < 3; i++) {
            choiceButtons[i].setText(choices.get(i));
        }
    }
    
    private void processAnswer(String selectedAnswer) {
        setChoiceButtonsVisible(false);
        resultLabel.setVisible(true);
        nextButton.setVisible(true);
        
        if (currentQuestionIndex == 4) {
            nextButton.setText("最終結果");
        }

        if (logic.checkAnswer(selectedAnswer, this.correctAnswer)) {
            correctAnswers++;
            resultLabel.setText("正解です！");
        } else {
            resultLabel.setText("不正解... 正解は「" + this.correctAnswer + "」でした。");
        }
    }

    private void nextStep(){
        currentQuestionIndex++;
        if (currentQuestionIndex < 5) {
            displayQuestion();
        } else {
            showFinalResult();
        }
    }

    private void showFinalResult() {
        // 既存のコンポーネントを非表示にする
        setChoiceButtonsVisible(false);
        nextButton.setVisible(false);
        progressLabel.setVisible(false);
        resultLabel.setVisible(false);
        questionLabel.setVisible(false);
    
        int credits = logic.calculateCredits(correctAnswers);
        currentPlayer.addCredit(credits);
    
        // ▼▼▼【ここからが新しいコード】▼▼▼
    
        if (correctAnswers == 5) {
            // --- 全問正解の場合 ---
            int bonusMoney = 10000;
            currentPlayer.addMoney(bonusMoney);
        
            // 表示する文字列
            String line1Text = "🎉パーフェクト！🎉";
            String line2Text = "5問全問正解です！おめでとう！";
            String line3Text = String.format("%d単位とボーナス%d円獲得！", credits, bonusMoney);

            // 各行を別々のJLabelで作成
            JLabel line1Label = new JLabel(line1Text);
            JLabel line2Label = new JLabel(line2Text);
            JLabel line3Label = new JLabel(line3Text);

            // 共通のフォントと色を設定
            Font resultFont = new Font("SansSerif", Font.BOLD, 28);
            Color resultColor = new Color(255, 100, 100);
        
            // ラベルのリストを作成して一括で設定
            JLabel[] labels = {line1Label, line2Label, line3Label};
            int yPosition = 120; // 最初のラベルのY座標
            for (JLabel label : labels) {
                label.setFont(resultFont);
                label.setForeground(resultColor);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBounds(0, yPosition, 640, 40); // Y座標と高さを設定
                eventPanel.add(label);
                yPosition += 40; // 次のラベルのためにY座標をずらす
            }

        } else {
            // --- 通常の結果表示の場合 ---
            String line1Text = String.format("最終結果: 5問中 %d問正解！", correctAnswers);
            String line2Text = String.format("%d単位獲得しました！", credits);

            JLabel line1Label = new JLabel(line1Text);
            JLabel line2Label = new JLabel(line2Text);
        
            Font resultFont = new Font("SansSerif", Font.BOLD, 24);
        
            JLabel[] labels = {line1Label, line2Label};
            int yPosition = 150;
            for (JLabel label : labels) {
                label.setFont(resultFont);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBounds(0, yPosition, 640, 40);
                eventPanel.add(label);
                yPosition += 40;
            }
        }

        // ゲームに戻るためのボタンを追加
        JButton backToGameButton = new JButton("ゲームに戻る");
        backToGameButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        backToGameButton.setBounds(240, 300, 160, 40);
        eventPanel.add(backToGameButton);

        backToGameButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor((Component)e.getSource());
            window.dispose();
        });
    
        // パネルを再描画
        eventPanel.revalidate();
        eventPanel.repaint();
    }

    
    
    private void setChoiceButtonsVisible(boolean visible){
        for(JButton button : choiceButtons){
            button.setVisible(visible);
        }
    }
}