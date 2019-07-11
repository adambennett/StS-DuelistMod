package duelistmod.helpers;

import java.util.*;
import java.util.Map.Entry;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.*;
import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.interfaces.*;
import duelistmod.variables.Tags;

public class Debug 
{
	public static void printTypedRarityInfo()
	{
		ArrayList<ArrayList<DuelistCard>> returned = setupRarityInfo();
		int listCounter = 0;
		
		System.out.println("Common Monsters\n----------------------");
		for (CardTags t : DuelistMod.monsterTypes)
		{
			ArrayList<DuelistCard> monstersOfType = new ArrayList<DuelistCard>();
			for (DuelistCard c : returned.get(listCounter))
			{
				if (c.hasTag(t)) { monstersOfType.add((DuelistCard) c.makeCopy()); }
			}
			
			int counter = 1;
			System.out.println("----------------------\n" + DuelistMod.typeCardMap_NAME.get(t) + "\n----------------------\n");
			for (DuelistCard c : monstersOfType)
			{
				System.out.println(counter + ": " + c.originalName);
				counter++;
			}
			System.out.println(counter - 1 + " total common " + DuelistMod.typeCardMap_NAME.get(t) + "s.");
		}
		System.out.println("End Common Monsters\n----------------------");
		listCounter+=3;
		
		System.out.println("Uncommon Monsters\n----------------------");
		for (CardTags t : DuelistMod.monsterTypes)
		{
			ArrayList<DuelistCard> monstersOfType = new ArrayList<DuelistCard>();
			for (DuelistCard c : returned.get(listCounter))
			{
				if (c.hasTag(t)) { monstersOfType.add((DuelistCard) c.makeCopy()); }
			}
			
			int counter = 1;
			System.out.println("----------------------\n" + DuelistMod.typeCardMap_NAME.get(t) + "\n----------------------\n");
			for (DuelistCard c : monstersOfType)
			{
				System.out.println(counter + ": " + c.originalName);
				counter++;
			}
			System.out.println(counter - 1 + " total uncommon " + DuelistMod.typeCardMap_NAME.get(t) + "s.");
		}
		System.out.println("End Uncommon Monsters\n----------------------");
		listCounter+=3;
		
		System.out.println("Rare Monsters\n----------------------");
		for (CardTags t : DuelistMod.monsterTypes)
		{
			ArrayList<DuelistCard> monstersOfType = new ArrayList<DuelistCard>();
			for (DuelistCard c : returned.get(listCounter))
			{
				if (c.hasTag(t)) { monstersOfType.add((DuelistCard) c.makeCopy()); }
			}
			
			int counter = 1;
			System.out.println("----------------------\n" + DuelistMod.typeCardMap_NAME.get(t) + "\n----------------------\n");
			for (DuelistCard c : monstersOfType)
			{
				System.out.println(counter + ": " + c.originalName);
				counter++;
			}
			System.out.println(counter - 1 + " total rare " + DuelistMod.typeCardMap_NAME.get(t) + "s.");
		}
		System.out.println("End Rare Monsters\n----------------------");
	}
	
