package defaultmod.archetypeAPI;

import java.util.ArrayList;

import archetypeAPI.archetypes.abstractArchetype;

public class ResummonArchetypeConstructor extends abstractArchetype {
    public static ArrayList<String> archetypeCardNames = new ArrayList<>();

    public ResummonArchetypeConstructor() {
        super(archetypeCardNames);
    }
}
