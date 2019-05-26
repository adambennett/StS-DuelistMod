package duelistmod;

import java.nio.charset.StandardCharsets;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.core.Settings;

public class Localization {

	// LOCALIZATION METHODS ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Copied from Jedi (who copied from Gatherer)
	public static String GetLocString(String locCode, String name)
	{
	    return Gdx.files.internal("resources/duelistModResources/localization/" + locCode + "/" + name + ".json").readString(String.valueOf(StandardCharsets.UTF_8));
	}

	public static String localize() 
	{
	    switch (Settings.language) 
	    {
	        //case RUS:
	        //    return "rus";
	        case ENG:
	            return "eng";
	            /*
	        case ZHS:
	        	return "zhs";
	        //case ZHT:
	       // 	return "zht";
	        case KOR:
	        	return "kor";
	        case DEU:
	        	return "deu";
	        case FRA:
	        	return "fra";
	        	*/
	        default:
	            return "eng";
	    }
	}

}
