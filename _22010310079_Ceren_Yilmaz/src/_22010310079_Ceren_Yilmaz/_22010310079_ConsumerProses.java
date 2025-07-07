package _22010310079_Ceren_Yilmaz;

public class _22010310079_ConsumerProses extends _22010310079_Proses {
    
    public _22010310079_ConsumerProses(String name, _22010310079_Kernel kernel) {
        super(name, kernel);
    }
    
    public void read(int second) {
        while (kernel.getCurrentSecond() < second) {
            kernel.waitOneSecond();
            kernel.incrementSecond();
        }
        kernel.read(this);
    }
}