Bank Management System – Testing av Programvare
Prosjektoversikt
Dette prosjektet er en bankadministrasjonsapplikasjon som ble utviklet som en del av faget "Testing av programvare." Hovedfokuset var å utføre omfattende testing, inkludert enhetstesting, integrasjonstesting og systemtesting. Applikasjonen lar bankkunder håndtere sine kontoer, betalinger og personlig informasjon, mens administratorer kan administrere kundedata og kontotyper.

Testverktøy
For å sikre grundig testing av systemet ble flere verktøy brukt:

Enhetstesting: JUnit i IntelliJ IDEA
Integrasjonstesting: SoapUI
Systemtesting: Selenium IDE for nettlesertesting
Testadministrasjon: Microsoft Test Manager (MTM)
Brukerhistorier
Bankkunde:
Se alle kontoer og saldoer.
Se oversikt over siste transaksjoner.
Registrere en betaling.
Endre personlig informasjon.
Administrator:
Hente alle kunder fra databasen.
Endre kundens informasjon.
Registrere en ny kunde.
Slette en kunde eller en kontotype.
Testing
Enhetstesting: JUnit ble brukt til å teste funksjoner som registerKonto(), endreKonto(), slettKonto(), samt flere sikkerhetsfunksjoner. Alle testene bestod som forventet.
Integrasjonstesting: SoapUI ble brukt til å teste integrasjonen av systemets API-er.
Systemtesting: Selenium IDE ble brukt til å automatisere brukerinteraksjoner. Testresultatene ble samlet og administrert via Microsoft Test Manager (MTM) for konsolidering av testresultater fra ulike verktøy.
Rapport
En detaljert testprosess og resultater finnes i docs/rapport-Full.pdf.

Lærdom
Testprosessen understreket viktigheten av tidlig testing i utviklingen for å unngå feil, samt hvor effektivt verktøy som JUnit, SoapUI, Selenium og MTM var for å forbedre kvaliteten på produktet.
