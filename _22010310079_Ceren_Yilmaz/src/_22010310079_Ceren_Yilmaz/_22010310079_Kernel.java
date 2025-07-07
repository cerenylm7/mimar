package _22010310079_Ceren_Yilmaz;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class _22010310079_Kernel {
    private final int PIPE_CAPACITY = 3;
    private final Queue<String> pipe;
    private final Queue<_22010310079_ConsumerProses> blockedConsumers;
    private final Queue<_22010310079_ProducerProses> blockedProducers;
    private int currentSecond;

    public _22010310079_Kernel() {
        pipe = new LinkedList<>();
        blockedConsumers = new LinkedList<>();
        blockedProducers = new LinkedList<>();
        currentSecond = 0;
    }
    
    public void incrementSecond() {
        currentSecond++;
        System.out.print(currentSecond + ". Saniye:");
    }
    
    public int getCurrentSecond() {
        return currentSecond;
    }
    
    public void write(_22010310079_ProducerProses producer, String message) {
        if (message.length() > 20) {
            System.out.print(producer.getName() + " prosesi 20 karakterden uzun mesaj gönderemez. Mesaj yazılmadı.");
            return;
        }

        if (pipe.size() == PIPE_CAPACITY) {
            System.out.print(producer.getName()+ " prosesi write çağrısı yaptı. "
                    + producer.getName() + " prosesi bloklandı ve kuyruğa alındı.");
            blockedProducers.add(producer);
        } else {
            System.out.print(producer.getName() + " prosesi write çağrısı yaptı.");
            pipe.add(message);

            if (!blockedConsumers.isEmpty()) {
                _22010310079_ConsumerProses consumer = blockedConsumers.poll();
                String readMessage = pipe.poll();
                System.out.print(consumer.getName() + " prosesi uyandırıldı. Okunan mesaj: " + readMessage);
            }
        }
    }

    
    public void read(_22010310079_ConsumerProses consumer) {
        if (pipe.isEmpty()) {
            System.out.print(consumer.getName() + " prosesi read çağrısı yaptı. " 
                    + consumer.getName() + " prosesi bloklandı ve kuyruğa alındı.");
            blockedConsumers.add(consumer);
        } else {
            String message = pipe.poll();
            System.out.print(consumer.getName() + " prosesi read çağrısı yaptı. Okunan mesaj: " + message);
            
            if (!blockedProducers.isEmpty()) {
                _22010310079_ProducerProses producer = blockedProducers.poll();
                String pendingMessage = producer.getPendingMessage();
                pipe.add(pendingMessage);
                System.out.print(producer.getName() + " prosesi uyandırıldı.");
            }
        }
    }
    
    public void waitOneSecond() {
        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println();
        } 
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}