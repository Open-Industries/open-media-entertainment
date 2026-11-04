package org.openindustries.media.functions.audience;

import io.quarkus.funqy.Funq;

import java.util.ArrayList;
import java.util.List;

/**
 * Serverless function (Knative / OpenShift Serverless).
 * Compares buffering events and completion rate against title targets; one finding per breach.
 * Compares measured values with targets and returns a verdict with one finding per
 * breach — machine-checkable assurance evidence.
 */
public class Function {

    public record Kpi(String assetId, int measuredBufferingEvents, int targetBufferingEvents,
                      double measuredCompletionRatePct, double targetCompletionRatePct) {
    }

    public record Verdict(String assetId, boolean compliant, List<String> findings) {
    }

    @Funq
    public Verdict audienceSlaEvaluator(Kpi kpi) {
        List<String> findings = new ArrayList<>();
        if (kpi.measuredBufferingEvents() > kpi.targetBufferingEvents()) {
            findings.add("bufferingEvents " + kpi.measuredBufferingEvents() + " exceeds target " + kpi.targetBufferingEvents());
        }
        if (kpi.measuredCompletionRatePct() < kpi.targetCompletionRatePct()) {
            findings.add("completionRatePct " + kpi.measuredCompletionRatePct() + " below target " + kpi.targetCompletionRatePct());
        }
        return new Verdict(kpi.assetId(), findings.isEmpty(), findings);
    }
}
