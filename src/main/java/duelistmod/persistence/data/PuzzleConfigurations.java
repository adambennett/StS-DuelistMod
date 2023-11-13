package duelistmod.persistence.data;

import duelistmod.dto.PuzzleConfigData;
import duelistmod.dto.StartingDeckStats;
import duelistmod.enums.DataCategoryType;
import duelistmod.enums.StartingDeck;
import duelistmod.persistence.DataDifferenceDTO;
import duelistmod.persistence.PersistentDuelistData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PuzzleConfigurations extends DataCategory {

    private HashMap<String, PuzzleConfigData> puzzleConfigurations = new HashMap<>();

    public PuzzleConfigurations() {
        this.category = "Puzzle Configuration Settings";
        this.type = DataCategoryType.Config_Setting;
    }

    public PuzzleConfigurations(PuzzleConfigurations from) {
        this(from.getPuzzleConfigurations());
    }

    public PuzzleConfigurations(HashMap<String, PuzzleConfigData> puzzleConfigurations) {
        this();
        this.puzzleConfigurations = puzzleConfigurations;
    }

    @Override
    public LinkedHashSet<DataDifferenceDTO<?>> generateMetricsDifferences(PersistentDuelistData defaultSettings, PersistentDuelistData playerSettings) {
        LinkedHashSet<DataDifferenceDTO<?>> output = new LinkedHashSet<>();
        for (Map.Entry<String, PuzzleConfigData> entry : this.puzzleConfigurations.entrySet()) {
            List<StartingDeck> deckMatches = Arrays.stream(StartingDeck.values()).filter(d -> d.getDeckId().equals(entry.getKey())).collect(Collectors.toList());
            if (!deckMatches.isEmpty()) {
                PuzzleConfigData base = deckMatches.get(0).getDefaultPuzzleConfig();
                PuzzleConfigData active = entry.getValue();
                for (Map.Entry<String, Object> e : active.getProperties().entrySet()) {
                    if (base.getProperties().containsKey(e.getKey()) && !base.getProperties().get(e.getKey()).equals(e.getValue())) {
                        output.add(new DataDifferenceDTO<>(this, "Puzzle Setting: " + e.getKey(), base.getProperties().get(e.getKey()), e.getValue()));
                    }
                }
            }
        }
        return output;
    }

    public HashMap<String, PuzzleConfigData> getPuzzleConfigurations() {
        return puzzleConfigurations;
    }

    public void setPuzzleConfigurations(HashMap<String, PuzzleConfigData> puzzleConfigurations) {
        this.puzzleConfigurations = puzzleConfigurations;
    }

    public HashMap<String, StartingDeckStats> getAllStats() {
        HashMap<String, StartingDeckStats> output = new HashMap<>();
        for (Map.Entry<String, PuzzleConfigData> entry : this.puzzleConfigurations.entrySet()) {
            output.put(entry.getKey(), entry.getValue().getStats());
        }
        return output;
    }
}
