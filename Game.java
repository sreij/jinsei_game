import java.awt.Color;
import java.awt.Font;
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
        isDrawBoard = false;
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

        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        panel.revalidate();
        panel.repaint();

        frame.add(panel);
        frame.revalidate();
        frame.repaint();

        System.out.println("display update OK");
    }

    // スタートアップ画面の設計
    void start() {
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        JLabel title = new JLabel("大学生人生ゲーム");
        title.setFont(new Font("SansSerif", Font.BOLD, 80));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        int titleWidth = 800;
        int titleHeight = 100;
        title.setBounds((frameWidth - titleWidth) / 2, frameHeight / 2 - titleHeight - 50, titleWidth, titleHeight);
        add(title);

        JButton startBtn = new JButton("スタート");
        startBtn.setFont(new Font("SansSerif", Font.PLAIN, 24));
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                section = 2;
                display();
            }
        });
        int startBtnWidth = 200;
        int startBtnHeight = 100;
        startBtn.setBounds((frameWidth - startBtnWidth) / 2, frameHeight / 2 + 50, startBtnWidth, startBtnHeight);
        add(startBtn);

        JButton settingBtn = new JButton("設定");
        settingBtn.setFont(new Font("SansSerif", Font.PLAIN, 18));
        settingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                section = 1;
                display();
            }
        });
        int settingBtnWidth = 150;
        int settingBtnHeight = 50;
        settingBtn.setBounds((frameWidth - settingBtnWidth) / 2,
                frameHeight / 2 + 50 + startBtnHeight + 20, settingBtnWidth, settingBtnHeight);
        add(settingBtn);
    }

    // 設定画面の実装
    void setting() {
        JLabel settingLabel = new JLabel("設定");
        settingLabel.setBounds(panel.getWidth() / 2 - 150, panel.getHeight() / 2 - 150, 300, 300);
        settingLabel.setFont(new Font("SansSerif", Font.BOLD, 50));
        settingLabel.setHorizontalAlignment(JLabel.CENTER);
        add(settingLabel);
    }

    // ゲーム画面の実装
    void run() {
        isDrawBoard = true;

        for (int i = -2; i <= 2; i++) {
            JLabel eventLabel;
            if (players[playingPlayer].getPosition() + i < 0
                    || players[playingPlayer].getPosition() + i >= board.event.length) {
                eventLabel = new JLabel("");
            } else {
                eventLabel = new JLabel(board.event[players[playingPlayer].getPosition() + i].getEventName());
                if (i == 0) {
                    eventLabel.setFont(eventLabel.getFont().deriveFont(20f));
                }
            }
            eventLabel.setBounds(panel.getWidth() / 5 * 2 + i * (panel.getWidth() / 5), panel.getHeight() / 4,
                    panel.getWidth() / 5, panel.getHeight() / 4);
            eventLabel.setHorizontalAlignment(JLabel.CENTER);
            add(eventLabel);
        }

        for (int i = 0; i < players.length; i++) {
            JLabel playerLabel = new JLabel(players[i].toString());
            playerLabel.setBounds(10 + i * (1280 / 4), 360, 250, 240);
            playerLabel.setFont(playerLabel.getFont().deriveFont(30f));
            playerLabel.setForeground(Color.BLACK);
            add(playerLabel);
        }

        JLabel turnLabel = new JLabel("現在のプレイヤー: " + players[playingPlayer].getName());
        turnLabel.setBounds(400, 40, 400, 50);
        turnLabel.setFont(turnLabel.getFont().deriveFont(24f));
        add(turnLabel);

        JLabel roll = new JLabel(rollResult + "");
        roll.setBounds(480, 620, 100, 50);

        JButton rollDise = new JButton("さいころを転がす");
        rollDise.setEnabled(true);
        rollDise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isEvent) {
                    rollResult = Dise.roll();
                    roll.setText(rollResult + "");
                    rollDise.setEnabled(false);
                    players[playingPlayer].addMoney(Dise.ROLL_COST);
                    setIsEvent(true);

                    // ▼▼▼【ここから修正】▼▼▼
                    players[playingPlayer].move(rollResult); // プレイヤーの位置を更新

                    // ゴールマスを超えた場合の処理
                    if (players[playingPlayer].getPosition() >= trout - 1) {
                        players[playingPlayer].setPosition(trout - 1); // 最終マスに固定
                    }
                    // ▲▲▲【ここまで修正】▲▲▲

                    runEvent(rollDise);
                }
            }
        });
        rollDise.setBounds(320, 720 - 120 + 20, 150, 50);

        add(roll);
        add(rollDise);
        if (!players[playingPlayer].getIsTutorial()) {
            rollDise.setEnabled(false);
            runEvent(rollDise);
        }
    }

    // ボードの描画
    void drawBoard(Graphics g) {
        g.setColor(Color.BLACK);
        int w = panel.getWidth();
        int h = panel.getHeight();
        g.drawLine(0, h / 4, w, h / 4);
        g.drawLine(0, h / 2, w, h / 2);
        g.fillOval(w / 2 - 20, h / 4 + 15, 40, 40);
        for (int i = 0; i <= 4; i++) {
            g.drawLine(i * w / 5, h / 4, i * w / 5, h / 2);
        }
    }

    void runEvent(JButton rollDise) {
        int playerPosition = players[playingPlayer].getPosition();
        JFrame eventFrame = new JFrame(
                players[playingPlayer].getName() + " : " + board.event[playerPosition].getEventName());
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

            private void close() {
                setIsEvent(false);

                // ▼▼▼【ここから修正】▼▼▼
                // 全員がゴールしたかチェック
                boolean allPlayersFinished = true;
                for (Player p : players) {
                    if (!p.isGraduate()) {
                        allPlayersFinished = false;
                        break;
                    }
                }

                if (allPlayersFinished) {
                    JOptionPane.showMessageDialog(frame, String.format("<html>全員ゴールしました！<br>タイトル画面に戻ります。</html>"));
                    // ここでゲームをリセットする処理を追加することも可能
                    section = 0;
                    display();
                    return;
                }

                // 次のプレイヤーへ（ゴール済みのプレイヤーはスキップ）
                do {
                    playingPlayer = (playingPlayer + 1) % players.length;
                } while (players[playingPlayer].isGraduate());
                // ▲▲▲【ここまで修正】▲▲▲

                rollResult = 0;
                rollDise.setEnabled(true);
                display();
            }
        });

        board.event[playerPosition].run(players[playingPlayer]);
        eventFrame.add(board.event[playerPosition].getPane());
        eventFrame.setVisible(true);
    }

    public static void main(String[] args) {
        Game game = new Game("大学生人生ゲーム");
        game.display();
    }
}