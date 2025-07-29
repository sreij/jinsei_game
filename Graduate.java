public class Graduate extends Event {
    private final int mustCredit = 124;
    Graduate(){
        super("卒業");
    }

    @Override
    public void execute(Player player) {
        if(player.getCredit() < mustCredit){
            System.out.println("NG");
        }
    }
    
}
