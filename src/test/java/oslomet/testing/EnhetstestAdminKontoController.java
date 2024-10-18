package oslomet.testing;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Sikkerhet.Sikkerhet;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class EnhetstestAdminKontoController {

    @InjectMocks
    //denne skal testes
    private AdminKontoController adminKontoController;

    @Mock
    //denne skal Mock-es
    private AdminRepository adminRepository;

    @Mock
    //denne skal Mocke-s
    private Sikkerhet sjekk;


    @Test
    public void testHentAlleKonto_OK() {


        // Arrange
        String personnummer = "12345678901"; // example personnummer
        List<Konto> expected = new ArrayList<>();
        expected.add(new Konto("123456789", "12345678901", 1000, "Brukskonto","nok",null));
        expected.add(new Konto("987654321", "986575231", 2000, "Brukskonto","nok",null));
        when(sjekk.loggetInn()).thenReturn(personnummer);
        when(adminRepository.hentAlleKonti()).thenReturn(expected);
        // Act
        List<Konto> actual = adminKontoController.hentAlleKonti();
        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testHentAlleKontiScenario() {
        // Arrange
        when(sjekk.loggetInn()).thenReturn(null);
        // Act
        List<Konto> actual = adminKontoController.hentAlleKonti();
        // Assert
        Assert.assertNull(actual);
    }

    @Test
    public void testRegistrerKonto() {
        // Arrange
        Konto konto = new Konto("123456789", "12345678901", 1000, "Brukskonto","nok",null);
        String expected = "Konto registrert";
        when(sjekk.loggetInn()).thenReturn("12345678901");
        when(adminRepository.registrerKonto(konto)).thenReturn(expected);

        // Act
        String actual = adminKontoController.registrerKonto(konto);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testRegistrerKontoNotLoggedIn() {
        // Arrange
        Konto konto = new Konto("123456789", "12345678901", 1000, "Brukskonto","nok",null);
        String expected = "Ikke innlogget";
        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        String actual = adminKontoController.registrerKonto(konto);

        // Assert
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void testEndreKontoLoggedIn() {
        // Arrange
        Konto konto = new Konto("123456789", "12345678901",
                1000, "Brukskonto","nok",null);
        String expected = "Konto endret";
        when(sjekk.loggetInn()).thenReturn("12345678901");
        when(adminRepository.endreKonto(konto)).thenReturn(expected);

        // Act
        String actual = adminKontoController.endreKonto(konto);

        // Assert
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testEndreKontoNotLoggedIn() {
        // Arrange
        Konto konto = new Konto("123456789", "12345678901",
                1000, "Brukskonto","nok",null);
        String expected = "Ikke innlogget";
        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        String actual = adminKontoController.endreKonto(konto);

        // Assert
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void testSlettKonto_OK() {
        // Arrange
        String kontonummer = "12345678901";
        String personnummer = "01010112345";
        when(sjekk.loggetInn()).thenReturn(personnummer);
        when(adminRepository.slettKonto(kontonummer)).thenReturn("Konto slettet");

        // Act
        String result = adminKontoController.slettKonto(kontonummer);

        // Assert
        assertEquals("Konto slettet", result);
    }


    @Test
    public void testSlettKonto_IKKEinnlogget() {
        // Arrange
        String kontonummer = "12345678901";
        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        String result = adminKontoController.slettKonto(kontonummer);

        // Assert
        assertEquals("Ikke innlogget", result);
    }

}
