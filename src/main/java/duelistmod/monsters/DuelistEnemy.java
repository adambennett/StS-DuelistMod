package duelistmod.monsters;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.pools.dragons.*;
import duelistmod.helpers.*;
import duelistmod.powers.enemyPowers.EnemyMiraclePower;

public class DuelistEnemy extends DuelistMonster
{
	
	public static final String ID = DuelistMod.makeID("KaibaA2"); 
	public static final String ID_YUGI = DuelistMod.makeID("YugiEnemy");
	private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID); 
	private static final MonsterStrings monsterstrings_YUGI = CardCrawlGame.languagePack.getMonsterStrings(ID_YUGI); 

	public static final String NAME = monsterstrings.NAME; 
	public static final String NAME_YUGI = monsterstrings_YUGI.NAME; 
	public static final String[] MOVES = monsterstrings.MOVES;
	public static final String[] MOVES_YUGI = monsterstrings_YUGI.MOVES; 
	public static final String[] DIALOG = monsterstrings.DIALOG; 
	public static final String[] DIALOG_YUGI = monsterstrings_YUGI.DIALOG; 
	
	private static final float HB_X = 0.0F;     
	private static final float HB_Y = -25.0F; 
	private static final float HB_W = 200.0F;   
	private static final float HB_H = 330.0F;   
	
	private static final int HP_MIN = 85; 
	private static final int HP_MAX = 115; 
	private static final int A7_HP_MIN = 95; 
	private static final int A7_HP_MAX = 140;
	
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
	
	private boolean kaiba = true;
	
	private static String getName()
	{
		DuelistMod.getEnemyDuelistModel();
		return DuelistMod.monsterIsKaiba ? NAME : NAME_YUGI;
	}
	
	private static String getID()
	{
		DuelistMod.getEnemyDuelistModel();
		return DuelistMod.monsterIsKaiba ? ID : ID_YUGI;
	}

	public DuelistEnemy() 
	{
		super(getName(), getID(), 50, HB_X, HB_Y, HB_W, HB_H, DuelistMod.kaibaEnemyModel, HP_MIN, HP_MAX, A7_HP_MIN, A7_HP_MAX);
		this.kaiba = DuelistMod.monsterIsKaiba;
		this.setHands(initHands());
		ArrayList<String> dialog = new ArrayList<String>();
		if (kaiba) { for (String s : DIALOG) { dialog.add(s); }}
		else { for (String s : DIALOG_YUGI) { dialog.add(s); }}
		this.setupDialog(dialog);
		this.type = EnemyType.ELITE;
		this.cardDialogMin = 3;
		this.cardDialogMax = 8;
		if (kaiba) { this.startingToken = "Dragon Token"; }
		else { this.startingToken = "Spellcaster Token"; }
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
		if (kaiba) { return kaibaMoves(); }
		else { return yugiMoves(); }
	}
	
	private ArrayList<ArrayList<String>> initHands()
	{
		if (kaiba) { return getKaibaHands(); }
		else { return getYugiHands(); }
	}

	@Override
	public void onUseDestructPotion() 
	{
		this.possibleHands.get(3).clear();
		if (kaiba)
		{
			this.possibleHands.get(3).add("Beserker Crush");
			this.possibleHands.get(3).add("Lesser Dragon");
			this.possibleHands.get(3).add("Cave Dragon");
			this.possibleHands.get(3).add("Labyrinth Wall");
			this.possibleHands.get(3).add("Golden Apples");
		}
		else
		{
			this.possibleHands.get(3).add("Forbidden Lance");
			this.possibleHands.get(3).add("Neo the Magic Swordsman");
			this.possibleHands.get(3).add("Red Medicine");
			this.possibleHands.get(3).add("#yRight #yArm");
			this.possibleHands.get(3).add("Golden Apples");
		}
	}
	
	private ArrayList<AbstractCard> yugiMoves()
	{
		ArrayList<AbstractCard> moveCards = new ArrayList<AbstractCard>();
		int summons = getSummons();
		//boolean hasLambs = hasLambs();
		int moveRoll = AbstractDungeon.aiRng.random(1, 5);
		switch (this.handIndex)
		{
		case 0:
			if (moveRoll == 1 && summons > 0)
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
			if (summons > 0)
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
			if (moveRoll == 5)
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
			if (!this.destructUsed && this.summonsThisCombat > 2 && summons > 0)
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
						moveCards.add(new RedMedicine());
						moveCards.add(new NeoMagic());
						moveCards.add(new GoldenApples());
						this.setMove((byte)2, Intent.ATTACK_BUFF, new NeoMagic().damage);
					}
					else if (roll == 3)
					{
						moveCards.add(new ExodiaRA());
						moveCards.add(new NeoMagic());
						moveCards.add(new RedMedicine());
						this.setMove((byte)2, Intent.ATTACK_BUFF, new NeoMagic().damage + new ExodiaRA().damage);
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
	
	private ArrayList<AbstractCard> kaibaMoves()
	{
		ArrayList<AbstractCard> moveCards = new ArrayList<AbstractCard>();
		int summons = getSummons();
		//boolean hasLambs = hasLambs();
		int moveRoll = AbstractDungeon.aiRng.random(1, 5);
		switch (this.handIndex)
		{
		case 0:
			if (this.hasPower(EnemyMiraclePower.POWER_ID)) { moveRoll = AbstractDungeon.aiRng.random(4, 5); }
			switch (moveRoll)
			{
				case 1:
					AbstractCard mir = new MiraculousDescentEnemy();
					mir.upgrade();
					moveCards.add(mir);
					moveCards.add(new LesserDragon());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK_BUFF, this.lesser.damage);
					break;
				case 2:
					AbstractCard mirB = new MiraculousDescentEnemy();
					mirB.upgrade();
					moveCards.add(mirB);
					moveCards.add(new PreventRat());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
					break;
				case 3:
					AbstractCard mirC = new MiraculousDescentEnemy();
					mirC.upgrade();
					moveCards.add(mirC);
					moveCards.add(new PowerWall());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.DEFEND_BUFF);					
					break;
				case 4:
					moveCards.add(new LesserDragon());
					moveCards.add(new PowerWall());
					moveCards.add(new Hinotama());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.lesser.damage + (this.hinotama.damage * this.hinotama.magicNumber));
					break;
				case 5:
					moveCards.add(new LesserDragon());
					moveCards.add(new PreventRat());
					moveCards.add(new PowerWall());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.lesser.damage);
					break;				
			}			
			break;
		case 1:
			if (summons > 2)
			{
				moveCards.add(new MillenniumShield());
				moveCards.add(new PreventRat());
				moveCards.add(new PowerWall());
				this.setMove((byte)2, Intent.DEFEND_BUFF);
			}
			
			else if (summons > 1)
			{
				moveCards.add(new PreventRat());
				moveCards.add(new MillenniumShield());
				moveCards.add(new PowerWall());
				this.setMove((byte)2, Intent.DEFEND_BUFF);
			}
			
			else if (summons > 0)
			{
				moveCards.add(new YamataDragon());
				moveCards.add(new Sparks());
				moveCards.add(new PowerWall());
				overflowCards.add(new PreventRat());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.yamata.damage + this.sparks.damage);
			}
			
			else
			{
				int roll = AbstractDungeon.aiRng.random(1, 2);
				if (roll == 1)
				{
					moveCards.add(new PreventRat());
					moveCards.add(new YamataDragon());
					moveCards.add(new PowerWall());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.yamata.damage);
				}
				else
				{
					moveCards.add(new Sparks());
					moveCards.add(new PreventRat());
					moveCards.add(new PowerWall());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.sparks.damage);
				}
			}
				
			break;
		case 2:						
			if (summons > 1)
			{
				int roll = AbstractDungeon.aiRng.random(1, 4);
				if (roll == 1)
				{
					moveCards.add(new BlueEyes());
					moveCards.add(new CaveDragon());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.bew.damage);
				}
				else if (roll == 2)
				{
					moveCards.add(new BlueEyes());
					moveCards.add(new Kuriboh());
					this.setMove((byte)2, Intent.ATTACK_BUFF, this.bew.damage);
				}
				else if (roll == 3)
				{
					moveCards.add(new BoosterDragon());
					moveCards.add(new CaveDragon());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
				}
				else 
				{
					moveCards.add(new BoosterDragon());
					moveCards.add(new Kuriboh());
					this.setMove((byte)2, Intent.BUFF);
				}
			}
			
			else if (summons > 0)
			{
				int roll = AbstractDungeon.aiRng.random(1, 2);
				if (roll == 1)
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new BlueEyes());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.bew.damage);
				}
				else
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new BoosterDragon());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
				}
			}
			
			else 
			{
				int roll = AbstractDungeon.aiRng.random(1, 2);
				if (roll == 1)
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new PowerWall());
					moveCards.add(new Kuriboh());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
				}
				else
				{
					moveCards.add(new Kuriboh());
					moveCards.add(new CaveDragon());
					moveCards.add(new PowerWall());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
				}
			}
			break;
		case 3:
			if (!this.destructUsed && this.summonsThisCombat > 2 && summons > 0)
			{
				moveCards.add(new LesserDragon());
				moveCards.add(new DestructPotion());
				moveCards.add(new GoldenApples());
				this.setMove((byte)2, Intent.ATTACK_BUFF, this.lesser.damage);
			}
			else
			{
				if (this.destructUsed)
				{
					int roll = AbstractDungeon.aiRng.random(1, 5);
					if (roll == 1)
					{
						moveCards.add(new BerserkerCrush());
						moveCards.add(new GoldenApples());
						this.setMove((byte)2, Intent.ATTACK_DEBUFF, this.beserk.damage);
					}
					else if (roll == 2)
					{
						moveCards.add(new BerserkerCrush());
						moveCards.add(new MirageDragon());
						this.setMove((byte)2, Intent.ATTACK_DEBUFF, this.mirage.damage + this.beserk.damage);
					}
					else if (roll == 3)
					{
						moveCards.add(new LesserDragon());
						moveCards.add(new MirageDragon());
						moveCards.add(new GoldenApples());
						this.setMove((byte)2, Intent.ATTACK_DEBUFF, this.mirage.damage + this.lesser.damage);
					}
					else if (roll == 4)
					{
						moveCards.add(new BerserkerCrush());
						moveCards.add(new LesserDragon());
						this.setMove((byte)2, Intent.ATTACK_DEBUFF, this.lesser.damage + this.beserk.damage);
					}
					else
					{
						moveCards.add(new LesserDragon());
						moveCards.add(new CaveDragon());
						moveCards.add(new GoldenApples());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, this.lesser.damage);
					}				
				}
				else
				{
					int roll = AbstractDungeon.aiRng.random(1, 4);
					if (roll == 1)
					{
						moveCards.add(new BerserkerCrush());
						moveCards.add(new GoldenApples());
						this.setMove((byte)2, Intent.ATTACK_DEBUFF, this.beserk.damage);
					}
					else if (roll == 2)
					{
						moveCards.add(new BerserkerCrush());
						moveCards.add(new MirageDragon());
						this.setMove((byte)2, Intent.ATTACK_DEBUFF, this.mirage.damage + this.beserk.damage);
					}
					else if (roll == 3)
					{
						moveCards.add(new LesserDragon());
						moveCards.add(new MirageDragon());
						moveCards.add(new GoldenApples());
						this.setMove((byte)2, Intent.ATTACK_DEBUFF, this.mirage.damage + this.lesser.damage);
					}
					else
					{
						moveCards.add(new BerserkerCrush());
						moveCards.add(new LesserDragon());
						this.setMove((byte)2, Intent.ATTACK_DEBUFF, this.lesser.damage + this.beserk.damage);
					}
				}
			}
			break;
		case 4:	
			int rand = AbstractDungeon.aiRng.random(1, 5);
			if (rand == 1)
			{
				moveCards.add(new ExploderDragonwing());
				moveCards.add(new CaveDragon());
				overflowCards.add(new BackgroundDragon());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.dragonwing.damage);
			}
			else if (rand == 2)
			{
				moveCards.add(new ExploderDragonwing());
				moveCards.add(new Sparks());
				overflowCards.add(new BackgroundDragon());
				this.setMove((byte)2, Intent.ATTACK, this.dragonwing.damage + this.sparks.damage);
			}
			else if (rand == 3)
			{
				moveCards.add(new BackgroundDragon());
				moveCards.add(new CaveDragon());
				this.setMove((byte)2, Intent.DEFEND);
			}
			else if (rand == 4)
			{
				moveCards.add(new Kuriboh());
				moveCards.add(new CaveDragon());
				moveCards.add(new Sparks());
				overflowCards.add(new BackgroundDragon());
				this.setMove((byte)2, Intent.ATTACK_BUFF, this.sparks.damage);
			}
			else
			{
				moveCards.add(new CaveDragon());
				moveCards.add(new Kuriboh());					
				moveCards.add(new Sparks());
				overflowCards.add(new BackgroundDragon());
				this.setMove((byte)2, Intent.ATTACK_BUFF, this.sparks.damage);
			}
			break;
		case 5:		
			if (summons > 0)
			{				
				moveCards.add(new SpiralSpearStrike());
				moveCards.add(new YamataDragon());
				this.setMove((byte)2, Intent.ATTACK_DEBUFF, this.yamata.damage + this.spiral.damage);
			}
			else
			{
				int roll = AbstractDungeon.aiRng.random(1, 4);
				if (roll == 1)
				{
					moveCards.add(new SpiralSpearStrike());
					moveCards.add(new CaveDragon());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.spiral.damage);
				}
				else if (roll == 2)
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new GoldenApples());					
					moveCards.add(new YamataDragon());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.yamata.damage);
				}
				else if (roll == 3)
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new Sparks());					
					moveCards.add(new YamataDragon());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.yamata.damage + this.sparks.damage);
				}
				else
				{
					moveCards.add(new SpiralSpearStrike());
					moveCards.add(new Sparks());
					this.setMove((byte)2, Intent.ATTACK, this.spiral.damage + this.sparks.damage);
				}	
			}
			break;
		case 6:			
			if (summons > 1)
			{
				int roll = AbstractDungeon.aiRng.random(1, 2);
				if (roll == 1)
				{
					moveCards.add(new RedEyes());
					AbstractCard red = new RedMedicine();
					red.upgrade();
					moveCards.add(red);
					this.setMove((byte)2, Intent.ATTACK_BUFF, this.redeyes.damage);
				}
				else
				{
					moveCards.add(new RedEyes());
					moveCards.add(new GoldenApples());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.redeyes.damage);
				}	
			}
			else
			{
				int roll = AbstractDungeon.aiRng.random(1, 4);
				if (roll == 1)
				{
					AbstractCard red = new RedMedicine();
					red.upgrade();
					moveCards.add(red);
					AbstractCard tot = new TotemDragon();
					tot.upgrade();
					moveCards.add(tot);
					this.setMove((byte)2, Intent.BUFF);
				}
				else if (roll == 2)
				{
					AbstractCard red = new RedMedicine();
					red.upgrade();
					moveCards.add(red);
					moveCards.add(new PowerWall());
					moveCards.add(new GoldenApples());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
				}
				else if (roll == 3)
				{
					AbstractCard tot = new TotemDragon();
					tot.upgrade();
					moveCards.add(tot);
					moveCards.add(new GoldenApples());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
				}
				else
				{
					AbstractCard tot = new TotemDragon();
					tot.upgrade();
					moveCards.add(tot);
					moveCards.add(new PowerWall());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
				}
			}
			break;
		case 7:
			if (summons >= this.armageddon)
			{
				int roll = AbstractDungeon.aiRng.random(1, 2);
				if (roll == 1)
				{
					moveCards.add(new ArmageddonDragonEmp());					
					moveCards.add(new GoldenApples());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.arma.damage);
				}
				else
				{
					moveCards.add(new ArmageddonDragonEmp());
					moveCards.add(new CaveDragon());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.arma.damage);
				}	
			}
			else if (summons >= this.armageddon - 1)
			{
				moveCards.add(new CaveDragon());
				moveCards.add(new ArmageddonDragonEmp());	
				overflowCards.add(new PreventRat());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.arma.damage);
			}
			else if (summons > 1)
			{
				int roll = AbstractDungeon.aiRng.random(1, 2);
				if (roll == 1)
				{
					moveCards.add(new RedEyes());					
					moveCards.add(new GoldenApples());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.redeyes.damage);
				}
				else
				{
					moveCards.add(new RedEyes());
					moveCards.add(new CaveDragon());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.redeyes.damage);
				}	
			}
			else if (summons > 0)
			{
				moveCards.add(new CaveDragon());
				moveCards.add(new RedEyes());
				overflowCards.add(new PreventRat());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.redeyes.damage);
			}
			else
			{
				int roll = AbstractDungeon.aiRng.random(1, 2);
				if (roll == 1)
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new PreventRat());
					moveCards.add(new GoldenApples());
					this.setMove((byte)2, Intent.DEFEND);
				}
				else
				{
					moveCards.add(new PreventRat());
					moveCards.add(new CaveDragon());				
					moveCards.add(new GoldenApples());
					this.setMove((byte)2, Intent.DEFEND);
				}	
			}
			break;
		case 8:		
			int seed = AbstractDungeon.aiRng.random(1, 4);
			if (seed == 1)
			{
				moveCards.add(new Reinforcements());
				moveCards.add(new LesserDragon());
				moveCards.add(new Sparks());	
				this.setMove((byte)2, Intent.ATTACK_BUFF, this.sparks.damage + this.lesser.damage);
			}
			else if (seed == 2)
			{
				moveCards.add(new CaveDragon());
				moveCards.add(new LesserDragon());
				moveCards.add(new Sparks());	
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.sparks.damage + this.lesser.damage);
			}
			else if (seed == 3)
			{
				moveCards.add(new CaveDragon());
				moveCards.add(new LesserDragon());
				moveCards.add(new LesserDragon());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.lesser.damage * 2);
			}
			else if (seed == 4)
			{
				moveCards.add(new Reinforcements());
				moveCards.add(new LesserDragon());
				moveCards.add(new LesserDragon());
				this.setMove((byte)2, Intent.ATTACK_BUFF, this.lesser.damage * 2);
			}
			break;
		case 9:
			int seedy = AbstractDungeon.aiRng.random(1, 5);
			if (seedy == 1)
			{
				moveCards.add(new Sparks());
				moveCards.add(new PowerWall());
				moveCards.add(new GoldenApples());
				overflowCards.add(new PreventRat());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.sparks.damage);
			}
			else if (seedy == 2)
			{
				moveCards.add(new PreventRat());
				moveCards.add(new Sparks());
				moveCards.add(new GoldenApples());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.sparks.damage);
			}
			else if (seedy == 3)
			{
				moveCards.add(new SpiralSpearStrike());
				moveCards.add(new GoldenApples());
				overflowCards.add(new PreventRat());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.spiral.damage);
			}
			else if (seedy == 4)
			{
				moveCards.add(new SpiralSpearStrike());
				moveCards.add(new Sparks());
				overflowCards.add(new PreventRat());
				this.setMove((byte)2, Intent.ATTACK, this.spiral.damage + this.sparks.damage);
			}
			else
			{
				moveCards.add(new SpiralSpearStrike());
				moveCards.add(new PreventRat());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, this.spiral.damage);
			}
			break;
		case 10:
			switch (moveRoll)
			{
				case 1:
					moveCards.add(new BerserkerCrush());
					moveCards.add(new Hinotama());
					overflowCards.add(new BackgroundDragon());
					this.setMove((byte)2, Intent.ATTACK, this.beserk.damage + (this.hinotama.damage * this.hinotama.magicNumber));
					break;
				case 2:
					moveCards.add(new BerserkerCrush());
					moveCards.add(new Reinforcements());
					overflowCards.add(new BackgroundDragon());
					this.setMove((byte)2, Intent.ATTACK_BUFF, this.beserk.damage);
					break;
				case 3:
					moveCards.add(new Reinforcements());
					moveCards.add(new LesserDragon());
					moveCards.add(new Hinotama());
					overflowCards.add(new BackgroundDragon());
					this.setMove((byte)2, Intent.ATTACK_BUFF, this.lesser.damage + (this.hinotama.damage * this.hinotama.magicNumber));
					break;
				case 4:
					moveCards.add(new BackgroundDragon());
					moveCards.add(new Hinotama());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.hinotama.damage, this.hinotama.magicNumber, true);
					break;
				case 5:
					moveCards.add(new BackgroundDragon());
					moveCards.add(new Reinforcements());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
					break;
			}
			break;
		case 11:
			if (summons > 0)
			{
				moveCards.add(new SpiralSpearStrike());
				moveCards.add(new YamataDragon());
				overflowCards.add(new PreventRat());
				this.setMove((byte)2, Intent.ATTACK, this.spiral.damage + this.yamata.damage);
			}
			else
			{
				int roll = AbstractDungeon.aiRng.random(1, 3);
				if (roll == 1)
				{
					moveCards.add(new SpiralSpearStrike());
					moveCards.add(new LesserDragon());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK, this.spiral.damage + this.lesser.damage);
				}
				else if (roll == 2)
				{
					moveCards.add(new SpiralSpearStrike());
					moveCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.spiral.damage);
				}
				else
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new YamataDragon());
					moveCards.add(new LesserDragon());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, this.lesser.damage + this.yamata.damage);
				}
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
	
	private ArrayList<ArrayList<String>> getKaibaHands()
	{
		ArrayList<ArrayList<String>> newHands = new ArrayList<ArrayList<String>>();
		ArrayList<String> hand0 = new ArrayList<String>();
		ArrayList<String> hand1 = new ArrayList<String>();
		ArrayList<String> hand2 = new ArrayList<String>();
		ArrayList<String> hand3 = new ArrayList<String>();
		ArrayList<String> hand4 = new ArrayList<String>();
		ArrayList<String> hand5 = new ArrayList<String>();
		ArrayList<String> hand6 = new ArrayList<String>();
		ArrayList<String> hand7 = new ArrayList<String>();
		ArrayList<String> hand8 = new ArrayList<String>();
		ArrayList<String> hand9 = new ArrayList<String>();
		ArrayList<String> hand10 = new ArrayList<String>();
		ArrayList<String> hand11 = new ArrayList<String>();
		hand0.add("Miraculous Descent+");
		hand0.add("Lesser Dragon");
		hand0.add("Hinotama");
		hand0.add("Castle Walls");
		hand0.add("Prevent Rat");    	

		hand1.add("Sparks");
		hand1.add("Prevent Rat");
		hand1.add("Castle Walls");
		hand1.add("Yamata Dragon"); 
		hand1.add("Millennium Shield");

		hand2.add("Blue Eyes"); 
		hand2.add("Cave Dragon");
		hand2.add("Castle Walls");
		hand2.add("Booster Dragon");
		hand2.add("Kuriboh");

		hand3.add("Berserker Crush");
		hand3.add("Lesser Dragon");
		hand3.add("Destruct Potion");
		hand3.add("Mirage Dragon"); 
		hand3.add("Golden Apples");

		hand4.add("Exploder Dragonwing");    
		hand4.add("Kuriboh");
		hand4.add("Cave Dragon");
		hand4.add("Background Dragon");
		hand4.add("Sparks"); 

		hand5.add("Golden Apples");
		hand5.add("Sparks");
		hand5.add("Cave Dragon");
		hand5.add("Spiral Spear Strike");    
		hand5.add("Yamata Dragon"); 

		hand6.add("Red Medicine+");
		hand6.add("Red Eyes");
		hand6.add("Totem Dragon+"); 
		hand6.add("Castle Walls");
		hand6.add("Golden Apples");

		hand7.add("Armageddon Dragon");
		hand7.add("Golden Apples");    	
		hand7.add("Cave Dragon");
		hand7.add("Red Eyes"); 
		hand7.add("Prevent Rat");
		
		hand8.add("Sparks");
		hand8.add("Reinforcements");    	
		hand8.add("Lesser Dragon");
		hand8.add("Cave Dragon"); 
		hand8.add("Lesser Dragon");
				
		hand9.add("Sparks");
		hand9.add("Castle Walls");    	
		hand9.add("Prevent Rat");
		hand9.add("Spiral Spear Strike"); 
		hand9.add("Golden Apples");
				
		hand10.add("Berserker Crush");
		hand10.add("Hinotama");    	
		hand10.add("Background Dragon");
		hand10.add("Lesser Dragon"); 
		hand10.add("Reinforcements");
				
		hand11.add("Lesser Dragon");
		hand11.add("Yamata Dragon");    	
		hand11.add("Cave Dragon");
		hand11.add("Spiral Spear Strike"); 
		hand11.add("Prevent Rat");
		
		
		newHands.add(hand0);
		newHands.add(hand1);
		newHands.add(hand2);
		newHands.add(hand3);
		newHands.add(hand4);
		newHands.add(hand5);
		newHands.add(hand6);
		newHands.add(hand7);
		newHands.add(hand8);
		newHands.add(hand9);
		newHands.add(hand10);
		newHands.add(hand11);
		return newHands;
	}

}
