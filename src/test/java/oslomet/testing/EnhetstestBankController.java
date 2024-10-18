package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentKundeInfo_loggetInn() {

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentKonti_LoggetInn()  {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }


    @Test
    public void testHentTransaksjoner() {
        // arrange
        String kontoNr = "12345678901";
        String fraDato = "2022-01-01";
        String tilDato = "2022-01-31";
        String personnummer = "01010110523";
        Konto expectedKonto = new Konto(kontoNr, personnummer, 1000, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn(personnummer);
        when(repository.hentTransaksjoner(kontoNr, fraDato, tilDato)).thenReturn(expectedKonto);

        // act
        Konto resultKonto = bankController.hentTransaksjoner(kontoNr, fraDato, tilDato);

        // assert
        assertEquals(expectedKonto, resultKonto);
    }

    @Test
    public void testHentTransaksjoner_ikke() {
        // arrange
        String kontoNr = "12345678901";
        String fraDato = "2022-01-01";
        String tilDato = "2022-01-31";

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        Konto resultKonto = bankController.hentTransaksjoner(kontoNr, fraDato, tilDato);

        // assert
        assertNull(resultKonto);
    }



    @Test
    public void hentSaldi_OK() {

        // arrange
        String personnummer = "01010110523";
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", personnummer,
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", personnummer,
                1000, "Sparekonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn(personnummer);
        when(repository.hentSaldi(personnummer)).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentSaldi_ikke() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertNull(resultat);
    }



    @Test
    public void registrerBetaling_OK() {
        // arrange
        Transaksjon enTransaksjon = new Transaksjon(1,"12345678910",130.00,"2020-03-04","Hei Hei","Avventer","01010110523");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.registrerBetaling(any(Transaksjon.class))).thenReturn("Betaling registrert");

        // act
        String resultat = bankController.registrerBetaling(enTransaksjon);

        // assert
        assertEquals("Betaling registrert", resultat);
    }

    @Test
    public void registrerBetaling_Ikke() {
        // arrange
        Transaksjon enTransaksjon = new Transaksjon(1,"12345678910",130.00,"2020-03-04","Hei Hei","Avventer","01010110523");

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = bankController.registrerBetaling(enTransaksjon);

        // assert
        assertNull(resultat);
    }



    @Test
    public void hentBetalinger_loggetInn() {
        // arrange
        String personnummer = "01010110523";
        List<Transaksjon> betalinger = new ArrayList<>();
        Transaksjon betaling1 = new Transaksjon(2, "01010110523",
                500.00, "23-02-2023", "Mat","avventer","12345678901");
        Transaksjon betaling2 = new Transaksjon(3, "01010110523",
                1000.00, "22-02-2023", "Husleie","avventer", "12345678901");
        betalinger.add(betaling1);
        betalinger.add(betaling2);

        when(sjekk.loggetInn()).thenReturn(personnummer);
        when(repository.hentBetalinger(personnummer)).thenReturn(betalinger);

        // act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // assert
        assertEquals(betalinger, resultat);
    }

    @Test
    public void hentBetalinger_ikkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        // assert
        assertNull(resultat);
    }


    @Test
    public void utforBetaling_loggetInn() {
        // arrange
        String personnummer = "01010110523";
        int txID = 1;

        when(sjekk.loggetInn()).thenReturn(personnummer);
        when(repository.utforBetaling(txID)).thenReturn("OK");
        List<Transaksjon> betalinger = new ArrayList<>();
        when(repository.hentBetalinger(personnummer)).thenReturn(betalinger);

        // act
        List<Transaksjon> resultat = bankController.utforBetaling(txID);

        // assert
        verify(sjekk, times(1)).loggetInn();
        verify(repository, times(1)).utforBetaling(txID);
        verify(repository, times(1)).hentBetalinger(personnummer);
        assertEquals(betalinger, resultat);
    }

    @Test
    public void utforBetaling_ikkeLoggetInn() {
        // arrange
        int txID = 1;

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Transaksjon> resultat = bankController.utforBetaling(txID);

        // assert
        verify(sjekk, times(1)).loggetInn();
        verify(repository, never()).utforBetaling(anyInt());
        verify(repository, never()).hentBetalinger(anyString());
        assertNull(resultat);
    }

    @Test
    public void utforBetaling_feilUtforing() {
        // arrange
        String personnummer = "01010110523";
        int txID = 1;

        when(sjekk.loggetInn()).thenReturn(personnummer);
        when(repository.utforBetaling(txID)).thenReturn("Feil");

        // act
        List<Transaksjon> resultat = bankController.utforBetaling(txID);

        // assert
        verify(sjekk, times(1)).loggetInn();
        verify(repository, times(1)).utforBetaling(txID);
        verify(repository, never()).hentBetalinger(anyString());
        assertNull(resultat);
    }


    @Test
    public void testEndreKundeInfo() {
        // Mock input data
        String personnummer = "12345678901";
        Kunde innKunde = new Kunde(personnummer,"Ola","Nordmann","Storgata 1",
                "3040","Drammen","123456987","12345625");

        // Mock session data
        when(sjekk.loggetInn()).thenReturn(personnummer);

        // Mock repository method call
        when(repository.endreKundeInfo(innKunde)).thenReturn("OK");

        // Call controller method and check output
        String result = bankController.endre(innKunde);
        assertEquals("OK", result);

        // Verify that the repository method was called with the expected input
        verify(repository).endreKundeInfo(innKunde);
    }

}


