
import javax.swing.JLabel;

class Daily extends Event{
    int[] moneys = {1000, 2000, 3000, 4000, 5000};

    Daily(){
        super("デイリー");
    }

    @Override
    public void execute(Player player) {
        JLabel result = new JLabel();
        result.setBounds(50, 50, 500, 30);
        eventPanel.add(result);
        
        if(Dise.roll(3) >= 2){
            player.addMoney(1000);
            result.setText(String.format("デイリーボーナス！%d円獲得！", moneys[Dise.roll(5)-1]));
        }else{
            player.addMoney(-1000);
            result.setText(String.format("今日は運が悪い！%d円失った...", -moneys[Dise.roll(5)-1]));
        }
    }
}