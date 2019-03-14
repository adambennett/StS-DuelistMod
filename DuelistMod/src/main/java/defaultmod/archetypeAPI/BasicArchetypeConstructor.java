package defaultmod.archetypeAPI;

import java.util.ArrayList;

import archetypeAPI.archetypes.abstractArchetype;

public class BasicArchetypeConstructor extends abstractArchetype {
    public static ArrayList<String> archetypeCardNames = new ArrayList<>();

    public BasicArchetypeConstructor() {
        super(archetypeCardNames);
    }
}
