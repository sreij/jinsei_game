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
        super("3択クイズチャレンジ");

        Font japaneseFont = new Font("SansSerif", Font.PLAIN, 14);

        progressLabel = new JLabel("1 / 5 問目");
        progressLabel.setFont(japaneseFont);
        progressLabel.setBounds(50, 20, 100, 30);
        eventPanel.add(progressLabel);
        
        questionLabel = new JLabel("ここに問題文が表示されます");
        questionLabel.setFont(japaneseFont);
        questionLabel.setBounds(50, 50, 540, 60);
        eventPanel.add(questionLabel);

        resultLabel = new JLabel("ここに正解・不正解が表示されます");
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
            nextButton.setText("最終結果を見る");
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
        setChoiceButtonsVisible(false);
        nextButton.setVisible(false);
        progressLabel.setVisible(false);
        resultLabel.setVisible(false);
        questionLabel.setVisible(false);
        
        int credits = logic.calculateCredits(correctAnswers);
        currentPlayer.addCredit(credits);
        
        JLabel finalResultLabel = new JLabel();
        
        if (correctAnswers == 5) {
            // ★★★ ここからが修正点 ★★★
            // 全問正解の場合の特別な演出
            int bonusMoney = 10000;
            currentPlayer.addMoney(bonusMoney); // ボーナス賞金を追加
            
            finalResultLabel.setText(String.format("<html><div style='text-align: center;'>🎉パーフェクト！🎉<br>5問全問正解です！<br>%d単位とボーナス%d円獲得！</div></html>", credits, bonusMoney));
            finalResultLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
            finalResultLabel.setForeground(new Color(255, 100, 100));
        } else {
            // 通常の結果表示
            finalResultLabel.setText(String.format("<html><div style='text-align: center;'>最終結果: 5問中 %d問正解！<br>%d単位獲得しました！</div></html>", correctAnswers, credits));
            finalResultLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        }
        
        finalResultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        finalResultLabel.setBounds(0, 150, 640, 100);
        eventPanel.add(finalResultLabel);

        // ゲームに戻るためのボタンを追加
        JButton backToGameButton = new JButton("ゲームに戻る");
        backToGameButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
        backToGameButton.setBounds(240, 300, 160, 40);
        eventPanel.add(backToGameButton);

        backToGameButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor((Component)e.getSource());
            window.dispose();
        });
    }
    
    private void setChoiceButtonsVisible(boolean visible){
        for(JButton button : choiceButtons){
            button.setVisible(visible);
        }
    }
}