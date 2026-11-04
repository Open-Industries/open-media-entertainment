package org.openindustries.media.functions.piracy;

import io.quarkus.funqy.Funq;

/**
 * Serverless function (Knative / OpenShift Serverless).
 * Scores piracy risk signals (leaks, torrent activity, social spread) with an auditable rationale.
 * Transparent, auditable rules: NORMAL (< 35), WARNING (35-69), CRITICAL (>= 70);
 * the rationale is returned with the score for audit (ISO/IEC 27002 A.8.15).
 * Stateless by design (12-Factor VI): all state arrives in the request.
 */
public class Function {

    public record Event(String assetId, int leakIndex, int torrentIndex, int socialIndex) {
    }

    public record Score(String assetId, int score, String decision, String rationale) {
    }

    @Funq
    public Score piracyRiskScorer(Event event) {
        int score = 0;
        StringBuilder why = new StringBuilder();
        if (event.leakIndex() >= 70) {
            score += 40;
            why.append("leakIndex >= 70; ");
        }
        if (event.torrentIndex() >= 60) {
            score += 30;
            why.append("torrentIndex >= 60; ");
        }
        if (event.socialIndex() >= 50) {
            score += 30;
            why.append("socialIndex >= 50; ");
        }
        String decision = score >= 70 ? "CRITICAL" : score >= 35 ? "WARNING" : "NORMAL";
        return new Score(event.assetId(), score, decision,
                why.length() == 0 ? "no signals" : why.toString().trim());
    }
}
