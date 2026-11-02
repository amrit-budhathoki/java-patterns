// Title: Strategy Pattern - Data Compression

// The Strategy pattern defines a family of algorithms, encapsulates each one,
// and makes them interchangeable. Use it when you have multiple ways to perform
// a task and want to switch between them at runtime without changing client code.

interface CompressionStrategy {
    String compress(String data);
}

class GzipCompression implements CompressionStrategy {
    @Override
    public String compress(String data) {
        return "[GZIP:" + data.replaceAll("a", "").replaceAll("e", "") + "]";
    }
}

class RleCompression implements CompressionStrategy {
    @Override
    public String compress(String data) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            int count = 1;
            while (i + 1 < data.length() && data.charAt(i + 1) == c) {
                count++;
                i++;
            }
            result.append(c).append(count);
        }
        return "[RLE:" + result + "]";
    }
}

class ZstdCompression implements CompressionStrategy {
    @Override
    public String compress(String data) {
        return "[ZSTD:" + data.substring(0, Math.min(3, data.length())) + "...]";
    }
}

class FileArchiver {
    private CompressionStrategy strategy;

    public FileArchiver(CompressionStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(CompressionStrategy strategy) {
        this.strategy = strategy;
    }

    public String archive(String data) {
        return strategy.compress(data);
    }
}

public class StrategyPatternCompressionExample {
    public static void main(String[] args) {
        String originalData = "aabbccddeeee";

        FileArchiver archiver = new FileArchiver(new GzipCompression());
        System.out.println("GZIP: " + archiver.archive(originalData));

        archiver.setStrategy(new RleCompression());
        System.out.println("RLE:  " + archiver.archive(originalData));

        archiver.setStrategy(new ZstdCompression());
        System.out.println("ZSTD: " + archiver.archive(originalData));
    }
}
