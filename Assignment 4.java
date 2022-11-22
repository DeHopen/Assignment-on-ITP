import java.util.List;
import java.util.Map;

enum PieceColor {
    WHITE,
    BLACK;

    public PieceColor parse(String color) {
        if (color.equals("White")) {
            return WHITE;
        }
        if (color.equals("Black")) {
            return BLACK;
        }
        return null;
    }
}
abstract class Position {
    private int x;
    private int y;
    public Position(int x, int y) {
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
        return "(" + x + ", " + y + ")";
    }

}
interface BishopMovement{
    default int getDiagonalMovesCount() {
        List<Position> or
    }
}
interface RookMovement{
    default int getHorizontalMovesCount() {
        return 0;
    }
    default int getVerticalMovesCount() {
        return 0;
    }
}

class Board {
    private Map<String, ChessPiece> piecePosition;
    private int size;

    public Board(int size) {
        this.size = size;
    }

    public void addPiece(ChessPiece piece) {
        piecePosition.put(piece.getPosition().toString(), piece);
    }

    public ChessPiece getPiece(Position position) {
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
    protected Position position;
    public ChessPiece(PieceColor color, Position position) {
        this.color = color;
        this.position = position;
    }
    public PieceColor getColor() {
        return color;
    }
    public Position getPosition() {
        return position;
    }
    public int getMovesCount() {
        return 0;
    }
    public int getCapturesCount() {
        return 0;
    }

}

class King extends ChessPiece {
    public King(PieceColor color, Position position) {
        super(color, position);
    }
}

class Knight extends ChessPiece {
    public Knight(PieceColor color, Position position) {
        super(color, position);
    }
}

class Bishop extends ChessPiece implements BishopMovement {
    public Bishop(PieceColor color, Position position) {
        super(color, position);
    }
    @Override
    public int getMovesCount() {
        return getDiagonalMovesCount();
    }
}

class Rook extends ChessPiece implements RookMovement {
    public Rook(PieceColor color, Position position) {
        super(color, position);
    }
    @Override
    public int getMovesCount() {
        return getHorizontalMovesCount() + getVerticalMovesCount();
    }
}

class Queen extends ChessPiece implements BishopMovement, RookMovement {
    public Queen(PieceColor color, Position position) {
        super(color, position);
    }
    @Override
    public int getMovesCount() {
        return getHorizontalMovesCount() + getVerticalMovesCount() + getDiagonalMovesCount();
    }
}

class Pawn extends ChessPiece {
    public Pawn(PieceColor color, Position position) {
        super(color, position);
    }
}
class Exception{
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
    private static Board board;

    public static void main(String[] args) {
        
    }
}


