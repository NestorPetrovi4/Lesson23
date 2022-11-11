import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ClientLog {
    private ArrayList<String[]> baskets = new ArrayList<>();

    public void log(int productNum, int amount) {
        baskets.add(new String[]{Integer.toString(productNum), Integer.toString(amount)});
    }

    public void exportAsCSV(File txtFile) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile, true))) {
            writer.writeAll(baskets, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