	public static void printRarityInfo()
	{
		ArrayList<ArrayList<DuelistCard>> returned = setupRarityInfo();
		int listCounter = 0;
		System.out.println("Common Monsters\n----------------------");
		int counter = 1;
		for (DuelistCard c : returned.get(listCounter))
		{
			System.out.println(counter + ": " + c.originalName);
			counter++;
		}
		System.out.println("----------------------");
		System.out.println(counter - 1 + " total common monsters.");
		listCounter++;
		
		System.out.println("Common Spells\n----------------------");
		counter = 1;
		for (DuelistCard c : returned.get(listCounter))
		{
			System.out.println(counter + ": " + c.originalName);
			counter++;
		}
		System.out.println("----------------------");
		System.out.println(counter - 1 + " total common spells.");
		listCounter++;
		
		System.out.println("Common Traps\n----------------------");
		counter = 1;
		for (DuelistCard c : returned.get(listCounter))
		{
			System.out.println(counter + ": " + c.originalName);
			counter++;
		}
		System.out.println("----------------------");
		System.out.println(counter - 1 + " total common traps.");
		listCounter++;
		
		System.out.println("Uncommon Monsters\n----------------------");
		counter = 1;
		for (DuelistCard c : returned.get(listCounter))
		{
			System.out.println(counter + ": " + c.originalName);
			counter++;
		}
		System.out.println("----------------------");
		System.out.println(counter - 1 + " total uncommon monsters.");
		listCounter++;
		
		System.out.println("Uncommon Spells\n----------------------");
		counter = 1;
		for (DuelistCard c : returned.get(listCounter))
		{
			System.out.println(counter + ": " + c.originalName);
			counter++;
		}
		System.out.println("----------------------");
		System.out.println(counter - 1 + " total uncommon spells.");
		listCounter++;
		
		System.out.println("Uncommon Traps\n----------------------");
		counter = 1;
		for (DuelistCard c : returned.get(listCounter))
		{
			System.out.println(counter + ": " + c.originalName);
			counter++;
		}
		System.out.println("----------------------");
		System.out.println(counter - 1 + " total uncommon traps.");
		listCounter++;
		
		System.out.println("Rare Monsters\n----------------------");
		counter = 1;
		for (DuelistCard c : returned.get(listCounter))
		{
			System.out.println(counter + ": " + c.originalName);
			counter++;
		}
		System.out.println("----------------------");
		System.out.println(counter - 1 + " total rare monsters.");
		listCounter++;
		
		System.out.println("Rare Spells\n----------------------");
		counter = 1;
		for (DuelistCard c : returned.get(listCounter))
		{
			System.out.println(counter + ": " + c.originalName);
			counter++;
		}
		System.out.println("----------------------");
		System.out.println(counter - 1 + " total rare spells.");
		listCounter++;
		
		System.out.println("Rare Traps\n----------------------");
		counter = 1;
		for (DuelistCard c : returned.get(listCounter))
		{
			System.out.println(counter + ": " + c.originalName);
			counter++;
		}
		System.out.println("----------------------");
		System.out.println(counter - 1 + " total rare traps.");
		listCounter++;
		
		System.out.println("Common Basic Cards\n----------------------");
		counter = 1;
		for (AbstractCard c : DuelistMod.basicCards)
		{
			if (c.rarity.equals(CardRarity.COMMON))
			{
				System.out.println(counter + ": " + c.originalName);
				counter++;
			}
		}
		System.out.println("----------------------");
		System.out.println(counter - 1 + " total common basic cards.");
		listCounter++;
		
		System.out.println("Uncommon Basic Cards\n----------------------");
		counter = 1;
		for (AbstractCard c : DuelistMod.basicCards)
		{
			if (c.rarity.equals(CardRarity.UNCOMMON))
			{
				System.out.println(counter + ": " + c.originalName);
				counter++;
			}
		}
		System.out.println("----------------------");
		System.out.println(counter - 1 + " total uncommon basic cards.");
		listCounter++;
		
		System.out.println("Rare Basic Cards\n----------------------");
		counter = 1;
		for (AbstractCard c : DuelistMod.basicCards)
		{
			if (c.rarity.equals(CardRarity.RARE))
			{
				System.out.println(counter + ": " + c.originalName);
				counter++;
			}
		}
		System.out.println("----------------------");
		System.out.println(counter - 1 + " total rare basic cards.");
		listCounter++;
		
		System.out.println("Common Ojama Cards\n----------------------");
		counter = 1;
		for (DuelistCard c : DuelistMod.myCards)
		{
			if (c.hasTag(Tags.OJAMA) && c.rarity.equals(CardRarity.COMMON))
			{
				System.out.println(counter + ": " + c.originalName);
				counter++;
			}
		}
		System.out.println("----------------------");
		System.out.println(counter - 1 + " total common Ojama cards.");
		listCounter++;
		
		System.out.println("Uncommon Ojama Cards\n----------------------");
		counter = 1;
		for (DuelistCard c : DuelistMod.myCards)
		{
			if (c.rarity.equals(CardRarity.UNCOMMON) && c.hasTag(Tags.OJAMA))
			{
				System.out.println(counter + ": " + c.originalName);
				counter++;
			}
		}
		System.out.println("----------------------");
		System.out.println(counter - 1 + " total uncommon Ojama cards.");
		listCounter++;
		
		System.out.println("Rare Ojama Cards\n----------------------");
		counter = 1;
		for (DuelistCard c : DuelistMod.myCards)
		{
			if (c.rarity.equals(CardRarity.RARE) && c.hasTag(Tags.OJAMA))
			{
				System.out.println(counter + ": " + c.originalName);
				counter++;
			}
		}
		System.out.println("----------------------");
		System.out.println(counter - 1 + " total rare Ojama cards.");
		listCounter++;
	}
	
