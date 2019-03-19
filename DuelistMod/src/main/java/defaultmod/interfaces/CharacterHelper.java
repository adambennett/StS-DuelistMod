package defaultmod.interfaces;

import com.megacrit.cardcrawl.core.Settings;

public class CharacterHelper 
{
	@SuppressWarnings("unused")
	private static String localize() 
	{
        switch (Settings.language) 
        {
            case RUS:
                return "rus";
            case ENG:
                return "eng";
            case ZHS:
            	return "zhs";
            case ZHT:
            	return "zht";
            case KOR:
            	return "kor";
            default:
                return "eng";
        }
    }
	
	public static void loadArchetypes()
	{
		//String loc = localize();
		/*
		BasicArchetypeConstructor.archetypeCardNames.add("defaultModResources/localization/APIJsons/Basic-Archetype-Duelist.json");
		TheDuelist.theDuelistArchetypeSelectionCards.addToTop(new BasicArchetype().makeCopy());
		
		DragonArchetypeConstructor.archetypeCardNames.add("defaultModResources/localization/APIJsons/Dragon-Archetype-Duelist.json");
		TheDuelist.theDuelistArchetypeSelectionCards.addToTop(new DragonsArchetype().makeCopy());
	
		ExodiaArchetypeConstructor.archetypeCardNames.add("defaultModResources/localization/APIJsons/Exodia-Archetype-Duelist.json");
		TheDuelist.theDuelistArchetypeSelectionCards.addToTop(new ExodiaArchetype().makeCopy());
		
		GodArchetypeConstructor.archetypeCardNames.add("defaultModResources/localization/APIJsons/God-Archetype-Duelist.json");
		TheDuelist.theDuelistArchetypeSelectionCards.addToTop(new GodArchetype().makeCopy());
		
		GuardianArchetypeConstructor.archetypeCardNames.add("defaultModResources/localization/APIJsons/Guardian-Archetype-Duelist.json");
		TheDuelist.theDuelistArchetypeSelectionCards.addToTop(new GuardianArchetype().makeCopy());
		
		InsectsArchetypeConstructor.archetypeCardNames.add("defaultModResources/localization/APIJsons/Insects-Archetype-Duelist.json");
		TheDuelist.theDuelistArchetypeSelectionCards.addToTop(new InsectArchetype().makeCopy());
		
		MagnetArchetypeConstructor.archetypeCardNames.add("defaultModResources/localization/APIJsons/Magnets-Archetype-Duelist.json");
		TheDuelist.theDuelistArchetypeSelectionCards.addToTop(new MagnetArchetype().makeCopy());
		
		NatureArchetypeConstructor.archetypeCardNames.add("defaultModResources/localization/APIJsons/Nature-Archetype-Duelist.json");
		TheDuelist.theDuelistArchetypeSelectionCards.addToTop(new NatureArchetype().makeCopy());
		
		OjamaArchetypeConstructor.archetypeCardNames.add("defaultModResources/localization/APIJsonsOjama-Archetype-Duelist.json");
		TheDuelist.theDuelistArchetypeSelectionCards.addToTop(new OjamaArchetype().makeCopy());
		
		PlantsArchetypeConstructor.archetypeCardNames.add("defaultModResources/localization/APIJsons/Plants-Archetype-Duelist.json");
		TheDuelist.theDuelistArchetypeSelectionCards.addToTop(new PlantsArchetype().makeCopy());
	
		PotsArchetypeConstructor.archetypeCardNames.add("defaultModResources/localization/APIJsons/Pots-Archetype-Duelist.json");
		TheDuelist.theDuelistArchetypeSelectionCards.addToTop(new PotsArchetype().makeCopy());
		
		ResummonArchetypeConstructor.archetypeCardNames.add("defaultModResources/localization/APIJsons/Resummon-Archetype-Duelist.json");
		TheDuelist.theDuelistArchetypeSelectionCards.addToTop(new ResummonArchetype().makeCopy());
		
		SpellcasterArchetypeConstructor.archetypeCardNames.add("defaultModResources/localization/APIJsons/Spellcaster-Archetype-Duelist.json");
		TheDuelist.theDuelistArchetypeSelectionCards.addToTop(new SpellcasterArchetype().makeCopy());
		
		SpellsArchetypeConstructor.archetypeCardNames.add("defaultModResources/localization/APIJsons/Spells-Archetype-Duelist.json");
		TheDuelist.theDuelistArchetypeSelectionCards.addToTop(new SpellsArchetype().makeCopy());
		
		SuperheavyArchetypeConstructor.archetypeCardNames.add("defaultModResources/localization/APIJsons/Superheavy-Archetype-Duelist.json");
		TheDuelist.theDuelistArchetypeSelectionCards.addToTop(new SuperheavyArchetype().makeCopy());
		
		ToonsArchetypeConstructor.archetypeCardNames.add("defaultModResources/localization/APIJsons/Toons-Archetype-Duelist.json");
		TheDuelist.theDuelistArchetypeSelectionCards.addToTop(new ToonsArchetype().makeCopy());
		
		TrapsArchetypeConstructor.archetypeCardNames.add("defaultModResources/localization/APIJsons/Traps-Archetype-Duelist.json");
		TheDuelist.theDuelistArchetypeSelectionCards.addToTop(new TrapsArchetype().makeCopy());
		*/
	}
}
