# Audience SLA Evaluator (serverless function)

Quarkus Funqy function on Knative (scale to zero). Compares buffering events and completion rate against title targets; one finding per breach.

Invoke: `POST /audienceSlaEvaluator` with the KPI payload.
