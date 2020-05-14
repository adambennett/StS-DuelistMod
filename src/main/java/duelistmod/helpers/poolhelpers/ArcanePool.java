package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.DuelistMod;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.pools.aqua.*;
import duelistmod.cards.pools.dragons.*;
import duelistmod.cards.pools.insects.VenomShot;
import duelistmod.cards.pools.machine.*;
import duelistmod.cards.pools.warrior.*;

public class ArcanePool 
{
	public static ArrayList<AbstractCard> deck()
	{
		ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
		if (AbstractDungeon.ascensionLevel < 10)
		{
			cards.add(new DestructPotion());		
		} 
		
		if (AbstractDungeon.ascensionLevel < 15)
		{
			cards.add(new DianKeto());
			cards.add(new RedMedicine());
		}

		if (!DuelistMod.ojamaBtnBool)
		{
			cards.add(new OjamaBlue());
			cards.add(new OjamaRed());
			cards.add(new OjamaKnight());
		}
		
		if (!DuelistMod.toonBtnBool)
		{
			cards.add(new ToonDefense());
		}

		cards.add(new ApprenticeIllusionMagician());	
		cards.add(new CatShark());	
		cards.add(new ClearKuriboh());	
		cards.add(new Cloning());	
		cards.add(new DarkFusion());			
		cards.add(new FishRain());	
		cards.add(new Hinotama());				
		cards.add(new MagicalStone());	
		cards.add(new SphereKuriboh());	
		cards.add(new Wiretap());				
		cards.add(new AmuletAmbition());	
		cards.add(new BerserkerCrush());	
		cards.add(new CharcoalInpachi());	
		cards.add(new CombinationAttack());			
		cards.add(new FeatherShot());	
		cards.add(new ForbiddenLance());	
		cards.add(new FuryFire());	
		cards.add(new InfernoFireBlast());	
		cards.add(new LightningDarts());
		cards.add(new OneForOne());			
		cards.add(new SilentDoom());	
		cards.add(new Wildfire());	
		cards.add(new DefenseDraw());	
		cards.add(new DefenseZone());	
		cards.add(new SafeZone());	
		cards.add(new FireDarts());	
		cards.add(new SpiralSpearStrike());	
		cards.add(new TyrantWing());	
		cards.add(new VeilDarkness());	
		cards.add(new AncientGearFist());	
		cards.add(new VenomShot());	
		cards.add(new CoreBlaster());	
		cards.add(new DoubleTool());	
		cards.add(new MachineKingPrototype());	
		cards.add(new MeteorDestruction());	
		cards.add(new SparkBlaster());	
		cards.add(new ChaosSeed());	
		cards.add(new LegendaryBlackBelt());	
		cards.add(new LegendarySword());	
		cards.add(new LightningBlade());	
		cards.add(new MagnumShield());	
		return cards;
	}
}
