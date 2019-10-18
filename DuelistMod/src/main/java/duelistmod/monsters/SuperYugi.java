package duelistmod.monsters;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.*;
import duelistmod.cards.dragons.*;
import duelistmod.cards.incomplete.*;
import duelistmod.powers.enemyPowers.EnemyExodiaPower;

public class SuperYugi extends DuelistMonster
{
	
	public static final String ID = DuelistMod.makeID("SuperYugi"); 	
	private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID);

	public static final String NAME = monsterstrings.NAME; 
	public static final String[] MOVES = monsterstrings.MOVES;
	public static final String[] DIALOG = monsterstrings.DIALOG; 
	
	private static final float HB_X = 0.0F;     
	private static final float HB_Y = -25.0F; 
	private static final float HB_W = 200.0F;   
	private static final float HB_H = 330.0F;   
	
	private static final int HP_MIN = 300; 
	private static final int HP_MAX = 380; 
	private static final int A7_HP_MIN = 350; 
	private static final int A7_HP_MAX = 430;
	
	private DuelistCard sparks;
	private DuelistCard lesser;	
	private DuelistCard hinotama;	
	private DuelistCard bew;	
	private DuelistCard beserk;
	private DuelistCard spiral;
	private DuelistCard redmed;
	private DuelistCard redeyes;	
	private DuelistCard totem;
	private DuelistCard miracle;
	private DuelistCard arma;
	private DuelistCard yamata;
	private DuelistCard mirage;
	private DuelistCard dragonwing;
	private DuelistCard darkMagician;
	private DuelistCard neoMagic;
	private DuelistCard sSkull;
	
	private static String getModel()
	{
		if (DuelistMod.oldCharacter) { return "OldYugiEnemy2"; }
		else { return "YugiEnemy2"; }
	}

	public SuperYugi() 
	{
		super(NAME, ID, 50, HB_X, HB_Y, HB_W, HB_H, getModel(), HP_MIN, HP_MAX, A7_HP_MIN, A7_HP_MAX);
		this.setHands(initHands());
		ArrayList<String> dialog = new ArrayList<String>();
		for (String s : DIALOG) { dialog.add(s); }
		this.setupDialog(dialog);
		this.type = EnemyType.ELITE;
		this.cardDialogMin = 3;
		this.cardDialogMax = 8;
		this.startingToken = "Spellcaster Token";
		this.overflowDialog = this.dialog.get(2);
		this.lowWeightHandIndex = 7;
		this.sparks = new Sparks();		
		this.lesser = new LesserDragon();	
		this.hinotama = new Hinotama();		
		this.bew = new BlueEyes();		
		this.beserk = new BerserkerCrush();	
		this.spiral = new SpiralSpearStrike();
		this.redmed = new RedMedicine();
		this.redeyes = new RedEyes();		
		this.totem = new TotemDragon();
		this.miracle = new MiraculousDescentEnemy();
		this.arma = new ArmageddonDragonEmp();
		this.yamata = new YamataDragon();
		this.mirage = new MirageDragon();
		this.dragonwing = new ExploderDragonwing();
		this.darkMagician = new DarkMagician();
		this.neoMagic = new NeoMagic();
		this.sSkull = new SummonedSkull();
		this.miracle.upgrade();
		this.totem.upgrade();
		this.redmed.upgrade();
		//this.deathSound = "coolKaibaSound";
	}
	
	@Override
	protected ArrayList<AbstractCard> getHandMove()
	{
		return yugiMoves();
	}
	
	private ArrayList<ArrayList<String>> initHands()
	{
		return getYugiHands();
	}

	@Override
	public void onUseDestructPotion() 
	{
		this.possibleHands.get(3).clear();
		this.possibleHands.get(3).add("Forbidden Lance");
		this.possibleHands.get(3).add("Neo the Magic Swordsman");
		this.possibleHands.get(3).add("Power Wall");
		this.possibleHands.get(3).add("#yRight #yArm");
		this.possibleHands.get(3).add("Golden Apples");		
	}
	
	private ArrayList<AbstractCard> yugiMoves()
	{
		ArrayList<AbstractCard> moveCards = new ArrayList<AbstractCard>();
		int summons = getSummons();
		//boolean hasLambs = hasLambs();
		int moveRoll = AbstractDungeon.aiRng.random(1, 5);
		ArrayList<DuelistCard> currentPieces = new ArrayList<DuelistCard>();
		if (this.hasPower(EnemyExodiaPower.POWER_ID)) { EnemyExodiaPower pow = (EnemyExodiaPower) this.getPower(EnemyExodiaPower.POWER_ID); currentPieces.addAll(pow.pieces); }
		boolean hasLeftLeg = false;
		boolean hasRightLeg = false;
		boolean hasLeftArm = false;
		boolean hasRightArm = false;
		for (DuelistCard c : currentPieces)
		{
			if (c instanceof ExodiaLL) { hasLeftLeg = true; }
			else if (c instanceof ExodiaRL) { hasRightLeg = true; }
			else if (c instanceof ExodiaRA) { hasRightArm = true; }
			else if (c instanceof ExodiaLA) { hasLeftArm = true; }
		}
		switch (this.handIndex)
		{
		case 0:
			if (!hasLeftLeg)
			{
				moveCards.add(new ExodiaLL());
				moveCards.add(new NeoMagic());
				moveCards.add(new PreventRat());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.neoMagic.damage);
			}
			else if (moveRoll == 1 && summons > 0)
			{
				moveCards.add(new NeoMagic());
				moveCards.add(new DarkMagician());
				overflowCards.add(new PreventRat());
				this.setMove((byte)2, Intent.ATTACK, this.darkMagician.damage + this.neoMagic.damage);
			}
			else if (moveRoll == 1)
			{
				moveCards.add(new NeoMagic());
				moveCards.add(new MysticalElf());
				moveCards.add(new PreventRat());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.neoMagic.damage);
			}
			else if (moveRoll == 2)
			{
				moveCards.add(new ExodiaLL());
				moveCards.add(new NeoMagic());
				moveCards.add(new MysticalElf());
				overflowCards.add(new PreventRat());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.neoMagic.damage);
			}
			else if (moveRoll == 3)
			{
				moveCards.add(new ExodiaLL());
				moveCards.add(new NeoMagic());
				moveCards.add(new PreventRat());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.neoMagic.damage);
			}
			else if (moveRoll == 4)
			{
				moveCards.add(new MysticalElf());
				moveCards.add(new DarkMagician());
				overflowCards.add(new PreventRat());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.darkMagician.damage);
			}
			else if (moveRoll == 5)
			{
				moveCards.add(new ExodiaLL());
				moveCards.add(new DarkMagician());
				overflowCards.add(new PreventRat());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.darkMagician.damage);
			}
			break;
		case 1:
			if (!hasRightLeg)
			{
				moveCards.add(new ExodiaRL());
				moveCards.add(new PreventRat());
				moveCards.add(new Sparks());
				moveCards.add(new MillenniumShield());
				this.setMove((byte)2, Intent.ATTACK_BUFF, this.sparks.damage);
			}
			else if (summons > 0)
			{
				moveCards.add(new ExodiaRL());
				moveCards.add(new PreventRat());
				moveCards.add(new Sparks());
				moveCards.add(new MillenniumShield());
				this.setMove((byte)2, Intent.ATTACK_BUFF, this.sparks.damage);
			}
			else if (moveRoll == 1)
			{
				moveCards.add(new ExodiaRL());
				moveCards.add(new PreventRat());
				moveCards.add(new Sparks());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.sparks.damage);
			}
			else if (moveRoll == 2)
			{
				moveCards.add(new Sparks());
				moveCards.add(new ExodiaRL());
				moveCards.add(new SummonedSkull());
				overflowCards.add(new PreventRat());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.sparks.damage + this.sSkull.damage);
			}
			else
			{
				moveCards.add(new Sparks());
				moveCards.add(new PreventRat());
				moveCards.add(new SummonedSkull());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.sparks.damage + this.sSkull.damage);
			}
			break;
		case 2:		
			if (!hasLeftArm)
			{
				moveCards.add(new CelticGuardian());
				moveCards.add(new MysticalElf());
				moveCards.add(new ExodiaLA());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, new ExodiaLA().damage + new CelticGuardian().damage);
			}
			else if (moveRoll == 5)
			{
				moveCards.add(new SwordsRevealing());
				this.setMove((byte)2, Intent.MAGIC);
			}
			else if (summons > 0 && moveRoll > 2)
			{
				moveCards.add(new MysticalElf());
				moveCards.add(new DarkMagician());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, new DarkMagician().damage);
			}
			else if (summons > 0 && moveRoll < 3)
			{
				moveCards.add(new CelticGuardian());
				moveCards.add(new DarkMagician());
				this.setMove((byte)2, Intent.ATTACK, new DarkMagician().damage + new CelticGuardian().damage);
			}
			else
			{
				moveCards.add(new CelticGuardian());
				moveCards.add(new MysticalElf());
				moveCards.add(new ExodiaLA());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, new ExodiaLA().damage + new CelticGuardian().damage);
			}
			break;
		case 3:
			if (!hasRightArm)
			{
				moveCards.add(new ExodiaRA());
				moveCards.add(new NeoMagic());
				moveCards.add(new PowerWall());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, new NeoMagic().damage + new ExodiaRA().damage);
			}
			else if (!this.destructUsed && this.summonsThisCombat > 2 && summons > 0)
			{
				moveCards.add(new ForbiddenLance());
				moveCards.add(new DestructPotion());
				moveCards.add(new GoldenApples());
				this.setMove((byte)2, Intent.ATTACK_BUFF, new ForbiddenLance().damage);
			}
			else
			{
				if (this.destructUsed)
				{
					int roll = AbstractDungeon.aiRng.random(1, 5);
					if (roll == 1)
					{
						moveCards.add(new ForbiddenLance());
						moveCards.add(new GoldenApples());
						this.setMove((byte)2, Intent.ATTACK_DEBUFF, new ForbiddenLance().damage);
					}
					else if (roll == 2)
					{
						moveCards.add(new PowerWall());
						moveCards.add(new NeoMagic());
						moveCards.add(new GoldenApples());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, new NeoMagic().damage);
					}
					else if (roll == 3)
					{
						moveCards.add(new ExodiaRA());
						moveCards.add(new NeoMagic());
						moveCards.add(new PowerWall());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, new NeoMagic().damage + new ExodiaRA().damage);
					}
					else if (roll == 4)
					{
						moveCards.add(new ExodiaRA());
						moveCards.add(new NeoMagic());
						moveCards.add(new GoldenApples());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, new NeoMagic().damage + new ExodiaRA().damage);
					}
					else
					{
						moveCards.add(new ForbiddenLance());
						moveCards.add(new NeoMagic());
						this.setMove((byte)2, Intent.ATTACK_DEBUFF, new ForbiddenLance().damage + new NeoMagic().damage);
					}				
				}
				else
				{
					int roll = AbstractDungeon.aiRng.random(1, 3);
					if (roll == 1)
					{
						moveCards.add(new ForbiddenLance());
						moveCards.add(new GoldenApples());
						this.setMove((byte)2, Intent.ATTACK_DEBUFF, new ForbiddenLance().damage);
					}
					else if (roll == 2)
					{
						moveCards.add(new ExodiaRA());
						moveCards.add(new NeoMagic());
						moveCards.add(new GoldenApples());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, new NeoMagic().damage + new ExodiaRA().damage);
					}
					else
					{
						moveCards.add(new ForbiddenLance());
						moveCards.add(new NeoMagic());
						this.setMove((byte)2, Intent.ATTACK_DEBUFF, new ForbiddenLance().damage + new NeoMagic().damage);
					}				
				}
			}
			break;
		case 4:	
			if (exodiaHeadDmg(new ExodiaHead()) > 0 && summons > 1) 
			{
				moveCards.add(new ExodiaHead());
				moveCards.add(new PowerWall());
				moveCards.add(new LabyrinthWall());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, exodiaHeadDmg(new ExodiaHead()));
			}
			else if (exodiaHeadDmg(new ExodiaHead()) > 0) 
			{
				moveCards.add(new ExodiaHead());
				moveCards.add(new PowerWall());
				moveCards.add(new CelticGuardian());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, exodiaHeadDmg(new ExodiaHead()) + new CelticGuardian().damage);
			}
			else if (summons > 0)
			{
				moveCards.add(new CelticGuardian());
				moveCards.add(new LabyrinthWall());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, new CelticGuardian().damage);
			}
			else 
			{
				moveCards.add(new CelticGuardian());
				moveCards.add(new SummonedSkull());
				moveCards.add(new PowerWall());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, new CelticGuardian().damage + sSkull.damage);
			}
			break;
		default:
			moveCards.add(new GoldenApples());
			moveCards.add(new GoldenApples());
			moveCards.add(new SilverApples());
			this.setMove((byte)2, Intent.MAGIC);
			break;
		}
		return moveCards;
	}

	private ArrayList<ArrayList<String>> getYugiHands()
	{
		ArrayList<ArrayList<String>> newHands = new ArrayList<ArrayList<String>>();
		ArrayList<String> hand0 = new ArrayList<String>();
		ArrayList<String> hand1 = new ArrayList<String>();
		ArrayList<String> hand2 = new ArrayList<String>();
		ArrayList<String> hand3 = new ArrayList<String>();
		ArrayList<String> hand4 = new ArrayList<String>();
		hand0.add("Dark Magician");
		hand0.add("Neo the Magic Swordsman");
		hand0.add("Mystical Elf");
		hand0.add("Prevent Rat");
		hand0.add("#yLeft #yLeg");    	

		hand1.add("Sparks");
		hand1.add("#yRight #yLeg");
		hand1.add("Prevent Rat");
		hand1.add("Summoned Skull"); 
		hand1.add("Millennium Shield");

		hand2.add("Dark Magician"); 
		hand2.add("Celtic Guardian");
		hand2.add("Mystical Elf");
		hand2.add("#yLeft #yArm");
		hand2.add("Swords of Revealing Light");

		hand3.add("Forbidden Lance");
		hand3.add("Neo the Magic Swordsman");
		hand3.add("Destruct Potion");
		hand3.add("#yRight #yArm"); 
		hand3.add("Golden Apples");
		
		hand4.add("Summoned Skull");
		hand4.add("Labyrinth Wall");
		hand4.add("Celtic Guardian");
		hand4.add("#yHead #yof #yExodia"); 
		hand4.add("Power Wall");
		
		newHands.add(hand0);
		newHands.add(hand1);
		newHands.add(hand2);
		newHands.add(hand3);
		newHands.add(hand4);
		return newHands;
	}

}
