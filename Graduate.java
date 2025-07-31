import javax.swing.*;
import java.awt.*;

public class Graduate extends Event {
    private final int mustCredit = 124;

    Graduate(){
        super("卒業判定");
    }

    @Override
    public void execute(Player player) {
        // プレイヤーがゴールに到達したことを記録します
        player.setGraduate(true);

        // 結果を表示するためのラベルを作成します
        JLabel resultLabel = new JLabel();
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setBounds(0, 150, 640, 100); // 高さを確保

        // 単位数に応じてメッセージを決定します
        if(player.getCredit() >= mustCredit){
            resultLabel.setText("<html><center>🎉 卒業おめでとう！ 🎉</center></html>");
        } else {
            resultLabel.setText(String.format(
                "<html><center>単位が足りない...<br>残念ながら留年です... (%d / %d単位)</center></html>",
                player.getCredit(),
                mustCredit
            ));
        }

        // ラベルと「ゲームに戻る」ボタンを画面に追加します
        eventPanel.add(resultLabel);
        eventPanel.add(backToGameButton);
    }
}