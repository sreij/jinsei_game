class Player {

    private String name;
    private int position;
    private int credit;
    private int money;
    private boolean graduate = false; // プレイヤーの卒業状態
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

    public boolean isGraduate() {
        return graduate;
    }

    public boolean getIsTutorial() {
        return isTutorial;
    }

    // プレイヤーの卒業状態を設定するメソッド
    public void setGraduate(boolean graduate) {
        this.graduate = graduate;
    }
    
    /*
     * セッターメソッド
     */
    // プレイヤーの位置を直接設定するメソッド
    public void setPosition(int position) {
        this.position = position;
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

    //チュートリアル実施状況の更新
    public void tutorialed() {
        isTutorial = true; // チュートリアルを受けたフラグを立てる
    }

    //名前の変更
    public void changeName(String newName) {
        this.name = newName; // プレイヤーの名前を変更
    }

    @Override
    public String toString() {
        return String.format("<html><b>%s</b><br>位置: %d<br>単位: %d<br>所持金: %d円</html>",
                name, position, credit, money);
    }
}