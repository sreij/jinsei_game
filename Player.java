//プレイヤーを管理するクラス
//プレイヤーに関する情報を保持
class Player {

    private String name;
    private int position;
    private int credit;
    private int money;
    private boolean isTutorial = false; // チュートリアルフラグ

    Player(String name) {
        this.name = name;
        position = 0;
        credit = 0;
        money = 10000;
    }

    /*
     * ゲッターメソッド
     */
    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public int getCredit() {
        return credit;
    }

    public int getMoney() {
        return money;
    }

    public boolean getIsTutorial() {
        return isTutorial;
    }

    //盤面移動
    public void move(int step) {
        position += step;
    }

    //単位取得
    public void addCredit(int credit) {
        this.credit += credit;
    }

    //お金取得
    public void addMoney(int money) {
        this.money += money;
    }

    public void tutorialed() {
        isTutorial = true; // チュートリアルを受けたフラグを立てる
    }

    public void changeName(String newName) {
        this.name = newName; // プレイヤーの名前を変更
    }   

    //プレイヤーの状態を表示
    /*
     * 名前（太字）
     * 位置: ?
     * 単位: ?
     * 所持金: ?
     */
    @Override
    public String toString() {
        return String.format("<html><b>%s</b><br>位置: %d<br>単位: %d<br>所持金: %d円</html>",
                name, position, credit, money);
    }

}
