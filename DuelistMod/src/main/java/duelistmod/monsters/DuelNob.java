package duelistmod.monsters;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType;
import com.megacrit.cardcrawl.powers.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.pools.machine.MeteorDestruction;
import duelistmod.cards.pools.zombies.MoltenZombie;
import duelistmod.powers.enemyPowers.EnemyMiraclePower;

public class DuelNob extends DuelistMonster
{
	
	public static final String ID = DuelistMod.makeID("DuelistNob"); // Monster ID (remember the prefix - yourModID:DefaultMonster)
	private static final MonsterStrings monsterstrings = CardCrawlGame.languagePack.getMonsterStrings(ID); // Grab the string

	public static final String NAME = monsterstrings.NAME; // The name of the monster
	public static final String[] MOVES = monsterstrings.MOVES; // The names of the moves
	public static final String[] DIALOG = monsterstrings.DIALOG; // The dialogue (if any)
	
	private static final float HB_X = 0.0F;     
	private static final float HB_Y = -25.0F; 
	private static final float HB_W = 200.0F;   
	private static final float HB_H = 330.0F;   
	
	private static final int HP_MIN = 82; 
	private static final int HP_MAX = 86; 
	private static final int A7_HP_MIN = 95; 
	private static final int A7_HP_MAX = 140;
	
    private boolean usedBellow;
    private boolean canVuln;	
	private DuelistCard molten;
	private DuelistCard meteor;
	private DuelistCard winged;
	private DuelistCard forbidden;
	
	public DuelNob(boolean setVuln)
	{
		super(NAME, ID, 50, HB_X, HB_Y, HB_W, HB_H, "DuelistNob", HP_MIN, HP_MAX, A7_HP_MIN, A7_HP_MAX);
		
		// Duelist Setup
		this.setHands(initHands());
		this.startingToken = "Spire Token";
		//this.lowWeightHandIndex = 7;
		this.molten = new MoltenZombie();
		this.winged = new WingedKuriboh();
		this.meteor = new MeteorDestruction();
		this.forbidden = new ForbiddenLance();
		
		// Nob Setup
		this.usedBellow = false;
        this.intentOffsetX = -30.0f * Settings.scale;
        this.type = EnemyType.ELITE;
        this.dialogX = -60.0f * Settings.scale;
        this.dialogY = 50.0f * Settings.scale;
        this.canVuln = setVuln;
        
        // HP Modification
        if (AbstractDungeon.ascensionLevel >= 8) {
            this.setHp(85, 90);
        }
        else {
            this.setHp(82, 86);
        }
        this.loadAnimation("duelistModResources/images/monsters/DuelistNob/skeleton.atlas", "duelistModResources/images/monsters/DuelistNob/skeleton.json", 1.0f);
		final AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
		e.setTime(e.getEndTime() * MathUtils.random());
		//this.deathSound = "coolKaibaSound";
	}
	
	public DuelNob() 
	{
		this(true);
	}
	
	@Override
	protected ArrayList<AbstractCard> getHandMove()
	{
		ArrayList<AbstractCard> moveCards = new ArrayList<AbstractCard>();
		int summons = getSummons();
		//boolean hasLambs = hasLambs();
		switch (this.handIndex)
		{
			// Anger hand
			case 2:
	            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DuelistNob.DIALOG[0], 1.0f, 3.0f));
	            if (AbstractDungeon.ascensionLevel >= 18) {
	                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new AngerPower(this, 3), 3));
	                break;
	            }
	            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new AngerPower(this, 2), 2));
				break;
				
			// Damage hand - 6 dmg (8 dmg on A3+)
			case 1:
				 AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
	             AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
	             if (this.canVuln) {
	                 AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 2, true), 2));
	                 break;
	             }
				break;
			
			// Damage hand - 14 dmg (16 dmg on A3+)
			case 0:						
				 AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
	             AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
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
	
	private ArrayList<ArrayList<String>> initHands(boolean usedAnger)
	{
		ArrayList<ArrayList<String>> newHands = new ArrayList<ArrayList<String>>();
		ArrayList<String> hand0 = new ArrayList<String>();
		ArrayList<String> hand1 = new ArrayList<String>();
		ArrayList<String> hand2 = new ArrayList<String>();
		if (usedAnger)
		{
			hand0.add("Meteor Destruction");
			hand0.add("Castle Walls");
			hand0.add("Obelisk");
			hand0.add("Reinforcements");
			hand0.add("");    	

			hand1.add("Molten Zombie");
			hand1.add("Castle Walls");
			hand1.add("Trip");
			hand1.add("Flute Kuriboh"); 
			hand1.add("Red Medicine");
			
			hand2.add("Dark Cubic Lord");
			hand2.add("Winged Kuriboh");
			hand2.add("Castle Walls");
			hand2.add("Summoned Skull"); 
			hand2.add("Giant Steel Soldier");
		}
		else
		{
			hand0.add("Meteor Destruction");
			hand0.add("Castle Walls");
			hand0.add("Obelisk");
			hand0.add("Reinforcements");
			hand0.add("");    	

			hand1.add("Molten Zombie");
			hand1.add("Castle Walls");
			hand1.add("Trip");
			hand1.add("Flute Kuriboh"); 
			hand1.add("Red Medicine");
			
			hand2.add("Overworked");
			hand2.add("Winged Kuriboh");
			hand2.add("Castle Walls");
			hand2.add("Summoned Skull"); 
			hand2.add("Giant Steel Soldier");	
		}

		newHands.add(hand0);
		newHands.add(hand1);
		newHands.add(hand2);
		return newHands;
	}
	
	// Meteor Destruction : 16 dmg
    // Molten Zombie : 8 dmg
    // Winged Kuriboh : 6 dmg
    // Forbidden Lance : 14 dmg
    // Dark Cubic Lord : trib 7, gain 9 strength
	
	private ArrayList<ArrayList<String>> initHands()
	{
		return this.initHands(false);
	}

	@Override
	public void onUseDestructPotion() {
		// TODO Auto-generated method stub
		
	}
}
