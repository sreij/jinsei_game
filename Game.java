import java.awt.Color;
import java.awt.Font; // Fontクラスをインポート
import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.*;

/*メインのクラス
 *ウィンドウを作成し、ゲームの進行を行う。
 *Playerオブジェクトとボードオブジェクトを管理する。
 */
class Game {
    private JFrame frame;
    private JPanel panel;
    private Player players[];
    private Board board;
    private int playerNum = 4;
    private int trout = 100;
    private int playingPlayer = 0; // 現在のプレイヤーのインデックス
    private int rollResult = 0; // サイコロの目の結果
    private boolean isEvent = false; // イベントが発生しているかどうかのフラグ
    private boolean isDrawBoard = false; // ボードを描画するかどうかのフラグ
    private JButton back;
    private int section = 0;

    // コンストラクタ
    Game(String name) {
        players = new Player[playerNum]; // 4players
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player("Player " + (i + 1));
        }
        board = new Board(trout); // ボードの初期化
        backButton(); // backボタンの設置
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (isDrawBoard)
                    drawBoard(g); // ボードを描画する
            }
        };
        panel.setLayout(null);

        frame = new JFrame(name);
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        // frame.setLayout(null);

        frame.setVisible(true);
    }

    // 戻るボタンの初期化と設定（右上に設置）
    final void backButton() {
        back = new JButton("戻る");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                section = 0;
                display();
            }
        });
        back.setBounds(1180, 20, 80, 30);
    }

    // 画面にボタンやテキストなどの要素を追加します
    void add(JComponent comp) {
        panel.add(comp);
    }

    void setIsEvent(boolean isEvent) {
        this.isEvent = isEvent; // イベントが発生しているかどうかのフラグを設定
    }

    // 描画用関数（コンポーネントのリセットと再描画）
    void display() {
        isDrawBoard = false; // ボードを描画するフラグを立てる
        // 画面のコンポーネントをリセット
        frame.remove(panel);
        panel.removeAll();
        switch (section) {
            case 0:
                start();
                break;
            case 1:
                setting();
                add(back);
                break;
            case 2:
                run();
                add(back);
                break;
            default:
                throw new AssertionError();
        }

        // panelの再描画
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        panel.revalidate();
        panel.repaint();

        // frameの再描画
        frame.add(panel);
        frame.revalidate();
        frame.repaint();

        // log
        System.out.println("display update OK");
    }

    // スタートアップ画面の設計
    void start() {
        // ウィンドウの幅と高さを取得
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        // タイトルの表示
        JLabel title = new JLabel("大学生人生ゲーム");
        // フォントを大きく、太字に設定
        title.setFont(new Font("SansSerif", Font.BOLD, 80));
        // テキストをラベルの中央に配置
        title.setHorizontalAlignment(SwingConstants.CENTER);
        // 位置とサイズを計算して設定 (中央より少し上)
        int titleWidth = 800;
        int titleHeight = 100;
        title.setBounds((frameWidth - titleWidth) / 2, frameHeight / 2 - titleHeight - 50, titleWidth, titleHeight);
        add(title);

        // スタートボタンの設計
        JButton startBtn = new JButton("スタート");
        startBtn.setFont(new Font("SansSerif", Font.PLAIN, 24)); // ボタンの文字も少し大きく
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                section = 2;
                display();
            }
        });
        // 位置とサイズを計算して設定 (中央より少し下)
        int startBtnWidth = 200;
        int startBtnHeight = 100;
        startBtn.setBounds((frameWidth - startBtnWidth) / 2, frameHeight / 2 + 50, startBtnWidth, startBtnHeight);
        add(startBtn);

        // 設定ボタンの設計
        JButton settingBtn = new JButton("設定");
        settingBtn.setFont(new Font("SansSerif", Font.PLAIN, 18));
        settingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                section = 1;
                display();
            }
        });
        // スタートボタンの下に配置
        int settingBtnWidth = 150;
        int settingBtnHeight = 50;
        settingBtn.setBounds((frameWidth - settingBtnWidth) / 2,
                frameHeight / 2 + 50 + startBtnHeight + 20, settingBtnWidth, settingBtnHeight);
        add(settingBtn);
    }

    // 設定画面の実装（何を設定するから決めてないが）
    void setting() {
        JLabel settingLabel = new JLabel("設定");
        settingLabel.setBounds(300, 300, 300, 300);
        add(settingLabel);
    }

    // ゲーム画面の実装
    void run() {
        isDrawBoard = true; // ボードを描画するフラグを立てる
        // 盤面の描画は、この下のdrawBoardメソッドで行う

        // 盤面にイベント名を追加
        for (int i = -2; i <= 2; i++) {
            JLabel eventLabel;
            if (players[playingPlayer].getPosition() + i < 0
                    || players[playingPlayer].getPosition() + i >= board.event.length) {
                eventLabel = new JLabel("");
            } else {
                eventLabel = new JLabel(board.event[players[playingPlayer].getPosition() + i].getEventName());
                if (i == 0) {
                    eventLabel.setFont(eventLabel.getFont().deriveFont(20f)); // 現在のイベントのフォントサイズを大きくする
                }
            }
            eventLabel.setBounds(panel.getWidth() / 5 * 2 + i * (panel.getWidth() / 5), panel.getHeight() / 4,
                    panel.getWidth() / 5, panel.getHeight() / 4);
            eventLabel.setHorizontalAlignment(JLabel.CENTER);
            add(eventLabel);
        }

        // プレイヤーステータス
        for (int i = 0; i < players.length; i++) {
            JLabel playerLabel = new JLabel(players[i].toString());
            playerLabel.setBounds(10 + i * (1280 / 4), 360, 250, 240);
            playerLabel.setFont(playerLabel.getFont().deriveFont(30f)); // フォントサイズを大きくする
            playerLabel.setForeground(Color.BLACK); // 文字色を黒に設定
            add(playerLabel);
        }

        // 現在のプレイヤー表示
        JLabel turnLabel = new JLabel("現在のプレイヤー: " + players[playingPlayer].getName());
        turnLabel.setBounds(400, 40, 400, 50);
        turnLabel.setFont(turnLabel.getFont().deriveFont(24f));
        add(turnLabel);

        // さいころの目
        JLabel roll = new JLabel(rollResult + "");
        roll.setBounds(480, 620, 100, 50);

        // さいころを振る
        JButton rollDise = new JButton("さいころを転がす");
        rollDise.setEnabled(true);
        rollDise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isEvent) {
                    rollResult = Dise.roll();
                    roll.setText(rollResult + "");
                    rollDise.setEnabled(false); // さいころを振った後はボタンを無効化
                    players[playingPlayer].addMoney(Dise.ROLL_COST); // サイコロを振るためのコストを引く
                    setIsEvent(true); // イベントが発生しているフラグを立てる
                    players[playingPlayer].move(rollResult); // プレイヤーの位置を更新
                    runEvent(rollDise);
                }
            }
        });
        rollDise.setBounds(320, 720 - 120 + 20, 150, 50);

        // 要素の追加
        add(roll);
        add(rollDise);
        if (!players[playingPlayer].getIsTutorial()) {
            rollDise.setEnabled(false); // チュートリアルのため、最初のマスではさいころを振れない
            runEvent(rollDise);
        }
    }

    // ボードの描画
    void drawBoard(Graphics g) {
        g.setColor(Color.BLACK);
        int w = panel.getWidth();
        int h = panel.getHeight();
        // 例：縦横に線を描画
        g.drawLine(0, h / 4, w, h / 4);
        g.drawLine(0, h / 2, w, h / 2);

        g.fillOval(w / 2 - 20, h / 4 + 15, 40, 40);

        for (int i = 0; i <= 4; i++) {
            g.drawLine(i * w / 5, h / 4, i * w / 5, h / 2); // 横線
        }
    }

    void runEvent(JButton rollDise) {
        JFrame eventFrame = new JFrame(
                players[playingPlayer].getName() + " : "
                        + board.event[players[playingPlayer].getPosition()].getEventName());
        eventFrame.setSize(640, 480);
        eventFrame.setResizable(false);
        eventFrame.setLocationRelativeTo(null);
        eventFrame.setLayout(null);
        eventFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                close();
            }

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                close();
            }

            // イベントの終了処理
            private void close() {
                setIsEvent(false);
                if (players[0].isGraduate() && players[1].isGraduate() && players[2].isGraduate()
                        && players[3].isGraduate()) {
                    // 全てのプレイヤーが卒業した場合、ゲーム終了
                    JOptionPane.showMessageDialog(frame, String.format("<html>全員卒業おめでとう！<br>タイトル画面に戻ります。</html>"));
                    section = 0; // タイトル画面に戻る
                    display();
                    return;
                }
                // 次のプレイヤーへ
                playingPlayer = (playingPlayer + 1) % players.length;
                while (players[playingPlayer].isGraduate()) {
                    playingPlayer = (playingPlayer + 1) % players.length; // 生存しているプレイヤーを探す
                }
                rollResult = 0;
                rollDise.setEnabled(true); // さいころを振るボタンを再度有効化
                display();
            }
        });

        int playerPosition = players[playingPlayer].getPosition(); // プレイヤーの位置を取得
        board.event[playerPosition >= board.event.length ? board.event.length - 1 : playerPosition]
                .run(players[playingPlayer]); // プレイヤーをイベントに設定
        eventFrame.add(board.event[players[playingPlayer].getPosition()].getPane()); // 最初のイベントパネルを取得して追加
        eventFrame.setVisible(true);
    }

    public static void main(String[] args) {
        Game game = new Game("大学生人生ゲーム");
        game.display();
    }
}