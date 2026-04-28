package users;

public enum ManagerType {
    HR(1),
    OR(2),
    FINANCE(3);

    private int number;

    private ManagerType(int number){
        setNumber(number);
    }

    public int getNumber(){
        return this.number;
    }

    private void setNumber(int number){
        this.number = number;
    }
}
