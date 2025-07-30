import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

class Tutorial extends Event{
    private final String explainText  = String.format("<html><h2>ゲームの進め方</h2><br>1. サイコロを振って、出た目の数だけ盤面を進みます。<br>2. 盤面上のイベントに応じて、クイズやタイピングなどのミニゲームが発生します。<br>3. ミニゲームをクリアすると、単位やお金がもらえます。<br>4. 単位を集めて卒業を目指しましょう！<br>ミニゲームが終わったら、Windowを閉じてください！<br><br>まずは名前を変更しましょう！<br>変更ができたら「決定」ボタンを押してください！</html>");

    Tutorial(){
        super("スタート");
    }

    @Override
    public void execute(Player player) {
        //eventPanel.removeAll();
        player.tutorialed(); // チュートリアルを受けたフラグを立てる

        // 説明ラベル
        JLabel explainLabel = new JLabel(explainText);
        explainLabel.setBounds(50, 0, 500, 200);

        // 名前入力フィールドとボタン
        JLabel nameLabel = new JLabel("プレイヤー名を入力してください:");
        nameLabel.setBounds(50, 220, 200, 30);

        JTextField inputField = new JTextField(player.getName());
        inputField.setBounds(50, 250, 200, 30);

        JLabel statusLabel = new JLabel("");
        statusLabel.setBounds(50, 290, 500, 30);

        JButton submitButton = new JButton("決定");
        submitButton.setBounds(300, 250, 100, 30);
        submitButton.addActionListener(e -> {
            String newName = inputField.getText().trim();
            if (!newName.isEmpty()) {
                player.changeName(newName); // プレイヤーの名前を変更
                statusLabel.setText("プレイヤー名が変更されました: " + newName);
            } else {
                statusLabel.setText("プレイヤー名は空にできません。");
            }
        });

        // イベントパネルにコンポーネントを追加
        eventPanel.add(nameLabel);
        eventPanel.add(statusLabel);
        eventPanel.add(inputField);
        eventPanel.add(submitButton);
        eventPanel.add(explainLabel);
        eventPanel.revalidate();
        eventPanel.repaint();
    }
}