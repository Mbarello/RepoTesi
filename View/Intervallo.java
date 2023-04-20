package View;

import java.time.LocalDateTime;

public class Intervallo {
    LocalDateTime inizio;
    LocalDateTime fine;

    public Intervallo(LocalDateTime inizio, LocalDateTime fine) {
        this.inizio = inizio;
        this.fine = fine;
    }

    public Intervallo(){}
    public LocalDateTime getInizio() {
        return inizio;
    }

    public void setInizio(LocalDateTime inizio) {
        this.inizio = inizio;
    }

    public LocalDateTime getFine() {
        return fine;
    }

    public void setFine(LocalDateTime fine) {
        this.fine = fine;
    }


}
