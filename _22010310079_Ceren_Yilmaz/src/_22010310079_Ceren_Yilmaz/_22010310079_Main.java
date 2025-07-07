package _22010310079_Ceren_Yilmaz;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class _22010310079_Main {

    public static void main(String[] args) {
        _22010310079_Kernel kernel = new _22010310079_Kernel();
        Map<String, _22010310079_ProducerProses> producers = new HashMap<>();
        Map<String, _22010310079_ConsumerProses> consumers = new HashMap<>();
        List<EventData> events = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("girdi.txt"))) {
            String line;
            boolean readingEvents = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.equalsIgnoreCase("olaylar:")) {
                    readingEvents = true;
                    continue;
                }

                if (!readingEvents) {
                   
                    String[] parts = line.split(" ");
                    if (parts[0].equalsIgnoreCase("producer")) {
                        for (int i = 2; i < parts.length; i++) {
                            String name = parts[i];
                            producers.put(name, new _22010310079_ProducerProses(name, kernel));
                        }
                    } else if (parts[0].equalsIgnoreCase("consumer")) {
                        for (int i = 2; i < parts.length; i++) {
                            String name = parts[i];
                            consumers.put(name, new _22010310079_ConsumerProses(name, kernel));
                        }
                    }
                } else {
                   
                    String[] parts = line.split(" ", 3);
                    String name = parts[0];
                    int second = Integer.parseInt(parts[1]);
                    boolean isProducer = producers.containsKey(name);
                    String message = (parts.length == 3) ? parts[2] : null;

                    events.add(new EventData(name, second, message, isProducer));
                }
            }
        } catch (IOException e) {
            System.out.println("Dosya okunurken hata oluştu: " + e.getMessage());
            return;
        }

        events.sort(Comparator.comparingInt(e -> e.second));
        System.out.println("girdi.txt dosyası okundu.");
        System.out.print("0. Saniye:");

        for (EventData event : events) {
            if (event.isProducer) {
                producers.get(event.processName).write(event.message, event.second);
            } else {
                consumers.get(event.processName).read(event.second);
            }
        }
    }

    static class EventData {
        String processName;
        int second;
        String message;
        boolean isProducer;

        public EventData(String processName, int second, String message, boolean isProducer) {
            this.processName = processName;
            this.second = second;
            this.message = message;
            this.isProducer = isProducer;
        }
    }
}