	private static ArrayList<ArrayList<DuelistCard>> setupRarityInfo()
	{
		ArrayList<ArrayList<DuelistCard>> holder = new ArrayList<ArrayList<DuelistCard>>();
		// 0  - common monsters
		// 1  - common spells
		// 2  - common traps
		// 3  - uncommon monsters
		// 4  - uncommon spells
		// 5  - uncommon traps
		// 6  - rare monsters
		// 7  - rare spells
		// 8  - rare traps
		// 9  - common other
		// 10 - uncommon other
		// 11 - rare other
		
		for (int i = 0; i < 12; i++) { holder.add(new ArrayList<DuelistCard>()); }
		
		int counter = 0;
		for (DuelistCard c : DuelistMod.myCards)
		{
			if (c.rarity.equals(CardRarity.COMMON))
			{
				if (c.hasTag(Tags.MONSTER))
				{
					holder.get(0).add((DuelistCard) c.makeCopy()); 
				}
				else if (c.hasTag(Tags.SPELL))
				{					
					holder.get(1).add((DuelistCard) c.makeCopy()); 
				}
				
				else if (c.hasTag(Tags.TRAP))
				{					
					holder.get(2).add((DuelistCard) c.makeCopy()); 
				}
			}
			else if (c.rarity.equals(CardRarity.UNCOMMON))
			{
				if (c.hasTag(Tags.MONSTER))
				{					
					holder.get(3).add((DuelistCard) c.makeCopy()); 
				}
				else if (c.hasTag(Tags.SPELL))
				{					
					holder.get(4).add((DuelistCard) c.makeCopy()); 
				}
				
				else if (c.hasTag(Tags.TRAP))
				{					
					holder.get(5).add((DuelistCard) c.makeCopy()); 
				}
			}
			else if (c.rarity.equals(CardRarity.RARE))
			{
				if (c.hasTag(Tags.MONSTER))
				{					
					holder.get(6).add((DuelistCard) c.makeCopy()); 
				}
				else if (c.hasTag(Tags.SPELL))
				{					
					holder.get(7).add((DuelistCard) c.makeCopy()); 
				}
				
				else if (c.hasTag(Tags.TRAP))
				{					
					holder.get(8).add((DuelistCard) c.makeCopy()); 
				}
			}
			else if (c.rarity.equals(CardRarity.SPECIAL) || c.rarity.equals(CardRarity.BASIC))
			{
				if (c.hasTag(Tags.MONSTER))
				{					
					holder.get(9).add((DuelistCard) c.makeCopy()); 
				}
				
				else if (c.hasTag(Tags.SPELL))
				{					
					holder.get(10).add((DuelistCard) c.makeCopy()); 
				}
				
				else if (c.hasTag(Tags.TRAP))
				{					
					holder.get(11).add((DuelistCard) c.makeCopy()); 
				}
			}
		}
		
		return holder;
	}
	
	
	public static void printTributeInfo()
	{
		Map<DuelistCard,Integer> cards = new HashMap<DuelistCard,Integer>();
    	Map<Integer,Integer> noTribs = new HashMap<Integer,Integer>();
    	ArrayList<ArrayList<DuelistCard>> tribLists = new ArrayList<ArrayList<DuelistCard>>();
    	for (DuelistCard c : DuelistMod.myCards)
    	{
    		if (c.baseTributes > 0 && c.hasTag(Tags.MONSTER))
    		{
    			cards.put(c, c.baseTributes);
    		}
    	}
    	
    	for (int i = 0; i <= 20; i++)
    	{
    		noTribs.put(i, 0);
    	}
    	
    	for (int i = 0; i <= 20; i++)
    	{
    		tribLists.add(new ArrayList<DuelistCard>());
	    	for (Entry<DuelistCard, Integer> a : cards.entrySet())
	    	{
	    		if (a.getValue() == i) { noTribs.put(i, noTribs.get(i) + 1); tribLists.get(i).add(a.getKey()); }
	    	}
    	}
    	
    	int counter = 0;
    	for (Entry<Integer,Integer> a : noTribs.entrySet())
    	{
    		if (tribLists.get(counter).size() > 0)
    		{
    			String tribsString = "";
    			for (DuelistCard c : tribLists.get(counter))
    			{
    				tribsString += c.originalName + ", ";
    			}
    			System.out.println("Tributes: " + a.getKey() + " (#" + a.getValue() + ") :: List: " + tribsString);
    		}
    		else { System.out.println("Tributes: " + a.getKey() + " (#" + a.getValue() + ")"); }
    		counter++;
    	}
	}
	
