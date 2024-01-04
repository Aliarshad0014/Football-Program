import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CompetitorList {

    private ArrayList<Competitor> competitors;

    public CompetitorList(String competitorsFile) {
        this.competitors = readCompetitorsFromFile(competitorsFile);
    }

    public ArrayList<Competitor> readCompetitorsFromFile(String csvFilePath) {
        ArrayList<Competitor> competitors = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip header line
                }

                String[] values = line.split(",");
                int competitorNumber = Integer.parseInt(values[0]);
                String firstName = values[1];
                String lastName = values[2];
                Name name = new Name(firstName, lastName);
                String email = values[3];
                String dateOfBirth = values[4];
                String category = values[5];
                int[] scores = new int[] {
                        Integer.parseInt(values[6]),
                        Integer.parseInt(values[7]),
                        Integer.parseInt(values[8]),
                        Integer.parseInt(values[9]),
                        Integer.parseInt(values[10])
                };

                Competitor competitor = new Competitor(competitorNumber, name, email, dateOfBirth, category, scores);
                competitors.add(competitor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return competitors;
    }

    public ArrayList<Competitor> getCompetitors() {
        return competitors;
    }
}
