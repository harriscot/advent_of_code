package main.java.prob6;

public class LanternFish {
    private int daysUntilGivesBirth;

    public LanternFish(int daysUntilGivesBirth){
        this.daysUntilGivesBirth = daysUntilGivesBirth;
    }

    public int getDaysUntilGivesBirth() {
        return daysUntilGivesBirth;
    }

    public void setDaysUntilGivesBirth(int days) {
        daysUntilGivesBirth = days;
    }


    // boolean givesBirth(){
    //     if(daysUntilGivesBirth == 0){
    //         daysUntilGivesBirth = 6;
    //         return true;
    //     } else  {
    //         daysUntilGivesBirth --;
    //         return false;
    //     }
    // }

    public String toString(){
        return("days until next child: " + daysUntilGivesBirth);
    }
}
