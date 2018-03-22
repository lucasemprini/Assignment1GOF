package model;

public class CellImpl implements Cell {

    private boolean currentState;
    private boolean nextState;

    public CellImpl(final boolean state) {
        this.currentState = state;
    }

    @Override
    public boolean getCurrentState() {
        return this.currentState;
    }

    @Override
    public boolean getNextState() {
        return this.nextState;
    }

    @Override
    public void setNextState(boolean nextState) {
        this.nextState = nextState;
    }

    @Override
    public void updateState() {
        this.currentState = this.nextState;
    }
}
