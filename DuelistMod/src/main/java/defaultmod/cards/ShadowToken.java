package defaultmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import defaultmod.DefaultMod;
import defaultmod.orbs.Shadow;
import defaultmod.patches.*;

public class ShadowToken extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DefaultMod.makeID("ShadowToken");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.SHADOW_TOKEN);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final int COST = -1;
    // /STAT DECLARATION/

    public ShadowToken() { super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); this.tags.add(DefaultMod.GOOD_TRIB); this.tags.add(DefaultMod.TOKEN); }
    public ShadowToken(String tokenName) { super(ID, tokenName, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); this.tags.add(DefaultMod.GOOD_TRIB); this.tags.add(DefaultMod.TOKEN); }
    @Override public void use(AbstractPlayer p, AbstractMonster m) { }
    @Override public AbstractCard makeCopy() { return new ShadowToken(); }
    @Override public boolean canUse(AbstractPlayer p, AbstractMonster m) { return false; }
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
}