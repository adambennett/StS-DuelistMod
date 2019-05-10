package duelistmod.cards.incomplete;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.actions.common.*;
import duelistmod.cards.tokens.KuribohToken;
import duelistmod.cards.typecards.*;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.AbstractCardEnum;

public class WingedKuriboh9 extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("WingedKuribohLv9");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("WingedKuribohLv9.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public WingedKuriboh9() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseBlock = this.block = 8;
        this.summons = this.baseSummons = 1;
        this.baseMagicNumber = this.magicNumber = 1;
        this.isSummon = true;
        this.tags.add(Tags.MONSTER);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(p, 1, new KuribohToken());
    	block(this.block);
    	ArrayList<DuelistCard> types = new ArrayList<DuelistCard>();
    	types.add(new AquaDrawTypeCard(this.magicNumber));
    	types.add(new DragonDrawTypeCard(this.magicNumber));
    	types.add(new FiendDrawTypeCard(this.magicNumber));
    	types.add(new InsectDrawTypeCard(this.magicNumber));
    	types.add(new MachineDrawTypeCard(this.magicNumber));
    	types.add(new NaturiaDrawTypeCard(this.magicNumber));
    	types.add(new PlantDrawTypeCard(this.magicNumber));
    	types.add(new PredaplantDrawTypeCard(this.magicNumber));
    	types.add(new SpellcasterDrawTypeCard(this.magicNumber));
    	types.add(new SuperheavyDrawTypeCard(this.magicNumber));
    	types.add(new ToonDrawTypeCard(this.magicNumber));
    	types.add(new ZombieDrawTypeCard(this.magicNumber));
    	AbstractDungeon.actionManager.addToTop(new CardSelectScreenResummonAction(types, 1, false, false, false));
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new WingedKuriboh9();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!this.upgraded) 
        {
        	this.upgradeName();
        	this.upgradeBlock(4);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) 
	{
		
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}