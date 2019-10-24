package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.cards.dragons.*;

public class DinosaurPool 
{
	public static ArrayList<AbstractCard> deck()
	{
		ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
		cards.add(new Anthrosaurus());
		cards.add(new Babycerasaurus());
		cards.add(new Beatraptor());
		cards.add(new BlackBrachios());
		cards.add(new BlackPtera());
		cards.add(new BlackStego());
		cards.add(new BlackTyranno());
		cards.add(new BlackVeloci());
		cards.add(new Carboneddon());
		cards.add(new CyberDinosaur());
		cards.add(new DarkDriceratops());
		cards.add(new Destroyersaurus());
		cards.add(new Dracocension());
		cards.add(new Duoterion());
		cards.add(new Freezadon());
		cards.add(new Frostosaurus());
		cards.add(new GalaxyTyranno());
		cards.add(new GiantRex());
		cards.add(new HazyFlameHydra());
		cards.add(new Hydrogeddon());
		cards.add(new Kabazauls());
		cards.add(new Lancephorhynchus());
		cards.add(new MadFlameKaiju());
		cards.add(new MadSwordBeast());
		cards.add(new Pyrorex());
		cards.add(new Sabersaurus());
		cards.add(new SauropodBrachion());
		cards.add(new SuperancientDinobeast());
		cards.add(new LivingFossil());
		cards.add(new LostWorld());
		cards.add(new UltraEvolutionPill());
		cards.add(new JurassicImpact());
		return cards;
	}
}
