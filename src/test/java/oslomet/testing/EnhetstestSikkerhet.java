package oslomet.testing;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import javax.servlet.http.HttpSession;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;


@RunWith(MockitoJUnitRunner.class)
public class EnhetstestSikkerhet {

    @InjectMocks
    //denne skal testes :
    private Sikkerhet sjekk;

    @Mock
    private HttpSession session;

    @Mock
    BankRepository bankRepository;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sjekk = new Sikkerhet();
        ReflectionTestUtils.setField(sjekk, "rep", bankRepository);
        ReflectionTestUtils.setField(sjekk, "session", session);
    }




    @Test
    public void sjekkLoggInn_validInputs_returnOk() {
        String personnummer = "12345678901";
        String passord = "passord123";
        String resultat = "OK";
        when(bankRepository.sjekkLoggInn(personnummer, passord)).thenReturn(resultat);

        String actual = sjekk.sjekkLoggInn(personnummer, passord);

        assertEquals("OK", actual);
        verify(session).setAttribute("Innlogget", personnummer);
    }

    @Test
    public void sjekkLoggInn_invalidPersonnummer_returnFeilPersonnummer() {
        String personnummer = "123";
        String passord = "passord123";

        String faktiske = sjekk.sjekkLoggInn(personnummer, passord);

        assertEquals("Feil i personnummer", faktiske);
        verifyNoInteractions(session);
        verifyNoInteractions(bankRepository);
    }

    @Test
    public void sjekkLoggInn_invalidPassord_returnFeilPassord() {
        String personnummer = "12345678901";
        String passord = "pwd";

        String faktiske = sjekk.sjekkLoggInn(personnummer, passord);

        assertEquals("Feil i passord", faktiske);
        verifyNoInteractions(session);
        verifyNoInteractions(bankRepository);
    }

    @Test
    public void sjekkLoggInn_invalidCredentials_returnFeilPersonnummerEllerPassord() {
        String personnummer = "12345678901";
        String passord = "passord123";
        String resultat = "Feil";
        when(bankRepository.sjekkLoggInn(personnummer, passord)).thenReturn(resultat);

        String faktiske = sjekk.sjekkLoggInn(personnummer, passord);

        assertEquals("Feil i personnummer eller passord", faktiske);
        verifyNoInteractions(session);
        verify(bankRepository).sjekkLoggInn(personnummer, passord);
    }



    @Test
    public void testLoggUt() {
        HttpSession mockSession = mock(HttpSession.class);
        Sikkerhet sjekk = new Sikkerhet();

// Set up session attribute "Innlogget" to simulate user being logged in
        String personnummer = "12345678901";


// Call loggUt method, which should set "Innlogget" to null
        sjekk.loggUt(mockSession);

// Verify that the session attribute "Innlogget" has been set to null
        verify(mockSession).setAttribute("Innlogget", null);
    }


    @Test
    public void testLoggInnAdmin_WithCorrectCredentials_ShouldReturnLoggetInn() {
        // Arrange
        String bruker = "Admin";
        String passord = "Admin";

        // Act
        String actualResult = sjekk.loggInnAdmin(bruker, passord);

        // Assert
        assertEquals("Logget inn", actualResult);
        verify(session).setAttribute("Innlogget", "Admin");
        verify(session, never()).setAttribute("Innlogget", null);
    }

    @Test
    public void testLoggInnAdmin_WithIncorrectCredentials_ShouldReturnIkkeLoggetInn() {
        // Arrange
        String bruker = "test";
        String passord = "test";

        // Act
        String actualResult = sjekk.loggInnAdmin(bruker, passord);

        // Assert
        assertEquals("Ikke logget inn", actualResult);
        verify(session).setAttribute("Innlogget", null);
        verify(session, never()).setAttribute("Innlogget", "Admin");
    }


    @Test
    public void testLoggetInn() {
        // Arrange
        HttpSession mockSession = mock(HttpSession.class);
        when(mockSession.getAttribute("Innlogget")).thenReturn("test-user");
        Sikkerhet sjekk = new Sikkerhet();
        sjekk.session = mockSession;

        // Act
        String actual = sjekk.loggetInn();

        // Assert
        assertEquals("test-user", actual);
    }

    @Test
    public void testIkkeLoggetInn() {
        // Arrange
        HttpSession mockSession = mock(HttpSession.class);
        String expected = null;

        // Set up mock session to simulate user not being logged in
        //when(mockSession.getAttribute("Innlogget")).thenReturn(null);

        // Act
        String actual = sjekk.loggetInn();

        // Assert
        assertEquals(expected, actual);
    }
}