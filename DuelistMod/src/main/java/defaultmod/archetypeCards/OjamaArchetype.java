package defaultmod.archetypeCards;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import archetypeAPI.cards.AbstractArchetypeCard;
import archetypeAPI.patches.ArchetypeCardTags;
import defaultmod.DefaultMod;
import defaultmod.archetypeAPI.OjamaArchetypeConstructor;
import defaultmod.patches.AbstractCardEnum;

public class OjamaArchetype extends AbstractArchetypeCard 
{
    // TEXT DECLARATION
    public static final String ID = DefaultMod.makeID("OjamaArchetype");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.OJAMA_ARCH);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    // /STAT DECLARATION/

    public OjamaArchetype() {
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
		 OjamaArchetypeConstructor coolArchetype = new OjamaArchetypeConstructor ();
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