	public static void printNonBasicSetCards(ArrayList<DuelistCard> checkCards)
	{
		ArrayList<AbstractCard> archetypeCards = new ArrayList<AbstractCard>();
		ArrayList<DuelistCard> toPrint = new ArrayList<DuelistCard>();
		for (StarterDeck s : DuelistMod.starterDeckList)
		{
			//if (s.getIndex() > 0 && s.getIndex() < 10)
			//{
				archetypeCards.addAll(s.getPoolCards());
				//DuelistMod.logger.info("added all cards from " + s.getSimpleName());
			//}
		}
		archetypeCards.addAll(DuelistMod.basicCards);
		for (DuelistCard c : checkCards)
		{
			if (!archetypeCards.contains(c) && !c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL))
			{
				//DuelistMod.logger.info("theDuelist:Debug:printNonBasicSetCards() ---> found a non-basic, non-archetype card: " + c.originalName);
				toPrint.add(c);
			}
			else
			{
				//DuelistMod.logger.info("theDuelist:Debug:printNonBasicSetCards() ---> found a card: " + c.originalName);
			}
		}
		
		for (int i = 0; i < toPrint.size(); i++)
		{
			DuelistMod.logger.info("theDuelist:Debug:printNonBasicSetCards() ---> found a non-basic, non-archetype card [" + i + "]: " + toPrint.get(i).originalName);
		}
	}

	public static void printCardSetsForGithubReadme(ArrayList<DuelistCard> cardsToPrint)
	{
		ArrayList<DuelistCard> all = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> full = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> reduced = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> limited = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> mod = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> random = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> core = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> toon = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> exodia = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> dragon = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> spellcaster = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> nature = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> creator = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> toonDeck = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> orb = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> resummon = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> generation = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> ojama = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> healDeck = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> incrementDeck = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> exodiaDeck = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> magnetDeck = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> aquaDeck = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> machineDeck = new ArrayList<DuelistCard>();
		for (DuelistCard c : cardsToPrint)
		{
			if (c.hasTag(Tags.ALL))
			{
				all.add(c);
			}
			else if (c.hasTag(Tags.FULL))
			{
				full.add(c);
			}
			else if (c.hasTag(Tags.REDUCED))
			{
				reduced.add(c);
			}

			else if (c.hasTag(Tags.LIMITED))
			{
				limited.add(c);
			}

			else if (c.hasTag(Tags.CONSPIRE) || c.hasTag(Tags.REPLAYSPIRE))
			{
				mod.add(c);
			}

			else if (c.hasTag(Tags.RANDOMONLY))
			{
				random.add(c);
			}

			else
			{
				core.add(c);
			}

			if (c.hasTag(Tags.EXODIA))
			{
				exodia.add(c);
			}

			if (c.hasTag(Tags.TOON))
			{
				toon.add(c);
			}

			if (c.hasTag(Tags.DRAGON_DECK))
			{
				dragon.add(c);
			}

			if (c.hasTag(Tags.SPELLCASTER_DECK))
			{
				spellcaster.add(c);
			}

			if (c.hasTag(Tags.NATURE_DECK))
			{
				nature.add(c);
			}

			if (c.hasTag(Tags.CREATOR_DECK))
			{
				creator.add(c);
			}

			if (c.hasTag(Tags.TOON_DECK))
			{
				toonDeck.add(c);
			}

			if (c.hasTag(Tags.ORB_DECK))
			{
				orb.add(c);
			}

			if (c.hasTag(Tags.RESUMMON_DECK))
			{
				resummon.add(c);
			}

			if (c.hasTag(Tags.GENERATION_DECK))
			{
				generation.add(c);
			}

			if (c.hasTag(Tags.OJAMA_DECK))
			{
				ojama.add(c);
			}

			if (c.hasTag(Tags.HEAL_DECK))
			{
				healDeck.add(c);
			}

			if (c.hasTag(Tags.HEAL_DECK))
			{
				healDeck.add(c);
			}

			if (c.hasTag(Tags.INCREMENT_DECK))
			{
				incrementDeck.add(c);
			}

			if (c.hasTag(Tags.EXODIA_DECK))
			{
				exodiaDeck.add(c);
			}

			if (c.hasTag(Tags.MAGNET_DECK))
			{
				magnetDeck.add(c);
			}

			if (c.hasTag(Tags.MACHINE_DECK))
			{
				machineDeck.add(c);
			}
		}

		for (DuelistCard c : core)
		{
			DuelistMod.logger.info(c.originalName + " - " + "[i]Core[/i]");
		}

		for (DuelistCard c : limited)
		{
			DuelistMod.logger.info(c.originalName + " - " + "[i]Limited[/i]");
		}

		for (DuelistCard c : reduced)
		{
			DuelistMod.logger.info(c.originalName + " - " + "[i]Reduced[/i]");
		}

		for (DuelistCard c : full)
		{
			DuelistMod.logger.info(c.originalName + " - " + "[i]Full[/i]");
		}

		for (DuelistCard c : all)
		{
			DuelistMod.logger.info(c.originalName + " - " + "[i]All[/i]");
		}

		for (DuelistCard c : mod)
		{
			DuelistMod.logger.info(c.originalName + " - " + "[i]Crossover[/i]");
		}

		for (DuelistCard c : random)
		{
			DuelistMod.logger.info(c.originalName + " - " + "[i]Random generation only[/i]");
		}

		for (DuelistCard c : toon)
		{
			DuelistMod.logger.info(c.originalName + " - " + "[i]Toons[/i]");
		}

		for (DuelistCard c : exodia)
		{
			DuelistMod.logger.info(c.originalName + " - " + "[i]Exodia[/i]");
		}

		for (DuelistCard c : dragon)
		{
			DuelistMod.logger.info(c.originalName + " - " + "[i]Dragon Deck[/i]");
		}

		for (DuelistCard c : spellcaster)
		{
			DuelistMod.logger.info(c.originalName + " - " + "[i]Spellcaster Deck[/i]");
		}

		for (DuelistCard c : nature)
		{
			DuelistMod.logger.info(c.originalName + " - " + "[i]Nature Deck[/i]");
		}

		for (DuelistCard c : creator)
		{
			DuelistMod.logger.info(c.originalName + " - " + "[i]Creator Deck[/i]");
		}

		for (DuelistCard c : toonDeck)
		{
			DuelistMod.logger.info(c.originalName + " - " + "[i]Toon Deck[/i]");
		}

		for (DuelistCard c : orb)
		{
			DuelistMod.logger.info(c.originalName + " - " + "[i]Orb Deck[/i]");
		}

		for (DuelistCard c : resummon)
		{
			DuelistMod.logger.info(c.originalName + " - " + "[i]Resummon Deck[/i]");
		}

		for (DuelistCard c : generation)
		{
			DuelistMod.logger.info(c.originalName + " - " + "[i]Generation Deck[/i]");
		}

		for (DuelistCard c : ojama)
		{
			DuelistMod.logger.info(c.originalName + " - " + "[i]Ojama Deck[/i]");
		}

		for (DuelistCard c : healDeck)
		{
			DuelistMod.logger.info(c.originalName + " - " + "[i]Heal Deck[/i]");
		}		

		for (DuelistCard c : incrementDeck)
		{
			DuelistMod.logger.info(c.originalName + " - " + "[i]Increment Deck[/i]");
		}

		for (DuelistCard c : exodiaDeck)
		{
			DuelistMod.logger.info(c.originalName + " - " + "[i]Exodia Deck[/i]");
		}

		for (DuelistCard c : magnetDeck)
		{
			DuelistMod.logger.info(c.originalName + " - " + "[i]Magnet Deck[/i]");
		}

		for (DuelistCard c : aquaDeck)
		{
			DuelistMod.logger.info(c.originalName + " - " + "[i]Aqua Deck[/i]");
		}

		for (DuelistCard c : machineDeck)
		{
			DuelistMod.logger.info(c.originalName + " - " + "[i]Machine Deck[/i]");
		}
	}

	@SuppressWarnings("unchecked")
	public static void outputSQLListsForMetrics() {
		ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
		cards.addAll(DuelistMod.myCards);

		System.out.println("Cards in cardlist: " + DuelistMod.myCards.size());

		String cardstring = "INSERT INTO `meta_card_data` (`id`, `name`, `character_class`, `neutral`, `invalid`, `rarity`, `type`, `cost`, `description`, `ignore_before`, `updated_on`, `score`, `a0_total`, `a114_total`, `a15_total`, `pick_updated_on`, `a0_pick`, `a114_pick`, `a15_pick`, `a0_not_pick`, `a114_not_pick`, `a15_not_pick`, `up_updated_on`, `a0_up`, `a114_up`, `a15_up`, `a0_purchased`, `a114_purchased`, `a15_purchased`, `a0_purged`, `a114_purged`, `a15_purged`, `wr_updated_on`, `a0_wr`, `a114_wr`, `a15_wr`, `a0_floor`, `a114_floor`, `a15_floor`, `a0_floordetails`, `a114_floordetails`, `a15_floordetails`) VALUES ";
		cardstring = cardstring + "(0,'',1,0,0,'','','','',NULL,'0000-00-00 00:00:00',0,0,0,0,'0000-00-00 00:00:00',0,0,0,0,0,0,'0000-00-00 00:00:00',0,0,0,0,0,0,0,0,0,'0000-00-00 00:00:00',0,0,0,0,0,0,'','',''),";

		System.out.println(cardstring);

		int i = 0;
		for (AbstractCard c : cards) {
			i++;
			cardstring = String.format("(%d,'%s',1,0,0,'%s','%s','%d','%s',NULL,'0000-00-00 00:00:00',0,0,0,0,'0000-00-00 00:00:00',0,0,0,0,0,0,'0000-00-00 00:00:00',0,0,0,0,0,0,0,0,0,'0000-00-00 00:00:00',0,0,0,0,0,0,'','',''),", i, c.cardID, Utilities.titleCase(c.rarity.name()), Utilities.titleCase(c.type.name()), c.cost, c.rawDescription.replace("'","\'"));
			System.out.println(cardstring);
		}

		//cardstring = cardstring.substring(0, cardstring.length() - 1) + ";/*!40000 ALTER TABLE `meta_card_data` ENABLE KEYS */;";
		System.out.println(";/*!40000 ALTER TABLE `meta_card_data` ENABLE KEYS */;");

		//System.out.println(cardstring);

		System.out.println(" ");
		System.out.println(" ");
		System.out.println(" ");


		ArrayList<AbstractRelic> relics = new ArrayList<>();

		HashMap<String,AbstractRelic> sharedRelics = (HashMap<String,AbstractRelic>)ReflectionHacks.getPrivateStatic(RelicLibrary.class, "sharedRelics");
		for (AbstractRelic relic : sharedRelics.values()) {
			relics.add(relic);
		}

		for (Entry<CardColor, HashMap<String, AbstractRelic>> a : BaseMod.getAllCustomRelics().entrySet())
		{
			for (AbstractRelic r : a.getValue().values())
			{
				relics.add(r);
				if (r != null)
				{
					System.out.println("theDuelist:outputSQLListsForMetrics() ---> added " + r.name + " to relics"); 
				}
				else
				{
					System.out.println("theDuelist:outputSQLListsForMetrics() ---> relic not added because it was null!");
				}
			}

			System.out.println("got here at least");
		}


		for (HashMap.Entry<AbstractCard.CardColor,HashMap<String,AbstractRelic>> entry : BaseMod.getAllCustomRelics().entrySet()) {
			for (AbstractRelic relic : entry.getValue().values()) {
				relics.add(relic);
				if (relic != null) { System.out.println("theDuelist:outputSQLListsForMetrics() ---> added " + relic.name + " to relics"); }
				else 
				{
					System.out.println("theDuelist:outputSQLListsForMetrics() ---> relic not added because it was null!");
				}
			}
		}

		String relicstring = "INSERT INTO `meta_relic_data` (`id`, `name`, `invalid`, `character_class`, `description`, `rarity`, `event_id`, `ignore_before`) VALUES ";


		i = 0;
		for (AbstractRelic relic: relics) {
			i++;
			relicstring = relicstring + String.format("(%d,'%s',0,1,'%s','%s',0,'0000-00-00'),", i, relic.name, relic.description, relic.tier.name().toLowerCase());
		}
		System.out.println(relicstring);


	}

	// DEBUG PRINT COMMANDS /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void printTextForTranslation()
	{
		DuelistMod.logger.info("theDuelist:DuelistMod:printTextForTranslation() ---> START");
		DuelistMod.logger.info("theDuelist:DuelistMod:printTextForTranslation() ---> Card Names");
		for (DuelistCard c : DuelistMod.myCards)
		{
			System.out.println(c.originalName);
			//logger.info(c.originalName);
		}

		DuelistMod.logger.info("theDuelist:DuelistMod:printTextForTranslation() ---> Card IDs");
		for (DuelistCard c : DuelistMod.myCards)
		{
			System.out.println(";()" + c.getID() + ",;()");
			//logger.info(c.originalName);
		}

		DuelistMod.logger.info("theDuelist:DuelistMod:printTextForTranslation() ---> Card Descriptions");
		for (DuelistCard c : DuelistMod.myCards)
		{
			System.out.println(c.rawDescription + " - " + DuelistCard.UPGRADE_DESCRIPTION);
			//logger.info(c.rawDescription);
		}

		/*
		logger.info("theDuelist:DuelistMod:printTextForTranslation() ---> Powers");
		String[] powerList = new String[] {"SummonPower", "DespairPower","JamPower", "ToonWorldPower",
				"ObeliskPower", "AlphaMagPower", "BetaMagPower", "GammaMagPower", "GreedShardPower",
				"MirrorPower", "ToonBriefcasePower", "DragonCapturePower", "PotGenerosityPower", "CannonPower",
				"CatapultPower", "BadReactionPower", "CastlePower", "EmperorPower", "MagicCylinderPower",
				"MirrorForcePower", "ImperialPower", "SliferSkyPower", "ExodiaPower", "DarkMirrorPower",
				"ParasitePower", "StormingMirrorPower", "RadiantMirrorPower", "SwordsBurnPower", "SwordsConcealPower",
				"SwordsRevealPower", "SummonSicknessPower", "TributeSicknessPower", "EvokeSicknessPower", "OrbHealPower",
				"OrbEvokerPower", "EnergyTreasurePower", "HealGoldPower", "TributeToonPower", "TributeToonPowerB", "GravityAxePower",
				"ToonRollbackPower", "ToonKingdomPower", "ReducerPower", "CrystallizerPower", "MountainPower", "VioletCrystalPower",
				"YamiPower", "TimeWizardPower", "TrapHolePower", "SwordDeepPower", "CocoonPower", "SarraceniantPower", "JinzoPower",
				"UltimateOfferingPower"};
		for (String s : powerList)
		{
			PowerStrings powerString = CardCrawlGame.languagePack.getPowerStrings(DuelistMod.makeID(s));
			String[] powerDesc = powerString.DESCRIPTIONS;
			for (int i = 0; i < powerDesc.length; i++)
			{
				if (i == 0) { System.out.print(s + " - " + powerDesc[i]);}
				else { System.out.print(powerDesc[i]); }
			}
			logger.info("");
		}

		logger.info("theDuelist:DuelistMod:printTextForTranslation() ---> Relics");
		String[] relicList = new String[] {"MillenniumPuzzle", "MillenniumEye", "MillenniumRing", "MillenniumKey",
				"MillenniumRod", "MillenniumCoin", "StoneExxod", "GiftAnubis"};
		for (String s : relicList)
		{
			RelicStrings relicString = CardCrawlGame.languagePack.getRelicStrings(DuelistMod.makeID(s));
			String[] relicDesc = relicString.DESCRIPTIONS;
			String flavor = relicString.FLAVOR;
			for (int i = 0; i < relicDesc.length; i++)
			{
				if (i == 0) { System.out.print(s + " - " + relicDesc[i]);}
				else { System.out.print(relicDesc[i]); }
			}
			logger.info(" -- " + flavor);
		}
		 */


		DuelistMod.logger.info("theDuelist:DuelistMod:printTextForTranslation() ---> END");
	}

}
