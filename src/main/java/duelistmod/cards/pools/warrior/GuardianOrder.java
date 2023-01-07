package duelistmod.cards.pools.warrior;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.rooms.AbstractRoom;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class GuardianOrder extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("GuardianOrder");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GuardianOrder.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public GuardianOrder() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseBlock = this.block = 0;
        this.baseMagicNumber = this.magicNumber = 3;
        this.baseTributes = this.tributes = 3;
        this.misc = 0;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.WARRIOR);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) 
    {
    	tribute();
        this.addToBot(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void applyPowers() {
        int minus = this.misc == 52 ? 0 : 1;
        int standardVal = (AbstractDungeon.player.hand.size() - minus) * this.magicNumber;
        this.block = this.baseBlock = standardVal;
        super.applyPowers();
        int diff = this.block - standardVal;
        this.block = ((AbstractDungeon.player.hand.size() - minus) * this.magicNumber) + diff;
        this.isBlockModified = this.block != standardVal;
        this.initializeDescription();
    }

    @Override
    public void update() {
        super.update();
        if (AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom().phase.equals(AbstractRoom.RoomPhase.COMBAT)) {
            this.applyPowers();
        }
        this.fixUpgradeDesc();
        this.initializeDescription();
    }

    
    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!upgraded) 
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	this.upgradeTributes(2);
        	this.upgradeMagicNumber(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
			
	}

	@Override
	public void onResummon(int summons) 
	{
		
		
	}

	@Override
	public String getID() { return ID; }
	
	@Override
    public AbstractCard makeCopy() { return new GuardianOrder(); }
	public void summonThis(int summons, DuelistCard c, int var) {}
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {}
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {}
}
