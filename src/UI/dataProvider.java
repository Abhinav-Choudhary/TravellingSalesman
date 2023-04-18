package UI;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class dataProvider {

    private String txtFile;

    public dataProvider(String txtFile) {
        this.txtFile = txtFile;
    }

    public ArrayList<Node> parseData() {
        ArrayList<Node> nodes = new ArrayList<>();
        String line = "";
        String txtSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(txtFile))) {
            // Skip the first line (header)
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(txtSplitBy);
                String crimeID = data[0].trim();
                double longitude = Double.parseDouble(data[1].trim());
                double latitude = Double.parseDouble(data[2].trim());
                Node node = new Node(crimeID, latitude, longitude);
                nodes.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nodes;
    }

    public static class Node {
        private String crimeID;
        private double latitude;
        private double longitude;

        public Node(String crimeID, double latitude, double longitude) {
            this.crimeID = crimeID;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public String getCrimeID() {
            return crimeID;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public void setCrimeID(String crimeID) {
            this.crimeID = crimeID;
        }
    }
}
