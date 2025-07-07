package _22010310079_Ceren_Yilmaz;

public class _22010310079_ProducerProses extends _22010310079_Proses {
    private String pendingMessage;
    
    public _22010310079_ProducerProses(String name, _22010310079_Kernel kernel) {
        super(name, kernel);
    }
    
    public void write(String message, int second) {
        this.pendingMessage = message;
        while (kernel.getCurrentSecond() < second) {
            kernel.waitOneSecond();
            kernel.incrementSecond();
        }
        kernel.write(this, message);
    }
    
    public String getPendingMessage() {
        return pendingMessage;
    }
}