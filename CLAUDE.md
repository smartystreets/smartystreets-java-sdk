# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
make test              # Run unit tests (mvn test)
make integration-test  # Run integration tests (mvn integration-test)
make compile           # Clean, test, then compile
make clean             # Clean and reset version files
make publish           # Full publish workflow with version updates
```

Run a single test class:
```bash
mvn test -Dtest=ClientTest
```

Run a single test method:
```bash
mvn test -Dtest=ClientTest#testMethodName
```

## Architecture

This is the official SmartyStreets Java SDK for address validation and geocoding APIs. Requires Java 11+.

### Sender Chain (Decorator Pattern)

HTTP requests flow through a chain of decorators, each implementing `Sender`:

```
RetrySender → LicenseSender → URLPrefixSender → SigningSender →
CustomQuerySender → CustomHeaderSender → StatusCodeSender → SmartySender
```

- `SmartySender`: OkHttp3-based HTTP client
- `StatusCodeSender`: Maps HTTP status codes to exceptions (401→BadCredentialsException, etc.)
- `RetrySender`: Exponential backoff, handles 429 rate limiting
- `SigningSender`: Adds authentication headers

### API Module Pattern

Each API (us_street, us_zipcode, international_street, etc.) follows the same structure:
- `Client.java`: Sends lookups and populates results
- `Lookup.java`: Input container for address/parameters
- `Batch.java`: Container for 1-100 lookups (batch operations)
- `Candidate.java` or `Result.java`: Output container with validated data

### Key Entry Points

- `ClientBuilder.java`: Fluent builder for creating API clients
- `src/main/java/com/smartystreets/api/{api_name}/Client.java`: Per-API client implementations
- `src/main/java/examples/`: Usage examples for each API

### Credentials

- `StaticCredentials`: Auth ID + Auth Token (server-to-server)
- `SharedCredentials`: Web Token + Referer (browser/mobile)

### Test Mocks

Test doubles are in `src/test/java/com/smartystreets/api/mocks/`:
- `MockSender`, `RequestCapturingSender`: Capture and verify requests
- `FakeSerializer`, `FakeSleeper`, `FakeLogger`: Stub implementations

## Supported APIs

- US Street Address (`us_street/`)
- US ZIP Code (`us_zipcode/`)
- US Reverse Geo (`us_reverse_geo/`)
- US Autocomplete Pro (`us_autocomplete_pro/`)
- US Extract (`us_extract/`)
- US Enrichment (`us_enrichment/`)
- International Street (`international_street/`)
- International Autocomplete (`international_autocomplete/`)
- International Postal Code (`international_postal_code/`)
