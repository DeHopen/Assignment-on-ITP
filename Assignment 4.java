
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    private static Board chessBoard;
    static int maxBoardSize = 1000;
    static int minBoardSize = 3;
    static int minNumberOfPieces = 2;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("input.txt"));
        int boardSize = sc.nextInt();
        int numberOfPieces = sc.nextInt();
        chessBoard = new Board(boardSize);
        PrintWriter writer;
        // output file
        try {
            writer = new PrintWriter("output.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        try {
            if (boardSize < minBoardSize || boardSize > maxBoardSize) {
                throw new InvalidBoardSizeException();
            }

            if (numberOfPieces < minNumberOfPieces || numberOfPieces > boardSize * boardSize) {
                throw new InvalidNumberOfPiecesException();
            }

            int numberOfWhiteKings = 0;
            int numberOfBlackKings = 0;

            for (int i = 0; i < numberOfPieces; i++) {
                ChessPiece.PieceName pieceName = ChessPiece.PieceName.parse(sc.next());

                if (pieceName == null) {
                    throw new InvalidPieceNameException();
                }

                PieceColor color = PieceColor.parse(sc.next());

                if (color == null) {
                    throw new InvalidPieceColorException();
                }

                // check pieceName
                PiecePosition position = new PiecePosition(sc.nextInt(), sc.nextInt());

                if (position.exceeds(boardSize)) {
                    throw new InvalidPiecePositionException();
                }

                ChessPiece piece = ChessPiece.chessFactory(pieceName, color, position);

                if (piece == null) {
                    throw new InvalidPieceNameException();
                }

                if (piece instanceof King) {
                    if (piece.getColor() == PieceColor.WHITE) {
                        numberOfWhiteKings++;
                    } else {
                        numberOfBlackKings++;
                    }
                }

                chessBoard.addPiece(piece);
            }

            if (sc.hasNext()) {
                throw new InvalidNumberOfPiecesException();
            }

            if (numberOfWhiteKings != 1 || numberOfBlackKings != 1) {
                throw new InvalidGivenKingsException();
            }


            for (ChessPiece piece : chessBoard.getPieces()) {
                int captures = chessBoard.getPiecePossibleCapturesCount(piece);
                int moves = chessBoard.getPiecePossibleMovesCount(piece);
                writer.println(moves + " " + captures);
            }
        } catch (Exception e) {
            writer.println(e.getMessage());
        } finally {
            sc.close();
            writer.close();
        }
    }

}


abstract class ChessPiece {
    protected PiecePosition position;
    protected PieceColor color;

    public ChessPiece(PiecePosition piecePosition, PieceColor pieceColor) {
        this.position = piecePosition;
        this.color = pieceColor;
    }

    public PieceColor getColor() {
        return color;
    }

    public PiecePosition getPosition() {
        return position;
    }

    abstract public int getMovesCount(Map<String, ChessPiece> positions, int boardSize);

    abstract public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize);

    public enum PieceName {
        KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN;

        public static PieceName parse(String pieceName) {
            switch (pieceName) {
                case "King":
                    return KING;
                case "Queen":
                    return QUEEN;
                case "Rook":
                    return ROOK;
                case "Bishop":
                    return BISHOP;
                case "Knight":
                    return KNIGHT;
                case "Pawn":
                    return PAWN;
                default:
                    return null;
            }
        }
    }

    public static ChessPiece chessFactory(PieceName pieceName, PieceColor color, PiecePosition position) {
        switch (pieceName) {
            case KING:
                return new King(color, position);
            case KNIGHT:
                return new Knight(color, position);
            case BISHOP:
                return new Bishop(color, position);
            case ROOK:
                return new Rook(color, position);
            case QUEEN:
                return new Queen(color, position);
            case PAWN:
                return new Pawn(color, position);
            default:
                return null;
        }
    }

}

class King extends ChessPiece {
    public King(PieceColor color, PiecePosition position) {
        super(position, color);
    }

    @Override
    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        int moves = 0;
        int x = position.getX();
        int y = position.getY();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {

                if (i == 0 && j == 0) {
                    continue;
                }
                PiecePosition newPosition = new PiecePosition(x + i, y + j);
                if (newPosition.exceeds(boardSize)) {
                    continue;
                }

                ChessPiece piece = positions.get(newPosition.toString());

                if (piece == null || piece.getColor() != color) {
                    moves++;
                }
            }
        }

        return moves;
    }

    @Override
    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        int captures = 0;
        int x = position.getX();
        int y = position.getY();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {

                if (i == 0 && j == 0) {
                    continue;
                }
                PiecePosition newPosition = new PiecePosition(x + i, y + j);
                if (newPosition.exceeds(boardSize)) {
                    continue;
                }

                ChessPiece piece = positions.get(newPosition.toString());

                if (piece != null && piece.getColor() != color) {
                    captures++;
                }
            }
        }

        return captures;
    }
}

