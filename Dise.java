import java.util.Random;

//サイコロのクラス
//やっていることは乱数を返すだけ
class Dise{
    static final int ROLL_COST=-1000;
    private static final Random rand = new Random();
    
    static int roll(){
        return roll(6);
    }
    
    static int roll(int max){
        return rand.nextInt(max) + 1;
    }
}