package duelistmod.cards.tokens;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import duelistmod.*;
import duelistmod.interfaces.*;
import duelistmod.orbs.Shadow;
import duelistmod.patches.AbstractCardEnum;

public class ShadowToken extends TokenCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("ShadowToken");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.SHADOW_TOKEN);
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

    public ShadowToken() { super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); this.tags.add(Tags.GOOD_TRIB); this.tags.add(Tags.TOKEN); this.purgeOnUse = true; this.isEthereal = true;}
    public ShadowToken(String tokenName) { super(ID, tokenName, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); this.tags.add(Tags.GOOD_TRIB); this.tags.add(Tags.TOKEN); this.purgeOnUse = true; this.isEthereal = true;}
    @Override public void use(AbstractPlayer p, AbstractMonster m) { summon(AbstractDungeon.player, 1, this); }
    @Override public AbstractCard makeCopy() { return new ShadowToken(); }
    
    @Override 
    public void customOnTribute(DuelistCard tc)
    {
    	ArrayList<AbstractOrb> orbList = AbstractDungeon.player.orbs;
		for (AbstractOrb o : orbList)
		{
			if (o instanceof Shadow)
			{
				Shadow shadowRef = (Shadow)o;
				shadowRef.tribShadowToken();
			}
		}
    }
    
	@Override public void onTribute(DuelistCard tributingCard) 
	{
		ArrayList<AbstractOrb> orbList = AbstractDungeon.player.orbs;
		for (AbstractOrb o : orbList)
		{
			if (o instanceof Shadow)
			{
				Shadow shadowRef = (Shadow)o;
				shadowRef.tribShadowToken();
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