class Knight extends ChessPiece {
    public Knight(PieceColor color, PiecePosition position) {
        super(position, color);
    }

    @Override
    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        int x = position.getX();
        int y = position.getY();

        int[] directions = {-2, -1, 1, 2};
        int moves = 0;

        for (int xDir : directions) {
            for (int yDir : directions) {
                if (Math.abs(xDir) != Math.abs(yDir)) {
                    PiecePosition newPosition = new PiecePosition(x + xDir, y + yDir);

                    if (newPosition.exceeds(boardSize)) {
                        continue;
                    }

                    ChessPiece piece = positions.get(newPosition.toString());
                    if (piece == null || piece.getColor() != color) {
                        moves++;
                    }
                }
            }
        }

        return moves;


    }

    @Override
    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        int x = position.getX();
        int y = position.getY();

        int[] directions = {-2, -1, 1, 2};
        int captures = 0;

        for (int xDir : directions) {
            for (int yDir : directions) {
                if (Math.abs(xDir) != Math.abs(yDir)) {
                    PiecePosition newPosition = new PiecePosition(x + xDir, y + yDir);

                    if (newPosition.exceeds(boardSize)) {
                        continue;
                    }

                    ChessPiece piece = positions.get(newPosition.toString());
                    if ((piece != null) && piece.getColor() != color) {
                        captures++;
                    }
                }
            }
        }

        return captures;
    }
}

class Bishop extends ChessPiece implements BishopMovement {
    public Bishop(PieceColor color, PiecePosition position) {
        super(position, color);
    }

    @Override
    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getDiagonalMovesCount(position, boardSize, color, positions);

    }

    @Override
    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getDiagonalCapturesCount(position, boardSize, color, positions);
    }
}

class Rook extends ChessPiece implements RookMovement {
    public Rook(PieceColor color, PiecePosition position) {
        super(position, color);
    }


    @Override
    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getOrthogonalMovesCount(position, boardSize, color, positions);
    }

    @Override
    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getOrthogonalCapturesCount(position, boardSize, color, positions);
    }
}

class Queen extends ChessPiece implements BishopMovement, RookMovement {
    public Queen(PieceColor color, PiecePosition position) {
        super(position, color);
    }


    @Override
    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getOrthogonalMovesCount(position, boardSize, color, positions) +
                getDiagonalMovesCount(position, boardSize, color, positions);
    }

    @Override
    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        return getOrthogonalCapturesCount(position, boardSize, color, positions) +
                getDiagonalCapturesCount(position, boardSize, color, positions);
    }

}

class Pawn extends ChessPiece {
    public Pawn(PieceColor color, PiecePosition position) {
        super(position, color);
    }

    @Override
    public int getMovesCount(Map<String, ChessPiece> positions, int boardSize) {
        int x = position.getX();
        int y = position.getY();

        int moves;

        int yDir = color == PieceColor.WHITE ? 1 : -1;
        PiecePosition newPosition = new PiecePosition(x, y + yDir);


        if (newPosition.exceeds(boardSize) || positions.containsKey(newPosition.toString())) {
            moves = 0;
        } else {
            moves = 1;
        }

        return moves + getCapturesCount(positions, boardSize);
    }

    @Override
    public int getCapturesCount(Map<String, ChessPiece> positions, int boardSize) {
        int x = position.getX();
        int y = position.getY();

        int captures = 0;

        int yDir = color == PieceColor.WHITE ? 1 : -1;
        int[] xDirs = {-1, 1};

        for (int xDir : xDirs) {
            PiecePosition newPosition = new PiecePosition(x + xDir, y + yDir);

            if (newPosition.exceeds(boardSize)) {
                continue;
            }

            ChessPiece piece = positions.get(newPosition.toString());
            if ((piece != null) && piece.getColor() != color) {
                captures++;
            }
        }

        return captures;
    }
}

abstract class Exception extends Throwable {
    abstract public String getMessage();
}


