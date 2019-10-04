package duelistmod.monsters;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.powers.enemyPowers.*;

public class SuperKaiba extends DuelistMonster
{

	public static final String ID = DuelistMod.makeID("SuperKaiba"); // Monster ID (remember the prefix - yourModID:DefaultMonster)
	private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID); // Grab the string

	public static final String NAME = monsterstrings.NAME; // The name of the monster
	public static final String[] MOVES = monsterstrings.MOVES; // The names of the moves
	public static final String[] DIALOG = monsterstrings.DIALOG; // The dialogue (if any)

	private static final float HB_X = 0.0F;     
	private static final float HB_Y = -25.0F; 
	private static final float HB_W = 200.0F;   
	private static final float HB_H = 330.0F;   

	private static final int HP_MIN = 200; 
	private static final int HP_MAX = 280; 
	private static final int A7_HP_MIN = 250; 
	private static final int A7_HP_MAX = 330;

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

	public SuperKaiba() 
	{
		super(NAME, ID, 50, HB_X, HB_Y, HB_W, HB_H, "KaibaModel2", HP_MIN, HP_MAX, A7_HP_MIN, A7_HP_MAX);
		this.setHands(initHands());
		ArrayList<String> dialog = new ArrayList<String>();
		for (String s : DIALOG) { dialog.add(s); }
		this.setupDialog(dialog);
		this.type = EnemyType.ELITE;
		this.cardDialogMin = 3;
		this.cardDialogMax = 8;
		this.startingToken = "Dragon Token";
		this.overflowDialog = this.dialog.get(2);
		//this.lowWeightHandIndex = 7;
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
		this.miracle.upgrade();
		this.totem.upgrade();
		this.redmed.upgrade();
		//this.deathSound = "coolKaibaSound";
	}

	@Override
	protected ArrayList<AbstractCard> getHandMove()
	{
		ArrayList<AbstractCard> moveCards = new ArrayList<AbstractCard>();
		int summons = getSummons();
		boolean hasLambs = hasLambs();
		int moveRoll = AbstractDungeon.aiRng.random(1, 5);
		switch (this.handIndex)
		{
		case 0:
			if (summons > 3)
			{
				moveCards.add(new ScrapFactory());
				moveCards.add(new BabyDragon());
				moveCards.add(new FiveHeaded());
				this.setMove((byte)2, Intent.ATTACK, 55);
			}
			else if (summons > 1)
			{
				moveCards.add(new ScrapFactory());
				AbstractCard mir = new MiraculousDescentEnemy();
				mir.upgrade();
				moveCards.add(mir);
				moveCards.add(new BabyDragon());
				moveCards.add(new Reinforcements());
				this.setMove((byte)2, Intent.DEFEND_BUFF);
			}
			else
			{
				int roll = AbstractDungeon.aiRng.random(1, 3);
				if (roll == 1)
				{
					AbstractCard mir = new MiraculousDescentEnemy();
					mir.upgrade();
					moveCards.add(mir);
					moveCards.add(new BabyDragon());
					this.setMove((byte)2, Intent.DEFEND);
				}
				else if (roll == 2)
				{
					AbstractCard mir = new MiraculousDescentEnemy();
					mir.upgrade();
					moveCards.add(mir);
					moveCards.add(new Reinforcements());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
				}
				else
				{
					moveCards.add(new Reinforcements());
					moveCards.add(new BabyDragon());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
				}
			}
			break;
		case 1:
			switch (moveRoll)
			{
			case 1:
				moveCards.add(new BabyDragon());
				moveCards.add(new BabyDragon());
				moveCards.add(new YamataDragon());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, 11);
				break;
			case 2:
				moveCards.add(new StrayLambs());
				moveCards.add(new ScrapFactory());
				moveCards.add(new BabyDragon());
				moveCards.add(new BabyDragon());
				moveCards.add(new YamataDragon());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, 11);
				break;
			case 3:
				moveCards.add(new BabyDragon());
				moveCards.add(new YamataDragon());
				moveCards.add(new BabyDragon());				
				this.setMove((byte)2, Intent.ATTACK_DEFEND, 11);
				break;
			case 4:
				moveCards.add(new BabyDragon());
				moveCards.add(new StrayLambs());
				moveCards.add(new ScrapFactory());
				moveCards.add(new BabyDragon());
				this.setMove((byte)2, Intent.DEFEND);
				break;
			case 5:
				moveCards.add(new BabyDragon());
				moveCards.add(new BabyDragon());
				this.setMove((byte)2, Intent.DEFEND);
				break;
			}
			break;
		case 2:
			if (summons > 1)
			{
				moveCards.add(new BlueEyes());
				moveCards.add(new CaveDragon());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, 25);
			}
			else if (summons == 1)
			{					
				moveCards.add(new CaveDragon());
				moveCards.add(new BlueEyes());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, 25);
			}
			else
			{
				switch (moveRoll)
				{
				case 1:
					moveCards.add(new CaveDragon());
					moveCards.add(new BerserkerCrush());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 11);
					break;
				case 2:
					moveCards.add(new CaveDragon());
					moveCards.add(new StrayLambs());
					this.setMove((byte)2, Intent.DEFEND);
					break;
				case 3:
					moveCards.add(new BabyDragon());
					moveCards.add(new BerserkerCrush());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 11);
					break;
				case 4:
					moveCards.add(new CaveDragon());
					moveCards.add(new BerserkerCrush());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 11);
					break;
				case 5:
					moveCards.add(new CaveDragon());
					moveCards.add(new StrayLambs());
					this.setMove((byte)2, Intent.DEFEND);
					break;
				}
			}
			break;
		case 3:
			if (!this.destructUsed && this.summonsThisCombat > 2)
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
			if (summons > 1)
			{
				AbstractCard totem = new Hinotama();
				totem.upgrade();
				moveCards.add(totem);
				moveCards.add(new BlueEyes());
				this.setMove((byte)2, Intent.ATTACK, 41);
			}
			else
			{
				switch (moveRoll)
				{
					case 1:
						AbstractCard totem = new Hinotama();
						totem.upgrade();
						moveCards.add(totem);
						moveCards.add(new Hinotama());
						moveCards.add(new BabyDragon());
						overflowCards.add(new BackgroundDragon());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 7, true);
						break;
					case 2:
						moveCards.add(new BabyDragon());
						moveCards.add(new BlueEyes());
						overflowCards.add(new BackgroundDragon());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 25);
						break;
					case 3:
						moveCards.add(new BabyDragon());
						moveCards.add(new BackgroundDragon());
						this.setMove((byte)2, Intent.DEFEND);
						break;
					case 4:							
						AbstractCard totemB = new Hinotama();
						totemB.upgrade();
						moveCards.add(totemB);
						moveCards.add(new BackgroundDragon());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 4, true);
						break;
					case 5:
						AbstractCard totemC = new Hinotama();
						totemC.upgrade();
						moveCards.add(totemC);
						moveCards.add(new Hinotama());
						moveCards.add(new BabyDragon());
						overflowCards.add(new BackgroundDragon());
						this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 7, true);
						break;
				}
			}
			break;
		case 5:
			if (summons >= armageddon)
			{
				int roll = AbstractDungeon.aiRng.random(1, 2);
				if (roll == 1)
				{
					moveCards.add(new ArmageddonDragonEmp());
					moveCards.add(new CaveDragon());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 85);
				}
				else
				{
					moveCards.add(new ArmageddonDragonEmp());
					moveCards.add(new Hinotama());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 97);
				}
			}
			else
			{
				switch (moveRoll)
				{
					case 1:
						moveCards.add(new CaveDragon());
						moveCards.add(new Reinforcements());
						moveCards.add(new Hinotama());
						this.setMove((byte)2, Intent.ATTACK_BUFF, 4, 3, true);
						break;
					case 2:
						moveCards.add(new Hinotama());
						moveCards.add(new StrayLambs());
						this.setMove((byte)2, Intent.ATTACK_BUFF, 4, 3, true);
						break;
					case 3:
						moveCards.add(new Reinforcements());
						moveCards.add(new Hinotama());
						moveCards.add(new CaveDragon());
						this.setMove((byte)2, Intent.ATTACK_BUFF, 4, 3, true);
						break;
					case 4:
						moveCards.add(new CaveDragon());
						moveCards.add(new StrayLambs());
						this.setMove((byte)2, Intent.DEFEND_BUFF);
						break;
					case 5:
						moveCards.add(new Reinforcements());
						moveCards.add(new StrayLambs());
						this.setMove((byte)2, Intent.BUFF);
						break;
				}
			}
			break;
		case 6:
			if (summons > 3)
			{
				moveCards.add(new BlueEyesUltimate());
				moveCards.add(new YamataDragon());
				this.setMove((byte)2, Intent.ATTACK, 56);
			}
			else if (summons > 2)
			{
				int roll = AbstractDungeon.aiRng.random(1, 2);
				if (roll == 1)
				{
					moveCards.add(new BlueEyesUltimate());
					moveCards.add(new CaveDragon());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 56);
				}
				else
				{
					moveCards.add(new BlueEyesUltimate());
					moveCards.add(new Hinotama());
					this.setMove((byte)2, Intent.ATTACK, 57);
				}					
			}
			else if (summons > 1)
			{
				int roll = AbstractDungeon.aiRng.random(1, 2);
				if (roll == 1)
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new BlueEyesUltimate());	
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 45);
				}
				else
				{
					moveCards.add(new BoosterDragon());
					moveCards.add(new Hinotama());
					this.setMove((byte)2, Intent.ATTACK_BUFF, 4, 3, true);
				}			
			}
			else if (summons > 0)
			{
				int roll = AbstractDungeon.aiRng.random(1, 2);
				if (roll == 1)
				{
					moveCards.add(new YamataDragon());
					moveCards.add(new Hinotama());	
					this.setMove((byte)2, Intent.ATTACK, 23);
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
				moveCards.add(new CaveDragon());
				moveCards.add(new YamataDragon());	
				moveCards.add(new Hinotama());	
				this.setMove((byte)2, Intent.ATTACK_DEFEND, 23);
			}
			break;
		case 7:
			switch (moveRoll)
			{
				case 1:
					AbstractCard hion = new Hinotama();
					hion.upgrade();
					moveCards.add(hion);
					moveCards.add(new CaveDragon());
					moveCards.add(new CaveDragon());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 4, true);
					break;
				case 2:
					moveCards.add(new CaveDragon());
					moveCards.add(new CaveDragon());
					moveCards.add(new GoldenApples());
					overflowCards.add(new PreventRat());	
					this.setMove((byte)2, Intent.DEFEND);
					break;
				case 3:
					moveCards.add(new CaveDragon());
					moveCards.add(new PreventRat());
					moveCards.add(new GoldenApples());	
					this.setMove((byte)2, Intent.DEFEND);
					break;
				case 4:						
					moveCards.add(new PreventRat());
					AbstractCard hionB = new Hinotama();
					hionB.upgrade();
					moveCards.add(hionB);
					moveCards.add(new GoldenApples());	
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 4, true);
					break;
				case 5:
					moveCards.add(new CaveDragon());
					moveCards.add(new CaveDragon());
					AbstractCard hionC = new Hinotama();
					hionC.upgrade();
					moveCards.add(hionC);	
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 4, true);
					break;
			}
			break;
		case 8:
			if (summons > 2)
			{
				moveCards.add(new Reinforcements());
				moveCards.add(new StardustDragon());
				this.setMove((byte)2, Intent.ATTACK_BUFF, 22);
			}
			
			else if (summons > 1)
			{
				moveCards.add(new CaveDragon());
				moveCards.add(new StardustDragon());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, 22);
			}
			
			else
			{
				int roll = AbstractDungeon.aiRng.random(1, 3);
				switch (roll)
				{
					case 1:
						moveCards.add(new CaveDragon());
						moveCards.add(new StrayLambs());
						this.setMove((byte)2, Intent.DEFEND_BUFF);
						break;
					case 2:
						moveCards.add(new CaveDragon());
						moveCards.add(new Reinforcements());
						moveCards.add(new SilverApples());
						this.setMove((byte)2, Intent.DEFEND_BUFF);
						break;
					case 3:
						moveCards.add(new StrayLambs());
						moveCards.add(new Reinforcements());
						this.setMove((byte)2, Intent.BUFF);
						break;
							
				}
			}
			break;
		case 9:
			if (summons > 2)
			{
				int roll = AbstractDungeon.aiRng.random(1, 2);
				if (roll == 1)
				{
					AbstractCard hino = new Hinotama();
					hino.upgrade();
					moveCards.add(new StardustDragon());
					moveCards.add(hino);
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK, 38);
				}
				else
				{
					moveCards.add(new StardustDragon());
					moveCards.add(new GoldenApples());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 22);
				}
			}
			
			else if (summons > 1)
			{
				moveCards.add(new CaveDragon());
				moveCards.add(new StardustDragon());
				overflowCards.add(new PreventRat());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, 22);
			}
			else
			{
				int roll = AbstractDungeon.aiRng.random(1, 2);
				if (roll == 1)
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new GoldenApples());
					AbstractCard hino = new Hinotama();
					hino.upgrade();
					moveCards.add(hino);
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 4, true);
				}
				else
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new PreventRat());
					AbstractCard hino = new Hinotama();
					hino.upgrade();
					moveCards.add(hino);
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 4, true);
				}
			}
			break;
		case 10:
			if (summons > 5)
			{
				int roll = AbstractDungeon.aiRng.random(1, 2);
				if (roll == 1)
				{
					moveCards.add(new BusterBlader());
					moveCards.add(new StardustDragon());
					overflowCards.add(new BackgroundDragon());
					this.setMove((byte)2, Intent.ATTACK, 53);
				}
				else
				{
					moveCards.add(new StardustDragon());
					moveCards.add(new BusterBlader());
					overflowCards.add(new BackgroundDragon());
					this.setMove((byte)2, Intent.ATTACK, 53);
				}
			}
			
			else if (summons > 1)
			{
				moveCards.add(new BackgroundDragon());
				moveCards.add(new BusterBlader());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, 31);
			}
			
			else if (summons > 0)
			{
				moveCards.add(new BabyDragon());
				moveCards.add(new StardustDragon());
				overflowCards.add(new BackgroundDragon());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, 22);
			}
			
			else
			{
				int roll = AbstractDungeon.aiRng.random(1, 2);
				if (roll == 1)
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new BabyDragon());
					moveCards.add(new BusterBlader());
					overflowCards.add(new BackgroundDragon());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 31);
				}
				else
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new BackgroundDragon());
					this.setMove((byte)2, Intent.DEFEND);
				}					
			}
			
			break;
		case 11:
			if (summons > 5)
			{
				int roll = AbstractDungeon.aiRng.random(1, 2);
				if (roll == 1)
				{
					moveCards.add(new ScrapFactory());
					moveCards.add(new CaveDragon());
					moveCards.add(new FiendSkull());
					moveCards.add(new BlueEyes());
					this.setMove((byte)2, Intent.ATTACK_DEBUFF, 40);
				}
				else
				{
					moveCards.add(new ScrapFactory());
					moveCards.add(new CaveDragon());
					moveCards.add(new RedEyes());
					moveCards.add(new BlueEyes());
					this.setMove((byte)2, Intent.ATTACK_DEBUFF, 39);
				}
			}
			
			else if (summons > 0)
			{
				int roll = AbstractDungeon.aiRng.random(1, 3);
				if (roll == 1)
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new RedEyes());
					this.setMove((byte)2, Intent.ATTACK_DEBUFF, 14);
				}
				else if (roll == 2)
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new FiendSkull());
					this.setMove((byte)2, Intent.ATTACK_DEBUFF, 15);
				}
				else
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new BlueEyes());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 25);
				}
			}
			
			else
			{
				moveCards.add(new CaveDragon());
				this.setMove((byte)2, Intent.DEFEND);
			}
			
			
			break;
		case 12:
			if (summons > 2)
			{
				moveCards.add(new RedEyes());
				moveCards.add(new Hinotama());
				this.setMove((byte)2, Intent.ATTACK, 26);
			}
			else if (summons > 0)
			{
				moveCards.add(new BabyDragon());
				moveCards.add(new RedEyes());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, 14);
			}
			else
			{
				int roll = AbstractDungeon.aiRng.random(1, 3);
				if (roll == 1)
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new BabyDragon());
					moveCards.add(new Hinotama());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 3, true);
				}
				else if (roll == 2)
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new StrayLambs());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
				}
				else
				{
					moveCards.add(new Hinotama());
					moveCards.add(new StrayLambs());
					this.setMove((byte)2, Intent.ATTACK_BUFF, 4, 3, true);
				}
			}
			break;
		case 13:
			if (summons > 2)
			{
				int roll = AbstractDungeon.aiRng.random(1, 3);
				if (roll == 1)
				{
					moveCards.add(new Hinotama());
					moveCards.add(new FiendSkull());
					this.setMove((byte)2, Intent.ATTACK, 27);
				}
				else if (roll == 2)
				{
					moveCards.add(new FiendSkull());
					moveCards.add(new CaveDragon());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 15);
				}
				else
				{
					moveCards.add(new SilverApples());
					moveCards.add(new FiendSkull());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 15);
				}
			}
			
			else if (summons > 1)
			{
				moveCards.add(new CaveDragon());
				moveCards.add(new FiendSkull());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, 15);
			}
			
			else 
			{					
				moveCards.add(new CaveDragon());
				moveCards.add(new Hinotama());
				moveCards.add(new SilverApples());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 3, true);
			}
			
			break;
		case 14:
			if (summons > 2)
			{
				moveCards.add(new Reinforcements());
				moveCards.add(new FiendSkull());
				this.setMove((byte)2, Intent.ATTACK_BUFF, 15);
			}
			
			else if (summons > 1)
			{
				int roll = AbstractDungeon.aiRng.random(1, 2);
				if (roll == 1)
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new FiendSkull());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 15);
				}
				else
				{
					moveCards.add(new BoosterDragon());
					moveCards.add(new SilverApples());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
				}
			}
			else if (summons > 0)
			{
				moveCards.add(new CaveDragon());
				moveCards.add(new BoosterDragon());
				this.setMove((byte)2, Intent.DEFEND_BUFF);
			}
			
			else
			{
				moveCards.add(new CaveDragon());
				moveCards.add(new Reinforcements());
				moveCards.add(new SilverApples());
				this.setMove((byte)2, Intent.DEFEND_BUFF);
			}
			
			break;
		case 15:
			if (summons > 0)
			{
				int roll = AbstractDungeon.aiRng.random(1, 2);
				if (roll == 1)
				{
					moveCards.add(new BabyDragon());
					moveCards.add(new Reinforcements());
					moveCards.add(new BusterBlader());
					this.setMove((byte)2, Intent.ATTACK_BUFF, 31);
				}
				else
				{
					moveCards.add(new StrayLambs());
					moveCards.add(new BusterBlader());
					this.setMove((byte)2, Intent.ATTACK_BUFF, 31);
				}
			}
			else
			{
				int roll = AbstractDungeon.aiRng.random(1, 3);
				if (roll == 1)
				{
					moveCards.add(new BabyDragon());
					moveCards.add(new Reinforcements());
					moveCards.add(new GoldenApples());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
				}
				else if (roll == 2)
				{
					moveCards.add(new BabyDragon());
					moveCards.add(new StrayLambs());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
				}
				else
				{
					moveCards.add(new StrayLambs());
					moveCards.add(new GoldenApples());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
				}
			}
			break;
		case 16:
			if (summons > 3)
			{
				moveCards.add(new ScrapFactory());
				moveCards.add(new BabyDragon());
				moveCards.add(new BlueEyes());
				moveCards.add(new BerserkerCrush());
				this.setMove((byte)2, Intent.ATTACK_DEBUFF, 36);
			}
			
			else if (summons > 2)
			{
				moveCards.add(new StardustDragon());
				moveCards.add(new BabyDragon());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, 22);
			}
			
			else if (summons > 0)
			{
				moveCards.add(new BabyDragon());
				moveCards.add(new StardustDragon());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, 22);
			}
			else
			{
				int roll = AbstractDungeon.aiRng.random(1,2);
				if (roll == 1)
				{
					moveCards.add(new BabyDragon());
					moveCards.add(new BerserkerCrush());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 11);
				}
				else
				{
					moveCards.add(new BabyDragon());
					moveCards.add(new BlueEyes());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 25);
				}
			}
			break;
		case 17:
			if (summons > 2)
			{
				int roll = AbstractDungeon.aiRng.random(1,2);
				if (roll == 1)
				{
					moveCards.add(new Hinotama());
					moveCards.add(new BlueEyesUltimate());
					overflowCards.add(new BackgroundDragon());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK, 57);
				}
				else
				{
					AbstractCard hino = new Hinotama();
					hino.upgrade();
					moveCards.add(hino);
					moveCards.add(new BlueEyesUltimate());
					overflowCards.add(new BackgroundDragon());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK, 61);
				}
			}
			
			else if (summons > 1)
			{
				int roll = AbstractDungeon.aiRng.random(1,2);
				if (roll == 1)
				{
					moveCards.add(new PreventRat());
					moveCards.add(new BlueEyesUltimate());
					overflowCards.add(new BackgroundDragon());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 45);
				}
				else
				{
					moveCards.add(new BabyDragon());
					moveCards.add(new BlueEyesUltimate());
					overflowCards.add(new BackgroundDragon());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 45);
				}
			}
			
			else
			{
				int roll = AbstractDungeon.aiRng.random(1,2);
				if (roll == 1)
				{
					moveCards.add(new BabyDragon());
					moveCards.add(new BackgroundDragon());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.DEFEND);
				}
				else
				{
					AbstractCard hino = new Hinotama();
					hino.upgrade();
					moveCards.add(hino);
					moveCards.add(new Hinotama());
					moveCards.add(new PreventRat());
					overflowCards.add(new BackgroundDragon());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 7, true);
				}
			}
			
			break;
		case 18:
			if (summons > 2)
			{
				AbstractCard hino = new Hinotama();
				hino.upgrade();
				moveCards.add(hino);
				moveCards.add(new BlueEyesUltimate());
				overflowCards.add(new BackgroundDragon());
				overflowCards.add(new PreventRat());
				overflowCards.add(new PreventRat());
				this.setMove((byte)2, Intent.ATTACK, 61);
			}
			
			else if (summons > 0)
			{
				moveCards.add(new BabyDragon());
				moveCards.add(new BlueEyesUltimate());
				overflowCards.add(new BackgroundDragon());
				overflowCards.add(new PreventRat());
				overflowCards.add(new PreventRat());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, 45);
			}
			
			else
			{
				int roll = AbstractDungeon.aiRng.random(1, 3);
				if (roll == 1)
				{
					moveCards.add(new PreventRat());
					AbstractCard hino = new Hinotama();
					hino.upgrade();
					moveCards.add(hino);
					moveCards.add(new BabyDragon());
					overflowCards.add(new BackgroundDragon());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 4, true);
				}
				else if (roll == 2)
				{
					moveCards.add(new BabyDragon());
					moveCards.add(new BackgroundDragon());
					overflowCards.add(new PreventRat());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.DEFEND);
				}
				else
				{
					AbstractCard hino = new Hinotama();
					hino.upgrade();
					moveCards.add(hino);
					moveCards.add(new PreventRat());
					moveCards.add(new PreventRat());
					overflowCards.add(new BackgroundDragon());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 4, true);
				}
			}
			
			break;
		case 19:
			if (summons > 1)
			{
				moveCards.add(new RedEyes());
				moveCards.add(new CaveDragon());
				overflowCards.add(new BackgroundDragon());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, 14);
			}
			else if (summons > 0)
			{					
				moveCards.add(new CaveDragon());
				moveCards.add(new RedEyes());
				overflowCards.add(new BackgroundDragon());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, 14);
			}
			else
			{
				int roll = AbstractDungeon.aiRng.random(1, 5);
				if (roll == 1)
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new BabyDragon());
					moveCards.add(new Hinotama());
					overflowCards.add(new BackgroundDragon());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 3, true);
				}
				else if (roll == 2)
				{
					moveCards.add(new CaveDragon());
					moveCards.add(new BackgroundDragon());
					this.setMove((byte)2, Intent.DEFEND);
				}
				else if (roll == 3)
				{
					moveCards.add(new BabyDragon());
					moveCards.add(new RedEyes());
					overflowCards.add(new BackgroundDragon());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 14);
				}
				else if (roll == 4)
				{
					moveCards.add(new Hinotama());
					moveCards.add(new BackgroundDragon());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 3, true);
				}
				else
				{
					moveCards.add(new BabyDragon());
					moveCards.add(new BackgroundDragon());
					this.setMove((byte)2, Intent.DEFEND);
				}
			}
			
			break;
		case 20:
			if (summons > 2)
			{
				moveCards.add(new StardustDragon());
				AbstractCard hino = new Hinotama();
				hino.upgrade();
				moveCards.add(hino);
				overflowCards.add(new BackgroundDragon());
				this.setMove((byte)2, Intent.ATTACK, 38);
			}
			
			else if (summons > 0)
			{
				moveCards.add(new BabyDragon());
				moveCards.add(new StardustDragon());
				overflowCards.add(new BackgroundDragon());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, 22);
			}
			
			else
			{
				int roll = AbstractDungeon.aiRng.random(1, 3);
				if (roll == 1)
				{
					moveCards.add(new BabyDragon());
					AbstractCard hino = new Hinotama();
					hino.upgrade();
					moveCards.add(hino);
					moveCards.add(new SilverApples());
					overflowCards.add(new BackgroundDragon());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 4, true);
				}
				else if (roll == 2)
				{
					moveCards.add(new SilverApples());
					moveCards.add(new BackgroundDragon());
					this.setMove((byte)2, Intent.DEFEND);
				}
				else
				{
					moveCards.add(new BackgroundDragon());
					AbstractCard hino = new Hinotama();
					hino.upgrade();
					moveCards.add(hino);
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 4, true);
				}
			}
			break;
		case 21:
			if (summons > this.earthGiant + 2)
			{
				moveCards.add(new EarthGiant());
				moveCards.add(new RedEyes());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, 14);
			}
			else if (summons > this.earthGiant + 1)
			{
				moveCards.add(new EarthGiant());
				moveCards.add(new LabyrinthWall());
				this.setMove((byte)2, Intent.DEFEND);
			}
			
			else if (summons >= this.earthGiant)
			{
				moveCards.add(new EarthGiant());
				AbstractCard red = new RedMedicine();
				red.upgrade();
				moveCards.add(red);
				this.setMove((byte)2, Intent.DEFEND_BUFF);
			}
			
			else if (summons > 1)
			{
				moveCards.add(new CaveDragon());
				moveCards.add(new RedEyes());
				this.setMove((byte)2, Intent.ATTACK_DEFEND, 14);
			}
			
			else
			{
				moveCards.add(new CaveDragon());
				AbstractCard red = new RedMedicine();
				red.upgrade();
				moveCards.add(red);
				this.setMove((byte)2, Intent.DEFEND_BUFF);
			}
			
			break;
		case 22:
			switch (moveRoll)
			{
				case 1:
					AbstractCard totem = new TotemDragon();
					totem.upgrade();
					moveCards.add(totem);
					moveCards.add(new Hinotama());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK_BUFF, 4, 4, true);
					break;
				case 2:
					moveCards.add(new PreventRat());
					moveCards.add(new CaveDragon());
					moveCards.add(new BabyDragon());
					this.setMove((byte)2, Intent.DEFEND);
					break;
				case 3:
					AbstractCard totemB = new TotemDragon();
					totemB.upgrade();
					moveCards.add(totemB);
					moveCards.add(new CaveDragon());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
					break;
				case 4:
					moveCards.add(new Hinotama());
					moveCards.add(new CaveDragon());
					moveCards.add(new BabyDragon());
					overflowCards.add(new PreventRat());
					this.setMove((byte)2, Intent.ATTACK_DEFEND, 4, 3, true);
					break;
				case 5:
					AbstractCard totemC = new TotemDragon();
					totemC.upgrade();
					moveCards.add(totemC);
					moveCards.add(new PreventRat());
					this.setMove((byte)2, Intent.DEFEND_BUFF);
					break;
			}
			break;
		default:
			switch (moveRoll)
			{
			case 1:
				moveCards.add(new GoldenApples());
				moveCards.add(new GoldenApples());
				moveCards.add(new SilverApples());
				this.setMove((byte)2, Intent.MAGIC);
				break;
			case 2:
				moveCards.add(new GoldenApples());
				moveCards.add(new GoldenApples());
				moveCards.add(new SilverApples());
				this.setMove((byte)2, Intent.MAGIC);
				break;
			case 3:
				moveCards.add(new GoldenApples());
				moveCards.add(new GoldenApples());
				moveCards.add(new SilverApples());
				this.setMove((byte)2, Intent.MAGIC);
				break;
			case 4:
				moveCards.add(new GoldenApples());
				moveCards.add(new GoldenApples());
				moveCards.add(new SilverApples());
				this.setMove((byte)2, Intent.MAGIC);
				break;
			case 5:
				moveCards.add(new GoldenApples());
				moveCards.add(new GoldenApples());
				moveCards.add(new SilverApples());
				this.setMove((byte)2, Intent.MAGIC);
				break;
			}
			break;
		}
		return moveCards;
	}

	private ArrayList<ArrayList<String>> initHands()
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
		ArrayList<String> hand12 = new ArrayList<String>();
		ArrayList<String> hand13 = new ArrayList<String>();
		ArrayList<String> hand14 = new ArrayList<String>();
		ArrayList<String> hand15 = new ArrayList<String>();
		ArrayList<String> hand16 = new ArrayList<String>();
		ArrayList<String> hand17 = new ArrayList<String>();
		ArrayList<String> hand18 = new ArrayList<String>();
		ArrayList<String> hand19 = new ArrayList<String>();
		ArrayList<String> hand20 = new ArrayList<String>();
		ArrayList<String> hand21 = new ArrayList<String>();
		ArrayList<String> hand22 = new ArrayList<String>();
		hand0.add("Miraculous Descent+");
		hand0.add("Reinforcements");
		hand0.add("Five Headed Dragon");
		hand0.add("Scrap Factory");
		hand0.add("Baby Dragon");    	

		hand1.add("Scrap Factory");
		hand1.add("Baby Dragon");
		hand1.add("Baby Dragon");
		hand1.add("Stray Lambs"); 
		hand1.add("Yamata Dragon");

		hand2.add("Cave Dragon"); 
		hand2.add("Blue Eyes");
		hand2.add("Beserker Crush");
		hand2.add("Stray Lambs");
		hand2.add("Baby Dragon");  	

		hand3.add("Berserker Crush");
		hand3.add("Lesser Dragon");
		hand3.add("Destruct Potion");
		hand3.add("Mirage Dragon"); 
		hand3.add("Golden Apples");
		
		hand4.add("Hinotama");    
		hand4.add("Blue Eyes");
		hand4.add("Background Dragon");
		hand4.add("Hinotama+");
		hand4.add("Baby Dragon"); 

		hand5.add("Reinforcements");
		hand5.add("Hinotama");
		hand5.add("Stray Lambs");
		hand5.add("Cave Dragon");    
		hand5.add("Armageddon Dragon"); 

		hand6.add("Blue Eyes Ultimate");
		hand6.add("Yamata Dragon");
		hand6.add("Booster Dragon"); 
		hand6.add("Cave Dragon");
		hand6.add("Hinotama");

		hand7.add("Cave Dragon");
		hand7.add("Cave Dragon");    	
		hand7.add("Hinotama+");
		hand7.add("Prevent Rat"); 
		hand7.add("Golden Apples");

		hand8.add("Stardust Dragon");
		hand8.add("Cave Dragon");
		hand8.add("Silver Apples");
		hand8.add("Reinforcements");
		hand8.add("Stray Lambs"); 

		hand9.add("Stardust Dragon");
		hand9.add("Hinotama+"); 	
		hand9.add("Cave Dragon"); 
		hand9.add("Golden Apples");
		hand9.add("Prevent Rat");

		hand10.add("Stardust Dragon");
		hand10.add("Cave Dragon"); 	
		hand10.add("Baby Dragon"); 
		hand10.add("Background Dragon");
		hand10.add("Buster Blader");
		
		hand11.add("Red Eyes");
		hand11.add("Fiend Skull");
		hand11.add("Cave Dragon");     	
		hand11.add("Blue Eyes"); 
		hand11.add("Scrap Factory");
		
		hand12.add("Red Eyes");
		hand12.add("Baby Dragon");
		hand12.add("Cave Dragon");     	
		hand12.add("Hinotama"); 
		hand12.add("Stray Lambs");
		
		hand13.add("Fiend Skull");
		hand13.add("Cave Dragon");
		hand13.add("Hinotama+");     	
		hand13.add("Five Headed Dragon"); 
		hand13.add("Silver Apples");
		
		hand14.add("Fiend Skull");
		hand14.add("Cave Dragon");
		hand14.add("Reinforcements");     	
		hand14.add("Booster Dragon"); 
		hand14.add("Silver Apples");
		
		hand15.add("Buster Blader");
		hand15.add("Baby Dragon");
		hand15.add("Reinforcements");     	
		hand15.add("Golden Apples"); 
		hand15.add("Stray Lambs");
		
		hand16.add("Baby Dragon");
		hand16.add("Stardust Dragon");
		hand16.add("Beserker Crush");     	
		hand16.add("Blue Eyes"); 
		hand16.add("Scrap Factory");
		
		hand17.add("Hinotama");
		hand17.add("Hinotama+");
		hand17.add("Blue Eyes Ultimate");     	
		hand17.add("Background Dragon"); 
		hand17.add("Prevent Rat");
		
		hand18.add("Hinotama+");
		hand18.add("Blue Eyes Ultimate");
		hand18.add("Background Dragon");     	
		hand18.add("Prevent Rat"); 
		hand18.add("Prevent Rat");

		hand19.add("Red Eyes");
		hand19.add("Baby Dragon");
		hand19.add("Cave Dragon");     	
		hand19.add("Hinotama"); 
		hand19.add("Background Dragon");
		
		hand20.add("Stardust Dragon");
		hand20.add("Hinotama+");
		hand20.add("Baby Dragon");     	
		hand20.add("Silver Apples"); 
		hand20.add("Background Dragon");
		
		hand21.add("Red Medicine+");
		hand21.add("Earth Giant");
		hand21.add("Labyrinth Wall");     	
		hand21.add("Cave Dragon"); 
		hand21.add("Red Eyes");
		
		hand22.add("Totem Dragon+");
		hand22.add("Prevent Rat");
		hand22.add("Hinotama");
		hand22.add("Cave Dragon"); 
		hand22.add("Baby Dragon+");

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
		newHands.add(hand12);
		newHands.add(hand13);
		newHands.add(hand14);
		newHands.add(hand15);
		newHands.add(hand16);
		newHands.add(hand17);
		newHands.add(hand18);
		newHands.add(hand19);
		newHands.add(hand20);
		newHands.add(hand21);
		newHands.add(hand22);
		return newHands;
	}

	@Override
	public void onUseDestructPotion() 
	{
		this.possibleHands.get(3).clear();
		this.possibleHands.get(3).add("Beserker Crush");
		this.possibleHands.get(3).add("Lesser Dragon");
		this.possibleHands.get(3).add("Cave Dragon");
		this.possibleHands.get(3).add("Labyrinth Wall");
		this.possibleHands.get(3).add("Golden Apples");
	}

}
