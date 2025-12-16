public class BankService{
    public static BankService instance;
    private BankService(){};
    public static BankService getInstance(){
        if(instance == null){
            instance = new BankService();
        }
        return instance;
    }
}