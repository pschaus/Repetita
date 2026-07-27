# Agent Guidelines

## Pre-Commit Requirements
- Do **not** make any git commit without ensuring that all tests pass first.
- Before committing any changes, run the test suite (e.g., `sbt test`) and verify that all tests complete with zero failures.

## Handling Test Failures
- Do **not** modify, alter, or delete failing tests to make them pass.
- Focus strictly on investigating and fixing the logical bugs in the core code to resolve test failures.

