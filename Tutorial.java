import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

class Tutorial extends Event{
    Tutorial(){
        super("スタート");
    }

    @Override
    public void execute(Player player) {
        //eventPanel.removeAll();
        player.tutorialed(); // チュートリアルを受けたフラグを立てる
        JLabel label = new JLabel(String.format("<html>ゲームスタート！ようこそ！<br>まずは名前を変更しましょう！名前の変更をしたら、「決定」ボタンを押してください！</html>"));
        label.setBounds(0,0,640, 100);
        JLabel nameLabel = new JLabel("プレイヤー名を入力してください:");
        nameLabel.setBounds(50, 70, 200, 30);
        JTextField inputField = new JTextField(player.getName());
        inputField.setBounds(50, 225, 200, 30);
        JLabel statusLabel = new JLabel("");
        statusLabel.setBounds(50, 260, 500, 30);
        JButton submitButton = new JButton("決定");
        submitButton.setBounds(300, 225, 100, 30);
        submitButton.addActionListener(e -> {
            String newName = inputField.getText().trim();
            if (!newName.isEmpty()) {
                player.changeName(newName); // プレイヤーの名前を変更
                statusLabel.setText("プレイヤー名が変更されました: " + newName);
            } else {
                statusLabel.setText("プレイヤー名は空にできません。");
            }
        });
        eventPanel.add(label);
        eventPanel.add(nameLabel);
        eventPanel.add(statusLabel);
        eventPanel.add(inputField);
        eventPanel.add(submitButton);
        eventPanel.revalidate();
        eventPanel.repaint();
    }
}