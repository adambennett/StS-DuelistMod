package duelistmod.cards.tokens;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.interfaces.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.relics.MachineToken;

public class SuperExplodingToken extends TokenCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("SuperExplodingToken");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("SuperExplodingToken.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = 0;
    // /STAT DECLARATION/

    public SuperExplodingToken() 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.BAD_TRIB); 
    	this.tags.add(Tags.TOKEN); 
    	this.tags.add(Tags.SUPER_EXPLODING_TOKEN);
    	this.purgeOnUse = true; 
    	this.isEthereal = true;
    	this.summons = this.baseSummons = 1;
    	this.isSummon = true;
    }
    public SuperExplodingToken(String tokenName) { super(ID, tokenName, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); this.tags.add(Tags.BAD_TRIB); this.tags.add(Tags.TOKEN); this.tags.add(Tags.SUPER_EXPLODING_TOKEN); this.purgeOnUse = true; this.isEthereal = true; this.summons = this.baseSummons = 2; this.isSummon = true;}
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(AbstractDungeon.player, this.summons, this); 
    }
    @Override public AbstractCard makeCopy() { return new SuperExplodingToken(); }
    
    @Override
    public void customOnTribute(DuelistCard tc)
    {
    	if (AbstractDungeon.player.hasRelic(MachineToken.ID))
		{
			int damageRoll = AbstractDungeon.cardRandomRng.random(DuelistMod.explosiveDmgLow * 2, DuelistMod.explosiveDmgHigh * 3);
			AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.getRandomMonster(), new DamageInfo(player(), damageRoll, damageTypeForTurn), AttackEffect.FIRE));
		}
		else
		{
			
			int damageRoll = AbstractDungeon.cardRandomRng.random(DuelistMod.explosiveDmgLow * 2, DuelistMod.explosiveDmgHigh * 3);
			damageSelf(damageRoll); 
			
		}
    }
    
	@Override public void onTribute(DuelistCard tributingCard) 
	{
		machineSynTrib(tributingCard);
		if (AbstractDungeon.player.hasRelic(MachineToken.ID))
		{
			int damageRoll = AbstractDungeon.cardRandomRng.random(2, 6);
			AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.getRandomMonster(), new DamageInfo(player(), damageRoll, damageTypeForTurn), AttackEffect.FIRE));
		}
		else
		{
			if (DuelistMod.challengeMode) 
			{ 
				int damageRoll = AbstractDungeon.cardRandomRng.random(2, 9);
				damageSelf(damageRoll); 
			}
			else 
			{ 
				int damageRoll = AbstractDungeon.cardRandomRng.random(2, 6);
				damageSelf(damageRoll); 
			}
		}
	}
	@Override public void onResummon(int summons) { }
	@Override public void summonThis(int summons, DuelistCard c, int var) {  }
	@Override public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {  }
	@Override public void upgrade() {}
	@Override
	public String getID() {
		return ID;
	}
	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}