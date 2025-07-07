package _22010310079_Ceren_Yilmaz;

public abstract class _22010310079_Proses {
    protected String name;
    protected _22010310079_Kernel kernel;
    
    public _22010310079_Proses(String name, _22010310079_Kernel kernel) {
        this.name = name;
        this.kernel = kernel;
    }
    
    public String getName() {
        return name;
    }
}