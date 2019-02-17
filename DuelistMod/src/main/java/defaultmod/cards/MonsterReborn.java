package defaultmod.cards;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;

public class MonsterReborn extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = defaultmod.DefaultMod.makeID("MonsterReborn");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.MONSTER_REBORN    );
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public MonsterReborn() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(DefaultMod.SPELL);
        this.exhaust = true;
		this.originalName = this.name;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	ArrayList<AbstractCard> discards = player().discardPile.group;
    	ArrayList<AbstractCard> toChooseFrom = new ArrayList<AbstractCard>();
    	for (AbstractCard c : discards) { if (c.tags.contains(DefaultMod.MONSTER) && !c.upgraded) { toChooseFrom.add(c); } }
    	if (toChooseFrom.size() > 0)
    	{
    		for (int i = 0; i < 5; i++)
    		{
	    		int randomAttack = ThreadLocalRandom.current().nextInt(0, toChooseFrom.size());
	    		AbstractCard chosen = toChooseFrom.get(randomAttack).makeStatEquivalentCopy();
	    		String cardName = chosen.originalName;
	    		System.out.println("theDuelist:MonsterReborn --- > Found: " + cardName);
	    		if (upgraded)
	    		{
	    			DuelistCard cardCopy = newCopyOfMonsterPumpkin(cardName);
	    			if (cardCopy != null)
	    			{
	    				if (!cardCopy.tags.contains(DefaultMod.TRIBUTE)) { cardCopy.misc = 52; }
	    				if (!chosen.upgraded) { cardCopy.upgrade(); }
	    				cardCopy.freeToPlayOnce = true;
	    				cardCopy.applyPowers();
	    				cardCopy.purgeOnUse = true;
	    				AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(cardCopy, m));
	    			}
	    		}
	    		else
	    		{
	    			DuelistCard cardCopy = newCopyOfMonsterPumpkin(cardName);
	    			if (cardCopy != null)
	    			{
	    				if (!cardCopy.tags.contains(DefaultMod.TRIBUTE)) { cardCopy.misc = 52; }
	    				cardCopy.freeToPlayOnce = true;
	    				cardCopy.applyPowers();
	    				cardCopy.purgeOnUse = true;
	    				if (chosen.upgraded) { cardCopy.upgrade(); }
	    				AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(cardCopy, m));
	    			}
	    		}
    		}
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new MonsterReborn();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}