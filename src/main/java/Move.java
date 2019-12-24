
public enum Move {
    L,
    R,
    U,
    D;

    Move opposite() {
        switch (this) {
            case L:
                return R;
            case R:
                return L;
            case U:
                return D;
            case D:
                return U;
        }
        throw new IllegalStateException();
    }
}