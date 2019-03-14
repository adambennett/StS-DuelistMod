package defaultmod.archetypeCards;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import archetypeAPI.cards.AbstractArchetypeCard;
import archetypeAPI.patches.ArchetypeCardTags;
import defaultmod.DefaultMod;
import defaultmod.archetypeAPI.SpellsArchetypeConstructor;
import defaultmod.patches.AbstractCardEnum;

public class SpellsArchetype extends AbstractArchetypeCard 
{
    // TEXT DECLARATION
    public static final String ID = DefaultMod.makeID("SpellsArchetype");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.SPELLS_ARCH);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    // /STAT DECLARATION/

    public SpellsArchetype() {
        super(ID, NAME, IMG, DESCRIPTION, TYPE, COLOR);
        if (DefaultMod.isApi)
        {
        	tags.add(ArchetypeCardTags.SINGLE);
        }
        
        tags.add(DefaultMod.ARCHETYPE);
    }

	
	@Override @SuppressWarnings("unused")
	public void archetypeEffect() 
	{
		 SpellsArchetypeConstructor coolArchetype = new SpellsArchetypeConstructor ();
	}

	@Override
	public String getTooltipDesc() {
		return EXTENDED_DESCRIPTION[1];
	}

	@Override
	public String getTooltipName() {
		return EXTENDED_DESCRIPTION[0];
	}
}