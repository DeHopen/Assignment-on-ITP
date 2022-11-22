import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

enum PieceColor {
    WHITE,
    BLACK;

    public PieceColor parse(String color) {
        if (color.equals("White")) {
            return WHITE;
        } else if (color.equals("Black")) {
            return BLACK;
        } else {
            throw new IllegalArgumentException("Invalid color");
        }
    }
}
class PiecePosition {
    private int x;
    private int y;
    public PiecePosition(int x, int y) {
        this.x = x;
        this.y = y;
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
interface BishopMovement {
    default int getDiagonalMovesCount(PiecePosition position, int boardSize, PieceColor color, Map<String, ChessPiece> positions) {
        int count = 0;
        int x = position.getX();
        int y = position.getY();
        int i = x + 1;
        int j = y + 1;
        while (i < boardSize && j < boardSize) {
            if (positions.containsKey(i + " " + j)) {
                if (positions.get(i + " " + j).getColor() != color) {
                    count++;
                }
                break;
            }
            count++;
            i++;
            j++;
        }
        i = x - 1;
        j = y - 1;
        while (i >= 0 && j >= 0) {
            if (positions.containsKey(i + " " + j)) {
                if (positions.get(i + " " + j).getColor() != color) {
                    count++;
                }
                break;
            }
            count++;
            i--;
            j--;
        }
        i = x + 1;
        j = y - 1;
        while (i < boardSize && j >= 0) {
            if (positions.containsKey(i + " " + j)) {
                if (positions.get(i + " " + j).getColor() != color) {
                    count++;
                }
                break;
            }
            count++;
            i++;
            j--;
        }
        i = x - 1;
        j = y + 1;
        while (i >= 0 && j < boardSize) {
            if (positions.containsKey(i + " " + j)) {
                if (positions.get(i + " " + j).getColor() != color) {
                    count++;
                }
                break;
            }
            count++;
            i--;
            j++;
        }
        return count;
    }

    default int getDiagonalCapturesCount(PiecePosition position, int boardSize, PieceColor color, Map<String, ChessPiece> positions) {
        int count = 0;
        int x = position.getX();
        int y = position.getY();
        int i = x + 1;
        int j = y + 1;
        while (i < boardSize && j < boardSize) {
            if (positions.containsKey(i + " " + j)) {
                if (positions.get(i + " " + j).getColor() != color) {
                    count++;
                }
                break;
            }
            i++;
            j++;
        }
        i = x - 1;
        j = y - 1;
        while (i >= 0 && j >= 0) {
            if (positions.containsKey(i + " " + j)) {
                if (positions.get(i + " " + j).getColor() != color) {
                    count++;
                }
                break;
            }
            i--;
            j--;
        }
        i = x + 1;
        j = y - 1;
        while (i < boardSize && j >= 0) {
            if (positions.containsKey(i + " " + j)) {
                if (positions.get(i + " " + j).getColor() != color) {
                    count++;
                }
                break;
            }
            i++;
            j--;
        }
        i = x - 1;
        j = y + 1;
        while (i >= 0 && j < boardSize) {
            if (positions.containsKey(i + " " + j)) {
                if (positions.get(i + " " + j).getColor() != color) {
                    count++;
                }
                break;
            }
            i--;
            j++;
        }
        return count;
    }
}
interface RookMovement{
    default int getHorizontalMovesCount(PiecePosition position, int boardSize, PieceColor color, Map<String, ChessPiece> positions) {
        int count = 0;
        int x = position.getX();
        int y = position.getY();
        int i = x + 1;
        while (i < boardSize) {
            if (positions.containsKey(i + " " + y)) {
                if (positions.get(i + " " + y).getColor() != color) {
                    count++;
                }
                break;
            }
            count++;
            i++;
        }
        i = x - 1;
        while (i >= 0) {
            if (positions.containsKey(i + " " + y)) {
                if (positions.get(i + " " + y).getColor() != color) {
                    count++;
                }
                break;
            }
            count++;
            i--;
        }
        return count;
    }
    default int getVerticalMovesCount(PiecePosition position, int boardSize, PieceColor color, Map<String, ChessPiece> positions) {
        int count = 0;
        int x = position.getX();
        int y = position.getY();
        int j = y + 1;
        while (j < boardSize) {
            if (positions.containsKey(x + " " + j)) {
                if (positions.get(x + " " + j).getColor() != color) {
                    count++;
                }
                break;
            }
            count++;
            j++;
        }
        j = y - 1;
        while (j >= 0) {
            if (positions.containsKey(x + " " + j)) {
                if (positions.get(x + " " + j).getColor() != color) {
                    count++;
                }
                break;
            }
            count++;
            j--;
        }
        return count;
    }
}

class Board {
    private Map<String, ChessPiece> piecePosition;
    private int size;

    public Board(int boardSize) {
        this.size = boardSize;
    }

    public void addPiece(ChessPiece piece) {
        piecePosition.put(piece.getPosition().toString(), piece);
    }

    public ChessPiece getPiece(PiecePosition position) {
        return piecePosition.get(position.toString());
    }

    public int getPiecePossibleMovesCount(ChessPiece piece) {
        return piece.getMovesCount();
    }

    public int getPiecePossibleCapturesCount(ChessPiece piece) {
        return piece.getCapturesCount();
    }
}

abstract class ChessPiece {
    protected PieceColor color;
    protected PiecePosition position;
    public ChessPiece(PieceColor color, PiecePosition position) {
        this.color = color;
        this.position = position;
    }
    public PieceColor getColor() {

        return color;
    }
    public PiecePosition getPosition() {

        return position;
    }
    public int getMovesCount() {

    }
    public int getCapturesCount() {

    }

}

class King extends ChessPiece {
    public King(PieceColor color, PiecePosition position) {
        super(color, position);
    }
}

class Knight extends ChessPiece {
    public Knight(PieceColor color, PiecePosition position) {
        super(color, position);
    }
}

class Bishop extends ChessPiece implements BishopMovement {
    public Bishop(PieceColor color, PiecePosition position) {
        super(color, position);
    }
}

class Rook extends ChessPiece implements RookMovement {
    public Rook(PieceColor color, PiecePosition position) {
        super(color, position);
    }
}

class Queen extends ChessPiece implements BishopMovement, RookMovement {
    public Queen(PieceColor color, PiecePosition position) {
        super(color, position);
    }
}

class Pawn extends ChessPiece {
    public Pawn(PieceColor color, PiecePosition position) {
        super(color, position);
    }
}
class Exception extends Throwable {
    public String getMessages(){

        return "Invalid Position";
    }
}
class InvalidPiecePositionException extends Exception{
    public String getMessages(){

        return "Invalid Position";
    }
}
class InvalidBoardSizeException extends Exception{
    public String getMessages(){

        return "Invalid Board Size";
    }
}
class InvalidNumberOfPiecesException extends Exception{
    public String getMessages(){

        return "Invalid Number of Pieces";
    }
}
class InvalidPieceColorException extends Exception{
    public String getMessages(){

        return "Invalid Piece Color";
    }
}
class InvalidPieceNameException extends Exception{
    public String getMessages(){

        return "Invalid Piece Name";
    }
}
class InvalidInputException extends Exception{
    public String getMessages(){

        return "Invalid Input";
    }
}
class InvalidGivenKingsException extends Exception{
    public String getMessages(){

        return "Invalid Given Kings";
    }
}

class Main {
    private static Board chessBoard;

    public static void main(String[] args) throws InvalidBoardSizeException, InvalidNumberOfPiecesException, InvalidPiecePositionException, InvalidPieceNameException, InvalidGivenKingsException, FileNotFoundException, InvalidPieceColorException {
        File file = new File("input.txt");
        Scanner sc = new Scanner(file);
        int boardSize = sc.nextInt();
        if (boardSize < 1 || boardSize > 8) {
            throw new InvalidBoardSizeException();
        }
        chessBoard = new Board(boardSize);
        int numberOfPieces = sc.nextInt();
        if (numberOfPieces < 1 || numberOfPieces > 32) {
            throw new InvalidNumberOfPiecesException();
        }
        int numberOfKings = 0;
        for (int i = 0; i < numberOfPieces; i++) {
            String pieceName = sc.next();
            String pieceColor = sc.next();
            int x = sc.nextInt();
            int y = sc.nextInt();
            if (x < 0 || x >= boardSize || y < 0 || y >= boardSize) {
                throw new InvalidPiecePositionException();
            }
            PiecePosition position = new PiecePosition(x, y);
            PieceColor color;
            if (pieceColor.equals("White")) {
                color = PieceColor.WHITE;
            } else if (pieceColor.equals("Black")) {
                color = PieceColor.BLACK;
            } else {
                throw new InvalidPieceColorException();
            }
            ChessPiece piece;
            switch (pieceName) {
                case "King":
                    piece = new King(color, position);
                    numberOfKings++;
                    break;
                case "Queen":
                    piece = new Queen(color, position);
                    break;
                case "Rook":
                    piece = new Rook(color, position);
                    break;
                case "Bishop":
                    piece = new Bishop(color, position);
                    break;
                case "Knight":
                    piece = new Knight(color, position);
                    break;
                case "Pawn":
                    piece = new Pawn(color, position);
                    break;
                default:
                    throw new InvalidPieceNameException();
            }
            chessBoard.addPiece(piece);
        }
        if (numberOfKings != 2) {
            throw new InvalidGivenKingsException();
        }
        int numberOfMoves = sc.nextInt();
        for (int i = 0; i < numberOfMoves; i++) {
            String pieceName = sc.next();
            String pieceColor = sc.next();
            int x = sc.nextInt();
            int y = sc.nextInt();
            if (x < 0 || x >= boardSize || y < 0 || y >= boardSize) {
                throw new InvalidPiecePositionException();
            }
            PiecePosition position = new PiecePosition(x, y);
            PieceColor color;
            if (pieceColor.equals("White")) {
                color = PieceColor.WHITE;
            } else if (pieceColor.equals("Black")) {
                color = PieceColor.BLACK;
            } else {
                throw new InvalidPieceColorException();
            }
            ChessPiece piece;
            switch (pieceName) {
                case "King":
                    piece = new King(color, position);
                    break;
                case "Queen":
                    piece = new Queen(color, position);
                    break;
                case "Rook":
                    piece = new Rook(color, position);
                    break;
                case "Bishop":
                    piece = new Bishop(color, position);
                    break;
                case "Knight":
                    piece = new Knight(color, position);
                    break;
                case "Pawn":
                    piece = new Pawn(color, position);
                    break;
                default:
                    throw new InvalidPieceNameException();
            }
            System.out.println(chessBoard.getPiecePossibleMovesCount(piece));
            System.out.println(chessBoard.getPiecePossibleCapturesCount(piece));
        }
    }
}

