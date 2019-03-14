package defaultmod.archetypeAPI;

import java.util.ArrayList;

import archetypeAPI.archetypes.abstractArchetype;

public class GodArchetypeConstructor extends abstractArchetype {
    public static ArrayList<String> archetypeCardNames = new ArrayList<>();

    public GodArchetypeConstructor() {
        super(archetypeCardNames);
    }
}
