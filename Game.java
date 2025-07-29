import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

/*メインのクラス
 *ウィンドウを作成し、ゲームの進行を行う。
 *Playerオブジェクトとボードオブジェクトを管理する。
 */
class Game{
    private JFrame frame;
    private JPanel panel;
    private Player players[];
    private Board board;
    private int playerNum = 4;
    private int trout = 100;
    private int playingPlayer = 0; //現在のプレイヤーのインデックス
    private int rollResult = 0; //サイコロの目の結果
    private boolean isEvent = false; //イベントが発生しているかどうかのフラグ
    private JButton back;
    private int section = 0;

    //コンストラクタ
    Game(String name){
        players = new Player[playerNum];    //4players
        for(int i = 0; i < players.length; i++) {
            players[i] = new Player("Player " + (i + 1));
        }
        board = new Board(trout);   //ボードの初期化
        backButton();               //backボタンの設置
        panel = new JPanel();       
        panel.setLayout(null);

        frame = new JFrame(name);
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        //frame.setLayout(null);

        frame.setVisible(true);
    }

    //戻るボタンの初期化と設定（右上に設置）
    final void backButton(){
        back = new JButton("戻る");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                section = 0;
                display();
            }
        });
        back.setBounds(1180, 20,80,30);
    }

    //画面にボタンやテキストなどの要素を追加します
    void add(JComponent comp){
        panel.add(comp);
    }

    void setIsEvent(boolean isEvent){
        this.isEvent = isEvent; //イベントが発生しているかどうかのフラグを設定
    }
    
    //描画用関数（コンポーネントのリセットと再描画）
    void display(){
        frame.remove(panel);
        panel.removeAll();  //画面のコンポーネントを削除
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
        
        //panelの再描画
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        panel.revalidate();
        panel.repaint();

        //frameの再描画
        frame.add(panel);
        frame.revalidate();
        frame.repaint();

        //log
        System.out.println("display update OK");
    }

    //スタートアップ画面の設計
    void start(){
        //タイトルの表示
        JLabel title= new JLabel("大学生人生ゲーム");
        title.setBounds(10,100,100,100);
        add(title);

        //スタートボタンの設計
        JButton startBtn = new JButton("スタート");
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                section = 2;
                display();
            }
        });
        startBtn.setBounds(10,300,200,100);
        add(startBtn);

        //設定ボタンの設計
        JButton settingBtn = new JButton("設定");
        settingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                section = 1;
                display();
            }
        });
        settingBtn.setBounds(10,600,100,100);
        add(settingBtn);
    }

    //設定画面の実装（何を設定するから決めてないが）
    void setting(){
        JLabel settingLabel = new JLabel("設定");
        settingLabel.setBounds(300,300,300,300);
        add(settingLabel);
    }

    //ゲーム画面の実装
    void run(){
        //プレイヤーステータス
        for(int i = 0; i < players.length; i++) {
            JLabel playerLabel = new JLabel(players[i].toString());
            playerLabel.setBounds(10 + i * (1280/4), 360, 250, 240);
            playerLabel.setFont(playerLabel.getFont().deriveFont(30f)); //フォントサイズを大きくする
            playerLabel.setForeground(Color.BLACK); //文字色を黒に設定
            add(playerLabel);
        }

        //現在のプレイヤー表示  
        JLabel turnLabel = new JLabel("現在のプレイヤー: " + players[playingPlayer].getName());
        turnLabel.setBounds(400, 40, 400, 50);
        turnLabel.setFont(turnLabel.getFont().deriveFont(24f));
        add(turnLabel);

        //さいころの目
        JLabel roll = new JLabel(rollResult + "");
        roll.setBounds(480,620,100,50);

        //さいころを振る
        JButton rollDise = new JButton("さいころを転がす");
        rollDise.setEnabled(true);
        rollDise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if (!isEvent) {
                    rollResult = Dise.roll();
                    roll.setText(rollResult + "");
                    rollDise.setEnabled(false); // さいころを振った後はボタンを無効化
                    players[playingPlayer].addMoney(Dise.ROLL_COST); // サイコロを振るためのコストを引く
                    setIsEvent(true); // イベントが発生しているフラグを立てる
                    players[playingPlayer].move(rollResult); //プレイヤーの位置を更新
                    runEvent(rollDise);
                }
            }
        });
        rollDise.setBounds(320,720-120+20,150,50);

        //要素の追加
        add(roll);
        add(rollDise);
        if(!players[playingPlayer].getIsTutorial()){
            rollDise.setEnabled(false); // チュートリアルのため、最初のマスではさいころを振れない
            runEvent(rollDise);
        }
    }

    void runEvent(JButton rollDise) {
        JFrame eventFrame = new JFrame(players[playingPlayer].getName() + " : " + board.event[players[playingPlayer].getPosition()].getEventName());
        eventFrame.setSize(640, 480);
        eventFrame.setResizable(false);
        eventFrame.setLocationRelativeTo(null);
        eventFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                setIsEvent(false);
                // 次のプレイヤーへ
                playingPlayer = (playingPlayer + 1) % players.length;
                rollResult = 0;
                rollDise.setEnabled(true); // さいころを振るボタンを再度有効化
                display();
            }
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                setIsEvent(false);
                // 次のプレイヤーへ
                playingPlayer = (playingPlayer + 1) % players.length;
                rollResult = 0; 
                rollDise.setEnabled(true); // さいころを振るボタンを再度有効化
                display();
            }
        });

        eventFrame.setResizable(false);
        JPanel eventPanel = board.event[players[playingPlayer].getPosition()].getPane(); //最初のイベントパネルを取得
        board.event[players[playingPlayer].getPosition()].run(players[playingPlayer]); //プレイヤーをイベントに設定
        eventFrame.add(eventPanel);
        eventFrame.setVisible(true);
    }

    public static void main(String[] args){
        Game game = new Game("大学生人生ゲーム");
        game.display();
    }
}