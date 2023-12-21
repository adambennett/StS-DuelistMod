package duelistmod.cards.other.tempCards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.powers.incomplete.HauntedPower;

public class HauntPowers extends DuelistCard 
{
	
	// TEXT DECLARATION
	public static final String ID = DuelistMod.makeID("HauntPowers");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("HauntedShrine.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.BLUE;
    private static final int COST = -2;
    // /STAT DECLARATION/
    
    public HauntPowers(int magic)
    {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
       	this.purgeOnUse = true;
    	this.dontTriggerOnUseCard = true;
    	this.magicNumber = this.baseMagicNumber = magic;
		CommonKeywordIconsField.useIcons.set(this, false);
    }


    @Override public AbstractCard makeCopy() { return new HauntPowers(this.magicNumber); } 
    @Override public AbstractCard makeStatEquivalentCopy() { return new HauntPowers(this.magicNumber); }
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	this.addToBot(new VFXAction(p, new VerticalAuraEffect(Color.BLACK, p.hb.cX, p.hb.cY), 0.33f));
    	this.addToBot(new VFXAction(p, new VerticalAuraEffect(Color.PURPLE, p.hb.cX, p.hb.cY), 0.33f));
    	this.addToBot(new VFXAction(p, new VerticalAuraEffect(Color.CYAN, p.hb.cX, p.hb.cY), 0.0f));
    	this.addToBot(new VFXAction(p, new BorderLongFlashEffect(Color.BLUE), 0.0f, true));
    	applyPowerToSelf(new HauntedPower(p, p, this.magicNumber, CardType.POWER));
    }   
	
	@Override public void upgrade()  {}	
	
}
