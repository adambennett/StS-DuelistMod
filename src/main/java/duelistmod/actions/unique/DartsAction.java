package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.orbs.*;
import duelistmod.abstracts.*;
import duelistmod.orbs.*;

import java.util.*;

public class DartsAction extends AbstractGameAction
{
	private int magicNumber = 0;
	private AbstractMonster lastMon;
	private final DuelistCard card;
	private final String orb;

	public DartsAction(DuelistCard cardRef, String orb) {
		this(cardRef.magicNumber, cardRef, null, orb);
	}

	public DartsAction(int magicNumber, DuelistCard cardRef, AbstractMonster lastMon, String orb) {
		this.actionType = ActionType.DAMAGE;
		this.duration = 0.1F;
		this.magicNumber = magicNumber;
		this.card = cardRef;
		this.lastMon = lastMon;
		this.orb = orb;
	}

	public void update() {
		if (this.magicNumber > 0) {
			ArrayList<AbstractMonster> mons = DuelistCard.getAllMons();
			if (mons.size() > 0) {
				AbstractMonster target = mons.get(AbstractDungeon.cardRandomRng.random(mons.size() - 1));
				this.card.attack(target);
				if (this.lastMon != null && target != this.lastMon) {
					channel();
				}
				this.lastMon = target;
				this.magicNumber--;
				if (this.magicNumber > 0) {
					mons = DuelistCard.getAllMons();
					if (mons.size() > 0) {
						DartsAction next = new DartsAction(this.magicNumber, this.card, target, this.orb);
						this.addToBot(next);
					}
				}
			}
		}
		this.isDone = true;
	}

	private void channel() {
		switch (this.orb) {
			case "Fire": DuelistCard.channelBottom(new FireOrb()); break;
			case "Lightning": DuelistCard.channelBottom(new Lightning()); break;
			default: break;
		}
	}
}
