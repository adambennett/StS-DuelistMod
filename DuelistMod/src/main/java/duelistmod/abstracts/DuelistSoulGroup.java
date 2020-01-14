package duelistmod.abstracts;

import java.util.*;

import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.characters.TheDuelist;

public class DuelistSoulGroup
{
    private static final Logger logger;
    public ArrayList<DuelistSoul> souls;
    //private static final int DEFAULT_SOUL_CACHE = 20;
    private final AbstractMonster target;
    
    public DuelistSoulGroup(AbstractMonster target) {
        this.souls = new ArrayList<DuelistSoul>();
        this.target = target;
        for (int i = 0; i < 20; ++i) {
            this.souls.add(new DuelistSoul(target));
        }
    }
    
    public void discard(final AbstractCard card, final boolean visualOnly) {
        boolean needMoreSouls = true;
        for (final DuelistSoul s : this.souls) {
            if (s.isReadyForReuse) {
                card.untip();
                card.unhover();
                s.discard(card, visualOnly);
                needMoreSouls = false;
                break;
            }
        }
        if (needMoreSouls) {
            DuelistSoulGroup.logger.info("Not enough souls, creating...");
            final DuelistSoul s2 = new DuelistSoul(target);
            s2.discard(card, visualOnly);
            this.souls.add(s2);
        }
    }
    
    public void discard(final AbstractCard card) {
        this.discard(card, false);
    }
    
    public void empower(final AbstractCard card) {
        boolean needMoreSouls = true;
        for (final DuelistSoul s : this.souls) {
            if (s.isReadyForReuse) {
                card.untip();
                card.unhover();
                s.empower(card);
                needMoreSouls = false;
                break;
            }
        }
        if (needMoreSouls) {
            DuelistSoulGroup.logger.info("Not enough souls, creating...");
            final DuelistSoul s2 = new DuelistSoul(target);
            s2.empower(card);
            this.souls.add(s2);
        }
    }
    
    public void empowerResummon(final AbstractCard card) {
        boolean needMoreSouls = true;
        for (final DuelistSoul s : this.souls) {
            if (s.isReadyForReuse) {
                card.untip();
                card.unhover();
                s.empowerResummon(card);
                needMoreSouls = false;
                break;
            }
        }
        if (needMoreSouls) {
            DuelistSoulGroup.logger.info("Not enough souls, creating...");
            final DuelistSoul s2 = new DuelistSoul(target);
            s2.empowerResummon(card);
            this.souls.add(s2);
        }
    }
    
    public void obtain(final AbstractCard card, final boolean obtainCard) {
        CardCrawlGame.sound.play("CARD_OBTAIN");
        boolean needMoreSouls = true;
        for (final DuelistSoul s : this.souls) {
            if (s.isReadyForReuse) {
                if (obtainCard) {
                    s.obtain(card);
                }
                needMoreSouls = false;
                break;
            }
        }
        if (needMoreSouls) {
            DuelistSoulGroup.logger.info("Not enough souls, creating...");
            final DuelistSoul s2 = new DuelistSoul(target);
            if (obtainCard) {
                s2.obtain(card);
            }
            this.souls.add(s2);
        }
    }
    
    public void shuffle(final AbstractCard card, final boolean isInvisible) {
        card.untip();
        card.unhover();
        card.darken(true);
        card.shrink(true);
        boolean needMoreSouls = true;
        for (final DuelistSoul s : this.souls) {
            if (s.isReadyForReuse) {
                s.shuffle(card, isInvisible);
                needMoreSouls = false;
                break;
            }
        }
        if (needMoreSouls) {
            DuelistSoulGroup.logger.info("Not enough souls, creating...");
            final DuelistSoul s2 = new DuelistSoul(target);
            s2.shuffle(card, isInvisible);
            this.souls.add(s2);
        }
    }
    
    public void onToBottomOfDeck(final AbstractCard card) {
        boolean needMoreSouls = true;
        for (final DuelistSoul s : this.souls) {
            if (s.isReadyForReuse) {
                card.untip();
                card.unhover();
                s.onToBottomOfDeck(card);
                needMoreSouls = false;
                break;
            }
        }
        if (needMoreSouls) {
            DuelistSoulGroup.logger.info("Not enough souls, creating...");
            final DuelistSoul s2 = new DuelistSoul(target);
            s2.onToBottomOfDeck(card);
            this.souls.add(s2);
        }
    }
    
    public void onToDeck(final AbstractCard card, final boolean randomSpot, final boolean visualOnly) {
        boolean needMoreSouls = true;
        for (final DuelistSoul s : this.souls) {
            if (s.isReadyForReuse) {
                card.untip();
                card.unhover();
                s.onToDeck(card, randomSpot, visualOnly);
                needMoreSouls = false;
                break;
            }
        }
        if (needMoreSouls) {
            DuelistSoulGroup.logger.info("Not enough souls, creating...");
            final DuelistSoul s2 = new DuelistSoul(target);
            s2.onToDeck(card, randomSpot, visualOnly);
            this.souls.add(s2);
        }
    }
    
    public void onToDeck(final AbstractCard card, final boolean randomSpot) {
        this.onToDeck(card, randomSpot, false);
    }
    
    public void update() {
        for (final DuelistSoul s : this.souls) {
            if (!s.isReadyForReuse) {
                s.update();
            }
        }
    }
    
    public void render(final SpriteBatch sb) {
        for (final DuelistSoul s : this.souls) {
            if (!s.isReadyForReuse) {
                s.render(sb);
            }
        }
    }
    
    public void remove(final AbstractCard card) {
        final Iterator<DuelistSoul> s = this.souls.iterator();
        while (s.hasNext()) {
            final DuelistSoul derp = s.next();
            if (derp.card == card) {
                s.remove();
                DuelistSoulGroup.logger.info(derp + " removed.");
                break;
            }
        }
    }
    
    public static boolean isActive() {
        for (final DuelistSoul s : TheDuelist.duelistSouls.souls) {
            if (!s.isReadyForReuse) {
                return true;
            }
        }
        return false;
    }
    
    static {
        logger = DuelistMod.logger;
    }
}
