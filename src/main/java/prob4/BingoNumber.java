package main.java.prob4;

public class BingoNumber {
    private Integer number;
    private boolean called;

    public BingoNumber(Integer num) {
        this.setNumber(num);
        this.setCalled(false);
    }
    public Integer getNumber() {
        return number;
    }
    public void setNumber(Integer number) {
        this.number = number;
    }
    public boolean isCalled() {
        return called;
    }
    public void setCalled(boolean called) {
        this.called = called;
    }
    
}
