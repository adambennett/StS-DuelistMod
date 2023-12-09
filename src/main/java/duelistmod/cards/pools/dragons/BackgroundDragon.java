package duelistmod.cards.pools.dragons;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

import java.util.List;

public class BackgroundDragon extends DuelistCard 
{
    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("BackgroundDragon");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("BackgroundDragon.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public BackgroundDragon() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = 14;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.DRAGON);
        this.tributes = this.baseTributes = 3;
        this.magicNumber = this.baseMagicNumber = 3;	// block on dragon summon
		this.originalName = this.name;
        this.enemyIntent = AbstractMonster.Intent.DEFEND;
    }
    
    @Override
    public void onSummonWhileInHand(DuelistCard c, int amt)
    {
    	if (c.hasTag(Tags.DRAGON) && amt > 0)
    	{
    		block(this.magicNumber);
    	}
    }
    
    @Override
    public void onSummonWhileInDiscard(DuelistCard c, int amt)
    {
    	if (c.hasTag(Tags.DRAGON) && amt > 0 && this.upgraded)
    	{
    		block(this.magicNumber);
    	}
    }
    
    @Override
    public void onSummonWhileInDraw(DuelistCard c, int amt)
    {
    	if (c.hasTag(Tags.DRAGON) && amt > 0 && this.upgraded)
    	{
    		block(this.magicNumber);
    	}
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        tribute();
        block();
        postDuelistUseCard(owner, targets);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new BackgroundDragon();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

	





	




	



}
