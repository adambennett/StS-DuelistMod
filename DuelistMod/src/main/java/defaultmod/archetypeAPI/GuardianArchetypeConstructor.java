package defaultmod.archetypeAPI;

import java.util.ArrayList;

import archetypeAPI.archetypes.abstractArchetype;

public class GuardianArchetypeConstructor extends abstractArchetype {
    public static ArrayList<String> archetypeCardNames = new ArrayList<>();

    public GuardianArchetypeConstructor() {
        super(archetypeCardNames);
    }
}
