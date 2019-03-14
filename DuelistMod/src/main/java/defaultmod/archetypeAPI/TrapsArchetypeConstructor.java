package defaultmod.archetypeAPI;

import java.util.ArrayList;

import archetypeAPI.archetypes.abstractArchetype;

public class TrapsArchetypeConstructor extends abstractArchetype {
    public static ArrayList<String> archetypeCardNames = new ArrayList<>();

    public TrapsArchetypeConstructor() {
        super(archetypeCardNames);
    }
}
