package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKundeController {

    @InjectMocks
    // denne skal testes
    private AdminKundeController adminKundeController;

    @Mock
    // denne skal Mock'es
    private AdminRepository adminRepository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;


    @Test
    public void hentAlle_loggetInn() {
        // Arrange
        List<Kunde> kunder = new ArrayList<>();
        kunder.add(new Kunde("1234523543","Ole", "Navn342", "Adresse1","2020", "12345678","","Admin"));
        kunder.add(new Kunde("1231354325","Per", "Navn23", "Adresse1","1111", "12345678","","Admin"));
        when(sjekk.loggetInn()).thenReturn("Admin1");
        when(adminRepository.hentAlleKunder()).thenReturn(kunder);

        // Act
        List<Kunde> result = adminKundeController.hentAlle();

        // Assert
        assertEquals(kunder, result);
        verify(sjekk, times(1)).loggetInn();
        verify(adminRepository, times(1)).hentAlleKunder();
    }

    @Test
    public void hentAlle_ikkeLoggetInn() {
        // Arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        List<Kunde> result = adminKundeController.hentAlle();

        // Assert
        assertNull(result);
        verify(sjekk, times(1)).loggetInn();
        verify(adminRepository, times(0)).hentAlleKunder();
    }


    @Test
    public void lagreKunde_LoggetInn() {
        // Arrange
        Kunde kunde = new Kunde("01010112345", "Ola", "Nordmann", "Osloveien 82",
                "0270", "Oslo", "12345678", "hemmelig");
        when(sjekk.loggetInn()).thenReturn("01010112345");
        when(adminRepository.registrerKunde(kunde)).thenReturn("Kunde lagret");

        // Act
        String resultat = adminKundeController.lagreKunde(kunde);

        // Assert
        assertEquals("Kunde lagret", resultat);
        verify(sjekk, times(1)).loggetInn();
        verify(adminRepository, times(1)).registrerKunde(kunde);
    }

    @Test
    public void lagreKunde_IkkeLoggetInn() {
        // Arrange
        Kunde kunde = new Kunde("01010112345", "Ola", "Nordmann", "Osloveien 82",
                "0270", "Oslo", "12345678", "hemmelig");
        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        String resultat = adminKundeController.lagreKunde(kunde);

        // Assert
        assertEquals("Ikke logget inn", resultat);
        verify(sjekk, times(1)).loggetInn();
        verify(adminRepository, never()).registrerKunde(any(Kunde.class));
    }


    @Test
    public void endre_loggetInn_returnererRiktigMelding() {
        // Arrange
        Kunde eksisterendeKunde = new Kunde("12345678901", "Ola", "Nordmann", "Oslo", "0123", "Oslo", "12345678", "passord123");
        Kunde oppdatertKunde = new Kunde("12345678901", "Per", "Hansen", "Bergen", "4567", "Bergen", "87654321", "passord123");
        when(sjekk.loggetInn()).thenReturn("Admin");
        when(adminRepository.endreKundeInfo(oppdatertKunde)).thenReturn("Endring vellykket");

        // Act
        String resultat = adminKundeController.endre(oppdatertKunde);

        // Assert
        verify(sjekk).loggetInn();
        verify(adminRepository).endreKundeInfo(oppdatertKunde);
        assertEquals("Endring vellykket", resultat);
    }

    @Test
    public void endre_ikkeLoggetInn_returnererRiktigMelding() {
        // Arrange
        Kunde oppdatertKunde = new Kunde("12345678901", "Per", "Hansen", "Bergen", "4567", "Bergen", "87654321", "passord123");
        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        String resultat = adminKundeController.endre(oppdatertKunde);

        // Assert
        verify(sjekk).loggetInn();
        verifyNoInteractions(adminRepository);
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void slettKunde_loggetInn_returnererSlettetMelding() {
        // Arrange
        String loggetInnPersonnummer = "01010112345";
        String personnummer = "02020212345";
        when(sjekk.loggetInn()).thenReturn(loggetInnPersonnummer);
        when(adminRepository.slettKunde(personnummer)).thenReturn("Kunde med personnummer " + personnummer + " er slettet");

        // Act
        String resultat = adminKundeController.slett(personnummer);

        // Assert
        assertEquals("Kunde med personnummer " + personnummer + " er slettet", resultat);
        verify(sjekk).loggetInn();
        verify(adminRepository).slettKunde(personnummer);
    }

    @Test
    public void slettKunde_ikkeLoggetInn_returnererIkkeLoggetInnMelding() {
        // Arrange
        String personnummer = "02020212345";
        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        String resultat = adminKundeController.slett(personnummer);

        // Assert
        assertEquals("Ikke logget inn", resultat);
        verify(sjekk).loggetInn();
        verify(adminRepository, never()).slettKunde(personnummer);
    }


}
