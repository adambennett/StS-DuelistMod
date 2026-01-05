package duelistmod.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import duelistmod.abstracts.MagnetCard;
import duelistmod.helpers.MagnetTransformHelper;

public class RetryMagnetReplaceAction extends AbstractGameAction {
    private final MagnetCard original;
    private final MagnetCard replacement;

    private int remainingAttempts;
    private float delaySeconds;
    private final float backoffMultiplier;
    private final float maxDelaySeconds;

    public RetryMagnetReplaceAction(
            MagnetCard original,
            MagnetCard replacement,
            int attempts,
            float initialDelaySeconds,
            float backoffMultiplier,
            float maxDelaySeconds
    ) {
        this.original = original;
        this.replacement = replacement;
        this.remainingAttempts = Math.max(1, attempts);
        this.delaySeconds = Math.max(0f, initialDelaySeconds);
        this.backoffMultiplier = Math.max(1f, backoffMultiplier);
        this.maxDelaySeconds = Math.max(0f, maxDelaySeconds);
    }

    @Override
    public void update() {
        // Wait until our delay expires
        if (delaySeconds > 0f) {
            delaySeconds -= com.badlogic.gdx.Gdx.graphics.getDeltaTime();
            return;
        }

        // Try replace
        boolean replaced = MagnetTransformHelper.replaceInAllPlayerGroups(original, replacement);
        MagnetTransformHelper.replaceInCardPopupIfViewing(original, replacement);

        if (replaced) {
            isDone = true;
            return;
        }

        // Not replaced; decrement and reschedule delay if we have attempts left
        remainingAttempts--;
        if (remainingAttempts <= 0) {
            isDone = true; // give up safely
            return;
        }

        // Backoff
        delaySeconds = Math.min(
                (delaySeconds <= 0f ? 0.02f : delaySeconds) * backoffMultiplier,
                maxDelaySeconds
        );

        // Also: small minimum delay so we don't spin same frame
        if (delaySeconds < 0.02f) delaySeconds = 0.02f;
    }
}
