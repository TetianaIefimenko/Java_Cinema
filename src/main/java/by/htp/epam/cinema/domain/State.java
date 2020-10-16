package by.htp.epam.cinema.domain;


public enum State {

    FREE("green"),
    BOOKED("yellow"),
    OCCUPIED("red");

    String buttonColor;

    State(String buttonColor) {
        this.buttonColor = buttonColor;
    }

    public String getButtonColor() {
        return buttonColor;
    }
}