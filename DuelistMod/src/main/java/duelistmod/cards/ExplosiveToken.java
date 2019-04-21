package duelistmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.*;
import duelistmod.relics.MachineToken;

public class ExplosiveToken extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("ExplosiveToken");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.EXPLOSIVE_TOKEN);
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

    public ExplosiveToken() { super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); this.tags.add(Tags.BAD_TRIB); this.tags.add(Tags.TOKEN); this.purgeOnUse = true;}
    public ExplosiveToken(String tokenName) { super(ID, tokenName, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); this.tags.add(Tags.BAD_TRIB); this.tags.add(Tags.TOKEN); this.purgeOnUse = true;}
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(AbstractDungeon.player, 1, this); 
    }
    @Override public AbstractCard makeCopy() { return new ExplosiveToken(); }
	@Override public void onTribute(DuelistCard tributingCard) 
	{
		if (AbstractDungeon.player.hasRelic(MachineToken.ID))
		{
			int damageRoll = AbstractDungeon.cardRandomRng.random(1, 3);
			AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.getRandomMonster(), new DamageInfo(player(), damageRoll, damageTypeForTurn), AttackEffect.FIRE));
		}
		else
		{
			if (DuelistMod.challengeMode) 
			{ 
				int damageRoll = AbstractDungeon.cardRandomRng.random(1, 6);
				damageSelf(damageRoll); 
			}
			else 
			{ 
				int damageRoll = AbstractDungeon.cardRandomRng.random(1, 3);
				damageSelf(damageRoll); 
			}
		}
	}
	@Override public void onResummon(int summons) { }
	@Override public void summonThis(int summons, DuelistCard c, int var) { summon(AbstractDungeon.player, 1, this); }
	@Override public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) { summon(AbstractDungeon.player, 1, this); }
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