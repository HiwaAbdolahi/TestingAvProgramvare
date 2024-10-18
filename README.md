# Bank Management System – Testing av Programvare

## Prosjektoversikt

Dette prosjektet er en bankadministrasjonsapplikasjon som ble utviklet som en del av faget "Testing av programvare." Hovedfokuset var å utføre omfattende testing, inkludert enhetstesting, integrasjonstesting og systemtesting. Applikasjonen lar bankkunder håndtere kontoer, betalinger og personlig informasjon, mens administratorer kan administrere kundedata og kontotyper.

## Testverktøy

For å sikre grundig testing av systemet ble flere verktøy brukt:

- **Enhetstesting**: JUnit i IntelliJ IDEA
- **Integrasjonstesting**: SoapUI
- **Systemtesting**: Selenium IDE for nettlesertesting
- **Testadministrasjon**: Microsoft Test Manager (MTM)

## Brukerhistorier

### Bankkunde:
1. Se alle kontoer og saldoer.
2. Se oversikt over siste transaksjoner.
3. Registrere en betaling.
4. Endre personlig informasjon.

### Administrator:
1. Hente alle kunder fra databasen.
2. Endre kundens informasjon.
3. Registrere en ny kunde.
4. Slette en kunde eller en kontotype.

## Testing

1. **Enhetstesting**: JUnit ble brukt til å teste funksjoner som `registerKonto()`, `endreKonto()`, `slettKonto()`, samt flere sikkerhetsfunksjoner. Alle testene bestod som forventet.
2. **Integrasjonstesting**: SoapUI ble brukt til å teste integrasjonen av systemets API-er.
3. **Systemtesting**: Selenium IDE ble brukt til å automatisere brukerinteraksjoner. Testresultatene ble samlet og administrert via **Microsoft Test Manager (MTM)** for konsolidering av testresultater fra ulike verktøy.

## Rapport

En detaljert testprosess og resultater finnes i [docs/rapport-Full.pdf](docs/rapport-Full.pdf).

## Lærdom

Testprosessen understreket viktigheten av tidlig testing i utviklingen for å unngå feil, samt hvor effektivt verktøy som JUnit, SoapUI, Selenium og MTM var for å forbedre kvaliteten på produktet.
