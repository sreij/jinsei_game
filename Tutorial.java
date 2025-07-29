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
        JLabel label = new JLabel("ゲームスタート！ようこそ！\nまずは名前を変更しましょう！名前の変更をしたら、「決定」ボタンを押してください！");
        label.setSize(300, 50);
        JLabel nameLabel = new JLabel("プレイヤー名を入力してください:");
        nameLabel.setBounds(50, 60, 200, 30);
        JTextField inputField = new JTextField(player.getName());
        inputField.setBounds(50, 100, 200, 30);
        JButton submitButton = new JButton("決定");
        submitButton.setBounds(50, 140, 100, 30);
        submitButton.addActionListener(e -> {
            String newName = inputField.getText().trim();
            if (!newName.isEmpty()) {
                player.changeName(newName); // プレイヤーの名前を変更
                System.out.println("プレイヤー名が変更されました: " + newName);
            } else {
                System.out.println("プレイヤー名は空にできません。");
            }
        });
        eventPanel.add(label);
        eventPanel.add(nameLabel);
        eventPanel.add(inputField);
        eventPanel.add(submitButton);
        eventPanel.revalidate();
        eventPanel.repaint();
    }
}