interface BishopMovement {
    default int getDiagonalMovesCount(PiecePosition position, int boardSize, PieceColor color, Map<String, ChessPiece> positions) {
        int moves = 0;
        int x = position.getX();
        int y = position.getY();

        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        for (int[] direction : directions) {
            int i = x;
            int j = y;

            while (true) {
                i += direction[0];
                j += direction[1];
                if (PiecePosition.exceeds(i, j, boardSize)) {
                    break;
                }

                if (positions.containsKey(i + " " + j)) {
                    if (positions.get(i + " " + j).getColor() != color) {
                        moves++;
                    }
                    break;
                }

                moves++;
            }
        }
        return moves;
    }

    default int getDiagonalCapturesCount(PiecePosition position, int boardSize, PieceColor color, Map<String, ChessPiece> positions) {
        int captures = 0;
        int x = position.getX();
        int y = position.getY();

        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        for (int[] direction : directions) {
            int i = x;
            int j = y;

            while (true) {
                i += direction[0];
                j += direction[1];
                if (PiecePosition.exceeds(i, j, boardSize)) {
                    break;
                }

                if (positions.containsKey(i + " " + j)) {
                    if (positions.get(i + " " + j).getColor() != color) {
                        captures++;
                    }
                    break;
                }

            }
        }
        return captures;
    }

}

interface RookMovement {
    default int getOrthogonalMovesCount(PiecePosition position, int boardSize, PieceColor color, Map<String, ChessPiece> positions) {
        int moves = 0;
        int x = position.getX();
        int y = position.getY();

        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int[] direction : directions) {
            int i = x;
            int j = y;

            while (true) {
                i += direction[0];
                j += direction[1];
                if (PiecePosition.exceeds(i, j, boardSize)) {
                    break;
                }

                if (positions.containsKey(i + " " + j)) {
                    if (positions.get(i + " " + j).getColor() != color) {
                        moves++;
                    }
                    break;
                }

                moves++;
            }
        }
        return moves;
    }

    default int getOrthogonalCapturesCount(PiecePosition position, int boardSize, PieceColor color, Map<String, ChessPiece> positions) {
        int captures = 0;
        int x = position.getX();
        int y = position.getY();

        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        for (int[] direction : directions) {
            int i = x;
            int j = y;

            while (true) {
                i += direction[0];
                j += direction[1];

                if (PiecePosition.exceeds(i, j, boardSize)) {
                    break;
                }

                if (positions.containsKey(i + " " + j)) {
                    if (positions.get(i + " " + j).getColor() != color) {
                        captures++;
                    }
                    break;
                }

            }
        }
        return captures;
    }
}

class InvalidPiecePositionException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid piece position";
    }
}

class InvalidBoardSizeException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid board size";
    }
}

class InvalidNumberOfPiecesException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid number of pieces";
    }
}

class InvalidPieceColorException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid piece color";
    }
}

class InvalidPieceNameException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid piece name";
    }
}

class InvalidInputException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid input";
    }
}

class InvalidGivenKingsException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid given Kings";
    }
}


class Board {
    private final Map<String, ChessPiece> positionsToPieces;
    private final List<ChessPiece> pieces = new ArrayList<>();
    private final int size;

    public Board(int boardSize) {
        this.size = boardSize;
        this.positionsToPieces = new HashMap<>(boardSize * boardSize);
    }

    public void addPiece(ChessPiece piece) throws InvalidPiecePositionException {
        String key = piece.getPosition().toString();

        if (positionsToPieces.containsKey(key)) {
            throw new InvalidPiecePositionException();
        }

        positionsToPieces.put(key, piece);
        pieces.add(piece);
    }

    public ChessPiece getPiece(PiecePosition position) {
        return positionsToPieces.get(position.toString());
    }

    public int getPiecePossibleMovesCount(ChessPiece piece) {
        return piece.getMovesCount(positionsToPieces, size);
    }

    public int getPiecePossibleCapturesCount(ChessPiece piece) {
        return piece.getCapturesCount(positionsToPieces, size);
    }

    public List<ChessPiece> getPieces() {
        return pieces;
    }
}


enum PieceColor {
    WHITE,
    BLACK;

    static public PieceColor parse(String color) {
        if (color.equals("White")) {
            return WHITE;
        } else if (color.equals("Black")) {
            return BLACK;
        } else {
            return null;
        }
    }
}

class PiecePosition {
    private final int x;
    private final int y;

    public PiecePosition(int onX, int onY) {
        x = onX;
        y = onY;
    }

    public boolean exceeds(int boardSize) {
        return PiecePosition.exceeds(x, y, boardSize);
    }

    static public boolean exceeds(int x, int y, int boardSize) {
        return (x < 1) || (x > boardSize) || (y < 1) || (y > boardSize);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return x + " " + y;
    }

}
//
