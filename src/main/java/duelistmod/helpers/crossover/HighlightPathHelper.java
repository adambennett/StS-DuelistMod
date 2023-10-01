package duelistmod.helpers.crossover;

import HighlightPath.patches.RightClickMapNodePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import duelistmod.DuelistMod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HighlightPathHelper {

    private static final HashMap<MapRoomNode, Boolean> nodeHighlightInfo = new HashMap<>();

    public static boolean isHighlighted(String node) {
        return DuelistMod.persistentDuelistData.highlightedNodes.contains(node) || nodeHighlightInfo.entrySet().stream().anyMatch(n -> n.getKey().toString().equalsIgnoreCase(node) && n.getValue() != null && n.getValue());
    }

    public static boolean isHighlighted(MapRoomNode node) {
        return DuelistMod.persistentDuelistData.highlightedNodes.contains(node.toString()) || nodeHighlightInfo.getOrDefault(node, false);
    }

    public static void updateInfo(MapRoomNode node, boolean isHighlighted) {
        nodeHighlightInfo.put(node, isHighlighted);
        if (!isHighlighted) {
            DuelistMod.persistentDuelistData.highlightedNodes.remove(node.toString());
        }
    }

    public static void onSave() {
        List<String> persistenceNodes = new ArrayList<>();
        for (Map.Entry<MapRoomNode, Boolean> entry : nodeHighlightInfo.entrySet()) {
            if (entry.getValue() != null && entry.getValue()) {
                persistenceNodes.add(entry.getKey().toString());
            }
        }
        DuelistMod.persistentDuelistData.highlightedNodes = persistenceNodes;
        DuelistMod.configSettingsLoader.save();
    }

    public static void onLoad() {
        if (AbstractDungeon.map != null) {
            for (ArrayList<MapRoomNode> a : AbstractDungeon.map) {
                for (MapRoomNode node : a) {
                    if (DuelistMod.persistentDuelistData.highlightedNodes.contains(node.toString())) {
                        RightClickMapNodePatch.HighlightedField.isHighlighted.set(node, true);
                        updateInfo(node, true);
                    }
                }
            }
        }
    }

    public static void reset() {
        nodeHighlightInfo.clear();
        DuelistMod.persistentDuelistData.highlightedNodes.clear();
    }

}
