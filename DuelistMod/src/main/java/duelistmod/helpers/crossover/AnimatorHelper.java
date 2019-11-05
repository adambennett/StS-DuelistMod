package duelistmod.helpers.crossover;

import java.nio.charset.StandardCharsets;
import java.util.*;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import eatyourbeets.relics.animator.*;

public class AnimatorHelper 
{

	public static void extraRelics()
	{
		Util.log("Adding a select set of relics from The Animator to the Duelist relic pool");
		BaseMod.addRelicToCustomPool(new Buoy(), AbstractCardEnum.DUELIST);
		BaseMod.addRelicToCustomPool(new CursedBlade(), AbstractCardEnum.DUELIST);
		
		UnlockTracker.markRelicAsSeen(Buoy.ID);
		UnlockTracker.markRelicAsSeen(CursedBlade.ID);		
	}
	
	public static ArrayList<AbstractCard> getAllCards() throws IllegalAccessException
	{
		ArrayList<AbstractCard> cards = new ArrayList<>();
		String jsonString = Gdx.files.internal("localization/eng/Animator_CardStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Map<String, CardStrings> map = new Gson().fromJson(jsonString, new TypeToken<HashMap<String, CardStrings>>()
        {
        }.getType());
        for (String s : map.keySet())
        {
            try
            {
            	Class cardClass = Class.forName("eatyourbeets.cards.animator." + s.replace("animator_", ""));
                AbstractCard card = new CancelCard();
				try {
					card = (AbstractCard) cardClass.newInstance();
				} catch (InstantiationException e) {
					Util.log("Error instantiating Animator card: "+ s);
				}
                if (!(card instanceof CancelCard)) { cards.add(card); }
            }
            catch( ClassNotFoundException e )
            {
               Util.log("Couldn't find class " + s);
            }
        }
		return cards; 
	}
}
