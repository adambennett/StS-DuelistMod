package defaultmod.cards;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.ReflectionHacks;
import defaultmod.DefaultMod;
import defaultmod.patches.*;
import defaultmod.powers.*;

public class CocoonEvolution extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DefaultMod.makeID("CocoonEvolution");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.COCOON_EVOLUTION);
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
    private ArrayList<AbstractCard> tooltips;
    // /STAT DECLARATION/

    public CocoonEvolution() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = 4;
        this.tributes = 2;
        this.tags.add(DefaultMod.MONSTER);
        this.tags.add(DefaultMod.ALL);
        this.tags.add(DefaultMod.COCOON);
        this.tags.add(DefaultMod.METAL_RAIDERS);
        this.originalName = this.name;
        this.misc = 0;
        tooltips = new ArrayList<>();
        tooltips.add(new PetitRef());
		tooltips.add(new GreatRef());
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute(p, this.tributes, false, this);
    	block(this.block);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new CocoonEvolution();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(4);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    // If player doesn't have enough summons, can't play card
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
    	// Check super canUse()
    	boolean canUse = super.canUse(p, m); 
    	if (!canUse) { return false; }
    	
    	// Pumpking & Princess
  		else if (this.misc == 52) { return true; }
    	
    	// Mausoleum check
    	else if (p.hasPower(EmperorPower.POWER_ID))
		{
			EmperorPower empInstance = (EmperorPower)p.getPower(EmperorPower.POWER_ID);
			if (!empInstance.flag)
			{
				return true;
			}
			
			else
			{
				if (p.hasPower(SummonPower.POWER_ID)) { int temp = (p.getPower(SummonPower.POWER_ID).amount); if (temp >= this.tributes) { return true; } }
			}
		}
    	
    	// Check for # of summons >= tributes
    	else { if (p.hasPower(SummonPower.POWER_ID)) { int temp = (p.getPower(SummonPower.POWER_ID).amount); if (temp >= this.tributes) { return true; } } }
    	
    	// Player doesn't have something required at this point
    	this.cantUseMessage = "Not enough Summons";
    	return false;
    }
    
    @Override
   	public void renderCardTip(SpriteBatch sb) {
   		super.renderCardTip(sb);
   		boolean renderTip = (boolean) ReflectionHacks.getPrivate(this, AbstractCard.class, "renderTip");

   		int count = 0;
   		if (!Settings.hideCards && renderTip) {
   			if (AbstractDungeon.player != null && AbstractDungeon.player.isDraggingCard) {
   				return;
   			}
   			for (AbstractCard c : tooltips) {
   				float dx = (AbstractCard.IMG_WIDTH * 0.9f - 5f) * drawScale;
   				float dy = (AbstractCard.IMG_HEIGHT * 0.4f - 5f) * drawScale;
   				if (current_x > Settings.WIDTH * 0.75f) {
   					c.current_x = current_x + dx;
   				} else {
   					c.current_x = current_x - dx;
   				}
   				if (count == 0) {
   					c.current_y = current_y + dy;
   				} else {
   					c.current_y = current_y - dy;
   				}
   				c.drawScale = drawScale * 0.8f;
   				c.render(sb);
   				count++;
   			}
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